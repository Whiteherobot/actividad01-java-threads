#!/usr/bin/env bash
# Compila y ejecuta. Opcional: lista de hilos, ej: ./scripts/run.sh 1,2,4,8,16
set -e
cd "$(dirname "$0")/.."
javac --release 17 -encoding UTF-8 -d out src/actividad01/*.java
java -Xmx2g -cp out actividad01.Main "$@"
