package hr.fer.zemris.java.hw16.jvdraw.shapes.editors;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;

/**
 * Editor for lines, offering tools to edit start and end points and color of the line.
 * @author damjan
 *
 */
public class LineEditor extends GeometricalObjectEditor {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Line that is edited. 
	 */
	private Line line; 
	
	/**
	 * Field for start x. 
	 */
	private JTextField startXField;
	
	/**
	 * Field for start y.
	 */
	private JTextField startYField;
	
	/**
	 * Field for end x. 
	 */
	private JTextField endXField;
	
	/**
	 * Field for end y. 
	 */
	private JTextField endYField;
	
	/**
	 * Color picker for changing line color. 
	 */
	private JColorArea colorArea; 
	
	/**
	 * Start x.
	 */
	private int startX;
	
	/**
	 * Start y. 
	 */
	private int startY;
	
	/**
	 * End x.
	 */
	private int endX;
	
	/**
	 * End y. 
	 */
	private int endY; 
	
	/**
	 * Default constructor. 
	 * @param line Line that is edited. 
	 */
	public LineEditor(Line line) {
		this.line = line;
		initGUI(); 
	}

	/**
	 * Helper method that initializes editor's GUI. 
	 */
	private void initGUI() {
		setLayout(new GridLayout(3, 3));
		JLabel startLabel = new JLabel("Starting x and y:"); 
		startXField = new JTextField(); 
		startXField.setText(Integer.toString(line.getStartX()));
		startYField = new JTextField(); 
		startYField.setText(Integer.toString(line.getStartY()));
		add(startLabel);
		add(startXField); 
		add(startYField);
		
		JLabel endLabel = new JLabel("Ending x and y:");
		endXField = new JTextField(); 
		endXField.setText(Integer.toString(line.getEndX()));
		endYField = new JTextField(); 
		endYField.setText(Integer.toString(line.getEndY()));
		add(endLabel); 
		add(endXField); 
		add(endYField); 
		
		JLabel colorLabel = new JLabel("Line color:"); 
		colorArea = new JColorArea(line.getColor()); 
		add(colorLabel); 
		add(colorArea); 
	}
	@Override
	public void checkEditing() {  
		startX = Integer.parseInt(startXField.getText()); 
		startY = Integer.parseInt(startYField.getText()); 
		endX = Integer.parseInt(endXField.getText()); 
		endY = Integer.parseInt(endYField.getText()); 
	}

	@Override
	public void acceptEditing() {
		line.setColor(colorArea.getCurrentColor());
		line.setStartX(startX);
		line.setStartY(startY);
		line.setEndX(endX);
		line.setEndY(endY);
	}

}
