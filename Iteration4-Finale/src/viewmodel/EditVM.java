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
    private final StringProperty fileName = new SimpleStringProperty();
    private final BooleanProperty showing = new SimpleBooleanProperty(false);
    private final ViewModel viewModel;
    
    public EditVM(ViewModel viewmodel){
        this.viewModel = viewmodel;
        
    }
    
    public StringProperty fileNameProperty(){
        return fileName;
    }
    
    void setFileName(String s){
        fileName.setValue(s);
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

    
    public ReadOnlyBooleanProperty showingProperty() {
        return showing;
    }

    public void setVisible(boolean b) {
        showing.setValue(b);
    }
    
    
    public void save() throws IOException{
        //fileSelected.getValue().getValue().setTextArea(text.get());
        this.viewModel.save(text.get(),text.length().get());
    }
}
