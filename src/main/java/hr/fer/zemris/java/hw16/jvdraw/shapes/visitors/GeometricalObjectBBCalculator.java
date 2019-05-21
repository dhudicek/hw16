package hr.fer.zemris.java.hw16.jvdraw.shapes.visitors;

import java.awt.Rectangle;

import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;

/**
 * Geometrical object visitor implementation that calculates the bounding box 
 * dimensions for the group of objects that it visits. 
 * @author damjan
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	/**
	 * Miminum x-coordinate. 
	 */
	private int minX; 
	
	/**
	 * Minimum y-coordinate.
	 */ 
	private int minY;
	
	/**
	 * Maximum x-coordinate.
	 */
	private int maxX; 
	
	/**
	 * Maximum y-coordinate.
	 */
	private int maxY; 
	
	/**
	 * Flag that's set to true if the visitor hasn't visited any objects yet. 
	 */
	private boolean empty; 
	
	/**
	 * Default constructor. 
	 */
	public GeometricalObjectBBCalculator() {
		empty = true;
	}
	
	@Override
	public void visit(Line line) {
		int givenMinX = Math.min(line.getStartX(), line.getEndX()); 
		int givenMinY = Math.min(line.getStartY(), line.getEndY()); 
		int givenMaxX = Math.max(line.getStartX(), line.getEndX());
		int givenMaxY = Math.max(line.getStartY(), line.getEndY()); 
		calculate(givenMinX, givenMinY, givenMaxX, givenMaxY); 
	}

	@Override
	public void visit(Circle circle) {
		int centerX = circle.getCenterX(); 
		int centerY = circle.getCenterY(); 
		int radius = circle.getRadius();
		int givenMinX = centerX - radius;
		int givenMinY = centerY - radius;  
		int givenMaxX = centerX + radius;  
		int givenMaxY = centerY + radius;
		calculate(givenMinX, givenMinY, givenMaxX, givenMaxY); 
	}

	@Override
	public void visit(FilledCircle circle) {
		visit((Circle)circle);  
	}
	
	/**
	 * Helper method that calculates the new bounds using top left and bottom right 
	 * corner coordinates for a certain graphical object. 
	 * @param givenMinX Object's minimum x. 
	 * @param givenMinY Object's minimum y. 
	 * @param givenMaxX Object's maximum x. 
	 * @param givenMaxY Object's maximum y. 
	 */
	private void calculate(int givenMinX, int givenMinY, int givenMaxX, int givenMaxY) {
		if (empty) {
			empty = false; 
			minX = givenMinX; 
			minY = givenMinY; 
			maxX = givenMaxX; 
			maxY = givenMaxY; 
		} else {
			minX = Math.min(minX, givenMinX);
			minY = Math.min(minY, givenMinY); 
			maxX = Math.max(maxX, givenMaxX); 
			maxY = Math.max(maxY, givenMaxY);
		}
	}

	/**
	 * Returns the bounding box that contains all objects that this visitor has visited. 
	 * @return Bounding box. 
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle(minX, minY, maxX - minX, maxY - minY); 
	}
}
