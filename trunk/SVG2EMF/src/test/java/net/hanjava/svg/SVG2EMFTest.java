package net.hanjava.svg;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

public class SVG2EMFTest extends TestCase {
    private void doConvert() throws IOException {
        String svgUrl = "http://upload.wikimedia.org/wikipedia/en/7/7f/Mickey_Mouse.svg";
        File emfFile = new File("mickey.emf");
        SVG2EMF.convert(svgUrl, emfFile);
    }

    public void testConvertHeadless() throws IOException {
    	System.setProperty("java.awt.headless", "true");
    	doConvert();
    }

    public void testConvertHeadful() throws IOException {
    	System.setProperty("java.awt.headless","false");
    	doConvert();
    }
}
