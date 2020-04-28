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
    private final ObjectProperty<TreeItem<Fichier>> fichierLeft;
    private final ObjectProperty<TreeItem<Fichier>> fichierRight;
    private final Model model;
    
    public ViewModel(Model model) throws IOException{
        this.model=model;   
        pathGauche.addListener((o,oldValue,newValue) -> {
            try {
                model.modif(newValue,pathDroite.get() );
                //model.setFichierGauche(newValue);
                fichierGaucheProperty().set(model.getLeft());
                fichierDroiteProperty().set(model.getRight());
                //System.out.println(pathDroite.get());
                //System.out.println(fichierGaucheProperty());
            } catch (IOException ex) {
                Logger.getLogger(ViewModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        pathDroite.addListener((o,oldValue,newValue) -> {
            try {
                model.modif(pathGauche.get() ,newValue);
                fichierGaucheProperty().set(model.getLeft());
                fichierDroiteProperty().set(model.getRight());
                //model.setFichierDroite(newValue);
                //fichierDroiteProperty().set(model.getRight());
                //System.out.println(pathGauche.get());
            } catch (IOException ex) {
                Logger.getLogger(ViewModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        fichierLeft = new SimpleObjectProperty<>(model.getLeft());
        fichierRight = new SimpleObjectProperty<>(model.getRight());
        
    }
    
    public StringProperty pathLeftProperty(){
        return this.pathGauche;
    }
    
    public StringProperty pathRightProperty(){
        return this.pathDroite;
    }
    
    public ObjectProperty<TreeItem<Fichier>> fichierGaucheProperty() throws IOException {
        return fichierLeft;
    }
    
    public ObjectProperty<TreeItem<Fichier>> fichierDroiteProperty() throws IOException {
        return fichierRight;
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
