package actividad01;

/** Los tres conteos que pide la actividad. Cada hilo tiene el suyo y al final se suman. */
public class Resultado {

    long palabras;
    long apariciones;
    long conSecuencia;

    void sumar(Resultado otro) {
        palabras += otro.palabras;
        apariciones += otro.apariciones;
        conSecuencia += otro.conSecuencia;
    }

    boolean igualA(Resultado otro) {
        return palabras == otro.palabras
                && apariciones == otro.apariciones
                && conSecuencia == otro.conSecuencia;
    }

    @Override
    public String toString() {
        return palabras + " palabras, " + apariciones + " veces \"" + Contador.PALABRA + "\", "
                + conSecuencia + " palabras con \"" + Contador.SECUENCIA + "\"";
    }
}
