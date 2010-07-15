
package de.laures.cewolf.jfree;

import java.util.HashMap;
import java.util.Map;
import de.laures.cewolf.util.Expr;

import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;

/**
 * A renderer that connects data points with lines and/or draws shapes at each data point. 
 * This renderer is designed for use with the XYPlot class.
 */

public class XYConditionRenderer extends XYLineAndShapeRenderer {

    private static final long serialVersionUID = -6413096273679067358L;

	private String shapeVisibleCondition;
	private String shapeFilledCondition;

	private static Expr expr = new Expr();

    /**
     * Creates a new renderer with both lines and shapes visible.
     */
    public XYConditionRenderer (boolean lines, boolean shapes) {
        super(lines, shapes);
    }

	public String getShapeVisibleCondition() { return shapeVisibleCondition; }

	public void setShapeVisibleCondition (String cond) { this.shapeVisibleCondition = cond; }

	public String getShapeFilledCondition() { return shapeFilledCondition; }

	public void setShapeFilledCondition (String cond) { this.shapeFilledCondition = cond; }

    /**
     * Returns the flag used to control whether or not the shape for an item is visible.
     *
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     *
     * @return A boolean.
     */
    public boolean getItemShapeVisible (int series, int item) {
		if ((shapeVisibleCondition == null) || (shapeVisibleCondition.length() == 0)) {
			return (super.getItemShapeVisible(series, item));
		} else {
			return evaluateCondition(shapeVisibleCondition, series, item);
		}
    }

    /**
     * Returns the flag used to control whether or not the shape for an item is filled.
     *
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     *
     * @return A boolean.
     */
    public boolean getItemShapeFilled (int series, int item) {
		if ((shapeFilledCondition == null) || (shapeFilledCondition.length() == 0)) {
			return (super.getItemShapeFilled(series, item));
		} else {
			return evaluateCondition(shapeFilledCondition, series, item);
		}
    }
 
 	private boolean evaluateCondition (String cond, int series, int item) {
		XYDataset ds = getPlot().getDataset();

		Map vars = new HashMap();
		vars.put("s", new Double(series));
		vars.put("i", new Double(item));
		vars.put("x", new Double(ds.getXValue(series,item)));
		vars.put("y", new Double(ds.getYValue(series,item)));
		double result = Expr.eval("cond("+cond+", 1, -1)", vars);
		return (result > 0);
	}

    /**
     * Tests this renderer for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean.
     */
    public boolean equals (Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof XYConditionRenderer)) {
            return false;
        }
        XYConditionRenderer that = (XYConditionRenderer) obj;
        if (! this.shapeVisibleCondition.equals(that.shapeVisibleCondition)) {
            return false;
        }
        if (! this.shapeFilledCondition.equals(that.shapeFilledCondition)) {
            return false;
        }
        return super.equals(obj);
    }

	public int hashCode() {
		assert false : "hashCode not designed";
		return 42; // any arbitrary constant will do 
	}
}
