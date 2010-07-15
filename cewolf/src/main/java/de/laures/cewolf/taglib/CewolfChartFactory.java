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

package de.laures.cewolf.taglib;

import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.CombinedRangeXYPlot;
import org.jfree.chart.plot.CompassPlot;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.urls.StandardXYURLGenerator;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.general.ValueDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.WindDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;

import de.laures.cewolf.jfree.ThermometerPlot;
import de.laures.cewolf.jfree.XYSplineRenderer;
import de.laures.cewolf.jfree.XYConditionRenderer;

import de.laures.cewolf.ChartValidationException;
import de.laures.cewolf.DatasetProduceException;

/**
 * Chart factory creates Jfreechart instances. To add a new factory use the
 * <code>CewolfChartFactory.registerFactory(new CewolfChartFactory() {...});</code> method.
 *
 * @author  Guido Laures
 */

public abstract class CewolfChartFactory implements ChartConstants, AxisConstants, LayoutConstants {

    // chart type string
  protected String chartType;
  // map contains registered factories, (String) chartType->CewolfChartFactory mappings
  private static Map factories = new HashMap();

    /** Creates a new instance of ChartFactory */
  protected CewolfChartFactory(String chartType) {
      this.chartType = chartType;
  }

  /**
   * Callback when the chart instance to be created.
     * @param title The title of chart
     * @param xAxisLabel label on x axis
     * @param yAxisLabel label on y axis
     * @param data The dataset to create chart for
     * @return The newly created JFreeChart instance
     *
     * @throws IncompatibleDatasetException If the incoming data is not compatible with this factory
     */
    public abstract JFreeChart getChartInstance (String title, String xAxisLabel, String yAxisLabel, Dataset data)
			throws IncompatibleDatasetException;

    //////////////// static part ///////////////////////

  /**
   * Register a new chart factory instance.
   * @param factory The factory to register
   */
  public static void registerFactory (CewolfChartFactory factory) {
      factories.put(factory.chartType, factory);
  }

  private static final int getChartTypeConstant (String type) {
    final int res = ChartTypes.typeList.indexOf(type.toLowerCase());
    if (res < 0) {
      throw new RuntimeException("unsupported chart type " + type);
    }
    return res;
  }

  private static final int getLayoutConstant (String layout) {
    return LayoutTypes.typeList.indexOf(layout.toLowerCase());
  }
 
