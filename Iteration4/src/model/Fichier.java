package model;


import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kuo
 */
public abstract class Fichier extends TreeItem<Fichier> {
    private final StringProperty nom;
    private final LongProperty size;
    private final ObjectProperty<LocalDateTime> dateTime;
    private final char type;
    private final Path path;
    private Etat etat;
    private List<Etat> selected = new ArrayList<>();
    
    public Fichier(String nom, char type, Path path) {
        this.nom = new SimpleStringProperty(nom);
        size = new SimpleLongProperty();
        dateTime = new SimpleObjectProperty<>(LocalDateTime.now());
        this.type = type;
        this.path = path;
        setExpanded(true);
        setValue(this);
    }

    
    public void setSelected(List<Etat> newSelected){
        this.selected = newSelected;
    }
    
    public List<Etat> getSelected(){
        return this.selected;
    }
    
    public void setEtat(Etat newEtat){
        etat = newEtat;
    }
    
    public Etat etat(){
        return etat;
    }
    
    public char type(){ return type; }
    
    public Path path(){ return path; }
    
    public String getNom() { return nom.get(); }
    
    public ObservableStringValue nomProperty() {
        return nom;
    }
    

    
    
    protected String formatAffichage(int decalage) {
        String res = "";
        for (int i = 0; i < decalage; ++i)
            res += "\t";
        return res;
    }
 
    public long getSize() {
        return size.get();
    }

    void setSize(long size) {
        this.size.set(size);
    }

    public ReadOnlyLongProperty sizeProperty() {
        return size;
    }
    
    public LocalDateTime getDateTime() {
        return dateTime.getValue();
    }
    
    void setDateTime(LocalDateTime dateTime) {
        this.dateTime.setValue(dateTime);
    }
    
    public ReadOnlyObjectProperty<LocalDateTime> dateTimeProperty() {
        return dateTime;
    }
    
    
    abstract void ajoutFichier(Fichier f);
    
    final void _ajoutFichier(Fichier f) {
        // Utilise super car la version redéfinie renvoie un immuable
        super.getChildren().add(f);
    }
    
    List<Fichier> getContent() {
        return getChildren().stream().map(ti -> ti.getValue()).collect(toList());
    }
    
    // Ne pas permettre de modifier la liste des enfants en dehors du modèle
    // par une référence de type TreeItem.
    @Override
    public final ObservableList<TreeItem<Fichier>> getChildren() {
        return FXCollections.unmodifiableObservableList(super.getChildren());
    }

    // Folder devra binder size et dateTime au résultat d'un calcul
    final void bindSizeTo(ObservableValue<Long> value) {
        size.bind(value);
    }

    final void bindDateTimeTo(ObservableValue<LocalDateTime> value) {
        dateTime.bind(value);
    }
  
    @Override
    public String toString() {
        return formatAffichage(0);
    }
    
    public abstract String getTextArea();
    public abstract void setTextArea(String text);
    public abstract Iterable<Fichier> fichiers();
}
