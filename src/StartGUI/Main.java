package StartGUI;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
	ApplicationContext context = new ClassPathXmlApplicationContext(Main.class.getResource("/AdmissionContest.xml").toString());;
	ControllerCandidat candidatService= (ControllerCandidat) context.getBean("candidatService"); 
	ControllerSectie sectieService = (ControllerSectie) context.getBean("sectieService"); 
	ControllerInscrieri inscriereService = (ControllerInscrieri) context.getBean("inscriereService");;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage=primaryStage;
        this.primaryStage.setTitle("Admission Contest");

        initRootView();
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
            Scene scene = new Scene(rootLayout,750,900);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
	
	@SuppressWarnings("unused")
	public void initStartPageView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/StartPage.fxml"));
            centerLayout = (AnchorPane) loader.load();
            rootLayout.setCenter(centerLayout);
            RootController rootCtrl=loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void initCandidatViewLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/CandidatiView2.fxml"));
            centerLayout = (AnchorPane) loader.load();
            rootLayout.setCenter(centerLayout);
            CandidatController viewCtrl=loader.getController();
            viewCtrl.setService(candidatService);
            candidatService.addObserver(viewCtrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void initAboutUsViewLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/AboutUsView.fxml"));
            centerLayout = (AnchorPane) loader.load();
            rootLayout.setCenter(centerLayout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void initSectiiViewLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/SectiiView.fxml"));
            centerLayout = (AnchorPane) loader.load();
            rootLayout.setCenter(centerLayout);
            SectiiController viewCtrl=loader.getController();
            viewCtrl.setService(sectieService);
            sectieService.addObserver(viewCtrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void initInscrieriViewLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/InscrieriView.fxml"));
            centerLayout = (AnchorPane) loader.load();
            rootLayout.setCenter(centerLayout);
            InscrieriController viewCtrl=loader.getController();
            viewCtrl.setService(inscriereService,candidatService,sectieService);
            inscriereService.addObserver(viewCtrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void save(){
		sectieService.saveRepo();
		candidatService.saveRepo();
		inscriereService.saveRepo();
	}
	
	public void close(){
		primaryStage.close();
	}
	
	
}
