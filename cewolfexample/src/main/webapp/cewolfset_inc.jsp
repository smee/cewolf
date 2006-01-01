<%@page import="java.util.*"%>
<%@page import="de.laures.cewolf.*"%>
<%@page import="de.laures.cewolf.tooltips.*"%>
<%@page import="de.laures.cewolf.links.*"%>
<%@page import="org.jfree.data.*"%>
<%@page import="org.jfree.data.time.*"%>
<%@page import="org.jfree.data.gantt.*"%>
<%@page import="org.jfree.chart.*"%>
<%@page import="org.jfree.chart.plot.*"%>
<%@page import="org.jfree.data.category.*"%>
<%@page import="org.jfree.data.general.*"%>
<%@page import="org.jfree.data.xy.*"%>
<%@page import="java.awt.*" %>
<%@ page import="de.laures.cewolf.taglib.CewolfChartFactory" %>
<%@ page import="org.jfree.chart.event.ChartProgressListener" %>
<%@ page import="org.jfree.chart.event.ChartProgressEvent" %>


<%
if (pageContext.getAttribute("initFlag") == null) {
  DatasetProducer timeData = new DatasetProducer() {
    public Object produceDataset(Map params) {
      TimeSeries ts = new TimeSeries("Cewolf Release Schedule", Month.class);
      ts.add(new Month(7, 2002), 0.1);
      ts.add(new Month(8, 2002), 0.4);
      ts.add(new Month(9, 2002), 0.9);
      ts.add(new Month(10, 2002), 1.0);
      return new TimeSeriesCollection(ts);
    }
    public String getProducerId() {
      return "TimeDataProducer";
    }
    public boolean hasExpired(Map params, Date since) {
      return false;
    }
  };
  pageContext.setAttribute("timeData", timeData);

  DatasetProducer signalsData = new DatasetProducer() {
    public Object produceDataset(Map params) {
      TimeSeries ts = new TimeSeries("Cewolf Release Schedule", Month.class);
      ts.add(new Month(7, 2002), 0.1);
      ts.add(new Month(8, 2002), 0.4);
      ts.add(new Month(9, 2002), 0.9);
      ts.add(new Month(10, 2002), 1.0);
      TimeSeriesCollection col = new TimeSeriesCollection(ts);
      return new org.jfree.data.general.SubSeriesDataset(col, 0);
    }
    public String getProducerId() {
      return "SignalsDataProducer";
    }
    public boolean hasExpired(Map params, Date since) {
      return false;
    }
  };
  pageContext.setAttribute("signalsData", signalsData);

  DatasetProducer xyData = new DatasetProducer() {
    public Object produceDataset(Map params) {
      XYSeries xys = new XYSeries("Example XY Dataset");
      double last = 0.0;
      for (int i = -50; i <= 50; i++) {
        double y = last + ((Math.random() * 100) - 50.0);
        xys.add((double) i, y);
        last = y;
      }
      return new XYSeriesCollection(xys);
    }
    public String getProducerId() {
      return "XYDataProducer";
    }
    public boolean hasExpired(Map params, Date since) {
      return false;
    }
  };
  pageContext.setAttribute("xyData", xyData);

  DatasetProducer windData = new DatasetProducer() {
    public Object produceDataset(Map params) {
      int itemCount = 10;
      Object[][][] values = new Integer[itemCount][itemCount][itemCount];
      for (int i = 0; i < itemCount; i++) {
        for (int j = 0; j < itemCount; j++) {
          for (int k = 0; k < itemCount; k++) {
            values[i][j][k] = new Integer((int) Math.random() * 5);
          }
        }
      }
      return new DefaultWindDataset(DefaultWindDataset.seriesNameListFromDataArray(values[0]), values);
    }
    public String getProducerId() {
      return "WindDataProducer";
    }
    public boolean hasExpired(Map params, Date since) {
      return false;
    }
  };
  pageContext.setAttribute("windData", windData);

  DatasetProducer pieData = new DatasetProducer() {
    public Object produceDataset(Map params) {
      final String[] categories = { "apples", "pies", "bananas", "oranges" };
      DefaultPieDataset ds = new DefaultPieDataset();
      for (int i = 0; i < categories.length; i++) {
        int y = (int) (Math.random() * 10 + 1);
        ds.setValue(categories[i], new Integer(y));
      }
      return ds;
    }
    public String getProducerId() {
      return "PieDataProducer";
    }
    public boolean hasExpired(Map params, Date since) {
      return false;
    }
  };
  pageContext.setAttribute("pieData", pieData);

  DatasetProducer categoryData = new DatasetProducer() {
    public Object produceDataset(Map params) {
      final String[] categories = { "apples", "pies", "bananas", "oranges" };
      final String[] seriesNames = { "Peter", "Helga", "Franz", "Olga" };
      final Integer[][] startValues = new Integer[seriesNames.length][categories.length];
      final Integer[][] endValues = new Integer[seriesNames.length][categories.length];
      for (int series = 0; series < seriesNames.length; series++) {
        for (int i = 0; i < categories.length; i++) {
          int y = (int) (Math.random() * 10 + 1);
          startValues[series][i] = new Integer(y);
          endValues[series][i] = new Integer(y + (int) (Math.random() * 10));
        }
      }
      DefaultIntervalCategoryDataset ds =
        new DefaultIntervalCategoryDataset(seriesNames, categories, startValues, endValues);
      return ds;
    }
    public String getProducerId() {
      return "CategoryDataProducer";
    }
    public boolean hasExpired(Map params, Date since) {
      return false;
    }
  };
  pageContext.setAttribute("categoryData", categoryData);

  DatasetProducer ganttData = new DatasetProducer() {
    final private long now = System.currentTimeMillis();
    final private long day = 1000 * 60 * 60 * 24;
    final private String[] workflows =
      { "Analysis", "Design", "Implementation", "Test", "Rollout", "Operations" };
    final private String[] person = { "Frank", "Paul", "Daisy", "Chris" };
    public Object produceDataset(Map params) {
      TaskSeriesCollection ds = new TaskSeriesCollection();
      for (int j = 0; j < 4; j++) {
        TaskSeries ser = new TaskSeries(person[j]);
        long lastEnd = now + getRandomTime();
        for (int i = 0; i < workflows.length; i++) {
          long myEnd = lastEnd + getRandomTime();
          Task t = new Task(workflows[i], new SimpleTimePeriod(new Date(lastEnd), new Date(myEnd)));
          ser.add(t);
          lastEnd = myEnd;
        }
        ds.add(ser);
      }
      return ds;
    }
    private long getRandomTime() {
      return day * (long) (Math.random() * 31 + 15);
    }
    public String getProducerId() {
      return "GanttDataProducer";
    }
    public boolean hasExpired(Map params, Date since) {
      return true;
    }
  };
  pageContext.setAttribute("ganttData", ganttData);

  DatasetProducer hiloData = new DatasetProducer() {
    public Object produceDataset(Map params) {
      int itemCount = 100;
      Date[] dates = new Date[itemCount];
      double[] high = new double[itemCount];
      double[] low = new double[itemCount];
      double[] open = new double[itemCount];
      double[] close = new double[itemCount];
      double[] volume = new double[itemCount];
      Calendar cal = new GregorianCalendar();
      for (int i = 0; i < itemCount; i++) {
        cal.roll(Calendar.HOUR, 1);
        dates[i] = cal.getTime();
        high[i] = Math.random() * 100 + 100;
        low[i] = high[i] - (Math.random() * 30);
        open[i] = low[i] + (Math.random() * (high[i] - low[i]));
        close[i] = open[i] + (Math.random() * (high[i] - open[i]));
        volume[i] = Math.random() * 1000;
      }
      OHLCDataset ds =
        new DefaultHighLowDataset("Laures, Inc.", dates, high, low, open, close, volume);
      return ds;
    }
    public String getProducerId() {
      return "HiLoDataProducer";
    }
    public boolean hasExpired(Map params, Date since) {
      return false;
    }
  };
  pageContext.setAttribute("highLowData", hiloData);

  DatasetProducer valueDatasetProducer = new DatasetProducer() {

    public Object produceDataset(Map params) throws DatasetProduceException {
      Integer val = new Integer(86);
      DefaultValueDataset data = new DefaultValueDataset(val);
      return data;
    }

    public boolean hasExpired(Map params, Date since) {
      return false;
    }

    public String getProducerId() {
      return "meterdata";
    }

  };

  ChartPostProcessor meterPP = new ChartPostProcessor() {

    public void processChart(Object chart, Map params) {
      MeterPlot plot = (MeterPlot) ((JFreeChart) chart).getPlot();

      double min = 0;
      double max = 260;
      double val = 86;
      double minCrit = 187;
      double maxCrit = max;
      double minWarn = 164;
      double maxWarn = minCrit;
      double maxNorm = minCrit;
      double minNorm = min;

      plot.setRange(new Range(min, max));
      plot.addInterval(new MeterInterval("Normal",new Range(minNorm, maxNorm),Color.green, new BasicStroke(2.0f), null));
      plot.addInterval(new MeterInterval("Warning",new Range(minWarn, maxWarn),Color.yellow, new BasicStroke(2.0f), null));
      plot.addInterval(new MeterInterval("Critical",new Range(minCrit, maxCrit),Color.red, new BasicStroke(2.0f), null));

      plot.setUnits("km/h");
    }
  };
  pageContext.setAttribute("meterRanges", meterPP);
  pageContext.setAttribute("meterData", valueDatasetProducer);

  LinkGenerator xyLG = new XYItemLinkGenerator() {
    public String generateLink(Object data, int series, int item) {
      return "#Series " + series;
    }
  };
  pageContext.setAttribute("xyLinkGenerator", xyLG);

  CategoryToolTipGenerator catTG = new CategoryToolTipGenerator() {
    public String generateToolTip(CategoryDataset dataset, int series, int index) {
      return String.valueOf(dataset.getValue(series, index));
    }
  };
  pageContext.setAttribute("categoryToolTipGenerator", catTG);

  PieToolTipGenerator pieTG = new PieToolTipGenerator() {
    public String generateToolTip(PieDataset dataset, Comparable section, int index) {
      return String.valueOf(index);
    }
  };
  pageContext.setAttribute("pieToolTipGenerator", pieTG);

  ChartPostProcessor dataColor = new ChartPostProcessor() {
    public void processChart(Object chart, Map params) {
      CategoryPlot plot = (CategoryPlot) ((JFreeChart) chart).getPlot();
      for (int i = 0; i < params.size(); i++) {
        String colorStr = (String) params.get(String.valueOf(i));
        plot.getRenderer().setSeriesPaint(i, java.awt.Color.decode(colorStr));
      }
    }
  };
  pageContext.setAttribute("dataColor", dataColor);
  pageContext.setAttribute("initFlag", "init");
    }
