package hr.fer.zemris.java.hw16.jvdraw.shapes.editors;

import javax.swing.JPanel;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * Abstract class that represents a {@link GeometricalObject} editor. Draws a panel 
 * that offers tools to change object's parameters. 
 * @author damjan
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Method that checks if current input in all fields is valid and throws 
	 * an exception otherwise. 
	 */
	public abstract void checkEditing(); 
	
	/**
	 * Method that updates the object with new values.  
	 */
	public abstract void acceptEditing(); 
}
