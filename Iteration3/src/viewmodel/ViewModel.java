/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;



import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import model.Fichier;
import model.Model;
/**
 *
 * @author kuo
 */
public class ViewModel {
    
    private final Model model;
    private StringProperty pathStringLeft = new SimpleStringProperty("");
    private StringProperty pathStringRight = new SimpleStringProperty("");
    
    public ViewModel(Model model){
        this.model=model;
        pathStringLeft.addListener((o, old, newV) -> {
            try {
                model.setFichierGauche(newV);
            } catch (IOException ex) {
                Logger.getLogger(ViewModel.class.getName()).log(Level.SEVERE, null, ex);
            }});    
    }
    
    public ObjectProperty<Fichier> fichierGaucheProperty() throws IOException{
        return new SimpleObjectProperty<>(model.getLeft());
    }
    
    public ObjectProperty<Fichier> fichierDroiteProperty() throws IOException{
        return new SimpleObjectProperty<>(model.getRight());
    }
    
    public StringProperty pathGaucheProperty() throws IOException{
        return new SimpleStringProperty(model.getPathLeft());
    }
    
    public StringProperty pathDroiteProperty() throws IOException{
        return new SimpleStringProperty(model.getPathRight());
    }
    
    public StringProperty pathGaucheSelectedProperty(){
        return pathStringLeft;
    }
    
    public StringProperty pathDroiteSelectedProperty(){
        return pathStringRight;
    }
}