%>
<html>
<head>
<link href="cewolf.css" rel="stylesheet" type="text/css"></head>
<BODY bgcolor="#DDE8F2">
<H1>Cewolf Chart Set</H1>
<p>
<table border=0>
<TR>
<TD>
colorpaint<BR>
<cewolf:chart id="timeChart" title="TimeSeries" type="timeseries">
    <cewolf:colorpaint color="#EEEEFF"/>
    <cewolf:data>
        <cewolf:producer id="timeData"/>
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="timeChart" renderer="/cewolf" width="300" height="300" />
</TD>
<TD>
colorpaint with aplpha blending. link map<br>
<cewolf:chart id="XYChart" title="XY" type="xy" xaxislabel="x-values" yaxislabel="y-values">
    <cewolf:colorpaint color="#AAAAFFEE"/>
    <cewolf:data>
        <cewolf:producer id="xyData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="XYChart" renderer="/cewolf" width="300" height="300">
  <cewolf:map linkgeneratorid="xyLinkGenerator"/>
</cewolf:img>
</TD>
</TR>
<TR>
<TD>
gradientpaint acyclic<br>
<cewolf:chart id="pieChart" title="Pie" type="pie">
    <cewolf:gradientpaint>
        <cewolf:point x="0" y="0" color="#FFFFFF" />
        <cewolf:point x="300" y="0" color="#DDDDFF" />
    </cewolf:gradientpaint>
    <cewolf:data>
        <cewolf:producer id="pieData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="pieChart" renderer="/cewolf" width="300" height="300"/>
