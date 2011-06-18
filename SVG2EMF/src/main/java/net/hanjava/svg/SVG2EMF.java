package net.hanjava.svg;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
        // build a SVGDocument.
        UserAgentAdapter ua = new UserAgentAdapter();
        DocumentLoader loader = new DocumentLoader(ua);
        BridgeContext cxt = new BridgeContext(ua, loader);
        Document svgDoc = loader.loadDocument(svgUri);
        loader.dispose();

        // Build a GVTTree
        GVTBuilder gvtBuilder = new GVTBuilder();
        GraphicsNode rootNode = gvtBuilder.build(cxt, svgDoc);
        cxt.dispose();

        // x,y can be non-(0,0)
        Rectangle2D bounds = rootNode.getBounds();
        int w = (int)(bounds.getX() + bounds.getWidth());
        int h = (int)(bounds.getY() + bounds.getHeight());

        // write to EmfWriter
        FileOutputStream emfStream = new FileOutputStream(emfFile);
        EmfWriterGraphics eg2d = new EmfWriterGraphics(emfStream, new Dimension(w, h));
        if(GraphicsEnvironment.isHeadless()) {
        	eg2d.setDeviceIndependent(true);
        }
        eg2d.startExport();
        rootNode.paint(eg2d);
        eg2d.dispose();
        eg2d.endExport();
        emfStream.close();
    }
}