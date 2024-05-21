package fp.bancos;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.ejemplos_b1_tipos.Persona;

public class Banco {

	private static Banco gestorDeBanco = null;
	private String nombre;
	private Integer codigoPostal;
	private String email;
	private Personas personas;
	private Empleados empleados;
	private Cuentas cuentas;
	private Prestamos prestamos;

	private Banco(String nombre, Integer codigoPostal, String email, Personas personas, Empleados empleados,
			Cuentas cuentas, Prestamos prestamos) {
		super();
		this.nombre = nombre;
		this.codigoPostal = codigoPostal;
		this.email = email;
		this.personas = personas;
		this.empleados = empleados;
		this.cuentas = cuentas;
		this.prestamos = prestamos;
	}

	public static Banco of() {
		String nombre = "Reina Mercedes";
		Integer codigoPostal = 41012;
		String email = "bib@us.es";
		String fp = "src/fp/ficherosbanco/personas.txt";
		String fe = "src/fp/ficherosbanco/empleados.txt";
		String fc = "src/fp/ficherosbanco/cuentas.txt";
		String fpt = "src/fp/ficherosbanco/prestamos.txt";
		return Banco.of(nombre, codigoPostal, email, fp, fe, fc, fpt);
	}

	public static Banco of(String nombre, Integer codigoPostal, String email, String fp, String fe, String fc,
			String fpt) {
		if (Banco.gestorDeBanco == null) {
			Personas personas = Personas.parse(fp);
			Empleados empleados = Empleados.parse(fe);
			Cuentas cuentas = Cuentas.parse(fc);
			Prestamos prestamos = Prestamos.parse(fpt);
			Banco.gestorDeBanco = new Banco(nombre, codigoPostal, email, personas, empleados, cuentas, prestamos);
		}
		return Banco.gestorDeBanco;
	}

	public Personas personas() {
		return this.personas;
	}

	public Empleados empleados() {
		return this.empleados;
	}

	public Cuentas cuentas() {
		return this.cuentas;
	}

	public Prestamos prestamos() {
		return this.prestamos;
	}

	// Préstamos gestionados por un empleado

	public Set<Prestamo> prestamosDeEmpleado(String dni) {
		return this.prestamos.todos().stream()
				.filter(p -> p.dniEmpleado().equals(dni))
				.collect(Collectors.toSet());
	}

	// Préstamos de un cliente

	public Set<Prestamo> prestamosDeCliente(String dni) {
		return this.prestamos.todos().stream()
				.filter(p -> p.dniCliente().equals(dni))
				.collect(Collectors.toSet());
	}

	// Empleado más joven


	public Optional<Persona> empleadoMasJoven() {
	    return this.empleados.todos().stream()
	            .min(Comparator.comparing(empleado -> ((Empleado) empleado).persona().edad()))
	            .map(Empleado::persona);
	}

	// Número de cuentas de cada cliente

	public Map<String, Integer> numeroDeCuentasDeCliente() {
		return this.cuentas.todas().stream()
				.collect(Collectors.groupingBy(Cuenta::getDni, Collectors.summingInt(e -> 1)));
	}

	public void test() {
		Set<String> dnis = this.personas().dnis();
		Set<String> dniCuentas =
				this.cuentas().todas().stream()
						.map(Cuenta::getDni)
						.filter(d -> !dnis.contains(d))
						.collect(Collectors.toSet());
		System.out.println(dniCuentas);
		Set<String> dniEmpleados =
				this.empleados().todos().stream()
						.map(Empleado::dni)
						.filter(d -> !dnis.contains(d))
						.collect(Collectors.toSet());
		System.out.println(dniEmpleados);
		Set<String> dniPrestamosClientes =
				this.prestamos().todos().stream()
						.map(Prestamo::dniCliente)
						.filter(d -> !dnis.contains(d))
						.collect(Collectors.toSet());
		System.out.println(dniPrestamosClientes);
		Set<String> dniPrestamosEmpleados =
				this.prestamos().todos().stream()
						.map(Prestamo::dniEmpleado)
						.filter(d -> !dnis.contains(d))
						.collect(Collectors.toSet());
		System.out.println(dniPrestamosEmpleados);
	}

	public static void main(String[] args) {
		Banco banco = Banco.of();
		banco.test();
	}

}
