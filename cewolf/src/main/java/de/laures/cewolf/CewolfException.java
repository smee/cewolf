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

package de.laures.cewolf;

/**
 * Base class of all Cewolf related exceptions
 * @author  Guido Laures
 */
public class CewolfException extends Exception {

    /** Creates a new instance of <code>CewolfException</code> without detail message. */
    public CewolfException() {
    }

    /**
     * Constructs an instance of <code>CewolfException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public CewolfException(String msg) {
        super(msg);
    }
    
    /**
     * Constructs exception with causing exception
     * @param msg message
     * @param cause cause exception
     */
    public CewolfException(String msg, Exception cause) {
        super(msg, cause);
    }
    
}
