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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.CombinedRangeXYPlot;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.general.ValueDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.SignalsDataset;
import org.jfree.data.xy.WindDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;

import de.laures.cewolf.ChartValidationException;
import de.laures.cewolf.DatasetProduceException;

/**
 * Chart factory creates Jfreechart instances. To add a new factory use the
 * <code>
 * 		CewolfChartFactory.registerFactory(new CewolfChartFactory() {...});
 * </code>
 * method.
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
    public abstract JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException;

    //////////////// static part ///////////////////////

  /**
   * Register a new chart factory instance.
   * @param factory The factory to register
   */
  public static void registerFactory(CewolfChartFactory factory) {
      factories.put(factory.chartType, factory);
  }

  private static final int getChartTypeConstant(String type) {
    final int res = ChartTypes.typeList.indexOf(type.toLowerCase());
    if (res < 0) {
      throw new RuntimeException("unsupported chart type " + type);
    }
    return res;
  }

  private static final int getLayoutConstant(String layout) {
    return LayoutTypes.typeList.indexOf(layout.toLowerCase());
  }
  
  static {
    // histogram chart type
    registerFactory(new CewolfChartFactory("histogram") {
	    public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
	        check(data, IntervalXYDataset.class, this.chartType);
	        return ChartFactory.createHistogram(title, xAxisLabel, yAxisLabel, (IntervalXYDataset) data, PlotOrientation.VERTICAL, true, false, false);
	     }
    });
  }

  public static JFreeChart getChartInstance(String chartType, String title, String xAxisLabel, String yAxisLabel, Dataset data) throws ChartValidationException {
      // first check the dynamically registered chart types
      CewolfChartFactory factory = (CewolfChartFactory) factories.get(chartType);
      if (factory != null) {
          // custom factory found, use it
          return factory.getChartInstance(title, xAxisLabel, yAxisLabel, data);
      }

    switch (getChartTypeConstant(chartType)) {
      case XY :
        check(data, XYDataset.class, chartType);
        return ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, (XYDataset) data, PlotOrientation.VERTICAL, true, true, true);
      case PIE :
        check(data, PieDataset.class, chartType);
        return ChartFactory.createPieChart(title, (PieDataset) data, true, true, true);
      case AREA_XY :
        check(data, XYDataset.class, chartType);
        return ChartFactory.createXYAreaChart(title, xAxisLabel, yAxisLabel, (XYDataset) data, PlotOrientation.VERTICAL, true, false, false);
      case SCATTER :
        check(data, XYDataset.class, chartType);
        return ChartFactory.createScatterPlot(title, xAxisLabel, yAxisLabel, (XYDataset) data, PlotOrientation.VERTICAL, true, false, false);
      case AREA :
        check(data, CategoryDataset.class, chartType);
        return ChartFactory.createAreaChart(title, xAxisLabel, yAxisLabel, (CategoryDataset) data, PlotOrientation.VERTICAL, true, false, false);
      case HORIZONTAL_BAR :
        check(data, CategoryDataset.class, chartType);
        return ChartFactory.createBarChart(title, xAxisLabel, yAxisLabel, (CategoryDataset) data, PlotOrientation.HORIZONTAL, true, false, false);
      case HORIZONTAL_BAR_3D :
        check(data, CategoryDataset.class, chartType);
        return ChartFactory.createBarChart3D(title, xAxisLabel, yAxisLabel, (CategoryDataset) data, PlotOrientation.HORIZONTAL, true, false, false);
      case LINE :
        check(data, CategoryDataset.class, chartType);
        return ChartFactory.createLineChart(title, xAxisLabel, yAxisLabel, (CategoryDataset) data, PlotOrientation.VERTICAL, true, false, false);
      case STACKED_HORIZONTAL_BAR :
        check(data, CategoryDataset.class, chartType);
        return ChartFactory.createStackedBarChart(title, xAxisLabel, yAxisLabel, (CategoryDataset) data, PlotOrientation.HORIZONTAL, true, false, false);
      case STACKED_VERTICAL_BAR :
        check(data, CategoryDataset.class, chartType);
        return ChartFactory.createStackedBarChart(title, xAxisLabel, yAxisLabel, (CategoryDataset) data, PlotOrientation.VERTICAL, true, false, false);
      case STACKED_VERTICAL_BAR_3D :
        check(data, CategoryDataset.class, chartType);
        return ChartFactory.createStackedBarChart3D(title, xAxisLabel, yAxisLabel, (CategoryDataset) data, PlotOrientation.VERTICAL, true, false, false);
      case VERTICAL_BAR :
        check(data, CategoryDataset.class, chartType);
        return ChartFactory.createBarChart(title, xAxisLabel, yAxisLabel, (CategoryDataset) data, PlotOrientation.VERTICAL, true, false, false);
      case VERTICAL_BAR_3D :
        check(data, CategoryDataset.class, chartType);
        return ChartFactory.createBarChart3D(title, xAxisLabel, yAxisLabel, (CategoryDataset) data, PlotOrientation.VERTICAL, true, false, false);
      case TIME_SERIES :
        check(data, XYDataset.class, chartType);
        return ChartFactory.createTimeSeriesChart(title, xAxisLabel, yAxisLabel, (XYDataset) data, true, false, false);
      case CANDLE_STICK :
        check(data, OHLCDataset.class, chartType);
        return ChartFactory.createCandlestickChart(title, xAxisLabel, yAxisLabel, (OHLCDataset) data, true);
      case HIGH_LOW :
        check(data, OHLCDataset.class, chartType);
        return ChartFactory.createHighLowChart(title, xAxisLabel, yAxisLabel, (OHLCDataset) data, true);
      case GANTT :
        check(data, IntervalCategoryDataset.class, chartType);
        return ChartFactory.createGanttChart(title, xAxisLabel, yAxisLabel, (IntervalCategoryDataset) data, true, false, false);
      case WIND :
        check(data, WindDataset.class, chartType);
        return ChartFactory.createWindPlot(title, xAxisLabel, yAxisLabel, (WindDataset) data, true, false, false);
      case SIGNAL :
        check(data, SignalsDataset.class, chartType);
        return ChartFactory.createSignalChart(title, xAxisLabel, yAxisLabel, (SignalsDataset) data, true);
      case VERRTICAL_XY_BAR :
        check(data, IntervalXYDataset.class, chartType);
        return ChartFactory.createXYBarChart(title, xAxisLabel, true,yAxisLabel, (IntervalXYDataset) data, PlotOrientation.VERTICAL, true, false, false);
      case PIE_3D :
        check(data, PieDataset.class, chartType);
        return ChartFactory.createPieChart3D(title, (PieDataset) data, true, false, false);
      case METER :
        check(data, ValueDataset.class, chartType);
        MeterPlot plot = new MeterPlot((ValueDataset) data);
        JFreeChart chart = new JFreeChart(title, plot);
        return chart;
      case STACKED_AREA :
        check(data, CategoryDataset.class, chartType);
        return ChartFactory.createStackedAreaChart(title, xAxisLabel, yAxisLabel, (CategoryDataset) data, PlotOrientation.VERTICAL, true, false, false);
      case BUBBLE :
        check(data, XYZDataset.class, chartType);
        return ChartFactory.createBubbleChart(title, xAxisLabel, yAxisLabel, (XYZDataset) data, PlotOrientation.VERTICAL, true, false, false);
      default :
        throw new UnsupportedChartTypeException(chartType + " is not supported.");
    }
  }

    public static JFreeChart getOverlaidChartInstance(String chartType, String title, String xAxisLabel, String yAxisLabel, int xAxisType, int yAxisType, List plotDefinitions)
    throws ChartValidationException, DatasetProduceException {
    final int chartTypeConst = getChartTypeConstant(chartType);
    final AxisFactory axisFactory = AxisFactory.getInstance();
    switch (chartTypeConst) {
      case OVERLAY_XY :
        ValueAxis domainAxis = (ValueAxis) axisFactory.createAxis(ORIENTATION_HORIZONTAL, xAxisType, xAxisLabel);
        // get main plot
        PlotDefinition mainPlotDef = (PlotDefinition) plotDefinitions.get(0);
        check((Dataset) mainPlotDef.getDataset(), XYDataset.class, chartType);
        XYPlot plot = (XYPlot) mainPlotDef.getPlot(chartTypeConst);
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis((ValueAxis) axisFactory.createAxis(ORIENTATION_VERTICAL, yAxisType, yAxisLabel));
        //plot.setRenderer(new StandardXYItemRenderer());
        // add second and later datasets to main plot
        for (int plotidx = 1;plotidx<plotDefinitions.size();plotidx++) {
          PlotDefinition subPlotDef = (PlotDefinition) plotDefinitions.get(plotidx);
          check((Dataset) subPlotDef.getDataset(), XYDataset.class, chartType);
          plot.setDataset(plotidx, (XYDataset)subPlotDef.getDataset());

          int rendererIndex = PlotTypes.getRendererIndex(subPlotDef.getType());
          XYItemRenderer rend = (XYItemRenderer) PlotTypes.getRenderer(rendererIndex);
          plot.setRenderer(plotidx, rend);
        }
        return new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
      case OVERLAY_CATEGORY ://added by lrh 2005-07-11
        CategoryAxis domainAxis2 = (CategoryAxis)axisFactory.createAxis(ORIENTATION_HORIZONTAL, xAxisType, xAxisLabel);
        // get main plot
        mainPlotDef = (PlotDefinition) plotDefinitions.get(0);
        check((Dataset) mainPlotDef.getDataset(), CategoryDataset.class, chartType);
        CategoryPlot plot2 = (CategoryPlot) mainPlotDef.getPlot(chartTypeConst);
        plot2.setDomainAxis(domainAxis2);
        plot2.setRangeAxis((ValueAxis)axisFactory.createAxis(ORIENTATION_VERTICAL, yAxisType, yAxisLabel));
        //plot.setRenderer(new StandardXYItemRenderer());
        // add second and later datasets to main plot
        for (int plotidx = 1;plotidx<plotDefinitions.size();plotidx++) {
          PlotDefinition subPlotDef = (PlotDefinition) plotDefinitions.get(plotidx);
          check((Dataset) subPlotDef.getDataset(), CategoryDataset.class, chartType);
          plot2.setDataset(plotidx, (CategoryDataset)subPlotDef.getDataset());

          int rendererIndex = PlotTypes.getRendererIndex(subPlotDef.getType()); 
          CategoryItemRenderer rend2 = (CategoryItemRenderer) PlotTypes.getRenderer(rendererIndex);
          plot2.setRenderer(plotidx, rend2);
        }
        return new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot2, true);
      default :
        throw new UnsupportedChartTypeException(chartType + " is not supported.");
    }
  }

  // [tb]
  public static JFreeChart getCombinedChartInstance(String chartType, String title, String xAxisLabel, String yAxisLabel, List plotDefinitions, String layout)
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
              check((Dataset) pd.getDataset(), XYDataset.class, chartType);
              XYPlot temp = (XYPlot) pd.getPlot(chartTypeConst);
              temp.setRangeAxis(new NumberAxis(pd.getYaxislabel()));
              ((CombinedDomainXYPlot) plot).add(temp);
            }
            return new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
          case RANGE :
            ValueAxis rangeAxis = new NumberAxis(yAxisLabel);
            plot = new CombinedRangeXYPlot(rangeAxis);
            for (int i = 0; i < plotDefinitions.size(); i++) {
              PlotDefinition pd = (PlotDefinition) plotDefinitions.get(i);
              check((Dataset) pd.getDataset(), XYDataset.class, chartType);
              XYPlot temp = (XYPlot) pd.getPlot(chartTypeConst);
              temp.setDomainAxis(new DateAxis(pd.getXaxislabel()));
              ((CombinedRangeXYPlot) plot).add(temp);
            }
            return new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
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
