package GUI;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import domain.Candidat;
import javafx.scene.Node;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import controller.Controller;


public class CandidatView {
    BorderPane borderPane;

    TableView<Candidat> candTable=new TableView<>();
    TableColumn<Candidat,String> nameColumn=new TableColumn<>("Nume");
    TableColumn<Candidat,String> phoneColumn=new TableColumn<>("Telefon");

    TextField textFieldId=new TextField();
    TextField textFieldName=new TextField();
    TextField textFieldPhone=new TextField();
    TextField textFieldVarsta=new TextField();    
    TextField textFieldAdress=new TextField();

    Button buttonAdd=new Button("Add");
    Button buttonUpdate=new Button("Update");
    Button buttonDelete=new Button("Delete");
    Button buttonClear = new Button("ClearAll");


    CandidatViewController ctrl;


    public CandidatView(Controller service){
        ctrl=new CandidatViewController(service, this);
        service.addObserver(ctrl);
        initBorderPane();
    }

    public BorderPane getView() {
        return borderPane;
    }

    private void initBorderPane() {
        borderPane=new BorderPane();
        borderPane.setTop(initTop());
        borderPane.setCenter(initCenter());
        borderPane.setLeft(initLeft());
    }

    private Node initTop() {
        AnchorPane anchorPane=new AnchorPane();

        Label l=new Label("Candidates management system");
        l.setFont(new Font(20));
        l.setStyle("-fx-font-weight: bold");
        AnchorPane.setTopAnchor(l,20d);
        AnchorPane.setRightAnchor(l,100d);
        anchorPane.getChildren().add(l);
        return anchorPane;
    }

    private Label createLabel(String s, int fontSize, Color c){
        Label l=new Label();
        l.setText(s);
        l.setFont(new Font(15));
        l.setTextFill(c);
        return l;
    }

    private Node initCenter() {
        AnchorPane anchorPane=new AnchorPane();

        //init GridPane candidates details
        GridPane gridCandidatDetails=new GridPane();
        gridCandidatDetails.setHgap(5);
        gridCandidatDetails.setVgap(5);
        AnchorPane.setLeftAnchor(gridCandidatDetails,20d);
        AnchorPane.setTopAnchor(gridCandidatDetails,20d);
        ColumnConstraints c=new ColumnConstraints();
        c.setPrefWidth(100);
        gridCandidatDetails.getColumnConstraints().add(c);



        Label labelName=createLabel("Name:",12,Color.BLACK);
        labelName.setStyle("-fx-font-weight: bold");
        Label labelPhone=createLabel("Phone number:",12,Color.BLACK);
        labelPhone.setStyle("-fx-font-weight: bold");
        Label labelAdress=createLabel("Adress:",12,Color.BLACK);
        labelAdress.setStyle("-fx-font-weight: bold");
        Label labelId=createLabel("Id:",12,Color.BLACK);
        labelId.setStyle("-fx-font-weight: bold");
        Label labelVarsta=createLabel("Age:",12,Color.BLACK);
        labelVarsta.setStyle("-fx-font-weight: bold");

        gridCandidatDetails.add(labelId,0,0);
        gridCandidatDetails.add(labelName,0,1);
        gridCandidatDetails.add(labelVarsta,0,2);
        gridCandidatDetails.add(labelPhone,0,3);
        gridCandidatDetails.add(labelAdress,0,4);
        
        gridCandidatDetails.add(textFieldId,1,0);
        gridCandidatDetails.add(textFieldVarsta,1,2);
        gridCandidatDetails.add(textFieldName,1,1);
        gridCandidatDetails.add(textFieldPhone,1,3);
        gridCandidatDetails.add(textFieldAdress,1,4);

        anchorPane.getChildren().add(gridCandidatDetails);
        //init HBox Button

        HBox hb=new HBox(5, buttonAdd,buttonUpdate, buttonDelete,buttonClear);
        buttonAdd.setOnAction(ctrl::handleAddCandidat);
        buttonUpdate.setOnAction(ctrl::handleUpdateCandidat);
        buttonClear.setOnAction(ctrl::handleClearFields);
        buttonDelete.setOnAction(ctrl::handleDeleteCandidat);

        AnchorPane.setBottomAnchor(hb,100d);
        AnchorPane.setLeftAnchor(hb,20d);
        //hb.setPadding(new Insets(30));
        anchorPane.getChildren().add(hb);

        return anchorPane;

    }

    private Node initLeft() {
        AnchorPane anchorPane=new AnchorPane();
        //anchor the TableView into the ap
        AnchorPane.setLeftAnchor(candTable,20d);
        AnchorPane.setTopAnchor(candTable,20d);

        candTable.setMinHeight(50d);
        candTable.setPrefHeight(300d);
        initTableView();
        anchorPane.getChildren().add(candTable);
        return anchorPane;
    }

    public void initTableView()
    {
        candTable.getColumns().add(nameColumn);
        candTable.getColumns().add(phoneColumn);


        //stabilirea valorilor asociate unei celule
        nameColumn.setCellValueFactory(new PropertyValueFactory<Candidat, String>("nume")); //
        // return an ReadOnlyObjectWrapper  if Candidat class don't have a name Property attribute
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Candidat, String>("tel"));


        //candTable.setItems(model);
        candTable.getSelectionModel().selectedItemProperty().addListener(ctrl.changedTableItemListener());

        // Auto resize columns
        candTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // clear Candidat
        ctrl.showCandidatDetails(null);
        // Listen for selection changes and show the Candidat details when changed.
        //aici
//        candTable.getSelectionModel().selectedItemProperty().addListener(
//                (observable,oldvalue,newValue)->showCandidatDetails(newValue) );

    }

}
