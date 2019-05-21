package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.shapes.editors.FilledCircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.shapes.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.shapes.visitors.GeometricalObjectVisitor;

/**
 * Sublclass of Circle that also has a fill color. 
 * @author damjan 
 *
 */
public class FilledCircle extends Circle {
	
	/**
	 * Fill color. 
	 */
	private Color fillColor; 

	/**
	 * Default constructor.
	 * @param centerX Center's x. 
	 * @param centerY Center's y. 
	 * @param radius Radius. 
	 * @param lineColor Line color. 
	 * @param fillColor Fill color. 
	 */
	public FilledCircle(int centerX, int centerY, int radius, Color lineColor, Color fillColor) {
		super(centerX, centerY, radius, lineColor); 
		this.fillColor = fillColor;
	}
	
	/**
	 * Constructor that parses info from a string formatted as: 
	 * "{@code FCIRCLE centerX centerY radius red green blue red green blue}", where first set 
	 * of RGB values represents outline color, and second set represents fill color. 
	 * @param fileLine File line. 
	 * @throws IllegalArgumentException If line is not formatted correctly. 
	 */
	public FilledCircle(String fileLine) throws IllegalArgumentException {
		super(fileLine);
		String[] args = fileLine.split("\\s+"); 
		try {
			fillColor = new Color(
					Integer.parseInt(args[7]),
					Integer.parseInt(args[8]),
					Integer.parseInt(args[9])
			);  
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid format for FCIRCLE: " + fileLine); 
		}
	}

	/**
	 * Returns line color. 
	 * @return Line color. 
	 */
	public Color getLineColor() {
		return super.getColor(); 
	}

	/**
	 * Sets line color. 
	 * @param lineColor Line color. 
	 */
	public void setLineColor(Color lineColor) {
		super.setColor(lineColor);
	}

	/**
	 * Returns fill color. 
	 * @return Fill color. 
	 */
	public Color getFillColor() {
		return fillColor;
	}

	/**
	 * Sets new fill color. 
	 * @param fillColor Fill color. 
	 */
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
		fire();
	}



	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this); 
	}
	
	@Override
	public String toString() { 
		return String.format("Filled circle (%d,%d), %d, #%s", getCenterX(), getCenterY(), getRadius(), Integer.toHexString(fillColor.getRGB()).substring(2)); 
	}

}
