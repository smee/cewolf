/**
 *  Copyright (c) 2005, Cambridge Technology Partners
 *  
 *	$Id: ChartImageRenderListener.java,v 1.1 2005/10/09 22:05:16 brianfox Exp $
 */
package de.laures.cewolf.event;

import de.laures.cewolf.util.RenderedImage;

/**
 * Event listener called back after the image is rendered. ChartPostProcessors
 * may implement this interface to get notified when the image gets rendered.
 * It is useful to retrieve the JFreeChart's ChartRenderingInfo object.
 * 
 * <pre>
 *	public void onImageRendered (RenderedImage renderedImage) {
 * 		ChartRenderingInfo info = (ChartRenderingInfo) renderedImage.renderingInfo;
 * 		...
 *  }
 * </pre>
 * 
 * @author zluspai
 */
public interface ChartImageRenderListener {

	/**
	 * Callback right after a new image gets rendered.
	 * 
	 * @param renderedImage The fresh image just got rendered
	 */
	public void onImageRendered (RenderedImage renderedImage);

}
