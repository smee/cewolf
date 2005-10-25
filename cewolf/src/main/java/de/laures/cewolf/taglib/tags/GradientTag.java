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

import java.awt.Color;


/** 
 * Tag &lt;gradient-paint&gt; which defines a paint of type gradient.
 * @author  Guido Laures 
 */
public class GradientTag extends CewolfTag implements Pointed {

    private SerializableGradientPaint gPaint = new SerializableGradientPaint();
    private int pointCount = 0;

    public int doStartTag() {
        return EVAL_PAGE;
    }

    public int doEndTag() {
        ((Painted)getParent()).setPaint(gPaint);
        return doAfterEndTag(EVAL_PAGE);
    }

    public void reset() {
        gPaint = new SerializableGradientPaint();
        pointCount = 0;
    }

    public void addPoint(int x, int y, Color c) {
        switch (pointCount) {
            case 0:
                gPaint.setPoint1(x, y, c);
                break;
            default:
                gPaint.setPoint2(x, y, c);
        }
        pointCount ++;
    }

    /**
     * Setter for property cyclic.
     * @param cyclic New value of property cyclic.
     */
    public void setCyclic(boolean cyclic) {
        gPaint.setCyclic(cyclic);
    }

}
