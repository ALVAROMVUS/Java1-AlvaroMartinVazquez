package fp.tests;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import fp.Tipos.Fecha;


public class TestFecha {
    public static void main(String[] args) {

        Integer año = 2024;
        Integer mes = 3;
        Integer dia = 18;
 

        System.out.println("Vamos a comparar la clase Fecha con el Localdate de Java:");
        System.out.println("La fecha elegida es el 18 de Marzo del 2024");
        Fecha fecha = Fecha.of(año, mes, dia);
        LocalDate localDate = LocalDate.of(año, mes, dia);
        System.out.println();
 
        System.out.println("----------INICIO----------");
        System.out.println("Representacion de la fecha:");
        System.out.println("LocalDate: " + fecha);
        System.out.println("LocalDate: " + localDate);
        System.out.println();
        
        System.out.println("Test del año bisiesto:");
        System.out.println("Fecha.esAñoBisiesto(): " + Fecha.esAñoBisiesto(fecha.año()));
        System.out.println("LocalDate.isLeapYear(): " + localDate.isLeapYear());
        System.out.println();
        
        System.out.println("Test de los dias en el mes:");
        System.out.println("Fecha.diasEnMes(): " + Fecha.diasEnMes(fecha.año(), fecha.mes()));
        System.out.println("LocalDate.lengthOfMonth(): " + localDate.lengthOfMonth());
        System.out.println();
 
        System.out.println("Test de sumar 13 dias:");
        Fecha fechaSumada = fecha.sumarDias(13);
        LocalDate localDateSumada = localDate.plusDays(13);
        System.out.println("Fecha.sumardias(10): " + fechaSumada);
        System.out.println("LocalDate.plusDays(10): " + localDateSumada);
        System.out.println();
        
        System.out.println("Test de restar 13 dias:");
        Fecha fechaRestada = fecha.restarDias(13);
        LocalDate localDateRestada = localDate.minusDays(13);
        System.out.println("Fecha.restarDias(10): " + fechaRestada);
        System.out.println("LocalDate.minusDays(10): " + localDateRestada);
        System.out.println();
 
        System.out.println("Test de diferencia en dias con las fechas sumadas:");
        Integer diferenciaEnDias = fecha.diferenciaEnDias(fechaSumada);
        long diferenciaEnDiasLocalDate = ChronoUnit.DAYS.between(localDate, localDateSumada);
        System.out.println("Fecha.diferenciaEnDias(fechaSumada): " + diferenciaEnDias);
        System.out.println("ChronoUnit.DAYS.between(localDate, localDateSumada): " + diferenciaEnDiasLocalDate);
        System.out.println();
        
        System.out.println("Test de diferencia en dias con las fechas restadas:");
        Integer diferenciaEnDias2 = fecha.diferenciaEnDias(fechaRestada);
        long diferenciaEnDiasLocalDate2 = ChronoUnit.DAYS.between(localDate, localDateRestada);
        System.out.println("Fecha.diferenciaEnDias(fechaSumada): " + diferenciaEnDias2);
        System.out.println("ChronoUnit.DAYS.between(localDate, localDateSumada): " + diferenciaEnDiasLocalDate2);
        System.out.println();
    }
}