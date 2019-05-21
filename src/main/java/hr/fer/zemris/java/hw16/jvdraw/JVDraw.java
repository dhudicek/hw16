package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.InputMap;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;
import hr.fer.zemris.java.hw16.jvdraw.shapes.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.shapes.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.shapes.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.shapes.visitors.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.shapes.visitors.GeometricalObjectWriter;
import hr.fer.zemris.java.hw16.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;

/**
 * Simple drawing program, containing tools for drawing colored lines, circles
 * and filled circles, editing already drawn objects, importing and exporting files. 
 * @author damjan
 *
 */
public class JVDraw extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Current tool for drawing.  
	 */
	private Tool currentState; 
	
	/**
	 * Drawing canvas. 
	 */
	private JDrawingCanvas canvas;
	
	/**
	 * Drawing model. 
	 */
	private DrawingModel model; 
	
	/**
	 * Path to current drawing. Can be null. 
	 */
	private Path currentFilePath; 
	
	/**
	 * Flag that shows if current model has been modified since last save. 
	 */
	private boolean modified; 
	
	/**
	 * List of all geometrical objects currently in the model. Shown in the sidebar.
	 */
	private JList<GeometricalObject> list; 
	
	/**
	 * Default constructor. 
	 */
	public JVDraw() {
		setSize(800, 600); 
		setLocation(200, 200); 
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(new ActionEvent(e.getSource(), e.getID(), e.paramString()));
			}
		});
		setTitle("JVDraw");  
		initGUI(); 
	}
	
	/**
	 * Helper method that initializes the GUI. 
	 */
	private void initGUI() {
		setLayout(new BorderLayout(5, 5));
		model = new DrawingModelImpl();
		model.addDrawingModelListener(new DrawingModelListener() {

			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				modified = true; 
			}

			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				modified = true; 
			}

			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				modified = true; 
			}
			
		});
		initToolbar(); 
		initCanvas(); 
		initSidebar();
		initMenuBar(); 
	}
	
	/**
	 * Helper method that initializes the toolbar and statusbar. 
	 */
	private void initToolbar() {
		JToolBar toolbar = new JToolBar();
		add(toolbar, BorderLayout.NORTH); 
		
		JColorArea fgColorArea = new JColorArea(Color.red); 
		JColorArea bgColorArea = new JColorArea(Color.BLUE); 
		toolbar.add(fgColorArea); 
		toolbar.add(bgColorArea);
		toolbar.addSeparator();
		
		ColorInfoBar infoBar = new ColorInfoBar(fgColorArea, bgColorArea); 
		add(infoBar, BorderLayout.SOUTH);
		
		Tool circleTool = new CircleTool(model, fgColorArea); 
		Tool lineTool = new LineTool(model, fgColorArea); 
		Tool filledCircleTool = new FilledCircleTool(model, fgColorArea, bgColorArea); 
		currentState = lineTool;
		ButtonGroup tools = new ButtonGroup(); 
		JToggleButton lineButton = new JToggleButton("Line");
		lineButton.setSelected(true);
		JToggleButton circleButton = new JToggleButton("Circle");
		JToggleButton filledCircleButton = new JToggleButton("Filled circle");
		
		lineButton.addActionListener((e) -> {
			if (((JToggleButton)e.getSource()).isSelected()) currentState = lineTool;
			canvas.setCurrentState(currentState);
		});
		circleButton.addActionListener((e) -> {
			if (((JToggleButton)e.getSource()).isSelected()) currentState = circleTool;
			canvas.setCurrentState(currentState);
		});
		filledCircleButton.addActionListener((e) -> {
			if (((JToggleButton)e.getSource()).isSelected()) currentState = filledCircleTool;
			canvas.setCurrentState(currentState);
		});
		
		tools.add(lineButton);
		tools.add(circleButton);
		tools.add(filledCircleButton);
		
		toolbar.add(lineButton);
		toolbar.add(circleButton);
		toolbar.add(filledCircleButton);
	}
	
	/**
	 * Helper method that initializes canvas. 
	 */
	private void initCanvas() { 
		canvas = new JDrawingCanvas(currentState, model);
		add(canvas, BorderLayout.CENTER); 
	}
	
	/**
	 * Helper method that initializes the sidebar. 
	 */
	private void initSidebar() {
		list = new JList<GeometricalObject>(new DrawingObjectListModel(model));
		list.setFocusable(true);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					GeometricalObject clicked = list.getSelectedValue(); 
					GeometricalObjectEditor editor = clicked.createGeometricalObjectEditor();
					if (JOptionPane.showConfirmDialog(
							JVDraw.this, 
							editor, 
							"Edit this object", 
							JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						try {
							editor.checkEditing();
							editor.acceptEditing();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(
									JVDraw.this, 
									"Could not edit object: " + ex.getMessage(), 
									"Error", 
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		
		InputMap imap = list.getInputMap(); 
		ActionMap amap = list.getActionMap(); 
		
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "deleteItem");
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0), "moveItemDown");
		//tri za istu stvar jer ovisno o layoutu tipkovnice i platformi Java dozivljava samo neke kratice
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, KeyEvent.SHIFT_DOWN_MASK), "moveItemUp");
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK), "moveItemUp");
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, KeyEvent.CTRL_DOWN_MASK), "moveItemUp");
		
		amap.put("deleteItem", deleteItem);
		amap.put("moveItemDown", moveItemDown);
		amap.put("moveItemUp", moveItemUp);
		
		list.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				list.clearSelection();
			}
		});
		
		add(new JScrollPane(list), BorderLayout.EAST); 
	}
	
	/**
	 * Helper method that initializes the menu bar. 
	 */
	private void initMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(new JMenuItem(openAction)); 
		fileMenu.add(new JMenuItem(saveAction));
		fileMenu.add(new JMenuItem(saveAsAction)); 
		fileMenu.add(new JMenuItem(exportAction)); 
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction)); 
		
		menuBar.add(fileMenu); 
		setJMenuBar(menuBar);
	}
	
	/**
	 * Saves current model to current file path, if it exists. Calls saveAsAction otherwise.  
	 */
	private Action saveAction = new AbstractAction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{
			putValue(NAME, "Save");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (currentFilePath == null) {
				saveAsAction.actionPerformed(e);
				return;
			}
			
			List<String> lines = createLinesFromModel(); 
			try {
				Files.write(currentFilePath, lines);
				modified = false;
			} catch (IOException e1) {
				showErrorMessage("Error", "Could not save file: " + e1.getMessage());
			} 
		}
		
	};
	
	/**
	 * Saves current model as a new .jvd file. 
	 */
	private Action saveAsAction = new AbstractAction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{
			putValue(NAME, "Save As"); 
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser(); 
			fc.setDialogTitle("Save file as");
			fc.setFileFilter(new FileNameExtensionFilter("JVD files", "jvd"));
			
			if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) return;
			Path path = fc.getSelectedFile().toPath();
			if (!path.getFileName().toString().toLowerCase().endsWith(".jvd")) {  
				path = path.resolveSibling(path.getFileName() + ".jvd");
				
			}
			
			List<String> lines = createLinesFromModel(); 
			try {
				Files.write(path, lines);
			} catch (IOException e1) {
				showErrorMessage("Error", "Unable to save file: " + e1.getMessage());
			} 
			setCurrentFilePath(path);
			modified = false;
		}
		
	}; 
	
	/**
	 * Opens a new .jvd file. Wanrs user if current model has been modified but not saved. 
	 */
	private Action openAction = new AbstractAction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{
			putValue(NAME, "Open"); 
		}
		@Override
		public void actionPerformed(ActionEvent e) { 
			if (modified) {
				int result = JOptionPane.showConfirmDialog(
						JVDraw.this, 
						"Unsaved changes in model. Do you want to save before opening a different file?",
						"Unsaved changes",
						JOptionPane.YES_NO_CANCEL_OPTION); 
				if (result == JOptionPane.YES_OPTION) {
					saveAction.actionPerformed(e);
					if (modified) return; 
				} else if (result == JOptionPane.CANCEL_OPTION) {
					return; 
				}
			}
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			fc.setFileFilter(new FileNameExtensionFilter("JVD files", "jvd"));
			
			if (fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) return;
			Path path = fc.getSelectedFile().toPath();
			try {
				List<String> lines = Files.readAllLines(path);
				clearAndFillModelFromLines(lines); 
				setCurrentFilePath(path);
				modified = false; 
			} catch (IOException e1) {
				showErrorMessage("Error", "Could not read file: " + e1.getMessage());
			} 
		}
	};
	
	/**
	 * Exports current drawing as a JPG, PNG or GIF image.
	 */
	private Action exportAction = new AbstractAction() { 
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			putValue(NAME, "Export"); 
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (model.getSize() == 0) {
				showErrorMessage("Error", "Cannot export an empty canvas.");
				return;
			}
			
			String[] options = new String[] {".gif", ".jpg", ".png"}; 
			int response = JOptionPane.showOptionDialog(
					JVDraw.this, 
					"Choose image format for export", 
					"Image format", 
					JOptionPane.DEFAULT_OPTION, 
					JOptionPane.PLAIN_MESSAGE, 
					null, 
					options, 
					null); 
			if (response < 0) return;
			String extension = options[response];  
			
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Export to " + extension);
			fc.setFileFilter(new FileNameExtensionFilter(extension + " files", extension));
			
			
			if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) return; 
			Path file = fc.getSelectedFile().toPath();
			if (!file.getFileName().toString().toLowerCase().endsWith(extension)) {
				file = file.resolveSibling(file.getFileName() + extension);
			}
			 
			BufferedImage image = createImage();
			try {
				ImageIO.write(image, extension.substring(1), file.toFile()); 
			} catch (IOException e1) {
				showErrorMessage("Error", "Could not write image: " + e1.getMessage());
				e1.printStackTrace();
			} 
		}
	};
	
	/**
	 * Moves selected object in the list one place up. 
	 */
	private Action moveItemUp = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (list.isSelectionEmpty()) return; 
			GeometricalObject o = list.getSelectedValue(); 
			model.changeOrder(o, -1);
			list.setSelectedValue(o, true);
		}
	};
	
	/**
	 * Moves selected object in the list one place down.
	 */
	private Action moveItemDown = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (list.isSelectionEmpty()) return; 
			GeometricalObject o = list.getSelectedValue(); 
			model.changeOrder(o, 1);
			list.setSelectedValue(o, true);
		}
	};
	
	/**
	 * Deletes selected object from the list. 
	 */
	private Action deleteItem = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (list.isSelectionEmpty()) return; 
			GeometricalObject o = list.getSelectedValue(); 
			model.remove(o);
		}
	};
	
	/**
	 * Checks if drawing has been modified, offers the user to save changes and then, 
	 * if saved, exists the application.
	 */
	private Action exitAction = new AbstractAction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			putValue(NAME, "Exit"); 
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!modified) {
				dispose(); 
				return; 
			}
			
			int result = JOptionPane.showConfirmDialog(
					JVDraw.this, 
					"Unsaved changes in model. Do you want to save before exiting?",
					"Unsaved changes",
					JOptionPane.YES_NO_CANCEL_OPTION); 
			if (result == JOptionPane.YES_OPTION) {
				saveAction.actionPerformed(e);
				if (!modified) dispose(); 
			} else if (result == JOptionPane.NO_OPTION) {
				dispose(); 
			}
		}
	};
	
	/**
	 * Turns the model into its textual representation, ready to be saved to .jvd file. 
	 * @return List of lines. 
	 */
	private List<String> createLinesFromModel() {
		List<String> lines = new ArrayList<>();
		GeometricalObjectVisitor writer = new GeometricalObjectWriter(lines); 
		for (int i = 0; i < model.getSize(); i++) {
			model.getObject(i).accept(writer);
		}
		return lines;
	}
	 
	/**
	 * Clears current model and fills it using given list of lines that represent 
	 * graphical object textually. 
	 * @param lines List of lines. 
	 * @throws IOException If unable to properly parse file. 
	 */
	private void clearAndFillModelFromLines(List<String> lines) throws IOException {
		for (int i = 0; i < model.getSize(); i++) {
			model.remove(model.getObject(i));
		}
		
		try {
			for (String line : lines) { 
				if (line.startsWith("LINE")) {
					model.add(new Line(line));
				} else if (line.startsWith("CIRCLE")) {
					model.add(new Circle(line));
				} else if (line.startsWith("FCIRCLE")) {
					model.add(new FilledCircle(line));
				} else {
					throw new IOException("Invalid line in file: " + line); 
				}
			}			
		} catch (IllegalArgumentException e) {
			throw new IOException(e); 
		}
	}
	
	/**
	 * Creates an image from current model. 
	 * @return Image. 
	 */
	private BufferedImage createImage() {
		GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
		for (int i = 0; i < model.getSize(); i++) {
			model.getObject(i).accept(bbcalc);
		}
		Rectangle box = bbcalc.getBoundingBox(); 
		
		BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = image.createGraphics();
		g.translate(-box.x, -box.y);
		g.setColor(Color.WHITE);
		g.fillRect(box.x, box.y, box.width, box.height);
		
		GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
		for (int i = 0; i < model.getSize(); i++) {
			model.getObject(i).accept(painter);
		}
		g.dispose();
		
		return image; 
	}
	
	/**
	 * Sets current file path to new file path and updates program's title accordingly. 
	 * @param path New file path. 
	 */
	private void setCurrentFilePath(Path path) {
		currentFilePath = path; 
		if (currentFilePath != null) {
			setTitle("JVDraw - " + currentFilePath.getFileName().toString());
		} else {
			setTitle("JVDraw");
		}
	}
	
	/**
	 * Helper method that shows an error message popup with given title.
	 * @param title Error title. 
	 * @param message Error message. 
	 */
	private void showErrorMessage(String title, String message) {
		JOptionPane.showMessageDialog(JVDraw.this, message, title, JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Main method that starts the program. 
	 * @param args Command line arguments. Not used. 
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JVDraw().setVisible(true));
	}
}
