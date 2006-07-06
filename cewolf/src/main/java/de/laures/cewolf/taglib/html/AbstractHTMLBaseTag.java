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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.laures.cewolf.taglib.tags.CewolfBodyTag;

/**
 * Abstract base class which holds attribbutes common for all HTML 4.0 tags.
 * @author  Guido Laures
 */
public abstract class AbstractHTMLBaseTag extends CewolfBodyTag implements Serializable{
	
	private static final Log LOG = LogFactory.getLog(AbstractHTMLBaseTag.class);
	   
    protected static final int UNDEFINED_INT = -1;
    protected static final float UNDEFINED_FLOAT = -1.0f;
    protected static final String UNDEFINED_STR = null;
    
    /** Holds value of property ID. */
    protected String id = UNDEFINED_STR;
    
    /** Holds value of property clazz. */
    protected String clazz = UNDEFINED_STR;
    
    /** Holds value of property style. */
    protected String style = UNDEFINED_STR;
    
    /** Holds value of property title. */
    protected String title = UNDEFINED_STR;
    
    /** Holds value of property lang. */
    protected String lang = UNDEFINED_STR;
    
    /** Holds value of property dir. */
    protected String dir = UNDEFINED_STR;
    
    /** Holds value of property onclick. */
    protected String onclick = UNDEFINED_STR;
    
    /** Holds value of property ondbclick. */
    protected String ondbclick = UNDEFINED_STR;
    
    /** Holds value of property onmousedown. */
    protected String onmousedown = UNDEFINED_STR;
    
    /** Holds value of property onmouseup. */
    protected String onmouseup = UNDEFINED_STR;
    
    /** Holds value of property onmouseover. */
    protected String onmouseover = UNDEFINED_STR;
    
    /** Holds value of property onmousemove. */
    protected String onmousemove = UNDEFINED_STR;
    
    /** Holds value of property onmouseout. */
    protected String onmouseout = UNDEFINED_STR;
    
    /** Holds value of property onkeypress. */
    protected String onkeypress = UNDEFINED_STR;
    
    /** Holds value of property onkeydown. */
    protected String onkeydown = UNDEFINED_STR;
    
    /** Holds value of property onkeyup. */
    protected String onkeyup = UNDEFINED_STR;
    
    public AbstractHTMLBaseTag(){
    	setId(UNDEFINED_STR);
    }
    
    protected abstract String getTagName();
    
    protected abstract boolean hasBody();
    
    protected abstract boolean wellFormed();
    
    protected void reset(){
        this.clazz = UNDEFINED_STR;
        this.dir = UNDEFINED_STR;
        this.id = UNDEFINED_STR;
        this.lang = UNDEFINED_STR;
        this.onclick = UNDEFINED_STR;
        this.ondbclick = UNDEFINED_STR;
        this.onkeydown = UNDEFINED_STR;
        this.onkeypress = UNDEFINED_STR;
        this.onkeyup = UNDEFINED_STR;
        this.onmousedown = UNDEFINED_STR;
        this.onmousemove = UNDEFINED_STR;
        this.onmouseout = UNDEFINED_STR;
        this.onmouseover = UNDEFINED_STR;
        this.onmouseup = UNDEFINED_STR;
        this.style = UNDEFINED_STR;
        this.title = UNDEFINED_STR;
    }
    
    public int doStartTag() throws JspException {
        JspWriter writer = pageContext.getOut();
        try {
            writer.write("<" + getTagName());
            writeAttributes(writer);
            if(hasBody()){
                writer.write(">");
                return EVAL_PAGE;
            } else {
                return SKIP_BODY;
            }
        } catch(IOException ioe){
        	super.pageContext.getServletContext().log("",ioe);
            throw new JspException(ioe);
        }
    }
    
    public int doEndTag() throws JspException {
        JspWriter writer = pageContext.getOut();
        try{
            if(hasBody()){
                writer.write("</" + getTagName() + ">");
            } else {
                if(wellFormed()){
                    writer.write(" /");
                }
                writer.write(">");
            }
            return doAfterEndTag(EVAL_PAGE);
        } catch(IOException ioe){
        	super.pageContext.getServletContext().log("",ioe);
            throw new JspException(ioe);
        }
    }
    
