package fp.bancos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Cuenta {

    private String iban;
    private String dni;
    private LocalDate fechaCreacion;
    private Double saldo;

    // Constructor privado
    private Cuenta(String iban, String dni, LocalDate fechaCreacion, Double saldo) {
        this.iban = iban;
        this.dni = dni;
        this.fechaCreacion = fechaCreacion;
        this.saldo = saldo;
    }

    // Método factoría
    public static Cuenta of(String iban, String dni, LocalDate fechaCreacion, Double saldo) {
        return new Cuenta(iban, dni, fechaCreacion, saldo);
    }

    // Método parse
    public static Cuenta parse(String text) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String[] partes = text.split(",");
        String iban = partes[0].strip();
        String dni = partes[1].strip();
        LocalDate fechaCreacion = LocalDate.parse(partes[2].strip(), formatter);
        Double saldo = Double.parseDouble(partes[3].strip());
        return Cuenta.of(iban, dni, fechaCreacion, saldo);
    }

    // Métodos observables
    public String getIban() {
        return iban;
    }

    public String getDni() {
        return dni;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public Double getSaldo() {
        return saldo;
    }

    // Métodos de operaciones
    public void ingresar(Double cantidad) {
        this.saldo += cantidad;
    }

    public void retirar(Double cantidad) {
        this.saldo -= cantidad;
    }

    // Representación como cadena
    @Override
    public String toString() {
        return String.format("%s,%.2f", iban, saldo);
    }

    // Criterio de igualdad
    @Override
    public int hashCode() {
        return Objects.hash(iban, dni, fechaCreacion, saldo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cuenta other = (Cuenta) obj;
        return Objects.equals(iban, other.iban) &&
               Objects.equals(dni, other.dni) &&
               Objects.equals(fechaCreacion, other.fechaCreacion) &&
               Objects.equals(saldo, other.saldo);
    }

    // Método main para pruebas
    public static void main(String[] args) {
        Cuenta cuenta = Cuenta.parse("ES12345678901234567890,12345678Z,2023-05-20,1000.50");
        System.out.println(cuenta);
        cuenta.ingresar(500.0);
        System.out.println(cuenta);
        cuenta.retirar(200.0);
        System.out.println(cuenta);
    }
}
