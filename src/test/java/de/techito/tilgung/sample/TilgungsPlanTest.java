package de.techito.tilgung.sample;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.techito.tilgung.controller.TilgungsPlanController;
import de.techito.tilgung.model.TilgungsPlan;

public class TilgungsPlanTest {

    @Test
    public void shouldGetCorrectNumberOfElements() {
        TilgungsPlan tilgungsPlan = TilgungsPlanController.getTilgungsPlan(
            BigDecimal.valueOf(10000),
            BigDecimal.valueOf(1500),
            BigDecimal.valueOf(0.03),
            BigDecimal.valueOf(0.015),
            LocalDate.of(2020, 9, 1));

        Assertions.assertEquals(7, tilgungsPlan.getZahlungen().size());
    }

}
