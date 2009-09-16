cls
@echo off
javac -sourcepath src -classpath bin -d bin src\*.java
for /l %%a in (2,1,8) do (
@echo current test %%a
java -classpath bin Driver 3 %%a 0
del report_y1.txt
rename report.txt report_y1.txt
java -DJAVA_OPTS=-Xms256m -Xmx512m -classpath bin Driver2
del report_y0.txt
rename report.txt report_y0.txt
)
