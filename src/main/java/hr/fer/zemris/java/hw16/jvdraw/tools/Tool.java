package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Interface that represents a State (State design pattern). Implementations
 * are different drawing tools for drawing on JDrawingCanvas. 
 * @author damjan
 *
 */
public interface Tool {
	
	/**
	 * Called when mouse is pressed. 
	 * @param e Event. 
	 */
	public void mousePressed(MouseEvent e);
	
	/**
	 * Called when mouse is released. 
	 * @param e Event. 
	 */
	public void mouseReleased(MouseEvent e);
	
	/**
	 * Called when mouse is clicked. 
	 * @param e Event. 
	 */
	public void mouseClicked(MouseEvent e);
	
	/**
	 * Called when mouse is moved. 
	 * @param e Event. 
	 */
	public void mouseMoved(MouseEvent e);
	
	/**
	 * Called when mouse is dragged. 
	 * @param e Event. 
	 */
	public void mouseDragged(MouseEvent e);
	
	/**
	 * Called to paint current state of component.
	 * @param g2d Graphics. 
	 */
	public void paint(Graphics2D g2d); 
}
