package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * JComponent that implements IColorProvider interface and represents a clickable component
 * that saves a current color, opens up a color chooser when clicked, 
 * and is always painted in its current color. Whenever the current color changes,
 * it notifies its listeners of the change. 
 * @author damjan
 *
 */
public class JColorArea extends JComponent implements IColorProvider {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Currently selected color. 
	 */
	private Color selectedColor;
	
	/**
	 * List of listeners. 
	 */
	private List<ColorChangeListener> listeners; 
	
	/**
	 * Default constructor. 
	 * @param color Color that will be set as selected color. 
	 */
	public JColorArea(Color color) {
		super(); 
		this.selectedColor = color; 
		listeners = new ArrayList<>();
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) { 
				Color newColor = JColorChooser.showDialog(
						JColorArea.this, 
						"Choose color", 
						selectedColor); 
				if (newColor != null) {
					setCurrentColor(newColor);
				}
			}
		});
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15); 
	}
	
	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize(); 
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor; 
	}
	
	/**
	 * Sets current color to given color and notifies listeners of the change.  
	 * @param color New current color. 
	 */
	public void setCurrentColor(Color color) {
		Color oldColor = selectedColor; 
		selectedColor = color; 
		this.repaint();
		listeners.forEach((l) -> l.newColorSelected(this, oldColor, selectedColor));
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l); 
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l); 
	}
	
}
