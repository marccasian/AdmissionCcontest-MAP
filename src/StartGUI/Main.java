package StartGUI;

import java.io.IOException;

import GUI.CandidatController;
import GUI.InscrieriController;
import GUI.RootController;
import GUI.SectiiController;
import controller.ControllerCandidat;
import controller.ControllerInscrieri;
import controller.ControllerSectie;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import StartGUI.Main;



public class Main extends Application{

	BorderPane rootLayout;
	AnchorPane centerLayout;
	Stage primaryStage;
	
	ControllerCandidat candidatService = new ControllerCandidat();
	ControllerSectie sectieService = new ControllerSectie();
	ControllerInscrieri inscriereService = new ControllerInscrieri();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		this.primaryStage=primaryStage;
        this.primaryStage.setTitle("Admission Contest");

        initRootView();
        //initStartPageView();

	}
	
	public void initRootView() {
        try {
            //Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Root.fxml"));
            rootLayout = (BorderPane) loader.load();
            RootController rootController=loader.getController();
            rootController.setMainApplic(this);
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout,660,900);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void initStartPageView() {
        try {
            // Load student view.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/StartPage.fxml"));
            centerLayout = (AnchorPane) loader.load();
            rootLayout.setCenter(centerLayout);
            //set the service and the model for controller class
            RootController rootCtrl=loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void initCandidatViewLayout() {
        try {
            // Load student view.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/CandidatiView.fxml"));
            centerLayout = (AnchorPane) loader.load();
            rootLayout.setCenter(centerLayout);
            //set the service and the model for controller class
            CandidatController viewCtrl=loader.getController();
            viewCtrl.setService(candidatService);
            candidatService.addObserver(viewCtrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void initSectiiViewLayout() {
        try {
            // Load student view.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/SectiiView.fxml"));
            centerLayout = (AnchorPane) loader.load();
            rootLayout.setCenter(centerLayout);
            //set the service and the model for controller class
            SectiiController viewCtrl=loader.getController();
            viewCtrl.setService(sectieService);
            sectieService.addObserver(viewCtrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void initInscrieriViewLayout() {
        try {
            // Load student view.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/InscrieriView.fxml"));
            centerLayout = (AnchorPane) loader.load();
            rootLayout.setCenter(centerLayout);
            //set the service and the model for controller class
            InscrieriController viewCtrl=loader.getController();
            viewCtrl.setService(inscriereService);
            inscriereService.addObserver(viewCtrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void save(){
		sectieService.saveRepo();
		candidatService.saveRepo();
	}
	
	public void close(){
		
	}
	
	
}
