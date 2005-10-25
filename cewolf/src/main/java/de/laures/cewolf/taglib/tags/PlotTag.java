/* ================================================================
 * Cewolf : Chart enabling Web Objects Framework
 * ================================================================
 *
 * Project Info:  http://cewolf.sourceforge.net
 * Project Lead:  Guido Laures (guido@laures.de);
 *
 * (C) Copyright 2002, by Guido Laures, and contributers
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

import java.util.Map;

import javax.servlet.jsp.JspException;

import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.taglib.DataAware;
import de.laures.cewolf.taglib.PlotContainer;
import de.laures.cewolf.taglib.PlotDefinition;
import de.laures.cewolf.taglib.TaglibConstants;

/** 
 * Tag &lt;sub-plot&gt; which defines a plot to overlay 
 * @author Chris McCann
 */
public class PlotTag extends CewolfTag implements TaglibConstants, DataAware {

    private PlotDefinition plotDefinition = new PlotDefinition();

    public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }
    
    public int doEndTag() throws JspException {
        PlotContainer pc = (PlotContainer)findAncestorWithClass(this, PlotContainer.class);
        if (pc == null) {
            throw new JspException("&lt;plot&gt; must be nested in a PlotContainer tag like &lt;overlaid-chart&gt;");
        }
        pc.addPlot(plotDefinition);
        return doAfterEndTag(EVAL_PAGE);
    }

    public void reset() {
        plotDefinition = new PlotDefinition();
    }

    /**
     * Setter for property type.
     * @param type New value of property type.
     */
    public void setType(String type) {
        plotDefinition.setType(type);
    }

    public void setDataProductionConfig(DatasetProducer dsp, Map params, boolean useCache) {
        plotDefinition.setDataProductionConfig(dsp, params, useCache);
    }
    
    /**
     * Setter for property xAxisLabel. [tb]
     * @param xAxisLabel New value of property xAxisLabel.
     */
    public void setXaxislabel(String xAxisLabel) {
        plotDefinition.setXaxislabel(xAxisLabel);
    }

    /**
     * Setter for property yAxisLabel. [tb]
     * @param yAxisLabel New value of property xAxisLabel.
     */
    public void setYaxislabel(String yAxisLabel) {
        plotDefinition.setYaxislabel(yAxisLabel);
    }


}
