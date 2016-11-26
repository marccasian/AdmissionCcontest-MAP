package GUI;

import domain.ValidatorException;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import domain.Candidat;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.util.Collection;
import java.util.List;

import controller.Controller;
import utils.Observable;
import utils.Observer;


public class CandidatViewController implements Observer<Candidat> {
    private ObservableList<Candidat> model;
    private CandidatView view;
    Controller service;

    
	public CandidatViewController(Controller service, CandidatView view){
        this.view=view;
        this.model= FXCollections.observableArrayList((Collection<? extends Candidat>)service.getCandidati());
        view.candTable.setItems(model);
        this.service=service;

    }

    public ChangeListener<Candidat> changedTableItemListener() {
        ChangeListener<Candidat> changeListener = (observable, oldvalue, newValue) -> {
            showCandidatDetails(newValue);
            view.textFieldId.setEditable(false);

        };

        return changeListener;
    }

    public void showCandidatDetails(Candidat value)
    {
        if (value==null)
        {
            view.textFieldName.setText("");
            view.textFieldPhone.setText("");
            view.textFieldAdress.setText("");
            view.textFieldId.setText("");
            view.textFieldVarsta.setText("");

        }
        else
        {
        	view.textFieldId.setText(value.getId().toString());
        	view.textFieldName.setText(value.getNume());
        	view.textFieldVarsta.setText(value.getVarsta().toString());
            view.textFieldPhone.setText(value.getTel());
            view.textFieldAdress.setText(value.getAdresa());            
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void update(Observable<Candidat> observable) {
            Controller s=(Controller)observable;
            model.setAll((List)s.getCandidati());
    }
    
    public Candidat extractCandidat(){
        String id=view.textFieldId.getText();
        String phone=view.textFieldPhone.getText();
        String name=view.textFieldName.getText();
        String adress=view.textFieldAdress.getText();
        String varsta=view.textFieldVarsta.getText();
        Candidat s=new Candidat(Integer.parseInt(id),name,phone,adress, Integer.parseInt(varsta));
        return s;
    }

    public void handleUpdateCandidat(ActionEvent e){
        Candidat c= extractCandidat();
        try {
            Candidat  updated=service.updateC(c);

            if (updated==null) {
                showMessage(Alert.AlertType.INFORMATION, "Actualizare cu succes", "Candidatul a fost actualizat!");
                showCandidatDetails(extractCandidat());
            }
            else
                showErrorMessage("Exista deja un candidat cu acest id!");
        } catch (ValidatorException | sun.security.validator.ValidatorException e1) {
            showErrorMessage(e1.getMessage());
        }
    }

    public void handleAddCandidat(ActionEvent e)
    {

        Candidat c= extractCandidat();
        try {
            Candidat  saved=service.saveC(c);
            if (saved==null) {
                showMessage(Alert.AlertType.INFORMATION, "Salvare cu succes", "Candidatul a fost adaugat!");
                showCandidatDetails(null);
            }
            else
                showErrorMessage("Exista deja un candidat cu acest id!");
        } catch (ValidatorException | sun.security.validator.ValidatorException e1) {
            showErrorMessage(e1.getMessage());
        }
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

    public void handleClearFields(ActionEvent actionEvent) {
        showCandidatDetails(null);
        view.textFieldId.setEditable(true);
    }


    public void handleDeleteCandidat(ActionEvent actionEvent) {
		Candidat s=extractCandidat();
		try{
			Candidat sters = service.deleteC(s);
			if (sters != null) { 
				showMessage(Alert.AlertType.INFORMATION, "Candidat sters!", "Candidatul "+sters.getNume()+" a fost sters cu succes!");
                showCandidatDetails(null);
			}
			else{
				showErrorMessage("Stergerea candidatului a esuat!");
			}
		} catch (ValidatorException | sun.security.validator.ValidatorException e1) {
            showErrorMessage(e1.getMessage());
        }
    }
    
    public void save(ActionEvent e){
    	service.saveRepo();
    }
}
