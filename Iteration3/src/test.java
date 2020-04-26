
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kuo
 */
public class test{
    
    public static void main(String[] args) {
        StringProperty pathGauche = new SimpleStringProperty("Je mange");
        
        StringProperty pathDroite = new SimpleStringProperty(" bien");
        
        StringProperty sum = new SimpleStringProperty("");
        pathDroite.addListener((o, old, newValue) -> {
            System.out.println(old);
        });
        sum.bindBidirectional(pathDroite);
        
        System.out.println(pathDroite.get());
        System.out.println(pathDroite);
        sum.setValue("pas");
        System.out.println(pathDroite.get());   
        System.out.println(pathDroite);
    }
}
