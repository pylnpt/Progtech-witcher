package project.progtechwitcher.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import project.progtechwitcher.Database.Database;
import project.progtechwitcher.Hash.MD5Hash;
import project.progtechwitcher.MainController;
import project.progtechwitcher.models.Jobs;
import project.progtechwitcher.models.user.CanAdvertiseJobs;
import project.progtechwitcher.models.user.CanTakeJobs;
import project.progtechwitcher.models.user.Role;
import project.progtechwitcher.models.user.UserBase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

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

    private UserBase user;
    private int jobId=0;
    private int reward=0;


    @FXML
    private void initialize()
    {
        Database.GetUsers(MainController.userId);
        user =Database.users.get(0);
        passwordOff();
        changePasswordBtn.setOnMouseClicked(event->{
            passwordOn();
        });
        cancelBtn.setOnMouseClicked(event -> passwordOff());
        ModPassword.setOnMouseClicked(event->ChangePasswd());

        generateTable();
        addDataToTextField();

        myJobsTable.setOnMouseClicked(e -> clickCell());
        SetJobDone();

    }

    private void passwordOff()
    {
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
    private void passwordOn()
    {
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

    private void addDataToTextField(){
        userNameInput.setText("");
        roleInput.setText("");
        levelInput.setText("");

        userNameInput.setText(user.getUsername());
        roleInput.setText(user.getRole().toString());
        levelInput.setText(Integer.toString(user.getLevel()));

    }

    private void generateTable(){

        ArrayList<Jobs> tableData;

        switch (user.getRole())
        {
            case ADMIN -> {
                tableData = new ArrayList<>();
                tableSection.managedProperty().bind(myJobsTable.visibleProperty());
                tableSection.setVisible(false);
                break;
            }
            case EMPLOYER -> {
                tableData = new ArrayList<>(user.advertisedJobs);
                addTableData(user.advertisedJobs);
                break;
            }
            case EMPLOYEE -> {
                tableData = new ArrayList<>();
                for(Jobs job : user.takenJobs)
                {
                    if(job.isDone() != true)
                    {
                        tableData.add(job);
                    }
                }
                addTableData(tableData);
                break;
            }
        }
    }
    @FXML
    public void addTableData(ArrayList<Jobs> tableData){
        TableColumn<String, Jobs> firstColumn = new TableColumn<>("Title");
        TableColumn<Integer, Jobs> secondColumn = new TableColumn<>("Reward");
        TableColumn<Integer, Jobs> thirdColumn = new TableColumn<>("Required level");

        firstColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        secondColumn.setCellValueFactory(new PropertyValueFactory<>("reward"));
        thirdColumn.setCellValueFactory(new PropertyValueFactory<>("requiredLevel"));

        firstColumn.prefWidthProperty().bind(myJobsTable.widthProperty().multiply(0.5));
        secondColumn.prefWidthProperty().bind(myJobsTable.widthProperty().multiply(0.2));
        thirdColumn.prefWidthProperty().bind(myJobsTable.widthProperty().multiply(0.3));

        firstColumn.setResizable(false);
        secondColumn.setResizable(false);
        thirdColumn.setResizable(false);

        myJobsTable.getColumns().add(firstColumn);
        myJobsTable.getColumns().add(secondColumn);
        myJobsTable.getColumns().add(thirdColumn);

        for (Jobs jobs : tableData){
            if(user.getRole() == Role.EMPLOYEE && jobs.isDone() == false)
            {
                myJobsTable.getItems().add(jobs);
            }
            else {
                if(jobs.getAcceptedBy()==0) {
                    myJobsTable.getItems().add(jobs);
                }
            }
        }
    }

    @FXML
    public void clickCell()
    {
        try {
            descriptionTextField.setText("");
            String splitDescription = myJobsTable.getSelectionModel().getSelectedItem().toString().split(",")[2];
            String description = splitDescription.split("'")[1];

            String idSplit = myJobsTable.getSelectionModel().getSelectedItem().toString().split(",")[0];
            jobId = Integer.parseInt(idSplit.split("=")[1]);
            descriptionTextField.setText(description);

            String rewSplit = myJobsTable.getSelectionModel().getSelectedItem().toString().split(",")[3];
            reward = Integer.parseInt(rewSplit.split("=")[1]);
        }
        catch(Exception e)
        {

        }
    }

    private void ChangePasswd()
    {
        if(!(currentPassword.getText()=="" || NewPassword.getText()=="" || NewPassword2.getText()==""))
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
    private void SetJobDone(){
        if(user.getRole() == Role.EMPLOYEE) {
            doneBtn.setOnMouseClicked(event -> {
                new CanTakeJobs(user).JobDone(jobId, reward+user.getLevel());
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
    private void Refresh()
    {
        Database.GetUsers(user.getId());
        user = Database.users.get(0);
        myJobsTable.getItems().clear();
        generateTable();
        descriptionTextField.setText("");
    }

}
