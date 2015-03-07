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

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Choices dialog
 * @author Fabian Prasser
 */
public class ChoicesDialog {

    /** Parent shell */
    private final Shell  parent;
    /** Title */
    private String       title         = "";
    /** Message */
    private String       message       = "";
    /** Image */
    private Image        image         = null;
    /** Items */
    private ChoiceItem[] choices;
    /** Choice */
    private int          choice        = -1;
    /** Style */
    private final int    style;
    /** Show arrows */
    private boolean      showArrows    = true;
    /** Default choice*/
    private ChoiceItem   defaultChoice = null;

    /**
     * Creates a new instance. Accepted styles are
     * APPLICATION_MODAL, PRIMARY_MODAL, SYSTEM_MODAL, or MODELESS
     * 
     * @param parent
     * @param style
     */
    public ChoicesDialog(Shell parent, int style) {
        this.checkStyle(style);
        this.parent = parent;
        this.style = style;
    }
    
    /**
     * Returns the choices
     */
    public ChoiceItem[] getChoices() {
        return choices;
    }

    /**
     * Returns the image
     */
    public Image getImage() {
        return image;
    }
    
    /**
     * Returns the message
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Returns the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns whether arrows are shown
     */
    public boolean isShowArrows() {
        return showArrows;
    }
    
    /**
     * Opens this dialog
     * @return The index of the selected choice, -1 if the shell was closed before a choice was made
     */
    public int open() {
        
        // Shell
        Display display = parent.getDisplay();
        final Shell shell = new Shell(parent, style | SWT.TITLE | SWT.CLOSE | SWT.BORDER);
        GridLayout layout = new GridLayout(2, false);
        layout.marginBottom = 15;
        layout.marginTop = 10;
        layout.marginLeft = 10;
        layout.marginRight = 15;
        shell.setLayout(layout);
        shell.setText(title);
        
        // Image
        if (this.image != null) {
            Label lblImage = new Label(shell, SWT.NONE);
            lblImage.setImage(this.image);
            lblImage.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
        }
        
        // Message
        Label lblMessage = new Label(shell, SWT.NONE);
        lblMessage.setText(this.message);
        lblMessage.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, this.image != null ? 1 : 2, 1));
        
        // Icon
        final Image imageArrow;
        if (showArrows) {
            imageArrow = new Image(display, this.getClass().getResourceAsStream("arrow.png"));
            shell.addDisposeListener(new DisposeListener(){
                @Override
                public void widgetDisposed(DisposeEvent arg0) {
                    if (imageArrow != null && !imageArrow.isDisposed()) {
                        imageArrow.dispose();
                    }
                }
            });
        } else {
            imageArrow = null;
        }
        
        // Choices
        int index = 0;
        for (ChoiceItem item : choices) {
            
            // Arrow
            if (this.showArrows) {
                Label lblImage = new Label(shell, SWT.NONE);
                lblImage.setImage(imageArrow);
                lblImage.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
            }
            
            // Item
            Button btnChoice = new Button(shell, SWT.PUSH);
            btnChoice.setText(item.getText());
            btnChoice.setToolTipText(item.getTooltipText());
            btnChoice.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, showArrows ? 1 : 2, 1));
            
            // Select default choice
            if (item == defaultChoice) {
                btnChoice.setFocus();
            }
            
            // Listener
            final int choice = index++;
            btnChoice.addSelectionListener(new SelectionListener() {
                @Override
                public void widgetDefaultSelected(SelectionEvent arg0) {
                    widgetSelected(arg0);
                }
                @Override
                public void widgetSelected(SelectionEvent arg0) {
                   ChoicesDialog.this.choice = choice;
                   shell.close();
                }
            });
        }

        shell.pack();
        center(shell);
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        return choice;
    }
    
    /**
     * Sets the choices
     * @param choices
     */
    public void setChoices(ChoiceItem... choices) {
        checkNull(choices);
        if (choices.length < 1) {
            throw new IllegalArgumentException("You must provide at least one choice");
        }
        this.choices = choices;
    }
    
    /**
     * Returns the default choice
     */
    public ChoiceItem setDefaultChoice() {
        return this.defaultChoice;
    }
    
    /**
     * Sets the default choice
     * @param item
     */
    public void setDefaultChoice(ChoiceItem item) {
        this.defaultChoice = item;
    }
    
    /**
     * Sets the image
     * @param image
     */
    public void setImage(Image image) {
        this.image = image;
    }
    
    /**
     * Sets the message
     * @param message
     */
    public void setMessage(String message) {
        this.checkNull(title);
        this.message = message;
    }

    /**
     * Sets whether arrows are shown
     * @param show
     */
    public void setShowArrows(boolean show) {
        this.showArrows = show;
    }

    /**
     * Sets the title
     * @param title
     */
    public void setTitle(String title) {
        this.checkNull(title);
        this.title = title;
    }

    /**
     * Centers the shell within its parent shell
     * @param shell
     */
    private void center(Shell shell) {
        
        // Init
        Shell parent = (Shell)shell.getParent();
        Rectangle bounds = parent.getBounds();
        Point size = shell.getSize();
        
        // Compute
        int x = bounds.x + bounds.width / 2 - size.x / 2;
        int y = bounds.y + bounds.height / 2 - size.y / 2;
        
        // Set
        shell.setLocation(x, y);
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

    /**
     * SWT protocol
     * @param style
     */
    private void checkStyle(int style) {
        if ((style & ~(SWT.APPLICATION_MODAL | SWT.PRIMARY_MODAL | SWT.SYSTEM_MODAL | SWT.MODELESS)) != 0) {
            throw new SWTException("Unsupported style");
        }
        if (Integer.bitCount(style) > 1) {
            throw new SWTException("Unsupports only one of APPLICATION_MODAL, PRIMARY_MODAL, SYSTEM_MODAL or SWT.MODELESS");
        }
    }
}
