/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
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
        StackPane stackPane = new StackPane(textArea);
        
        setOnHiding((e) -> editVM.setVisible(false));
        editVM.showingProperty().addListener((obj, old, act) -> {
            if(act) showAndWait();
        });
        
        Scene scene = new Scene(stackPane, 600, 400);        
        setScene(scene);
        /*
        titleProperty().bind(editVM.fileNameProperty().
                concat(" : ").
                concat(editVM.textLengthProperty()).
                concat(" octets")
        );*/
    }
    
}
