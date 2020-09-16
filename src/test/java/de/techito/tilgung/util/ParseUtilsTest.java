package de.techito.tilgung.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ParseUtilsTest {

    @Test
    public void shouldReturnTrueOnNumber() {
        assertTrue(ParseUtils.isNumeric("1234"));
    }

    @Test
    public void shouldReturnTrueOnFloatingPointNumber() {
        assertTrue(ParseUtils.isNumeric("12.34"));
    }

    @Test
    public void shouldReturnFalseOnNull() {
        assertFalse(ParseUtils.isNumeric(null));
    }

    @Test
    public void shouldReturnFalseOnEmptyString() {
        assertFalse(ParseUtils.isNumeric(""));
    }

    @Test
    public void shouldReturnFalseOnCharacterString() {
        assertFalse(ParseUtils.isNumeric("123gjkl654"));
    }
    
}
