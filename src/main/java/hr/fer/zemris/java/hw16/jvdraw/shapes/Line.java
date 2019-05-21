package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.shapes.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.shapes.editors.LineEditor;
import hr.fer.zemris.java.hw16.jvdraw.shapes.visitors.GeometricalObjectVisitor;

/**
 * Line with its starting point, ending point and color. 
 * @author damjan
 *
 */
public class Line extends GeometricalObject {

	/**
	 * x-coordinate of starting point. 
	 */
	private int startX;
	
	/**
	 * y-coordinate of starting point. 
	 */
	private int startY; 
	
	/**
	 * x-coordinate of ending point. 
	 */
	private int endX; 
	
	/**
	 * y-coordinate of ending point. 
	 */
	private int endY; 
	
	/**
	 * Color.
	 */
	private Color color; 
	
	/**
	 * Default constructor. 
	 * @param startX Starting point x. 
	 * @param startY Starting point y. 
	 * @param endX Ending point x. 
	 * @param endY Ending point y. 
	 * @param color Color of line. 
	 */
	public Line(int startX, int startY, int endX, int endY, Color color) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.color = color;
	}
	
	/**
	 * Constructor that parses all of line's parameters from a string of format
	 * "{@code LINE startx starty endx endy red green blue}"
	 * @param fileLine Line to be parsed. 
	 * @throws IllegalArgumentException If line is not formatted correctly. 
	 */
	public Line(String fileLine) throws IllegalArgumentException {
		String[] args = fileLine.split("\\s+"); 
		try {
			startX = Integer.parseInt(args[1]); 
			startY = Integer.parseInt(args[2]); 
			endX = Integer.parseInt(args[3]); 
			endY = Integer.parseInt(args[4]);
			color = new Color(
					Integer.parseInt(args[5]), 
					Integer.parseInt(args[6]), 
					Integer.parseInt(args[7]));
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid format for LINE: " + fileLine); 
		}
	}
	
	/**
	 * Returns starting point x. 
	 * @return Starting point x. 
	 */
	public int getStartX() {
		return startX;
	}

	/**
	 * Returns starting point y. 
	 * @return Starting point y. 
	 */
	public int getStartY() {
		return startY;
	}
	
	/**
	 * Returns ending point x.  
	 * @return Ending point x. 
	 */
	public int getEndX() {
		return endX;
	}

	/**
	 * Returns ending point y.
	 * @return Ending point y. 
	 */
	public int getEndY() {
		return endY;
	}

	/**
	 * Returns line color. 
	 * @return Color. 
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets starting x. 
	 * @param startX Starting x. 
	 */
	public void setStartX(int startX) {
		this.startX = startX;
		fire(); 
	}

	/**
	 * Sets starting y. 
	 * @param startY Starting y. 
	 */
	public void setStartY(int startY) {
		this.startY = startY;
		fire(); 
	}

	/**
	 * Sets ending x. 
	 * @param endX Ending x. 
	 */
	public void setEndX(int endX) {
		this.endX = endX;
		fire(); 
	}
	
	/**
	 * Sets ending y. 
	 * @param endY Ending y. 
	 */
	public void setEndY(int endY) {
		this.endY = endY;
		fire(); 
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
		return new LineEditor(this); 
	}
	
	@Override
	public String toString() {
		return String.format("Line (%d,%d) - (%d,%d)", startX, startY, endX, endY); 
	}
}
