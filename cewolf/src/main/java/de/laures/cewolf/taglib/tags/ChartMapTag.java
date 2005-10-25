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

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.LegendItemEntity;
import org.jfree.chart.entity.PieSectionEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;

import de.laures.cewolf.CewolfException;
import de.laures.cewolf.Configuration;
import de.laures.cewolf.links.CategoryItemLinkGenerator;
import de.laures.cewolf.links.LinkGenerator;
import de.laures.cewolf.links.PieSectionLinkGenerator;
import de.laures.cewolf.links.XYItemLinkGenerator;
import de.laures.cewolf.taglib.util.BrowserDetection;
import de.laures.cewolf.taglib.util.PageUtils;
import de.laures.cewolf.tooltips.CategoryToolTipGenerator;
import de.laures.cewolf.tooltips.PieToolTipGenerator;
import de.laures.cewolf.tooltips.ToolTipGenerator;
import de.laures.cewolf.tooltips.XYToolTipGenerator;

/**
 * Tag &lt;map&gt; which defines the tooltip and link tags.
 * @see DataTag
 * @author  Guido Laures
 */
public class ChartMapTag extends CewolfTag {

	private static final long serialVersionUID = -3742340487378471159L;
	
	private static final Log LOG = LogFactory.getLog(ChartMapTag.class);
		
	ToolTipGenerator toolTipGenerator = null;
	LinkGenerator linkGenerator = null;
	
	// If the links provided by the JFreeChart renderer should be used.
	boolean useJFreeChartLinkGenerator = false;
	// If the tooltips provided by the JFreeChart renderer should be used.
	boolean useJFreeChartTooltipGenerator = false;

