package GUI;

import domain.ValidatorException;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import domain.Candidat;
import domain.Sectie;
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

import controller.Controller;
import controller.ControllerSectie;
import utils.Observable;
import utils.Observer;


public class SectiiController implements Observer<Sectie> {
    private ObservableList<Sectie> model;
    
    ControllerSectie service;
    @FXML
    private TableView<Sectie> sectTable;
    @FXML
    private TableColumn<Sectie, String> nameColumn;
    @FXML
    private TableColumn<Sectie, String> nrLocColumn;

    
	public SectiiController(){
      
    }
	
	public void setService(ControllerSectie sectieService) {
        this.service=sectieService;
        this.model= FXCollections.observableArrayList((Collection<? extends Sectie>)sectieService.getSectii());
        sectTable.setItems(model);
    }
	
	 /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	nameColumn.setCellValueFactory(new PropertyValueFactory<Sectie, String>("nume"));
    	nrLocColumn.setCellValueFactory(new PropertyValueFactory<Sectie, String>("nrLoc"));
    }

    @SuppressWarnings("unchecked")
	@Override
    public void update(Observable<Sectie> observable) {
            ControllerSectie s=(ControllerSectie)observable;
            model.setAll((List)s.getSectii());
    }

    public void handleUpdateSectie(){
    	try {
    		Sectie s= sectTable.getSelectionModel().getSelectedItem();
        	if (null != s)
        		showSectieEditDialog(s);
        	else showErrorMessage("Nu ati selectat nici o sectie");
        	
        } catch (NumberFormatException e) {
        	showErrorMessage("ID-ul si Numarul de locuri trebuie sa fie numere naturale!");
        }
    }

    public void handleAddSectie(ActionEvent e)
    {
    	showSectieSaveDialog();
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

    public void handleDeleteSectie(ActionEvent actionEvent) {
    	try{
			Sectie s =sectTable.getSelectionModel().getSelectedItem();
			if (null != s){        	
				Sectie stearsa = service.deleteS(s);
				if (stearsa != null) { 
					showMessage(Alert.AlertType.INFORMATION, "Sectie stearsa!", "Sectia "+stearsa.getNume()+" a fost stearsa cu succes!");
				}
				else{
					showErrorMessage("Stergerea Sectiei a esuat!");
				}
			}
			else{
				showErrorMessage("Nu ati selectat nici o sectie!");
			}
		} catch (ValidatorException | sun.security.validator.ValidatorException e1) {
            showErrorMessage(e1.getMessage());
        }
    }
    
    public void save(ActionEvent e){
    	service.saveRepo();
    }
    
    public void showSectieEditDialog(Sectie s) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SectiiController.class.getResource("EditSectieView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Sectie");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditSectieController editCandidatController= loader.getController();
            editCandidatController.setService(service, dialogStage, s);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showSectieSaveDialog() {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SectiiController.class.getResource("AddSectieView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Sectie");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            AddSectieController addSectieController= loader.getController();
            addSectieController.setService(service, dialogStage);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
