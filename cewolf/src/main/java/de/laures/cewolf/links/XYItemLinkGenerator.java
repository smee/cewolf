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

package de.laures.cewolf.links;

/**
 * A LinkGenerator for XYDatasets.
 * @author  Guido Laures
 * @since 0.8
 */
public interface XYItemLinkGenerator extends LinkGenerator {

    /**
     * Generate a link for a XYDataset, series and item.
     * @param data the dataset. This will normally be of a subtype of XYDataset. It is
     * genralized to avoid dependencies to the concrete chart implementation
     * @param series the series to produce a link for
     * @param item the item to produce the link for
     * @return the URL of the link belonging to the series/item
     */
    String generateLink (Object data, int series, int item);

}
