import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    public Button addMonster;
    public TableView<Monster> table;
    public TableColumn<Monster, String> col_id;
    public TableColumn<Monster, String> col_name;
    public TableColumn<Monster, String> col_sex;
    public TableColumn<Monster, String> col_genus;
    public TextField nameEntry;
    public TextField sexEntry;
    public TextField genusEntry;

    ObservableList<Monster> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        col_genus.setCellValueFactory(new PropertyValueFactory<>("genus"));

        DatabaseOperations db = new DatabaseOperations();
        db.setTableView(oblist);

        table.setItems(oblist);
    }

    public void addMonsterClicked() {
        DatabaseOperations db = new DatabaseOperations();
        db.insert(nameEntry.getText(), sexEntry.getText(), genusEntry.getText());
    }
}
