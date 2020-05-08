/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;

import java.io.IOException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.scene.control.TreeItem;
import model.Fichier;

/**
 *
 * @author kuo
 */
public class EditVM {
    private final StringProperty text = new SimpleStringProperty();
    private final BooleanProperty showing = new SimpleBooleanProperty(false);
    private final ObjectProperty<TreeItem<Fichier>> fileSelected = new SimpleObjectProperty<>();
    private final ViewModel viewModel;
    
    public EditVM(ViewModel viewmodel){
        this.viewModel = viewmodel;
        text.set("essai");
        System.out.println(fileSelected);
        fileSelected.bindBidirectional(viewmodel.currentFileSelectedProperty());
        fileSelected.addListener((o,oldValue,newValue) ->{
            System.out.println(newValue);
            if(newValue.getValue().type()=='F'){
                text.set(newValue.getValue().getTextArea());
            }
        });
    }
    

    void setText(String s) {
        text.setValue(s);
    }
    
    public StringProperty textProperty() {
        return text;
    }
    
    public ObservableIntegerValue textLengthProperty() {
        return text.length();
    }

    public StringProperty fileNameProperty() {
        return viewModel.selectedFileNameProperty();
    }
    
    public ReadOnlyBooleanProperty showingProperty() {
        return showing;
    }

    public void setVisible(boolean b) {
        showing.setValue(b);
    }
    
    public ObjectProperty<TreeItem<Fichier>> fileSelectedProperty() {
        return fileSelected;
    }
    
    public void save() throws IOException{
        //fileSelected.getValue().getValue().setTextArea(text.get());
        this.viewModel.save(text.get(),text.length().get());
    }
}
