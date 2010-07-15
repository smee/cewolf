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
 * either version 2.1 of
 * the License, or (at your option) any later version.
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

import java.awt.Image;
import java.awt.Paint;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.*;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.Dataset;
import org.jfree.ui.RectangleEdge;

import de.laures.cewolf.ChartHolder;
import de.laures.cewolf.ChartPostProcessor;
import de.laures.cewolf.ChartValidationException;
import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.PostProcessingException;
import de.laures.cewolf.event.ChartImageRenderListener;
import de.laures.cewolf.util.ImageHelper;
import de.laures.cewolf.util.RenderedImage;

/**
 * Serializable implementaton of a ChartDefinition.
 * @author glaures
 * @see de.laures.cewolf.ChartHolder
 */
public abstract class AbstractChartDefinition
		implements ChartHolder, Serializable, ChartImageRenderListener {

    protected String title;
	protected String xAxisLabel, yAxisLabel;
	protected String type;
    protected boolean xAxisInteger = false, yAxisInteger = false;
    protected boolean xTickMarksVisible = true, yTickMarksVisible = true;
    protected boolean xTickLabelsVisible = true, yTickLabelsVisible = true;
    protected boolean borderVisible = false;
    protected boolean plotBorderVisible = true;

    private boolean antialias = true;
    private String background;
    private float backgroundImageAlpha = 1.0f;
    private Paint backgroundPaint, plotBackgroundPaint, borderPaint, plotBorderPaint;

    private int legendAnchor = TaglibConstants.ANCHOR_SOUTH;
    protected boolean showLegend = true;

    private List postProcessors = new ArrayList();
    private List postProcessorsParams = new ArrayList();

    private JFreeChart chart;

	protected abstract JFreeChart produceChart() throws DatasetProduceException, ChartValidationException;
  
    //gets first legend in the list
    public LegendTitle getLegend()
    {
      //i need to find the legend now.
      LegendTitle legend = null;
      List subTitles = chart.getSubtitles();
      Iterator iter = subTitles.iterator();
      while (iter.hasNext())
      {
        Object o = iter.next();
        if (o instanceof LegendTitle)
        {
          legend = (LegendTitle) o;
          break;
        }
      }
      return legend;
    }

    //removes first legend in the list
    public void removeLegend()
    {
      List subTitles = chart.getSubtitles();
      Iterator iter = subTitles.iterator();
      while (iter.hasNext())
      {
        Object o = iter.next();
        if (o instanceof LegendTitle)
        {
          iter.remove();
          break;
        }
      }
    }

    /**
     * This method triggers the dataset and chart production. It is only
     * from outside if there is no cached image available in the the image cache.
     */
    public JFreeChart getChart() throws DatasetProduceException, ChartValidationException, PostProcessingException {
        if (chart == null) {
            chart = produceChart();
            chart.setAntiAlias(antialias);

            if (background != null) {
                Image image = ImageHelper.loadImage(background);
                chart.setBackgroundImage(image);
                chart.setBackgroundImageAlpha(backgroundImageAlpha);
            }

            if (backgroundPaint != null) {
                chart.setBackgroundPaint(backgroundPaint);
            }

            if (plotBackgroundPaint != null) {
                chart.getPlot().setBackgroundPaint(plotBackgroundPaint);
            }

            if (borderPaint != null) {
                chart.setBorderPaint(borderPaint);
            }

            if (plotBorderPaint != null) {
                chart.getPlot().setOutlinePaint(plotBorderPaint);
            }

			chart.setBorderVisible(borderVisible);;
			chart.getPlot().setOutlineVisible(plotBorderVisible);;

            if (showLegend) {
                LegendTitle legend = this.getLegend();
                switch (legendAnchor) 
                {
                    case TaglibConstants.ANCHOR_NORTH :
                        legend.setPosition(RectangleEdge.TOP);
                        break;
                    case TaglibConstants.ANCHOR_WEST :
						legend.setPosition(RectangleEdge.LEFT);
                        break;
                    case TaglibConstants.ANCHOR_EAST :
						legend.setPosition(RectangleEdge.RIGHT);
                        break;
                    default :
						legend.setPosition(RectangleEdge.BOTTOM);
                }
            } else {
              this.removeLegend();
            }

			Plot plot = chart.getPlot();
			if (plot instanceof CategoryPlot) {
				CategoryPlot cplot = (CategoryPlot) plot;

				for (int i=0; i<cplot.getRangeAxisCount(); i++) {
					ValueAxis axis = cplot.getRangeAxis(i);
					if (axis instanceof NumberAxis) {
						if (yAxisInteger)
							((NumberAxis) axis).setStandardTickUnits(NumberAxis.createIntegerTickUnits());
					}
					if (axis != null) {
						axis.setTickMarksVisible(yTickMarksVisible);
						axis.setTickLabelsVisible(yTickLabelsVisible);
					}
				}

				for (int i=0; i<cplot.getDomainAxisCount(); i++) {
					cplot.getDomainAxis(i).setTickMarksVisible(xTickMarksVisible);
					cplot.getDomainAxis(i).setTickLabelsVisible(xTickLabelsVisible);
				}
			} else if (plot instanceof FastScatterPlot) {
				FastScatterPlot fsplot = (FastScatterPlot) plot;

				ValueAxis axis = fsplot.getDomainAxis();
				if (axis instanceof NumberAxis) {
					if (xAxisInteger)
						((NumberAxis) axis).setStandardTickUnits(NumberAxis.createIntegerTickUnits());
				}
				if (axis != null) {
					axis.setTickMarksVisible(xTickMarksVisible);
					axis.setTickLabelsVisible(xTickLabelsVisible);
				}

				axis = fsplot.getRangeAxis();
				if (axis instanceof NumberAxis) {
					if (yAxisInteger)
						((NumberAxis) axis).setStandardTickUnits(NumberAxis.createIntegerTickUnits());
				}
				if (axis != null) {
					axis.setTickMarksVisible(yTickMarksVisible);
				}
			} else if (plot instanceof ThermometerPlot) {
				ValueAxis axis = ((ThermometerPlot) plot).getRangeAxis();
				if (axis instanceof NumberAxis) {
					if (yAxisInteger)
						((NumberAxis) axis).setStandardTickUnits(NumberAxis.createIntegerTickUnits());
				}
				if (axis != null) {
					axis.setTickMarksVisible(yTickMarksVisible);
					axis.setTickLabelsVisible(yTickLabelsVisible);
				}
			} else if (plot instanceof XYPlot) {
				XYPlot xyplot = (XYPlot) plot;

				for (int i=0; i<xyplot.getRangeAxisCount(); i++) {
					ValueAxis axis = xyplot.getRangeAxis(i);
					if (axis instanceof NumberAxis) {
						if (yAxisInteger)
							((NumberAxis) axis).setStandardTickUnits(NumberAxis.createIntegerTickUnits());
					}
					if (axis != null) {
						axis.setTickMarksVisible(yTickMarksVisible);
						axis.setTickLabelsVisible(yTickLabelsVisible);
					}
				}

				for (int i=0; i<xyplot.getDomainAxisCount(); i++) {
					ValueAxis axis = xyplot.getDomainAxis(i);
					if (axis instanceof NumberAxis) {
						if (xAxisInteger)
							((NumberAxis) axis).setStandardTickUnits(NumberAxis.createIntegerTickUnits());
					}
					if (axis != null) {
						axis.setTickMarksVisible(xTickMarksVisible);
						axis.setTickLabelsVisible(xTickLabelsVisible);
					}
				}
			}

            // postProcessing
			for (int i = 0; i < postProcessors.size(); i++) {
				ChartPostProcessor cpp = (ChartPostProcessor) postProcessors.get(i);
				try {
					cpp.processChart(chart, (Map) postProcessorsParams.get(i));
				} catch (Throwable t) {
					t.printStackTrace();
					throw new PostProcessingException(t.getClass().getName() + " raised by post processor '" +
							cpp + "'.\nPost processing of this post processor " + "has been ignored.");
				}
			}
        }
        return chart;
    }

    /**
     * Sets the antialias.
     * @param antialias The antialias to set
     */
    public void setAntialias (boolean antialias) {
        this.antialias = antialias;
    }

    /**
     * Sets the background.
     * @param background The background to set
     */
    public void setBackground (String background) {
        this.background = background;
    }

    /**
     * Sets the backgroundImageAlpha.
     * @param backgroundImageAlpha The backgroundImageAlpha to set
     */
    public void setBackgroundImageAlpha (float backgroundImageAlpha) {
        this.backgroundImageAlpha = backgroundImageAlpha;
    }

    /**
     * Sets the legendAnchor.
     * @param legendAnchor The legendAnchor to set
     */
    public void setLegendAnchor (int legendAnchor) {
        this.legendAnchor = legendAnchor;
    }

    /**
     * Sets the background paint.
     * @param paint The background paint to set
     */
    public void setBackgroundPaint (Paint paint) {
        this.backgroundPaint = paint;
    }

    /**
     * Sets the plot's background paint.
     * @param paint The plot's background paint to set
     */
    public void setPlotBackgroundPaint (Paint paint) {
        this.plotBackgroundPaint = paint;
    }

    /**
     * Sets the showLegend.
     * @param showLegend The showLegend to set
     */
    public void setShowLegend (boolean showLegend) {
        this.showLegend = showLegend;
    }

    /**
     * Sets the title.
     * @param title The title to set
     */
    public void setTitle (String title) {
        this.title = title;
    }

    /**
     * Sets the type.
     * @param type The type to set
     */
    public void setType (String type) {
        this.type = type;
    }

    /**
     * Sets the xAxisLabel.
     * @param xAxisLabel The xAxisLabel to set
     */
    public void setXAxisLabel (String xAxisLabel) {
        this.xAxisLabel = xAxisLabel;
    }

    /**
     * Sets the yAxisLabel.
     * @param yAxisLabel The yAxisLabel to set
     */
    public void setYAxisLabel (String yAxisLabel) {
        this.yAxisLabel = yAxisLabel;
    }

    /**
     * Whether the domain (X) axis should show integer values only.
     */
    public void setXaxisinteger (boolean xAxisInteger) {
        this.xAxisInteger = xAxisInteger;
    }

    /**
     * Whether the range (Y) axis should show integer values only.
     */
    public void setYaxisinteger (boolean yAxisInteger) {
        this.yAxisInteger = yAxisInteger;
    }

    /**
     * Whether the domain (X) axis should show any tick marks.
     */
    public void setXtickmarksvisible (boolean xTickMarksVisible) {
        this.xTickMarksVisible = xTickMarksVisible;
    }

    /**
     * Whether the range (Y) axis should show any tick marks.
     */
    public void setYtickmarksvisible (boolean yTickMarksVisible) {
        this.yTickMarksVisible = yTickMarksVisible;
    }

    /**
     * Whether the domain (X) axis should show any tick labels.
     */
    public void setXticklabelsvisible (boolean xTickLabelsVisible) {
        this.xTickLabelsVisible = xTickLabelsVisible;
    }

    /**
     * Whether the range (Y) axis should show any tick labels.
     */
    public void setYticklabelsvisible (boolean yTickLabelsVisible) {
        this.yTickLabelsVisible = yTickLabelsVisible;
    }

    /**
     * Whether the chart is drawn with a border.
     */
    public void setBorderVisible (boolean borderVisible) {
        this.borderVisible = borderVisible;
    }

    /**
     * Whether the plot is drawn with a border.
     */
    public void setPlotBorderVisible (boolean plotBorderVisible) {
        this.plotBorderVisible = plotBorderVisible;
    }

    /**
     * Sets the chart border paint.
     */
    public void setBorderPaint (Paint paint) {
        this.borderPaint = paint;
    }

    /**
     * Sets the plot border paint.
     */
    public void setPlotBorderPaint (Paint paint) {
        this.plotBorderPaint = paint;
    }

    public void addPostProcessor (ChartPostProcessor cpp) {
        postProcessors.add(cpp);
    }

    public void addPostProcessorParams (Map params) {
        postProcessorsParams.add(params);
    }

	/**
	 * Callback right after a new image gets rendered.
	 * Implemented so if postprocessors implement the ImageRenderListener interface then they will be called back also
	 * 
	 * @param renderedImage The fresh image just got rendered
	 */
	public void onImageRendered (RenderedImage renderedImage) {
		// if the postprocessor implements ImageRenderListener interface call it!
        for (int i = 0; i < postProcessors.size(); i++) {
            ChartPostProcessor cpp = (ChartPostProcessor)postProcessors.get(i);
            if (cpp instanceof ChartImageRenderListener) {
            	((ChartImageRenderListener) cpp).onImageRendered(renderedImage);
            }
        }
	}
}
