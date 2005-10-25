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

import java.awt.Paint;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import de.laures.cewolf.ChartPostProcessor;
import de.laures.cewolf.taglib.AbstractChartDefinition;
import de.laures.cewolf.taglib.TaglibConstants;

/**
 * Root tag &lt;chart&gt; of a chart definition. Defines all values for the
 * page scope variable of type ChartDefinition which is used by the img
 * tag to render the appropriate chart.
 * @author  Guido Laures
 */
public abstract class AbstractChartTag extends CewolfTag implements CewolfRootTag, TaglibConstants, Painted {
    
    protected AbstractChartDefinition chartDefinition = createChartDefinition();
    
    protected abstract AbstractChartDefinition createChartDefinition();
    
    public int doStartTag(){
        return EVAL_BODY_INCLUDE;
    }
    
    public int doEndTag() throws JspException {
        pageContext.setAttribute(getId(), chartDefinition, PageContext.PAGE_SCOPE);
        return doAfterEndTag(EVAL_PAGE);
    }
    
    
    public void reset() {
    	chartDefinition = createChartDefinition();
    }
    
    public String getChartId() {
        return getId();
    }
    
    /**
     * Setter for property title.
     * @param title New value of property title.
     */
    public void setTitle(String title) {
        chartDefinition.setTitle(title);
    }
    
    /**
     * Setter for property xAxisLabel.
     * @param xAxisLabel New value of property xAxisLabel.
     */
    public void setXaxislabel(String xAxisLabel) {
        chartDefinition.setXAxisLabel(xAxisLabel);
    }
    
    /**
     * Setter for property xAxisLabel.
     * @param xAxisLabel New value of property xAxisLabel.
     */
    public void setYaxislabel(String yAxisLabel) {
        chartDefinition.setYAxisLabel(yAxisLabel);
    }
    
    public void setBackground(String src) {
        String srcFile = pageContext.getServletContext().getRealPath(src);
        chartDefinition.setBackground(srcFile);
    }
    
    public void setBackgroundimagealpha(Float alpha) {
        chartDefinition.setBackgroundImageAlpha(alpha.floatValue());
    }
    
    public void setAntialias(boolean anti) {
        chartDefinition.setAntialias(anti);
    }
    
    /**
     * Setter for property legend.
     * @param legend New value of property legend.
     */
    public void setShowlegend(boolean legend) {
        chartDefinition.setShowLegend(legend);
    }
    
    /**
     * Setter for property legend.
     * @param legend New value of property legend.
     */
    public void setLegendanchor(String anchor) {
        if ("north".equalsIgnoreCase(anchor)) {
            chartDefinition.setLegendAnchor(ANCHOR_NORTH);
        } else if ("south".equalsIgnoreCase(anchor)) {
            chartDefinition.setLegendAnchor(ANCHOR_SOUTH);
        } else if ("west".equalsIgnoreCase(anchor)) {
            chartDefinition.setLegendAnchor(ANCHOR_WEST);
        } else if ("east".equalsIgnoreCase(anchor)) {
            chartDefinition.setLegendAnchor(ANCHOR_EAST);
        }
    }
    
    public void addChartPostProcessor(ChartPostProcessor pp, Map params) {
        chartDefinition.addPostProcessor(pp);
        chartDefinition.addPostProcessorParams(params);
    }
    
    public void setPaint(Paint paint){
        chartDefinition.setPaint(paint);
    }

    /**
         * Setter for property type.
         * @param type New value of property type.
         */
    public void setType(String type) {
        chartDefinition.setType(type);
    }
    
}
