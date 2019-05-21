package hr.fer.zemris.java.hw16.jvdraw.shapes.visitors;

import java.awt.Color;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;

/**
 * Geometrical object visitor that can turn each object into its textual representation,
 * that can then be written into a .jvd file and read later. 
 * @author damjan
 *
 */
public class GeometricalObjectWriter implements GeometricalObjectVisitor {

	/**
	 * List of strings representing objects that have been visited so far. 
	 */
	private List<String> text; 
	
	/**
	 * Default constructor. 
	 * @param text List of strings that this visitor will fill by visiting objects.
	 */
	public GeometricalObjectWriter(List<String> text) {
		this.text= text;
	}
	
	@Override
	public void visit(Line line) {
		text.add(
				String.format("LINE %d %d %d %d %d %d %d", 
						line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY(),
						line.getColor().getRed(), line.getColor().getGreen(), line.getColor().getBlue())
		);
	}

	@Override
	public void visit(Circle circle) {
		text.add(
				String.format("CIRCLE %d %d %d %d %d %d", 
						circle.getCenterX(), circle.getCenterY(), circle.getRadius(),
						circle.getColor().getRed(), circle.getColor().getGreen(), circle.getColor().getBlue())
		);
	}

	@Override
	public void visit(FilledCircle circle) {
		Color fc = circle.getFillColor(); 
		Color lc = circle.getLineColor(); 
		text.add(
				String.format("FCIRCLE %d %d %d %d %d %d %d %d %d", 
						circle.getCenterX(), circle.getCenterY(), circle.getRadius(),
						lc.getRed(), lc.getGreen(), lc.getBlue(), 
						fc.getRed(), fc.getGreen(), fc.getBlue())
		);
	}

}
