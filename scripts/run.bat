@echo off
REM Compila y ejecuta. Opcional: lista de hilos, ej: scripts\run.bat 1,2,4,8,16
chcp 65001 >nul
cd /d "%~dp0\.."
javac --release 17 -encoding UTF-8 -d out src\actividad01\*.java || exit /b 1
java -Xmx2g -Dstdout.encoding=UTF-8 -cp out actividad01.Main %*
