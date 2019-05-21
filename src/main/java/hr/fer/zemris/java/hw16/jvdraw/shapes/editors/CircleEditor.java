package hr.fer.zemris.java.hw16.jvdraw.shapes.editors;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;

/**
 * Editor for a circle. 
 * @author damjan
 *
 */
public class CircleEditor extends GeometricalObjectEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Reference to circle. 
	 */
	private Circle circle; 
	
	/**
	 * Field for center x value. 
	 */
	private JTextField centerXField;
	
	/**
	 * Field for center y value. 
	 */
	private JTextField centerYField;
	
	/**
	 * Field for radius value. 
	 */
	private JTextField radiusField; 
	
	/**
	 * Color area for picking line color. 
	 */
	private JColorArea colorArea; 
	
	/**
	 * Center x value. 
	 */
	private int centerX;
	
	/**
	 * Center y value. 
	 */
	private int centerY;
	
	/**
	 * Radius value. 
	 */
	private int radius; 
	
	/**
	 * Default constructor. 
	 * @param circle Circle to be edited. 
	 */
	public CircleEditor(Circle circle) {
		this.circle = circle;
		initGUI(); 
	}

	/**
	 * Helper method that initializes the editor GUI. 
	 */
	private void initGUI() {
		if (circle instanceof FilledCircle) {
			setLayout(new GridLayout(4, 3)); 			
		} else {
			setLayout(new GridLayout(3, 3)); 
		}
		add(new JLabel("Set center x and y:")); 
		centerXField = new JTextField(); 
		centerXField.setText(Integer.toString(circle.getCenterX()));
		centerYField = new JTextField(); 
		centerYField.setText(Integer.toString(circle.getCenterY()));
		add(centerXField); 
		add(centerYField); 
		
		add(new JLabel("Set radius:")); 
		radiusField = new JTextField(); 
		radiusField.setText(Integer.toString(circle.getRadius()));
		add(radiusField); 
		add(new JLabel("")); 
		
		add(new JLabel("Set line color:"));
		colorArea = new JColorArea(circle.getColor()); 
		add(colorArea);
		add(new JLabel("")); 
	}
	
	
	@Override
	public void checkEditing() {  
		centerX = Integer.parseInt(centerXField.getText()); 
		centerY = Integer.parseInt(centerYField.getText()); 
		radius = Integer.parseInt(radiusField.getText());  
		if (radius < 0) throw new IllegalArgumentException("Radius must be positive."); 
	}

	@Override
	public void acceptEditing() {
		circle.setColor(colorArea.getCurrentColor());
		circle.setCenterX(centerX);
		circle.setCenterY(centerY);
		circle.setRadius(radius);
	}

}
