package project.progtechwitcher.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import project.progtechwitcher.Database.Database;
import project.progtechwitcher.models.user.UserBase;

public class UsersController {
    @FXML
    private Button adminButton;
    @FXML
    private ListView  userList;
    @FXML
    private ListView  detailList;
    private ObservableList<String> usernames = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
//        Database.GetUsers(0);
        addUserToList();

    }

    private void addUserToList()
    {
//        list_todo.setItems(items);
        userList.setItems(usernames);

        Database.GetUsers(0);
        for(UserBase user:Database.users)
        {
            usernames.add(user.getUsername());
        }
    }
//        Database.GetUsers(0);



//    private void addDetailToList(){
//        Database.GetUsers(0);
//        for(UserBase user:Database.users)
//        {
//            usernames.add(user.getUsername());
//        }
//    }
}
