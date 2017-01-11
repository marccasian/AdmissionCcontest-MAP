package GUI;

import domain.ValidatorException;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import domain.Inscriere;
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
public class InscrieriController implements Observer<Inscriere> {
    private ObservableList<Inscriere> model;

    ControllerInscrieri service;
    ControllerSectie serviceS;
    ControllerCandidat serviceC;
    
    @FXML
    private TableView<Inscriere> inscrieriTable;
    @FXML
    private TableColumn<Inscriere, String> candidatColumn;
    @FXML
    private TableColumn<Inscriere, String> sectieColumn;

    
	public InscrieriController(){
      
    }
	//
	public void setService(ControllerInscrieri inscriereService, ControllerCandidat servic, ControllerSectie servis) {
        this.service=inscriereService;
        this.serviceS=servis;
        this.serviceC=servic;
        this.model= FXCollections.observableArrayList((Collection<? extends Inscriere>)inscriereService.getInscrieri());
        inscrieriTable.setItems(model);
       
    }
	
	 /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	candidatColumn.setCellValueFactory(new PropertyValueFactory<>("numec"));
    	sectieColumn.setCellValueFactory(new PropertyValueFactory<>("numes"));//merge fara, defapt nu merge :)))
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void update(Observable<Inscriere> observable) {
		ControllerInscrieri s=(ControllerInscrieri)observable;
            model.setAll((List)s.getInscrieri());
    }
    
    public void handleUpdateInscriere(){
        try {
        	Inscriere c= inscrieriTable.getSelectionModel().getSelectedItem();
        	if (null != c)
        		showInscriereEditDialog(c);
        	else showErrorMessage("Nu ati selectat nici un candidat!");
        	
        } catch (NumberFormatException e) {
        	showErrorMessage("ID si Varsta trebuie sa fie numere naturale!");
        }
    }

    public void handleAddInscriere()
    {
    	showInscriereSaveDialog();
    }
    
    public void handleTop3()
    {
    	showTop3Dialog();
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

    public void handleDeleteInscriere(ActionEvent actionEvent) {		
		try{
			Inscriere c =inscrieriTable.getSelectionModel().getSelectedItem();
			if (null != c){        	
				Inscriere sters = service.deleteI(c);
				if (sters != null) { 
					showMessage(Alert.AlertType.INFORMATION, "Inscriere stearsa!", "Inscriereul "+sters.toString()+" a fost stearsa cu succes!");
				}
				else{
					showErrorMessage("Stergerea inscrierii a esuat!");
				}
			}
			else{
				showErrorMessage("Nu ati selectat nici o inscriere!");
			}
					
		} catch (ValidatorException | sun.security.validator.ValidatorException e1) {
            showErrorMessage(e1.getMessage());
        }
    }
    
    public void save(ActionEvent e){
    	service.saveRepo();
    }
    
    public void showInscriereSaveDialog() {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(InscrieriController.class.getResource("AddInscriereView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Inscriere");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            AddInscriereController addInscriereController= loader.getController();
            addInscriereController.setService(service,serviceC,serviceS,dialogStage);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
    
    public void showTop3Dialog() {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(InscrieriController.class.getResource("Top3View.fxml"));
            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Top 3 sectii");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            RaportController raportController= loader.getController();
            raportController.setService(service,serviceC,serviceS,dialogStage);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
    
    public void showInscriereEditDialog(Inscriere c) {
//        try {
//            // create a new stage for the popup dialog.
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(InscrieriController.class.getResource("EditInscriereView.fxml"));
//            AnchorPane root = (AnchorPane) loader.load();
//
//            // Create the dialog Stage.
//            Stage dialogStage = new Stage();
//            dialogStage.setTitle("Edit Inscrieree");
//            dialogStage.initModality(Modality.WINDOW_MODAL);
//            //dialogStage.initOwner(primaryStage);
//            Scene scene = new Scene(root);
//            dialogStage.setScene(scene);
//
//            EditInscriereController editInscriereController= loader.getController();
//            editInscriereController.setService(service, dialogStage, c);
//
//            dialogStage.show();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
