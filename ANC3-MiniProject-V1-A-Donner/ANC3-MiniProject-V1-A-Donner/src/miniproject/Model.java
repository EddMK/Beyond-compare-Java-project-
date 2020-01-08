/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniproject ;


import miniproject.InvalidTransferException;
import miniproject.MiniProject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author kuo
 */
public class Model {
    
    private static final int MIN_WORD_LENGTH = 3;
    private final List<String> toDoList = new ArrayList<>();
    private final List<String> doneList = new ArrayList<>();
    
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
    
    
    
}
