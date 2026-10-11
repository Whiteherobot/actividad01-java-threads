# Actividad práctica 01: Procesamiento secuencial y paralelo de textos con Java Threads

**Autor:** Michael Lata · Computación Paralela

El programa analiza *Don Quijote de la Mancha* (Project Gutenberg #2000) y cuenta:

1. El total de palabras.
2. Las apariciones de `quijote`.
3. Las palabras que contienen `ción`.

No distingue mayúsculas de minúsculas. Lo hace primero de forma secuencial y después repartiendo el texto entre varios hilos con `Thread`, `start()` y `join()`.

## Archivos

```
src/actividad01/
├── Main.java          # carga el libro, divide el texto, lanza los hilos y mide los tiempos
├── Contador.java      # cuenta las palabras de un rango del texto (lo usan el secuencial y cada hilo)
├── HiloContador.java  # hilo que procesa un bloque
└── Resultado.java     # los tres conteos y su suma
data/quijote.txt       # libro original
docs/informe.html      # informe de resultados
```

## Cómo ejecutar

Se necesita JDK 17 o superior.

```bash
./scripts/run.sh          # Linux / macOS
scripts\run.bat           # Windows
```

O a mano:

```bash
javac --release 17 -encoding UTF-8 -d out src/actividad01/*.java
java -Xmx2g -cp out actividad01.Main
```

Por defecto prueba con 1, 2 y 4 hilos. Para probar con otros números de hilos, pásalos separados por comas, empezando siempre con 1:

```bash
./scripts/run.sh 1,2,4,8,16,20,32
```

En VS Code basta abrir la carpeta y ejecutar `Main` con F5.

## Cómo se mide

- El archivo se carga en memoria antes de medir. El tiempo se toma con `System.nanoTime()` y cuenta solo el procesamiento.
- Cada configuración se ejecuta 3 veces sin medir (calentamiento del JIT) y luego 10 veces. Se reporta el promedio.
- La versión ampliada repite el libro 100 veces.
- En cada ejecución se comprueba que el resultado paralelo sea igual al secuencial.