	public int doStartTag() throws JspException {
		// Object linkGenerator = getLinkGenerator();
		Mapped root = (Mapped) PageUtils.findRoot(this, pageContext);
		root.enableMapping();
		String chartId = ((CewolfRootTag) root).getChartId();
		try {
			Dataset dataset = PageUtils.getDataset(chartId, pageContext);
			Writer out = pageContext.getOut();
			final boolean isIE = BrowserDetection.isIE((HttpServletRequest) pageContext.getRequest());
			if (hasToolTips()) {
				enableToolTips(out, isIE);
			}
			out.write("<MAP name=\"" + chartId + "\">\n");
			ChartRenderingInfo info = (ChartRenderingInfo) root.getRenderingInfo();
			Iterator entities = info.getEntityCollection().iterator();
			while (entities.hasNext()) {
				ChartEntity ce = (ChartEntity) entities.next();
				out.write("\n<AREA shape=\"" + ce.getShapeType() + "\" ");
				out.write("COORDS=\"" + ce.getShapeCoords() + "\" ");
		        if (ce instanceof XYItemEntity)
		        {
		          dataset = ((XYItemEntity)ce).getDataset();
		        }
				if (!(ce instanceof LegendItemEntity)) {
					if (hasToolTips()) {
						writeOutToolTip(dataset, out, isIE, ce);
					}
					if (hasLinks()) {
						writeOutLink(linkGenerator, dataset, out, ce);
					}
				}
				out.write(">");
			}
		} catch (IOException ioex) {
			log.error(ioex);
			throw new JspException(ioex.getMessage());
		} catch (CewolfException cwex) {
			log.error(cwex);
			throw new JspException(cwex.getMessage());
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		// print out image map end
		Writer out = pageContext.getOut();
		try {
			out.write("</MAP>");
		} catch (IOException ioex) {
			log.error(ioex);
			throw new JspException(ioex.getMessage());
		}
		return doAfterEndTag(EVAL_PAGE);
	}

	public void reset() {
		this.toolTipGenerator = null;
		this.linkGenerator = null;
	}

	public void writeOutLink(Object linkGen, Dataset dataset, Writer out, ChartEntity ce) throws IOException {
		final String link = generateLink(dataset, ce);
		
		if (null != link) {
			final String href = ((HttpServletResponse) pageContext.getResponse()).encodeURL(link);
			out.write("HREF=\"" + href + "\"");
		}
	}

	private void writeOutToolTip(Dataset dataset, Writer out, final boolean isIE, ChartEntity ce) throws IOException, JspException {
		String toolTip = generateToolTip(dataset, ce);
		if (null != toolTip) {
			if (!isIE) {
				out.write("ONMOUSEOVER=\"return overlib('"
						+ toolTip + "', WIDTH, '20');\" ONMOUSEOUT=\"return nd();\" ");
			} else {
				out.write("ALT=\"" + toolTip + "\" ");
			}
		}
	}

	public void enableToolTips(Writer out, final boolean isIE) throws IOException {
		if (!PageUtils.isToolTipsEnabled(pageContext) && !isIE) {
			Configuration config = Configuration.getInstance(pageContext.getServletContext());
			String overLibURL = ChartImgTag.fixAbsolutURL(config.getOverlibURL(), pageContext);
			out.write("<script language=\"JavaScript\" src=\"");
			out.write(overLibURL + "\"><!-- overLIB (c) Erik Bosrup --></script>\n");
			out.write("<div id=\"overDiv\" style=\"position:absolute; visibility:hidden; z-index:1000;\"></div>\n");
			PageUtils.setToolTipsEnabled(pageContext);
		}
	}

	private String generateLink(Dataset dataset, ChartEntity ce) {
		String link = null;
		if (useJFreeChartLinkGenerator) {
			link = ce.getURLText();
		} else if (linkGenerator instanceof CategoryItemLinkGenerator) {
			if (ce instanceof CategoryItemEntity) {
				CategoryItemEntity catEnt = (CategoryItemEntity) ce;
				link = ((CategoryItemLinkGenerator) linkGenerator).generateLink(dataset, catEnt.getSeries(), catEnt.getCategory());
			}
		} else if (linkGenerator instanceof XYItemLinkGenerator) {
		    if (ce instanceof XYItemEntity) {
				XYItemEntity xyEnt = (XYItemEntity) ce;
				link = ((XYItemLinkGenerator) linkGenerator).generateLink(dataset, xyEnt.getSeriesIndex(), xyEnt.getItem());
		    } else {
		        // Note; there is a simple ChartEntity also passed since Jfreechart 1.0rc1, that is ignored
		        LOG.debug("Link entity skipped, not XYItemEntity.class:" + ce);
		    }
		} else if (linkGenerator instanceof PieSectionLinkGenerator) {
		    if (ce instanceof PieSectionEntity) {
				PieSectionEntity pieEnt = (PieSectionEntity) ce;
				link = ((PieSectionLinkGenerator) linkGenerator).generateLink(dataset, pieEnt.getSectionKey());
		    }
		}
		return link;
	}

	private String generateToolTip(Dataset dataset, ChartEntity ce) throws JspException {
		String tooltip = null;
		if (useJFreeChartTooltipGenerator) {
			tooltip = ce.getToolTipText();
		} else if (toolTipGenerator instanceof CategoryToolTipGenerator) {
		    if (ce instanceof CategoryItemEntity) {
				CategoryItemEntity catEnt = (CategoryItemEntity) ce;
				tooltip = ((CategoryToolTipGenerator) toolTipGenerator).generateToolTip((CategoryDataset) dataset, catEnt.getSeries(), catEnt.getCategoryIndex());
		    }
		} else if (toolTipGenerator instanceof XYToolTipGenerator) {
		    if (ce instanceof XYItemEntity) {
				XYItemEntity xyEnt = (XYItemEntity) ce;
				tooltip = ((XYToolTipGenerator) toolTipGenerator).generateToolTip((XYDataset) dataset, xyEnt.getSeriesIndex(), xyEnt.getItem());
		    }
		} else if (toolTipGenerator instanceof PieToolTipGenerator) {
		    if (ce instanceof PieSectionEntity) {
				PieSectionEntity pieEnt = (PieSectionEntity) ce;
				PieDataset ds = (PieDataset) dataset;
				final int index = pieEnt.getSectionIndex();
				tooltip = ((PieToolTipGenerator) toolTipGenerator).generateToolTip(ds, ds.getKey(index), index);
		    }
		} else {
			// throw because category is unknown
		    throw new JspException(
				"TooltipgGenerator of class " + toolTipGenerator.getClass().getName() + " does not implement the appropriate TooltipGenerator interface for entity type " + ce.getClass().getName());
		}
		return tooltip;
	}

	private boolean hasToolTips() throws JspException {
		if (toolTipGenerator!=null && useJFreeChartTooltipGenerator) {
			throw new JspException("Can't have both tooltipGenerator and useJFreeChartTooltipGenerator parameters specified!");
	}
		return toolTipGenerator != null || useJFreeChartTooltipGenerator;
	}

	public void setTooltipgeneratorid(String id) {
		this.toolTipGenerator = (ToolTipGenerator) pageContext.findAttribute(id);
	}

	private boolean hasLinks() throws JspException {
		if (linkGenerator!=null && useJFreeChartLinkGenerator) {
			throw new JspException("Can't have both linkGenerator and useJFreeChartLinkGenerator parameters specified!");
	}
		return linkGenerator != null || useJFreeChartLinkGenerator;
	}

	public void setLinkgeneratorid(String id) {
		this.linkGenerator = (LinkGenerator) pageContext.findAttribute(id);
	}
	
	/**
	 * Setter of the useJFreeChartLinkGenerator field.
	 * @param useJFreeChartLinkGenerator the useJFreeChartLinkGenerator to set.
	 */
	public void setUseJFreeChartLinkGenerator(boolean useJFreeChartLinkGenerator) {
		this.useJFreeChartLinkGenerator = useJFreeChartLinkGenerator;
	}
	/**
	 * Setter of the useJFreeChartTooltipGenerator field.
	 * @param useJFreeChartTooltipGenerator the useJFreeChartTooltipGenerator to set.
	 */
	public void setUseJFreeChartTooltipGenerator(boolean useJFreeChartTooltipGenerator) {
		this.useJFreeChartTooltipGenerator = useJFreeChartTooltipGenerator;
	}
	
}
