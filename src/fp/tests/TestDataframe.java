package fp.tests;

import java.util.List;
import fp.Tipos.DataFrame;

public class TestDataframe {
    public static void main(String[] args) {
    	
    	System.out.println("ARCHIVO PERSONAS: \n");
        // Cargar el DataFrame desde el archivo CSV
        DataFrame d = DataFrame.parse("src/fp/ficheros/personas.csv", List.of("Nombre", "Apellido", "Edad", "Altura", "Fecha"));
        
        // Mostrar las diez primeras filas
        System.out.println("Las 10 primeras filas:");
        System.out.println(d.head(10));
        
        // Mostrar las diez últimas filas
        System.out.println("\nLas 10 últimas filas:");
        System.out.println(d.tail(10));
        
        // Mostrar las cinco primeras filas
        System.out.println("\nLas cinco primeras filas:");
        System.out.println(d.head(5));
        
        // Mostrar las cinco últimas filas
        System.out.println("\nLas cinco últimas filas:");
        System.out.println(d.tail(5));
        
        // Mostrar una porción entre dos valores enteros que indican las filas
        System.out.println("\nPorción entre las filas 2 y 5:");
        System.out.println(d.slice(2, 5));
        
        // Eliminar la columna "Edad"
        DataFrame dSinEdad = d.removeColum("Edad");
        System.out.println("\nDataFrame sin la columna 'Edad':");
        System.out.println(dSinEdad);
        
        // Devolver solo aquellas filas con edad mayor estricto que sesenta años
        Integer columnIndexEdad = d.getColumnIndex("Edad");
        DataFrame mayoresDe60 = d.filter(row -> Integer.parseInt(row.get(columnIndexEdad)) > 60);
        System.out.println("\nPersonas con edad mayor de 60 años:");
        System.out.println(mayoresDe60);
        
        
        
    	System.out.println("ARCHIVO MASCOTAS: \n");
    	
        // Cargar el DataFrame desde el archivo CSV
        DataFrame g = DataFrame.parse("src/fp/ficheros/mascotas.csv", List.of("IdMascota","Nombre","Especie","Sexo","Ubicacion","Estado"));
        
        // Mostrar las diez primeras filas
        System.out.println("Las diez primeras filas:");
        System.out.println(g.head(10));
        
        // Mostrar las diez últimas filas
        System.out.println("\nLas diez últimas filas:");
        System.out.println(g.tail(10));
        
        // Mostrar las cinco primeras filas
        System.out.println("\nLas cinco primeras filas:");
        System.out.println(g.head(5));
        
        // Mostrar las cinco últimas filas
        System.out.println("\nLas cinco últimas filas:");
        System.out.println(g.tail(5));
        
        // Mostrar una porción entre dos valores enteros que indican las filas
        System.out.println("\nPorción entre las filas 4 y 7:");
        System.out.println(g.slice(4, 7));
       
        // Eliminar la columna "Ubicacion"
        DataFrame gSinUbicacion = g.removeColum("Ubicacion");
        System.out.println("\nDataFrame sin la columna 'Ubicacion':");
        System.out.println(gSinUbicacion);
        
    	System.out.println("ARCHIVO PELICULAS: \n");
    	
        // Cargar el DataFrame desde el archivo CSV
        DataFrame j = DataFrame.parse("src/fp/ficheros/pp.csv", List.of("Titulo", "Valoracion", "Anyo_estreno"));
        
        // Mostrar las diez primeras filas
        System.out.println("Las diez primeras filas:");
        System.out.println(j.head(10));
        
        // Mostrar las diez últimas filas
        System.out.println("\nLas diez últimas filas:");
        System.out.println(j.tail(10));
        
        // Mostrar las cinco primeras filas
        System.out.println("\nLas cinco primeras filas:");
        System.out.println(j.head(5));
        
        // Mostrar las cinco últimas filas
        System.out.println("\nLas cinco últimas filas:");
        System.out.println(j.tail(5));
        
        // Mostrar una porción entre dos valores enteros que indican las filas
        System.out.println("\nPorción entre las filas 2 y 3:");
        System.out.println(j.slice(2, 3));
       
        // Eliminar la columna "Valoración"
        DataFrame jSinValoracion = j.removeColum("Valoracion");
        System.out.println("\nDataFrame sin la columna 'Valoracion':");
        System.out.println(jSinValoracion);
    }
}
