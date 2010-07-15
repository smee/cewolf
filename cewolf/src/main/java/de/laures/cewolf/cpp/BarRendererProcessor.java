package de.laures.cewolf.cpp;

import java.awt.Color;
import java.awt.Paint;
import java.io.Serializable;
import java.util.*;

import de.laures.cewolf.ChartPostProcessor;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.*;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.renderer.category.*;

/**
* A postprocessor for setting/removing the bar outline (default: false),
* the item margin for 2D and 3D bar charts (default: 0.2%), whether or not
* item labels are visible (default: no), and the color to use for item labels (default: black).
* It also has an option to set custom category colors (as opposed to custom series colors,
* which is what the SeriesPaintProcessor provides); if you use this you'll want to set showlegend=false.
* <P>
* Usage:<P>
* &lt;chart:chartpostprocessor id="barRenderer"&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="outline" value="true"/&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="itemMargin" value="0.1"/&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="showItemLabels" value="true"/&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="itemLabelColor" value="#336699" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="categoryColors" value="true"/&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="0" value="#FFFFAA" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="1" value="#AAFFAA" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="2" value="#FFAAFF" /&gt;<BR>
* &nbsp;&nbsp;&lt;chart:param name="3" value="#FFAAAA" /&gt;<BR>
* &lt;/chart:chartpostprocessor&gt;
*/

public class BarRendererProcessor implements ChartPostProcessor, Serializable
{
	static final long serialVersionUID = 6687503315061004361L;

    public void processChart (Object chart, Map params) {
		JFreeChart localChart = (JFreeChart) chart;
        Plot plot = (Plot) localChart.getPlot();

		if (plot instanceof CategoryPlot) {
			CategoryPlot catPlot = (CategoryPlot) plot;
			CategoryItemRenderer ciRenderer = catPlot.getRenderer();

			if (ciRenderer instanceof BarRenderer) {
				boolean outline = false;
				boolean showItemLabels = false;
				boolean categoryColors = false;
				double itemMargin = BarRenderer.DEFAULT_ITEM_MARGIN;
				Color itemLabelColor = new Color(0, 0, 0);

				String str = (String) params.get("outline");
				if (str != null)
					outline = "true".equals(str);

				str = (String) params.get("showItemLabels");
				if (str != null)
					showItemLabels = "true".equals(str);

				str = (String) params.get("categoryColors");
				if (str != null)
					categoryColors = "true".equals(str);

				str = (String) params.get("itemLabelColor");
				if (str != null && str.trim().length() > 0) {
					try {
						itemLabelColor = Color.decode(str);
					} catch (NumberFormatException nfex) { }
				}

				str = (String) params.get("itemMargin");
				if (str != null && str.trim().length() > 0) {
					try {
						itemMargin = Double.parseDouble(str);
					} catch (NumberFormatException nfex) { }
				}

				if (categoryColors) {
					List paints = new ArrayList();
					for (int i = 0; ; i++) {
						String colorStr = (String) params.get(String.valueOf(i));
						if (colorStr == null)
							break;
						paints.add(Color.decode(colorStr));
					}

					/* need to do most specific first! */
					if (ciRenderer instanceof BarRenderer3D) {
						catPlot.setRenderer(new CustomBarRenderer3D(paints));
					} else if (ciRenderer instanceof BarRenderer) {
						catPlot.setRenderer(new CustomBarRenderer(paints));
					}

					ciRenderer = catPlot.getRenderer();
				}

				BarRenderer renderer = (BarRenderer) ciRenderer;
				renderer.setDrawBarOutline(outline);
				renderer.setItemMargin(itemMargin);

				if (showItemLabels) {
					renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
					renderer.setBaseItemLabelsVisible(true);
					renderer.setBaseItemLabelPaint(itemLabelColor);
				}
				//ItemLabelPosition itemlabelposition
				//	= new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER, TextAnchor.CENTER, 0.0);
				//renderer.setBasePositiveItemLabelPosition(itemlabelposition);
			}
		}
	}

    /**
     * A custom renderer that returns a different color for each item in a single series.
     */
    private class CustomBarRenderer extends BarRenderer {

        /** The colors. */
        private List colors;

        /**
         * Creates a new renderer.
         *
         * @param colors  the colors.
         */
        public CustomBarRenderer (List colors) {
            this.colors = colors;
        }

        /**
         * Returns the paint for an item. Overrides the default behaviour inherited from AbstractSeriesRenderer.
         *
         * @param row  the series.
         * @param column  the category.
         *
         * @return The item color.
         */
        public Paint getItemPaint (int row, int column) {
            return (Paint) colors.get(column % colors.size());
        }
    }

    /**
     * A custom renderer that returns a different color for each item in a single series.
     */
    private class CustomBarRenderer3D extends BarRenderer3D {

        /** The colors. */
        private List colors;

        /**
         * Creates a new renderer.
         *
         * @param colors  the colors.
         */
        public CustomBarRenderer3D (List colors) {
            this.colors = colors;
        }

        /**
         * Returns the paint for an item. Overrides the default behaviour inherited from AbstractSeriesRenderer.
         *
         * @param row  the series.
         * @param column  the category.
         *
         * @return The item color.
         */
        public Paint getItemPaint (int row, int column) {
            return (Paint) colors.get(column % colors.size());
        }
    }
}

