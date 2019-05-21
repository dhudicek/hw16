package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

/**
 * Interface implemented by listeners to color changes from a color provider. 
 * @author damjan
 *
 */
public interface ColorChangeListener {
	
	/**
	 * Called when color provider changes color. 
	 * @param source Color provider. 
	 * @param oldColor Old color. 
	 * @param newColor New color. 
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor); 
}
