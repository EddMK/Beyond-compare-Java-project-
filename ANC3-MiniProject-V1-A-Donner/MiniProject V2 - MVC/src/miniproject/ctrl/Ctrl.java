/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniproject.ctrl;
import miniproject.model.InvalidTransferException;
import miniproject.model.Model;
/**
 *
 * @author kuo
 */
public class Ctrl {

    private final Model model;

    public Ctrl(Model model) {
        this.model = model;
    }
    
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
}
