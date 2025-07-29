#!/bin/bash

JAR_NAME="tsp-solver.jar"

rm -rf out
mkdir out

echo "Compilando clases..."
find . -name "*.java" > sources.txt
javac -d out @sources.txt || { echo "Error al compilar"; exit 1; }

echo "Generando $JAR_NAME..."
jar cfm $JAR_NAME manifest.txt -C out .

rm sources.txt

