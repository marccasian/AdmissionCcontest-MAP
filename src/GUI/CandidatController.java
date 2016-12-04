package GUI;

import domain.ValidatorException;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import domain.Candidat;
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
import utils.Observable;
import utils.Observer;

public class CandidatController implements Observer<Candidat> {
    private ObservableList<Candidat> model;

    ControllerCandidat service;
    @FXML
    private TableView<Candidat> candTable;
    @FXML
    private TableColumn<Candidat, String> nameColumn;
    @FXML
    private TableColumn<Candidat, String> telColumn;

    
	public CandidatController(){
      
    }
	
	public void setService(ControllerCandidat candidatService) {
        this.service=candidatService;
        this.model= FXCollections.observableArrayList((Collection<? extends Candidat>)candidatService.getCandidati());
        candTable.setItems(model);
    }
	
	 /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	nameColumn.setCellValueFactory(new PropertyValueFactory<Candidat, String>("nume"));
    	telColumn.setCellValueFactory(new PropertyValueFactory<Candidat, String>("tel"));
    }

	@SuppressWarnings("unchecked")
	@Override
    public void update(Observable<Candidat> observable) {
            ControllerCandidat s=(ControllerCandidat)observable;
            model.setAll((List)s.getCandidati());
    }
    
    public void handleUpdateCandidat(){
        try {
        	Candidat c= candTable.getSelectionModel().getSelectedItem();
        	if (null != c)
        		showCandidatEditDialog(c);
        	else showErrorMessage("Nu ati selectat nici un candidat!");
        	
        } catch (NumberFormatException e) {
        	showErrorMessage("ID si Varsta trebuie sa fie numere naturale!");
        }
    }

    public void handleAddCandidat()
    {
    	showCandidatSaveDialog();
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

    public void handleDeleteCandidat(ActionEvent actionEvent) {		
		try{
			Candidat c =candTable.getSelectionModel().getSelectedItem();
			if (null != c){        	
				Candidat sters = service.deleteC(c);
				if (sters != null) { 
					showMessage(Alert.AlertType.INFORMATION, "Candidat sters!", "Candidatul "+sters.getNume()+" a fost sters cu succes!");
				}
				else{
					showErrorMessage("Stergerea candidatului a esuat!");
				}
			}
			else{
				showErrorMessage("Nu ati selectat nici un candidat!");
			}
					
		} catch (ValidatorException | sun.security.validator.ValidatorException e1) {
            showErrorMessage(e1.getMessage());
        }
    }
    
    public void save(ActionEvent e){
    	service.saveRepo();
    }
    
    public void showCandidatSaveDialog() {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CandidatController.class.getResource("AddCandidatView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Candidate");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            AddCandidatController addCandidatController= loader.getController();
            addCandidatController.setService(service, dialogStage);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showCandidatEditDialog(Candidat c) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CandidatController.class.getResource("EditCandidatView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Candidate");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditCandidatController editCandidatController= loader.getController();
            editCandidatController.setService(service, dialogStage, c);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
