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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.LegendTitle;
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
public abstract class AbstractChartDefinition implements ChartHolder, Serializable, TaglibConstants, ChartImageRenderListener {
    
    protected static Log log = LogFactory.getLog(AbstractChartDefinition.class);

    protected String title;
	protected String xAxisLabel;
	protected String yAxisLabel;
	protected String type;

    private boolean antialias = true;
    private String background;
    private float backgroundImageAlpha = 1.0f;
    private Paint paint;

    private int legendAnchor = ANCHOR_SOUTH;
    private boolean showLegend = true;

    private transient List postProcessors = new ArrayList();
    private List postProcessorsParams = new ArrayList();

    private transient JFreeChart chart;
	
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
     * from outside if there is no cached image available in the the
     * image cache.
     */
    public Object getChart() throws DatasetProduceException, ChartValidationException, PostProcessingException {
        if (chart == null) {
            chart = produceChart();
            chart.setAntiAlias(antialias);
            if (background != null) {
                Image image = ImageHelper.loadImage(background);
                chart.setBackgroundImage(image);
                chart.setBackgroundImageAlpha(backgroundImageAlpha);
            }
            if (paint != null) {
                chart.setBackgroundPaint(paint);
            }
            if (showLegend) 
            {

                LegendTitle legend = this.getLegend();
                switch (legendAnchor) 
                {
                    case ANCHOR_NORTH :
                        legend.setPosition(RectangleEdge.TOP);
                        break;
                    case ANCHOR_WEST :
                      legend.setPosition(RectangleEdge.RIGHT);
                        break;
                    case ANCHOR_EAST :
                      legend.setPosition(RectangleEdge.LEFT);
                        break;
                    default :
                      legend.setPosition(RectangleEdge.BOTTOM);
                }
            } 
            else 
            {
              this.removeLegend();
            }
            // postProcessing
            for (int i = 0; i < postProcessors.size(); i++) {
                ChartPostProcessor pp = (ChartPostProcessor)postProcessors.get(i);
                try {
                    pp.processChart(chart, (Map)postProcessorsParams.get(i));
                } catch (Throwable t) {
                	log.error(t);
                    throw new PostProcessingException(t.getClass().getName() + " raised by post processor '" +
                    		pp + "'.\nPost processing of this post processor " + "has been ignored.");
                }
            }
        }
        return chart;
    }

    /**
     * Sets the antialias.
     * @param antialias The antialias to set
     */
    public void setAntialias(boolean antialias) {
        this.antialias = antialias;
    }

    /**
     * Sets the background.
     * @param background The background to set
     */
    public void setBackground(String background) {
        this.background = background;
    }

    /**
     * Sets the backgroundImageAlpha.
     * @param backgroundImageAlpha The backgroundImageAlpha to set
     */
    public void setBackgroundImageAlpha(float backgroundImageAlpha) {
        this.backgroundImageAlpha = backgroundImageAlpha;
    }

    /**
     * Sets the legendAnchor.
     * @param legendAnchor The legendAnchor to set
     */
    public void setLegendAnchor(int legendAnchor) {
        this.legendAnchor = legendAnchor;
    }

    /**
     * Sets the paint.
     * @param paint The paint to set
     */
    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    /**
     * Sets the showLegend.
     * @param showLegend The showLegend to set
     */
    public void setShowLegend(boolean showLegend) {
        this.showLegend = showLegend;
    }

    /**
     * Sets the title.
     * @param title The title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the type.
     * @param type The type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the xAxisLabel.
     * @param xAxisLabel The xAxisLabel to set
     */
    public void setXAxisLabel(String xAxisLabel) {
        this.xAxisLabel = xAxisLabel;
    }

    /**
     * Sets the yAxisLabel.
     * @param yAxisLabel The yAxisLabel to set
     */
    public void setYAxisLabel(String yAxisLabel) {
        this.yAxisLabel = yAxisLabel;
    }

    public void addPostProcessor(ChartPostProcessor pp) {
        postProcessors.add(pp);
    }

    public void addPostProcessorParams(Map params) {
        postProcessorsParams.add(params);
    }
    
	/**
	 * Callback right after a new image gets rendered.
	 * Implemented, so if postprocessors implement the ImageRenderListener interface
	 * then they will be called back also
	 * 
	 * @param renderedImage The fresh image just got rendered
	 */
	public void onImageRendered (RenderedImage renderedImage) {
		// if the postprocessor implements ImageRenderListener interface call it!
        for (int i = 0; i < postProcessors.size(); i++) {
            ChartPostProcessor pp = (ChartPostProcessor)postProcessors.get(i);
            if (pp instanceof ChartImageRenderListener) {
            	((ChartImageRenderListener) pp).onImageRendered(renderedImage);
            }
        }		
	}

}
