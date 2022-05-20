package project.progtechwitcher.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import project.progtechwitcher.Database.Database;
import project.progtechwitcher.MainController;
import project.progtechwitcher.models.user.CanAdvertiseJobs;
import project.progtechwitcher.models.user.UserBase;

public class PostAJobController {
    ObservableList<String> comboBoxItemList = FXCollections.observableArrayList("1","2","3","4","5");
    @FXML
    private TextField jobTitleInput;
    @FXML
    private TextArea jobDescriptionTextArea;
    @FXML
    private TextField requiredLevelInput;
    @FXML
    private ComboBox rewardComboBox;
    @FXML
    private Button postTheJobBtn;
    private UserBase user;

    @FXML
    private void initialize()
    {
        Database.GetUsers(MainController.userId);
        user = Database.users.get(0);

        if(user.getId() != 0) {
            rewardComboBox.setItems(comboBoxItemList);
            postTheJobBtn.setOnAction(event -> AddJob());
        }
    }
    private void AddJob()
    {
        new CanAdvertiseJobs(user).AdvertiseJob(jobTitleInput.getText(),
                jobDescriptionTextArea.getText(), Integer.parseInt(rewardComboBox.getValue().toString()), Integer.parseInt(requiredLevelInput.getText()));
    }

}