    public void writeAttributes(Writer wr){
        try {
            appendAttributeDeclaration(wr, getId(), "ID");
            appendAttributeDeclaration(wr, this.clazz, "CLASS");
            appendAttributeDeclaration(wr, this.style, "STYLE");
            appendAttributeDeclaration(wr, this.onclick, "ONCLICK");
            appendAttributeDeclaration(wr, this.ondbclick, "ONDBCLICK");
            appendAttributeDeclaration(wr, this.onkeydown, "ONKEYDOWN");
            appendAttributeDeclaration(wr, this.onkeypress, "ONKEYPRESS");
            appendAttributeDeclaration(wr, this.onkeyup, "ONKEYUP");
            appendAttributeDeclaration(wr, this.onmousedown, "ONMOUSEDOWN");
            appendAttributeDeclaration(wr, this.onmousemove, "ONMOUSEMOVE");
            appendAttributeDeclaration(wr, this.onmouseout, "ONMOUSEOUT");
            appendAttributeDeclaration(wr, this.onmouseover, "ONMOUSEOVER");
            appendAttributeDeclaration(wr, this.onmouseup, "ONMOUSEUP");
            appendAttributeDeclaration(wr, this.title, "TITLE");
            appendAttributeDeclaration(wr, this.lang, "LANG");
            appendAttributeDeclaration(wr, this.dir, "DIR");
        } catch(IOException ioex){
        	LOG.info("Exception", ioex);
        }
    }
    
    protected void appendAttributeDeclaration(Writer wr, String attr, String name) throws IOException {
        if(attr != UNDEFINED_STR){
            wr.write(" " + name + "=\"" + attr + "\"");
        }
    }
    
    protected void appendAttributeDeclaration(Writer wr, int attr, String name) throws IOException {
        // does not call the according method for Strings to avoid additional
        // String instantiation
        if(attr != UNDEFINED_INT){
            wr.write(" " + name + "=\"" + attr + "\"");
        }
    }
    
    protected void appendAttributeDeclaration(Writer wr, float attr, String name) throws IOException {
        // does not call the according method for Strings to avoid additional
        // String instantiation
        if(attr != UNDEFINED_FLOAT){
            wr.write(" " + name + "=\"" + attr + "\"");
        }
    }
    
    /** Setter for property clazz.
     * @param clazz New value of property clazz.
     */
    public void setClass(String clazz) {
        this.clazz = clazz;
    }
    
    /** Setter for property style.
     * @param style New value of property style.
     */
    public void setStyle(String style) {
        this.style = style;
    }
    
    /** Setter for property title.
     * @param title New value of property title.
     */
    public void setHtmltitle(String title) {
        this.title = title;
    }
    
    /** Setter for property lang.
     * @param lang New value of property lang.
     */
    public void setLang(String lang) {
        this.lang = lang;
    }
    
    /** Setter for property dir.
     * @param dir New value of property dir.
     */
    public void setDir(String dir) {
        this.dir = dir;
    }
    
    /** Setter for property onclick.
     * @param onclick New value of property onclick.
     */
    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }
    
    /** Setter for property ondbclick.
     * @param ondbclick New value of property ondbclick.
     */
    public void setOndbclick(String ondbclick) {
        this.ondbclick = ondbclick;
    }
    
    /** Setter for property onmousedown.
     * @param onmousedown New value of property onmousedown.
     */
    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }
    
    /** Setter for property onmouseup.
     * @param onmouseup New value of property onmouseup.
     */
    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
    }
    
    /** Setter for property onmouseover.
     * @param onmouseover New value of property onmouseover.
     */
    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }
    
    /** Setter for property onmousemove.
     * @param onmousemove New value of property onmousemove.
     */
    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
    }
    
    /** Setter for property onmouseout.
     * @param onmouseout New value of property onmouseout.
     */
    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }
    
    /** Setter for property onkeypress.
     * @param onkeypress New value of property onkeypress.
     */
    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }
    
    /** Setter for property onkeydown.
     * @param onkeydown New value of property onkeydown.
     */
    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }
    
    /** Setter for property onkeyup.
     * @param onkeyup New value of property onkeyup.
     */
    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }
    
}
