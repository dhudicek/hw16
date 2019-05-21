package hr.fer.zemris.java.hw16.jvdraw.shapes.visitors;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;

/**
 * Implementation of {@linkGeometricalObjectVisitor} that paints visited objects
 * using Graphics2D. 
 * @author damjan
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {
	
	/**
	 * Graphics used for painting shapes. 
	 */
	private Graphics2D g2d; 
	
	/**
	 * Default constructor. 
	 * @param g2d {@link Graphics2D}
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}

	@Override
	public void visit(Line line) {
		int startX = line.getStartX();
		int startY = line.getStartY(); 
		int endX = line.getEndX(); 
		int endY = line.getEndY(); 
		g2d.setColor(line.getColor());
		g2d.drawLine(startX, startY, endX, endY);
	}

	@Override
	public void visit(Circle circle) {
		int radius = circle.getRadius();
		int cornerX = circle.getCenterX() - radius; 
		int cornerY = circle.getCenterY() - radius; 
		
		g2d.setColor(circle.getColor());
		g2d.drawOval(cornerX, cornerY, 2*radius, 2*radius); 
	}

	@Override
	public void visit(FilledCircle circle) {
		visit((Circle) circle); 
		
		int radius = circle.getRadius(); 
		int cornerX = circle.getCenterX() - radius; 
		int cornerY = circle.getCenterY() - radius;  
		
		g2d.setColor(circle.getFillColor());
		g2d.fillOval(cornerX, cornerY, 2*radius, 2*radius);
	}
}
