package fp.tests;

import java.util.Optional;
import fp.bancos.Banco;

import us.lsi.ejemplos_b1_tipos.Persona;

public class TestBanco {

    private static Banco banco = Banco.of();

    private static void testPrestamosDeEmpleado() {
        System.out.println("TEST DE PRÉSTAMOS DE EMPLEADO");
        System.out.println(banco.prestamosDeEmpleado("29776956Y"));
        System.out.println(banco.prestamosDeEmpleado("66673299W"));
    }

    private static void testPrestamosDeCliente() {
        System.out.println("TEST DE PRÉSTAMOS DE CLIENTE");
        System.out.println(banco.prestamosDeCliente("64482505G"));
        System.out.println(banco.prestamosDeCliente("70272109W"));
    }

    private static void testEmpleadoMasJoven() {
        System.out.println("TEST DE EMPLEADO MÁS JOVEN");
        Optional<Persona> empleadoMasJoven = banco.empleadoMasJoven();
        if (empleadoMasJoven.isPresent()) {
            System.out.println(empleadoMasJoven.get());
        } else {
            System.out.println("No hay empleados.");
        }
    }

    private static void testNumeroCuentasDeCliente() {
        System.out.println("TEST DE NÚMERO DE CUENTAS DE CLIENTE");
        System.out.println(banco.numeroDeCuentasDeCliente());
    }

    public static void main(String[] args) {
        System.out.println("TEST DEL TIPO BANCO:");
        testPrestamosDeEmpleado();
        testPrestamosDeCliente();
        testEmpleadoMasJoven();
        testNumeroCuentasDeCliente();
    }
}
