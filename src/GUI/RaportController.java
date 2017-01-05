package GUI;

import domain.ValidatorException;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import domain.Inscriere;
import domain.RaportItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import controller.ControllerCandidat;
import controller.ControllerInscrieri;
import controller.ControllerSectie;
import utils.Observable;
import utils.Observer;

@SuppressWarnings("unused")
public class RaportController implements Observer<Inscriere> {
    private ObservableList<RaportItem> model;

    ControllerInscrieri service;
    ControllerSectie serviceS;
    ControllerCandidat serviceC;
    Stage dialogStage;
    
    @FXML
    private TableView<RaportItem> raportTable;
    @FXML
    private TableColumn<RaportItem, Integer> numarCandidatiColumn;
    @FXML
    private TableColumn<RaportItem, String> sectieColumn;

    
	public RaportController(){
      
    }
	//
	public void setService(ControllerInscrieri inscriereService, ControllerCandidat servic, ControllerSectie servis, Stage stage) {
        this.service=inscriereService;
        this.serviceS=servis;
        this.serviceC=servic;
        this.dialogStage = stage;
        this.model= FXCollections.observableArrayList((Collection<? extends RaportItem>)inscriereService.getRaport());
        raportTable.setItems(model);
    }
	
	 /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	sectieColumn.setCellValueFactory(new PropertyValueFactory<>("numeSectie"));
    	numarCandidatiColumn.setCellValueFactory(new PropertyValueFactory<>("nrLocOcupate"));
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void update(Observable<Inscriere> observable) {
		ControllerInscrieri s=(ControllerInscrieri)observable;
            model.setAll((List)s.getRaport());
    }

    static void showMessage(Alert.AlertType type, String header, String text){
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.showAndWait();
    }

    static void showErrorMessage(String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }
    
    public void save(ActionEvent e){
    	service.saveRepo();
    }
    
    @FXML
    public void handleCancel(){
        dialogStage.close();
    }    
}
