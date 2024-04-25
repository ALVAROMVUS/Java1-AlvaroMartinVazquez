package fp.Tipos;


import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.tools.Enumerate;
import us.lsi.tools.File2;
import us.lsi.tools.Stream2;

public class DataFrameImpl implements DataFrame {
    // Atributos
    private List<String> columNames;
    private Map<String, Integer> columIndex;
    private List<List<String>> rows;

    // Constructores
    private DataFrameImpl(List<String> columNames, Map<String, Integer> columIndex, List<List<String>> rows) {
        this.columNames = columNames;
        this.columIndex = columIndex;
        this.rows = rows;
    }

    // Métodos de factoría
    private static DataFrameImpl of(List<String> columNames, Map<String, Integer> columIndex, List<List<String>> rows) {
        return new DataFrameImpl(columNames, columIndex, rows);
    }

    public static DataFrameImpl of(Map<String, List<String>> data) {
        List<String> columNames = new ArrayList<>(data.keySet());
        Map<String, Integer> columIndex = IntStream.range(0, columNames.size())
                .boxed()
                .collect(Collectors.toMap(columNames::get, Function.identity()));
        List<List<String>> rows = new ArrayList<>();
        int numRows = data.get(columNames.get(0)).size();
        for (int i = 0; i < numRows; i++) {
            List<String> row = new ArrayList<>();
            for (String colName : columNames) {
                row.add(data.get(colName).get(i));
            }
            rows.add(row);
        }
        return DataFrameImpl.of(columNames, columIndex, rows);
    }

