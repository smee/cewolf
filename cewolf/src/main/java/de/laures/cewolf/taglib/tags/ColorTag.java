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
import java.io.Serializable;
import java.util.Map;

import org.jfree.chart.JFreeChart;

import de.laures.cewolf.ChartPostProcessor;
import de.laures.cewolf.NonSerializableChartPostProcessor;
import de.laures.cewolf.taglib.util.ColorHelper;

/** 
 * Tag &lt;color&gt; which sets the color of its parent tag.
 * This must implement the Colored interface
 * @see Colored
 * @author  Guido Laures 
 */
public class ColorTag extends CewolfBodyTag
		implements ChartPostProcessor, NonSerializableChartPostProcessor {

	static final long serialVersionUID = -4882939573657296673L;

	private SerializableColorTag serTag = new SerializableColorTag();

    public int doEndTag() {
        ((Painted)getParent()).setPaint(serTag.getColor());
        return doAfterEndTag(EVAL_PAGE);
    }
    
    protected void reset(){ }

    public void setColor(String s) {
		serTag.setColor(s);
    }

    protected Color getColor() {
		return serTag.getColor();
    }

	public void processChart (Object chart, Map args) {
		serTag.processChart((JFreeChart) chart, args);
	}

	public ChartPostProcessor getSerializablePostProcessor() {
		return serTag;
	}

	private class SerializableColorTag implements ChartPostProcessor, Serializable {

		static final long serialVersionUID = -7400971583172945660L;

		private Color color = Color.white;

		public void setColor (String s) {
			this.color = ColorHelper.getColor(s);
		}

		protected Color getColor() {
			return color;
		}

		public void processChart (Object chart, Map args) {
			((JFreeChart) chart).setBackgroundPaint(color);
		}
	}
}
