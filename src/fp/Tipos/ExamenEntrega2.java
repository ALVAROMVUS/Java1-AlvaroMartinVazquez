package fp.Tipos;

import java.util.ArrayList;
import java.util.List;

import fp.Tipos.DataFrame;
import fp.Tipos.DataFrameImpl;

public class ExamenEntrega2 {

	public static void main(String[] args) {
        ExamenEntrega2 examen = new ExamenEntrega2();

        DataFrame g = DataFrame.parse("src/fp/ficheros/mascotas.csv", List.of("IdMascota","Nombre","Especie","Sexo","Ubicacion","Estado"));
        DataFrame j = DataFrame.parse("src/fp/ficheros/pp.csv", List.of("Titulo", "Valoracion", "Anyo_estreno"));

        System.out.println("\nDefensa, vamos a trabajar con los dataframe mascota y peliculas.");
        //
        System.out.println("\nDataFrame vacío de mascota, manteniendo las columnas:");
        DataFrame gvacio = examen.emptyDataFrame(g);
        System.out.println(gvacio);

        //
        System.out.println("\nDataFrame mascotas después de quitar la columna 1º:");
        DataFrame gsin1 = examen.removeColumnIndex(g,1);
        System.out.println(gsin1);

        //
        DataFrame result = examen.addDataFrame(g,j);
        System.out.println("DataFrame resultante despues de agregar  el dataframe peliculas detrás de la última columna de el de mascotas:");
        System.out.println(result.toString());

        //
        System.out.println("\nDivision del DataFrame mascotas a partir de su segunda columna:");
        List<DataFrame> divididos = examen.divideDataFrame(g, 2);
        for (DataFrame df : divididos) {
            System.out.println(df);
        }
        System.out.println("\n Podemos obserbar que la division es perfecta, teniendo 3 columnas en cada DataFrame resultante.");
        // COMPROBACION DE RESTRICCIONES. (Esta parte estará comentada para que el codigo se pueda ejectuar. Puede descomentar esto para comprobarlo.)
        
        
        //System.out.println("\nDataFrame mascotas después de quitar la columna 70º, da error:");
        //DataFrame gsin70 = g.removeColumnIndex(g,70);
        //System.out.println(gsin70);
        
        
        //System.out.println("\nDivision del DataFrame mascotas a partir de su columna 50, da error:");
        //List<DataFrame> divididos50 = g.divideDataFrame(g, 50);
        //System.out.println(divididos50);
    }
    
    public DataFrame emptyDataFrame(DataFrame df) {
        List<String> columNames = df.columNames();
        List<List<String>> emptyRows = new ArrayList<>();
        return DataFrameImpl.of(columNames, emptyRows);
    }

    public DataFrame addDataFrame(DataFrame df1, DataFrame df2) {
        if (!(df1 instanceof DataFrameImpl) || !(df2 instanceof DataFrameImpl)) {
            throw new IllegalArgumentException("Los dataframes deben ser instancias de DataFrameImpl");
        }

        DataFrameImpl df1Impl = (DataFrameImpl) df1;
        DataFrameImpl df2Impl = (DataFrameImpl) df2;

        List<String> columnNames1 = df1Impl.columNames();
        List<List<String>> rows1 = df1Impl.rows();
        List<String> columnNames2 = df2Impl.columNames();
        List<List<String>> rows2 = df2Impl.rows();

        columnNames1.addAll(columnNames2);

        for (int i = 0; i < Math.min(rows1.size(), rows2.size()); i++) {
            rows1.get(i).addAll(rows2.get(i));
        }

        return DataFrameImpl.of(columnNames1, rows1);
    }

    public DataFrame removeColumnIndex(DataFrame df, Integer ci) {
        if (!(df instanceof DataFrameImpl)) {
            throw new IllegalArgumentException("El DataFrame debe ser una instancia de DataFrameImpl");
        }

        DataFrameImpl dfImpl = (DataFrameImpl) df;
        String columnNombre = dfImpl.columNames().get(ci);
        return dfImpl.removeColum(columnNombre);
    }

    public List<DataFrame> divideDataFrame(DataFrame df, int ci) {
        List<DataFrame> dividedFrames = new ArrayList<>();
        if (ci >= 0 && ci < df.columNumber()) {
            List<String> columNames1 = df.columNames().subList(0, ci + 1);
            List<String> columNames2 = df.columNames().subList(ci + 1, df.columNumber());

            List<List<String>> rows = df.rows();
            List<List<String>> rows1 = new ArrayList<>();
            List<List<String>> rows2 = new ArrayList<>();

            for (List<String> row : rows) {
                List<String> row1 = row.subList(0, ci + 1);
                List<String> row2 = row.subList(ci + 1, row.size());
                rows1.add(row1);
                rows2.add(row2);
            }

            DataFrameImpl firstFrame = DataFrameImpl.of(columNames1, rows1);
            DataFrameImpl secondFrame = DataFrameImpl.of(columNames2, rows2);

            dividedFrames.add(firstFrame);
            dividedFrames.add(secondFrame);
        } else {
            System.err.println("Error: el valor de 'ci' está fuera de rango.");
        }
        return dividedFrames;
    }
}
