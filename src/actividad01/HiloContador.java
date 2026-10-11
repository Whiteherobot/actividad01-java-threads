package actividad01;

/** Hilo que cuenta las palabras de un bloque del texto. */
public class HiloContador extends Thread {

    private final String texto;
    private final int inicio;
    private final int fin;
    Resultado resultado;

    HiloContador(String texto, int inicio, int fin) {
        this.texto = texto;
        this.inicio = inicio;
        this.fin = fin;
    }

    @Override
    public void run() {
        resultado = Contador.contar(texto, inicio, fin);
    }
}