</TD>
<TD>
background texture paint<br>
<cewolf:chart id="areaXYChart" title="AreaXY" type="areaxy" >
    <cewolf:texturepaint image="/img/bg.jpg" width="60" height="60" />
    <cewolf:data>
        <cewolf:producer id="xyData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="areaXYChart" renderer="/cewolf" width="300" height="300"/>
</TD>
</TR>
<TR>
<TD>
image map links<br>
<cewolf:chart id="scatterPlot" title="ScatterPlot" type="scatter">
    <cewolf:data>
        <cewolf:producer id="xyData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="scatterPlot" renderer="/cewolf" width="300" height="300" />
</TD>
<TD>
background image<BR>
<cewolf:chart id="areaChart" title="Area" type="area" xaxislabel="Fruit" yaxislabel="favorite"  background="/img/bg.jpg">
    <cewolf:data>
        <cewolf:producer id="categoryData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="areaChart" renderer="/cewolf" width="300" height="300"/>
</TD>
</TR>
<TR>
<TD>
vertical labels with tooltips but without image map links<BR>
<cewolf:chart id="horizontalBarChart" title="HorizontalBar" type="horizontalBar" xaxislabel="Fruit" yaxislabel="favorite">
    <cewolf:data>
        <cewolf:producer id="categoryData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="horizontalBarChart" renderer="/cewolf" width="300" height="300">
    <cewolf:map tooltipgeneratorid="categoryToolTipGenerator"/>
