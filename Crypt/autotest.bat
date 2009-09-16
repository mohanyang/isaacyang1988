cls
@echo off
javac -sourcepath src -classpath bin -d bin src\*.java
for /l %%a in (1,1,8) do (
for /l %%b in (17,1,32) do (
@echo current test %%a  %%b%
java -classpath bin Driver 3 %%a %%b
del report_y1.txt
rename report.txt report_y1.txt
java -classpath bin Driver2
del report_y0.txt
rename report.txt report_y0.txt
)
)
