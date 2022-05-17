package project.progtechwitcher.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import project.progtechwitcher.Database.Database;
import project.progtechwitcher.MainController;
import project.progtechwitcher.models.user.CanAdvertiseJobs;

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

    @FXML
    private void initialize()
    {
        if(MainController.userId != 0) {
            Database.GetUsers(MainController.userId);
            rewardComboBox.setItems(comboBoxItemList);

            postTheJobBtn.setOnAction(event -> new CanAdvertiseJobs(Database.users.get(0)).AdvertiseJob(jobTitleInput.getText(),
                    jobDescriptionTextArea.getText(), Integer.parseInt(rewardComboBox.getValue().toString()), Integer.parseInt(requiredLevelInput.getText())));
        }
    }

}
