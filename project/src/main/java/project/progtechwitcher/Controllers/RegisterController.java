package project.progtechwitcher.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {
    ObservableList<String> comboBoxItemList = FXCollections.observableArrayList("Witcher", "Employer");
    @FXML
    private TextField userNameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private PasswordField rePasswordInput;
    @FXML
    private ComboBox roleComboBox;
    @FXML
    private Button registerBtn;

    @FXML
    private void initialize()
    {
        roleComboBox.setItems(comboBoxItemList);
        //registerBtn.setOnAction(event -> System.out.println("kox"));
    }
}
