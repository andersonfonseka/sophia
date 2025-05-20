package org.sophia.editor;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Util {
	
	
	public static void drawCenteredString(Graphics2D g, String text, Shape shape, Font font) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X coordinate for the text
	    int x = (int) (shape.getX() + (shape.getWidth() - metrics.stringWidth(text)) / 2);
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = (int) (shape.getY() + ((shape.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent());
	    // Set the font
	    g.setFont(font);
	    // Draw the String
	    g.drawString(text, x, y+35);
	}
	
	public static ImageIcon getImageIcon(String path) {
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(Util.class.getResourceAsStream(path));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return new ImageIcon(img);
	}

}
