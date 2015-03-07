SWT Choices Dialog
====

This project implements a dialog for SWT in which users may choose from one of several options. It is inspired by the 
DialogBox provided by the [Opal project](code.google.com/a/eclipselabs.org/p/opal/). In contrast to their implementation
the dialog provided by this project uses the native widgets of the respective platform and it supports keyboard interactions.

Example
------	

This basic example displays the a dialog box with three options:

```Java

// Create choices
ChoiceItem[] items = new ChoiceItem[]{
	new ChoiceItem("Exit and save my project", "Save your work in progress and exit the program"),
	new ChoiceItem("Exit and don't save", "Exit the program without saving your project"),
	new ChoiceItem("Don't exit", "Return to the program"),
};

// Create dialog
ChoicesDialog dialog = new ChoicesDialog(shell, SWT.APPLICATION_MODAL);

// Configure
dialog.setTitle("Exit");
dialog.setMessage("Do you really want to exit?");
dialog.setImage(Display.getCurrent().getSystemImage(SWT.ICON_QUESTION));
dialog.setChoices(items);
dialog.setDefaultChoice(2);

// Open
int choice = dialog.open();

// Cancel
if (choice == -1) {

// Choice selected, will be one of {0,1,2}
} else {

}
```

The above code will result in the following dialog:

[![Screenshot](https://raw.github.com/prasser/swtchoices/master/media/screenshot.png)](https://raw.github.com/prasser/swtchoices/master/media/screenshot.png)

Download
------
A binary version (JAR file) is available for download [here](https://rawgithub.com/prasser/swtchoices/master/jars/swtchoicesdialog-0.0.1.jar).

The according Javadoc is available for download [here](https://rawgithub.com/prasser/swtchoices/master/jars/swtchoicesdialog-0.0.1-doc.jar). 

Documentation
------
Online documentation can be found [here](https://rawgithub.com/prasser/swtchoices/master/doc/index.html).

License
------
EPL 1.0
