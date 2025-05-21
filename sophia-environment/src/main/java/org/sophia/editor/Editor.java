package org.sophia.editor;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.sophia.elements.FigureDiamond;
import org.sophia.elements.FigureEllipse;
import org.sophia.elements.FigureLine;
import org.sophia.elements.FigureRectangle;
import org.sophia.elements.FigureRoundedRectangle;
import org.sophia.elements.Shape;
import org.sophia.model.Event;
import org.sophia.model.Flow;

public class Editor extends JFrame {

	private static final String REMOVE = "remove";

	private static final String CLEAR = "clear";

	private static final String LINE = "line";

	private static final String PLAY = "play";

	private static final String DIAMOND = "diamond";

	private static final String ELLIPSE_START = "ellipse-start";

	private static final String ELLIPSE_END = "ellipse-end";
	
	private static final String RECTANGLE = "rectangle";

	private static final String ROUNDRECTANGLE = "roundrectangle";

	private static final String DRAW = "draw";

	private static final String MOVE = "move";

	private static final String SELECTION = "selection";

	private static final long serialVersionUID = 1L;
	
	private Flow flow;
	
	private Shape selectedShape;
	
	private List<Shape> selectedRect = new ArrayList<>();
	
	private List<FigureLine> lines = new ArrayList<FigureLine>();

	private Map<String, Shape> figureMap = new HashMap<>();
	
	private int selectionNumber;
	
	private final EditorToolBar editorToolBar;

	private int x, y;

