/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;



import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TreeItem;
import model.Etat;
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
    private final BooleanProperty allSelected = new SimpleBooleanProperty();
    private final BooleanProperty newerRightSelected = new SimpleBooleanProperty(false);
    private final BooleanProperty newerLeftSelected = new SimpleBooleanProperty(false);
    private final BooleanProperty orphanSelected = new SimpleBooleanProperty(false);
    private final BooleanProperty sameSelected = new SimpleBooleanProperty(false);
    private final BooleanProperty foldersOnlySelected = new SimpleBooleanProperty(false);
    private final Model model;
    
    public ViewModel(Model model) throws IOException{
        this.model=model; 
        fichierLeft = new SimpleObjectProperty<>(model.getLeft());
        fichierRight = new SimpleObjectProperty<>(model.getRight());
        pathGauche.addListener((o,oldValue,newValue) -> {
            try {
                model.modif(newValue,pathDroite.get() );
                fichierLeft.set(model.getLeft());
                fichierRight.set(model.getRight());
            } catch (IOException ex) {
                Logger.getLogger(ViewModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        pathDroite.addListener((o,oldValue,newValue) -> {
            try {
                model.modif(pathGauche.get() ,newValue);
                fichierLeft.set(model.getLeft());
                fichierRight.set(model.getRight());
            } catch (IOException ex) {
                Logger.getLogger(ViewModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); 
        newerLeftSelected.addListener((o,oldValue,newValue) ->{
            model.boutons(newValue, newerRightSelected.get(), orphanSelected.get(), sameSelected.get(), foldersOnlySelected.get());
            fichierLeft.set(model.getGaucheModif());//Gauche newer
            fichierRight.set(model.getDroiteModif());// Droite older
        });
        newerRightSelected.addListener((o,oldValue,newValue) ->{
            model.boutons(newerLeftSelected.get(), newValue, orphanSelected.get(), sameSelected.get(), foldersOnlySelected.get());
            fichierLeft.set(model.getGaucheModif());//Gauche newer
            fichierRight.set(model.getDroiteModif());// Droite older
        });
        orphanSelected.addListener((o,oldValue,newValue) ->{
            model.boutons(newerLeftSelected.get(),newerRightSelected.get() ,newValue, sameSelected.get(), foldersOnlySelected.get());
            fichierLeft.set(model.getGaucheModif());//Gauche newer
            fichierRight.set(model.getDroiteModif());// Droite older
        });
        sameSelected.addListener((o,oldValue,newValue) ->{
            model.boutons(newerLeftSelected.get(),newerRightSelected.get() ,orphanSelected.get(),newValue , foldersOnlySelected.get());
            fichierLeft.set(model.getGaucheModif());//Gauche newer
            fichierRight.set(model.getDroiteModif());// Droite older
        });
        foldersOnlySelected.addListener((o,oldValue,newValue) ->{
            model.boutons(newerLeftSelected.get(),newerRightSelected.get() ,orphanSelected.get(), sameSelected.get(), newValue);
            fichierLeft.set(model.getGaucheModif());//Gauche newer
            fichierRight.set(model.getDroiteModif());// Droite older
        });

        
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
    
    public BooleanProperty allSelectedProperty(){
        return this.allSelected;
    }
    
    public BooleanProperty newerRightSelectedProperty(){
        return this.newerRightSelected;
    }
    
    public BooleanProperty newerLeftSelectedProperty(){
        return this.newerLeftSelected;
    }
    
    public BooleanProperty orphanSelectedProperty(){
        return this.orphanSelected;
    }
    
    public BooleanProperty sameSelectedProperty(){
        return this.sameSelected;
    }
    
    public BooleanProperty foldersOnlySelectedProperty(){
        return this.foldersOnlySelected;
    }
    
    public void all() throws IOException{
        //Model model = new Model();
        model.modif(pathGauche.get(),pathDroite.get() );
        fichierLeft.set(model.getLeft());
        fichierRight.set(model.getRight());
        newerLeftSelected.set(false);
        newerRightSelected.set(false);
        orphanSelected.set(false);
        sameSelected.set(false);
        foldersOnlySelected.set(false);
    }
}
