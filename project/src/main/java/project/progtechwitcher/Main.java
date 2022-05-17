package project.progtechwitcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.progtechwitcher.Database.ConnectToDatabase;
import project.progtechwitcher.Logging.Log;
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
//        ConnectToDatabase.GetJobs(0, ConnectToDatabase.jobs, 'a');
//        ConnectToDatabase.GetUsers(0);
//
//
//
//        //kiíratnás
//        for(Jobs job:ConnectToDatabase.jobs)
//        {
//            System.out.println(job.toString());
//        }
//        for(UserBase x:ConnectToDatabase.users)
//        {
//            System.out.println(x.toString());
//        }

        launch();
    }
}