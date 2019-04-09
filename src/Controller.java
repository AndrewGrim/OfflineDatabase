import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public TableView<Monster> databaseTable;
    public TableColumn<Monster, String> col_id;
    public TableColumn<Monster, String> col_name;
    public TableColumn<Monster, String> col_gender;
    public TableColumn<Monster, String> col_species;
    public TableColumn<Monster, String> col_generation;

    public TextField nameEntry;
    public ComboBox<String> genderCombo;
    public ComboBox<String> speciesCombo;
    public ComboBox<String> generationCombo;
    public Button addMonster;

    public ImageView imageViewIcon;

    public String titleIconPath = "images/mhw-title.png";
    public String titleIceborneIconPath = "images/mhw-title-iceborne.png";

    ObservableList<Monster> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        col_species.setCellValueFactory(new PropertyValueFactory<>("species"));
        col_generation.setCellValueFactory(new PropertyValueFactory<>("generation"));

        Image image = new Image(titleIconPath);
        imageViewIcon.setImage(image);

        DatabaseOperations db = new DatabaseOperations();
        db.setTableView(oblist);

        databaseTable.setItems(oblist);

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
                "Elder Dragon",
                "Relict"
        );

        generationCombo.getItems().addAll(
                "1st",
                "2nd",
                "3rd",
                "4th",
                "5th"
        );
    }

    public void addMonster() {
        if (nameEntry.getLength() == 0) {
            System.out.println("You should probably enter a name if you want to submit new data!");
        } else if (genderCombo.getValue() == null) {
            System.out.println("You should probably enter a gender if you want to submit new data!");
        } else if (speciesCombo.getValue() == null) {
            System.out.println("You should probably enter a species if you want to submit new data!");
        } else if (generationCombo.getValue() == null) {
            System.out.println("You should probably enter a generation if you want to submit new data!");
        } else {
            //System.out.println("currently disabled!");
            DatabaseOperations db = new DatabaseOperations();

            databaseTable.getItems().clear(); // cleans up the table to prevent duplicates

            db.insert(nameEntry.getText(), genderCombo.getValue(), speciesCombo.getValue(), generationCombo.getValue());
            db.setTableView(oblist);

            databaseTable.setItems(oblist);
        }
    }

    public void loadMonsterIcon() {
        // get the row selected and from the id load the appropriate image of the monster
        int index = databaseTable.getSelectionModel().getSelectedIndex();
        Monster m = databaseTable.getItems().get(index);
        int id = m.getId();
        String iconPath = "images/" + id + ".png";

        try {
            Image image = new Image(iconPath);
            imageViewIcon.setImage(image);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Monster ID icon not found in the 'images' folder!");
            Image image = new Image(titleIconPath);
            imageViewIcon.setImage(image);
        }
    }
}