</cewolf:img>
</TD>
<TD>
<cewolf:chart id="horizontalBarChart3D" title="HorizontalBar3D" type="horizontalBar3D" xaxislabel="Fruit" yaxislabel="favorite">
    <cewolf:data>
        <cewolf:producer id="categoryData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="horizontalBarChart3D" renderer="/cewolf" width="300" height="300"/></TD>
</TR>
<TR>
<TD>
<cewolf:chart id="lineChart" title="LineChart" type="line" xaxislabel="Fruit" yaxislabel="favorite">
    <cewolf:data>
        <cewolf:producer id="categoryData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="lineChart" renderer="/cewolf" width="300" height="300"/>
</TD>
<TD>
<cewolf:chart id="stackedHorizontalBar" title="StackedHorizontalBar" type="stackedHorizontalBar" xaxislabel="Fruit" yaxislabel="favorite">
    <cewolf:data>
        <cewolf:producer id="categoryData" />
    </cewolf:data>
    <cewolf:chartpostprocessor id="dataColor">
        <cewolf:param name="0" value='<%= "#FFFFAA" %>'/>
        <cewolf:param name="1" value='<%= "#AAFFAA" %>'/>
        <cewolf:param name="2" value='<%= "#FFAAFF" %>'/>
        <cewolf:param name="3" value='<%= "#FFAAAA" %>'/>
    </cewolf:chartpostprocessor>
</cewolf:chart>
<cewolf:img chartid="stackedHorizontalBar" renderer="/cewolf" width="300" height="300"/>
</TD>
</TR>

<TR>
<TD>
<cewolf:chart id="stackedVerticalBar" title="StackedVerticalBar" type="stackedVerticalBar" xaxislabel="Fruit" yaxislabel="favorite">
    <cewolf:data>
        <cewolf:producer id="categoryData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="stackedVerticalBar" renderer="/cewolf" width="300" height="300"/>
</TD>
<TD>
<cewolf:chart id="stackedVerticalBar3D" title="StackedVerticalBar3D" type="stackedVerticalBar3D" xaxislabel="Fruit" yaxislabel="favorite">
    <cewolf:data>
        <cewolf:producer id="categoryData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="stackedVerticalBar3D" renderer="/cewolf" width="300" height="300"/>
</TD>
</TR>


<TR>
<TD>
<cewolf:chart
  id="verticalBar"
  title="VerticalBarChart"
  type="verticalBar"
  xaxislabel="Fruit"
  yaxislabel="favorite">
    <cewolf:data>
        <cewolf:producer id="categoryData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img
  chartid="verticalBar"
  renderer="/cewolf"
  width="300"
  height="300"/>
</TD>
<TD>
<cewolf:chart id="verticalBar3D" title="VerticalBar3D" type="verticalBar3D" xaxislabel="Fruit" yaxislabel="favorite">
    <cewolf:data>
        <cewolf:producer id="categoryData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="verticalBar3D" renderer="/cewolf" width="300" height="300"/>
