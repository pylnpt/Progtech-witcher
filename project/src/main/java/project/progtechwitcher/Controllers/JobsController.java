package project.progtechwitcher.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import project.progtechwitcher.Database.Database;
import project.progtechwitcher.Database.TypeForReadingJobs;
import project.progtechwitcher.models.Jobs;
import project.progtechwitcher.models.user.Role;

import java.util.ArrayList;

public class JobsController {
    @FXML
    private TableView takeAJobTable;

    @FXML
    private void initialize(){
        Database.GetJobs(0, Database.jobs, TypeForReadingJobs.ALL);

    }

    public void addTableData(ArrayList<Jobs> tableData){
        TableColumn<String, Jobs> firstColumn = new TableColumn<>("Title");
        TableColumn<Integer, Jobs> secondColumn = new TableColumn<>("Reward");
        TableColumn<Integer, Jobs> thirdColumn = new TableColumn<>("Required level");
        TableColumn<Integer, Jobs> fourthColumn = new TableColumn<>("Description");
        TableColumn<Integer, Jobs> fifthColumn = new TableColumn<>("Take the job");

        firstColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        secondColumn.setCellValueFactory(new PropertyValueFactory<>("reward"));
        thirdColumn.setCellValueFactory(new PropertyValueFactory<>("requiredLevel"));
        fourthColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        fifthColumn.setCellValueFactory(new PropertyValueFactory<>("button"));

        firstColumn.prefWidthProperty().bind(takeAJobTable.widthProperty().multiply(0.2));
        secondColumn.prefWidthProperty().bind(takeAJobTable.widthProperty().multiply(0.2));
        thirdColumn.prefWidthProperty().bind(takeAJobTable.widthProperty().multiply(0.2));
        fourthColumn.prefWidthProperty().bind(takeAJobTable.widthProperty().multiply(0.2));
        fifthColumn.prefWidthProperty().bind(takeAJobTable.widthProperty().multiply(0.2));

        firstColumn.setResizable(false);
        secondColumn.setResizable(false);
        thirdColumn.setResizable(false);
        fourthColumn.setResizable(false);
        fifthColumn.setResizable(false);

        takeAJobTable.getColumns().add(firstColumn);
        takeAJobTable.getColumns().add(secondColumn);
        takeAJobTable.getColumns().add(thirdColumn);

        for (Jobs jobs : tableData){
            if(Database.users.get(0).getRole() == Role.EMPLOYEE && jobs.isDone() == false)
            {
                takeAJobTable.getItems().add(jobs);
            }
            else {
                takeAJobTable.getItems().add(jobs);
            }
        }
        System.out.println(takeAJobTable.getSelectionModel().getSelectedItem());
    }


}
