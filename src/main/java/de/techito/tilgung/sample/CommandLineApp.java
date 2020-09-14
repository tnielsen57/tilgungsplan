package de.techito.tilgung.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;

import de.techito.tilgung.controller.TilgungsPlanController;
import de.techito.tilgung.model.TilgungsPlan;

public class CommandLineApp {

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Ihr Gesamtdarlehen: ");
            String darlehenInput = br.readLine();
            System.out.print("Ihr gewünschter monatlicher Beitrag: ");
            String monatsBeitrag = br.readLine();
            System.out.print("Ihr Tilgungssatz: ");
            String tilgunsSatzInput = br.readLine();
            System.out.print("Ihr Zinssatz: ");
            String zinsSatzInput = br.readLine();
            TilgungsPlan tilgungsPlan = TilgungsPlanController.getTilgungsPlan(new BigDecimal(darlehenInput), new BigDecimal(monatsBeitrag), new BigDecimal(tilgunsSatzInput), new BigDecimal(zinsSatzInput), LocalDate.now().withDayOfMonth(1));
            System.out.println(tilgungsPlan.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    
}
