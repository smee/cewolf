/* ================================================================
 * Cewolf : Chart enabling Web Objects Framework
 * ================================================================
 *
 * Project Info:  http://cewolf.sourceforge.net
 * Project Lead:  Guido Laures (guido@laures.de);
 *
 * (C) Copyright 2002, by Guido Laures and contributers
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

package de.laures.cewolf.taglib;

/**
 * Contains some base constants to avoid explicit dependancy to concrete Plot 
 * implementation's constant values. The constants of this class also serve as
 * the base contract for data exchange between sub packages.
 * @author  Chris McCann
 */
public interface PlotConstants {
    int XY_AREA = 0;
    int XY_LINE = 1;
    int XY_SHAPES_AND_LINES = 2;
    int SCATTER = 3;
    int XY_VERTICAL_BAR = 4;
    int STEP = 5;
    int CANDLESTICK = 6;
    int HIGH_LOW = 7;
    int VERTICAL_BAR = 8;
    int AREA = 9;
    int LINE = 10;
    int SHAPES_AND_LINES = 11;
    int SPLINE = 12;
    int SPIDERWEB = 13;
	int STACKED_XY_AREA = 14;
	int STACKED_XY_AREA2 = 15;
}
