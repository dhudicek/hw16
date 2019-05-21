package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.shapes.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.shapes.visitors.GeometricalObjectVisitor;

/**
 * Abstract class representing a geometrical object as a Subject in Observer pattern. 
 * @author damjan
 *
 */
public abstract class GeometricalObject {
	
	/**
	 * List of listeners. 
	 */
	private List<GeometricalObjectListener> listeners = new ArrayList<>(); 

	/**
	 * Method used to accept a visitor. 
	 * @param v Visitor. 
	 */
	public abstract void accept(GeometricalObjectVisitor v);
	
	/**
	 * Creates a specific implementation of a geometrical object editor, 
	 * depending on the type of the geometrical object. 
	 * @return Geometrical object editor. 
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();
	
	/**
	 * Adds a listener. 
	 * @param l Listener. 
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l); 
	}
	
	/**
	 * Removes a listener.
	 * @param l Listener. 
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l); 
	}
	
	/**
	 * Notifies all listeners that the object has changed.
	 */
	protected void fire() {
		listeners.forEach((l) -> l.geometricalObjectChanged(this));
	}
}
