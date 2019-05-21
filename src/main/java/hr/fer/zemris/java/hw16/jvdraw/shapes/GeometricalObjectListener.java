package hr.fer.zemris.java.hw16.jvdraw.shapes;

/**
 * Interface representing a geometrical object listener that is notified any time
 * the object has been changed. 
 * @author damjan
 *
 */
public interface GeometricalObjectListener {
	
	/**
	 * Called by object when it changes. 
	 * @param o Geometrical object that changed. 
	 */
	public void geometricalObjectChanged(GeometricalObject o); 
}
