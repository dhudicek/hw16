package hr.fer.zemris.java.hw16.jvdraw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObjectListener;

/**
 * Implementation of DrawingModel. 
 * @author damjan
 *
 */
public class DrawingModelImpl implements DrawingModel, GeometricalObjectListener {

	/**
	 * List of geometrical objects. 
	 */
	private List<GeometricalObject> objects;
	
	/**
	 * List of listeners. 
	 */
	private List<DrawingModelListener> listeners;
	
	/**
	 * Default constructor. 
	 */
	public DrawingModelImpl() {
		objects = new ArrayList<>();
		listeners = new ArrayList<>(); 
	}
	@Override
	public int getSize() {
		return objects.size(); 
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject o) {
		objects.add(o); 
		o.addGeometricalObjectListener(this);
		listeners.forEach((l) -> l.objectsAdded(this, objects.indexOf(o), objects.indexOf(o)));
	}

	@Override
	public void remove(GeometricalObject o) {
		o.removeGeometricalObjectListener(this);
		int index = objects.indexOf(o); 
		objects.remove(o);  
		listeners.forEach((l) -> l.objectsRemoved(this, index, index));
	}

	@Override
	public void changeOrder(GeometricalObject o, int offset) {
		int index = objects.indexOf(o);
		int newIndex = index + offset; 
		if (newIndex >= objects.size() || newIndex < 0) return;
		
		objects.remove(o); 
		ListIterator<GeometricalObject> iter = objects.listIterator(newIndex); 
		iter.add(o);
		
		listeners.forEach((l) -> l.objectsChanged(this, newIndex, newIndex)); 
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l); 
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l); 
	}
	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		int index = objects.indexOf(o); 
		listeners.forEach((l) -> l.objectsChanged(this, index, index));
	}

}
