package de.techito.tilgung.util;

import java.util.regex.Pattern;

public class ParseUtils {

    private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    private ParseUtils() {
        // Nicht instanziieren, Util-Klasse mit statischen Methoden
    }
 
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false; 
        }
        return pattern.matcher(strNum).matches();
    }
    
}
