package net.hanjava.svg;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import org.freehep.graphicsio.emf.EMFGraphics2D;

public class EmfWriterGraphics extends EMFGraphics2D {
    static private GraphicsConfiguration waDC;

    public EmfWriterGraphics(OutputStream os, Dimension size) {
        super(os, size);
        // Workaround Image
        BufferedImage waImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        if(waDC == null) {
            waDC = waImg.createGraphics().getDeviceConfiguration();
        }
    }

    protected EmfWriterGraphics(EMFGraphics2D graphics, boolean doRestoreOnDispose) {
        super(graphics, doRestoreOnDispose);
    }

    @Override
    public GraphicsConfiguration getDeviceConfiguration() {
        return waDC;
    }

    @Override
    public Graphics create() {
        // Create a new graphics context from the current one.
        try {
            // Save the current context for restore later.
            writeGraphicsSave();
        } catch(IOException e) {
            handleException(e);
        }
        // The correct graphics context should be created.
        EMFGraphics2D result = new EmfWriterGraphics(this, true);
        return result;
    }
}
