package org.sophia.elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public interface Shape {
	
	public String getId();

	public void setId(String id);
	
	public Color getBorderColor();
	
	public void setBorderColor(Color borderColor);

	public Color getFillColor();

	public void setFillColor(Color color);
	
	public String getTitle();
	
	public double getX();

	public double getY();

	public double getWidth();

	public double getHeight();
	
	public double getCenterX();

	public double getCenterY();
	
	public boolean isSelected(Point point);
	
	public void draw(Graphics2D g);
	
	public void move(int x, int y);

	public String getDescription();

	public void setName(String text);

	public void setDescription(String text);

	public String getName();
	
	public String getVariable();
	
	public void setVariable(String variable);
	
	public String getSource();

	public String getTarget();
	
}
