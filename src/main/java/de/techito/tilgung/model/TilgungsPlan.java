package de.techito.tilgung.model;

import java.math.BigDecimal;
import java.util.List;

public class TilgungsPlan {

    private BigDecimal monatlicheRate;

    private List<Zahlung> zahlungen;

    public TilgungsPlan(BigDecimal monatlicheRate, List<Zahlung> zahlungen) {
        this.monatlicheRate = monatlicheRate;
        this.zahlungen = zahlungen;
    }

    public List<Zahlung> getZahlungen() {
        return this.zahlungen;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator");
        sb.append("***** Ihr Tilgungsplan: *****");
        sb.append(lineSeparator);
        sb.append("Monatliche Rate: " + this.monatlicheRate);
        for (Zahlung zahlung : this.zahlungen) {
            sb.append(lineSeparator);
            sb.append(zahlung.toString());
        }
        return sb.toString();
    }
    
}
