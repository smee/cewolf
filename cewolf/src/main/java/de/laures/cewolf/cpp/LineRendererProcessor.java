package de.laures.cewolf.cpp;

import java.awt.Color;
import java.io.Serializable;
import java.util.Map;

import de.laures.cewolf.ChartPostProcessor;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.chart.renderer.xy.*;

import de.laures.cewolf.jfree.XYConditionRenderer;

/**
* A postprocessor for controlling the appearance of shapes at data points
* for use with XYPlot (XYConditionRenderer) and CategoryPlot (LineAndShapeRenderer).
* <BR><b>shapes</b> whether or not to draw shapes at data points; default false
* <BR><b>outline</b> whether to draw shapes as outlines or solid; default false (i.e., solid)
* <BR><b>useFillPaint</b> whether to use the fill paint to draw the interior of shapes; default false
* <BR><b>fillPaint</b> the color to be used for interior of shapes; default: the line color
* <BR><b>useOutlinePaint</b> whether to use the outline paint to draw the outline of shapes; default false
* <BR><b>outlinePaint</b> the color to be used for outline of shapes; default: the line color
* <BR><b>shapeVisibleCondition</b>; optional, (for XY plots only)
* <BR><b>shapeFilledCondition</b>; optional, (for XY plots only)
* <P>
* <b>Usage</b><P>
* <code>
* &lt;cewolf:chartpostprocessor id="lineRenderer"&gt;<BR>
* &nbsp;&nbsp;&lt;cewolf:param name="shapes" value="true" /&gt;<BR>
* &nbsp;&nbsp;&lt;cewolf:param name="outline" value="true" /&gt;<BR>
* &nbsp;&nbsp;&lt;cewolf:param name="useFillPaint" value="true" /&gt;<BR>
* &nbsp;&nbsp;&lt;cewolf:param name="fillPaint" value="#FFFFFF" /&gt;<BR>
* &nbsp;&nbsp;&lt;cewolf:param name="useOutlinePaint" value="true" /&gt;<BR>
* &nbsp;&nbsp;&lt;cewolf:param name="outlinePaint" value="#000000" /&gt;<BR>
* &nbsp;&nbsp;&lt;cewolf:param name="shapeVisibleCondition" value="..." /&gt;<BR>
* &nbsp;&nbsp;&lt;cewolf:param name="shapeFilledCondition" value="..." /&gt;<BR>
* &lt;/cewolf:chartpostprocessor&gt;
* </code>
* <P>
* <b>Conditional expressions for shapeFilledCondition and shapeVisibleCondition</b><P>
* Must a mathematical/logical expression that evaluates to true or false.<BR>
* Several variables are available that can be used in the expression:<BR>
* "x" - the x value of the point<BR>
* "y" - the y value of the point<BR>
* "i" - the index of the current point; 0 &le; i &lt; number of points in the series<BR>
* "s" - the index of the series if there is more than one; 0 &le; s &lt; number of series<P>
* Furthermore, numerous operators and functions can be used:<BR>
*	+  -  *  /  ^  %  sin()  cos()  tan()  asin()  acos()  atan() exp()  ln()  sqrt()  cond(,,)  min()  max()<BR>
*<BR>
*	cond works somewhat like the "a ? b : c" construct in C and Java. The 1st argument should evaluate<BR>
*	to true or false (the numerical comparison operators can be used); if it's true,<BR>
*	cond evaluates to the 2nd argument, otherwise to the 3rd.<BR>
*<BR>
*	"PI" and "E" are predefined to be the corresponding mathematical constants.<BR>
*<BR>
*	Here's an EBNF of the expressions this class understands:<BR>
*<BR>
*	expr:	 ['-' | '+' ] term {'+' term | '-' term}.<BR>
*	term:	 factor {'*' factor | '/' factor | '%' factor}.<BR>
*	factor:	 primary {'^' factor}.<BR>
*	compExpr:	 ['-' | '+' ] term  compOper term<BR>
*	compOper:	'=' | '<' | '>' | '<>' | '<=' | '>='<BR>
*	primary: number | '(' expr ')' | 'sin' '(' expr ')' | 'cos' '(' expr ')' | 'tan' '(' expr ')' |<BR>
*			'exp' '(' expr ')' | 'ln' '(' expr ')' | 'atan' '(' expr ')'| 'acos' '(' expr ')' |<BR>
*			'sqrt' '(' expr ')' | 'cond' '(' compExpr ',' expr ',' expr ')' | 'asin' '(' expr ')'<BR>
*			'min' '(' expr (',' expr) * ')' | 'max' '(' expr (',' expr) * ')' |<BR>
*			'and' '(' expr (',' expr) * ')' | 'or' '(' expr (',' expr) * ')' | 'not' '(' expr ')' | variable <BR>
*	variable:	[a-zA-Z]+<BR>
*	number:	intnumber | realnumber.<BR>
*	realnumber:	[ intnumber ] . [ intnumber ].<BR>
*	intnumber:	digit {digit}.<BR>
*	digit:	0 | 1 | 2 | 3 | ... | 9<BR>
*<P>
* <b>Examples</b><P>
* <code>and (x&gt;=0, y&gt;=0)</code> - true for all points in the upper right quadrant<BR>
* <code>or (i&gt;=10, i&lt;=20)</code> - true for points no. 10 through 20 <BR>
*/