	public static JFreeChart getChartInstance (String chartType, String title,
					String xAxisLabel, String yAxisLabel, Dataset data, boolean showLegend)
  			throws ChartValidationException {
		// first check the dynamically registered chart types
		CewolfChartFactory factory = (CewolfChartFactory) factories.get(chartType);
		if (factory != null) {
			// custom factory found, use it
			return factory.getChartInstance(title, xAxisLabel, yAxisLabel, data);
		}

		JFreeChart chart;

    switch (getChartTypeConstant(chartType)) {
      case XY :
        check(data, XYDataset.class, chartType);
        chart = createXYLineChart(title, xAxisLabel, yAxisLabel,
					(XYDataset) data, PlotOrientation.VERTICAL, showLegend, true, true);
        return chart;
      case PIE :
        check(data, PieDataset.class, chartType);
        chart = ChartFactory.createPieChart(title, (PieDataset) data, showLegend, true, true);
		return chart;
      case AREA_XY :
        check(data, XYDataset.class, chartType);
        chart = ChartFactory.createXYAreaChart(title, xAxisLabel, yAxisLabel,
					(XYDataset) data, PlotOrientation.VERTICAL, showLegend, false, false);
		return chart;
      case SCATTER :
        check(data, XYDataset.class, chartType);
        chart = createScatterPlot(title, xAxisLabel, yAxisLabel,
					(XYDataset) data, PlotOrientation.VERTICAL, showLegend, false, false);
		return chart;
      case AREA :
        check(data, CategoryDataset.class, chartType);
        chart = ChartFactory.createAreaChart(title, xAxisLabel, yAxisLabel,
					(CategoryDataset) data, PlotOrientation.VERTICAL, showLegend, false, false);
		return chart;
      case HORIZONTAL_BAR :
        check(data, CategoryDataset.class, chartType);
        chart = ChartFactory.createBarChart(title, xAxisLabel, yAxisLabel,
					(CategoryDataset) data, PlotOrientation.HORIZONTAL, showLegend, false, false);
		return chart;
      case HORIZONTAL_BAR_3D :
        check(data, CategoryDataset.class, chartType);
        chart = ChartFactory.createBarChart3D(title, xAxisLabel, yAxisLabel,
					(CategoryDataset) data, PlotOrientation.HORIZONTAL, showLegend, false, false);
		return chart;
      case LINE :
        check(data, CategoryDataset.class, chartType);
        chart = ChartFactory.createLineChart(title, xAxisLabel, yAxisLabel,
					(CategoryDataset) data, PlotOrientation.VERTICAL, showLegend, false, false);
		return chart;
      case STACKED_HORIZONTAL_BAR :
        check(data, CategoryDataset.class, chartType);
        chart = ChartFactory.createStackedBarChart(title, xAxisLabel, yAxisLabel,
					(CategoryDataset) data, PlotOrientation.HORIZONTAL, showLegend, false, false);
		return chart;
      case STACKED_HORIZONTAL_BAR_3D :
        check(data, CategoryDataset.class, chartType);
        chart = ChartFactory.createStackedBarChart3D(title, xAxisLabel, yAxisLabel,
					(CategoryDataset) data, PlotOrientation.HORIZONTAL, showLegend, false, false);
		return chart;
      case STACKED_VERTICAL_BAR :
        check(data, CategoryDataset.class, chartType);
        chart = ChartFactory.createStackedBarChart(title, xAxisLabel, yAxisLabel,
					(CategoryDataset) data, PlotOrientation.VERTICAL, showLegend, false, false);
		return chart;
      case STACKED_VERTICAL_BAR_3D :
        check(data, CategoryDataset.class, chartType);
        chart = ChartFactory.createStackedBarChart3D(title, xAxisLabel, yAxisLabel,
					(CategoryDataset) data, PlotOrientation.VERTICAL, showLegend, false, false);
		return chart;
      case VERTICAL_BAR :
        check(data, CategoryDataset.class, chartType);
        chart = ChartFactory.createBarChart(title, xAxisLabel, yAxisLabel,
					(CategoryDataset) data, PlotOrientation.VERTICAL, showLegend, false, false);
		return chart;
      case VERTICAL_BAR_3D :
        check(data, CategoryDataset.class, chartType);
        chart = ChartFactory.createBarChart3D(title, xAxisLabel, yAxisLabel,
					(CategoryDataset) data, PlotOrientation.VERTICAL, showLegend, false, false);
		return chart;
      case TIME_SERIES :
        check(data, XYDataset.class, chartType);
        chart = ChartFactory.createTimeSeriesChart(title, xAxisLabel, yAxisLabel,
					(XYDataset) data, showLegend, false, false);
		return chart;
      case CANDLE_STICK :
        check(data, OHLCDataset.class, chartType);
        chart = ChartFactory.createCandlestickChart(title, xAxisLabel, yAxisLabel, (OHLCDataset) data, true);
		return chart;
      case HIGH_LOW :
        check(data, OHLCDataset.class, chartType);
        chart = ChartFactory.createHighLowChart(title, xAxisLabel, yAxisLabel, (OHLCDataset) data, true);
		return chart;
      case GANTT :
        check(data, IntervalCategoryDataset.class, chartType);
        chart = ChartFactory.createGanttChart(title, xAxisLabel, yAxisLabel,
					(IntervalCategoryDataset) data, showLegend, false, false);
		return chart;
      case WIND :
        check(data, WindDataset.class, chartType);
        chart = ChartFactory.createWindPlot(title, xAxisLabel, yAxisLabel,
					(WindDataset) data, showLegend, false, false);
		return chart;
      case VERTICAL_XY_BAR :
        check(data, IntervalXYDataset.class, chartType);
        chart = ChartFactory.createXYBarChart(title, xAxisLabel, true, yAxisLabel,
					(IntervalXYDataset) data, PlotOrientation.VERTICAL, showLegend, false, false);
		return chart;
      case PIE_3D :
        check(data, PieDataset.class, chartType);
        chart = ChartFactory.createPieChart3D(title, (PieDataset) data, showLegend, false, false);
		return chart;
      case METER :
        check(data, ValueDataset.class, chartType);
        MeterPlot plot = new MeterPlot((ValueDataset) data);
        chart = new JFreeChart(title, new Font("SansSerif", Font.BOLD, 18), plot, showLegend);
        ChartFactory.getChartTheme().apply(chart);
        return chart;
		case DIAL :
			check(data, ValueDataset.class, chartType);
			DialPlot dplot = new DialPlot((ValueDataset) data);
			dplot.addPointer(new DialPointer.Pin());
			StandardDialScale scale = new StandardDialScale();
			scale.setTickLabelFont(new Font("Dialog", Font.BOLD, 10));
			dplot.addScale(0, scale);
			chart = new JFreeChart(title, new Font("SansSerif", Font.BOLD, 18), dplot, showLegend);
			ChartFactory.getChartTheme().apply(chart);
			return chart;
		case THERMOMETER :
			check(data, ValueDataset.class, chartType);
			ThermometerPlot tplot = new ThermometerPlot((ValueDataset) data);
			chart = new JFreeChart(title, new Font("SansSerif", Font.BOLD, 18), tplot, showLegend);
			ChartFactory.getChartTheme().apply(chart);
			return chart;
		case COMPASS :
			check(data, ValueDataset.class, chartType);
			CompassPlot cplot = new CompassPlot((ValueDataset) data);
			chart = new JFreeChart(title, new Font("SansSerif", Font.BOLD, 18), cplot, showLegend);
			ChartFactory.getChartTheme().apply(chart);
			return chart;
      case STACKED_AREA :
        check(data, CategoryDataset.class, chartType);
        chart = ChartFactory.createStackedAreaChart(title, xAxisLabel, yAxisLabel,
					(CategoryDataset) data, PlotOrientation.VERTICAL, showLegend, false, false);
		return chart;
      case BUBBLE :
        check(data, XYZDataset.class, chartType);
        chart = ChartFactory.createBubbleChart(title, xAxisLabel, yAxisLabel,
					(XYZDataset) data, PlotOrientation.VERTICAL, showLegend, false, false);
		return chart;
      case SPLINE :
        check(data, XYDataset.class, chartType);
        chart = createSplineChart(title, xAxisLabel, yAxisLabel,
					(XYDataset) data, PlotOrientation.VERTICAL, showLegend, true, true);
		return chart;
      case HISTOGRAM :
		check(data, IntervalXYDataset.class, chartType);
        chart = ChartFactory.createHistogram(title, xAxisLabel, yAxisLabel,
					(IntervalXYDataset) data, PlotOrientation.VERTICAL, true, false, false);
		return chart;
      case SPIDERWEB :
		check(data, CategoryDataset.class, chartType);
        SpiderWebPlot swplot = new SpiderWebPlot((CategoryDataset) data);
        chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, swplot, showLegend);
		ChartFactory.getChartTheme().apply(chart);
		return chart;

      default :
        throw new UnsupportedChartTypeException(chartType + " is not supported.");
    }
  }

    /**
     * Creates a spline chart (based on an {@link XYDataset}) with default settings.
     *
     * @param title  the chart title (<code>null</code> permitted).
     * @param xAxisLabel  a label for the X-axis (<code>null</code> permitted).
     * @param yAxisLabel  a label for the Y-axis (<code>null</code> permitted).
     * @param dataset  the dataset for the chart (<code>null</code> permitted).
     * @param orientation  the plot orientation (horizontal or vertical) (<code>null</code> NOT permitted).
     * @param showLegend  a flag specifying whether or not a legend is required.
     * @param tooltips  configure chart to generate tool tips?
     * @param urls  configure chart to generate URLs?
     *
     * @return The chart.
     */
    public static JFreeChart createSplineChart (String title, String xAxisLabel,
			String yAxisLabel, XYDataset dataset, PlotOrientation orientation,
			boolean showLegend, boolean tooltips, boolean urls) {

        if (orientation == null) {
            throw new IllegalArgumentException("Null 'orientation' argument.");
        }
        NumberAxis xAxis = new NumberAxis(xAxisLabel);
        xAxis.setAutoRangeIncludesZero(false);
        NumberAxis yAxis = new NumberAxis(yAxisLabel);
        XYItemRenderer renderer = new XYSplineRenderer(10);
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
        plot.setOrientation(orientation);
        if (tooltips) {
            renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
        }
        if (urls) {
            renderer.setURLGenerator(new StandardXYURLGenerator());
        }

        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, showLegend);
        ChartFactory.getChartTheme().apply(chart);
        return chart;
    }

    /**
     * Creates a scatter plot with default settings.  The chart object
     * returned by this method uses an {@link XYPlot} instance as the plot,
     * with a {@link NumberAxis} for the domain axis, a  {@link NumberAxis}
     * as the range axis, and an {@link XYConditionRenderer} as the renderer.
     *
     * @param title  the chart title (<code>null</code> permitted).
     * @param xAxisLabel  a label for the X-axis (<code>null</code> permitted).
     * @param yAxisLabel  a label for the Y-axis (<code>null</code> permitted).
     * @param dataset  the dataset for the chart (<code>null</code> permitted).
     * @param orientation  the plot orientation (horizontal or vertical) (<code>null</code> NOT permitted).
     * @param legend  a flag specifying whether or not a legend is required.
     * @param tooltips  configure chart to generate tool tips?
     * @param urls  configure chart to generate URLs?
     *
     * @return A scatter plot.
     */
    public static JFreeChart createScatterPlot (String title, String xAxisLabel,
            String yAxisLabel, XYDataset dataset, PlotOrientation orientation,
            boolean legend, boolean tooltips, boolean urls) {

        if (orientation == null) {
            throw new IllegalArgumentException("Null 'orientation' argument.");
        }
        NumberAxis xAxis = new NumberAxis(xAxisLabel);
        xAxis.setAutoRangeIncludesZero(false);
        NumberAxis yAxis = new NumberAxis(yAxisLabel);
        yAxis.setAutoRangeIncludesZero(false);

        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, null);

        XYToolTipGenerator toolTipGenerator = null;
        if (tooltips) {
            toolTipGenerator = new StandardXYToolTipGenerator();
        }

        XYURLGenerator urlGenerator = null;
        if (urls) {
            urlGenerator = new StandardXYURLGenerator();
        }
        XYItemRenderer renderer = new XYConditionRenderer(false, true);
        renderer.setBaseToolTipGenerator(toolTipGenerator);
        renderer.setURLGenerator(urlGenerator);
        plot.setRenderer(renderer);
        plot.setOrientation(orientation);

        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend);
        ChartFactory.getChartTheme().apply(chart);
        return chart;
    }

    /**
     * Creates a line chart (based on an {@link XYDataset}) with default settings.
     *
     * @param title  the chart title (<code>null</code> permitted).
     * @param xAxisLabel  a label for the X-axis (<code>null</code> permitted).
     * @param yAxisLabel  a label for the Y-axis (<code>null</code> permitted).
     * @param dataset  the dataset for the chart (<code>null</code> permitted).
     * @param orientation  the plot orientation (horizontal or vertical) (<code>null</code> NOT permitted).
     * @param legend  a flag specifying whether or not a legend is required.
     * @param tooltips  configure chart to generate tool tips?
     * @param urls  configure chart to generate URLs?
     *
     * @return The chart.
     */
    public static JFreeChart createXYLineChart (String title, String xAxisLabel,
				String yAxisLabel, XYDataset dataset, PlotOrientation orientation,
				boolean legend, boolean tooltips, boolean urls) {

        if (orientation == null) {
            throw new IllegalArgumentException("Null 'orientation' argument.");
        }
        NumberAxis xAxis = new NumberAxis(xAxisLabel);
        xAxis.setAutoRangeIncludesZero(false);
        NumberAxis yAxis = new NumberAxis(yAxisLabel);
        XYItemRenderer renderer = new XYConditionRenderer(true, false);
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
        plot.setOrientation(orientation);
        if (tooltips) {
            renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
        }
        if (urls) {
            renderer.setURLGenerator(new StandardXYURLGenerator());
        }

        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend);
        ChartFactory.getChartTheme().apply(chart);
        return chart;
    }

    public static JFreeChart getOverlaidChartInstance (String chartType, String title, String xAxisLabel,
			String yAxisLabel, int xAxisType, int yAxisType, List plotDefinitions, boolean showLegend)
    	throws ChartValidationException, DatasetProduceException {
    final int chartTypeConst = getChartTypeConstant(chartType);
    final AxisFactory axisFactory = AxisFactory.getInstance();
    switch (chartTypeConst) {
      case OVERLAY_XY :
        ValueAxis domainAxis = (ValueAxis) axisFactory.createAxis(ORIENTATION_HORIZONTAL, xAxisType, xAxisLabel);
        // get main plot
        PlotDefinition mainPlotDef = (PlotDefinition) plotDefinitions.get(0);
		if (domainAxis instanceof NumberAxis && mainPlotDef.isXaxisinteger()) {
			((NumberAxis) domainAxis).setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		}
        check(mainPlotDef.getDataset(), XYDataset.class, chartType);
        XYPlot plot = (XYPlot) mainPlotDef.getPlot(chartTypeConst);
        plot.setDomainAxis(domainAxis);
        ValueAxis rangeAxis = (ValueAxis) axisFactory.createAxis(ORIENTATION_VERTICAL, yAxisType, yAxisLabel);
		if (rangeAxis instanceof NumberAxis && mainPlotDef.isYaxisinteger()) {
			((NumberAxis) rangeAxis).setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		}
        plot.setRangeAxis(rangeAxis);
        //plot.setRenderer(new StandardXYItemRenderer());
        // add second and later datasets to main plot
        for (int plotidx = 1;plotidx<plotDefinitions.size();plotidx++) {
          PlotDefinition subPlotDef = (PlotDefinition) plotDefinitions.get(plotidx);
          check(subPlotDef.getDataset(), XYDataset.class, chartType);
          plot.setDataset(plotidx, (XYDataset)subPlotDef.getDataset());

          int rendererIndex = PlotTypes.getRendererIndex(subPlotDef.getType());
          XYItemRenderer rend = (XYItemRenderer) PlotTypes.getRenderer(rendererIndex);
          plot.setRenderer(plotidx, rend);
        }
        return new JFreeChart(title, new Font("SansSerif", Font.BOLD, 18), plot, showLegend);
      case OVERLAY_CATEGORY ://added by lrh 2005-07-11
        CategoryAxis domainAxis2 = (CategoryAxis)axisFactory.createAxis(ORIENTATION_HORIZONTAL, xAxisType, xAxisLabel);
        // get main plot
        mainPlotDef = (PlotDefinition) plotDefinitions.get(0);
        check(mainPlotDef.getDataset(), CategoryDataset.class, chartType);
        CategoryPlot plot2 = (CategoryPlot) mainPlotDef.getPlot(chartTypeConst);
        plot2.setDomainAxis(domainAxis2);
        ValueAxis va = (ValueAxis) axisFactory.createAxis(ORIENTATION_VERTICAL, yAxisType, yAxisLabel);
		if (va instanceof NumberAxis && mainPlotDef.isYaxisinteger()) {
			((NumberAxis) va).setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		}
        plot2.setRangeAxis(va);
        //plot.setRenderer(new StandardXYItemRenderer());
        // add second and later datasets to main plot
        for (int plotidx = 1;plotidx<plotDefinitions.size();plotidx++) {
          PlotDefinition subPlotDef = (PlotDefinition) plotDefinitions.get(plotidx);
          check(subPlotDef.getDataset(), CategoryDataset.class, chartType);
          plot2.setDataset(plotidx, (CategoryDataset)subPlotDef.getDataset());

          int rendererIndex = PlotTypes.getRendererIndex(subPlotDef.getType()); 
          CategoryItemRenderer rend2 = (CategoryItemRenderer) PlotTypes.getRenderer(rendererIndex);
          plot2.setRenderer(plotidx, rend2);
        }
        return new JFreeChart(title, new Font("SansSerif", Font.BOLD, 18), plot2, showLegend);
      default :
        throw new UnsupportedChartTypeException(chartType + " is not supported.");
    }
  }

  public static JFreeChart getCombinedChartInstance (String chartType, String title, String xAxisLabel,
  			String yAxisLabel, List plotDefinitions, String layout, boolean showLegend)
    	throws ChartValidationException, DatasetProduceException {
    final int chartTypeConst = getChartTypeConstant(chartType);
    switch (chartTypeConst) {
      case COMBINED_XY :
        final int layoutConst = getLayoutConstant(layout);
        Plot plot = null;
        switch (layoutConst) {
          case DOMAIN :
            ValueAxis domainAxis = new DateAxis(xAxisLabel);
            plot = new CombinedDomainXYPlot(domainAxis);
            for (int i = 0; i < plotDefinitions.size(); i++) {
              PlotDefinition pd = (PlotDefinition) plotDefinitions.get(i);
              check(pd.getDataset(), XYDataset.class, chartType);
              XYPlot temp = (XYPlot) pd.getPlot(chartTypeConst);
              NumberAxis na  = new NumberAxis(pd.getYaxislabel());
				if (pd.isYaxisinteger())
					na.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
              temp.setRangeAxis(na);
              ((CombinedDomainXYPlot) plot).add(temp);
            }
            return new JFreeChart(title, new Font("SansSerif", Font.BOLD, 18), plot, showLegend);
          case RANGE :
            ValueAxis rangeAxis = new NumberAxis(yAxisLabel);
            plot = new CombinedRangeXYPlot(rangeAxis);
			boolean allInteger = true;
            for (int i = 0; i < plotDefinitions.size(); i++) {
              PlotDefinition pd = (PlotDefinition) plotDefinitions.get(i);
				if (! pd.isYaxisinteger())
					allInteger = false;
              check(pd.getDataset(), XYDataset.class, chartType);
              XYPlot temp = (XYPlot) pd.getPlot(chartTypeConst);
              temp.setDomainAxis(new DateAxis(pd.getXaxislabel()));
              ((CombinedRangeXYPlot) plot).add(temp);
            }
			if (allInteger)
				((NumberAxis) rangeAxis).setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            return new JFreeChart(title, new Font("SansSerif", Font.BOLD, 18), plot, showLegend);
          default :
            throw new AttributeValidationException(layout, " any value");
        }
      default :
        throw new UnsupportedChartTypeException(chartType);
    }
  }

  /**
   * Helper to check if the given dataset is the expected type.
   * @param data The dataset
   * @param clazz Expected type (class)
   * @param chartType The chart type string
   * @throws IncompatibleDatasetException If not the expected class
   */
  public static void check(Dataset data, Class clazz, String chartType) throws IncompatibleDatasetException {
    if (!clazz.isInstance(data)) {
      throw new IncompatibleDatasetException("Charts of type " + chartType + " " + "need datasets of type " + clazz.getName());
    }
  }

}
