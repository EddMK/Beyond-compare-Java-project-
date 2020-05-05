/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableIntegerValue;

/**
 *
 * @author kuo
 */
public class EditVM {
    private final StringProperty text = new SimpleStringProperty();
    private final BooleanProperty showing = new SimpleBooleanProperty(false);
    private final ViewModel viewModel;
    
    public EditVM(ViewModel viewmodel){
        this.viewModel = viewmodel;
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
/*   
    public StringProperty fileNameProperty() {
        return viewModel.selectedFileNameProperty();
    }
*/  
    public ReadOnlyBooleanProperty showingProperty() {
        return showing;
    }

    public void setVisible(boolean b) {
        showing.setValue(b);
    }

        
    
    
}
