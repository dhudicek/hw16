package hr.fer.zemris.java.hw16.jvdraw.shapes.visitors;

import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;

/**
 * Interface that models a visitor that can visit specific geometrical objects and 
 * carry out actions depending on their type. 
 * @author damjan
 *
 */
public interface GeometricalObjectVisitor {
	
	/**
	 * Called when visiting a line. 
	 * @param line Line that is visited. 
	 */
	public abstract void visit(Line line);
	
	/**
	 * Called when visiting a circle. 
	 * @param circle Circle that is visited. 
	 */
	public abstract void visit(Circle circle);
	
	/**
	 * Called when visiting a filled circle. 
	 * @param circle Filled circle that is visite. 
	 */
	public abstract void visit(FilledCircle circle); 
}
