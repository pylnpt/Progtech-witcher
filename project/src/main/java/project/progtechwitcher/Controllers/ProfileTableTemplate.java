package project.progtechwitcher.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import project.progtechwitcher.models.Jobs;
import project.progtechwitcher.models.user.Role;
import project.progtechwitcher.models.user.UserBase;

import java.util.ArrayList;

public class ProfileTableTemplate extends SuperTableTemplate{
    ArrayList<Object> tableData = new ArrayList<>();
    @Override
    protected void getTableData(TableView tableName, HBox container, UserBase user) {
        tableData = new ArrayList<>();
        switch (user.getRole())
        {
            case ADMIN -> {
                tableData = new ArrayList<>();
                container.managedProperty().bind(tableName.visibleProperty());
                container.setVisible(false);
                break;
            }
            case EMPLOYER -> {
                tableData = new ArrayList<>(user.advertisedJobs);
                addTableData(tableData, tableName, user);
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
                addTableData(tableData, tableName, user);
                break;
            }
        }
    }
    @Override
    @FXML
    protected void addTableData(ArrayList<Object> tableData, TableView tableName, UserBase user) {
        ArrayList<Jobs> jobList = new ArrayList<>();
        for(Object object: tableData) {
            jobList.add((Jobs) object);
        }

        for (Jobs jobs : jobList){
            if(user.getRole() == Role.EMPLOYEE && jobs.isDone() == false)
            {
                tableName.getItems().add(jobs);
            }
            else {
                if(jobs.getAcceptedBy() == 0) {
                    tableName.getItems().add(jobs);
                }
            }
        }
    }
    @Override
    protected void addColToTable(TableView tableName) { }
}
