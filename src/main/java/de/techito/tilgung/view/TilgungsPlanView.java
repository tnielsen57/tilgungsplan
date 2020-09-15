package de.techito.tilgung.view;

import java.math.BigDecimal;
import java.time.LocalDate;

import de.techito.tilgung.controller.TilgungsPlanController;
import de.techito.tilgung.util.ParseUtils;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TilgungsPlanView extends Application {

    private BigDecimal gesamtdarlehen;

    private BigDecimal monatlicheRate;

    private BigDecimal tilgungsSatz;

    private BigDecimal zinsSatz;

    private LocalDate ersteFaelligkeit;

    private Label errorLabel = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 20, 10));

        errorLabel.setText("Bitte alle Werte vorgeben!");
        errorLabel.setVisible(false);

        Label gesamtDarlehenLabel = new Label("Gesamtdarlehen:");
        TextField gesamtDarlehenInput = new TextField();
        gesamtDarlehenInput.addEventHandler(InputEvent.ANY, event -> {
            if (ParseUtils.isNumeric(gesamtDarlehenInput.getText())) {
                this.gesamtdarlehen = new BigDecimal(gesamtDarlehenInput.getText());
            } else {
                this.gesamtdarlehen = null;
            }
        });

        Label monatlicheRateLabel = new Label("Gewünschte monatliche Rate:");
        TextField monatlicheRateInput = new TextField();
        monatlicheRateInput.addEventHandler(InputEvent.ANY, event -> {
            if (ParseUtils.isNumeric(monatlicheRateInput.getText())) {
                this.monatlicheRate = new BigDecimal(monatlicheRateInput.getText());
            } else {
                this.monatlicheRate = null;
            }
        });

        Label tilgungsSatzLabel = new Label("Tilgungssatz:");
        TextField tilgungsSatzInput = new TextField();
        tilgungsSatzInput.addEventHandler(InputEvent.ANY, event -> {
            if (ParseUtils.isNumeric(tilgungsSatzInput.getText())) {
                this.tilgungsSatz = new BigDecimal(tilgungsSatzInput.getText());
            } else {
                this.tilgungsSatz = null;
            }
        });

        Label zinsSatzLabel = new Label("Zinssatz:");
        TextField zinsSatzInput = new TextField();
        zinsSatzInput.addEventHandler(InputEvent.ANY, event -> {
            if (ParseUtils.isNumeric(zinsSatzInput.getText())) {
                this.zinsSatz = new BigDecimal(zinsSatzInput.getText());
            } else {
                this.zinsSatz = null;
            }
        });

        Label erstFaelligkeitLabel = new Label("Erste Rate fällig am:");
        DatePicker ersteFaelligkeitInput = new DatePicker();
        ersteFaelligkeitInput.addEventHandler(InputEvent.ANY, event -> ersteFaelligkeit = ersteFaelligkeitInput.getValue());

        Button createButton = new Button("Erstelle Tilgungsplan");
        createButton.setOnMouseClicked(createTilgungsplanHandler());
        createButton.setOnTouchPressed(createTilgungsplanHandler());

        Button quitButton = new Button("Beenden");
        quitButton.setOnMouseClicked(closeWindowHandler());
        quitButton.setOnTouchPressed(closeWindowHandler());

        grid.add(gesamtDarlehenLabel, 0, 1);
        grid.add(gesamtDarlehenInput, 1, 1);

        grid.add(monatlicheRateLabel, 0, 2);
        grid.add(monatlicheRateInput, 1, 2);

        grid.add(tilgungsSatzLabel, 0, 3);
        grid.add(tilgungsSatzInput, 1, 3);

        grid.add(zinsSatzLabel, 0, 4);
        grid.add(zinsSatzInput, 1, 4);

        grid.add(erstFaelligkeitLabel, 0, 5);
        grid.add(ersteFaelligkeitInput, 1, 5);

        grid.add(errorLabel, 0, 6);

        grid.add(createButton, 0, 7);
        grid.add(quitButton, 1, 7);

        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tilgungsplan");
        primaryStage.show();
    }


    private EventHandler<Event> createTilgungsplanHandler() {
        return new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (gesamtdarlehen != null && monatlicheRate != null && tilgungsSatz != null && zinsSatz != null && ersteFaelligkeit != null) {
                    errorLabel.setVisible(false);
                    System.out.println(
                        TilgungsPlanController.getTilgungsPlan(
                            gesamtdarlehen,
                            monatlicheRate,
                            tilgungsSatz,
                            zinsSatz,
                            ersteFaelligkeit).toString());
                } else {
                    errorLabel.setVisible(true);
                }
            }
        };
    }

    private EventHandler<Event> closeWindowHandler() {
        return new EventHandler<Event>(){
            @Override
            public void handle(Event event) {
                Button closeButton = (Button) event.getSource();
                Stage stage = (Stage) closeButton.getScene().getWindow();
                stage.close();
            }
        };
    }

    
}
