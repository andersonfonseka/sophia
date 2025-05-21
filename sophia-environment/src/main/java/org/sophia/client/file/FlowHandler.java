package org.sophia.client.file;

import org.sophia.editor.Editor;
import org.sophia.elements.Shape;
import org.sophia.model.Event;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class FlowHandler extends DefaultHandler {
	
	private Editor editor;
	
	public FlowHandler(Editor editor) {
		super();
		this.editor = editor;
		this.editor.clear();
	}

	@Override
	public void startDocument() throws SAXException {
		System.out.println("Iniciando analise");
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("Finalizando analise");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals("Node")) {
			String figureType = attributes.getValue("type");

			int positionX = Integer.valueOf(attributes.getValue("position-x")).intValue();
			int positionY = Integer.valueOf(attributes.getValue("position-y")).intValue();

			Shape shp = null;
			
			if (figureType.equals("FigureRectangle")) {
				shp = editor.createRectangle(attributes.getValue("id"), positionX, positionY);
			} else if (figureType.equals("FigureEllipse")){
				shp = editor.createEllipse(attributes.getValue("id"), positionX, positionY, "START");
			} if (figureType.equals("FigureDiamond")){
				shp = editor.createDiamond(attributes.getValue("id"), positionX, positionY);
			}
			
			if (shp != null) {
				shp.setTitle(attributes.getValue("title"));
				shp.setName(attributes.getValue("name"));
				shp.setVariable(attributes.getValue("variable"));
				shp.setDescription(attributes.getValue("description"));
				
				Event event = editor.getFlow().getEvent(shp.getId());
				event.setTitle(shp.getName());
				event.setContent(shp.getDescription());
				event.setVariable(shp.getVariable());
			}

			
		} else if (qName.equals("Connection")) {
			
			String source = attributes.getValue("source");
			String target = attributes.getValue("target");
			
			editor.createLine(attributes.getValue("id"), source, target);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("Nodes")) {
			editor.repaint();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		System.out.println(ch);
	}
	
	

}
