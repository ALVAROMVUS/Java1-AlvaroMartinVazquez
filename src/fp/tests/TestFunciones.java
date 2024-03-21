package fp.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import fp.funciones.Funciones;
public class TestFunciones {

    public static void main(String[] args) {
        testEsPrimo();
        testCombinatorio();
        testFormula();
        testDiferenciaValoresLista();
        testCadenaMasLarga();
    }

    public static void testEsPrimo() {
        System.out.println("Probando la funcion esPrimo:");
        System.out.println("17:" + Funciones.esPrimo(17));
        System.out.println("20:" + Funciones.esPrimo(20));
        System.out.println("497:" + Funciones.esPrimo(497));
        System.out.println();
    }

    public static void testCombinatorio() {
        System.out.println("Probando la funcion combinatorio:");
        System.out.println("C(10, 2) = " + Funciones.combinatorio(10, 2));
        System.out.println("C(5, 3) = " + Funciones.combinatorio(5, 3));
        System.out.println();
    }

    public static void testFormula() {
        System.out.println("Probando la funcion formula:");
        System.out.println("f(3, 2) = " + Funciones.formula(3, 2));
        System.out.println("f(4, 1) = " + Funciones.formula(4, 1));
        System.out.println();
    }

    public static void testDiferenciaValoresLista() {
        System.out.println("Probando la funcion diferenciaValoresLista:");
        List<Integer> listaNumeros = new ArrayList<>(Arrays.asList(10, 15, 20));
        System.out.println("Diferencias: " + Funciones.diferenciaValoresLista(listaNumeros));
        System.out.println();
    }

    public static void testCadenaMasLarga() {
        System.out.println("Probando la funcion cadenaMasLarga:");
        List<String> lista = new ArrayList<>(Arrays.asList("Torremolinos", "Lisboa", "Calañas"));
        System.out.println("Cadena más larga: " + Funciones.cadenaMasLarga(lista));
        System.out.println();
    }
}
