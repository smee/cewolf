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

package de.laures.cewolf.taglib.util;

import java.awt.Color;


/**
 * Helper class to calculate colors
 *  @author  Guido Laures 
 */
public class ColorHelper {

    private ColorHelper() {
    }

    public static final Color getColor(String hexString) {
        try {
             if (hexString == null || hexString.length() < 7) {
                return Color.black;
            }
            final int red = Integer.parseInt(hexString.substring(1, 3), 16);
            final int green = Integer.parseInt(hexString.substring(3, 5), 16);
            final int blue = Integer.parseInt(hexString.substring(5, 7), 16);
            int alpha = 0;
            if (hexString.length() > 8) {
                alpha = Integer.parseInt(hexString.substring(7, 9), 16);
            }
            if (alpha > 0) {
                return new Color(red, green, blue, alpha);
            } else {
                return new Color(red, green, blue);
            }
        } catch (NumberFormatException nfe) {
            return Color.black;
        }
    }

}
