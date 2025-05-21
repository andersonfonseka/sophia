package org.sophia.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Objects;
import java.util.UUID;

public class FigureDiamond extends Polygon implements Shape {

	private static final long serialVersionUID = 1L;

	private String id = UUID.randomUUID().toString();
	
	private Color fillColor = Color.YELLOW;
	
	private String title = "OPTIONS";
	
	private String name = "";
	
	private String description = "";
	
	private String variable = "";
	
	private Color borderColor = Color.BLACK;
	
	private Polygon _polygon;
	
	private int x;
	
	private int y;
	
	public FigureDiamond(int x, int y) {
		this.x = x;
		this.y = y;
		_polygon = createDiamond(x, y);
	}
	
    private Polygon createDiamond(int cx, int cy) {
        int half = 70 / 2;
        int[] xPoints = {this.x, this.x + half, this.x, this.x - half};
        int[] yPoints = {this.y - half, this.y, this.y + half, this.y};
        return new Polygon(xPoints, yPoints, 4);
    }
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Color getBorderColor() {
		return this.borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public Color getFillColor() {
		return this.fillColor;
	}

	public void setFillColor(Color color) {
		this.fillColor = color;
	}

	public boolean isSelected(Point point) {
		boolean selected = false;
		
		if ((point.x >= (this.x-35) && point.x <= (this.x + 35)) &&
			(point.y >= this.y-35 && point.y <= (this.y + 35))) {
			selected = true;
		}

		return selected;
	}

	public void draw(Graphics2D g) {
		
		g.setColor(this.getFillColor());
		g.fill(this._polygon);

		g.setColor(this.getBorderColor());
		g.setStroke(new BasicStroke(2));
		
		drawCenteredString(g, this.name, this, new Font("Arial", Font.BOLD, 10));
		
		g.draw(this._polygon);
	}
	
	public void drawCenteredString(Graphics2D g, String text, Shape shape, Font font) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X coordinate for the text
	    int x = (int) ((shape.getX()-(shape.getWidth()/2)) + ((shape.getWidth() - metrics.stringWidth(text))/2));
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = (int) (shape.getY() + ((shape.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent());
	    // Set the font
	    g.setFont(font);
	    // Draw the String
	    g.drawString(text, x, y);
	}
	
	public void drawSelectionState(Graphics2D g2) {
		if (g2 != null) {
			g2.setColor(Color.BLACK);

			g2.drawRect((int) this.x - 35, this.y - 35, 5, 5);
			g2.fillRect((int) this.x - 35, this.y - 35, 5, 5);

			g2.drawRect((int) this.x+35, this.y - 35, 5, 5);
			g2.fillRect((int) this.x+35, this.y - 35, 5, 5);

			g2.drawRect((int) this.x - 35, this.y + 35, 5, 5);
			g2.fillRect((int) this.x - 35, this.y + 35, 5, 5);

			g2.drawRect(this.x + 35, this.y + 35, 5, 5);
			g2.fillRect(this.x + 35, this.y + 35, 5, 5);
		}
	}

	public void move(int x, int y) {

		this.x = x;
		this.y = y;
		
		_polygon = createDiamond(x, y);
	}

	@Override
	public double getX() {
		return this.x;
	}

	@Override
	public double getY() {
		return this.y;
	}

	@Override
	public double getWidth() {
		return 100;
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public double getCenterX() {
		return this.x;
	}

	@Override
	public double getCenterY() {
		// TODO Auto-generated method stub
		return this.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FigureDiamond other = (FigureDiamond) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public void setName(String text) {
		this.name = text;
	}

	@Override
	public void setDescription(String text) {
		this.description = text;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	@Override
	public String getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTitle(String value) {
		// TODO Auto-generated method stub
		
	}

	
}
