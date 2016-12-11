package GUI;

import domain.Candidat;
import domain.ValidatorException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import controller.ControllerCandidat;

@SuppressWarnings("unused")
public class EditCandidatController {

    @FXML
    private TextField textFieldId;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldPhone;
    @FXML
    private TextField textFieldAdress;
    @FXML
    private TextField textFieldVarsta;
    	
    private ControllerCandidat service;
    Stage dialogStage;
    Candidat candidat;

    @FXML
    private void initialize() {
    }


    public void setService(ControllerCandidat service,  Stage stage, Candidat c) {
        this.service = service;
        this.dialogStage=stage;
        this.candidat=c;
        if (null != c) {
            setFields(c);
            textFieldId.setEditable(false);
        }
    }
    
    public Candidat extractCandidat(){
        String id=textFieldId.getText();
        String phone=textFieldPhone.getText();
        String name=textFieldName.getText();
        String adress=textFieldAdress.getText();
        String varsta=textFieldVarsta.getText();
        Candidat c=new Candidat(Integer.parseInt(id),name,phone,adress, Integer.parseInt(varsta));
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
        	Candidat c= extractCandidat();
        	Candidat  updated=service.updateC(c);
            if (updated==null) {
                showMessage(Alert.AlertType.INFORMATION, "Salvare cu succes", "Candidatul a fost adaugat!");
                dialogStage.close();
            }
            else
                showErrorMessage("Exista deja un candidat cu acest id!");
        } catch (ValidatorException | sun.security.validator.ValidatorException e1) {
            showErrorMessage(e1.getMessage());
        } catch (NumberFormatException e) {
        	showErrorMessage("ID si Varsta trebuie sa fie numere naturale!");
        }
        catch (Exception e) {
        	showErrorMessage(e.getMessage());
        }
    }

   
    private void setFields(Candidat c)
    {
        textFieldName.setText(c.getNume());
        textFieldPhone.setText(c.getTel());
        textFieldAdress.setText(c.getAdresa());
        textFieldVarsta.setText(c.getVarsta().toString());
        textFieldId.setText(c.getId().toString());
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}
