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

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import de.laures.cewolf.Configuration;
import de.laures.cewolf.taglib.tags.ChartImgTag;
import de.laures.cewolf.taglib.util.BrowserDetection;

/**
 * Interface for the custom tooltip renderer, which is able to generate any javascript or
 * other methods for showing the tooltip when the mouse is over a map element.
 * @author zluspai
 */
public interface ITooltipRenderer {
	
	/**
	 * Initiatlize the renderer, this will be called once per cewolf:map tag on a page. 
	 * @param request The request 
	 * @param out The output stream, it may html data onto.
	 * @param pageContext The current page context
	 * @throws IOException Any IOException
	 */
	public void init(HttpServletRequest request, Writer out, PageContext pageContext) throws IOException;
	
	/**
	 * Render one tooltip on the output stream.
	 * Note this renderer will only be called with not-null tooltips!
	 * 
	 * @param request The current request
	 * @param toolTip The tooltip text
	 * 
	 * @throws IOException any io exception
	 */
	public void render(Writer out, String toolTip) throws IOException;
	
	
	/*************************
	 * Default tooltip renderer implementations below...
	 **************************/
	
	/**
	 * A tooltip renderer, which will always use overlib javascript library.
	 * @author zluspai
	 */
	public static class Overlib implements ITooltipRenderer {
		// page scope attribute to avoid duplicate initalization of overlib library
		private final static String TOOLTIPS_ENABLED_ATTR = Overlib.class.getName() + ".ttenabled";
		
		private final void setToolTipsEnabled(PageContext ctx) {
			if (!isToolTipsEnabled(ctx)) {
				ctx.setAttribute(TOOLTIPS_ENABLED_ATTR, "true",PageContext.PAGE_SCOPE);
			}
		}

		private final boolean isToolTipsEnabled(PageContext ctx) {
			return ctx.getAttribute(TOOLTIPS_ENABLED_ATTR, PageContext.PAGE_SCOPE) != null;
		}
		    
		public void init(HttpServletRequest request, Writer out, PageContext pageContext) throws IOException {	
			Configuration config = Configuration.getInstance(pageContext.getServletContext());
			String overLibURL = ChartImgTag.fixAbsolutURL(config.getOverlibURL(), pageContext);
			out.write("<script language=\"JavaScript\" src=\"");
			out.write(overLibURL + "\"><!-- overLIB (c) Erik Bosrup --></script>\n");
			out.write("<div id=\"overDiv\" style=\"position:absolute; visibility:hidden; z-index:1000;\"></div>\n");
			setToolTipsEnabled(pageContext);
		}		
		
		public void render(Writer out, String toolTip) throws IOException {
			out.write("ONMOUSEOVER=\"return overlib('"+ toolTip + "', WIDTH, '20');\" ONMOUSEOUT=\"return nd();\" ");			
		}

	}
	
	/**
	 * IE specific tooltip renderer. This uses Alt tags to render the tooltip.
	 * @author zluspai
	 *
	 */
	public static class IE implements ITooltipRenderer {
		public void init(HttpServletRequest request, Writer out, PageContext pageContext) throws IOException {
			// nothing to do
		}		
		
		public void render(Writer out, String toolTip) throws IOException {
			out.write("ALT=\"" + toolTip + "\" ");			
		}		
	}
	
	/**
	 * 'smart' tooltip renderer, which will use the IETooltipRenderer for Internet explorer, and
	 * overlib otherwise.
	 * @author zluspai
	 */
	public static class Smart implements ITooltipRenderer {

		private ITooltipRenderer delegate = null;
		
		public void init(HttpServletRequest request, Writer out, PageContext pageContext) throws IOException {
			// decide which renderer is going to be used...
			if (BrowserDetection.isIE(request)) {
				delegate = new IE();
			}
			else {
				delegate = new Overlib();
			}
			// call delegate init
			delegate.init(request, out, pageContext);
		}

		public void render(Writer out, String toolTip) throws IOException {
			// delegate the render task
			delegate.render(out, toolTip);
		}		
	}	
	
	
}