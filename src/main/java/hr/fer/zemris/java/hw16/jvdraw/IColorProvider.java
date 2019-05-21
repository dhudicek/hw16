package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

/**
 * Interface implemented by color providers that have a current color and notify
 * listeners when the color is changed. 
 * @author damjan
 *
 */
public interface IColorProvider {
	
	/**
	 * Returns current color. 
	 * @return Current color. 
	 */
	public Color getCurrentColor();
	
	/**
	 * Adds a color change listener.  
	 * @param l Listener.
	 */
	public void addColorChangeListener(ColorChangeListener l); 
	
	/**
	 * Removes a color change listener. 
	 * @param l Listener. 
	 */
	public void removeColorChangeListener(ColorChangeListener l); 
}
