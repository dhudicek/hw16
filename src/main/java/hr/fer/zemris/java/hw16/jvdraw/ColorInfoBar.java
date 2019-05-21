package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * Info bar that shows information about current foreground and background colors. 
 * @author damjan
 *
 */
public class ColorInfoBar extends JLabel implements ColorChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Foreground color provider.
	 */
	private IColorProvider fgColorProvider;
	
	/**
	 * Background color provider. 
	 */
	private IColorProvider bgColorProvider;
	
	/**
	 * Default constructor. Registers this object as listener to both color providers.
	 * @param fgColorProvider Foreground color provider.
	 * @param bgColorProvider Background color provider.
	 */
	public ColorInfoBar(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
		fgColorProvider.addColorChangeListener(this);
		bgColorProvider.addColorChangeListener(this);
		this.newColorSelected(null, null, null);
	}


	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		Color fgColor = fgColorProvider.getCurrentColor();
		Color bgColor = bgColorProvider.getCurrentColor(); 
		this.setText(String.format("Foreground color: (%d, %d, %d), background color: (%d, %d, %d)", 
				fgColor.getRed(), fgColor.getGreen(), fgColor.getBlue(), 
				bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue())
		);
	} 
	
	
}
