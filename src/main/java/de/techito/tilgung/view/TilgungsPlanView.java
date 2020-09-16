package de.techito.tilgung.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import de.techito.tilgung.controller.TilgungsPlanController;
import de.techito.tilgung.util.ParseUtils;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TilgungsPlanView extends Application {

    private BigDecimal gesamtdarlehen;

    private BigDecimal monatlicheRate;

    private BigDecimal tilgungsSatz;

    private BigDecimal zinsSatz;

    private LocalDate ersteFaelligkeit;

    private ObservableList<TilgungsPlanTableViewModel> zahlungen = FXCollections.observableArrayList();

    private Label errorLabel = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane mainGrid = new GridPane();
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);
        mainGrid.setPadding(new Insets(10, 10, 20, 10));

        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setPadding(new Insets(10, 10, 20, 10));

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

        TableView<TilgungsPlanTableViewModel> zahlungenTable = new TableView<>();

        TableColumn<TilgungsPlanTableViewModel, String> column1 = new TableColumn<>("Fälligkeit");
        column1.setCellValueFactory(new PropertyValueFactory<>("zahlungsDatum"));

        TableColumn<TilgungsPlanTableViewModel, String> column2 = new TableColumn<>("Tilgungsanteil");
        column2.setCellValueFactory(new PropertyValueFactory<>("tilgungsAnteil"));

        TableColumn<TilgungsPlanTableViewModel, String> column3 = new TableColumn<>("Zinsanteil");
        column3.setCellValueFactory(new PropertyValueFactory<>("zinsAnteil"));

        TableColumn<TilgungsPlanTableViewModel, String> column4 = new TableColumn<>("Restdarlehen");
        column4.setCellValueFactory(new PropertyValueFactory<>("restDarlehen"));

        TableColumn<TilgungsPlanTableViewModel, String> column5 = new TableColumn<>("Zinsen gesamt bisher");
        column5.setCellValueFactory(new PropertyValueFactory<>("zinsenGesamtBisher"));

        zahlungenTable.getColumns().add(column1);
        zahlungenTable.getColumns().add(column2);
        zahlungenTable.getColumns().add(column3);
        zahlungenTable.getColumns().add(column4);
        zahlungenTable.getColumns().add(column5);

        zahlungenTable.setItems(zahlungen);

        inputGrid.add(gesamtDarlehenLabel, 0, 1);
        inputGrid.add(gesamtDarlehenInput, 1, 1);

        inputGrid.add(monatlicheRateLabel, 0, 2);
        inputGrid.add(monatlicheRateInput, 1, 2);

        inputGrid.add(tilgungsSatzLabel, 0, 3);
        inputGrid.add(tilgungsSatzInput, 1, 3);

        inputGrid.add(zinsSatzLabel, 0, 4);
        inputGrid.add(zinsSatzInput, 1, 4);

        inputGrid.add(erstFaelligkeitLabel, 0, 5);
        inputGrid.add(ersteFaelligkeitInput, 1, 5);

        inputGrid.add(errorLabel, 0, 6);

        inputGrid.add(createButton, 0, 7);
        inputGrid.add(quitButton, 1, 7);

        mainGrid.add(inputGrid, 0, 1);
        mainGrid.add(zahlungenTable, 0, 2);

        Scene scene = new Scene(mainGrid);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tilgungsplan");
        primaryStage.show();
    }


    private EventHandler<Event> createTilgungsplanHandler() {
        return event -> {
            if (gesamtdarlehen != null && monatlicheRate != null && tilgungsSatz != null && zinsSatz != null && ersteFaelligkeit != null) {
                errorLabel.setVisible(false);
                zahlungen.clear();
                List<TilgungsPlanTableViewModel> zahlungenToAdd = 
                    TilgungsPlanController.getTilgungsPlan(gesamtdarlehen, monatlicheRate, tilgungsSatz, zinsSatz, ersteFaelligkeit)
                        .getZahlungen()
                        .stream()
                        .map(TilgungsPlanTableViewModel::new)
                        .collect(Collectors.toList());
                for (TilgungsPlanTableViewModel zahlung : zahlungenToAdd) {
                    zahlungen.add(zahlung);
                }
            } else {
                errorLabel.setVisible(true);
            }
        };
    }

    private EventHandler<Event> closeWindowHandler() {
        return event -> {
            Button closeButton = (Button) event.getSource();
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        };
    }

    
}
