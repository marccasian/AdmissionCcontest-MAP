package GUI;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import controller.Controller;


public class Main extends Application {


    @Override
    public void start(Stage stage) {
        Controller service=new Controller();
        CandidatView candView=new CandidatView(service);
        Parent root=candView.getView();
        Scene scene = new Scene(root, 550, 500);
        stage.setTitle("Admission Contest");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}