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

package de.laures.cewolf.taglib.tags;

import javax.servlet.jsp.JspException;

import de.laures.cewolf.ChartPostProcessor;
import de.laures.cewolf.taglib.util.PageUtils;

/** 
 * Tag to define a post processor.
 * @see ChartPostProcessor
 * @author  Guido Laures 
 */
public class ChartPostProcessorTag extends AbstractParameterizedObjectTag {

    public int doEndTag() throws JspException {
        ChartPostProcessor pp = null;
        try {
            pp = (ChartPostProcessor)getObject();
            if (pp == null) {
                throw new JspException("Could not find ChartEnhanncer under ID '" + getId() + "'");
            }
        } catch (ClassCastException cce) {
            throw new JspException("Bean under ID '" + getId() + "' is of type '"
            + pp.getClass().getName() +
            "'.\nType expected:" + ChartPostProcessor.class.getName());
        }
        AbstractChartTag rt = (AbstractChartTag)PageUtils.findRoot(this, pageContext);
        rt.addChartPostProcessor((ChartPostProcessor)getObject(), getParameters());
        return doAfterEndTag(EVAL_PAGE);
    }

}
