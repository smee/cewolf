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

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
 * Some simple image rendering helper methods.
 * @author  Guido Laures 
 */
public class ImageHelper {

    private static final Component comp = new Component() { };
    private static final MediaTracker tracker = new MediaTracker(comp);
    private static final Log log = LogFactory.getLog(ImageHelper.class);

    /** Creates a new instance of ImageHelper */
    private ImageHelper() {
    }

    public static final Image loadImage(String fileName) {
        final Image image = java.awt.Toolkit.getDefaultToolkit().getImage(fileName);
        synchronized(tracker) {
            tracker.addImage(image, 0);
            try {
                tracker.waitForID(0, 0);
            } catch (InterruptedException e) {
                log.debug("INTERRUPTED while loading Image");
            }
            tracker.removeImage(image, 0);
        }
        return image;
    }

    public static BufferedImage loadBufferedImage(String fileName) {
        Image image = loadImage(fileName);
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }
        /*        final boolean hasAlpha = hasAlpha(image);
       int transparency = Transparency.OPAQUE;
        if (hasAlpha) {
            transparency = Transparency.BITMASK;
        }*/
        int width = (int)Math.max(1.0, image.getWidth(null));
        int height = (int)Math.max(1.0, image.getHeight(null));
        // BufferedImage bimage = GRAPHICS_CONV.createCompatibleImage(width, height, transparency);
        BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics g = bimage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }

    public static boolean hasAlpha(Image image) {
        if (image instanceof BufferedImage) {
            return ((BufferedImage)image).getColorModel().hasAlpha();
        }
        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ColorModel cm = pg.getColorModel();
        if(cm == null){
        	return false;
        }
        return cm.hasAlpha();
    }

    public static BufferedImage createImage(int width, int height) {
        // return GRAPHICS_CONV.createCompatibleImage(width, height);
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

}
