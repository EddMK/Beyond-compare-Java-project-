/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniproject.viewmodel;
import java.util.Arrays;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import miniproject.model.InvalidTransferException;
import miniproject.model.Model;
/**
 *
 * @author kuo
 */
public class ViewModel {
    
    
    private final BooleanProperty addToDo = new SimpleBooleanProperty(false);
    private final BooleanProperty setToDo = new SimpleBooleanProperty(false);
    private final BooleanProperty setDone = new SimpleBooleanProperty(false);
    private final StringProperty addText = new SimpleStringProperty();
    private final IntegerProperty numLineToDoListSelected = new SimpleIntegerProperty(-1); 
    private final IntegerProperty numLineDoneListSelected = new SimpleIntegerProperty(-1);
    private final Model model;
    
    private static final List<String> INIT_DATA = Arrays.asList(
            "Jouer à SuperMario",
            "Traîner sur FaceBook",
            "Revoir Pro2",
            "Twitter",
            "Travailler au projet Anc3",
            "Regarder du foot",
            "Ecouter de la musique"
    );

    public ViewModel(Model model) {
        this.model = model;
        for(int i = 0; i<INIT_DATA.size(); i++){
            model.addToDo(INIT_DATA.get(i));
        }
    }
    
    public SimpleListProperty<String> todolistProperty() {
        return new SimpleListProperty<>(model.getToDoList());
    }
    
    public SimpleListProperty<String> donelistProperty() {
        return new SimpleListProperty<>(model.getDoneList());
    }
    
    public StringProperty addTextProperty() {
        return addText;
    }
    
    public BooleanProperty addToDoProperty() {
        return addToDo;
    }
    
    public BooleanProperty setToDoProperty() {
        return setToDo;
    }
    
    public BooleanProperty setDoneProperty() {
        return setDone;
    }
    
    public IntegerProperty numLineToDoListSelectedProperty() {
        return numLineToDoListSelected;
    }
    
    public IntegerProperty numLineDoneListSelectedProperty() {
        return numLineDoneListSelected;
    }
    
    public void line_added(){
        String txt = addText.get();
        if(model.addToDo(txt))
            addText.set("");
        else
            addText.set(txt);
    }
    
    public void line_setdone() throws InvalidTransferException{
        model.setDone(numLineToDoListSelected.get());
          
    }
    
    public void line_settodo()throws InvalidTransferException{
        model.setToDo(numLineDoneListSelected.get());
    }    
/*
    public void setToDo(int numLine) throws InvalidTransferException{
        model.setToDo(numLine);
    }
    
    public void setDone(int numLine) throws InvalidTransferException{
        model.setDone(numLine);
    }
    
    public void addToDo(String ligne){
         model.addToDo(ligne);
    }
    
    public void lineSelectionTodolist(int numLine) {
        if (numLine >=0 && numLine < model.getToDoList().size())
            model.select(numLine);
        else
            model.unselect();
    }
    
    public void lineSelectionDonelist(int numLine) {
        if (numLine >=0 && numLine < model.getDoneList().size())
            model.select(numLine);
        else
            model.unselect();
    }
*/
}
