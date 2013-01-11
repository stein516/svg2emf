package net.hanjava.svg;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import junit.framework.TestCase;

import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.gvt.GraphicsNode;
import org.freehep.graphicsio.emf.EMFInputStream;
import org.freehep.graphicsio.emf.EMFRenderer;
import org.w3c.dom.Document;

public class Issue5Test extends TestCase {
    
	/**
	 * Test case for https://code.google.com/p/svg2emf/issues/detail?id=5
	 * @throws IOException 
	 */
	public void testIssue5() throws IOException {
		
		// Sanity check
		URL issue5SvgUrl = getClass().getClassLoader().getResource("issue5.svg");
		assertNotNull(issue5SvgUrl);
		File testFile = new File(issue5SvgUrl.getFile());
		assertNotNull(testFile);
		assertTrue(testFile.exists());
		
		UserAgentAdapter ua = new UserAgentAdapter();
		DocumentLoader loader = new DocumentLoader(ua);
		try {
			BridgeContext bridgeContext = new BridgeContext(ua, loader);
			try {
			  // load an SVGDocument.
				Document svgDocument = loader.loadDocument(testFile.toURI().toString());
				
				// Check original svg image size
				Double originalSvgWidth = getSvgSize(svgDocument.getDocumentElement().getAttribute("width"));
				Double originalSvgHeight = getSvgSize(svgDocument.getDocumentElement().getAttribute("height"));
				
				// build a GVTTree
				GVTBuilder gvtBuilder = new GVTBuilder();
				GraphicsNode rootNode = gvtBuilder.build(bridgeContext, svgDocument);
				
				// Check GraphicsNode size
				Double graphicsNodeWidth = rootNode.getSensitiveBounds().getWidth();
				Double graphicsNodeHeight = rootNode.getSensitiveBounds().getHeight();
				assertEquals("Original SVG width does not match GraphicsNode width", originalSvgWidth, graphicsNodeWidth);
				assertEquals("Original SVG height does not match GraphicsNode height", originalSvgHeight, graphicsNodeHeight);
				
				// write EMF 
				ByteArrayOutputStream emfStream = new ByteArrayOutputStream();
				
			  // x,y can be non-(0,0)
				Rectangle2D bounds = rootNode.getBounds();
				int w = (int) (bounds.getX() + bounds.getWidth());
				int h = (int) (bounds.getY() + bounds.getHeight());

				// Check calculated EMF size
				assertEquals("Calculated EMF width does not match original SVG width", Double.valueOf(w), originalSvgWidth);
				assertEquals("Calculated EMF width does not match original SVG height", Double.valueOf(h), originalSvgHeight);
				
				Dimension size = new Dimension(w, h);
				EmfWriterGraphics eg2d = new EmfWriterGraphics(emfStream, size);
				eg2d.setDeviceIndependent(true);
				eg2d.startExport();
				rootNode.paint(eg2d);
				eg2d.endExport();
				
				emfStream.flush();
				emfStream.close();

				byte[] emfData = emfStream.toByteArray();
				
				// Check outputted EMF size
				EMFRenderer emfRenderer = new EMFRenderer(new EMFInputStream(new ByteArrayInputStream(emfData)));
				Dimension emfSize = emfRenderer.getSize();

				assertEquals("Converted EMF width does not match original SVG width", Double.valueOf(emfSize.width), originalSvgWidth);
				assertEquals("Converted EMF height does not match original SVG height", Double.valueOf(emfSize.height), originalSvgHeight);
				
			} finally {
				// dispose bridge context
				bridgeContext.dispose();
			}
		} finally {
			// dispose loader
			loader.dispose();
		}
	}
	
	private Double getSvgSize(String size) {
		return Double.parseDouble(size.endsWith("px") ? size.substring(0, size.length() - 2) : size);
	}

	
}
