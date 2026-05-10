#!/bin/bash
# University Management System - Linux/Mac Build Script

echo ""
echo "====================================================="
echo "  University Management System - Build & Run"
echo "====================================================="
echo ""

# Check if lib folder exists
if [ ! -d "lib" ]; then
    echo "ERROR: lib folder not found!"
    echo "Please download dependencies first"
    exit 1
fi

# Check if key dependencies exist
if [ ! -f "lib/mysql-connector-j-9.3.0.jar" ]; then
    echo "ERROR: mysql-connector-j-9.3.0.jar not found!"
    exit 1
fi

if [ ! -f "lib/jcalendar-1.4.jar" ]; then
    echo "ERROR: jcalendar-1.4.jar not found!"
    exit 1
fi

if [ ! -f "lib/rs2xml.jar" ]; then
    echo "WARNING: rs2xml.jar not found - some features may not work!"
    echo ""
fi

# Create bin directory
mkdir -p bin

# Set classpath
CLASSPATH="lib/*"
echo "Classpath: $CLASSPATH"
echo ""

# Compile
echo "[1/2] Compiling Java files..."
javac -cp "$CLASSPATH" -d bin src/university/management/system/*.java

if [ $? -ne 0 ]; then
    echo "ERROR: Compilation failed!"
    exit 1
fi

echo ""
echo "Compilation successful!"
echo ""

# Run
echo "[2/2] Running application..."
java -cp "bin:lib/*" university.management.system.Main
