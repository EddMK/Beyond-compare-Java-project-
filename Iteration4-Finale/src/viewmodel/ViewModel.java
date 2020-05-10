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
    private final BooleanProperty newerRightSelected = new SimpleBooleanProperty(false);
    private final BooleanProperty newerLeftSelected = new SimpleBooleanProperty(false);
    private final BooleanProperty orphanSelected = new SimpleBooleanProperty(false);
    private final BooleanProperty sameSelected = new SimpleBooleanProperty(false);
    private final BooleanProperty foldersOnlySelected = new SimpleBooleanProperty(false);
    private final ObjectProperty<TreeItem<Fichier>> selectedFileLeft = new SimpleObjectProperty<>();
    private final ObjectProperty<TreeItem<Fichier>> selectedFileRight = new SimpleObjectProperty<>();
    private final ObjectProperty<TreeItem<Fichier>> currentFileSelected = new SimpleObjectProperty<>();
    private final Model model;
    private final EditVM editor;
    
    public ViewModel(Model model) throws IOException{
        this.model=model; 
        this.editor = new EditVM(this);
        fichierLeft = new SimpleObjectProperty<>(model.getRootLeft(newerLeftSelected.get(), newerRightSelected.get(), orphanSelected.get(), sameSelected.get(), foldersOnlySelected.get()));
        fichierRight = new SimpleObjectProperty<>(model.getRootRight(newerLeftSelected.get(), newerRightSelected.get(), orphanSelected.get(), sameSelected.get(), foldersOnlySelected.get()));
        pathGauche.addListener((o,oldValue,newValue) -> {
            try {
                model.modif(newValue,pathDroite.get() );
                fichierLeft.setValue(model.getRootLeft(false, false, false, false, false));
                fichierRight.setValue(model.getRootRight(false, false, false, false, false));
                newerLeftSelected.set(false);
                newerRightSelected.set(false);
                orphanSelected.set(false);
                sameSelected.set(false);
                foldersOnlySelected.set(false);
            } catch (IOException ex) {
                Logger.getLogger(ViewModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        pathDroite.addListener((o,oldValue,newValue) -> {
            try {
                model.modif(pathGauche.get() ,newValue);
                fichierLeft.setValue(model.getRootLeft(false, false, false, false, false));
                fichierRight.setValue(model.getRootRight(false, false, false, false, false));
                newerLeftSelected.set(false);
                newerRightSelected.set(false);
                orphanSelected.set(false);
                sameSelected.set(false);
                foldersOnlySelected.set(false);
            } catch (IOException ex) {
                Logger.getLogger(ViewModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); 
        newerLeftSelected.addListener((o,oldValue,newValue) ->{
            //model.boutons(newValue, newerRightSelected.get(), orphanSelected.get(), sameSelected.get(), foldersOnlySelected.get());
            fichierLeft.set(model.getRootLeft(newValue, newerRightSelected.get(), orphanSelected.get(), sameSelected.get(), foldersOnlySelected.get()));//Gauche newer
            fichierRight.set(model.getRootRight(newValue, newerRightSelected.get(), orphanSelected.get(), sameSelected.get(), foldersOnlySelected.get()));// Droite older
        });
        newerRightSelected.addListener((o,oldValue,newValue) ->{
            //model.boutons(newerLeftSelected.get(), newValue, orphanSelected.get(), sameSelected.get(), foldersOnlySelected.get());
            fichierLeft.set(model.getRootLeft(newerLeftSelected.get(), newValue, orphanSelected.get(), sameSelected.get(), foldersOnlySelected.get()));//Gauche newer
            fichierRight.set(model.getRootRight(newerLeftSelected.get(), newValue, orphanSelected.get(), sameSelected.get(), foldersOnlySelected.get()));// Droite older

        });
        orphanSelected.addListener((o,oldValue,newValue) ->{
            //model.boutons(newerLeftSelected.get(),newerRightSelected.get() ,newValue, sameSelected.get(), foldersOnlySelected.get());
            fichierLeft.set(model.getRootLeft(newerLeftSelected.get(), newerRightSelected.get(),newValue, sameSelected.get(), foldersOnlySelected.get()));//Gauche newer
            fichierRight.set(model.getRootRight(newerLeftSelected.get(), newerRightSelected.get(), newValue, sameSelected.get(), foldersOnlySelected.get()));// Droite older

        });
        sameSelected.addListener((o,oldValue,newValue) ->{
            //model.boutons(newerLeftSelected.get(),newerRightSelected.get() ,orphanSelected.get(),newValue , foldersOnlySelected.get());
            fichierLeft.set(model.getRootLeft(newerLeftSelected.get(), newerRightSelected.get(), orphanSelected.get(), newValue, foldersOnlySelected.get()));//Gauche newer
            fichierRight.set(model.getRootRight(newerLeftSelected.get(), newerRightSelected.get(), orphanSelected.get(), newValue, foldersOnlySelected.get()));// Droite older

        });
        foldersOnlySelected.addListener((o,oldValue,newValue) ->{
            //model.boutons(newerLeftSelected.get(),newerRightSelected.get() ,orphanSelected.get(), sameSelected.get(), newValue);
            fichierLeft.set(model.getRootLeft(newerLeftSelected.get(), newerRightSelected.get(), orphanSelected.get(), sameSelected.get(), newValue));//Gauche newer
            fichierRight.set(model.getRootRight(newerLeftSelected.get(), newerRightSelected.get(), orphanSelected.get(), sameSelected.get(), newValue));// Droite older

        }); 
        selectedFileLeft.addListener((o,oldValue,newValue) ->{ 
            currentFileSelected.set(newValue);
        });
        selectedFileRight.addListener((o,oldValue,newValue) ->{ 
            currentFileSelected.set(newValue);
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
    
    
    public ObjectProperty<TreeItem<Fichier>> selectedFileLeftProperty() {
        return selectedFileLeft;
    }
    
    public ObjectProperty<TreeItem<Fichier>> selectedFileRightProperty() {
        return selectedFileRight;
    }//currentFileSelected
    
    public ObjectProperty<TreeItem<Fichier>> currentFileSelectedProperty() {
        return currentFileSelected;
    }
    
    public void all() throws IOException{
        fichierLeft.setValue(model.getRootLeft(false, false, false, false, false));
        fichierRight.setValue(model.getRootRight(false, false, false, false, false));
        newerLeftSelected.set(false);
        newerRightSelected.set(false);
        orphanSelected.set(false);
        sameSelected.set(false);
        foldersOnlySelected.set(false);
    }
    
    public void openSelectedLeftFile() {
        if(selectedFileLeft.get().getValue().type()=='F'){
            editor.setFileName(selectedFileLeft.get().getValue().getNom());
            editor.setText(selectedFileLeft.get().getValue().getTextArea());
            editor.setVisible(true);
        }
    }
    
    public void openSelectedRightFile() {
        if(selectedFileRight.get().getValue().type()=='F'){
            editor.setFileName(selectedFileRight.get().getValue().getNom());
            editor.setText(selectedFileRight.get().getValue().getTextArea());
            editor.setVisible(true);
        }
    }
    
    
    public EditVM getEditVM() {
        return editor;
    }
    
    public void save(String newText,int newSize) throws IOException{
        model.bindModif(currentFileSelected.get().getValue(),newText,newSize);
        fichierLeft.setValue(model.getRootLeft(newerLeftSelected.get(), newerRightSelected.get(), orphanSelected.get(), sameSelected.get(), foldersOnlySelected.get()));
        fichierRight.setValue(model.getRootRight(newerLeftSelected.get(), newerRightSelected.get(), orphanSelected.get(), sameSelected.get(), foldersOnlySelected.get()));
    }
    
 
}
