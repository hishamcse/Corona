package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DialogController {
    @FXML
    private Label name;
    @FXML
    private Label cases;
    @FXML
    private Label deaths;
    @FXML
    private Label recovered;
    @FXML
    private Label active;
    @FXML
    private Label date;
    @FXML
    private Label time;

    public void show(String country,CountryInfo countryInfo){
        int c=0;

        name.setFont(Font.font(30));
        cases.setFont(Font.font(30));
        deaths.setFont(Font.font(30));
        recovered.setFont(Font.font(30));
        active.setFont(Font.font(30));

        for(int i=0;i<countryInfo.getNameslist().size();i++){
            if(countryInfo.getNameslist().get(i).toLowerCase().equals(country.toLowerCase())){
                name.setText(countryInfo.getWholelist().get(i).getName());
                cases.setText(countryInfo.getWholelist().get(i).getCases());
                deaths.setText(countryInfo.getWholelist().get(i).getDeaths());
                recovered.setText(countryInfo.getWholelist().get(i).getRecovered());
                active.setText(countryInfo.getWholelist().get(i).getActive());
                DateTimeFormatter df=DateTimeFormatter.ofPattern("MMMM d,yyyy");
                date.setText(df.format(LocalDate.now()));
                time.setText(countryInfo.getTime().toString().substring(0,8));
                c=1;
                break;
            }
        }
//        if(c==0){
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Error");
//            alert.setHeaderText("Warning");
//            alert.setContentText("Wrong Entry for Country");
//            alert.showAndWait();
//            return;
//        }
    }
}
