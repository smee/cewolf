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

package de.laures.cewolf.example;

import de.laures.cewolf.WebConstants;

/**
 * Parameter bean used by the testpage JSP.  
 * @author  Guido Laures 
 */
public class ParamBean implements java.io.Serializable, WebConstants {

    /** Holds value of property width. */
    private int width = 300;

    /** Holds value of property height. */
    private int height = 300;

    /** Holds value of property antialias. */
    private boolean antialias = true;

    /** Holds value of property title. */
    private String title = "";

    /** Holds value of property minVal. */
    private int minVal = -100;

    /** Holds value of property maxVal. */
    private int maxVal = 100;

    /** Holds value of property colorColor. */
    private String colorColor = "#FFFFFF";

    /** Holds value of property selectedPaint. */
    private String paint = "color";

    /** Holds value of property gradientColor1. */
    private String gradientColor1 = "#FFFFFF";

    /** Holds value of property gradientX1. */
    private int gradientX1;

    /** Holds value of property gradientX2. */
    private int gradientX2;

    /** Holds value of property gradientColor2. */
    private String gradientColor2 = "#FFFFFF";

    /** Holds value of property gradientY2. */
    private int gradientY2;

    /** Holds value of property gradientY1. */
    private int gradientY1;

    /** Holds value of property textureImage. */
    private String textureImage = "";

    /** Holds value of property textureWidth. */
    private int textureWidth = 50;

    /** Holds value of property textureHeight. */
    private int textureHeight = 50;

    /** Holds value of property cyclic. */
    private boolean cyclic;

    /** Holds value of property legend. */
    private boolean legend;

    /** Holds value of property legendAnchor. */
    private String legendAnchor = "south";

    /** Holds value of property extraTitle. */
    private String extraTitle;
    
    private String mimeType = MIME_PNG;

    /**
     * Getter for property width.
     * @return Value of property width.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Setter for property width.
     * @param width New value of property width.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Getter for property height.
     * @return Value of property height.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Setter for property height.
     * @param height New value of property height.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Setter for property antialias.
     * @param antialias New value of property antialias.
     */
    public void setAntialias(boolean antialias) {
        // this.antialias = "on".equals(antialias);
        this.antialias = antialias;
    }

    public boolean getAntialias() {
        return antialias;
    }

    /**
     * Getter for property title.
     * @return Value of property title.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Setter for property title.
     * @param title New value of property title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for property minVal.
     * @return Value of property minVal.
     */
    public int getMinVal() {
        return this.minVal;
    }

    /**
     * Setter for property minVal.
     * @param minVal New value of property minVal.
     */
    public void setMinVal(int minVal) {
        this.minVal = minVal;
    }

    /**
     * Getter for property maxVal.
     * @return Value of property maxVal.
     */
    public int getMaxVal() {
        return this.maxVal;
    }

    /**
     * Setter for property maxVal.
     * @param maxVal New value of property maxVal.
     */
    public void setMaxVal(int maxVal) {
        this.maxVal = maxVal;
    }

    /**
     * Getter for property colorColor.
     * @return Value of property colorColor.
     */
    public String getColorColor() {
        return this.colorColor;
    }

    /**
     * Setter for property colorColor.
     * @param colorColor New value of property colorColor.
     */
    public void setColorColor(String colorColor) {
        this.colorColor = colorColor;
    }

    /**
     * Getter for property selectedPaint.
     * @return Value of property selectedPaint.
     */
    public String getPaint() {
        return this.paint;
    }

    /**
     * Setter for property selectedPaint.
     * @param selectedPaint New value of property selectedPaint.
     */
    public void setPaint(String selectedPaint) {
        this.paint = selectedPaint;
    }

    public boolean isSelectedPaint(String p) {
        return paint.equalsIgnoreCase(p);
    }

    /**
     * Getter for property gradientColor1.
     * @return Value of property gradientColor1.
     */
    public String getGradientColor1() {
        return this.gradientColor1;
    }

    /**
     * Setter for property gradientColor1.
     * @param gradientColor1 New value of property gradientColor1.
     */
    public void setGradientColor1(String gradientColor1) {
        this.gradientColor1 = gradientColor1;
    }

    /**
     * Getter for property gradientX1.
     * @return Value of property gradientX1.
     */
    public int getGradientX1() {
        return this.gradientX1;
    }

