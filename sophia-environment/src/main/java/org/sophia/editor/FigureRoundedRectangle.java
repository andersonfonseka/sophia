package org.sophia.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.util.Objects;
import java.util.UUID;

public class FigureRoundedRectangle extends RoundRectangle2D.Double implements Shape  {

	private static final long serialVersionUID = 1L;

	private String id = UUID.randomUUID().toString();
	
	private Color fillColor = new Color(254, 254, 204);
	
	private String title = "AGENT";
	
	private String name = "";
	
	private String description = "";
	
	private String variable = "";
	
	private Color borderColor = Color.BLACK;
	
	public FigureRoundedRectangle(int x, int y) {
		super(x - 37, y - 25, 75, 50, 20, 20);
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
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void draw(Graphics2D g) {
		
		g.setColor(this.getFillColor());
		g.fillRect((int) this.getX(), (int) this.getY(), (int) this.getWidth(), (int) this.getHeight());

		g.setColor(this.getBorderColor());
		g.setStroke(new BasicStroke(2));
		
		RenderingHints rh =
	            new RenderingHints(RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_ON);

	        rh.put(RenderingHints.KEY_RENDERING,
	               RenderingHints.VALUE_RENDER_QUALITY);

	        g.setRenderingHints(rh);

	        g.setFont(new Font("Purisa", Font.PLAIN, 13));
	
	        Util.drawCenteredString(g, this.name, this, new Font("Arial", Font.BOLD, 10));

		g.draw(this);
	
	}

	public void move(int x, int y) {
		setRoundRect((x - (getWidth() / 2)), (y - (getHeight() / 2)), getWidth(), getHeight(), getArcWidth(), getArcHeight());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FigureRoundedRectangle other = (FigureRoundedRectangle) obj;
		return Objects.equals(id, other.id);
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

	public boolean isSelected(Point point) {
		boolean selected = false;
		if (this.contains(point)) {
			selected = true;
		}
		return selected;
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
	
}
