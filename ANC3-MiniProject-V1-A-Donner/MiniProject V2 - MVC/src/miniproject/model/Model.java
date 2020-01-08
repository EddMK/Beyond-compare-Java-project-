/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniproject.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
/**
 *
 * @author kuo
 */
public class Model extends Observable{
    
    private static final int MIN_WORD_LENGTH = 3;
    private final List<String> toDoList = new ArrayList<>();
    private final List<String> doneList = new ArrayList<>();
    private int numLineSelected = -1;
    private String lineSelected;
    
    public enum TypeNotif {
        INIT, LINE_SETDONE, LINE_SETTODO, LINE_ADDED, LINE_SELECTED,LINE_UNSELECTED
    }
    
    public Model(List<String> listes){
        for(String mot : listes){
            if((!this.toDoList.contains(mot)) &&(!this.doneList.contains(mot)) && (mot.length()>=MIN_WORD_LENGTH))
                toDoList.add(mot);
        }
    }
    
    public Model(){
        
    }
    
    public List<String> getToDoList(){
        return Collections.unmodifiableList(this.toDoList);
    }
    
    public List<String> getDoneList(){
        return Collections.unmodifiableList(this.doneList);
    }
    
    public void setDone(int valeur) throws InvalidTransferException{
        if((valeur>=0 && valeur< toDoList.size())&&(!toDoList.isEmpty())){
            numLineSelected=valeur;
            lineSelected = this.toDoList.get(numLineSelected);
            this.doneList.add(lineSelected);
            this.toDoList.remove(numLineSelected);
            notif(TypeNotif.LINE_SETDONE);
        }
        else{
            throw new InvalidTransferException();
        }            
    }
    
    public void setToDo(int valeur) throws InvalidTransferException{
        if((valeur>=0 && valeur< doneList.size())&&(!doneList.isEmpty())){
            numLineSelected=valeur;
            lineSelected = this.doneList.get(numLineSelected);
            this.toDoList.add(this.doneList.get(valeur));
            this.doneList.remove(valeur);
            notif(TypeNotif.LINE_SETTODO);
        }else{
            throw new InvalidTransferException();
        }
    }
    
    public boolean addToDo(String txt)  {
        if(txt.length()<3 || this.getToDoList().contains(txt)|| this.getDoneList().contains(txt))
            return false;
        else {
            this.toDoList.add(txt);
            lineSelected = txt;
            notif(TypeNotif.LINE_ADDED);
            return true;
        }
    }
    
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
    
}
