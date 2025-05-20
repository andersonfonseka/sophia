package org.sophia.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JToolBar;

import org.sophia.properties.FigureProperties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class EditorToolBar extends JToolBar {
	
	private Editor editor;
	
	private String operation = "draw";
	
	private String figureType = "";

	public EditorToolBar(Editor editor) {
		
		this.editor = editor;
		
		setOrientation(JToolBar.VERTICAL);
		
		JButton line = new JButton(Util.getImageIcon("/img/assoico.jpg"));

		JButton drawe = new JButton(Util.getImageIcon("/img/end.png"));
		JButton drawend = new JButton(Util.getImageIcon("/img/start.jpg"));
		
		
		JButton drawr = new JButton(Util.getImageIcon("/img/brain.png"));
		JButton drawrr = new JButton(Util.getImageIcon("/img/agent.png"));
		JButton diamond = new JButton(Util.getImageIcon("/img/decision.jpg"));
		
		JButton select = new JButton(Util.getImageIcon("/img/selecionar.png"));
		JButton move = new JButton(Util.getImageIcon("/img/mover.jpg"));
		JButton properties = new JButton(Util.getImageIcon("/img/propico.jpg"));

		JButton play = new JButton(Util.getImageIcon("/img/play.png"));
		
		JButton clear = new JButton(Util.getImageIcon("/img/wipe.png"));
		JButton remove = new JButton(Util.getImageIcon("/img/erase.png"));
		
		JButton save = new JButton(Util.getImageIcon("/img/save.png"));
		

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        	
					StringBuilder sb = new StringBuilder();
					sb.append("<Nodes>\n");
				
		        	Collection<Shape> col = editor.getFigureMap().values();
		        	XmlMapper mapper = new XmlMapper();
		        	
		        	for (Shape shape : col) {
		        		if (!(shape instanceof FigureLine)) {
							sb.append("<Node id='" + shape.getId() + "' type='" + shape.getClass().getSimpleName() + "'>\n");
								sb.append("<title>" + shape.getTitle() + "</title>\n");
								sb.append("<name>" + shape.getName() + "</name>\n");
								sb.append("<variable>" + shape.getVariable() + "</variable>\n");
								sb.append("<description>" + shape.getDescription() + "</description>\n");
								sb.append("<fillColor>" + shape.getFillColor() + "</fillColor>\n");
								sb.append("<borderColor>" + shape.getBorderColor() + "</borderColor>\n");
								sb.append("<position-x>" + shape.getX() + "</position-x>\n");
								sb.append("<position-y>" + shape.getY() + "</position-y>\n");
							sb.append("</Node>\n");
		        		}
		        	}
			
					for (Shape shape : col) {
						if (shape instanceof FigureLine) {
							sb.append("<Connection id='" + shape.getId() + "' type='" + shape.getClass().getSimpleName() + "'>\n");
								sb.append("<source>" + shape.getSource() + "</source>\n");
								sb.append("<target>" + shape.getTarget() + "</target>\n");
							sb.append("</Connection>\n");
						}
					}
		        	
		        	sb.append("</Nodes>");
		        	
		        	System.out.println(sb.toString());
		        	
		}});


		properties.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operation = "selection";
				FigureProperties.getInstance(editor, editor.getSelectedShape());
			}
		});
		
		
		line.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operation = "selection";
				figureType = "line";
			}
		});
		
		drawe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operation = "draw";
				figureType = "ellipse-start";
			}
		});
		
		drawend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operation = "draw";
				figureType = "ellipse-end";
			}
		});


		drawr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operation = "draw";
				figureType = "rectangle";
			}
		});

		
		drawrr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operation = "draw";
				figureType = "roundrectangle";
			}
		});

		diamond.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operation = "draw";
				figureType = "diamond";
			}
		});

		
		move.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operation = "move";
			}
		});
		
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operation = "play";
				editor.repaint();
			}
		});

		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operation = "clear";
				editor.repaint();
			}
		});

		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operation = "remove";
				editor.repaint();
			}
		});

		select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operation = "selection";
			}
		});
		
		add(save);
		
		addSeparator();
		
		add(drawe);
		add(drawend);
		
		addSeparator();
		
		add(drawr);
		add(drawrr);
		add(diamond);
		add(line);
		
		addSeparator();
		
		add(select);
		add(move);
		add(properties);
		
		addSeparator();
		
		add(play);
		
		addSeparator();
		
		add(remove);
		add(clear);
		
		
	}
	
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getFigureType() {
		return figureType.trim();
	}

	public void setFigureType(String figureType) {
		this.figureType = figureType;
	}
	
	
}
