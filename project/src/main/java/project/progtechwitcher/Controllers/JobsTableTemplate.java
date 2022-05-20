package project.progtechwitcher.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import project.progtechwitcher.Database.Database;
import project.progtechwitcher.Database.TypeForReadingJobs;
import project.progtechwitcher.Logging.Log;
import project.progtechwitcher.models.Jobs;
import project.progtechwitcher.models.user.UserBase;

import java.util.ArrayList;

public class JobsTableTemplate extends SuperTableTemplate {
    ArrayList<Object> tableData = new ArrayList<>();

    @Override
    protected void getTableData(TableView tableName, HBox container, UserBase userBase) {
        Database.jobs.clear();
        Database.GetJobs(0, Database.jobs, TypeForReadingJobs.ALL);

    }

    @Override
    @FXML
    protected void addTableData(ArrayList<Object> tableData, TableView tableName, UserBase user) {
        tableData = new ArrayList<>();
        for (Jobs jobs : Database.jobs) {
            if (jobs.getAcceptedBy() != 0) {
                tableName.getItems().add(jobs);
                System.out.println(jobs.toString());
            }
        }
    }

    protected void addColToTable(TableView tableName) {
        try{
            TableColumn<String, Jobs> column = new TableColumn<>("Required level");
            column.setCellValueFactory(new PropertyValueFactory<>("requiredLevel"));
            column.prefWidthProperty().bind(tableName.widthProperty().multiply(0.3));
            column.setResizable(false);
            tableName.getColumns().add(column);

            for (Jobs jobs : Database.jobs) {
                if (jobs.getAcceptedBy() != 0) {
                    tableName.getItems().add(jobs.getRequiredLevel());
                }
            }
        } catch (Exception e) {
            Log.Warning(JobsTableTemplate.class, "There was a problem with the table generation.");
        }
    }
}
