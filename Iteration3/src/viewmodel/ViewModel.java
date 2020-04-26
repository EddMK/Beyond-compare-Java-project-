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
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import model.Fichier;
import model.Model;
/**
 *
 * @author kuo
 */
public class ViewModel {
    
    private final StringProperty pathGauche = new SimpleStringProperty("TestBC/RootBC_Left");
    private final StringProperty pathDroite = new SimpleStringProperty("TestBC/RootBC_Right");
    private final Model model;
    
    public ViewModel(Model model){
        this.model=model;   
        pathGauche.addListener((o,oldValue,newValue) -> {
            /*try {
                //model.setFichierGauche(newValue);
                System.out.println(model.getLeft().toString());
                System.out.println(FichierGaucheProperty());
            } catch (IOException ex) {
                Logger.getLogger(ViewModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            */
        });
        pathDroite.addListener((o,oldValue,newValue) -> {
            /*try {
                model.setFichierDroite(newValue);
            } catch (IOException ex) {
                Logger.getLogger(ViewModel.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        });
    }
    
    public StringProperty pathLeftProperty(){
        return this.pathGauche;
    }
    
    public StringProperty pathRightProperty(){
        return this.pathDroite;
    }
    
    public ObjectProperty<TreeItem<Fichier>> fichierGaucheProperty() throws IOException {
        return new SimpleObjectProperty(model.getLeft());
    }
    
    public ObjectProperty<TreeItem<Fichier>> fichierDroiteProperty() throws IOException {
        return new SimpleObjectProperty(model.getRight());
    }
    
    public static TreeItem<Fichier> makeTreeRoot(Fichier root) {
        TreeItem<Fichier> res = new TreeItem<>(root);
        res.setExpanded(true);
        if (root.type()=='D') {
            root.fichiers().forEach(se -> {
                res.getChildren().add(makeTreeRoot(se));
            });
        }
        return res;
    }
}
