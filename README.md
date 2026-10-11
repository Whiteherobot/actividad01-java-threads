# Actividad 01 – Java Threads

Computación Paralela · Michael Lata

Programa en Java que cuenta en *Don Quijote* el total de palabras, cuántas veces aparece "quijote" y cuántas palabras tienen "ción". Primero lo hace de forma secuencial y después dividiendo el texto entre varios hilos (`Thread`, `start()` y `join()`), y compara los tiempos.

El informe con los resultados está en [`docs/informe.html`](docs/informe.html).

## Ejecutar

Requiere JDK 17 o superior.

```bash
./scripts/run.sh        # Linux
scripts\run.bat         # Windows
```

Sin argumentos mide con 1, 2 y 4 hilos. Para usar otros valores:

```bash
./scripts/run.sh 1,2,4,8,16
```

(el primero tiene que ser 1, porque es la referencia secuencial)

## Código

| Archivo | Contenido |
|---|---|
| `Main.java` | carga el libro, divide el texto, lanza los hilos y mide |
| `Contador.java` | cuenta las palabras de un rango del texto |
| `HiloContador.java` | el hilo que procesa cada parte |
| `Resultado.java` | los tres conteos |

Cada prueba se repite 10 veces (más 3 de calentamiento que no se cuentan) y se saca el promedio. La versión ampliada es el libro repetido 100 veces.
