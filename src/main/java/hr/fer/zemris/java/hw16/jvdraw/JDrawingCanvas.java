package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.shapes.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.shapes.visitors.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;

/**
 * JComponent that represents a drawing canvas. Listens a {@linkDrawingModel} to paint
 * objects, and forwards mouse clicks to current drawing tool.  
 * @author damjan
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Currently selected drawing tool. 
	 */
	private Tool currentState;
	
	/**
	 * Drawing model. 
	 */
	private DrawingModel model;  
	
	/**
	 * Default constructor. Registers this canvas as a listener to given drawing model. 
	 * @param currentState Current tool. 
	 * @param model Drawing model. 
	 */
	public JDrawingCanvas(Tool currentState, DrawingModel model) {
		this.currentState = currentState;
		this.model = model;   
		model.addDrawingModelListener(this);
		setFocusable(true);
		initMouseListeners(); 
	}
	
	/**
	 * Helper method that initializes mouse listeners that forward requests to current state.
	 */
	private void initMouseListeners() {
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				requestFocusInWindow();
				currentState.mouseClicked(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				requestFocusInWindow(); 
				currentState.mousePressed(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				currentState.mouseReleased(e);
			}
			
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				currentState.mouseMoved(e);
				repaint(); 
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				currentState.mouseDragged(e);
				repaint(); 
			}
		});
	}
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		GeometricalObjectVisitor painter = new GeometricalObjectPainter((Graphics2D)g);  
		for (int i = 0; i < model.getSize(); i++) {
			model.getObject(i).accept(painter);
		}
		currentState.paint((Graphics2D) g);
		g.dispose();
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint(); 
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint(); 
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint(); 
	}
	
	/**
	 * Sets current state.  
	 * @param currentState New state. 
	 */
	public void setCurrentState(Tool currentState) {
		this.currentState = currentState; 
	}
}
