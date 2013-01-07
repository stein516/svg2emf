package net.hanjava.svg;

import java.io.File;
import java.net.URL;

import net.hanjava.svg.SVG2EMF;

public class CLI {
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			printUsage();
			System.exit(-1);
		}

		String arg = args[0];
		boolean isFilePath = !arg.contains(":/");
		URL inputUrl = null;
		if (isFilePath) {
			inputUrl = new File(arg).toURI().toURL();
		} else {
			inputUrl = new URL(arg);
		}

		String inputPath = inputUrl.getPath();
		String fileName = inputPath.substring( inputPath.lastIndexOf('/') + 1 );

		System.out.println("[SVG2EMF] input : "+inputUrl);
		File output = new File(fileName+".emf");
		SVG2EMF.convert(inputUrl.toString(), output);
		System.out.println("[SVG2EMF] output : "+output.getAbsolutePath());
	}

	private static void printUsage() {
		String msg = "Usage: svg2emf input.svg";
		System.out.println(msg);
		System.out.println("input can be any local path or remote URL");
		System.out.println("Contact: behumble@hanjava.net");
	}
}