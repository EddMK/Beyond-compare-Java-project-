package model;


import java.io.IOException;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kuo
 */
public class Dossier extends Fichier{
    //private final List<Fichier> fichiers = new ArrayList<>();
    private final boolean tete;
    private final List<Fichier> fichiers = new ArrayList<>();
    
    
    public Dossier(String nom, char type, Path path, Boolean tete) {
        super(nom,type, path);
        this.tete=tete;
    }

    @Override
    public int taille() {
        int res = 0;
        for (Fichier f : fichiers)
            res += f.taille();
        return res;
    }
    
    @Override
    protected String formatAffichage(int decalage) {       
        StringBuilder res = new StringBuilder();
            try {
                res.append(super.formatAffichage(decalage))
                        .append(nom()).append(" ")
                        .append(type()).append(" ")
                        .append(lastModificationTime(path()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                        .append(" ").append(taille())
                        .append(" ").append(etat())
                        //.append(" - contient : ").
                        .append("\n");
            } catch (IOException ex) {
                Logger.getLogger(Dossier.class.getName()).log(Level.SEVERE, null, ex);
            }
        Collections.sort(fichiers,new Comparator<Fichier>(){ 
            @Override
            public int compare(Fichier f1, Fichier f2){
                return Character.compare(f1.type(), f2.type());
            }   
        });
        for (Fichier f : fichiers){             
            res.append(f.formatAffichage(decalage + 1));
        }
        return res.toString();
    }
    
    @Override
    public void ajoutFichier(Fichier f) {
        fichiers.add(f);
    }
    
    @Override
    public Iterable<Fichier> fichiers() {
        return fichiers;
    }

}