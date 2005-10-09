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

import java.util.Arrays;
import java.util.List;

/**
 * Contains the list of all possible axis type string values which can be used
 * in the <code>type</code> attribute of a &lt;plot&gt; tag.
 * @author  Guido Laures
 */
public class AxisTypes {

    /** All type strings in an array */
    public static final String[] typeNames =    {
        "date",
        "number",
        "category",
    };

    /**
     * The whole typeNames array inside of a list.
     * @see #typeNames
     */
    public static final List typeList = Arrays.asList(typeNames);

    private AxisTypes() {
    }

}
