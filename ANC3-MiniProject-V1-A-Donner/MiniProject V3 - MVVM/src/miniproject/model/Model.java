/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniproject.model;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author kuo
 */
public class Model{
    
    private static final int MIN_WORD_LENGTH = 3;
    private final ObservableList<String> toDoList = FXCollections.observableArrayList();
    private final ObservableList<String> doneList = FXCollections.observableArrayList();
    //private int numLineSelected = -1;
    //private String lineSelected;
    //private final ObservableList<String> lines = FXCollections.observableArrayList();

    
    public Model(List<String> listes){
        for(String mot : listes){
            if((!this.toDoList.contains(mot)) &&(!this.doneList.contains(mot)) && (mot.length()>=MIN_WORD_LENGTH))
                toDoList.add(mot);
        }
    }
    
    public Model(){
        
    }
    
    public ObservableList<String> getToDoList(){
        return FXCollections.unmodifiableObservableList(toDoList);
    }

    public ObservableList<String> getDoneList(){
        return FXCollections.unmodifiableObservableList(doneList);
    }
    
    public void setDone(int valeur) throws InvalidTransferException{
        if((valeur>=0 && valeur< toDoList.size())&&(!toDoList.isEmpty())){
            this.doneList.add(this.toDoList.get(valeur));
            this.toDoList.remove(valeur);
        }
        else{
            throw new InvalidTransferException();
        }            
    }
    
    public void setToDo(int valeur) throws InvalidTransferException{
        if((valeur>=0 && valeur< doneList.size())&&(!doneList.isEmpty())){
            this.toDoList.add(this.doneList.get(valeur));
            this.doneList.remove(valeur);
        }else{
            throw new InvalidTransferException();
        }
    }
    
    public boolean addToDo(String txt)  {
        if(txt.length()<3 || this.getToDoList().contains(txt)|| this.getDoneList().contains(txt))
            return false;
        else {
            this.toDoList.add(txt);
            return true;
        }
    }
    /*
    public String getAdded(){
        return lineSelected;
    }
    
    public void select(int index) {
        numLineSelected = index;
        notif(TypeNotif.LINE_SELECTED);
    }
    
    public void unselect() {
        numLineSelected = -1;
        notif(TypeNotif.LINE_UNSELECTED);
    }
    
    public int selectedIndex() {
        return numLineSelected;
    }
    
    public void notif(TypeNotif typeNotif) {
        setChanged();
        notifyObservers(typeNotif);
    }
    */
}
