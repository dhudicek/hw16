package hr.fer.zemris.java.hw16.jvdraw.model;

/**
 * Interface implemented by listeners to DrawingModel changes. 
 * @author damjan
 *
 */
public interface DrawingModelListener {
	
	/**
	 * Called when objects were added to the model. 
	 * @param source Drawing model. 
	 * @param index0 Starting index, inclusive. 
	 * @param index1 Ending index, inclusive. 
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);
	
	/**
	 * Called when objects were removed from the model. 
	 * @param source Drawing model. 
	 * @param index0 Starting index, inclusive. 
	 * @param index1 Ending index, inclusive. 
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);
	
	/**
	 * Called when objects in the model were modified. 
	 * @param source Drawing model. 
	 * @param index0 Starting index, inclusive. 
	 * @param index1 Ending index, inclusive. 
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1); 
}
