package org.sophia.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


public class FigureEllipse extends Ellipse2D.Double implements Shape, Serializable {

	private static final long serialVersionUID = 1L;

	private String id = UUID.randomUUID().toString();
	
	private String type = "START";
	
	private Color fillColor = Color.BLACK;
	
	private String title = "Start/End";
	
	private String name = "";
	
	private String description = "";
	
	private String variable = "";
	
	private Color borderColor = Color.BLACK;
	
	public FigureEllipse() {}
	
	public FigureEllipse(int x, int y) {
		super(x - 20, y - 20, 40, 40);
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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		
		if (type.equals("END")) {
			this.fillColor = Color.BLACK;
		} else if (type.equals("START")){
			this.fillColor = Color.WHITE;
		}
	}

	
	public boolean isSelected(Point point) {
		boolean selected = false;
		if (this.contains(point)) {
			selected = true;
		}
		return selected;
	}	

	public void draw(Graphics2D g) {
		
		g.setColor(this.getFillColor());
		g.fill(this);

		g.setColor(this.getBorderColor());
		g.setStroke(new BasicStroke(3));
		
        Util.drawCenteredString(g, this.name, this, new Font("Arial", Font.BOLD, 10));


		g.draw(this);
	}

	public void move(int x, int y) {
		setFrame(x, y, this.width, this.height);
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
		FigureEllipse other = (FigureEllipse) obj;
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
