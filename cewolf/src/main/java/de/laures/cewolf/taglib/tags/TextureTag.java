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
 * either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 */

package de.laures.cewolf.taglib.tags;

import javax.servlet.jsp.JspException;


/** 
 * Tag &lt;texture&gt; which defines a texture paint.
 * @author  Guido Laures 
 */
public class TextureTag extends CewolfBodyTag {

	static final long serialVersionUID = -3062291419098213756L;

    private String image;
    private int width;
    private int height;

    public int doEndTag() throws JspException {
        String realFileName = pageContext.getServletContext().getRealPath(image);
        ((Painted)getParent()).setPaint(new SerializableTexturePaint(realFileName, width, height));
        return doAfterEndTag(EVAL_PAGE);
    }

    protected void reset() {
    }

    public void setImage(String s) {
        this.image = s;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
