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

package de.laures.cewolf.tooltips;

import org.jfree.data.general.PieDataset;

/**
 * @author glaures
 */
public interface PieToolTipGenerator extends ToolTipGenerator {

	/**
	 * Generates a tool tip text item for a particular category in the dataset.
	 * 
	 * @param data  the dataset.
	 * @param key  the item key.
	 * @param pieIndex  the pie index (differentiates between pies in a 'multi' pie chart).
	 *
	 * @return The tool tip text.
	 */
	String generateToolTip(PieDataset data, Comparable key, int pieIndex);
}
