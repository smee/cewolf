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

package de.laures.cewolf.taglib.tags;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.io.Serializable;

/** 
 * Special gradient paint which can be serialized.
 * @see GradientPaint
 * @author  Guido Laures 
 */
public class SerializableGradientPaint implements Paint, Serializable {

    private int x1;
    private int y1;
    private Color c1 = Color.white;
    private int x2;
    private int y2;
    private Color c2 = Color.white;
    private boolean cyclic = false;
    private transient Paint paint = null;

    public SerializableGradientPaint() {
    }

    /** Creates a new instance of SerializableGradientPaint */
    public SerializableGradientPaint(int x1, int y1, Color c1, int x2, int y2, Color c2) {
        this.x1 = x1;
        this.y1 = y1;
        this.c1 = c1;
        this.x2 = x2;
        this.y2 = y2;
        this.c2 = c2;
    }

    private Paint getPaint() {
        if (paint == null) {
            createPaint();
        }
        return paint;
    }

    private void createPaint() {
        paint = new GradientPaint(x1, y1, c1, x2, y2, c2, cyclic);
    }

    public void setPoint1(int x, int y, Color c) {
        this.x1 = x;
        this.y1 = y;
        this.c1 = c;
    }

    public void setPoint2(int x, int y, Color c) {
        this.x2 = x;
        this.y2 = y;
        this.c2 = c;
    }

    public java.awt.PaintContext createContext(ColorModel colorModel, Rectangle rectangle, Rectangle2D rectangle2D,
    AffineTransform affineTransform, RenderingHints renderingHints) {
        return getPaint().createContext(colorModel, rectangle, rectangle2D, affineTransform, renderingHints);
    }

    public int getTransparency() {
        return getPaint().getTransparency();
    }

    public void setCyclic(boolean b) {
        this.cyclic = b;
    }

    public String toString() {
        return "" + x1 + "," + y1 + "," + c1 + "," + x2 + "," + y2 + "," + c2;
    }
}
