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
public class EditSectieController {

    @FXML
    private TextField textFieldId;
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


    public void setService(ControllerSectie service,  Stage stage, Sectie s) {
        this.service = service;
        this.dialogStage=stage;
        this.sectie=s;
        if (null != s) {
            setFields(s);
            textFieldId.setEditable(false);
        }
    }
    
    public Sectie extractSectie(){
        String id=textFieldId.getText();
        String nume=textFieldName.getText();
        String nrLoc=textFieldNrLoc.getText();
        Sectie c=new Sectie(Integer.parseInt(id),nume, Integer.parseInt(nrLoc));
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
        	Sectie  updated=service.updateS(s);
            if (updated==null) {
                showMessage(Alert.AlertType.INFORMATION, "Editare cu succes", "Sectia a fost editata!");
                dialogStage.close();
            }
            else
                showErrorMessage("Exista deja o sectie cu acest id!");
        } catch (ValidatorException | sun.security.validator.ValidatorException e1) {
            showErrorMessage(e1.getMessage());
        } catch (NumberFormatException e) {
        	showErrorMessage("ID-ul si numarul de locuri trebuie sa fie numere naturale!");
        }
        catch (Exception e) {
        	showErrorMessage(e.getMessage());
        }
    }

   
    private void setFields(Sectie s)
    {
        textFieldName.setText(s.getNume());
        textFieldNrLoc.setText(s.getNrLoc().toString());
        textFieldId.setText(s.getId().toString());
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}
