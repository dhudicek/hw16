package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;

/**
 * State used to draw a line. First click determines starting point, second click determines
 * ending point. Mouse is tracked in real-time and line is drawn to show what it will look like
 * before the ending point is set. 
 * @author damjan
 *
 */
public class LineTool implements Tool {

	/**
	 * Drawing model. 
	 */
	private DrawingModel model;
	
	/**
	 * Color provider for line color. 
	 */
	private IColorProvider colorProvider; 
	
	/**
	 * Flag that shows if drawing is in process (starting point has been set). 
	 */
	private boolean isActive; 
	
	/**
	 * True if user is currently dragging the mouse, false otherwise. 
	 */
	private boolean isDragging; 
	
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
	 * Default constructor. 
	 * @param model Drawing model. 
	 * @param colorProvider Line color provider. 
	 */
	public LineTool(DrawingModel model, IColorProvider colorProvider) {
		this.model = model;
		this.colorProvider = colorProvider;
		isActive = false; 
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (!isActive) {
			setStarting(e); 
		} else {
			setEnding(e); 
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (isActive && isDragging) {
			setEnding(e); 
			isDragging = false;
		}
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		if (isActive) {
			setCurrent(e); 
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		isDragging = true;
		if (!isActive) {
			setStarting(e); 
		}
		setCurrent(e); 
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (isActive) {
			g2d.setColor(colorProvider.getCurrentColor());
			g2d.drawLine(startX, startY, endX, endY);
		}
	}
	
	/**
	 * Helper method that sets the starting point. 
	 * @param e Event. 
	 */
	private void setStarting(MouseEvent e) {
		isActive = true; 
		startX = e.getX();
		startY = e.getY(); 
	} 
	
	/**
	 * Helper method that sets the current mouse position. 
	 * @param e Event. 
	 */
	private void setCurrent(MouseEvent e) {
		endX = e.getX(); 
		endY = e.getY();  
	}
	
	/**
	 * Helper method that sets the ending point and adds a new line to the model. 
	 * @param e Event. 
	 */
	private void setEnding(MouseEvent e) {
		isActive = false;
		endX = e.getX(); 
		endY = e.getY(); 
		Line line = new Line(startX, startY, endX, endY, colorProvider.getCurrentColor()); 
		model.add(line);
	}

}
