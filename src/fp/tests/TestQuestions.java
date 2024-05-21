package fp.tests;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import fp.bancos.Banco;
import fp.bancos.Questions;
import fp.bancos.Questions.Info2;

import us.lsi.ejemplos_b1_tipos.Persona;


public class TestQuestions {

    public static void main(String[] args) {
        Banco banco = Banco.of();

        testVencimientoDePrestamos(banco);
        testClienteConMasPrestamos(banco);
        testCantidadPrestamosEmpleado(banco);
        testEmpleadoMasLongevo(banco);
        testRangoDeInteresesDePrestamos(banco);
        testNumPrestamosPorMesAño(banco);
    }

    public static void testVencimientoDePrestamos(Banco banco) {
        System.out.println("TEST DE VENCIMIENTO DE PRESTAMOS");
        Set<LocalDate> vencimientos = Questions.vencimientoDePrestamosDeCliente(banco, "64482505G");
        vencimientos.forEach(System.out::println);
    }

    public static void testClienteConMasPrestamos(Banco banco) {
        System.out.println("TEST DE CLIENTE CON MAS PRESTAMOS");
        Persona cliente = Questions.clienteConMasPrestamos(banco);
        System.out.println(cliente);
    }

    public static void testCantidadPrestamosEmpleado(Banco banco) {
        System.out.println("TEST CANTIDAD PRESTAMOS EMPLEADO");
        Double cantidad = Questions.cantidadPrestamosEmpleado(banco, "86921364V");
        System.out.println(cantidad);
    }

    public static void testEmpleadoMasLongevo(Banco banco) {
        System.out.println("TEST EMPLEADO MAS LONGEVO");
        Persona empleado = Questions.empleadoMasLongevo(banco);
        System.out.println(empleado);
    }

    public static void testRangoDeInteresesDePrestamos(Banco banco) {
        System.out.println("TEST DE RANGO DE INTERESES DE PRESTAMOS");
        Questions.Info rango = Questions.rangoDeInteresDePrestamos(banco);
        System.out.println(rango);
    }

    public static void testNumPrestamosPorMesAño(Banco banco) {
        System.out.println("TEST DE NUM PRESTAMOS POR MES AÑO");
        Map<Info2, Long> prestamosPorMesAno = Questions.numPrestamosPorMesAño(banco);
        prestamosPorMesAno.forEach((key, value) -> System.out.println(key + ": " + value));
    }
}
