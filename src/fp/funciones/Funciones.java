package fp.funciones;

import java.util.ArrayList;
import java.util.List;

public class Funciones {

    public static void main(String[] args) {
	}

    public static boolean esPrimo(int numero) {
        for (int n = 2; n < numero; n++) {
            if (numero % n == 0) {
                return false;
            }
        }
        return true;
    }

    public static int combinatorio(int numerador, int orden) {
        if (orden > numerador) {
            throw new IllegalArgumentException("El orden no puede ser mayor que el numerador");
        }

        int productoN = 1;
        for (int numero1 = numerador - orden + 1; numero1 <= numerador; numero1++) {
            productoN *= numero1;
        }

        int productoO = 1;
        for (int numero2 = 1; numero2 <= orden; numero2++) {
            productoO *= numero2;
        }

        return productoN / productoO;
    }

    public static double formula(int n, int k) {
        if (k > n) {
            throw new IllegalArgumentException();
        }

        double parte1 = 1.0 / factorial(k);
        double parte2 = 0;
        for (int i = 0; i <= k; i++) {
            double suma = Math.pow(-1, i) * combinatorio(k, i) * Math.pow(k - i, n);
            parte2 += suma;
        }

        return parte1 * parte2;
    }

    public static List<Integer> diferenciaValoresLista(List<Integer> listaNumeros) {
        if (listaNumeros.size() < 2) {
            throw new IllegalArgumentException("Numeros insuficientes para la función");
        }

        List<Integer> diferencias = new ArrayList<>();
        for (int i = 1; i < listaNumeros.size(); i++) {
            int diferencia = listaNumeros.get(i) - listaNumeros.get(i - 1);
            diferencias.add(diferencia);
        }

        return diferencias;
    }

    public static String cadenaMasLarga(List<String> lista) {
        return lista.stream().max((s1, s2) -> s1.length() - s2.length()).orElse(null);
    }

    public static int funcionP2(int n, int k, int i) {
        if (n < k || i >= k + 1) {
            throw new IllegalArgumentException();
        }

        int resultado = 1;
        for (int producto = i; producto < k - 1; producto++) {
            resultado *= n - i;
        }
        return resultado;
    }


    public static int factorial(int num) {
        int factorial = 1;
        for (int i = 1; i <= num; i++) {
            factorial *= i;
        }
        return factorial;
    }
}