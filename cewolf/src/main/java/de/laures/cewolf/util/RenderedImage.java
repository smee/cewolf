package de.laures.cewolf.util;

/**
 * @author glaures
 */
public class RenderedImage {

	public final byte[] data;
	public final String mimeType;
	public final transient Object renderingInfo;

	public RenderedImage(byte[] data, String mimeType, Object renderingInfo) {
		this.data = data;
		this.mimeType = mimeType;
		this.renderingInfo = renderingInfo;
	}

}
