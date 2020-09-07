package de.techito.tilgung.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import de.techito.tilgung.model.Zahlung;

public class TilgungsPlan {

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Ihr Gesamtdarlehen: ");
            String darlehenInput = br.readLine();
            System.out.print("Ihr gewünschter monatlicher Beitrag: ");
            String monatsBeitrag = br.readLine();
            System.out.print("Ihr Tilgungssatz: ");
            String tilgunsSatzInput = br.readLine();
            System.out.print("Ihr Zinssatz: ");
            String zinsSatzInput = br.readLine();
            List<Zahlung> tilgungsPlan = getTilgungsPlan(new BigDecimal(darlehenInput), new BigDecimal(monatsBeitrag), new BigDecimal(tilgunsSatzInput), new BigDecimal(zinsSatzInput), LocalDate.now().withDayOfMonth(1));
            for(Zahlung zahlung : tilgungsPlan) {
                System.out.println(zahlung.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static List<Zahlung> getTilgungsPlan(
            BigDecimal darlehensSumme,
            BigDecimal gewuenschterMonatsbeitrag,
            BigDecimal tilgungsSatz,
            BigDecimal zinsSatz,
            LocalDate ersteRateFaellig) {

        List<Zahlung> tilgungsPlan = new ArrayList<>();

        BigDecimal monatlicheRate;
        if (gewuenschterMonatsbeitrag != null && gewuenschterMonatsbeitrag.compareTo(BigDecimal.ZERO) != 0) {
            monatlicheRate = gewuenschterMonatsbeitrag;
        } else {
            BigDecimal rateImErstenJahr = darlehensSumme.multiply(tilgungsSatz).add(darlehensSumme.multiply(zinsSatz));
            monatlicheRate = rateImErstenJahr.divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);
        }
        System.out.println("Ihre monatliche Rate: " + monatlicheRate);

        BigDecimal restDarlehen = darlehensSumme;
        LocalDate datumRatenzahlung = null;
        BigDecimal gesamtZinsen = BigDecimal.ZERO;

        while (restDarlehen.compareTo(BigDecimal.ZERO) > 0) {


            if (datumRatenzahlung == null) {
                datumRatenzahlung = ersteRateFaellig;
            } else {
                datumRatenzahlung = datumRatenzahlung.plusMonths(1);
            }

            Zahlung aktuelleZahlung;
            BigDecimal zinsenDerRate, tilgungDerRate;

            if (restDarlehen.compareTo(monatlicheRate) > 0) {
                zinsenDerRate = restDarlehen.multiply(zinsSatz).divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
                tilgungDerRate = monatlicheRate.subtract(zinsenDerRate).setScale(2, RoundingMode.HALF_UP);
                restDarlehen = restDarlehen.subtract(tilgungDerRate);
                gesamtZinsen = gesamtZinsen.add(zinsenDerRate);
                aktuelleZahlung = new Zahlung(datumRatenzahlung, tilgungDerRate, zinsenDerRate, restDarlehen, gesamtZinsen);
            } else {
                BigDecimal letzteZinsen = restDarlehen.multiply(zinsSatz).divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
                gesamtZinsen = gesamtZinsen.add(letzteZinsen);
                aktuelleZahlung = new Zahlung(datumRatenzahlung, restDarlehen, letzteZinsen, BigDecimal.ZERO, gesamtZinsen);
                restDarlehen = BigDecimal.ZERO;
            }
            tilgungsPlan.add(aktuelleZahlung);
        }
        return tilgungsPlan;
    }
}
