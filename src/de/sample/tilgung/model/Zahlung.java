package de.sample.tilgung.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Zahlung {

    private LocalDate zahlungsDatum;

    private BigDecimal tilgungsAnteil;

    private BigDecimal zinsAnteil;

    private BigDecimal restDarlehen;

    private BigDecimal zinsenGesamtBisher;

    public Zahlung(LocalDate zahlungsDatum, BigDecimal tilgungsAnteil, BigDecimal zinsAnteil, BigDecimal restDarlehen, BigDecimal zinsenGesamtBisher) {
        this.zahlungsDatum = zahlungsDatum;
        this.tilgungsAnteil = tilgungsAnteil;
        this.zinsAnteil = zinsAnteil;
        this.restDarlehen = restDarlehen;
        this.zinsenGesamtBisher = zinsenGesamtBisher;
    }

    @Override
    public String toString() {
        return "Datum der Zahlung: " + zahlungsDatum
                + " | Tilgung: " + tilgungsAnteil
                + " | Zinsen: " + zinsAnteil
                + " | Restdarlehen: " + restDarlehen
                + " | Bisher gezahlte Zinsen insgesamt: " + zinsenGesamtBisher;
    }
}
