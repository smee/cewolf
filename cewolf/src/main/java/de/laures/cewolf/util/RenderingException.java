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

package de.laures.cewolf.util;

import de.laures.cewolf.CewolfException;

/**
 * Thrown when an error during rendering occured.
 * @author glaures
 *
 */
public class RenderingException extends CewolfException {

	/**
	 * Constructor for RenderingException.
	 */
	public RenderingException() {
		super();
	}

	/**
	 * Constructor for RenderingException.
	 * @param msg
	 */
	public RenderingException(String msg) {
		super(msg);
	}

	public RenderingException(Throwable ex){
		super(ex.getMessage());
	}
}
