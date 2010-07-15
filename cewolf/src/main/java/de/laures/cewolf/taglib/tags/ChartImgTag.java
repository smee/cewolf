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
 * either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 */

package de.laures.cewolf.taglib.tags;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

import org.jfree.chart.ChartRenderingInfo;

import de.laures.cewolf.CewolfException;
import de.laures.cewolf.ChartHolder;
import de.laures.cewolf.ChartImage;
import de.laures.cewolf.Configuration;
import de.laures.cewolf.Storage;
import de.laures.cewolf.WebConstants;
import de.laures.cewolf.taglib.ChartImageDefinition;
import de.laures.cewolf.taglib.TaglibConstants;
import de.laures.cewolf.taglib.html.HTMLImgTag;
import de.laures.cewolf.taglib.util.MIMEExtensionHelper;
import de.laures.cewolf.taglib.util.PageUtils;

/**
 * This is the tag implementation of the &lt;img&gt; tag. This tag inputs the
 * proper &lt;img&gt; tag into the HTML page delivered to the client. It therefor
 * determines the chart ID which will be used by the rendering servlet to retrieve the chart.
 * 
 * @author glaures
 * @see de.laures.cewolf.ChartImage
 */

public class ChartImgTag extends HTMLImgTag implements CewolfRootTag, Mapped
{
	static final long serialVersionUID = 6176973400519372326L;

	private static final String  DEFAULT_MIME_TYPE = WebConstants.MIME_PNG;
	private static final String  TAG_NAME_SVG      = "EMBED";
	private static final int     DEFAULT_TIMEOUT   = 300;

	private String               chartId           = null;
	private String               renderer;
	private String               mimeType          = DEFAULT_MIME_TYPE;
	private int                  timeout           = DEFAULT_TIMEOUT;
	protected String             sessionKey        = null;

	private ChartImageDefinition chartImageDefinition;

  public int doStartTag() throws JspException
  {
    final ChartHolder chartHolder = PageUtils.getChartHolder(chartId, pageContext);
    this.chartImageDefinition = new ChartImageDefinition(chartHolder, width, height, ChartImage.IMG_TYPE_CHART, mimeType, timeout);
    Storage storage = Configuration.getInstance(pageContext.getServletContext()).getStorage();
    try
    {
      this.sessionKey = storage.storeChartImage(chartImageDefinition, pageContext);
    }
    catch (CewolfException cwex)
    {
      throw new JspException(cwex.getMessage());
    }
    return EVAL_PAGE;
  }

  public int doAfterBody() throws JspException
  {
    try
    {
      // double checking for null as Resin had problems with that
      final BodyContent body = getBodyContent();
      if (body != null)
      {
        final JspWriter writer = getPreviousOut();
        if (writer != null)
        {
          body.writeOut(writer);
        }
      }

    }
    catch (IOException ioex)
    {
		System.err.println("ChartImgTag.doAfterBody: "+ioex.getMessage());
      throw new JspException(ioex.getMessage());
    }
    return SKIP_BODY;
  }

  public int doEndTag() throws JspException
  {
    super.doStartTag();
    final StringBuffer buffer = new StringBuffer(" src=\"");
    buffer.append(getImgURL());
    buffer.append("\"");
    try
    {
      pageContext.getOut().write(buffer.toString());
    }
    catch (IOException ioex)
    {
      reset();
		System.err.println("ChartImgTag.doEndTag: "+ioex.getMessage());
      throw new JspException(ioex.getMessage());
    }
    return super.doEndTag();
  }

  /**
   * Fix an absolute url given as attribute by adding the full application url path to it.
   * It is considered absolute url (not relative) when it starts with "/"
   * @param url The url to fix
   * @param request The http request
   * @return Fixed url contains the full path
   */
  public static String fixAbsolutURL(String url, HttpServletRequest request) {
    if (url.startsWith("/"))
    {
      //final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
      final String context = request.getContextPath();
      url = context + url;
    }
    return url;
  }
  
  /**
   * Same as the other fixAbsolutURL, convinience only.
   * @param url The url to fix
   * @param pageContext The page context
   * @return Fixed url contains the full path
   */
  public static String fixAbsolutURL(String url, PageContext pageContext) {
  	return fixAbsolutURL(url, (HttpServletRequest) pageContext.getRequest());
  }

