package org.sophia.properties;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Properties extends JFrame {
	
	public Properties(){}
	
	public Properties(String title){
		setTitle(title);
		setSize(new Dimension(400, 250));
		
		int posX = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 400) / 2;
		int posY = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 250) / 2;
		
		setLocation(posX, posY);
		setVisible(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
 
	
}
