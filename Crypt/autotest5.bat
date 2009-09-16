cls
@echo off
javac -sourcepath src -classpath bin -d bin src\*.java
for /l %%a in (1,1,8) do(
java -DJAVA_OPTS=-Xms256m -Xmx512m -classpath bin Driver5 %%a > log_%%a.txt
rename report.txt report_%%a.dat
)

