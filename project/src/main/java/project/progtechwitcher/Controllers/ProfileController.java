package project.progtechwitcher.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import project.progtechwitcher.Hash.MD5Hash;
import project.progtechwitcher.Main;
import project.progtechwitcher.MainController;
import project.progtechwitcher.models.user.CanAdvertiseJobs;
import project.progtechwitcher.models.user.CanTakeJobs;
import project.progtechwitcher.models.user.Role;
import project.progtechwitcher.models.user.UserBase;

public class ProfileController {
    @FXML
    private TextField userNameInput;
    @FXML
    private TextField roleInput;
    @FXML
    private TextField levelInput;
    @FXML
    private Button changePasswordBtn;
    @FXML
    private TableView myJobsTable;
    @FXML
    private HBox tableSection;
    @FXML
    TextArea descriptionTextField;
    @FXML
    private Button doneBtn;

    @FXML
    private Button ModPassword;
    @FXML
    private PasswordField currentPassword;
    @FXML
    private PasswordField NewPassword;
    @FXML
    private PasswordField NewPassword2;
    @FXML
    private Button cancelBtn;

    private int jobId=0;
    UserBase user = MainController.user;
    ProfileTableTemplate profileTable = new ProfileTableTemplate();

    @FXML
    private void initialize()
    {
        profileTable.createTable(myJobsTable, profileTable.tableData, tableSection);
        System.out.println(user.toString());
        passwordOff();
        changePasswordBtn.setOnMouseClicked(event-> {
            passwordOn();
        });

        cancelBtn.setOnMouseClicked(event -> passwordOff());
        ModPassword.setOnMouseClicked(event->ChangePasswd());

        myJobsTable.setOnMouseClicked(e -> clickCell());
        addDataToTextField();
        SetJobDone();

    }
    private void passwordOff() {
        ModPassword.setDisable(true);
        ModPassword.setManaged(false);
        ModPassword.setVisible(false);

        cancelBtn.setDisable(true);
        cancelBtn.setManaged(false);
        cancelBtn.setVisible(false);

        currentPassword.setDisable(true);
        currentPassword.setVisible(false);

        NewPassword.setDisable(true);
        NewPassword.setVisible(false);

        NewPassword2.setDisable(true);
        NewPassword2.setVisible(false);
    }
    private void passwordOn() {
        ModPassword.setDisable(false);
        ModPassword.setManaged(true);
        ModPassword.setVisible(true);

        cancelBtn.setDisable(false);
        cancelBtn.setManaged(true);
        cancelBtn.setVisible(true);

        currentPassword.setDisable(false);
        currentPassword.setVisible(true);

        NewPassword.setDisable(false);
        NewPassword.setVisible(true);

        NewPassword2.setDisable(false);
        NewPassword2.setVisible(true);
    }
    private void addDataToTextField() {
        userNameInput.setText(user.getUsername());
        roleInput.setText(user.getRole().toString());
        levelInput.setText(Integer.toString(user.getLevel()));
    }
    @FXML
    public void clickCell() {
        try {
            descriptionTextField.setText("");
            String splitDescription = myJobsTable.getSelectionModel().getSelectedItem().toString().split(",")[2];
            String description = splitDescription.split("'")[1];

            String idSplit = myJobsTable.getSelectionModel().getSelectedItem().toString().split(",")[0];
            jobId = Integer.parseInt(idSplit.split("=")[1]);
            descriptionTextField.setText(description);

        }
        catch(Exception e)
        {

        }
    }
    private void ChangePasswd() {
        if(!(currentPassword.getText() =="" || NewPassword.getText()=="" || NewPassword2.getText()==""))
        {
            if(MD5Hash.getMd5(currentPassword.getText()).equals(user.getPassword()))
            {
                if(NewPassword.getText().equals(NewPassword2.getText()))
                {
                    user.ModifyPassword(NewPassword.getText());
                }
            }
        }
    }
    @FXML
    private void SetJobDone() {
        if(this.user.getRole() == Role.EMPLOYEE) {
            doneBtn.setOnMouseClicked(event -> {
                new CanTakeJobs(user).JobDone(jobId);
                Refresh();
                addDataToTextField();
            });
        }
        else
        {
            doneBtn.setText("Delete Job");
            doneBtn.setOnMouseClicked(event -> {
                try {
                    new CanAdvertiseJobs(user).DeleteJob(jobId);
                    Refresh();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    private void Refresh() {
        profileTable.createTable(myJobsTable, profileTable.tableData, tableSection);
        descriptionTextField.setText("");
    }
}