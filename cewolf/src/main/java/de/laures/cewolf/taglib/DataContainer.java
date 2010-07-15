/*
 * Created on 13.04.2003
 */
package de.laures.cewolf.taglib;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.jfree.data.general.Dataset;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.taglib.util.DatasetProductionTimeStore;
import de.laures.cewolf.taglib.util.KeyGenerator;
import de.laures.cewolf.util.Assert;

/**
 * @author guido
 */
public class DataContainer implements DataAware, Serializable {

	static final long serialVersionUID = 8018224798228025683L;

    private transient Dataset data;
    private transient DatasetProducer producer;

    private Map datasetProductionParams;
    private long datasetProduceTime;
    private boolean useCache = true;

    public void setDataProductionConfig(DatasetProducer dsp, Map params, boolean useCache) {
        producer = dsp;
        datasetProductionParams = params;
        this.useCache = useCache;
        checkDataProductionNeed();
    }

    public Dataset getDataset() throws DatasetProduceException {
        Assert.check(producer != null, "you need to specifiy a producer for the data of the chart.");
        if (data == null) {
            data = (Dataset) producer.produceDataset(datasetProductionParams);
            DatasetProductionTimeStore dataCache = DatasetProductionTimeStore.getInstance();
            dataCache.addEntry(producer.getProducerId(), datasetProductionParams, new Date(datasetProduceTime));
        }
        Assert.check(data != null, "your producer of type " + producer.getClass().getName() + " produced a null dataset.");
        return data;
    }

    /**
     * This method checks if there has been a dataset production 
     * for the same DatasetProvider and parameters. If so the DatasetProducer
     * is consulted to check the expiry of this data.
     * If the data has expired the retrieval of a cached image of this
     * ChartDefinition is avoided by setting the datasetProduceTime to the
     * actual time. After this the hash code of this object can not be
     * present in the image cache and so a new image with new data will be rendered.
     * If the data did not expire the last dataset production time is stored
     * as a memeber to reach the same hash code for this object as the one before if possible.
     * This method is called during serialization to ensure the same serialized
     * representation of this and a eventually formally stored object.
     */
    private void checkDataProductionNeed() {
        final String prodId = producer.getProducerId();
        DatasetProductionTimeStore dataCache = DatasetProductionTimeStore.getInstance();
        if (useCache && dataCache.containsEntry(prodId, datasetProductionParams)) {
            Date produceTime = dataCache.getProductionTime(prodId, datasetProductionParams);
            // cached data available
            if (!producer.hasExpired(datasetProductionParams, produceTime)) {
                this.datasetProduceTime = produceTime.getTime();
                return;
            }
            dataCache.removeEntry(prodId, datasetProductionParams);
        }
        datasetProduceTime = System.currentTimeMillis();
    }

}
