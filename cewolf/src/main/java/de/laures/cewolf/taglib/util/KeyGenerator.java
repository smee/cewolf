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

import java.io.IOException;
import java.io.Serializable;
import java.rmi.MarshalledObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.laures.cewolf.CewolfRuntimeException;

/**
 * @author  Guido Laures
 */
public abstract class KeyGenerator {
    
    private static final Log log = LogFactory.getLog(KeyGenerator.class);

    /**
     * Special exception thrown when key not found
     * @author zluspai
     */
    private static class NoKeyException extends CewolfRuntimeException {
		public NoKeyException(String msg) {
			super(msg);
		}
    	
		public NoKeyException(String msg, Exception cause) {
			super(msg, cause);
		}
    }

    public static int generateKey(Serializable obj) {
        if (obj == null) {
            throw new NoKeyException("assertion failed: can not generate key for null,");
        }
        try {
            MarshalledObject mo = new MarshalledObject(obj);
            return mo.hashCode();
        } catch (IOException ioex) {
            log.error("IOException during key generation KeyGenerator.generateKey()", ioex);
            throw new NoKeyException(obj + " is not serializable.", ioex);
        }
    }
}
