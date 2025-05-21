package org.sophia.elements;

public abstract class FigureFactory {

	
	
	public abstract FigureDiamond createFigureDiamond();
	
	public abstract FigureEllipse createFigureEllipse();

	public abstract FigureLine createFigureLine();
	
	public abstract FigureRectangle createFigureRectangle();
	
	public abstract FigureRoundedRectangle createFigureRoundedRectangle();
	
}
