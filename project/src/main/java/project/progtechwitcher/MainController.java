package project.progtechwitcher;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import project.progtechwitcher.Database.Database;
import project.progtechwitcher.Logging.Log;
import project.progtechwitcher.models.user.Employee;
import project.progtechwitcher.models.user.Employer;
import project.progtechwitcher.models.user.UserBase;

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
    private Button loginBtn;
    @FXML
    private TextField usernameInput;
    @FXML
    private TextField passwordInput;
    @FXML
    private Label useridlabel;
    public static int userId = 0;

    @FXML
    private void initialize() {
        loadPage("/project/progtechwitcher/fxml/home-view.fxml");
        useridlabel.setVisible(false);

        SetButtonsOn();
        initializeNavigationNotLoggedIn();

        loginBtn.setOnAction(event -> {
            Object node = Database.Login(usernameInput.getText(), passwordInput.getText());
            try {
                useridlabel.setText(Integer.toString(((UserBase) node).getId()));
                userId = Integer.parseInt(useridlabel.getText());
                if(userId!=0) {
                    LoggedInFields();
                }
            }
            catch(Exception e)
            {
                Log.Warning(MainController.class, e.getMessage());
            }
            if(node == null)
            {
                NotLoggedInFields();
            }
            useridlabel.setText("");
            //System.out.println(userId);
        });
    }

    private void LoggedInFields()
    {
        usernameInput.setText("");
        usernameInput.setVisible(false);
        usernameInput.setDisable(true);
        passwordInput.setText("");
        passwordInput.setVisible(false);
        passwordInput.setDisable(true);
        loginBtn.setText("Logout");

        Database.GetUsers(userId);
        System.out.println(Database.users.get(0).toString());

        switch(Database.users.get(0).getRole())
        {

            case ADMIN -> {
                SetButtonsOn();
                initializeNavigationAdmin();
                break;
            }
            case EMPLOYER -> {
                SetButtonsOn();
                initializeNavigationEmployer();
                break;
            }
            case EMPLOYEE -> {
                SetButtonsOn();
                initializeNavigationEmployee();
                break;
            }
            default -> {
                SetButtonsOn();
                initializeNavigationNotLoggedIn();
                break;
            }
        }

    }
    private void NotLoggedInFields()
    {
        SetButtonsOn();
        initializeNavigationNotLoggedIn();
        loadPage("/project/progtechwitcher/fxml/home-view.fxml");
        userId=0;
        usernameInput.setVisible(true);
        usernameInput.setDisable(false);
        passwordInput.setVisible(true);
        passwordInput.setDisable(false);
        loginBtn.setText("Login");
    }

    private void initializeNavigationEmployee() {
        homeBtn.setOnAction(event -> loadPage("/project/progtechwitcher/fxml/home-view.fxml"));
        profileBtn.setOnAction(event -> loadPage("/project/progtechwitcher/fxml/profile-view.fxml"));
        profileBtn.setTranslateY(-(65));
        jobsBtn.setOnAction(event -> loadPage("/project/progtechwitcher/fxml/jobs-view.fxml"));
        usersBtn.setDisable(true);
        usersBtn.setVisible(false);
        postAJobBtn.setDisable(true);
        postAJobBtn.setVisible(false);
        registerBtn.setDisable(true);
        registerBtn.setVisible(false);
    }
    private void initializeNavigationEmployer() {
        homeBtn.setOnAction(event -> loadPage("/project/progtechwitcher/fxml/home-view.fxml"));
        profileBtn.setOnAction(event -> loadPage("/project/progtechwitcher/fxml/profile-view.fxml"));
        profileBtn.setTranslateY(-(65+65));
        jobsBtn.setDisable(true);
        jobsBtn.setVisible(false);
        usersBtn.setDisable(true);
        usersBtn.setVisible(false);
        postAJobBtn.setOnAction(event -> loadPage("/project/progtechwitcher/fxml/post-a-job-view.fxml"));
        registerBtn.setDisable(true);
        registerBtn.setVisible(false);
    }
    private void initializeNavigationAdmin() {
        homeBtn.setOnAction(event -> loadPage("/project/progtechwitcher/fxml/home-view.fxml"));
        profileBtn.setOnAction(event -> loadPage("/project/progtechwitcher/fxml/profile-view.fxml"));
        jobsBtn.setOnAction(event -> loadPage("/project/progtechwitcher/fxml/jobs-view.fxml"));
        usersBtn.setOnAction(event -> loadPage("/project/progtechwitcher/fxml/users-view.fxml"));
        usersBtn.setTranslateY(-(65+65+65));
        postAJobBtn.setDisable(true);
        postAJobBtn.setVisible(false);
        registerBtn.setDisable(true);
        registerBtn.setVisible(false);
    }
    private void initializeNavigationNotLoggedIn() {
        homeBtn.setOnAction(event -> loadPage("/project/progtechwitcher/fxml/home-view.fxml"));
        profileBtn.setDisable(true);
        profileBtn.setVisible(false);
        jobsBtn.setDisable(true);
        jobsBtn.setVisible(false);
        usersBtn.setDisable(true);
        usersBtn.setVisible(false);
        postAJobBtn.setDisable(true);
        postAJobBtn.setVisible(false);
        registerBtn.setOnAction(event -> loadPage("/project/progtechwitcher/fxml/register-view.fxml"));
        registerBtn.setTranslateY(-(65+65+65));
    }

    private void SetButtonsOn()
    {
        profileBtn.setDisable(false);
        profileBtn.setVisible(true);
        jobsBtn.setDisable(false);
        jobsBtn.setVisible(true);
        usersBtn.setDisable(false);
        usersBtn.setVisible(true);
        postAJobBtn.setDisable(false);
        postAJobBtn.setVisible(true);
        registerBtn.setDisable(false);
        registerBtn.setVisible(true);
        homeBtn.setDisable(false);
        homeBtn.setVisible(true);
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