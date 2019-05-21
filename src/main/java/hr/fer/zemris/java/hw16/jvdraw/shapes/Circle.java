package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.shapes.editors.CircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.shapes.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.shapes.visitors.GeometricalObjectVisitor;

/**
 * Circle with center coordinates, radius and color. 
 * @author damjan
 *
 */
public class Circle extends GeometricalObject {
	
	/**
	 * x-coordinate of center. 
	 */
	private int centerX; 
	
	/**
	 * y-coordinate of center. 
	 */
	private int centerY; 
	
	/**
	 * Radius. 
	 */
	private int radius;
	
	/**
	 * Line color. 
	 */
	private Color color; 

	/**
	 * Default constructor. 
	 * @param centerX Center's x. 
	 * @param centerY Center's y. 
	 * @param radius Radius. 
	 * @param color Line color. 
	 */
	public Circle(int centerX, int centerY, int radius, Color color) {
		super();
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
		this.color = color;
	}
	
	/**
	 * Constructor that parses all of circle's parameters from a line of format 
	 * "{@code CIRCLE centerX centerY radius red green blue}"
	 * @param fileLine Line describing a circle. 
	 * @throws IllegalArgumentException If incorrectly formatted. 
	 */
	public Circle(String fileLine) throws IllegalArgumentException {
		String[] args = fileLine.split("\\s+"); 
		try {
			centerX = Integer.parseInt(args[1]);
			centerY = Integer.parseInt(args[2]); 
			radius = Integer.parseInt(args[3]); 
			color = new Color(
					Integer.parseInt(args[4]),
					Integer.parseInt(args[5]), 
					Integer.parseInt(args[6])
			);
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid format for CIRCLE: " + fileLine); 
		}
	}

	/**
	 * Returns center's x-coordinate. 
	 * @return Center x. 
	 */
	public int getCenterX() {
		return centerX;
	}

	/**
	 * Sets center's x-coorrdinate. 
	 * @param centerX Center x. 
	 */
	public void setCenterX(int centerX) {
		this.centerX = centerX;
		fire(); 
	}

	/**
	 * Returns center's y-coordinate. 
	 * @return Center's y. 
	 */
	public int getCenterY() {
		return centerY;
	}

	/**
	 * Sets center's y-coordinate.
	 * @param centerY Center's y. 
	 */
	public void setCenterY(int centerY) {
		this.centerY = centerY;
		fire(); 
	}

	/**
	 * Returns radius. 
	 * @return Radius. 
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Sets radius. 
	 * @param radius Radius. 
	 */
	public void setRadius(int radius) {
		this.radius = radius;
		fire(); 
	}

	/**
	 * Returns color. 
	 * @return Color. 
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets color. 
	 * @param color Color. 
	 */
	public void setColor(Color color) {
		this.color = color;
		fire(); 
	}



	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this); 
	}
	
	@Override
	public String toString() {
		return String.format("Circle (%d,%d), %d", centerX, centerY, radius); 
	}

}
