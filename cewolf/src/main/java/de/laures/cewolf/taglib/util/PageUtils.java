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

package de.laures.cewolf.taglib.util;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.jfree.data.general.Dataset;

import de.laures.cewolf.ChartHolder;
import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.taglib.tags.CewolfRootTag;

/**
 * Helper class to easily retrieve some objects of page context.
 * @author  glaures
 */
public class PageUtils {
        
    /** Creates a new instance of ChartDefinitionFactory */
    private PageUtils() {}
    
    public static ChartHolder getChartHolder(String chartId, PageContext ctx){
        return (ChartHolder)ctx.getAttribute(chartId, PageContext.PAGE_SCOPE);
    }
    
    public static final ChartHolder getChartHolder(Tag tag, PageContext ctx){
        CewolfRootTag root = findRoot(tag, ctx);
        if(root instanceof ChartHolder){
            return (ChartHolder)root;
        } else {
            return getChartHolder(root.getChartId(), ctx);
        }
    }
    
    public static final Dataset getDataset(String chartId, PageContext ctx) throws DatasetProduceException {
        return (Dataset) getChartHolder(chartId, ctx).getDataset();
    }

    public static final CewolfRootTag findRoot(Tag t, PageContext ctx){
        Tag res = t;
        while(!(res instanceof CewolfRootTag)){
            res = res.getParent();
        }
        return (CewolfRootTag)res;
    }
    
   
}
