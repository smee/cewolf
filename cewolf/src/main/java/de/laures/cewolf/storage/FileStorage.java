/* ================================================================
 * Cewolf : Chart enabling Web Objects Framework
 * ================================================================
 *
 * Project Info:  http://cewolf.sourceforge.net
 * Project Lead:  Guido Laures (guido@laures.de);
 *
 * (C) Copyright 2002, by Guido Laures
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 */

package de.laures.cewolf.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import de.laures.cewolf.CewolfException;
import de.laures.cewolf.ChartImage;
import de.laures.cewolf.Configuration;
import de.laures.cewolf.Storage;
import de.laures.cewolf.taglib.util.KeyGenerator;

/**
 * Storage for storing images as files in the web application directory as files _chart-XXXXX.
 * Note that by default the files won't ever be removed. To remove saved images on VM exit set
 * the <code>FileStorage.deleteOnExit</code> configuration parameter to "true". For example:
 * 
 * <pre>
 *		<init-param>
 *			<param-name>storage</param-name>
 *			<param-value>de.laures.cewolf.storage.FileStorage</param-value>
 *		</init-param>
 *		<init-param>
 *				<param-name>FileStorage.deleteOnExit</param-name>
 *				<param-value>true</param-value>
 *		</init-param> 
 *	</pre> 
 * 
 * @author guido
 */
public class FileStorage implements Storage {

	static final long serialVersionUID = -6342203760851077577L;

	String basePath = null;
	List stored = new ArrayList();
	private boolean deleteOnExit = false;

	/**
	 * @see de.laures.cewolf.Storage#storeChartImage(ChartImage, PageContext)
	 */
	public String storeChartImage(ChartImage cid, PageContext pageContext) {
		if(contains(cid, pageContext)){
			return getKey(cid);
		}
		String id = getKey(cid);
		ObjectOutputStream oos = null;
		try {
			String fileName = getFileName(id);
			pageContext.getServletContext().log("Storing image to file " + fileName);
			File f = new File(fileName);
			if (deleteOnExit) {
				f.deleteOnExit();
			}
			oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(new SerializableChartImage(cid));
			oos.close();
		} catch(IOException ioex){
			ioex.printStackTrace();
		} catch(CewolfException cwex){
			cwex.printStackTrace();
		} finally {
			if(oos != null){
				try {
					oos.close();
				} catch(IOException ioex){
					ioex.printStackTrace();
				}
			}
		}
		return id;
	}

	/**
	 * @see de.laures.cewolf.Storage#getChartImage(String, HttpServletRequest)
	 */
	public ChartImage getChartImage (String id, HttpServletRequest request) {
		ChartImage res = null;
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(getFileName(id)));
			res = (ChartImage) ois.readObject();
			ois.close();
		} catch (Exception ex){
			ex.printStackTrace();
		} finally {
			if (ois != null){
				try {
					ois.close();
				} catch (IOException ioex){
					ioex.printStackTrace();
				}
			}
		}
		return res;
	}

	/**
	 * see de.laures.cewolf.Storage#contains(ChartImage, PageContext)
	 */
	public boolean contains(ChartImage chartImage, PageContext pageContext) {
		return new File(getFileName(chartImage)).exists();
	}

	/**
	 * see de.laures.cewolf.Storage#getKey(ChartImage)
	 */
	public String getKey(ChartImage chartImage) {
		return String.valueOf(KeyGenerator.generateKey((Serializable)chartImage));
	}

	/**
	 * @see de.laures.cewolf.Storage#init(ServletContext)
	 */
	public void init(ServletContext servletContext) throws CewolfException {
		basePath = servletContext.getRealPath("/");
		Configuration config = Configuration.getInstance(servletContext);
		deleteOnExit = "true".equalsIgnoreCase("" + config.getParameters().get("FileStorage.deleteOnExit"));
		servletContext.log("FileStorage initialized, deleteOnExit=" + deleteOnExit);
	}

	private String getFileName(ChartImage chartImage){
		return getFileName(getKey(chartImage));
	}

	private String getFileName(String id){
		return basePath + "_chart" + id;
	}

	/**
	 * @see de.laures.cewolf.Storage#removeChartImage(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	public String removeChartImage(String imgKey, HttpServletRequest pageContext) throws CewolfException {
		File file = new File(getFileName(imgKey));
		if (file.exists())
		{
			if (!file.delete())
			{
				throw new CewolfException("Could not delete file " + file.getAbsolutePath());
			}
		}
		return imgKey;
	}

}
