package project.progtechwitcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.progtechwitcher.Database.Database;
import project.progtechwitcher.Database.TypeForReadingJobs;
import project.progtechwitcher.Hash.MD5Hash;
import project.progtechwitcher.models.Jobs;
import project.progtechwitcher.models.user.UserBase;

import java.io.IOException;
import java.util.Objects;


public class Main extends Application {
    double x,y = 0;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/progtechwitcher/fxml/home-page-view.fxml")));

        Scene scene = new Scene(root);

        // stage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() -x );
            stage.setY(event.getScreenY() - y);
        });


        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        Database.GetJobs(0, Database.jobs, TypeForReadingJobs.ALL);
        for(Jobs x: Database.jobs)
        {
            System.out.println(x.toString());
        }
        Database.GetUsers(0);
        for(UserBase x: Database.users)
        {
            System.out.println(x.toString());
        }
        System.out.println(MD5Hash.getMd5("123"));
        launch();
    }
}