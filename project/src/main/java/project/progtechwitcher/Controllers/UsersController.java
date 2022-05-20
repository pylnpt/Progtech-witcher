package project.progtechwitcher.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import project.progtechwitcher.Database.Database;
import project.progtechwitcher.MainController;
import project.progtechwitcher.models.user.Admin;
import project.progtechwitcher.models.user.Role;
import project.progtechwitcher.models.user.RoleConverter;
import project.progtechwitcher.models.user.UserBase;

public class UsersController {
    @FXML
    private Button adminBtn;
    @FXML
    private ListView  userList;
    @FXML
    private TextField roleTf;
    @FXML
    private TextField jobTf;
    @FXML
    private TextField levelTf;
    @FXML
    private TextField unameTf;
    private Admin a;
    private ObservableList<String> usernames = FXCollections.observableArrayList();
    private UserBase u;
    @FXML
    private void initialize(){
        Database.GetUsers(MainController.userId);
        a = (Admin)Database.users.get(0);
        addUserToList();
        Database.GetUsers(0);
        userList.setOnMouseClicked(e -> clickCell());
    }
    @FXML
    public void clickCell()
    {
        try {
            UserBase u = Database.users.get(userList.getSelectionModel().getSelectedIndex());
            unameTf.setText(userList.getSelectionModel().getSelectedItem().toString());
            roleTf.setText(RoleConverter.RoleToString(u.getRole()));
            levelTf.setText(Integer.toString(u.getLevel()));
            int count=0;
            if(u.getRole()== Role.EMPLOYEE)
            {
                count = u.takenJobs.size();
            }
            else
            {
                count = u.advertisedJobs.size();
            }
            jobTf.setText(Integer.toString(count));
            adminBtn.setOnMouseClicked(event -> DeleteUser(u.getId()));
        }
        catch(Exception e)
        {

        }
    }


    private void addUserToList()
    {
        userList.setItems(usernames);

        Database.GetUsers(0);
        for(UserBase user:Database.users)
        {
            usernames.add(user.getUsername());
        }
    }

    private void SetDetailsEmpty()
    {
        unameTf.setText("");
        roleTf.setText("");
        levelTf.setText("");
        jobTf.setText("");
    }
    private void DeleteUser(int userId)
    {
        if(userId!=0)
        {
            if(userId==a.getId())
            {
                adminBtn.setDisable(true);
            }
            else
            {
                adminBtn.setDisable(false);
            }
            a.DeleteUser(userId);
            System.out.println("Deleted user: "+userId);
            userList.getItems().clear();
            SetDetailsEmpty();
            addUserToList();
        }
    }
}
