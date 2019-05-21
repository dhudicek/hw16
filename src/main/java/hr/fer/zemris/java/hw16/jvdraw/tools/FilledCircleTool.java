package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;

/**
 * Extension of CircleTool that adds support for fill color. 
 * @author damjan
 *
 */
public class FilledCircleTool extends CircleTool {

	/**
	 * Fill color provider.
	 */
	private IColorProvider fillColorProvider;
	
	/**
	 * Default constructor. 
	 * @param model Drawing model.
	 * @param lineColorProvider Line color provider.
	 * @param fillColorProvider Fill color provider.
	 */
	public FilledCircleTool(DrawingModel model, IColorProvider lineColorProvider, IColorProvider fillColorProvider) {
		super(model, lineColorProvider); 
		this.fillColorProvider = fillColorProvider;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!isActive) {
			super.mouseClicked(e);
		} else {
			setEnding(e); 
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (isActive && isDragging) {
			setEnding(e); 
			isDragging = false; 
		}
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (isActive) {
			super.paint(g2d);
			int cornerX = centerX - radius; 
			int cornerY = centerY - radius; 
			g2d.setColor(fillColorProvider.getCurrentColor());
			g2d.fillOval(cornerX, cornerY, 2*radius, 2*radius);
		}
	}
	
	/**
	 * Helper method that sets the ending values and adds a new filled circle to the model. 
	 * @param e Event.
	 */
	private void setEnding(MouseEvent e) {
		isActive = false;
		int endX = e.getX(); 
		int endY = e.getY(); 
		radius = (int)Math.hypot(centerX - endX, centerY - endY);
		model.add(new FilledCircle(centerX, centerY, radius, colorProvider.getCurrentColor(), fillColorProvider.getCurrentColor()));
	}
}