  /**
   * Build the image url
   * @param renderer the url of the renderer
   * @param pageContext Page context
   * @param sessionKey The session key for the image stored.
   * @param width The width 
   * @param height The height
   * @param mimeType the mime-type (for example png) of it
   * @return The full url 
   */
  public static String buildImgURL (String renderer, PageContext pageContext, String sessionKey, int width,
									int height, String mimeType, boolean forceSessionId, boolean removeAfterRender) {
	renderer = fixAbsolutURL(renderer, pageContext);
	final HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
	StringBuffer url = new StringBuffer(response.encodeURL(renderer));
	if (url.toString().indexOf(WebConstants.SESSIONID_KEY) == -1)
	{
		if (forceSessionId)
		{
			  final String sessionId = pageContext.getSession().getId();
			  url.append(";" + WebConstants.SESSIONID_KEY + "=" + sessionId);			
		}
	}
	url.append("?" + WebConstants.IMG_PARAM + "=" + sessionKey);
	url.append(WebConstants.AMPERSAND + WebConstants.WIDTH_PARAM + "=" + width);
	url.append(WebConstants.AMPERSAND + WebConstants.HEIGHT_PARAM + "=" + height);
	if (removeAfterRender)
	{
		url.append(WebConstants.AMPERSAND + WebConstants.REMOVE_AFTER_RENDERING + "=true");		
	}
	url.append(WebConstants.AMPERSAND + "iehack=" + MIMEExtensionHelper.getExtensionForMimeType(mimeType));
	return url.toString();  	
  }
  
  /**
   * To enable further server side scriptings on JSP output the session ID is
   * always encoded into the image URL even if cookies are enabled on the client side.
   */
  protected String getImgURL()
  {
  	return buildImgURL(renderer, pageContext, sessionKey, width, height, mimeType, forceSessionId, removeAfterRender);
  }

  public ChartRenderingInfo getRenderingInfo() throws CewolfException
  {
    return chartImageDefinition.getRenderingInfo();
  }

  protected String getMimeType()
  {
    return mimeType;
  }

  protected void reset()
  {
    this.mimeType = DEFAULT_MIME_TYPE;
    // as of a weird JSP compiler in resin a reused tag's attribute is only set if
    // it changes. So width and height may not be unset to ensure correct values.
    int lHeight = this.height;
    int lWidth = this.width;
    int lTimeout = this.timeout;
    super.reset();
    this.height = lHeight;
    this.width = lWidth;
    this.timeout = lTimeout;
  }

  public void enableMapping()
  {
    setUsemap("#" + chartId);
  }

  public String getChartId()
  {
    return getChartid();
  }

  public void setChartid (String id)
  {
    this.chartId = id;
  }

  public String getChartid()
  {
    return chartId;
  }

  public void setRenderer (String renderer)
  {
    this.renderer = renderer;
  }

  protected String getRenderer()
  {
    return this.renderer;
  }

  public int getWidth()
  {
    return this.width;
  }

  public int getHeight()
  {
    return height;
  }

  /**
   * Sets the mimeType.
   * 
   * @param mimeType The mimeType to set
   */
  public void setMime (String mimeType)
  {
    this.mimeType = mimeType;
  }

  /**
   * @see de.laures.cewolf.taglib.html.AbstractHTMLBaseTag#getTagName()
   */
  protected String getTagName()
  {
    if (WebConstants.MIME_SVG.equals(mimeType))
    {
      return TAG_NAME_SVG;
    }
    return super.getTagName();
  }

  /**
   * @see de.laures.cewolf.taglib.html.AbstractHTMLBaseTag#writeAttributes(Writer)
   */
  public void writeAttributes (Writer wr)
  {
    super.writeAttributes(wr);
    if (WebConstants.MIME_SVG.equals(mimeType))
    {
      try
      {
        appendAttributeDeclaration(wr, "http://www.adobe.com/svg/viewer/install/", "PLUGINSPAGE");
      }
      catch (IOException ioex)
      {
        ioex.printStackTrace();
      }
    }
  }

  /**
   * @return Returns the timeout.
   */
  public int getTimeout()
  {
    return timeout;
  }
  /**
   * @param timeout The timeout to set.
   */
  public void setTimeout (int timeout)
  {
    this.timeout = timeout;
  }
}
