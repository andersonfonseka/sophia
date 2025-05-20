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

import javax.swing.JFrame;
import javax.swing.JPanel;

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
	
	private Event sourceEvent;
	
	private Event targetEvent;
	
	private Shape selectedShape;
	
	private List<Shape> selectedRect = new ArrayList<>();
	
	private List<FigureLine> lines = new ArrayList<FigureLine>();

	private Map<String, Shape> map = new HashMap();
	
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
				if (editorToolBar.getOperation().equals(DRAW) && editorToolBar.getFigureType().equals(ROUNDRECTANGLE) && e.getY() > 100) {
					FigureRoundedRectangle figRect = new FigureRoundedRectangle((int) e.getX(), (int) e.getY());
					selectedShape = figRect;
					map.put(figRect.getId(), figRect);

					Event event = new Event(figRect.getId());
					event.setType("FigureRoundedRectangle");
					flow.addEvent(event);
					
					repaint();
				} else if (editorToolBar.getOperation().equals(DRAW) && editorToolBar.getFigureType().equals(RECTANGLE) && e.getY() > 100) {
					FigureRectangle figRect = new FigureRectangle((int) e.getX(), (int) e.getY());
					selectedShape = figRect;
					map.put(figRect.getId(), figRect);

					Event event = new Event(figRect.getId());
					event.setType("FigureRectangle");
					flow.addEvent(event);
										
					repaint();
				} else if (editorToolBar.getOperation().equals(DRAW) && editorToolBar.getFigureType().equals(ELLIPSE_START) && e.getY() > 100) {
					FigureEllipse figRect = new FigureEllipse((int) e.getX(), (int) e.getY());
					figRect.setType("START");
					selectedShape = figRect;
					map.put(figRect.getId(), figRect);

					Event event = new Event(figRect.getId());
					event.setType("FigureEllipse");
					flow.addEvent(event);
										
					repaint();
 				} else if (editorToolBar.getOperation().equals(DRAW) && editorToolBar.getFigureType().equals(ELLIPSE_END) && e.getY() > 100) {
					FigureEllipse figRect = new FigureEllipse((int) e.getX(), (int) e.getY());
					figRect.setType("END");
					selectedShape = figRect;
					map.put(figRect.getId(), figRect);

					Event event = new Event(figRect.getId());
					event.setType("FigureEllipse");
					flow.addEvent(event);
										
					repaint();
 				} 
				else if (editorToolBar.getOperation().equals(DRAW) && editorToolBar.getFigureType().equals(DIAMOND) && e.getY() > 100) {
					FigureDiamond figRect = new FigureDiamond((int) e.getX(), (int) e.getY());
					selectedShape = figRect;
					map.put(figRect.getId(), figRect);

					Event event = new Event(figRect.getId());
					event.setType("FigureDiamond");
					flow.addEvent(event);
					
					repaint();
 				}
			}

			public void mousePressed(MouseEvent e) {}

			public void mouseExited(MouseEvent e) {}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
				
				if (editorToolBar.getOperation().equals(SELECTION)) {
					for (Shape fg : map.values()) {
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
		
		//setSize(new Dimension(800, 600));
		setVisible(true);
		setResizable(true);

	}

	public void paint(Graphics g) {

		super.paint(g);

		if (selectedShape != null && editorToolBar.getOperation().equals(DRAW)) {

			Graphics2D g2 = (Graphics2D) g;

			selectedShape.draw(g2);
			
			redrawComponents(g2);
			
			
			if (selectedShape instanceof FigureDiamond) {
				((FigureDiamond) selectedShape).drawSelectionState(g2);
			} else {
				drawSelectionState(g2);
			}

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

				if (selectedShape instanceof FigureDiamond) {
					((FigureDiamond) selectedShape).drawSelectionState(g2);
				} else {
					drawSelectionState(g2);
				}
			}

		} else if (editorToolBar.getOperation().equals(SELECTION) && editorToolBar.getFigureType().equals("")) {

			Graphics2D g2 = (Graphics2D) g;

			if (g2 != null) {
				g2.setColor(Color.WHITE);
				g2.fillRect(100, 70, getWidth(), getHeight());
			}

			redrawComponents(g2);
	
			if (selectedShape instanceof FigureDiamond) {
				((FigureDiamond) selectedShape).drawSelectionState(g2);
			} else {
				drawSelectionState(g2);
			}
			
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
				figureLine.paint(g2);
				
				Event event1 = flow.getEvent(fig1.getId());
				Event event2 = flow.getEvent(fig2.getId());
				event1.addEvent(event2);
				
				lines.add(figureLine);
				
				this.selectedRect.clear();
				
				editorToolBar.setOperation(MOVE);
				editorToolBar.setFigureType("");
			}

			redrawComponents(g2);

			if (selectedShape instanceof FigureDiamond) {
				((FigureDiamond) selectedShape).drawSelectionState(g2);
			} else {
				drawSelectionState(g2);
			}

		
			
		} else if (editorToolBar.getOperation().equals(CLEAR)) {

			Graphics2D g2 = (Graphics2D) g;

			this.map.clear();
			this.lines.clear();

			if (g2 != null) {
				g2.setColor(Color.WHITE);
				g2.fillRect(100, 70, getWidth(), getHeight());
			}
			
			redrawComponents(g2);

		} else if (editorToolBar.getOperation().equals(REMOVE)) {

			Graphics2D g2 = (Graphics2D) g;

			map.remove(selectedShape);

			if (g2 != null) {
				g2.setColor(Color.WHITE);
				g2.fillRect(100, 70, getWidth(), getHeight());
			}

			redrawComponents(g2);
		}

	}

	private void redrawComponents(Graphics2D g2) {
		
		for (FigureLine figureLine : lines) {
			figureLine.paint(g2);
		}
		
		for (Shape fg : map.values()) {
			g2.setColor(fg.getFillColor());
			g2.fill((java.awt.Shape) fg);

			g2.setColor(fg.getBorderColor());
			g2.setStroke(new BasicStroke(2));

			fg.draw(g2);
		}
		
	}

	private void drawSelectionState(Graphics2D g2) {
		if (g2 != null) {
			g2.setColor(Color.BLACK);

			g2.drawRect((int) selectedShape.getX() - 5, (int) selectedShape.getY() - 5, 5, 5);
			g2.fillRect((int) selectedShape.getX() - 5, (int) selectedShape.getY() - 5, 5, 5);

			g2.drawRect((int) (selectedShape.getX() + selectedShape.getWidth()), (int) selectedShape.getY() - 5, 5, 5);
			g2.fillRect((int) (selectedShape.getX() + selectedShape.getWidth()), (int) selectedShape.getY() - 5, 5, 5);

			g2.drawRect((int) selectedShape.getX() - 5, (int) (selectedShape.getY() + selectedShape.getHeight()), 5, 5);
			g2.fillRect((int) selectedShape.getX() - 5, (int) (selectedShape.getY() + selectedShape.getHeight()), 5, 5);

			g2.drawRect((int) (selectedShape.getX() + selectedShape.getWidth()), (int) (selectedShape.getY() + selectedShape.getHeight()), 5, 5);
			g2.fillRect((int) (selectedShape.getX() + selectedShape.getWidth()), (int) (selectedShape.getY() + selectedShape.getHeight()), 5, 5);
		}
	}

	public Shape getSelectedShape() {
		return selectedShape;
	}

	public Flow getFlow() {
		return flow;
	}
	
}
