package project.progtechwitcher.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
        rewardComboBox.setItems(comboBoxItemList);
        postTheJobBtn.setOnAction(event -> System.out.println("kex"));
    }
}
