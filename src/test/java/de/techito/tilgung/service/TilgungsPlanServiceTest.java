package de.techito.tilgung.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.techito.tilgung.model.TilgungsPlan;
import de.techito.tilgung.model.Zahlung;

public class TilgungsPlanServiceTest {

    @Test
    public void shouldGetCorrectNumberOfElements() {
        TilgungsPlan tilgungsPlan = TilgungsPlanService.getTilgungsPlan(
            BigDecimal.valueOf(10000),
            BigDecimal.valueOf(2500),
            BigDecimal.valueOf(3),
            BigDecimal.valueOf(1.2),
            LocalDate.of(2020, 9, 1),
            false,
            null);

        Assertions.assertEquals(BigDecimal.valueOf(2500), tilgungsPlan.getMonatlicheRate());
        Assertions.assertEquals(5, tilgungsPlan.getZahlungen().size());
        
        List<Zahlung> zahlungen = tilgungsPlan.getZahlungen();

        Assertions.assertNotNull(zahlungen.get(0));
        Assertions.assertEquals(BigDecimal.valueOf(249000, 2), zahlungen.get(0).getTilgungsAnteil());
        Assertions.assertEquals(BigDecimal.valueOf(1000, 2), zahlungen.get(0).getZinsAnteil());
        Assertions.assertEquals(BigDecimal.valueOf(751000, 2), zahlungen.get(0).getRestDarlehen());
        Assertions.assertEquals(BigDecimal.valueOf(1000, 2), zahlungen.get(0).getZinsenGesamtBisher());
        Assertions.assertEquals(LocalDate.of(2020, 9, 1), zahlungen.get(0).getZahlungsDatum());

        Assertions.assertNotNull(zahlungen.get(1));
        Assertions.assertEquals(BigDecimal.valueOf(249249, 2), zahlungen.get(1).getTilgungsAnteil());
        Assertions.assertEquals(BigDecimal.valueOf(751, 2), zahlungen.get(1).getZinsAnteil());
        Assertions.assertEquals(BigDecimal.valueOf(501751, 2), zahlungen.get(1).getRestDarlehen());
        Assertions.assertEquals(BigDecimal.valueOf(1751, 2), zahlungen.get(1).getZinsenGesamtBisher());
        Assertions.assertEquals(LocalDate.of(2020, 10, 1), zahlungen.get(1).getZahlungsDatum());

        Assertions.assertNotNull(zahlungen.get(2));
        Assertions.assertEquals(BigDecimal.valueOf(249498, 2), zahlungen.get(2).getTilgungsAnteil());
        Assertions.assertEquals(BigDecimal.valueOf(502, 2), zahlungen.get(2).getZinsAnteil());
        Assertions.assertEquals(BigDecimal.valueOf(252253, 2), zahlungen.get(2).getRestDarlehen());
        Assertions.assertEquals(BigDecimal.valueOf(2253, 2), zahlungen.get(2).getZinsenGesamtBisher());
        Assertions.assertEquals(LocalDate.of(2020, 11, 1), zahlungen.get(2).getZahlungsDatum());

        Assertions.assertNotNull(zahlungen.get(3));
        Assertions.assertEquals(BigDecimal.valueOf(249748, 2), zahlungen.get(3).getTilgungsAnteil());
        Assertions.assertEquals(BigDecimal.valueOf(252, 2), zahlungen.get(3).getZinsAnteil());
        Assertions.assertEquals(BigDecimal.valueOf(2505, 2), zahlungen.get(3).getRestDarlehen());
        Assertions.assertEquals(BigDecimal.valueOf(2505, 2), zahlungen.get(3).getZinsenGesamtBisher());
        Assertions.assertEquals(LocalDate.of(2020, 12, 1), zahlungen.get(3).getZahlungsDatum());

        Assertions.assertNotNull(zahlungen.get(4));
        Assertions.assertEquals(BigDecimal.valueOf(2505, 2), zahlungen.get(4).getTilgungsAnteil());
        Assertions.assertEquals(BigDecimal.valueOf(003, 2), zahlungen.get(4).getZinsAnteil());
        Assertions.assertEquals(BigDecimal.valueOf(0), zahlungen.get(4).getRestDarlehen());
        Assertions.assertEquals(BigDecimal.valueOf(2508, 2), zahlungen.get(4).getZinsenGesamtBisher());
        Assertions.assertEquals(LocalDate.of(2021, 1, 1), zahlungen.get(4).getZahlungsDatum());

    }

}
