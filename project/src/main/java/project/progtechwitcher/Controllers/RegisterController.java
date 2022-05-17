package project.progtechwitcher.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import project.progtechwitcher.Database.Database;
import project.progtechwitcher.Logging.Log;
import project.progtechwitcher.models.user.RoleConverter;

import java.util.Objects;

public class RegisterController {
    ObservableList<String> comboBoxItemList = FXCollections.observableArrayList("employee", "employer");
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

            registerBtn.setOnAction(event -> {try{KOX(userNameInput.getText(), passwordInput.getText(), rePasswordInput.getText(), roleComboBox.getValue().toString());}
            catch(Exception e)
            {
                Log.Error(RegisterController.class, e.getMessage());
            }
            });

    }

    private void KOX(String uname, String password, String rePassword, String role)
    {
        if (checkIfFieldsAreCorrect(uname, password, rePassword, role))
        {
            Database.Registrate(uname, password, RoleConverter.StringToRole(role));
            System.out.println("Registrated");
        }

        System.out.println("Nok!");
    }

    private boolean checkIfFieldsAreCorrect(String uname, String password, String rePassword, String role){
        if(!Objects.equals(uname, "") || !Objects.equals(password, "") ||
                !Objects.equals(rePassword, "") || !Objects.equals(role, null))
        {
            if(Objects.equals(password, rePassword)) {
                return true;
            }
        }
        return false;
    }
}
