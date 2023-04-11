package de.techito.tilgung.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.logging.Logger;

import de.techito.tilgung.service.TilgungsPlanService;

public class CommandLineApp {

    private static Logger log = Logger.getLogger(CommandLineApp.class.getName());

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            log.info("Ihr Gesamtdarlehen: ");
            String darlehenInput = br.readLine();
            log.info("Ihr gewünschter monatlicher Beitrag: ");
            String monatsBeitrag = br.readLine();
            log.info("Ihr Tilgungssatz: ");
            String tilgunsSatzInput = br.readLine();
            log.info("Ihr Zinssatz: ");
            String zinsSatzInput = br.readLine();
            
            String tilgungsplanString = 
                    TilgungsPlanService.getTilgungsPlan(
                        new BigDecimal(darlehenInput), 
                        new BigDecimal(monatsBeitrag), 
                        new BigDecimal(tilgunsSatzInput), 
                        new BigDecimal(zinsSatzInput), 
                        LocalDate.now().withDayOfMonth(1),
                        false,
                        null).toString();
                        
            log.info(tilgungsplanString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    
}
