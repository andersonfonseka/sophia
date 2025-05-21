package org.sophia.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.Objects;
import java.util.UUID;

public class FigureLine extends Line2D.Double implements Shape {
	
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
	
	public String getSource() {
		return this.source.getId();
	}

	public String getTarget() {
		return this.target.getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public void draw(Graphics2D g) {
		
		if (source instanceof FigureDiamond) {
			x1 = source.getCenterX()+(source.getWidth()/3);
		} else {
			x1 = source.getCenterX()+(source.getWidth()/2);
		}
		
		y1 = source.getCenterY();
		y2 = target.getCenterY();
		
		if (target instanceof FigureDiamond) {
			x2 = target.getCenterX()-(target.getWidth()/3);
		} else {
			x2 = target.getCenterX()-(target.getWidth()/2);
		}
		
		g.setStroke(new BasicStroke(3));
		g.setColor(Color.BLACK);
		g.draw(this);
		
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

	@Override
	public void setId(String id) {}

	@Override
	public Color getBorderColor() {return null;}

	@Override
	public void setBorderColor(Color borderColor) {}

	@Override
	public Color getFillColor() {return null;}

	@Override
	public void setFillColor(Color color) {}

	@Override
	public String getTitle() {return null;}

	@Override
	public double getX() {return 0;}

	@Override
	public double getY() {return 0;}

	@Override
	public double getWidth() {return 0;}

	@Override
	public double getHeight() {return 0;}

	@Override
	public double getCenterX() {return 0;}

	@Override
	public double getCenterY() {return 0;}

	public boolean isSelected(Point point) {
		boolean selected = false;
		if ((this.getX1() <= point.getX() && this.getX2() >= point.getX()) &&
			(this.getY1() <= point.getY() && this.getY2() >= point.getY())) {
			selected = true;
			System.out.println(this);
		}
		return selected;
	}	

	@Override
	public void move(int x, int y) {}

	@Override
	public String getDescription() {return null;}

	@Override
	public void setName(String text) {}

	@Override
	public void setDescription(String text) {}

	@Override
	public String getName() {return null;}

	@Override
	public String getVariable() {return null;}

	@Override
	public void setVariable(String variable) {}
	
	public void drawSelectionState(Graphics2D g2) {
		if (g2 != null) {
			g2.setColor(Color.BLACK);

			g2.drawRect((int) this.getX1() - 5, (int) this.getY1() - 5, 5, 5);
			g2.fillRect((int) this.getX1() - 5, (int) this.getY1() - 5, 5, 5);

			g2.drawRect((int) (this.getX2()), (int) this.getY2() - 5, 5, 5);
			g2.fillRect((int) (this.getX2()), (int) this.getY2() - 5, 5, 5);
			
		}
	}

	@Override
	public void setTitle(String value) {
		// TODO Auto-generated method stub
		
	}
	
}
