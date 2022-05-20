package project.progtechwitcher.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import project.progtechwitcher.Database.Database;
import project.progtechwitcher.Logging.Log;
import project.progtechwitcher.MainController;
import project.progtechwitcher.models.Jobs;
import project.progtechwitcher.models.user.UserBase;

import java.util.ArrayList;

public abstract class SuperTableTemplate {
    public void createTable(TableView tableName, ArrayList<Object> tableData, HBox container) {

        UserBase user = refreshUser(MainController.userId);// kötelező, nem közös
        tableName.getItems().clear(); // kötelező, közös
        tableData.clear(); // kötelező, közös

        getTableData(tableName, container, user); // kötelező, nem közös
        generateTableSkeleton(tableName);  // kötelező, közös
        addTableData(tableData,tableName, user); // kötelező, nem közös
        addColToTable(tableName); // kötelező, nem közös

    }
    protected  abstract void getTableData(TableView tableName, HBox container, UserBase userBase);
    @FXML
    private final void generateTableSkeleton(TableView tableName) {

        try {
            TableColumn<String, Jobs> firstColumn = new TableColumn<>("Title");
            TableColumn<Integer, Jobs> secondColumn = new TableColumn<>("Reward");

            firstColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            secondColumn.setCellValueFactory(new PropertyValueFactory<>("reward"));

            firstColumn.prefWidthProperty().bind(tableName.widthProperty().multiply(0.5));
            secondColumn.prefWidthProperty().bind(tableName.widthProperty().multiply(0.5));

            firstColumn.setResizable(false);
            secondColumn.setResizable(false);

            tableName.getColumns().addAll(firstColumn, secondColumn);
        } catch (Exception e ) {
            Log.Warning(SuperTableTemplate.class, "There was something wrong with the table generation.");
        }

    }
    protected abstract void addTableData(ArrayList<Object> tableData, TableView tableName, UserBase user);
    protected  abstract  void addColToTable(TableView tableName);
    protected  final UserBase refreshUser(int userId) {
        Database.GetUsers(userId);
        return Database.users.get(0);
    }

}
