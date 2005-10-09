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

package de.laures.cewolf.taglib.util;

import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author glaures
 */
public class DatasetProductionTimeStore extends Hashtable {

    private static final DatasetProductionTimeStore instance = new DatasetProductionTimeStore();
    private static final Log log = LogFactory.getLog(DatasetProductionTimeStore.class);
    public static final DatasetProductionTimeStore getInstance() {
        return instance;
    }

    private DatasetProductionTimeStore() {
    }

    public boolean containsEntry(String producerId, Map params) {
        return containsKey(new DatasetProductionTimesKey(producerId, params));
    }

    public void addEntry(String producerId, Map params, Date produceTime) {
      log.debug("add entry: " + producerId);
        put(new DatasetProductionTimesKey(producerId, params), produceTime);
    }

    public void removeEntry(String producerId, Map params) {
      log.debug("remove entry: " + producerId);
      
        remove(new DatasetProductionTimesKey(producerId, params));
    }

    public Date getProductionTime(String producerId, Map params) {
        return (Date) get(new DatasetProductionTimesKey(producerId, params));
    }

    public String paramsToString(Map params){
    	Iterator it = params.keySet().iterator();
    	StringBuffer buf = new StringBuffer("[");
    	while(it.hasNext()){
    		String key = (String)it.next();
    		buf.append(key + ":" + params.get(key));
    	}
    	buf.append("]");
    	return buf.toString();
    }
    		
}
