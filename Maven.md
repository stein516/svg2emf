# Introduction #

svg2emf can also be found from Maven. Project is currently hosted in Google Code SVN repository, so you need to add following code into your pom.xml:

```
<repositories>
   <repository>
      <id>svg2emf.googlecode.com</id>
      <url>http://svg2emf.googlecode.com/svn/m2/releases</url>
   </repository>
</repositories>
```

Or if you want also to use snapshot releases you need to add following lines:

```
<repositories>
   <repository>
      <id>svg2emf.googlecode.com</id>
      <url>http://svg2emf.googlecode.com/svn/m2/releases</url>
   </repository>
   <repository>
      <id>svg2emf.googlecode.com</id>
      <url>http://svg2emf.googlecode.com/svn/m2/snapshots</url>
   </repository>
</repositories>
```