package fp.Tipos;


public record Fecha(int año, int mes, int dia) implements Comparable<Fecha> {
    private static final int[] diasPorMes = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};


    public static Fecha parse(String text) {
        String[] partes = text.split("-");
        int año = Integer.parseInt(partes[0].trim());
        if (año < 0) {
            throw new IllegalArgumentException("No se permiten años negativos");
        }
        int mes = Integer.parseInt(partes[1].trim());
        if (mes <= 0 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12");
        }
        int dia = Integer.parseInt(partes[2].trim());
        if (dia <= 0 || dia > diasEnMes(año, mes)) {
            throw new IllegalArgumentException("El número de día no corresponde al rango de días en el mes");
        }
        return new Fecha(año, mes, dia);
    }

    public static Fecha of(int año, int mes, int dia) {
        return new Fecha(año, mes, dia);
    }

    public String NombreMes() {
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        return meses[mes - 1];
    }
    
    public String diaSemana() {
        String[] lista = {"Sabado", "Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes"};
        int zeller = congruenciaZeller(this.año, this.mes, this.dia);
        return lista[zeller];
    }

    public Fecha sumarDias(int cantidadDias) {
        int diaActual = this.dia;
        int mesActual = this.mes;
        int añoActual = this.año;
                    
        while (cantidadDias > 0) {
            int diasMesActual = Fecha.diasEnMes(añoActual, mesActual);
            int diasRestantesMes = diasMesActual - diaActual + 1;
                
            if (cantidadDias >= diasRestantesMes) {
                cantidadDias -= diasRestantesMes;
                diaActual = 1;
                mesActual++;
                if (mesActual > 12) {
                    mesActual = 1;
                    añoActual++;
                }
            } else {
                diaActual += cantidadDias;
                cantidadDias = 0;
            }
        }
        return new Fecha(añoActual, mesActual, diaActual);
    }

    public Fecha restarDias(int cantidadDias) {
        int diaActual = this.dia;
        int mesActual = this.mes;
        int añoActual = this.año;

        while (cantidadDias > 0) {
            if (cantidadDias >= diaActual) {
                cantidadDias -= diaActual;
                mesActual--;
                if (mesActual < 1) {
                    mesActual = 12;
                    añoActual--;
                }
                diaActual = Fecha.diasEnMes(añoActual, mesActual);
            } else {
                diaActual -= cantidadDias;
                cantidadDias = 0;
            }
        }
        return new Fecha(añoActual, mesActual, diaActual);
    }

    public int diferenciaEnDias(Fecha fechaI) {
        int[] diasPorMes1 = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int[] diasPorMes2 = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        int dias1 = this.dia;
        int meses1 = this.mes;
        int años1 = this.año;

        int dias2 = fechaI.dia;
        int meses2 = fechaI.mes;
        int años2 = fechaI.año;
            
        if (Fecha.esAñoBisiesto(años1)) {
            diasPorMes1[2] = 29;
        } else {
            diasPorMes1[2] = 28;
        }
            
        if (Fecha.esAñoBisiesto(años2)) {
            diasPorMes2[2] = 29;
        } else {
            diasPorMes2[2] = 28;
        }
            
        int diasTotales1 = dias1 + metodo(diasPorMes1, meses1) + (años1 * 365);
        int diasTotales2 = dias2 + metodo(diasPorMes2, meses2) + (años2 * 365);

        return Math.abs(diasTotales1 - diasTotales2);
    }

    private int metodo(int[] array, int hasta) {
        int suma = 0;
        for (int i = 1; i <= hasta; i++) {
            suma += array[i];
        }
        return suma;
    }


    public static boolean esAñoBisiesto(int año) {
        return (año % 4 == 0 && año % 100 != 0) || (año % 400 == 0);
    }

    public static int diasEnMes(int año, int mes) {
        if (mes == 2 && esAñoBisiesto(año)) {
            return 29;
        }
        return diasPorMes[mes];
    }
    
    public static int congruenciaZeller(int año, int mes, int dia) {
        int añoZ = año;
        int mesZ = mes;
        int diaZ = dia;
        if (mesZ < 3) {
            añoZ = añoZ - 1;
            mesZ = mesZ + 12;
        }
        int K = (añoZ % 100);
        int J = (int) Math.floor(añoZ / 100);
        int h = (diaZ + (int) (13 * (mesZ + 1) / 5) + K + (int) (K / 4) + (int) (J / 4) + 5 * J) % 7;

        return h;
    }

    @Override
    public int compareTo(Fecha otraFecha) {
        if (año != otraFecha.año) {
            return año - otraFecha.año;
        } else if (mes != otraFecha.mes) {
            return mes - otraFecha.mes;
        } else {
            return dia - otraFecha.dia;
        }
    }
    
//DEFENSA ----------------------------------------
    
    public Fecha restarDiasFechaDada(Fecha fecha, int n) {
        int diaActual = fecha.dia;
        int mesActual = fecha.mes;
        int añoActual = fecha.año;

        while (n > 0) {
            if (n >= diaActual) {
                n -= diaActual;
                mesActual--;
                if (mesActual < 1) {
                    mesActual = 12;
                    añoActual--;
                }
                diaActual = Fecha.diasEnMes(añoActual, mesActual);
            } else {
                diaActual -= n;
                n = 0;
            }
        }
        return new Fecha(añoActual, mesActual, diaActual);
    }

    @Override
    public String toString() {
        return String.format("%s, %d de %s de %d.", diaSemana(), dia, NombreMes(), año);
    }
}