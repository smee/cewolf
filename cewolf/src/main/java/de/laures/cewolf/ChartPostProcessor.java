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

package de.laures.cewolf;

import java.io.Serializable;
import java.util.Map;

import org.jfree.chart.JFreeChart;

/**
 * Postprocesses a generated chart instance. Objects of this class can be used to
 * customize a generated and afterwards rendered chart instance additionally.
 * To provide a postprocessor the &lt;chartpostprocessor&gt; tag is used. 
 * There can be an unlimited number of postprocessors registered.
 * <P>
 * Change by U. Dittmer: ChartPostProcessor implementations need to be serializable now.
 * Amongst other consequences, that means they can no longer be declared in JSP pages.
 * Keeping code in JSPs should be avoided anyway, but some cewolf demos did this.
 *
 * @see de.laures.cewolf.taglib.tags.ChartPostProcessorTag
 */
public interface ChartPostProcessor {

    /**
     * Processes a generated chart. This method is called by the ChartProducer
     * after a chart instance is generated and before it is rendered (if so).
     * The ChartProducer is responsible for post processing a chart dependant
     * on and only on the provided parameters. This means that the same parameters
     * should result in the same postprocessings.
     * @param chart the chart instance; only JFreeChart instances are supported
     * @param params parameters passed to the postprocessor. These are defined in the JSP
     * @see de.laures.cewolf.taglib.tags.ChartPostProcessorTag
     */
    void processChart (Object chart, Map params);
}
