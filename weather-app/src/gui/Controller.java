package gui;

import domain.weather;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import service.Service;

import java.io.Writer;
import java.util.List;

public class Controller {
    private Service service;

    public Controller(Service service) {
        this.service = service;
    }
    @FXML
    private ListView<weather> WeatherList;
    @FXML
    private TextArea Result=new TextArea();
    @FXML
    private TextField preciptiationType = new TextField();
    @FXML
    private TextField newProbability= new TextField();
    @FXML
    private TextField NewDescription = new TextField();
    @FXML
    void update(){
        weather report = WeatherList.selectionModelProperty().get().getSelectedItem();
        report.update(Integer.parseInt(newProbability.getText()),NewDescription.getText());
        service.updateInDatabase(report);
        //WeatherList.getSelectionModel().getSelectedItem().update(Integer.parseInt(newProbability.getText()),NewDescription.getText());

        ObservableList<weather> reports = FXCollections.observableList(service.getAll());
        WeatherList.setItems(reports);

        ObservableList<String> descriptions = FXCollections.observableList(service.descriptions());
        Filter.setItems(descriptions);
    }
    @FXML
    void ShowResult(ActionEvent event) {
        Result.setText(String.valueOf(service.hoursof(preciptiationType.getText())));
    }
    @FXML
    private ComboBox<String> Filter = new ComboBox<>();
    public void initialize()
    {
        ObservableList<weather> reports = FXCollections.observableList(service.getAll());
        WeatherList.setItems(reports);
        ObservableList<String> descriptions = FXCollections.observableList(service.descriptions());
        Filter.setItems(descriptions);
    }
    @FXML
    void filterText(ActionEvent event) {
        ObservableList<weather> reports = FXCollections.observableList(service.filterBy(Filter.getValue()));
        WeatherList.setItems(reports);
    }

}
