Echo off
javac mgharmony.java TerminalMenu.java
jar cfm mgharmony.jar manifest.txt Dot.class TerminalMenu.class StylesAndProperties.class mgharmony.class
del *.class