</TD>
</TR>
<TR>
<TD>
<cewolf:chart id="candleStick" title="CandleStick" type="candleStick" xaxislabel="Time">
    <cewolf:data>
        <cewolf:producer id="highLowData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="candleStick" renderer="/cewolf" width="300" height="300"/>
</TD>
<TD>
<cewolf:chart id="highLow" title="HighLow" type="highLow" xaxislabel="Time">
    <cewolf:data>
        <cewolf:producer id="highLowData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="highLow" renderer="/cewolf" width="300" height="300"/>
</TD>
</TR>
<TR>
<TD>
<cewolf:chart id="gantt" title="Gantt" type="gantt" xaxislabel="Workflow">
    <cewolf:colorpaint color="#FFEEEE"/>
    <cewolf:data>
        <cewolf:producer id="ganttData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="gantt" renderer="/cewolf" width="300" height="300"/>
</TD>

</TR>
<TR>
<TD>
Separate legend before chart rendering<br>
<cewolf:chart id="verticalXY" title="VerticalXYBar" type="verticalXYBar" xaxislabel="Time" showlegend="false">
    <cewolf:data>
        <cewolf:producer id="timeData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:legend id="verticalXY" renderer="/cewolf" width="300" height="40" mime="image/png" /><br>
<cewolf:img chartid="verticalXY" renderer="/cewolf" width="300" height="300" />
</TD>
<TD>
Pie Chart with separate legend after chart rendering (tooltips)<br>
<cewolf:chart id="pie3d" title="Pie3D" type="pie3D" showlegend="false">
    <cewolf:data>
        <cewolf:producer id="pieData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="pie3d" renderer="/cewolf" width="300" height="300"   >
  <cewolf:map tooltipgeneratorid="pieToolTipGenerator"/>
</cewolf:img>
<br>
<cewolf:legend id="pie3d" renderer="/cewolf" width="300" height="40" />
</TD>
</TR>
<TR>
<TD>
Meter Chart<br>
<cewolf:chart id="meterChart" title="Speed" type="meter">
    <cewolf:data>
        <cewolf:producer id="meterData" />
    </cewolf:data>
   <cewolf:chartpostprocessor id="meterRanges">
   </cewolf:chartpostprocessor>
</cewolf:chart>
<cewolf:img chartid="meterChart" renderer="/cewolf" width="300" height="300" />
</TD>
<TD>
Stacked Area<br>
<cewolf:chart id="stackedArea" title="Stacked Area" type="stackedarea">
    <cewolf:data>
        <cewolf:producer id="categoryData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="stackedArea" renderer="/cewolf" width="300" height="300" />
</TD>
</TR>

<% // example showing how to add custom chart type via adding custom factory
  CewolfChartFactory.registerFactory(new CewolfChartFactory("CustomChartType") {
    public JFreeChart getChartInstance(String title, String xAxisLabel, String yAxisLabel, org.jfree.data.general.Dataset data)
            throws de.laures.cewolf.taglib.IncompatibleDatasetException {
            // well this is same as 'stackedarea' type
      check(data, CategoryDataset.class, this.chartType);
      JFreeChart chart = ChartFactory.createStackedAreaChart(title, xAxisLabel, yAxisLabel, (CategoryDataset) data, PlotOrientation.VERTICAL, true, false, false);
      // just to do something extra print out the process events:
      chart.addProgressListener(new ChartProgressListener(){
        public void chartProgress(ChartProgressEvent ev) {
                  System.out.print("Chart event on CustomChartType:");
          if (ev.getType() == ChartProgressEvent.DRAWING_STARTED) {
            System.out.println("DRAWING_STARTED");
          }
          if (ev.getType() == ChartProgressEvent.DRAWING_FINISHED) {
            System.out.println("DRAWING_FINISHED");
          }
              }
      });
      return chart;
      }
    });
 %>
<TR>
<TD>
Custom chart type<br>
<cewolf:chart id="customChartType" title="Custom chart type" type="CustomChartType">
    <cewolf:data>
        <cewolf:producer id="categoryData" />
    </cewolf:data>
</cewolf:chart>
<cewolf:img chartid="customChartType" renderer="/cewolf" width="300" height="300" />
</TD>
</TR>

</TABLE>
</body>
</html>
