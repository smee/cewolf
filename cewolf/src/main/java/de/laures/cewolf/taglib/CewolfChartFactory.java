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
  protected String _chartType;
  // expected type/superclass for the dataset
  protected Class _expectedDatasetType;
  
  // map contains registered factories, (String) chartType->CewolfChartFactory mappings
  private static Map factories = new HashMap();

  /** 
   * Creates a new instance of ChartFactory 
   * @chartType The chart type string
   * @expectedDatasetType The dataset must instance of this type
   */
  protected CewolfChartFactory(String chartType, Class expectedDatasetType) {
      this._chartType = chartType;
      this._expectedDatasetType = expectedDatasetType;
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
      factories.put(factory._chartType.toLowerCase(), factory);
  }
    
  /**
   * Get cewolf chart factory for a certain type.
   * @param chartType The type
   * @return the factory, or null if not found
   */
  public static CewolfChartFactory getCewolfChartFactory(String chartType) {
      CewolfChartFactory factory = (CewolfChartFactory) factories.get(chartType.toLowerCase());
      return factory;
  }
  
  private static final int getLayoutConstant(String layout) {
    return LayoutTypes.typeList.indexOf(layout.toLowerCase());
  }
  
  // register/create known factories
  static {
    // histogram chart type
    registerFactory(new CewolfChartFactory("histogram", IntervalXYDataset.class) {
	    public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
	        return ChartFactory.createHistogram(title, xAxisLabel, yAxisLabel, (IntervalXYDataset) data, PlotOrientation.VERTICAL, true, false, false);
	     }
    });
    
    registerFactory(new CewolfChartFactory("xy",  XYDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, (XYDataset) data, PlotOrientation.VERTICAL, true, true, true);
    	}
    });
    
    registerFactory(new CewolfChartFactory("pie", PieDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createPieChart(title, (PieDataset) data, true, true, true);
    	}
    });
    
    registerFactory(new CewolfChartFactory("areaxy", XYDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createXYAreaChart(title, xAxisLabel, yAxisLabel, (XYDataset) data, PlotOrientation.VERTICAL, true, false, false);
    	}
    });

    registerFactory(new CewolfChartFactory("scatter", XYDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createScatterPlot(title, xAxisLabel, yAxisLabel, (XYDataset) data, PlotOrientation.VERTICAL, true, false, false);
    	}
    });
    
    registerFactory(new CewolfChartFactory("area", CategoryDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createAreaChart(title, xAxisLabel, yAxisLabel, (CategoryDataset) data, PlotOrientation.VERTICAL, true, false, false);
    	}
    });
    
    registerFactory(new CewolfChartFactory("horizontalbar", CategoryDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createBarChart(title, xAxisLabel, yAxisLabel, (CategoryDataset) data, PlotOrientation.HORIZONTAL, true, false, false);
    	}
    });
    
    registerFactory(new CewolfChartFactory("horizontalbar3d", CategoryDataset.class ) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createBarChart3D(title, xAxisLabel, yAxisLabel, (CategoryDataset) data, PlotOrientation.HORIZONTAL, true, false, false);
    	}
    });
    registerFactory(new CewolfChartFactory("line", CategoryDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createLineChart(title, xAxisLabel, yAxisLabel, (CategoryDataset) data, PlotOrientation.VERTICAL, true, false, false);
    	}
    });
    registerFactory(new CewolfChartFactory("stackedhorizontalbar", CategoryDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createStackedBarChart(title, xAxisLabel, yAxisLabel, (CategoryDataset) data, PlotOrientation.HORIZONTAL, true, false, false);
    	}
    });
    registerFactory(new CewolfChartFactory("stackedverticalbar", CategoryDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createStackedBarChart(title, xAxisLabel, yAxisLabel, (CategoryDataset) data, PlotOrientation.VERTICAL, true, false, false);
    	}
    });
    registerFactory(new CewolfChartFactory("stackedverticalbar3d", CategoryDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createStackedBarChart3D(title, xAxisLabel, yAxisLabel, (CategoryDataset) data, PlotOrientation.VERTICAL, true, false, false);
    	}
    });
    registerFactory(new CewolfChartFactory("verticalbar", CategoryDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createBarChart(title, xAxisLabel, yAxisLabel, (CategoryDataset) data, PlotOrientation.VERTICAL, true, false, false);
    	}
    });
    
    registerFactory(new CewolfChartFactory("verticalbar3d", CategoryDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createBarChart3D(title, xAxisLabel, yAxisLabel, (CategoryDataset) data, PlotOrientation.VERTICAL, true, false, false);
    	}
    });
    registerFactory(new CewolfChartFactory("timeseries", XYDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createTimeSeriesChart(title, xAxisLabel, yAxisLabel, (XYDataset) data, true, false, false);
    	}
    });
    registerFactory(new CewolfChartFactory("candlestick", OHLCDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createCandlestickChart(title, xAxisLabel, yAxisLabel, (OHLCDataset) data, true);
    	}
    });
    registerFactory(new CewolfChartFactory("highlow", OHLCDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createHighLowChart(title, xAxisLabel, yAxisLabel, (OHLCDataset) data, true);
    	}
    });
    registerFactory(new CewolfChartFactory("gantt", IntervalCategoryDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createGanttChart(title, xAxisLabel, yAxisLabel, (IntervalCategoryDataset) data, true, false, false);
    	}
    });
    registerFactory(new CewolfChartFactory("wind", WindDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createWindPlot(title, xAxisLabel, yAxisLabel, (WindDataset) data, true, false, false);
    	}
    });
    /*
    registerFactory(new CewolfChartFactory("signal", SignalsDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
    	     return ChartFactory.createSignalChart(title, xAxisLabel, yAxisLabel, (SignalsDataset) data, true);
    	}
    });
    */
    registerFactory(new CewolfChartFactory("verticalxybar", IntervalXYDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createXYBarChart(title, xAxisLabel, true,yAxisLabel, (IntervalXYDataset) data, PlotOrientation.VERTICAL, true, false, false);
    	}
    });
    registerFactory(new CewolfChartFactory("pie3d", PieDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createPieChart3D(title, (PieDataset) data, true, false, false);
    	}
    });
    registerFactory(new CewolfChartFactory("meter", ValueDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            MeterPlot plot = new MeterPlot((ValueDataset) data);
            JFreeChart chart = new JFreeChart(title, plot);
            return chart;
    	}
    });
    registerFactory(new CewolfChartFactory("stackedarea", CategoryDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createStackedAreaChart(title, xAxisLabel, yAxisLabel, (CategoryDataset) data, PlotOrientation.VERTICAL, true, false, false);
    	}
    });
    registerFactory(new CewolfChartFactory("bubble", XYZDataset.class) {
    	public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, Dataset data) throws IncompatibleDatasetException {
            return ChartFactory.createBubbleChart(title, xAxisLabel, yAxisLabel, (XYZDataset) data, PlotOrientation.VERTICAL, true, false, false);
    	}
    });
    
  }

  /**
   * Create the desired chart instance
   * @param chartType The chart type string
   * @param title The title of the chart
   * @param xAxisLabel
   * @param yAxisLabel
   * @param data The dataset of the chart
   * @return The created instance
   * @throws ChartValidationException
   */
  public static JFreeChart getChartInstance(String chartType, String title, String xAxisLabel, String yAxisLabel, Dataset data) throws ChartValidationException {
      // first check the dynamically registered chart types
      CewolfChartFactory factory = getCewolfChartFactory(chartType);
      if (factory != null) {
    	  // first check the dataset type
    	  check(data, factory._expectedDatasetType, chartType);
          // custom factory found, use it
          return factory.getChartInstance(title, xAxisLabel, yAxisLabel, data);
      }

      throw new UnsupportedChartTypeException(chartType + " is not supported.");
  }

    public static JFreeChart getOverlaidChartInstance(String chartType, String title, String xAxisLabel, String yAxisLabel, int xAxisType, int yAxisType, List plotDefinitions)
    throws ChartValidationException, DatasetProduceException {
    //final int chartTypeConst = getChartTypeConstant(chartType);
    final AxisFactory axisFactory = AxisFactory.getInstance();
    
    if (chartType.equals(ChartConstants.CHARTTYPE_OVERLAID_XY)) {
        ValueAxis domainAxis = (ValueAxis) axisFactory.createAxis(ORIENTATION_HORIZONTAL, xAxisType, xAxisLabel);
        // get main plot
        PlotDefinition mainPlotDef = (PlotDefinition) plotDefinitions.get(0);
        check((Dataset) mainPlotDef.getDataset(), XYDataset.class, chartType);
        XYPlot plot = (XYPlot) mainPlotDef.getPlot(chartType);
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis((ValueAxis) axisFactory.createAxis(ORIENTATION_VERTICAL, yAxisType, yAxisLabel));
        //plot.setRenderer(new StandardXYItemRenderer());
        // add second and later datasets to main plot
        for (int plotidx = 1;plotidx<plotDefinitions.size();plotidx++) {
          PlotDefinition subPlotDef = (PlotDefinition) plotDefinitions.get(plotidx);
          check((Dataset) subPlotDef.getDataset(), XYDataset.class, chartType);
          plot.setDataset(plotidx, (XYDataset)subPlotDef.getDataset());

          //int rendererIndex = PlotTypes.getRendererIndex(subPlotDef.getType());
          XYItemRenderer rend = (XYItemRenderer) PlotTypes.getRenderer(subPlotDef.getType());
          //getRenderer(rendererIndex);
          plot.setRenderer(plotidx, rend);
        }
        return new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
      }
      
      if (chartType.equals(ChartConstants.CHARTTYPE_OVERLAID_CATEGORY)) {
        //added by lrh 2005-07-11
        CategoryAxis domainAxis2 = (CategoryAxis)axisFactory.createAxis(ORIENTATION_HORIZONTAL, xAxisType, xAxisLabel);
        // get main plot
        PlotDefinition mainPlotDef = (PlotDefinition) plotDefinitions.get(0);
        check((Dataset) mainPlotDef.getDataset(), CategoryDataset.class, chartType);
        CategoryPlot plot2 = (CategoryPlot) mainPlotDef.getPlot(chartType);
        plot2.setDomainAxis(domainAxis2);
        plot2.setRangeAxis((ValueAxis)axisFactory.createAxis(ORIENTATION_VERTICAL, yAxisType, yAxisLabel));
        //plot.setRenderer(new StandardXYItemRenderer());
        // add second and later datasets to main plot
        for (int plotidx = 1;plotidx<plotDefinitions.size();plotidx++) {
          PlotDefinition subPlotDef = (PlotDefinition) plotDefinitions.get(plotidx);
          check((Dataset) subPlotDef.getDataset(), CategoryDataset.class, chartType);
          plot2.setDataset(plotidx, (CategoryDataset)subPlotDef.getDataset());

          //int rendererIndex = PlotTypes.getRendererIndex(subPlotDef.getType()); 
          CategoryItemRenderer rend2 = (CategoryItemRenderer) PlotTypes.getRenderer(subPlotDef.getType());
          //Renderer(rendererIndex);
          plot2.setRenderer(plotidx, rend2);
        }
        return new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot2, true);
      }
      
      // otherwise:
      throw new UnsupportedChartTypeException(chartType + " is not supported.");
  }

  // [tb]
  public static JFreeChart getCombinedChartInstance(String chartType, String title, String xAxisLabel, String yAxisLabel, List plotDefinitions, String layout)
    throws ChartValidationException, DatasetProduceException {
    //final int chartTypeConst = getChartTypeConstant(chartType);
	if (chartType.equals(ChartConstants.CHARTTYPE_COMBINED_XY)) {
        final int layoutConst = getLayoutConstant(layout);
        Plot plot = null;
        switch (layoutConst) {
          case DOMAIN :
            ValueAxis domainAxis = new DateAxis(xAxisLabel);
            plot = new CombinedDomainXYPlot(domainAxis);
            for (int i = 0; i < plotDefinitions.size(); i++) {
              PlotDefinition pd = (PlotDefinition) plotDefinitions.get(i);
              check((Dataset) pd.getDataset(), XYDataset.class, chartType);
              XYPlot temp = (XYPlot) pd.getPlot(chartType);
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
              XYPlot temp = (XYPlot) pd.getPlot(chartType);
              temp.setDomainAxis(new DateAxis(pd.getXaxislabel()));
              ((CombinedRangeXYPlot) plot).add(temp);
            }
            return new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
          default :
            throw new AttributeValidationException(layout, " any value");
        }
     }
     // otherwise unknown chart type:
     throw new UnsupportedChartTypeException(chartType);
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
