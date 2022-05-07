package project.progtechwitcher;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class HomePageController {
    @FXML
    private AnchorPane contentContainer;
    @FXML
    private Button homeBtn;
    @FXML
    private Button profileBtn;
    @FXML
    private Button usersBtn;
    @FXML
    private Button jobsBtn;
    @FXML
    private Button postAJobBtn;

    @FXML
    private void initialize() {
        // homeBtn.setOnAction(event -> loadPage("Cats"));
        // profileBtn.setOnAction(event -> loadPage("Dogs"));
        usersBtn.setOnAction(event -> loadPage("Birds"));
        jobsBtn.setOnAction(event -> loadPage("jobs-view.fxml"));
        //postAJobBtn.setOnAction(event -> loadPage("Dogs"));
    }

    private void loadPage(String name) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(name)));
            AnchorPane newPane = loader.load();

            // Set the loaded FXML file as the content of our main right-side pane
            contentContainer.getChildren().setAll(newPane);

            // Reset the anchors
            AnchorPane.setBottomAnchor(newPane, 0.0);
            AnchorPane.setLeftAnchor(newPane, 0.0);
            AnchorPane.setRightAnchor(newPane, 0.0);
            AnchorPane.setTopAnchor(newPane, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}