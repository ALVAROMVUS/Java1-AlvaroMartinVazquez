package fp.bancos;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.tools.File2;

public class Cuentas {

	private static Cuentas gestorDeCuentas = null;
	private Set<Cuenta> cuentas;
	private Map<String, Cuenta> cuentasIban;

	// Constructor privado
	private Cuentas(Set<Cuenta> cuentas) {
		this.cuentas = cuentas;
		this.cuentasIban = this.cuentas.stream().collect(Collectors.toMap(c -> c.getIban(), c -> c));
	}

	// Método factoría
	public static Cuentas of() {
		if (Cuentas.gestorDeCuentas == null)
			Cuentas.gestorDeCuentas = Cuentas.parse("bancos/cuentas.txt");
		return Cuentas.gestorDeCuentas;
	}

	// Método de parseo
	public static Cuentas parse(String fichero) {
		Set<Cuenta> cuentas = File2.streamDeFichero(fichero, "UTF-8")
				.map(ln -> Cuenta.parse(ln))
				.collect(Collectors.toSet());
		Cuentas.gestorDeCuentas = new Cuentas(cuentas);
		return Cuentas.gestorDeCuentas;
	}

	// Propiedades
	public Set<Cuenta> todas() {
		return this.cuentas;
	}

	public Optional<Cuenta> cuentaIban(String iban) {
		return Optional.ofNullable(this.cuentasIban.getOrDefault(iban, null));
	}

	public Integer size() {
		return this.cuentas.size();
	}

	public Cuenta cuentaIndex(Integer index) {
		return this.cuentas.stream().toList().get(index);
	}

	// Representación como cadena
	@Override
	public String toString() {
		return this.cuentas.stream()
				.map(c -> c.toString())
				.collect(Collectors.joining("\n", "Cuentas:\n", ""));
	}

	public static void main(String[] args) {
		Cuentas cuentas = Cuentas.of();
		System.out.println(cuentas);
		System.out.println("______________");
		Optional<Cuenta> oc = cuentas.cuentaIban("ES12345678901234567890");
		if (oc.isPresent()) {
			Cuenta c = oc.get();
			System.out.println(c);
			c.ingresar(500.0);
			System.out.println(c);
			c.retirar(200.0);
			System.out.println(c);
		}
		System.out.println("______________");
		System.out.println("Total cuentas: " + cuentas.size());
		System.out.println("Cuenta en índice 0: " + cuentas.cuentaIndex(0));
	}
}
