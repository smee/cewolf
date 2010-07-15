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

package de.laures.cewolf.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.StringTokenizer;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * Some methods to render messages or exceptions into an image.
 * @author glaures
 */
public class RenderingHelper {
	
	private final static int PADDING_X = 5;
	
	public static String renderMessage(String msg, int width, int height, OutputStream out) throws IOException {
		BufferedImage image = ImageHelper.createImage(width, height);
		Graphics gr = image.getGraphics();
		gr.setColor(Color.white);
		gr.fillRect(0, 0, width, height);
		gr.setColor(Color.black);
		gr.drawString(msg, PADDING_X, height/2 - 7);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(image);
		param.setQuality(1.0f, true);
		encoder.encode(image, param);
		return "image/jpeg";
	}
    
	public static String renderException(Throwable ex, int width, int height, OutputStream out) throws IOException {
		BufferedImage image = ImageHelper.createImage(width, height);
		Graphics gr = image.getGraphics();
		gr.setColor(Color.white);
		gr.fillRect(0, 0, width, height);
		gr.setColor(Color.red);
		gr.drawString(ex.getClass().getName() + " raised:", PADDING_X, 15);
		gr.drawString(String.valueOf(ex.getMessage()), PADDING_X, 30);
		gr.setColor(Color.black);
		Font stFont = gr.getFont().deriveFont(9f);
		gr.setFont(stFont);
		drawStackTrace(gr, PADDING_X, 50, ex);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(image);
		param.setQuality(1.0f, true);
		encoder.encode(image, param);
		return "image/jpeg";
	}
    
    private static void drawStackTrace(Graphics gr, int x, int y, Throwable ex) {
        final int linePadding = 4;
        int lineHeight = gr.getFont().getSize() + linePadding;
        int currY = y;
        Enumeration lines = new StringTokenizer(getStackTrace(ex), "\n", false);
        while (lines.hasMoreElements()) {
            gr.drawString(((String)lines.nextElement()).trim(), x, currY);
            currY += lineHeight;
        }
    }
    
    private static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        pw.close();
        return sw.getBuffer().toString();
    }
    
}
