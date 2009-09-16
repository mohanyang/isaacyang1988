cls
@echo off
javac -sourcepath src -classpath bin -d bin src\*.java
java -DJAVA_OPTS=-Xms256m -Xmx512m -classpath bin Driver6

