package de.techito.tilgung.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import de.techito.tilgung.model.TilgungsPlan;
import de.techito.tilgung.model.Zahlung;

public class TilgungsPlanService {

    private TilgungsPlanService() {
        // Nicht instanziieren, nur statische Methoden
    }

    public static TilgungsPlan getTilgungsPlan(
            BigDecimal darlehensSumme,
            BigDecimal gewuenschterMonatsbeitrag,
            BigDecimal tilgungsSatz,
            BigDecimal zinsSatz,
            LocalDate ersteRateFaellig,
            boolean isSondertilgung,
            BigDecimal sondertilgungBetrag) {

        List<Zahlung> zahlungen = new ArrayList<>();

        // Faktor für Berechnung der Tilgung / Zinsen aus Prozentwert ermitteln
        zinsSatz = zinsSatz.movePointLeft(2);
        tilgungsSatz = tilgungsSatz.movePointLeft(2);

        BigDecimal monatlicheRate;
        if (gewuenschterMonatsbeitrag != null && gewuenschterMonatsbeitrag.compareTo(BigDecimal.ZERO) != 0) {
            monatlicheRate = gewuenschterMonatsbeitrag;
        } else {
            BigDecimal rateImErstenJahr = darlehensSumme.multiply(tilgungsSatz).add(darlehensSumme.multiply(zinsSatz));
            monatlicheRate = rateImErstenJahr.divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);
        }

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
            BigDecimal zinsenDerRate;
            BigDecimal tilgungDerRate;

            if (restDarlehen.compareTo(monatlicheRate) > 0) {
                // Sonderzahlung einmal jährlich
                if (isSondertilgung 
                        && datumRatenzahlung != null 
                        && datumRatenzahlung.getMonthValue() == 12 
                        && restDarlehen.compareTo(sondertilgungBetrag) > 0) {
                    restDarlehen = restDarlehen.subtract(sondertilgungBetrag);
                    zahlungen.add(new Zahlung(datumRatenzahlung, sondertilgungBetrag, BigDecimal.ZERO, restDarlehen, gesamtZinsen));
                }

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
            zahlungen.add(aktuelleZahlung);
        }
        return new TilgungsPlan(monatlicheRate, zahlungen);
    }
    
}
