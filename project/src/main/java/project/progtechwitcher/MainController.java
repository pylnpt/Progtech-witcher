package project.progtechwitcher;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class MainController {
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
    private Button registerBtn;

    @FXML
    private void initialize() {
        loadPage("/project/progtechwitcher/fxml/home-view.fxml");

        initializeNavigation();;
    }

    private void initializeNavigation() {
        homeBtn.setOnAction(event -> loadPage("/project/progtechwitcher/fxml/home-view.fxml"));
        profileBtn.setOnAction(event -> loadPage("/project/progtechwitcher/fxml/profile-view.fxml"));
        usersBtn.setOnAction(event -> loadPage("/project/progtechwitcher/fxml/users-view.fxml"));
        jobsBtn.setOnAction(event -> loadPage("/project/progtechwitcher/fxml/jobs-view.fxml"));
        postAJobBtn.setOnAction(event -> loadPage("/project/progtechwitcher/fxml/post-a-job-view.fxml"));
        registerBtn.setOnAction(event -> loadPage("/project/progtechwitcher/fxml/register-view.fxml"));
    }

    private void loadPage(String pageName) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(pageName)));
            AnchorPane newPane = loader.load();

            contentContainer.getChildren().setAll(newPane);

            AnchorPane.setBottomAnchor(newPane, 0.0);
            AnchorPane.setLeftAnchor(newPane, 0.0);
            AnchorPane.setRightAnchor(newPane, 0.0);
            AnchorPane.setTopAnchor(newPane, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}