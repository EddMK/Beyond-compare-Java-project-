package model;


import java.io.IOException;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kuo
 */
public class FichierSimple extends Fichier {
    private Etat etat;
    private final StringProperty text;

    public FichierSimple(String nom, char type, Path path,Long size,Etat etat,String text) {
        super(nom,type, path);
        this.etat=etat;
        this.setSize(size);
        this.text = new SimpleStringProperty(text);
    }
    
    @Override
    public void setEtat(Etat newEtat){
        etat=newEtat;
    }

    
    @Override
    public Etat etat() {
        return etat;
    }
    
    @Override
    protected String formatAffichage(int decalage) {       
        return super.formatAffichage(decalage) + getNom() +" " +  type()  + "  "+
                getDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))+
                " "+getSize()+" "+etat()+"\n";
    }

    @Override
    public void ajoutFichier(Fichier f) {
        throw new UnsupportedOperationException("Not supported."); 
    }
    
    
    @Override
    public Iterable<Fichier> fichiers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void setSelected(List<Etat> newSelected){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Etat> getSelected(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getTextArea() {
        return this.text.get();
    }

    @Override
    public void setTextArea(String text) {
        this.text.set(text);
    }

}
