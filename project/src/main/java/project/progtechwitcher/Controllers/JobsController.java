package project.progtechwitcher.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class JobsController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

}
