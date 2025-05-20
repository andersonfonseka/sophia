package org.sophia.model;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class EventStrategy {
	
	private Flow flow;
	
	public EventStrategy(Flow flow) {
		super();
		this.flow = flow;
	}

	public void execute(Event event) {
		
		if (event != null && (event.getType().equals("FigureRectangle") || event.getType().equals("FigureEllipse"))) {

			String conteudo = replaceVariables(event);
			
			if (event.getVariable() != null && event.getVariable().trim().length() == 0) {
				JOptionPane.showMessageDialog(null, conteudo, event.getTitle(), JOptionPane.INFORMATION_MESSAGE);
				execute(event.next());
			} else {
				String value = JOptionPane.showInputDialog(null, conteudo, event.getTitle(), JOptionPane.QUESTION_MESSAGE);
				flow.addVariable(event.getVariable(), value);
				execute(event.next());
			}
			
		} else if (event != null && event.getType().equals("Fallback")) {
		
			System.out.println("Não existem mais eventos a serem executados.\nFluxo finalizado.");
		
		} else if (event != null && event.getType().equals("FigureDiamond")) {
			
			String conteudo = replaceVariables(event);
			
			JFrame jFrame = new JFrame();
			jFrame.setLayout(new FlowLayout());
			
			jFrame.add(new JLabel(conteudo));
			
			JButton[] buttons = new JButton[event.totalChildEvents()];
			Event[] ev = event.getEvents();
			
			for (int i = 0; i < buttons.length; i++) {
				
				buttons[i] = new JButton(ev[i].getTitle());
				jFrame.add(buttons[i]);
				
				String id = ev[i].getId();

				buttons[i].addActionListener(new ActionListener() {
				
					@Override
					public void actionPerformed(ActionEvent e) {
						execute(event.next(id));
						jFrame.setVisible(false);
					}
				});
			}
			
			jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			jFrame.setTitle(event.getTitle());
			jFrame.setSize(new Dimension(320, 240));
			jFrame.setVisible(true);
		}
	}

	private String replaceVariables(Event event) {
		String[] values = flow.getVariables();
		String text = event.getContent();
		for (int i = 0; i < values.length; i++) {
			text = text.replace("[" + values[i] + "]", flow.getValue(values[i]));
		}
		
		String conteudo = text;
		return conteudo;
	}
}
