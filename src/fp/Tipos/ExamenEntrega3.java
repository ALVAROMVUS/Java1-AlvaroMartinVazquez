package fp.Tipos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import fp.bancos.Banco;
import fp.bancos.Empleado;
import fp.bancos.Prestamo;
import us.lsi.ejemplos_b1_tipos.Persona;

public class ExamenEntrega3 {
	
	    public static List<Empleado> empleadosEntreDiaMes(Banco banco, String ini, String fin) {
	        // Parse the ini and fin strings to get the day and month
	        LocalDate iniDate = LocalDate.parse("2000/" + ini, DateTimeFormatter.ofPattern("yyyy/dd/MM"));
	        LocalDate finDate = LocalDate.parse("2000/" + fin, DateTimeFormatter.ofPattern("yyyy/dd/MM"));

	        // Verify that ini is less than or equal to fin
	        if (iniDate.isAfter(finDate)) {
	            throw new IllegalArgumentException("La fecha ini debe ser menor o igual que la fecha fin.");
	        }

	        // Check the day and month range
	        if (iniDate.getDayOfMonth() < 1 || iniDate.getDayOfMonth() > 31 || finDate.getDayOfMonth() < 1 || finDate.getDayOfMonth() > 31) {
	            throw new IllegalArgumentException("El día debe estar entre 1 y 31.");
	        }
	        if (iniDate.getMonthValue() < 1 || iniDate.getMonthValue() > 12 || finDate.getMonthValue() < 1 || finDate.getMonthValue() > 12) {
	            throw new IllegalArgumentException("El mes debe estar entre 1 y 12.");
	        }

	        return banco.empleados().todos().stream()
	                .filter(e -> {
	                    LocalDate fechaNacimiento = e.persona().fechaDeNacimiento().toLocalDate().withYear(2000);
	                    return (fechaNacimiento.isAfter(iniDate) || fechaNacimiento.isEqual(iniDate)) &&
	                           (fechaNacimiento.isBefore(finDate) || fechaNacimiento.isEqual(finDate));
	                })
	                .collect(Collectors.toList());
	    }
	    
	  //--
	    
	    public static Map<Character, List<Empleado>> empleadosConLetraDNI(Banco banco, Set<Character> letras) {
	        // Filtrar letras válidas (alfabéticas)
	        Set<Character> letrasValidas = letras.stream()
	                .filter(Character::isLetter)
	                .map(Character::toUpperCase)
	                .collect(Collectors.toSet());

	        // Asociar empleados por letra de DNI
	        Map<Character, List<Empleado>> empleadosPorLetra = banco.empleados().todos().stream()
	                .filter(e -> letrasValidas.contains(getLastChar(e.dni())))
	                .collect(Collectors.groupingBy(e -> getLastChar(e.dni())));

	        // Añadir entradas para las letras que no tengan empleados
	        letrasValidas.forEach(letra -> empleadosPorLetra.putIfAbsent(letra, List.of()));

	        return empleadosPorLetra;
	    }

	    private static char getLastChar(String str) {
	        return str.charAt(str.length() - 1);
	    }
	    
	    //--
	    
	    public static double clientesEdadMedia(Banco banco, Integer m) {
	        if (m <= 0) {
	            throw new IllegalArgumentException("El valor de 'm' debe ser positivo.");
	        }
	        Objects.requireNonNull(m, "El valor de 'm' no puede ser nulo.");

	        return banco.personas().todos().stream()
	                .filter(p -> p.edad() >= m)
	                .collect(Collectors.averagingDouble(Persona::edad));
	    }
	    
	    //--
	    
	    public static Set<String> clientesConMenosPrestamos(Banco banco, int n) {
	        Objects.requireNonNull(banco, "El objeto 'banco' no puede ser nulo.");
	        
	        if (n <= 0) {
	            throw new IllegalArgumentException("El valor de 'n' debe ser mayor que 0.");
	        }
	        
	        return banco.prestamos().todos().stream()
	                .collect(Collectors.groupingBy(Prestamo::dniCliente, Collectors.counting()))
	                .entrySet().stream()
	                .sorted(Map.Entry.comparingByValue())
	                .limit(n)
	                .map(Map.Entry::getKey)
	                .collect(Collectors.toSet());
	    }
	    
