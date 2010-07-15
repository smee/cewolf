package de.laures.cewolf.util;

import java.io.Serializable;

import org.jfree.chart.ChartRenderingInfo;

/**
 * @author glaures
 */
public class RenderedImage implements Serializable {

	static final long serialVersionUID = -8445285130734561561L;

	public final byte[] data;
	public final String mimeType;
	public final ChartRenderingInfo renderingInfo;

	public RenderedImage (byte[] data, String mimeType, ChartRenderingInfo renderingInfo) {
		this.data = data;
		this.mimeType = mimeType;
		this.renderingInfo = renderingInfo;
	}

}
