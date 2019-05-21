package hr.fer.zemris.java.hw16.jvdraw.model;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * List model that models a list of geometrical objects. Adapts a drawing model and 
 * notifies listeners of changes in the model. 
 * @author damjan
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to drawing model. 
	 */
	private DrawingModel model; 
	
	/**
	 * Default constructor. Registers this object as a listener to given drawing model. 
	 * @param model Drawing model. 
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(this);
	}
	@Override
	public int getSize() {
		return model.getSize(); 
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index); 
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		super.fireIntervalAdded(source, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		super.fireIntervalRemoved(source, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		super.fireContentsChanged(source, index0, index1);
	}

}
