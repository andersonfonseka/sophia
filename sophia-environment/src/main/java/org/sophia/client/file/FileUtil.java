package org.sophia.client.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.sophia.editor.Editor;
import org.sophia.elements.FigureLine;
import org.sophia.elements.Shape;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class FileUtil {

	public static void saveFile(Editor editor) {

		StringBuilder sb = new StringBuilder();
		
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<Nodes>\n");

		Collection<Shape> col = editor.getFigureMap().values();
		XmlMapper mapper = new XmlMapper();

		for (Shape shape : col) {
			if (!(shape instanceof FigureLine)) {
				sb.append("<Node id=\"" + shape.getId() + "\" type=\"" + shape.getClass().getSimpleName() + "\"");
				sb.append(" title=\"" + shape.getTitle() + "\"");
				sb.append(" name=\"" + shape.getName() + "\"");
				sb.append(" variable=\"" + shape.getVariable() + "\"");
				sb.append(" description=\"" + shape.getDescription() + "\"");
				sb.append(" position-x=\"" + (int) shape.getX() + "\"");
				sb.append(" position-y=\"" + (int) shape.getY() + "\"/>\n");
			}
		}

		for (Shape shape : col) {
			if (shape instanceof FigureLine) {
				sb.append("<Connection id=\"" + shape.getId() + "\" type=\"" + shape.getClass().getSimpleName() + "\"");
				sb.append(" source=\"" + shape.getSource() + "\"");
				sb.append(" target=\"" + shape.getTarget() + "\"/>\n");
			}
		}

		sb.append("</Nodes>");

		System.out.println(sb.toString());

		JFileChooser fileChooser = new JFileChooser();

		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files", "xml");

		fileChooser.setFileFilter(filter);

		int returnVal = fileChooser.showSaveDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			try (FileWriter fw = new FileWriter(fileChooser.getSelectedFile() + ".xml")){
				fw.write(sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public static void openFile(Editor editor) {

		JFileChooser fileChooser = new JFileChooser();

		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files", "xml");

		fileChooser.setFileFilter(filter);

		int returnVal = fileChooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			
			StringBuilder sb = new StringBuilder();

			try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))){
				
				String line;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line);
	            }
	            
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder db = factory.newDocumentBuilder();
	            InputSource inStream = new InputSource();
	            inStream.setCharacterStream(new StringReader(sb.toString()));
	          
	            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	            
	            saxParserFactory.setValidating(true);
	            
	            SAXParser saxParser = saxParserFactory.newSAXParser();
	            saxParser.parse(inStream, new FlowHandler(editor));     
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
	}

}
