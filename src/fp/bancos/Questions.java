package fp.bancos;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.ejemplos_b1_tipos.Persona;

public class Questions {

    // Vencimiento de los prestamos de un cliente
    public static Set<LocalDate> vencimientoDePrestamosDeCliente(Banco banco, String dni) {
        return banco.prestamosDeCliente(dni).stream()
                .map(Prestamo::fechaVencimiento)
                .collect(Collectors.toSet());
    }

    // Persona con más prestamos
    public static Persona clienteConMasPrestamos(Banco banco) {
        return banco.personas().todos().stream()
                .max(Comparator.comparingInt(p -> banco.prestamosDeCliente(p.dni()).size()))
                .orElse(null);
    }

    // Cantidad total de los crétditos gestionados por un empleado
    public static Double cantidadPrestamosEmpleado(Banco banco, String dni) {
        return banco.prestamosDeEmpleado(dni).stream()
                .mapToDouble(Prestamo::cantidad)
                .sum();
    }

    // Empleado más longevo
    public static Persona empleadoMasLongevo(Banco banco) {
        return banco.empleadoMasJoven().orElse(null);
    }

    // Interés mínimo, máximo y medio de los préstamos
    public static record Info(Double min, Double max, Double mean) {
        public String toString() {
            return String.format("(%.2f,%.2f,%.2f)", this.min(), this.max(), this.mean());
        }
    }

    public static Info rangoDeInteresDePrestamos(Banco banco) {
        Double min = banco.prestamos().todos().stream()
                .mapToDouble(Prestamo::interes)
                .min()
                .orElse(0.0);
        Double max = banco.prestamos().todos().stream()
                .mapToDouble(Prestamo::interes)
                .max()
                .orElse(0.0);
        Double mean = banco.prestamos().todos().stream()
                .mapToDouble(Prestamo::interes)
                .average()
                .orElse(0.0);
        return new Info(min, max, mean);
    }

    // Número de préstamos por mes y año
    public static record Info2(Integer mes, Integer año) {
        public String toString() {
            return String.format("(%d,%d)", this.mes(), this.año());
        }
    }

    public static Map<Info2, Long> numPrestamosPorMesAño(Banco banco) {
        return banco.prestamos().todos().stream()
                .collect(Collectors.groupingBy(p -> new Info2(p.fechaComienzo().getMonthValue(), p.fechaComienzo().getYear()), Collectors.counting()));
    }
}
