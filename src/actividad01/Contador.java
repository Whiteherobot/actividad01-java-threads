package actividad01;

/**
 * Cuenta las palabras del texto entre las posiciones [inicio, fin).
 * Lo usan tanto la versión secuencial como cada hilo, así ambas aplican el mismo criterio.
 */
public class Contador {

    static final String PALABRA = "quijote";
    static final String SECUENCIA = "ción";

    static Resultado contar(String texto, int inicio, int fin) {
        Resultado r = new Resultado();
        int i = inicio;
        while (i < fin) {
            // saltar espacios y signos de puntuación
            while (i < fin && !Character.isLetter(texto.charAt(i))) i++;

            // una palabra es una secuencia seguida de letras (incluye tildes y ñ)
            int ini = i;
            while (i < fin && Character.isLetter(texto.charAt(i))) i++;
            if (ini == i) break;

            String palabra = texto.substring(ini, i).toLowerCase();
            r.palabras++;
            if (palabra.equals(PALABRA)) r.apariciones++;
            if (palabra.contains(SECUENCIA)) r.conSecuencia++;
        }
        return r;
    }
}
