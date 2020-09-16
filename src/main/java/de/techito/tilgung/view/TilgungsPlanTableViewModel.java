package de.techito.tilgung.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import de.techito.tilgung.model.Zahlung;
import javafx.beans.property.SimpleStringProperty;

public class TilgungsPlanTableViewModel {

    private final SimpleStringProperty zahlungsDatum;

    private final SimpleStringProperty tilgungsAnteil;

    private final SimpleStringProperty zinsAnteil;

    private final SimpleStringProperty restDarlehen;

    private final SimpleStringProperty zinsenGesamtBisher;

    public TilgungsPlanTableViewModel(String zahlungsDatum, String tilgungsAnteil, String zinsAnteil, String restDarlehen, String zinsenGesamtBisher) {
        this.zahlungsDatum = new SimpleStringProperty(zahlungsDatum);
        this.tilgungsAnteil = new SimpleStringProperty(tilgungsAnteil);
        this.zinsAnteil = new SimpleStringProperty(zinsAnteil);
        this.restDarlehen = new SimpleStringProperty(restDarlehen);
        this.zinsenGesamtBisher = new SimpleStringProperty(zinsenGesamtBisher);
    }

    public TilgungsPlanTableViewModel(Zahlung zahlung) {
        this(
            zahlung.getZahlungsDatum().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.GERMAN)),
            zahlung.getTilgungsAnteil().toString(),
            zahlung.getZinsAnteil().toString(),
            zahlung.getRestDarlehen().toString(),
            zahlung.getZinsenGesamtBisher().toString());
    }

    public String getZahlungsDatum() {
        return this.zahlungsDatum.get();
    }

    public void setZahlungsDatum(LocalDate zahlungsDatum) {
        this.zahlungsDatum.set(zahlungsDatum.toString());
    }

    public String getTilgungsAnteil() {
        return this.tilgungsAnteil.get();
    }

    public void setTilgungsAnteil(BigDecimal tilgungsAnteil) {
        this.tilgungsAnteil.set(tilgungsAnteil.toString());
    }

    public String getZinsAnteil() {
        return zinsAnteil.get();
    }

    public void setZinsAnteil(BigDecimal zinsAnteil) {
        this.zinsAnteil.set(zinsAnteil.toString());
    }

    public String getRestDarlehen() {
        return restDarlehen.get();
    }

    public void setRestDarlehen(BigDecimal restDarlehen) {
        this.restDarlehen.set(restDarlehen.toString());
    }

    public String getZinsenGesamtBisher() {
        return zinsenGesamtBisher.get();
    }
    
    public void setZinsenGesamtBisher(BigDecimal zinsenGesamtBisher) {
        this.zinsenGesamtBisher.set(zinsenGesamtBisher.toString());
    }

}
