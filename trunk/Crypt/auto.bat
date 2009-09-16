echo off
javac -sourcepath src -classpath bin -d bin src\*.java
move /Y crypto.jar bin/crypto.jar
cd bin
jar uf crypto.jar *
move /Y crypto.jar ../crypto.jar
cd..