    /**
     * Setter for property gradientX1.
     * @param gradientX1 New value of property gradientX1.
     */
    public void setGradientX1(int gradientX1) {
        this.gradientX1 = gradientX1;
    }

    /**
     * Getter for property gradientX2.
     * @return Value of property gradientX2.
     */
    public int getGradientX2() {
        return this.gradientX2;
    }

    /**
     * Setter for property gradientX2.
     * @param gradientX2 New value of property gradientX2.
     */
    public void setGradientX2(int gradientX2) {
        this.gradientX2 = gradientX2;
    }

    /**
     * Getter for property gradientColor2.
     * @return Value of property gradientColor2.
     */
    public String getGradientColor2() {
        return this.gradientColor2;
    }

    /**
     * Setter for property gradientColor2.
     * @param gradientColor2 New value of property gradientColor2.
     */
    public void setGradientColor2(String gradientColor2) {
        this.gradientColor2 = gradientColor2;
    }

    /**
     * Getter for property gradientY2.
     * @return Value of property gradientY2.
     */
    public int getGradientY2() {
        return this.gradientY2;
    }

    /**
     * Setter for property gradientY2.
     * @param gradientY2 New value of property gradientY2.
     */
    public void setGradientY2(int gradientY2) {
        this.gradientY2 = gradientY2;
    }

    /**
     * Getter for property gradientY1.
     * @return Value of property gradientY1.
     */
    public int getGradientY1() {
        return this.gradientY1;
    }

    /**
     * Setter for property gradientY1.
     * @param gradientY1 New value of property gradientY1.
     */
    public void setGradientY1(int gradientY1) {
        this.gradientY1 = gradientY1;
    }

    /**
     * Getter for property textureImage.
     * @return Value of property textureImage.
     */
    public String getTextureImage() {
        return this.textureImage;
    }

    /**
     * Setter for property textureImage.
     * @param textureImage New value of property textureImage.
     */
    public void setTextureImage(String textureImage) {
        this.textureImage = textureImage;
    }

    /**
     * Getter for property textureWidth.
     * @return Value of property textureWidth.
     */
    public int getTextureWidth() {
        return this.textureWidth;
    }

    /**
     * Setter for property textureWidth.
     * @param textureWidth New value of property textureWidth.
     */
    public void setTextureWidth(int textureWidth) {
        this.textureWidth = textureWidth;
    }

    /**
     * Getter for property textureHeight.
     * @return Value of property textureHeight.
     */
    public int getTextureHeight() {
        return this.textureHeight;
    }

    /**
     * Setter for property textureHeight.
     * @param textureHeight New value of property textureHeight.
     */
    public void setTextureHeight(int textureHeight) {
        this.textureHeight = textureHeight;
    }

    /**
     * Getter for property cyclic.
     * @return Value of property cyclic.
     */
    public boolean getCyclic() {
        return this.cyclic;
    }

    /**
     * Setter for property cyclic.
     * @param cyclic New value of property cyclic.
     */
    public void setCyclic(boolean cyclic) {
        this.cyclic = cyclic;
    }

    /**
     * Getter for property legend.
     * @return Value of property legend.
     */
    public boolean isLegend() {
        return this.legend;
    }

    /**
     * Setter for property legend.
     * @param legend New value of property legend.
     */
    public void setLegend(boolean legend) {
        this.legend = legend;
    }

    /**
     * Getter for property legendAnchor.
     * @return Value of property legendAnchor.
     */
    public String getLegendAnchor() {
        return this.legendAnchor;
    }

    /**
     * Setter for property legendAnchor.
     * @param legendAnchor New value of property legendAnchor.
     */
    public void setLegendAnchor(String legendAnchor) {
        this.legendAnchor = legendAnchor;
    }

    public boolean isLegendAnchorSelected(String anchor) {
        return this.legendAnchor.equalsIgnoreCase(anchor);
    }

    /**
     * Getter for property extraTitle.
     * @return Value of property extraTitle.
     */
    public String getExtraTitle() {
        return (extraTitle == null? "" : extraTitle);
    }

    /**
     * Setter for property extraTitle.
     * @param extraTitle New value of property extraTitle.
     */
    public void setExtraTitle(String extraTitle) {
        this.extraTitle = extraTitle;
    }

	/**
	 * Returns the mimeType.
	 * @return String
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * Sets the mimeType.
	 * @param mimeType The mimeType to set
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

}
