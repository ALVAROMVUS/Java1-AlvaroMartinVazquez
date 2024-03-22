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
        testfuncionp2();
        testfuncionc2();
        testfuncions2();
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
//DEFENSA--------------------------------
    
    public static void testfuncionp2() {
        System.out.println("Probando la funcion P2:");
        System.out.println("P2(7,2,2) = " + Funciones.funcionP2(7, 2,2));
        System.out.println("P2(6,3,1) = " + Funciones.funcionP2(6, 3,1));
        System.out.println();
        
    }
    
    public static void testfuncionc2() {
        System.out.println("Probando la funcion C2:");
        System.out.println("C2(10, 2) = " + Funciones.funcionC2(10, 2));
        System.out.println("C2(6, 2) = " + Funciones.funcionC2(6, 2));
        System.out.println();
        
    }
    
    public static void testfuncions2() {
        System.out.println("Probando la funcion S2:");
        System.out.println("S2(3, 2) = " + Funciones.funcionS2(4, 2));
        System.out.println("S2(6, 2) = " + Funciones.funcionS2(4, 3));
        System.out.println();
    }
}
