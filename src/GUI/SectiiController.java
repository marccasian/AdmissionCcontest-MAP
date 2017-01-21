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
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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


@SuppressWarnings("unused")
public class SectiiController implements Observer<Sectie> {
    private ObservableList<Sectie> model;
    
    ControllerSectie service;
    @FXML
    private TableView<Sectie> sectTable;
    @FXML
    private TableColumn<Sectie, String> nameColumn;
    @FXML
    private TableColumn<Sectie, String> nrLocColumn;
    @FXML
    private Pagination pagination ;
    
    @FXML
    private TextField nrLocFilterTextField;
    
    @FXML
    private TextField numeFilterTextField;
    
    final private int rowsPerPage = 15;
    
	public SectiiController(){
      
    }
	
	public void setService(ControllerSectie sectieService) {
        this.service=sectieService;
        this.model= FXCollections.observableArrayList((Collection<? extends Sectie>)sectieService.getSectii());
        this.pagination.setPageCount(getNrPages());
        this.pagination.setCurrentPageIndex(0);
        updateTable(sectTable, 0);
    }
	
	private void updateTable(TableView<Sectie> table, Integer index) {
		this.pagination.setPageCount(getNrPages());
        int start = index * rowsPerPage ;
        int end = start + rowsPerPage;
        if (start + rowsPerPage > this.model.size()){
        	end = this.model.size();
        }
        table.getItems().setAll(FXCollections.observableArrayList(this.model.subList(start, end)));
    }
	
	 /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	nameColumn.setCellValueFactory(new PropertyValueFactory<Sectie, String>("nume"));
    	nrLocColumn.setCellValueFactory(new PropertyValueFactory<Sectie, String>("nrLoc"));
    	pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> updateTable(sectTable, newIndex.intValue()));
    }
    
    private int getNrPages(){
    	int last_pag = 0;
        if (this.model.size() % rowsPerPage != 0){
        	last_pag = 1;
        }
        return this.model.size() / rowsPerPage + last_pag;
    }
    
    private void refreshTable(){
    	this.pagination.setPageCount(getNrPages());
        updateTable(sectTable, pagination.getCurrentPageIndex());  
    }
    

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void update(Observable<Sectie> observable) {
            ControllerSectie s=(ControllerSectie)observable;
            model.setAll((List)s.getSectii());
            refreshTable();
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
    	refreshTable();
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
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            AddSectieController addSectieController= loader.getController();
            addSectieController.setService(service, dialogStage);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void handleFilterNrLoc()
    {
    	Integer nr = getNrLocForFilter();
    	if (nr >= 0){
    		this.model= FXCollections.observableArrayList((Collection<? extends Sectie>)this.service.filterSectiiNrLoc(nr));
    		refreshTable();
    	}
    	nrLocFilterTextField.setText("");
    }
    
    public void handleFilterNume()
    {
    	String nu = getNameForFilter();
    	if (nu != ""){
    		this.model= FXCollections.observableArrayList((Collection<? extends Sectie>)this.service.filterSectiiNume(nu));
    		refreshTable();
    	}
    	numeFilterTextField.setText("");
    }
    
    public void handleRemoveFilter()
    {
    	this.model= FXCollections.observableArrayList((Collection<? extends Sectie>)this.service.getSectii());
    	refreshTable();
    }    
    
    private Integer getNrLocForFilter() {
    	String nrs = nrLocFilterTextField.getText();
    	Integer nr = -1;
    	try{
    		nr = Integer.parseInt(nrs);
    		if (nr >= 0){
    			return nr;
    		}
    		throw new NumberFormatException();
    	}catch (NumberFormatException e) {
        	showErrorMessage("Numarul de locuri trebuie sa fie numar natural!");
        }
    	
    	return nr;
	}
    
    private String getNameForFilter() {
    	String nrs = numeFilterTextField.getText();
    	return nrs;
	}
    
}
