package de.techito.tilgung.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Zahlung {

    private LocalDate zahlungsDatum;

    private BigDecimal tilgungsAnteil;

    private BigDecimal zinsAnteil;

    private BigDecimal restDarlehen;

    private BigDecimal zinsenGesamtBisher;

    @Override
    public String toString() {
        return "Datum der Zahlung: " + zahlungsDatum
                + " | Tilgung: " + tilgungsAnteil
                + " | Zinsen: " + zinsAnteil
                + " | Restdarlehen: " + restDarlehen
                + " | Bisher gezahlte Zinsen insgesamt: " + zinsenGesamtBisher;
    }
}
