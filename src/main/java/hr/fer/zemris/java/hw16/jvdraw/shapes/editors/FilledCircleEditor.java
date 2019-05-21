package hr.fer.zemris.java.hw16.jvdraw.shapes.editors;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw16.jvdraw.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;

/**
 * Extension of CircleEditor that adds editing of fill color. 
 * @author damjan
 *
 */
public class FilledCircleEditor extends CircleEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Filled circle that is edited. 
	 */
	private FilledCircle filledCircle; 

	/**
	 * Used to choose a fill color. 
	 */
	private JColorArea fillColorArea; 
 
	/**
	 * Default constructor. 
	 * @param filledCircle Filled circle to be edited. 
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		super(filledCircle); 
		this.filledCircle = filledCircle;
		initGUI(); 
	}
	
	/**
	 * Helper method that initializes the GUI. 
	 */
	private void initGUI() {
		add(new JLabel("Set fill color:")); 
		fillColorArea = new JColorArea(filledCircle.getFillColor()); 
		add(fillColorArea); 
	}

	@Override
	public void checkEditing() {
		super.checkEditing();
	}

	@Override
	public void acceptEditing() {
		super.acceptEditing();
		filledCircle.setFillColor(fillColorArea.getCurrentColor());
	}

}