public class LineRendererProcessor implements ChartPostProcessor, Serializable
{
	static final long serialVersionUID = -1915129061254557434L;

    public void processChart (Object chart, Map params) {
		JFreeChart localChart = (JFreeChart) chart;
        Plot plot = (Plot) localChart.getPlot();

		if (plot instanceof CategoryPlot) {
			CategoryPlot catPlot = (CategoryPlot) plot;
			CategoryItemRenderer ciRenderer = catPlot.getRenderer();

			if (ciRenderer instanceof LineAndShapeRenderer) {
				LineAndShapeRenderer lasRenderer = (LineAndShapeRenderer) ciRenderer;

				boolean shapes = false;
				String str = (String) params.get("shapes");
				if (str != null)
					shapes = "true".equals(str);
				lasRenderer.setBaseShapesVisible(shapes);

				boolean outline = false;
				str = (String) params.get("outline");
				if (str != null)
					outline = "true".equals(str);
				lasRenderer.setDrawOutlines(outline);

				boolean useFillPaint = false;
				str = (String) params.get("useFillPaint");
				if (str != null)
					useFillPaint = "true".equals(str);
				lasRenderer.setUseFillPaint(useFillPaint);

				if (useFillPaint) {
					str = (String) params.get("fillPaint");
					if (str != null && str.trim().length() > 0) {
						try {
							Color fillPaint = Color.decode(str);
							lasRenderer.setBaseFillPaint(fillPaint);
						} catch (NumberFormatException nfex) { }
					}
				}

				boolean useOutlinePaint = false;
				str = (String) params.get("useOutlinePaint");
				if (str != null)
					useOutlinePaint = "true".equals(str);
				lasRenderer.setUseOutlinePaint(useOutlinePaint);

				if (useOutlinePaint) {
					str = (String) params.get("outlinePaint");
					if (str != null && str.trim().length() > 0) {
						try {
							Color outlinePaint = Color.decode(str);
							lasRenderer.setBaseOutlinePaint(outlinePaint);
						} catch (NumberFormatException nfex) { }
					}
				}
			}
		} else if (plot instanceof XYPlot) {
			XYPlot xyPlot = (XYPlot) plot;
			XYItemRenderer xyRenderer = xyPlot.getRenderer();

			if (xyRenderer instanceof XYConditionRenderer) {
				XYConditionRenderer xyCondRender = (XYConditionRenderer) xyRenderer;

				boolean shapes = false;
				String str = (String) params.get("shapes");
				if (str != null)
					shapes = "true".equals(str);
				xyCondRender.setBaseShapesVisible(shapes);

				boolean outline = false;
				str = (String) params.get("outline");
				if (str != null)
					outline = "true".equals(str);
				xyCondRender.setDrawOutlines(outline);

				boolean useFillPaint = false;
				str = (String) params.get("useFillPaint");
				if (str != null)
					useFillPaint = "true".equals(str);
				xyCondRender.setUseFillPaint(useFillPaint);

				if (useFillPaint) {
					str = (String) params.get("fillPaint");
					if (str != null && str.trim().length() > 0) {
						try {
							Color fillPaint = Color.decode(str);
							xyCondRender.setBaseFillPaint(fillPaint);
						} catch (NumberFormatException nfex) { }
					}
				}

				boolean useOutlinePaint = false;
				str = (String) params.get("useOutlinePaint");
				if (str != null)
					useOutlinePaint = "true".equals(str);
				xyCondRender.setUseOutlinePaint(useOutlinePaint);

				if (useOutlinePaint) {
					str = (String) params.get("outlinePaint");
					if (str != null && str.trim().length() > 0) {
						try {
							Color outlinePaint = Color.decode(str);
							xyCondRender.setBaseOutlinePaint(outlinePaint);
						} catch (NumberFormatException nfex) { }
					}
				}

				str = (String) params.get("shapeVisibleCondition");
				if (str != null)
					xyCondRender.setShapeVisibleCondition(str);

				str = (String) params.get("shapeFilledCondition");
				if (str != null)
					xyCondRender.setShapeFilledCondition(str);
			}
		}
	}
}

