package net.hanjava.svg;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Document;

/**
 * @author behumble@hanjava.net
 */
public class SVG2EMF {

	private SVG2EMF() {
	}

	public static void convert(String svgUri, File emfFile) throws IOException {
		UserAgentAdapter ua = new UserAgentAdapter();
		DocumentLoader loader = new DocumentLoader(ua);
		try {
			BridgeContext bridgeContext = new BridgeContext(ua, loader);
			try {
			  // load an SVGDocument.
				Document svgDocument = loader.loadDocument(svgUri);

				// build a GVTTree
				GraphicsNode rootNode = buildGVTTree(bridgeContext, svgDocument);
				
				// write EMF into a file
				OutputStream emfStream = new FileOutputStream(emfFile);
				writeEmf(rootNode, emfStream);
				emfStream.flush();
				emfStream.close();
			} finally {
				// dispose bridge context
				bridgeContext.dispose();
			}
		} finally {
			// dispose loader
			loader.dispose();
		}
	}

	public static void convert(String svgUri, InputStream svgStream, OutputStream emfStream) throws IOException {
		UserAgentAdapter ua = new UserAgentAdapter();
		DocumentLoader loader = new DocumentLoader(ua);
		try {
			BridgeContext bridgeContext = new BridgeContext(ua, loader);
			try {
			  // load an SVGDocument.
				Document svgDocument = loader.loadDocument(svgUri, svgStream);

				// build a GVTTree
				GraphicsNode rootNode = buildGVTTree(bridgeContext, svgDocument);
				
			  // write EMF into a stream
				writeEmf(rootNode, emfStream);
			} finally {
				// dispose bridge context
				bridgeContext.dispose();
			}
		} finally {
			// dispose loader
			loader.dispose();
		}
	}

	private static GraphicsNode buildGVTTree(BridgeContext bridgeContext, Document svgDoc) {
		GVTBuilder gvtBuilder = new GVTBuilder();
		GraphicsNode rootNode = gvtBuilder.build(bridgeContext, svgDoc);
		return rootNode;
	}

	private static void writeEmf(GraphicsNode rootNode, OutputStream emfStream) throws IOException {
		// x,y can be non-(0,0)
		Rectangle2D bounds = rootNode.getBounds();
		int w = (int) (bounds.getX() + bounds.getWidth());
		int h = (int) (bounds.getY() + bounds.getHeight());

		Dimension size = new Dimension(w, h);
		EmfWriterGraphics eg2d = new EmfWriterGraphics(emfStream, size);
		eg2d.setDeviceIndependent(true);
		eg2d.startExport();
		rootNode.paint(eg2d);
		eg2d.endExport();
	}
}