    public static DataFrameImpl of(Map<String, List<String>> data, List<String> columNames) {
        Map<String, List<String>> filteredData = data.entrySet().stream()
                .filter(entry -> columNames.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return DataFrameImpl.of(filteredData);
    }

    public static DataFrameImpl of(List<String> columNames, List<List<String>> rows) {
        Map<String, Integer> columIndex = IntStream.range(0, columNames.size())
                .boxed()
                .collect(Collectors.toMap(columNames::get, Function.identity()));
        return DataFrameImpl.of(columNames, columIndex, rows);
    }
    
    public static DataFrameImpl parse(String file) {
        Map<String, List<String>> data = File2.mapDeCsv(file);
        return DataFrameImpl.of(data);
    }

    public static DataFrameImpl parse(String file, List<String> columNames) {
        Map<String, List<String>> data = File2.mapDeCsv(file);
        return DataFrameImpl.of(data, columNames);
    }

    // Métodos de las propiedades
    @Override
    public List<String> columNames() {
        return new ArrayList<>(columNames);
    }

    @Override
    public Integer columNumber() {
        return columNames.size();
    }

    @Override
    public List<String> colum(String name) {
        return rows.stream()
                .map(row -> row.get(columIndex.get(name)))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> colum(Integer index) {
        return rows.stream()
                .map(row -> row.get(index))
                .collect(Collectors.toList());
    }

    @Override
    public <R> List<R> colum(String name, Class<R> type) {
        return colum(name).stream()
                .map(value -> DataFrame.parse(value, type))
                .collect(Collectors.toList());
    }

    @Override
    public <R> List<R> colum(Integer index, Class<R> type) {
        return colum(index).stream()
                .map(value -> DataFrame.parse(value, type))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean columAllDifferent(String name) {
        return allDifferent(colum(name));
    }
    
    public Integer getColumnIndex(String columnName) {
        return this.columIndex.get(columnName);
    }
    @Override
    public String propertie(List<String> row, String colum) {
        return row.get(columIndex.get(colum));
    }

    @Override
    public <R> R propertie(List<String> row, String colum, Class<R> type) {
        String text = row.get(columIndex.get(colum));
        return DataFrame.parse(text, type);
    }

    @Override
    public String cell(Integer row, String colum) {
        return rows.get(row).get(columIndex.get(colum));
    }

    @Override
    public String cell(Integer row, Integer colum) {
        return rows.get(row).get(colum);
    }

    @Override
    public String cell(String row, String colum, String propertie) {
        int rowIndex = Integer.parseInt(row);
        int colIndex = columIndex.get(colum);
        return rows.get(rowIndex).get(colIndex);
    }

    @Override
    public Integer rowNumber() {
        return rows.size();
    }

    @Override
    public List<String> row(Integer i) {
        return new ArrayList<>(rows.get(i));
    }

    @Override
    public List<String> row(String row, String colum) {
        if (columAllDifferent(colum)) {
            int colIndex = columIndex.get(colum);
            return rows.stream()
                    .filter(r -> !r.get(colIndex).equals(row))
                    .findFirst()
                    .orElse(new ArrayList<>());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<List<String>> rows() {
        return rows.stream()
                .map(ArrayList::new)
                .collect(Collectors.toList());
    }

    @Override
    public DataFrame head() {
        return head(5);
    }

    @Override
    public DataFrame head(Integer n) {
        return DataFrameImpl.of(columNames, columIndex, rows.subList(0, Math.min(n, rows.size())));
    }

    @Override
    public DataFrame tail() {
        return tail(5);
    }

    @Override
    public DataFrame tail(Integer n) {
        return DataFrameImpl.of(columNames, columIndex, rows.subList(Math.max(0, rows.size() - n), rows.size()));
    }

    @Override
    public DataFrame slice(Integer n, Integer m) {
        return DataFrameImpl.of(columNames, columIndex, rows.subList(n, Math.min(m, rows.size())));
    }

    @Override
    public DataFrame filter(Predicate<List<String>> p) {
        List<List<String>> filteredRows = rows.stream()
                .filter(p)
                .collect(Collectors.toList());
        return DataFrameImpl.of(columNames, columIndex, filteredRows);
    }

    @Override
    public <E extends Comparable<? super E>> DataFrame sortBy(Function<List<String>, E> f, Boolean reverse) {
        List<List<String>> sortedRows = new ArrayList<>(rows);
        Comparator<List<String>> comparator = Comparator.comparing(f);
        if (reverse) {
            comparator = comparator.reversed();
        }
        sortedRows.sort(comparator);
        return DataFrameImpl.of(columNames, columIndex, sortedRows);
    }

    private Set<Integer> indexes(List<String> columNames) {
        return columNames.stream()
                .map(columIndex::get)
                .collect(Collectors.toSet());
    }

    private List<String> select(List<String> ls, Set<Integer> sl) {
        return IntStream.range(0, ls.size())
                .filter(sl::contains)
                .mapToObj(ls::get)
                .collect(Collectors.toList());
    }

    @Override
    public <R> DataFrame groupBy(List<String> columNames, String newColumn, BinaryOperator<R> op,
                                  Function<List<String>, R> value) {
        Function<List<String>, List<String>> key = ls -> select(ls, indexes(columNames));
        Map<List<String>, R> g = Stream2.groupingReduce(rows.stream(), key, op, value);
        DataFrame r = DataFrame.of(columNames, g.keySet().stream().toList());
        r = r.addColum(newColumn, g.values().stream().map(DataFrameImpl::string).toList());
        return r;
    }

    @Override
    public DataFrame addColum(String newColum, List<String> datos) {
        List<String> newColumNames = new ArrayList<>(columNames);
        newColumNames.add(newColum);
        Map<String, Integer> newColumIndex = new HashMap<>(columIndex);
        newColumIndex.put(newColum, columNames.size());
        List<List<String>> newRows = IntStream.range(0, rowNumber())
                .mapToObj(i -> {
                    List<String> newRow = new ArrayList<>(rows.get(i));
                    newRow.add(datos.get(i));
                    return newRow;
                })
                .collect(Collectors.toList());
        return DataFrameImpl.of(newColumNames, newColumIndex, newRows);
    }

    @Override
    public DataFrame addCalculatedColum(String newColum, Function<List<String>, String> f) {
        List<String> newColumValues = rows.stream()
                .map(f)
                .collect(Collectors.toList());
        return addColum(newColum, newColumValues);
    }

    @Override
    public DataFrame removeColum(String colum) {
        int indexToRemove = columIndex.get(colum);
        List<String> newColumNames = new ArrayList<>(columNames);
        newColumNames.remove(colum);
        Map<String, Integer> newColumIndex = new HashMap<>();
        IntStream.range(0, newColumNames.size())
                .forEach(i -> newColumIndex.put(newColumNames.get(i), i));
        List<List<String>> newRows = rows.stream()
                .map(row -> {
                    List<String> newRow = new ArrayList<>(row);
                    newRow.remove(indexToRemove);
                    return newRow;
                })
                .collect(Collectors.toList());
        return DataFrameImpl.of(newColumNames, newColumIndex, newRows);
    }

    // Métodos adicionales: redefinidos de Object
    @Override
    public String toString() {
        Integer t = 13;
        String r = this.format(" ", this.columNames(), t);
        String line = this.line(this.columNames().size() + 1, t);
        String r3 = Stream2.enumerate(this.rows.stream()).map(x -> this.format(x, t))
                .collect(Collectors.joining("\n", r + "\n" + line + "\n", "\n"));
        return r3;
    }

    private String format(String propertie, List<String> ls, Integer n) {
        List<String> nls = new ArrayList<>();
        nls.add(propertie);
        nls.addAll(ls);
        String fmt = "%-" + n + "s";
        return nls.stream().map(c -> String.format(fmt, c)).collect(Collectors.joining("|", "|", "|"));
    }

    private String format(Enumerate<List<String>> e, Integer n) {
        return this.format(e.counter().toString(), e.value(), n);
    }

    private String line(Integer m, Integer n) {
        String s = IntStream.range(0, n).mapToObj(i -> "_").collect(Collectors.joining(""));
        return IntStream.range(0, m).mapToObj(i -> s).collect(Collectors.joining("|", "|", "|"));
    }

    // Métodos auxiliares
    private static Boolean allDifferent(List<String> values) {
        return values.size() == values.stream().distinct().count();
    }

    static String string(Object r) {
        String s = null;
        if (r instanceof LocalDate) {
            LocalDate r1 = (LocalDate) r;
            s = r1.toString();
        } else if (r instanceof LocalTime) {
            LocalTime r1 = (LocalTime) r;
            s = r1.toString();
        } else if (r instanceof LocalDateTime) {
            LocalDateTime r1 = (LocalDateTime) r;
            s = r1.toString();
        } else {
            s = r.toString();
        }
        return s;
    }

    @SuppressWarnings("unchecked")
    public static <R> R parse(String text, Class<R> type) {
        Object r = null;
        if (type.equals(LocalDate.class)) {
            r = LocalDate.parse(text);
        } else if (type.equals(LocalTime.class)) {
            r = LocalTime.parse(text);
        } else if (type.equals(LocalDateTime.class)) {
            r = LocalDateTime.parse(text);
        } else if (type.equals(Double.class)) {
            r = Double.parseDouble(text);
        } else if (type.equals(Integer.class)) {
            r = Integer.parseInt(text);
        } else {
            r = text;
        }
        return (R) r;
    }
}