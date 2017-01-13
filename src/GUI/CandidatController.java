package GUI;

import domain.ValidatorException;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import domain.Candidat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import controller.ControllerCandidat;
import utils.Observable;
import utils.Observer;

@SuppressWarnings("unused")
public class CandidatController implements Observer<Candidat> {
    private ObservableList<Candidat> model;

    ControllerCandidat service;
    @FXML
    private TableView<Candidat> candTable;
    @FXML
    private TableColumn<Candidat, String> nameColumn;
    @FXML
    private TableColumn<Candidat, String> telColumn;
    @FXML
    private Pagination pagination ;
    
    final private int rowsPerPage = 15;
    
	public CandidatController(){
    }
	
	public void setService(ControllerCandidat candidatService) {
        this.service=candidatService;
        this.model= FXCollections.observableArrayList((Collection<? extends Candidat>)candidatService.getCandidati());
        this.pagination.setPageCount(getNrPages());
        this.pagination.setCurrentPageIndex(0);
        updateTable(candTable, 0);
    }
	
	private void updateTable(TableView<Candidat> table, Integer index) {
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
    	nameColumn.setCellValueFactory(new PropertyValueFactory<Candidat, String>("nume"));
    	telColumn.setCellValueFactory(new PropertyValueFactory<Candidat, String>("tel"));
    	pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> updateTable(candTable, newIndex.intValue()));
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
        updateTable(candTable, pagination.getCurrentPageIndex());  
    }
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void update(Observable<Candidat> observable) {
            ControllerCandidat s=(ControllerCandidat)observable;
            model.setAll((List)s.getCandidati());
            refreshTable();
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
    
    
    public void handleFilterMajori()
    {
    	this.model= FXCollections.observableArrayList((Collection<? extends Candidat>)this.service.filterCandidatiMajori());
    	refreshTable();
    }
    
    public void handleFilterC()
    {
    	this.model= FXCollections.observableArrayList((Collection<? extends Candidat>)this.service.filterCandidatiC());
    	refreshTable();
    }
    
    public void handleRemoveFilter()
    {
    	this.model= FXCollections.observableArrayList((Collection<? extends Candidat>)this.service.getCandidati());
    	refreshTable();
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
		refreshTable();
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