	    //--------------------------------------------------------------------------
	    public static void main(String[] args) {
	        Banco banco = Banco.of();

	        List<Empleado> empleados = ExamenEntrega3.empleadosEntreDiaMes(banco, "20/09", "31/12");
	        System.out.println("Empleados nacidos entre 01/01 y 31/12:");
	        empleados.forEach(System.out::println);
	        System.out.println("\n");
	        
	        List<Empleado> empleados2 = ExamenEntrega3.empleadosEntreDiaMes(banco, "30/04", "10/10");
	        System.out.println("Empleados nacidos entre 30/04 y 10/10:");
	        empleados2.forEach(System.out::println);
	        System.out.println("\n");
	        
	        //---------------------------
	        System.out.println("\n");
	        Set<Character> letras = Set.of('A', 'B', 'C');


	        Map<Character, List<Empleado>> empleadosPorLetra = ExamenEntrega3.empleadosConLetraDNI(banco, letras);

	        empleadosPorLetra.forEach((letra, listaEmpleados) -> {
	            System.out.printf("Empleados con DNI que contiene '%c':%n", letra);
	            listaEmpleados.forEach(empleado -> System.out.println(empleado.toString()));
	            System.out.println("-------------------------------");
	        });
	        
	       //----------------------------
	        int m = 20;
	        double edadMedia = ExamenEntrega3.clientesEdadMedia(banco, m);
	        System.out.println("Edad media de clientes con edad mayor o igual a " + m + ": " + edadMedia + "\n");
	        
	        int m1 = 40;
	        double edadMedia1 = ExamenEntrega3.clientesEdadMedia(banco, m1);
	        System.out.println("Edad media de clientes con edad mayor o igual a " + m1 + ": " + edadMedia1 + "\n");
	        
	        //---------------------------
	        int n = 5;
	        System.out.println("Los " + n + " clientes con menos prestamos");
	        Set<String> clientesConMenosPrestamos = ExamenEntrega3.clientesConMenosPrestamos(banco, n);
	        System.out.println(clientesConMenosPrestamos + "\n");
	        int n1 = 10;
	        System.out.println("Los " + n1 + " clientes con menos prestamos");
	        Set<String> clientesConMenosPrestamos2 = ExamenEntrega3.clientesConMenosPrestamos(banco, n1);
	        System.out.println(clientesConMenosPrestamos2);
	        
	 	   //Comprobacion de excepciones
	        
	        //List<Empleado> empleadose = ExamenEntrega3.empleadosEntreDiaMes(banco, "40/20", "341/12");
	        //System.out.println("Empleados nacidos entre 40/20 y 341/12:");
	        //empleadose.forEach(System.out::println);
	        //System.out.println("\n");
	        
	        
	        
	        //Set<Character> letrase = Set.of('9', 'Pepe', 'Chiquito de la calzada');
	        //Map<Character, List<Empleado>> empleadosPorLetrae = ExamenEntrega3.empleadosConLetraDNI(banco, letrase);

	        //empleadosPorLetrae.forEach((letra, listaEmpleados) -> {
	            //System.out.printf("Empleados con DNI que contiene '%c':%n", letra);
	            //listaEmpleados.forEach(empleado -> System.out.println(empleado.toString()));
	            //System.out.println("-------------------------------");
	        //});
	        
	        //int me = -20;
	        //double edadMediae = ExamenEntrega3.clientesEdadMedia(banco, me);
	        //System.out.println("Edad media de clientes con edad mayor o igual a " + me + ": " + edadMediae + "\n");
	        
	        
	        //int ne = -5;
	        //System.out.println("Los " + ne + " clientes con menos prestamos");
	        //Set<String> clientesConMenosPrestamose = ExamenEntrega3.clientesConMenosPrestamos(banco, ne);
	        //System.out.println(clientesConMenosPrestamose + "\n");
	   }
}

	    
