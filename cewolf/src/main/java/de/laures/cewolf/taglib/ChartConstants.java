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

package de.laures.cewolf.taglib;

/**
 * Contains some base constants to avoid explicit dependancy to concrete chart 
 * implementation's constant values. The constants of this class also serve as
 * the base contract for data exchange between sub packages.
 * @author  Guido Laures
 */
public interface ChartConstants {

    int AREA = 0;
    int AREA_XY = 1;
    int HORIZONTAL_BAR = 2;
    int HORIZONTAL_BAR_3D = 3;
    int LINE = 4;
    int PIE = 5;
    int SCATTER = 6;
    int STACKED_HORIZONTAL_BAR = 7;
    int STACKED_VERTICAL_BAR = 8;
    int STACKED_VERTICAL_BAR_3D = 9;
    int TIME_SERIES = 10;
    int VERTICAL_BAR = 11;
    int VERTICAL_BAR_3D = 12;
    int XY = 13;
    int CANDLE_STICK = 14;
    int HIGH_LOW = 15;
    int GANTT = 16;
    int WIND = 17;
    int SIGNAL = 18;
    int VERRTICAL_XY_BAR = 19;
    int PIE_3D = 20;
    int OVERLAY_XY = 21;
    int OVERLAY_CATEGORY = 22;
    int COMBINED_XY = 23;
    int METER = 24;
    int STACKED_AREA = 25;
    int BUBBLE = 26;
}
