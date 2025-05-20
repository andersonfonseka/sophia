package org.sophia.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

import org.sophia.properties.FigureProperties;

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

		
		JButton clear = new JButton("Clear");
		JButton remove = new JButton("Remove");
		JButton play = new JButton("Play");

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
		
		add(remove);
		add(clear);
		add(play);
		
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
