package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.util.Optional;

public class Controller {

    private CountryInfo countryInfo;
    @FXML
    private TableView<CountryInfo> tableView;
    @FXML
    private ListView<String> listView;
    @FXML
    private ProgressBar progressbar;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField textField;

    public void initialize() {
        countryInfo = new CountryInfo();
        countryInfo.allinitialize();
    }

    @FXML
    public void showtable() {
        Task<ObservableList<CountryInfo>> task = new Task<ObservableList<CountryInfo>>() {
            @Override
            protected ObservableList<CountryInfo> call() throws Exception {
                return FXCollections.observableArrayList(countryInfo.getWholelist());
            }
        };
//        tableView.setItems(countryInfo.getWholelist());
        tableView.itemsProperty().bind(task.valueProperty());
        progressbar.progressProperty().bind(task.progressProperty());
        progressbar.setVisible(true);

        task.setOnSucceeded(event -> progressbar.setVisible(false));
        task.setOnFailed(event -> progressbar.setVisible(false));

        new Thread(task).start();
    }

    @FXML
    public void showcountry() {
        Task<ObservableList<String>> task = new Task<ObservableList<String>>() {
            @Override
            protected ObservableList<String> call() throws Exception {
                return FXCollections.observableArrayList(countryInfo.getNameslist().sorted());
            }
        };
        listView.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }

    @FXML
    public void showdialog() {
        String name = listView.getSelectionModel().getSelectedItem();
        if (name == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Warning");
            alert.setContentText("no country is selected to show");
            alert.showAndWait();
            return;
        } else {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(borderPane.getScene().getWindow());
            dialog.setTitle("Country Details");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("dialog.fxml"));

            try {
                dialog.getDialogPane().setContent(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

            DialogController controller = fxmlLoader.getController();
            controller.show(name, countryInfo);

            Optional<ButtonType> o = dialog.showAndWait();
        }
    }

    @FXML
    public void showdialogsearch() {
        int a=0;
        String name = textField.getText();
        for(int i=0;i<countryInfo.getNameslist().size();i++){
            if(countryInfo.getNameslist().get(i).toLowerCase().equals(name.toLowerCase())){
                a=1;
                break;
            }
        }
        if (name == null || a==0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Warning");
            alert.setContentText("no country is entered or country is invalid");
            alert.showAndWait();
            return;
        } else {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(borderPane.getScene().getWindow());
            dialog.setTitle("Country Details");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("dialog.fxml"));

            try {
                dialog.getDialogPane().setContent(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

            DialogController controller = fxmlLoader.getController();
            controller.show(name, countryInfo);

            Optional<ButtonType> o = dialog.showAndWait();
        }
    }
}
