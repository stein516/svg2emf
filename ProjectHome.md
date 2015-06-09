converts SVG(Scalable Vector Graphics) files to EMF(Enhanced Meta File) files using [batik](http://xmlgraphics.apache.org/batik/) and [FreeHEP VectorGraphics](http://java.freehep.org/vectorgraphics/) library
# Download #
  * [Executable JAR](http://svg2emf.googlecode.com/files/svg2emf.jar)
# Test Sample #
  * http://svg2emf.googlecode.com/svn/trunk/SVG2EMF/src/test/java/net/hanjava/svg/SVG2EMFTest.java
```
public void testConvert() throws IOException {
    String svgUrl = "http://upload.wikimedia.org/wikipedia/en/7/7f/Mickey_Mouse.svg";
    File emfFile = new File("mickey.emf");
    SVG2EMF.convert(svgUrl, emfFile);
}
```
## Example SVG ##
![![](http://upload.wikimedia.org/wikipedia/en/thumb/7/7f/Mickey_Mouse.svg/344px-Mickey_Mouse.svg.png)](http://upload.wikimedia.org/wikipedia/en/7/7f/Mickey_Mouse.svg)
## Converted EMF ##
[![](http://svg2emf.googlecode.com/files/emf-ppt.png)](http://svg2emf.googlecode.com/svn/trunk/SVG2EMF/mickey.emf)

[![](http://mac.softpedia.com/base_img/softpedia_free_award_f.gif)](http://mac.softpedia.com/progClean/svg2emf-Clean-63256.html)