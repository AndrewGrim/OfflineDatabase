import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import java.util.Optional;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    // setup for different variables so they can be easily used by all the functions
    public Label statusLabel;

    public TableView<Monster> databaseTable;
    public TableColumn<Monster, String> col_id;
    public TableColumn<Monster, String> col_name;
    public TableColumn<Monster, String> col_species;
    public TableColumn<Monster, String> col_generation;
    public TableColumn<Monster, String> col_size;

    public TextField nameEntry;
    public ComboBox<String> speciesCombo;
    public ComboBox<String> generationCombo;
    public ComboBox<String> sizeCombo;
    public Button addMonster;
    public Button updateMonster;
    public Button deleteMonster;
    public Button changeTitleIcon;

    ObservableList<Monster> oblist = FXCollections.observableArrayList();

    public DatabaseOperations db = new DatabaseOperations();

    public ImageView imageViewIcon;

    public String titleIconPath = db.getDefaultIconPath();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // runs on startup populates the tableview with the data from the database
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_species.setCellValueFactory(new PropertyValueFactory<>("species"));
        col_generation.setCellValueFactory(new PropertyValueFactory<>("generation"));
        col_size.setCellValueFactory(new PropertyValueFactory<>("size"));

        // loads the title icon
        Image image = new Image(titleIconPath);
        imageViewIcon.setImage(image);

        DatabaseOperations db = new DatabaseOperations();
        db.setTableView(oblist);

        databaseTable.setItems(oblist);

        // gives all the options for the different comboboxes
        speciesCombo.getItems().addAll(
                "Bird Wyvern",
                "Brute Wyvern",
                "Elder Dragon",
                "Flying Wyvern",
                "Fanged Wyvern",
                "Fish",
                "Herbivore",
                "Lynian",
                "Neopteron",
                "Piscine Wyvern",
                "Relict",
                "Wingdrake"
        );

        generationCombo.getItems().addAll(
                "1st",
                "2nd",
                "3rd",
                "4th",
                "5th"
        );

        sizeCombo.getItems().addAll(
                "Large",
                "Small"

        );
    }

    public void addNewMonster() {
        // check whether any fields are empty and adds a monster into the db using the entered values, reload tableview
        if (nameEntry.getLength() == 0) {
            System.out.println("You should probably enter a name if you want to submit new data!");
        } else if (speciesCombo.getValue() == null) {
            System.out.println("You should probably enter a species if you want to submit new data!");
        } else if (generationCombo.getValue() == null) {
            System.out.println("You should probably enter a generation if you want to submit new data!");
        } else if (sizeCombo.getValue() == null) {
            System.out.println("You should probably enter a size if you want to submit new data!");
        } else {
            databaseTable.getItems().clear(); // cleans up the table to prevent duplicates

            db.insertMonster(nameEntry.getText(), speciesCombo.getValue(), generationCombo.getValue(), sizeCombo.getValue());
            db.setTableView(oblist);

            databaseTable.setItems(oblist);
        }
    }

    public void updateSelectedMonster() {
        // grab the id of the currently selected monster and update its details in the db, reload tableview
        int localID = getMonsterID();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Update this ID?");
        alert.setContentText("Are you sure you want to update this Monster ID: " + localID + " in the database?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if (nameEntry.getLength() == 0) {
                System.out.println("You should probably enter a name if you want to update data!");
            } else if (speciesCombo.getValue() == null) {
                System.out.println("You should probably enter a species if you want to update data!");
            } else if (generationCombo.getValue() == null) {
                System.out.println("You should probably enter a generation if you want to update data!");
            } else if (sizeCombo.getValue() == null) {
                System.out.println("You should probably enter a size if you want to update data!");
            } else {
                db.updateMonster(nameEntry.getText(), speciesCombo.getValue(), generationCombo.getValue(), sizeCombo.getValue(), getMonsterID());
                statusLabel.setText("Monster ID: " + localID + " updated!");
                refreshTableView();
            }
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    public void loadMonsterIcon() {
        // grabs the currently selected id, from that the monsters name and loads the icon for that monster
        String iconPath = "images/" + db.getMonsterName(getMonsterID()) + ".png";
        System.out.println("Monster ID: " + getMonsterID() + " Monster Name: " + db.getMonsterName(getMonsterID()));

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

    public void deleteSelectedMonster() {
        // grabs the currently selected monster's id and deletes that monster from the db, reload tableview
        int localID = getMonsterID();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete this ID?");
        alert.setContentText("Are you sure you want to delete this Monster ID: " + localID + " from the database?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            db.delete("monsters", getMonsterID());
            statusLabel.setText("Monster ID: " + localID + " deleted!");
            refreshTableView();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    public void setTitleIcon() {
        // changes the default icon to the other one, also shows the newly selected icon in imageview
        db.updateDefaultIcon(db.getDefaultIconName());
        statusLabel.setText("Default title icon changed to: " + db.getDefaultIconName() + "!");
        String iconPath = db.getDefaultIconPath();

        try {
            Image image = new Image(iconPath);
            imageViewIcon.setImage(image);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Title icon not found in the 'images' folder!");
            Image image = new Image(titleIconPath);
            imageViewIcon.setImage(image);
        }
    }

    public int getMonsterID() {
        // returns the currently selected monster's id
        resetStatusLabel();
        int index = databaseTable.getSelectionModel().getSelectedIndex();
        Monster m = databaseTable.getItems().get(index);
        int id = m.getId();
        return id;
    }

    public void refreshTableView() {
        // clears the table, loads the data from the db again and populates the tableview with the new info
        databaseTable.getItems().clear(); // cleans up the table to prevent duplicates
        db.setTableView(oblist);
        databaseTable.setItems(oblist);
    }

    public void resetStatusLabel() {
        // changes the status label at the bottom to not display any messages
        statusLabel.setText("");
    }
}
