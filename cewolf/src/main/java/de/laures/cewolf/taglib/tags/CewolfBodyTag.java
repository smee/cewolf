package de.laures.cewolf.taglib.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author glaures
 */
public abstract class CewolfBodyTag extends BodyTagSupport {

	protected final int doAfterEndTag(int returnVal) {
		reset();
		return returnVal;
	}

	protected abstract void reset();

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException {
		return doAfterEndTag(super.doEndTag());
	}

}