	public Editor() throws InvocationTargetException, InterruptedException {
		
		this.flow = new Flow();

		setTitle("Sophia");

		setLayout(new BorderLayout());
		
		final JPanel panelToolbox = new JPanel();
		this.editorToolBar = new EditorToolBar(this);
		
		panelToolbox.setLayout(new FlowLayout());
		panelToolbox.add(editorToolBar);
		
		add(panelToolbox, BorderLayout.WEST);

		addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				if (editorToolBar.getOperation().equals(DRAW) && editorToolBar.getFigureType().equals(ROUNDRECTANGLE)) {
					createRoundedRectangle(UUID.randomUUID().toString(), e.getX(), e.getY());
					repaint();
				} else if (editorToolBar.getOperation().equals(DRAW) && editorToolBar.getFigureType().equals(RECTANGLE)) {
					createRectangle(UUID.randomUUID().toString(), e.getX(), e.getY());
					repaint();
				} else if (editorToolBar.getOperation().equals(DRAW) && editorToolBar.getFigureType().equals(ELLIPSE_START)) {
					createEllipse(UUID.randomUUID().toString(), e.getX(), e.getY(), "START");
					repaint();
 				} else if (editorToolBar.getOperation().equals(DRAW) && editorToolBar.getFigureType().equals(ELLIPSE_END)) {
					createEllipse(UUID.randomUUID().toString(), e.getX(), e.getY(), "END");
					repaint();
 				} else if (editorToolBar.getOperation().equals(DRAW) && editorToolBar.getFigureType().equals(DIAMOND)) {
					createDiamond(UUID.randomUUID().toString(), e.getX(), e.getY());
					repaint();
 				}
			}

			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				if (editorToolBar.getOperation().equals(SELECTION)) {
					for (Shape fg : figureMap.values()) {
						if (fg.isSelected(e.getPoint())) {
							selectedShape = fg;
						}
					}
					repaint();
				}
			}
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {}
			public void mouseDragged(MouseEvent e) {
				if (editorToolBar.getOperation().equals(MOVE)) {
					x = e.getX();
					y = e.getY();
					repaint();
				}
			}
		});

		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent e) {
				repaint();
			}
		});
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(getToolkit().getScreenSize());
		setVisible(true);
		setResizable(true);

	}

	public void paint(Graphics g) {

		super.paint(g);

		if (selectedShape != null && editorToolBar.getOperation().equals(DRAW)) {

			Graphics2D g2 = (Graphics2D) g;

			selectedShape.draw(g2);
			
			redrawComponents(g2);
			
			
			selectedShape.drawSelectionState(g2);

			editorToolBar.setOperation(MOVE);
			editorToolBar.setFigureType("");


		} else if (editorToolBar.getOperation().equals(PLAY)) {

			Graphics2D g2 = (Graphics2D) g;
			redrawComponents(g2);
			
			System.out.println(flow);
			flow.play();
			
			editorToolBar.setOperation(DRAW);
			
			
		} else if (editorToolBar.getOperation().equals(MOVE)) {

			Graphics2D g2 = (Graphics2D) g;

			if (g2 != null) {
				g2.setColor(Color.WHITE);
				g2.fillRect(100, 70, getWidth(), getHeight());

				selectedShape.move(this.x, this.y);

				redrawComponents(g2);

				selectedShape.drawSelectionState(g2);

			}

		} else if (editorToolBar.getOperation().equals(SELECTION) && editorToolBar.getFigureType().equals("")) {

			Graphics2D g2 = (Graphics2D) g;

			if (g2 != null) {
				g2.setColor(Color.WHITE);
				g2.fillRect(100, 70, getWidth(), getHeight());
			}

			redrawComponents(g2);
	
			selectedShape.drawSelectionState(g2);
			
			editorToolBar.setOperation(MOVE);
		
			
		} else if (editorToolBar.getOperation().equals(SELECTION) && editorToolBar.getFigureType().equals(LINE)) {

			Graphics2D g2 = (Graphics2D) g;

			if (g2 != null) {
				g2.setColor(Color.WHITE);
				g2.fillRect(100, 70, getWidth(), getHeight());
			}
			

			if (this.selectionNumber < 1) {
				this.selectedRect.add(selectedShape);
				this.selectionNumber++;
			} else {
				this.selectionNumber = 0;

				this.selectedRect.add(selectedShape);
				
				Shape fig1 = this.selectedRect.get(0);
				Shape fig2 = this.selectedRect.get(1);
				
				FigureLine figureLine = new FigureLine(fig1, fig2);
				figureLine.draw(g2);
				
				Event event1 = flow.getEvent(fig1.getId());
				Event event2 = flow.getEvent(fig2.getId());
				event1.addEvent(event2);
				
				figureMap.put(figureLine.getId(), figureLine);
				
				this.selectedRect.clear();
				
				editorToolBar.setOperation(MOVE);
				editorToolBar.setFigureType("");
			}

			redrawComponents(g2);

			selectedShape.drawSelectionState(g2);
		
			
		} else if (editorToolBar.getOperation().equals(CLEAR)) {

			Graphics2D g2 = (Graphics2D) g;

			clear();

			if (g2 != null) {
				g2.setColor(Color.WHITE);
				g2.fillRect(100, 70, getWidth(), getHeight());
			}
			
			redrawComponents(g2);

		} else if (editorToolBar.getOperation().equals(REMOVE)) {

			Graphics2D g2 = (Graphics2D) g;

			figureMap.remove(selectedShape.getId());

			if (g2 != null) {
				g2.setColor(Color.WHITE);
				g2.fillRect(100, 70, getWidth(), getHeight());
			}

			redrawComponents(g2);
		}

	}

	private void redrawComponents(Graphics2D g2) {
		for (Shape fg : figureMap.values()) {
			g2.setColor(fg.getFillColor());
			g2.fill((java.awt.Shape) fg);

			g2.setColor(fg.getBorderColor());
			g2.setStroke(new BasicStroke(2));

			fg.draw(g2);
		}
	}

	public Shape getSelectedShape() {
		return selectedShape;
	}

	public Flow getFlow() {
		return flow;
	}
	
	public Map<String, Shape> getFigureMap() {
		return figureMap;
	}
	
	public FigureRoundedRectangle createRoundedRectangle(String id, int x, int y) {
		FigureRoundedRectangle figRect = new FigureRoundedRectangle(x, y);
		figRect.setId(id);
		
		selectedShape = figRect;

		addFigure(figRect.getId(), figRect);
		
		return figRect;
	}
	
	public FigureRectangle createRectangle(String id, int x, int y) {
		FigureRectangle figRect = new FigureRectangle(x, y);
		figRect.setId(id);
		
		selectedShape = figRect;

		addFigure(figRect.getId(), figRect);
		
		return figRect;
	}
	
	public FigureEllipse createEllipse(String id, int x, int y, String type) {
		
		FigureEllipse figRect = new FigureEllipse(x, y);
		figRect.setId(id);
		
		figRect.setType(type);
		selectedShape = figRect;

		addFigure(figRect.getId(), figRect);
		
		return figRect;
	}
	
	public FigureDiamond createDiamond(String id, int x, int y) {
		FigureDiamond figRect = new FigureDiamond(x, y);
		figRect.setId(id);
		
		selectedShape = figRect;
		
		addFigure(figRect.getId(), figRect);
		
		return figRect;
	}
	
	public void createLine(String id, String sourceId, String targetId) {
		
		Shape shp1 = this.figureMap.get(sourceId);
		Shape shp2 = this.figureMap.get(targetId);
		
		FigureLine figRect = new FigureLine(shp1, shp2);
		figRect.setId(id);
		
		Event event1 = flow.getEvent(shp1.getId());
		Event event2 = flow.getEvent(shp2.getId());
		event1.addEvent(event2);
		
		addFigure(figRect.getId(), figRect);
	}
	
	public void addFigure(String id, Shape shape) {
		figureMap.put(id, shape);

		Event event = new Event(id);
		event.setType(shape.getClass().getSimpleName());
		flow.addEvent(event);
	}
	
	public void clear() {
		this.figureMap.clear();
		this.flow.clear();
	}
}
