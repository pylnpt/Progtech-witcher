package project.progtechwitcher.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import project.progtechwitcher.Database.Database;
import project.progtechwitcher.MainController;
import project.progtechwitcher.models.Jobs;
import project.progtechwitcher.models.user.Role;
import project.progtechwitcher.models.user.UserBase;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
    private void initialize()
    {
        if(MainController.userId != 0){
            Database.GetUsers(MainController.userId);
        }

        generateTable();
        addDataToTextField();

        myJobsTable.setOnMouseClicked(e -> clickCell());
        asdBtn.setOnMouseClicked(e-> clearTable());

    }
    private void clearTable(){
        myJobsTable.getItems().clear();
    }
    private void addDataToTextField(){
        for(UserBase user: Database.users)
        {
            userNameInput.setText(user.getUsername());
            roleInput.setText(user.getRole().toString());
            levelInput.setText(Integer.toString(user.getLevel()));
        }
    }

    private void generateTable(){
        UserBase employer = Database.users.get(0);

        ArrayList<Jobs> tableData;

        switch (employer.getRole())
        {
            case ADMIN -> {
                tableData = new ArrayList<>();
                tableSection.managedProperty().bind(myJobsTable.visibleProperty());
                tableSection.setVisible(false);
                break;
            }
            case EMPLOYER -> {
                tableData = new ArrayList<>(employer.advertisedJobs);
                addTableData(employer.advertisedJobs);
                break;
            }
            case EMPLOYEE -> {
                tableData = new ArrayList<>(employer.takenJobs);
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
            if(Database.users.get(0).getRole() == Role.EMPLOYEE && jobs.isDone() == false)
            {
                myJobsTable.getItems().add(jobs);
            }
            else {
                myJobsTable.getItems().add(jobs);
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

            descriptionTextField.setText(description);
        }
        catch(Exception e)
        {

        }
    }


}
