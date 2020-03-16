/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;



import java.io.IOException;
import javafx.beans.property.SimpleObjectProperty;
import model.Fichier;
import model.Model;
/**
 *
 * @author kuo
 */
public class ViewModel {
    private final Model model;
    
    public ViewModel(Model model){
        this.model=model;
    }
    /*
    public SimpleListProperty<TreeItem<Fichier>> linesProperty() {
        return new SimpleListProperty<>(model.getTreeItems());
    }*/
    
    public SimpleObjectProperty<Fichier> getFichierGauche() throws IOException{
        return new SimpleObjectProperty<>(model.getGauche().getValue());
    }
    
    public SimpleObjectProperty<Fichier> getFichierDroite() throws IOException{
        return new SimpleObjectProperty<>(model.getDroite().getValue());
    }

}
