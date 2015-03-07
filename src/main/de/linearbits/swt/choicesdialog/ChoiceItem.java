/*******************************************************************************
 * Copyright (c) 2015 Fabian Prasser
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Fabian Prasser (fabian.prasser at gmail dot com) - initial API and implementation
 *******************************************************************************/
package de.linearbits.swt.choicesdialog;

/**
 * Item for the choices dialog
 * @author Fabian Prasser
 */
public class ChoiceItem {

    /** Text*/
    private String text;
    /** Tooltip text*/
    private String tooltipText;
    
    /**
     * Creates a new instance
     * @param text
     * @param tooltipText
     */
    public ChoiceItem(String text, String tooltipText) {
        checkNull(text);
        checkNull(tooltipText);
        this.text = text;
        this.tooltipText = tooltipText;
    }

    /**
     * Returns the text
     */
    public String getText() {
        return text;
    }
    
    /**
     * Returns the tooltip text
     */
    public String getTooltipText() {
        return tooltipText;
    }
    
    /**
     * Sets the text
     */
    public void setText(String text) {
        checkNull(text);
        this.text = text;
    }
    
    /**
     * Sets the tooltip text
     */
    public void setTooltipText(String tooltipText) {
        checkNull(tooltipText);
        this.tooltipText = tooltipText;
    }
    
    /**
     * Checks for null arguments
     * @param object
     */
    private void checkNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Null is not a valid argument");
        }
    }
}
