/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import viewmodel.EditVM;

/**
 *
 * @author kuo
 */
public class EditView extends Stage{
    
    public EditView(Stage primaryStage, EditVM editVM) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.textProperty().bindBidirectional(editVM.textProperty());
        StackPane stackPane = new StackPane(textArea);
        VBox scene2 = new VBox(textArea);
        
        Button save = new Button("save");
        save.setOnAction((ActionEvent event) -> {
            try {
                editVM.save();
            } catch (IOException ex) {
                Logger.getLogger(EditView.class.getName()).log(Level.SEVERE, null, ex);
            }
            //editVM.setVisible(false);
        });
        HBox boxButton = new HBox(save);
        boxButton.setAlignment(Pos.CENTER);
        scene2.getChildren().add(boxButton);
        
        setOnHiding((e) -> editVM.setVisible(false));
        editVM.showingProperty().addListener((obj, old, act) -> {
            if(act) showAndWait();
        });
        
        Scene scene = new Scene(scene2);        
        setScene(scene);
       
        titleProperty().bind(editVM.fileNameProperty().
                concat(" : ").
                concat(editVM.textLengthProperty()).
                concat(" octets")
        );
    }
   
}
