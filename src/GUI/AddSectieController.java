package GUI;

import domain.Sectie;
import domain.ValidatorException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import controller.ControllerSectie;

@SuppressWarnings("unused")
public class AddSectieController {

    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldNrLoc;
    	
    private ControllerSectie service;
    Stage dialogStage;
    Sectie sectie;

    @FXML
    private void initialize() {
    }

    public void setService(ControllerSectie service,  Stage stage) {
        this.service = service;
        this.dialogStage=stage;
        //textFieldId.setEditable(false);
        //textFieldId.setAccessibleText("Se va genera automat");
    }
    
    public Sectie extractSectie(){
        Integer id=service.getNewID();
        String nume=textFieldName.getText();
        String nrLoc=textFieldNrLoc.getText();
        Sectie c=new Sectie(id,nume, Integer.parseInt(nrLoc));
        return c;
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
        	Sectie s = extractSectie();
        	Sectie  saved = service.saveS(s);
            if (saved==null) {
                showMessage(Alert.AlertType.INFORMATION, "Salvare cu succes", "Sectia a fost adaugata!");
                clearFields();
            }
            else
                showErrorMessage("Exista deja o sectie cu acest id!");
        } catch (ValidatorException | sun.security.validator.ValidatorException e1) {
            showErrorMessage(e1.getMessage());
        } catch (NumberFormatException e) {
        	showErrorMessage("Numarul de locuri trebuie sa fie numer natural!");
        }
        catch (Exception e) {
        	showErrorMessage(e.getMessage());
        }
    }

    private void clearFields() {
    	textFieldName.setText("");
        textFieldNrLoc.setText("");
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}
