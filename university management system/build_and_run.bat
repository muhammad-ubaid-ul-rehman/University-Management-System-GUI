@echo off
REM University Management System - Compile and Run Script
REM This script compiles and runs the application with all dependencies

setlocal enabledelayedexpansion

echo.
echo =====================================================
echo  University Management System - Build & Run
echo =====================================================
echo.

REM Check if lib folder exists
if not exist "lib" (
    echo ERROR: lib folder not found!
    echo Please download dependencies first using: powershell -File download_dependencies.ps1
    pause
    exit /b 1
)

REM Check if key dependencies exist
if not exist "lib\mysql-connector-j-9.0.0.jar" (
    echo ERROR: mysql-connector-j-9.0.0.jar not found!
    pause
    exit /b 1
)

if not exist "lib\jcalendar-1.4.jar" (
    echo ERROR: jcalendar-1.4.jar not found!
    pause
    exit /b 1
)

if not exist "lib\rs2xml.jar" (
    echo WARNING: rs2xml.jar not found - some features may not work!
    echo Download rs2xml.jar manually and place in lib/ folder
    echo.
)

REM Create bin directory
if not exist "bin" mkdir bin

REM Set classpath
setlocal
set CLASSPATH=lib\*
echo Classpath: !CLASSPATH!
echo.

REM Compile
echo [1/2] Compiling Java files...
javac -cp "lib\*" -d bin src/university/management/system/*.java

if %ERRORLEVEL% neq 0 (
    echo ERROR: Compilation failed!
    echo Check compilation output above
    pause
    exit /b 1
)

echo.
echo Compilation successful!
echo.

REM Run
echo [2/2] Running application...
java -cp "bin;lib\*" university.management.system.Main

endlocal
