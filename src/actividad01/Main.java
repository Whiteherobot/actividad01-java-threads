package actividad01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    static final int REPETICIONES = 10;   // ejecuciones que se promedian
    static final int CALENTAMIENTO = 3;   // ejecuciones previas que no se miden (JIT)
    static final int VECES_AMPLIADO = 100;

    public static void main(String[] args) throws Exception {
        // Hilos a probar. Por defecto 1, 2 y 4; se puede pasar otra lista, ej: 1,2,4,8,16
        int[] hilos = {1, 2, 4};
        if (args.length > 0) {
            String[] partes = args[0].split(",");
            hilos = new int[partes.length];
            for (int i = 0; i < partes.length; i++) hilos[i] = Integer.parseInt(partes[i].trim());
        }

        // El archivo se carga en memoria antes de medir
        String original = cargar("data/quijote.txt");
        String ampliado = (original + "\n").repeat(VECES_AMPLIADO);

        Resultado esperadoOriginal = secuencial(original);
        Resultado esperadoAmpliado = secuencial(ampliado);
        System.out.println("Núcleos lógicos: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Original: " + esperadoOriginal);
        System.out.println("Ampliado: " + esperadoAmpliado);
        System.out.println();

        System.out.println("Conjunto | Hilos          | Tiempo promedio (ms) | Aceleración");
        medir("Original", original, esperadoOriginal, hilos);
        medir("Ampliado", ampliado, esperadoAmpliado, hilos);
    }

    /** Lee el libro y quita el encabezado y la licencia de Project Gutenberg. */
    static String cargar(String ruta) throws IOException {
        String texto = Files.readString(Path.of(ruta));
        int inicio = texto.indexOf('\n', texto.indexOf("*** START OF")) + 1;
        int fin = texto.indexOf("*** END OF");
        return texto.substring(inicio, fin);
    }

    static Resultado secuencial(String texto) {
        return Contador.contar(texto, 0, texto.length());
    }

    static Resultado paralelo(String texto, int numHilos) throws InterruptedException {
        int[] cortes = dividir(texto, numHilos);

        HiloContador[] hilos = new HiloContador[numHilos];
        for (int i = 0; i < numHilos; i++) {
            hilos[i] = new HiloContador(texto, cortes[i], cortes[i + 1]);
            hilos[i].start();
        }

        Resultado total = new Resultado();
        for (HiloContador hilo : hilos) {
            hilo.join();
            total.sumar(hilo.resultado);
        }
        return total;
    }

    /** Divide el texto en partes de tamaño parecido sin cortar ninguna palabra. */
    static int[] dividir(String texto, int partes) {
        int[] cortes = new int[partes + 1];
        cortes[partes] = texto.length();
        for (int i = 1; i < partes; i++) {
            int corte = (int) ((long) texto.length() * i / partes);
            // si el corte cae dentro de una palabra, se mueve hasta el final de esa palabra
            while (corte < texto.length() && Character.isLetter(texto.charAt(corte))) corte++;
            cortes[i] = corte;
        }
        return cortes;
    }

    /** Mide cada configuración de hilos y muestra el tiempo promedio y la aceleración. */
    static void medir(String nombre, String texto, Resultado esperado, int[] hilos) throws InterruptedException {
        double tiempoSecuencial = 0;
        for (int h : hilos) {
            double suma = 0;
            for (int r = 0; r < CALENTAMIENTO + REPETICIONES; r++) {
                long t0 = System.nanoTime();
                Resultado res = (h == 1) ? secuencial(texto) : paralelo(texto, h);
                long t1 = System.nanoTime();

                if (!res.igualA(esperado)) throw new IllegalStateException("Con " + h + " hilos el resultado no coincide");
                if (r >= CALENTAMIENTO) suma += (t1 - t0) / 1_000_000.0;
            }
            double promedio = suma / REPETICIONES;
            if (h == 1) tiempoSecuencial = promedio;
            System.out.printf("%-8s | %-14s | %20.3f | %.2f%n",
                    nombre, h == 1 ? "1 (secuencial)" : h, promedio, tiempoSecuencial / promedio);
        }
    }
}
