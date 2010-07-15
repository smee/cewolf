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

/**
 * @author glaures
 */
public class DatasetProductionTimeStore extends Hashtable {

	static final long serialVersionUID = -9086317504718166157L;

    private static final DatasetProductionTimeStore instance = new DatasetProductionTimeStore();

    public static final DatasetProductionTimeStore getInstance() {
        return instance;
    }

    private DatasetProductionTimeStore() {
    }

    public boolean containsEntry (String producerId, Map params) {
        return containsKey(new DatasetProductionTimesKey(producerId, params));
    }

    public void addEntry (String producerId, Map params, Date produceTime) {
        put(new DatasetProductionTimesKey(producerId, params), produceTime);
    }

    public void removeEntry (String producerId, Map params) {
        remove(new DatasetProductionTimesKey(producerId, params));
    }

    public Date getProductionTime (String producerId, Map params) {
        return (Date) get(new DatasetProductionTimesKey(producerId, params));
    }

    public String paramsToString (Map params){
    	Iterator it = params.entrySet().iterator();
    	StringBuffer buf = new StringBuffer("[");
    	while (it.hasNext()) {
    		Map.Entry entry = (Map.Entry) it.next();
    		buf.append(entry.getKey() + ":" + entry.getValue());
    	}
    	buf.append("]");
    	return buf.toString();
    }

}
