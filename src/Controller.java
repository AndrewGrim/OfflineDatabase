import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    public TableView<Monster> table;
    public TableColumn<Monster, String> col_id; // could be trouble??
    public TableColumn<Monster, String> col_name;
    public TableColumn<Monster, String> col_gender;
    public TableColumn<Monster, String> col_species;
    public TableColumn<Monster, String> col_generation;
    public TableColumn<Monster, String> col_icon;

    public TextField nameEntry;
    public ComboBox<String> genderCombo;
    public ComboBox<String> speciesCombo;
    public ComboBox<String> generationCombo;
    public TextField iconIDEntry;

    public Button addMonster;

    ObservableList<Monster> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        col_species.setCellValueFactory(new PropertyValueFactory<>("species"));
        col_generation.setCellValueFactory(new PropertyValueFactory<>("generation"));
        col_icon.setCellValueFactory(new PropertyValueFactory<>("icon"));

        DatabaseOperations db = new DatabaseOperations();
        db.setTableView(oblist);

        table.setItems(oblist);

        genderCombo.getItems().addAll(
                "Male",
                "Female",
                "Both",
                "Undefined"
        );

        speciesCombo.getItems().addAll(
                "Flying Wyvern",
                "Fanged Wyvern",
                "Brute Wyvern",
                "Bird Wyvern",
                "Piscine Wyvern",
                "Elder Dragon"
        );

        generationCombo.getItems().addAll(
                "1st",
                "2nd",
                "3rd",
                "4th",
                "5th"
        );
    }

    public void addMonsterClicked() {
        if (nameEntry.getLength() == 0) {
            System.out.println("You should probably enter a name if you want to submit new data!");
        } else if (genderCombo.getValue() == null) {
            System.out.println("You should probably enter a gender if you want to submit new data!");
        } else if (speciesCombo.getValue() == null) {
            System.out.println("You should probably enter a species if you want to submit new data!");
        } else if (generationCombo.getValue() == null) {
            System.out.println("You should probably enter a generation if you want to submit new data!");
        }  else if (iconIDEntry.getLength() == 0) {
            System.out.println("You should probably enter an iconID if you want to submit new data!");
        } else {
            //System.out.println("currently disabled!");
            DatabaseOperations db = new DatabaseOperations();

            table.getItems().clear(); // cleans up the table to prevent duplicates

            db.insert(nameEntry.getText(), genderCombo.getValue(), speciesCombo.getValue(), generationCombo.getValue(), iconIDEntry.getText());
            db.setTableView(oblist);


            table.setItems(oblist);
        }
    }
}
