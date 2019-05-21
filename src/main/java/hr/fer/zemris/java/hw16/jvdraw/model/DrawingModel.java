package hr.fer.zemris.java.hw16.jvdraw.model;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * Interface that models a drawing. Contains a list of {@linkGeometricalObject} elements and 
 * provides methods for adding, removing and changing order of elements. Also provides 
 * methods for adding and removing listeners that it notifies whenever the model changes.
 * @author damjan
 *
 */
public interface DrawingModel {
	
	/**
	 * Returns the number of objects in the model. 
	 * @return Number of objects. 
	 */
	public int getSize(); 
	
	/**
	 * Returns object at given index. 
	 * @param index Index of object. 
	 * @return Geometrical object. 
	 * @throws IndexOutOfBoundsException If given index is out of bounds. 
	 */
	public GeometricalObject getObject(int index); 
	
	/**
	 * Adds a geometrical object to the model and notifies listeners.  
	 * @param o Geometrical object. 
	 */
	public void add(GeometricalObject o);
	
	/**
	 * Removes a geometrical object from the model and notifies listeners. 
	 * @param o Geometrical object. 
	 */
	public void remove(GeometricalObject o); 
	
	/**
	 * Changes the index of geometrical object to index + offset. Does nothing 
	 * if given offset would put the object out of index bounds.  
	 * @param o Geometrical object. 
	 * @param offset Offset. 
	 */
	public void changeOrder(GeometricalObject o, int offset); 
	
	/**
	 * Adds a listener.
	 * @param l Listener. 
	 */
	public void addDrawingModelListener(DrawingModelListener l);
	
	/**
	 * Removes a listener.
	 * @param l Listener. 
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}
