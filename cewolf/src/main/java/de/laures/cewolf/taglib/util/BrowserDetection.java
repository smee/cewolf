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

import javax.servlet.http.HttpServletRequest;
/**
 * @author glaures
 */
public class BrowserDetection {

	private final static String USER_AGENT_KEY = "User-Agent";
	private final static String MSIE = "msie";

	private final static int IE = 0;
	private final static int OTHER = 1;

	public static final int getBrowser (HttpServletRequest request) {
		String agent = request.getHeader(USER_AGENT_KEY);
		if (agent == null || agent.toLowerCase().indexOf(MSIE) < 0) {
			return OTHER;
		} else {
			return IE;
		}
	}

	public static final boolean isIE (HttpServletRequest request){
		return getBrowser(request) == IE;
	}
}
