package org.sophia.properties;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.sophia.editor.Editor;
import org.sophia.editor.Shape;
import org.sophia.model.Event;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class FigureProperties extends Properties {
	
	private Editor editor;
	
	private static FigureProperties instance;

	private Shape figureBean;
	
	final JTextField txName = new JTextField(30);

	final JTextArea txDescription = new JTextArea(10, 30);
	
	final JTextField txVariable = new JTextField(30);
	
	public static FigureProperties getInstance(Editor pEditor, Shape pFigureBean){
		if (instance == null) {
			instance = new FigureProperties(pEditor, pFigureBean);
		} else {
			if (!instance.isVisible()){
				instance.figureBean = pFigureBean;
				instance.setTitle(pFigureBean.getTitle());
				instance.txName.setText(pFigureBean.getName());
				instance.txDescription.setText(pFigureBean.getDescription());
				instance.txVariable.setText(pFigureBean.getVariable());
				instance.setVisible(true);
			}
		}
		
		return instance;
	}
	
	private FigureProperties (){
		super();
	}
	
	
	private FigureProperties(Editor pEditor, Shape pFigureBean) {
		
		super(pFigureBean.getTitle());
		
		this.editor = pEditor;

		txDescription.setLineWrap(true);
		
		this.figureBean = pFigureBean;
		
		JPanel jPanel = new JPanel(new MigLayout());
		
		JLabel lbName = new JLabel("Name");
		txName.setText(figureBean.getName());
				
		jPanel.add(lbName, "wrap");
		jPanel.add(txName, "wrap");
		
		JLabel lbDescription = new JLabel("Description");
		txDescription.setText(figureBean.getDescription());
		
		jPanel.add(lbDescription, "wrap");
		jPanel.add(txDescription, "wrap");
		
		JLabel lbVariable = new JLabel("Variable");
		txVariable.setText(figureBean.getVariable());
		
		jPanel.add(lbVariable, "wrap");
		jPanel.add(txVariable, "wrap");
		
		JButton btApply = new JButton("Apply");
		JButton btCancel = new JButton("Cancel");
		
		btApply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				figureBean.setId(figureBean.getId());
				figureBean.setName(txName.getText());
				figureBean.setDescription(txDescription.getText());
				figureBean.setVariable(txVariable.getText());
				
				Event event = editor.getFlow().getEvent(figureBean.getId());
				event.setTitle(figureBean.getName());
				event.setContent(figureBean.getDescription());
				event.setVariable(figureBean.getVariable());
				
				editor.repaint();
				
				doDefaultCloseAction();
			}
		});
		
		btCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doDefaultCloseAction();
			}
		});
		
		jPanel.add(btApply);
		jPanel.add(btCancel);
		
		add(jPanel);
		
	}

	protected void doDefaultCloseAction() {
		this.setVisible(false);
		
	}
	
}
