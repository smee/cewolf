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

package de.laures.cewolf.taglib.html;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

/**
 * HTML img tag.
 * @author  Guido Laures
 */
public class HTMLImgTag extends AbstractHTMLBaseTag implements Serializable{
	
	private final static String TAG_NAME = "IMG";
    
    /** Holds value of property width. */
    protected int width = UNDEFINED_INT;
    
    /** Holds value of property height. */
    protected int height = UNDEFINED_INT;
    
    /** Holds value of property src. */
    protected String src = UNDEFINED_STR;
    
    /** Holds value of property alt. */
    protected String alt = "";
    
    /** Holds value of property longDesc. */
    protected String longDesc = UNDEFINED_STR;
    
    /** Holds value of property useMap. */
    protected String useMap = UNDEFINED_STR;
    
    /** Holds value of property ismap. */
    protected String ismap = UNDEFINED_STR;
    
    /** Holds value of property align. */
    protected String align = UNDEFINED_STR;
    
    /** Holds value of property border. */
    protected int border = 0;
    
    /** Holds value of property hSpace. */
    protected int hSpace = UNDEFINED_INT;
    
    /** Holds value of property vSpace. */
    protected int vSpace = UNDEFINED_INT;
    
    /*
     public void writeTag(Writer writer) throws IOException {
        writer.write("<img ");
        writeAttributes(writer);
        writer.write("/>");
    }
     **/
    
    public void writeAttributes(Writer wr){
        try {
            super.writeAttributes(wr);
            appendAttributeDeclaration(wr, this.border, "BORDER");
            appendAttributeDeclaration(wr, this.hSpace, "HSPACE");
            appendAttributeDeclaration(wr, this.height, "HEIGHT");
            appendAttributeDeclaration(wr, this.vSpace, "VSPACE");
            appendAttributeDeclaration(wr, this.width, "WIDTH");
            appendAttributeDeclaration(wr, this.align, "ALIGN");
            appendAttributeDeclaration(wr, this.alt, "ALT");
            appendAttributeDeclaration(wr, this.ismap, "ISMAP");
            appendAttributeDeclaration(wr, this.longDesc, "LONGDESC");
            appendAttributeDeclaration(wr, this.src, "SRC");
            appendAttributeDeclaration(wr, this.useMap, "USEMAP");
        } catch(IOException ioex){
            ioex.printStackTrace();
        }
    }
    
    protected void reset(){
        // width = UNDEFINED_INT;
        // height = UNDEFINED_INT;
        src = UNDEFINED_STR;
        alt = "";
        longDesc = UNDEFINED_STR;
        useMap = UNDEFINED_STR;
        ismap = UNDEFINED_STR;
        align = UNDEFINED_STR;
        border = 0;
        hSpace = UNDEFINED_INT;
        vSpace = UNDEFINED_INT;
        super.reset();
    }
    
    /** Setter for property width.
     * @param width New value of property width.
     */
    public void setWidth(int width) {
        this.width = width;
    }
    
    /** Setter for property height.
     * @param height New value of property height.
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    /** Setter for property src.
     * @param src New value of property src.
     */
    public void setSrc(String src) {
        this.src = src;
    }
    
    /** Setter for property alt.
     * @param alt New value of property alt.
     */
    public void setAlt(String alt) {
        this.alt = alt;
    }
    
    /** Setter for property longDesc.
     * @param longDesc New value of property longDesc.
     */
    public void setLongdesc(String longDesc) {
        this.longDesc = longDesc;
    }

    /** Setter for property useMap.
     * @param useMap New value of property useMap.
     */
    public void setUsemap(String useMap) {
        this.useMap = useMap;
    }
    
    /** Setter for property ismap.
     * @param ismap New value of property ismap.
     */
    public void setIsmap(String ismap) {
        this.ismap = ismap;
    }
    
    /** Setter for property align.
     * @param align New value of property align.
     */
    public void setAlign(String align) {
        this.align = align;
    }
    
    /** Setter for property border.
     * @param border New value of property border.
     */
    public void setBorder(int border) {
        this.border = border;
    }
    
    /** Setter for property hSpace.
     * @param hSpace New value of property hSpace.
     */
    public void setHspace(int hSpace) {
        this.hSpace = hSpace;
    }
    
    /** Setter for property vSpace.
     * @param vSpace New value of property vSpace.
     */
    public void setVspace(int vSpace) {
        this.vSpace = vSpace;
    }
    
    protected String getTagName() {
        return TAG_NAME;
    }
    
    protected boolean hasBody() {
        return false;
    }
    
    protected boolean wellFormed() {
        return false;
    }
    
}
