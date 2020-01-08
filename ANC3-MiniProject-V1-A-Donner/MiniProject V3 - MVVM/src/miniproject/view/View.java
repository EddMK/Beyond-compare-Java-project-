/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniproject.view;


import miniproject.viewmodel.ViewModel;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import miniproject.model.InvalidTransferException;
/**
 *
 * @author kuo
 */
public class View extends HBox{
    
    private final ListView<String> toDoList = new ListView<>();//ca
    private final ListView<String> doneList = new ListView<>();//ca
    private final Label toDoLabel = new Label("À faire");
    private final Label doneLabel = new Label("C'est fait");
    private final Label addLabel = new Label("À ajouter : ");
    private final Button setDone = new Button(">>");//ca
    private final Button setToDo = new Button("<<");//ca
    private final Button addButton = new Button(">>");//ca
    private final TextField addText = new TextField();//ca
    private final VBox lBox = new VBox();
    private final VBox cBox = new VBox();
    private final VBox rBox = new VBox();
    private final VBox addBox = new VBox();
    
    private final ViewModel viewModel;

    public View(Stage primaryStage, ViewModel viewModel) {
        this.viewModel = viewModel;
        configComponents();
        configBindings();
        configListeners();
        Scene scene = new Scene(this, 800, 400);
        primaryStage.setTitle("MiniProject V3");
        primaryStage.setScene(scene);
    }
    
    private void configComponents() {
        configEditZone();
        configTextZone();
        configWindow();
    }
    
    private void configEditZone() {
        addBox.getChildren().addAll(addLabel, addText, addButton);
        addBox.setAlignment(Pos.CENTER);
        addBox.setSpacing(20);
        addBox.setPrefWidth(250);
    }
    
    public void configTextZone(){
        lBox.getChildren().addAll(toDoLabel, toDoList);
        lBox.setAlignment(Pos.CENTER);
        lBox.setSpacing(5);
        lBox.setPrefWidth(250);
        cBox.getChildren().addAll(setDone, setToDo);
        cBox.setSpacing(20);
        cBox.setAlignment(Pos.CENTER);
        rBox.getChildren().addAll(doneLabel, doneList);
        rBox.setAlignment(Pos.CENTER);
        rBox.setSpacing(5);
        rBox.setPrefWidth(250);
        setDone.setPrefWidth(60);
        setToDo.setPrefWidth(60);
        addButton.setPrefWidth(60);
        setToDo.setDisable(true);
        setDone.setDisable(true);
        addButton.setDisable(true);
    }
    
    private void configWindow() {
        this.setPadding(new Insets(10));
        this.setSpacing(10);
        this.getChildren().addAll(addBox, lBox, cBox, rBox);
    }
    
    private void configBindings() {
        configDataBindings();
        configActionsBindings();
        configViewModelBindings();
    }
    
    private void configDataBindings() {
        toDoList.setItems(viewModel.todolistProperty());
        doneList.setItems(viewModel.donelistProperty());
        addText.textProperty().bindBidirectional(viewModel.addTextProperty());
    }
    
    private void configActionsBindings(){
        addButton.disableProperty().bindBidirectional(viewModel.addToDoProperty());
        setToDo.disableProperty().bindBidirectional(viewModel.setToDoProperty());
        setDone.disableProperty().bindBidirectional(viewModel.setDoneProperty());
    }
    
    private void configViewModelBindings() {
        //viewModel.focusTextZoneProperty().bind(lvLines.focusedProperty());
        viewModel.numLineToDoListSelectedProperty().bind(toDoList.getSelectionModel().selectedIndexProperty());
        viewModel.numLineDoneListSelectedProperty().bind(doneList.getSelectionModel().selectedIndexProperty());
    }
    
    private void configListeners() {
        configListenersEditZone();//permet d ecrire dans la barre de recherche
        configListenersTransfertZone();//permet de selectionner dans la liste de texte
    }
    
    public void configListenersEditZone(){
        addText.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                viewModel.line_added();
            }
        });// le bouton fonctionne mais pas enter
       
        addText.textProperty().addListener((obs, old, act) -> {
            addButton.setDisable(act.length() <= 2);
        });// Le bouton ne fonctionne pas
        
        addButton.setOnAction(e -> {
            viewModel.line_added();
        });
    }
    
    public void configListenersTransfertZone(){
        configListenersTransfertTodolist();
        configListenersTransfertDoneList();
        configListenersTransfertFocus();
    }
    
    public void configListenersTransfertTodolist(){
        setToDo.setOnAction(e -> {
            try {
                viewModel.line_settodo();
            } catch (InvalidTransferException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        doneList.setOnMouseClicked(e -> {//on sait pas faire le double click 
            if (e.getClickCount() == 2) {
                try {
                    viewModel.line_settodo();
                } catch (InvalidTransferException ex) {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        doneList.getSelectionModel().selectedIndexProperty().addListener((obs, old, act) -> {
            setToDo.setDisable((int) act == -1);
        });//le bouton ne s'active pas
    }
    
    public void configListenersTransfertDoneList(){
        setDone.setOnAction(e -> {try {
            //on peut toucher au bouton
            viewModel.line_setdone();
            } catch (InvalidTransferException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        toDoList.setOnMouseClicked(e -> {//on sait pas faire le double click
            if (e.getClickCount() == 2) {
                try {
                    viewModel.line_setdone();
                } catch (InvalidTransferException ex) {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        toDoList.getSelectionModel().selectedIndexProperty().addListener((obs, old, act) -> {
            setDone.setDisable((int) act == -1);
        });
    }//ctrl.lineSelectionTodolist(toDoList.getSelectionModel().getSelectedIndex());
    
    public void configListenersTransfertFocus(){
        addText.focusedProperty().addListener((obs, old, act) -> {
            if(!act)
                addText.requestFocus();
        });
    }  
}