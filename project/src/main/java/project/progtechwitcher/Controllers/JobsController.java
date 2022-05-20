package project.progtechwitcher.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import project.progtechwitcher.Database.Database;
import project.progtechwitcher.Database.TypeForReadingJobs;


public class JobsController {
    @FXML
    private TableView jobsTable;
    @FXML
    TextArea descriptionField;
    @FXML
    HBox container;

    JobsTableTemplate jobsTableTemplate = new JobsTableTemplate();

    @FXML
    private void initialize() {
        jobsTableTemplate.tableData.clear();
        jobsTableTemplate.createTable(jobsTable, jobsTableTemplate.tableData, container);
    }
}
