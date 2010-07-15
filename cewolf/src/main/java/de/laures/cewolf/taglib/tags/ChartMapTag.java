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

import java.awt.Shape;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

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

	// constants for the HTML attributes and JavaScript methods
	private static final String MAP_TAGNAME = "map";
	private static final String AREA_TAGNAME = "area";
	private static final String COORD_ATTRIBUTE = "coords";
	private static final String SHAPE_ATTRIBUTE = "shape";
	private static final String NAME_ATTRIBUTE = "name";
	private static final String ID_ATTRIBUTE = "id";
	private static final String TARGET_ATTRIBUTE = "target";
	private static final String HREF_ATTRIBUTE = "href";
	private static final String MOUSEOVER_METHOD = "onmouseover";
	private static final String MOUSEOUT_METHOD = "onmouseout";
	private static final String ALT_ATTRIBUTE = "alt";
	private static final String TITLE_ATTRIBUTE = "title";

	ToolTipGenerator toolTipGenerator = null;
	LinkGenerator linkGenerator = null;
	String target = null;

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
			String mapTagContent = "<" + MAP_TAGNAME; 
			mapTagContent += " " + NAME_ATTRIBUTE + "=\"" + chartId + "\"";
			mapTagContent += " " + ID_ATTRIBUTE + "=\"" + chartId + "\"";
			mapTagContent += " >";
			out.write(mapTagContent);
			ChartRenderingInfo info = (ChartRenderingInfo) root.getRenderingInfo();
			Iterator entities = info.getEntityCollection().iterator();
			boolean altAttrbInserted, hasContent;
			StringBuffer sb = new StringBuffer(200);
			while (entities.hasNext()) {
				altAttrbInserted = false;
				hasContent = false;
				ChartEntity ce = (ChartEntity) entities.next();
				sb.append("\n<" + AREA_TAGNAME + " " + SHAPE_ATTRIBUTE + "=\"" + ce.getShapeType() + "\" ");
				sb.append(COORD_ATTRIBUTE + "=\"" + ce.getShapeCoords() + "\" ");
		        if (ce instanceof XYItemEntity) {
					dataset = ((XYItemEntity) ce).getDataset();
				}
				if (! (ce instanceof LegendItemEntity)) {
					if (hasToolTips()) {
						if (writeOutToolTip(dataset, sb, isIE, ce))
							hasContent = true;
						altAttrbInserted = true;
					} 
					if (hasLinks()) {
						if (writeOutLink(linkGenerator, dataset, sb, ce))
							hasContent = true;
					}
				}
				if (!altAttrbInserted) {
					sb.append(" " + ALT_ATTRIBUTE + "=\"\"");
				}
				sb.append(" />");
				if (hasContent) {
					out.write(sb.toString());
				}
				sb.setLength(0);
			}
		} catch (IOException ioex) {
			System.err.println("ChartMapTag.doStartTag: "+ioex.getMessage());
			throw new JspException(ioex.getMessage());
		} catch (CewolfException cwex) {
			System.err.println("ChartMapTag.doStartTag: "+cwex.getMessage());
			throw new JspException(cwex.getMessage());
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		// print out image map end
		Writer out = pageContext.getOut();
		try {
			out.write("\n</" + MAP_TAGNAME + ">");
		} catch (IOException ioex) {
			System.err.println("ChartMapTag.doEndTag: "+ioex.getMessage());
			throw new JspException(ioex.getMessage());
		}
		return doAfterEndTag(EVAL_PAGE);
	}

	public void reset() {
		this.toolTipGenerator = null;
		this.linkGenerator = null;
	}

	private boolean writeOutLink (Object linkGen, Dataset dataset, StringBuffer sb, ChartEntity ce) throws IOException {
		final String link = generateLink(dataset, ce);

		if (null != link) {
			final String href = ((HttpServletResponse) pageContext.getResponse()).encodeURL(link);
			sb.append(HREF_ATTRIBUTE + "=\"" + href + "\"");
			if (target != null) {
				sb.append(" " + TARGET_ATTRIBUTE + "=\"" + target + "\"");
			}
			return true;
		}

		return false;
	}

	private boolean writeOutToolTip (Dataset dataset, StringBuffer sb, final boolean isIE, ChartEntity ce) throws IOException, JspException {
		String toolTip = generateToolTip(dataset, ce);
		boolean altAttributeInserted = false;
		boolean hasContent = false;
		if (null != toolTip) {
			hasContent = true;
			if (!isIE) {
				sb.append(" " +  MOUSEOVER_METHOD + "=\"return overlib('"
						+ toolTip + "', width, '20');\" " + MOUSEOUT_METHOD + "=\"return nd();\" ");
			} else {
				sb.append(ALT_ATTRIBUTE + "=\"" + toolTip + "\" ");
				// Added "title" attribute for IE8 and newer
				sb.append(TITLE_ATTRIBUTE + "=\"" + toolTip + "\" ");
				altAttributeInserted = true;
			}
		} 
		if (!altAttributeInserted) {
			sb.append(" " + ALT_ATTRIBUTE + "=\"\" ");
		}
		return hasContent;
	}

	public void enableToolTips (Writer out, final boolean isIE) throws IOException {
		if (!PageUtils.isToolTipsEnabled(pageContext) && !isIE) {
			Configuration config = Configuration.getInstance(pageContext.getServletContext());
			String overLibURL = ChartImgTag.fixAbsolutURL(config.getOverlibURL(), pageContext);
			out.write("<script type=\"text/javascript\" language=\"JavaScript\" src=\"");
			out.write(overLibURL + "\"></script>\n");
			out.write("<div id=\"overDiv\" style=\"position:absolute; visibility:hidden; z-index:1000;\"></div>\n");
			PageUtils.setToolTipsEnabled(pageContext);
		}
	}

	private String generateLink (Dataset dataset, ChartEntity ce) {
		String link = null;
		if (useJFreeChartLinkGenerator) {
			link = ce.getURLText();
		} else if (linkGenerator instanceof CategoryItemLinkGenerator || linkGenerator instanceof XYItemLinkGenerator || linkGenerator instanceof PieSectionLinkGenerator) {
    		if (linkGenerator instanceof CategoryItemLinkGenerator) {
    			if (ce instanceof CategoryItemEntity) {
    				CategoryItemEntity catEnt = (CategoryItemEntity) ce;
    				link = ((CategoryItemLinkGenerator) linkGenerator).generateLink(dataset, catEnt.getSeries(), catEnt.getCategory());
    			}
    		}

    		if (linkGenerator instanceof XYItemLinkGenerator) {
				if (ce instanceof XYItemEntity) {
    				XYItemEntity xyEnt = (XYItemEntity) ce;
    				link = ((XYItemLinkGenerator) linkGenerator).generateLink(dataset, xyEnt.getSeriesIndex(), xyEnt.getItem());
				} else {
					// Note; there is a simple ChartEntity also passed since Jfreechart 1.0rc1, that is ignored
					// System.out.println("ChartMapTag.generateLink: Link entity skipped, not XYItemEntity.class:" + ce);
				}
    		}

    		if (linkGenerator instanceof PieSectionLinkGenerator) {
				if (ce instanceof PieSectionEntity) {
					PieSectionEntity pieEnt = (PieSectionEntity) ce;
					link = ((PieSectionLinkGenerator) linkGenerator).generateLink(dataset, pieEnt.getSectionKey());
				}
    		}
		}
		return link;
	}

	private String generateToolTip(Dataset dataset, ChartEntity ce) throws JspException {
		String tooltip = null;
		if (useJFreeChartTooltipGenerator) {
			tooltip = ce.getToolTipText();
		} else if (toolTipGenerator instanceof CategoryToolTipGenerator || toolTipGenerator instanceof XYToolTipGenerator || toolTipGenerator instanceof PieToolTipGenerator) {
		  if (toolTipGenerator instanceof CategoryToolTipGenerator) {
		    if (ce instanceof CategoryItemEntity) {
				CategoryItemEntity catEnt = (CategoryItemEntity) ce;
				tooltip = ((CategoryToolTipGenerator) toolTipGenerator).generateToolTip((CategoryDataset) dataset, catEnt.getSeries(), catEnt.getCategoryIndex());
		    }
		  }
		    
		  if (toolTipGenerator instanceof XYToolTipGenerator) {
		    if (ce instanceof XYItemEntity) {
				XYItemEntity xyEnt = (XYItemEntity) ce;
				tooltip = ((XYToolTipGenerator) toolTipGenerator).generateToolTip((XYDataset) dataset, xyEnt.getSeriesIndex(), xyEnt.getItem());
		    }
		  }

		  if (toolTipGenerator instanceof PieToolTipGenerator) {
		    if (ce instanceof PieSectionEntity) {
				PieSectionEntity pieEnt = (PieSectionEntity) ce;
				PieDataset ds = (PieDataset) dataset;
				final int index = pieEnt.getSectionIndex();
				tooltip = ((PieToolTipGenerator) toolTipGenerator).generateToolTip(ds, ds.getKey(index), index);
		    }
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

	public void setTarget (String target) {
		this.target = target;
	}
}
