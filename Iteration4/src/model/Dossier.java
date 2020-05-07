package model;


import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.beans.binding.ObjectBinding;

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
    private final boolean tete;
    private final List<Fichier> fichiers = new ArrayList<>();
    private final SizeBinding sizeBinding = new SizeBinding();
    private final DateTimeBinding dateTimeBinding = new DateTimeBinding();
    
    
    public Dossier(String nom, char type, Path path, Boolean tete) {
        super(nom,type, path);
        this.tete=tete;
        
        // Si la liste des enfants change, taille et date doivent être recalculées
        addToSizeBinding(getChildren()); 
        addToDateTimeBinding(getChildren());
        
        // taille et date sont liées au Bindings
        bindSizeTo(sizeBinding);
        bindDateTimeTo(dateTimeBinding);
    }
    
    private void addToSizeBinding(Observable obs) {
        sizeBinding.addBinding(obs);
        sizeBinding.invalidate();
    }
    
    // Ajoute une Observable dont dépend le Binding et provoque un recalcul
    private void addToDateTimeBinding(Observable obs) {
        dateTimeBinding.addBinding(obs);
        dateTimeBinding.invalidate();
    }
    
    /*
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
                        .append(" ").append(getSelected())
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
    */
    @Override
    protected String formatAffichage(int decalage) {
        StringBuilder res = new StringBuilder();
        res.append(super.formatAffichage(decalage))
           .append(getNom()).append(" ")
           .append(getSize()).append(" ")
           .append(getDateTime())
           .append(" - contient : ").append("\n");
        for (Fichier f : getContent()) 
            res.append(f.formatAffichage(decalage + 1));
        return res.toString();
    }
    
    @Override
    public void ajoutFichier(Fichier f) {
        // Taille et date dépendent des tailles et dates des enfants
        addToSizeBinding(f.sizeProperty());
        addToDateTimeBinding(f.dateTimeProperty());
        _ajoutFichier(f);
    }
    
    @Override
    public Iterable<Fichier> fichiers() {
        return fichiers;
    }
    
        
    // Un Binding pour le recalcul de la taille
    private class SizeBinding extends ObjectBinding<Long> {
        
        @Override // La taille est la sommme des taille des enfants
        protected Long computeValue() {
            return getChildren().stream().map(f -> f.getValue().getSize()).reduce(0L, (s1, s2) -> s1 + s2);
        }
        
        // Chaque Observable modifiée provoque un recalcul
        // Ajoute une Observable dont dépend le Binding
        void addBinding(Observable obs) {
            super.bind(obs);
        }
        
    }
    
    // Un Binding pour le recalcul de la date
    private class DateTimeBinding extends ObjectBinding<LocalDateTime> {

        @Override // La date est la plus récente des dates des enfants ou la date actuelle
        protected LocalDateTime computeValue() {
            return getChildren().isEmpty() ? LocalDateTime.now() : getChildren().stream().map(f -> f.getValue().getDateTime()).max(LocalDateTime::compareTo).get();
        }
        
        // Chaque Observable modifiée provoque un recalcul
        // Ajoute une Observable dont dépend le Binding
        void addBinding(Observable obs) {
            super.bind(obs);
        }
        
    }


}