package GUI;

import domain.Candidat;
import domain.Inscriere;
import domain.Sectie;
import domain.ValidatorException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Collection;

import controller.ControllerCandidat;
import controller.ControllerInscrieri;
import controller.ControllerSectie;

public class AddInscriereController {

	private ObservableList<Candidat> modelC;
    ControllerCandidat serviceC;
	@FXML
    private TableView<Candidat> candidatiTable;
    @FXML
    private TableColumn<Candidat, String> numecColumn;
    @FXML
    private TableColumn<Candidat, String> telcColumn;

    private ObservableList<Sectie> modelS;
    ControllerSectie serviceS;
    @FXML
    private TableView<Sectie> sectiiTable;
    @FXML
    private TableColumn<Sectie, String> numesColumn;
    @FXML
    private TableColumn<Sectie, String> nrLocsColumn;

    @FXML
    private TextField textFieldId;    
    	
    private ControllerInscrieri service;
    Stage dialogStage;
    Inscriere candidat;

    @FXML
    private void initialize() {
    	numesColumn.setCellValueFactory(new PropertyValueFactory<Sectie, String>("nume"));
    	nrLocsColumn.setCellValueFactory(new PropertyValueFactory<Sectie, String>("nrLoc"));
    	numecColumn.setCellValueFactory(new PropertyValueFactory<Candidat, String>("nume"));
    	telcColumn.setCellValueFactory(new PropertyValueFactory<Candidat, String>("tel"));
    }

    public void setService(ControllerInscrieri service2, ControllerCandidat servic, ControllerSectie servis,  Stage stage) {
        this.service = service2;
        this.serviceC = servic;
        this.serviceS = servis;
        this.dialogStage=stage;
        this.modelC= FXCollections.observableArrayList((Collection<? extends Candidat>)serviceC.getCandidati());
        candidatiTable.setItems(modelC);
        this.modelS= FXCollections.observableArrayList((Collection<? extends Sectie>)serviceS.getSectii());
        sectiiTable.setItems(modelS);
    }
    
    public Inscriere extractInscriere(){
    	Candidat c= candidatiTable.getSelectionModel().getSelectedItem();
    	Sectie s= sectiiTable.getSelectionModel().getSelectedItem();
    	String id=textFieldId.getText();
    	Integer nr = 0;
    	try{
    		nr = Integer.parseInt(id);
    		return new Inscriere(nr,c,s);
    	} catch (NumberFormatException e) {
        	showErrorMessage("ID-ul trebuie sa fie numar natural!");
        }
    	return null;
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


    @FXML
    public void handleSave(){
        try {
        	Inscriere c= extractInscriere();
        	if (null != c){
				Inscriere  saved=service.saveI(c);
			    if (saved==null) {
			        showMessage(Alert.AlertType.INFORMATION, "Salvare cu succes", "Inscrierea a fost adaugata!");
			        clearFields();
			    }
			    else
			        showErrorMessage("Exista deja o inscriere cu acest id!");
            }
        } catch (ValidatorException | sun.security.validator.ValidatorException e1) {
            showErrorMessage(e1.getMessage());
        } catch (NumberFormatException e) {
        	showErrorMessage("ID trebuie sa fie numer natural!");
        }
        catch (Exception e) {
        	showErrorMessage(e.getMessage());
        }
    }
    
    private void clearFields() {
        textFieldId.setText("");
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}
