package org.sophia.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.Objects;
import java.util.UUID;

public class FigureLine extends Line2D.Double {
	
	private static final long serialVersionUID = 1L;

	private String id = UUID.randomUUID().toString();
	
	private Shape source;
	
	private Shape target;
	
	public FigureLine(Shape source, Shape target) {
		super();
		this.source = source;
		this.target = target;
	}

	public String getId() {
		return id;
	}
	
	public void setSource(Shape source) {
		this.source = source;
	}

	public void setTarget(Shape target) {
		this.target = target;
	}

	public void paint(Graphics2D g) {
		
		x1 = source.getCenterX();
		y1 = source.getCenterY();
		x2 = target.getCenterX();
		y2 = target.getCenterY();
		
		g.setStroke(new BasicStroke(3));
		g.setColor(Color.BLACK);
		g.draw(this);
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
		FigureLine other = (FigureLine) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
}
