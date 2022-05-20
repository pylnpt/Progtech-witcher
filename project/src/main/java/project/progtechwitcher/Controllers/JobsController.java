package project.progtechwitcher.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import project.progtechwitcher.Database.Database;
import project.progtechwitcher.Database.TypeForReadingJobs;
import project.progtechwitcher.MainController;
import project.progtechwitcher.models.user.Admin;
import project.progtechwitcher.models.user.CanTakeJobs;
import project.progtechwitcher.models.user.Employee;
import project.progtechwitcher.models.user.Role;


public class JobsController {
    @FXML
    private TableView jobsTable;
    @FXML
    TextArea descriptionArea;
    @FXML
    HBox container;
    @FXML
    private Button ApplyDeleteButton;
    @FXML
    private TextField JobNameTf;
    @FXML
    private TextField RewardTf;
    @FXML
    private TextField ReqLevelTf;
    private int jobId = 0;
    private int level;
    JobsTableTemplate jobsTableTemplate = new JobsTableTemplate();

    @FXML
    private void initialize() {
        jobsTableTemplate.tableData.clear();
        setBtn();
        jobsTableTemplate.createTable(jobsTable, jobsTableTemplate.tableData, container);
        jobsTable.setOnMouseClicked(e -> clickCell());

    }
    private void setBtn()
    {
        if(MainController.user.getRole()== Role.EMPLOYEE)
        {

        }
        else
        {
            ApplyDeleteButton.setText("Delete Job");
        }
    }
    public void clickCell() {
        try {
            descriptionArea.setText("");
            String splitDescription = jobsTable.getSelectionModel().getSelectedItem().toString().split(",")[2];
            String description = splitDescription.split("'")[1];

            String splitname = jobsTable.getSelectionModel().getSelectedItem().toString().split(",")[1];
            String name = splitname.split("'")[1];
            JobNameTf.setText(name);

            String idSplit = jobsTable.getSelectionModel().getSelectedItem().toString().split(",")[0];
            jobId = Integer.parseInt(idSplit.split("=")[1]);
            descriptionArea.setText(description);

            String rewSplit = jobsTable.getSelectionModel().getSelectedItem().toString().split(",")[3];
            int reward = Integer.parseInt(rewSplit.split("=")[1]);
            RewardTf.setText(Integer.toString(reward));

            String levelSplit = jobsTable.getSelectionModel().getSelectedItem().toString().split(",")[4];
            level = Integer.parseInt(levelSplit.split("=")[1]);
            ReqLevelTf.setText(Integer.toString(level));
        }
        catch(Exception e)
        {

        }
    }
    private void buttonClick(Role role)
    {
        switch (role) {
            case ADMIN -> {
                Refresh();
            }
            case EMPLOYEE -> {
                Refresh();
            }
        }
    }
    private void Refresh() {
        jobsTableTemplate.createTable(jobsTable, jobsTableTemplate.tableData, container);

        jobsTable.getItems().clear();
        descriptionArea.setText("");
    }
}
