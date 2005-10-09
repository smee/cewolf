package de.laures.cewolf.taglib;

import java.util.Map;

import de.laures.cewolf.DatasetProducer;

/**
 * @author glaures
 */
public interface DataAware {
	
	public void setDataProductionConfig(DatasetProducer dsp, Map params, boolean useCache);
}
