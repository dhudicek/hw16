package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;

/**
 * State used to draw a circle. First click determines starting point, second click 
 * determines radius and adds circle to model. Mouse is tracked between first and second 
 * click and circle is drawn in real time before the ending point is set. 
 * @author damjan
 *
 */
public class CircleTool implements Tool {

	/**
	 * Drawing model. 
	 */
	protected DrawingModel model; 
	
	/**
	 * Color provider for line color. 
	 */
	protected IColorProvider colorProvider; 
	
	/**
	 * x-coordinate of center.
	 */
	protected int centerX; 
	
	/**
	 * y-coordinate of center.
	 */
	protected int centerY; 
	
	/**
	 * Radius.
	 */
	protected int radius; 
	
	/**
	 * True if starting point has been set, false otherwise. 
	 */
	protected boolean isActive;  
	
	/**
	 * True if user is currently dragging, false otherwise. 
	 */
	protected boolean isDragging; 
	
	/**
	 * Default constructor.
	 * @param model Drawing model.
	 * @param colorProvider Line color provider.
	 */
	public CircleTool(DrawingModel model, IColorProvider colorProvider) {
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
		setCurrent(e); 
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
			int cornerX = centerX - radius; 
			int cornerY = centerY - radius; 
			g2d.setColor(colorProvider.getCurrentColor());
			g2d.drawOval(cornerX, cornerY, 2*radius, 2*radius);			
		}
	}

	/**
	 * Helper method that sets the center of the circle. 
	 * @param e Event. 
	 */
	private void setStarting(MouseEvent e) {
		isActive = true;
		centerX = e.getX(); 
		centerY = e.getY(); 
	}
	
	/**
	 * Helper method that sets radius value for current mouse position. 
	 * @param e Event.
	 */
	private void setCurrent(MouseEvent e) {
		if (isActive) {
			radius = (int)Math.hypot(centerX - e.getX(), centerY - e.getY()); 
		}
	}
	
	/**
	 * Helper method that sets the ending values and adds a new circle to the model. 
	 * @param e Event. 
	 */
	private void setEnding(MouseEvent e) {
		isActive = false;
		int endX = e.getX(); 
		int endY = e.getY(); 
		radius = (int)Math.hypot(centerX - endX, centerY - endY);
		model.add(new Circle(centerX, centerY, radius, colorProvider.getCurrentColor()));
	}
	
}
