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
 * Contains some base constants to avoid explicit dependancy to concrete chart 
 * implementation's constant values. The constants of this class also serve as
 * the base contract for data exchange between sub packages.
 * @author  Guido Laures
 */
public interface WebConstants {

    /**
     * The key for the session ID.
     */
	String SESSIONID_KEY = "jsessionid";

    /**
     * Escape of an Ampersand in HTML
     */
	String AMPERSAND = "&amp;";

    /**
     * The image parameter key.
     */
    String IMG_PARAM = "img";

    /**
     * The key for the width of the image.
     */
    String WIDTH_PARAM = "width";

    /**
     * The key for the height of the image.
     */
    String HEIGHT_PARAM = "height";
    
      /**
     * Remove image from Storage after rendering
     */
    String REMOVE_AFTER_RENDERING = "removeAfterRendering";

    /**
     * MIME name of a PNG image.
     */
  	String MIME_PNG = "image/png";

    /**
     * MIME name of a JPEG image.
     */
  	String MIME_JPEG = "image/jpeg";
 
    /**
     * MIME name of a SVG image
     */
	String MIME_SVG = "image/svg+xml";
  
}
