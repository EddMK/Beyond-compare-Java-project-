/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TreeItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author kuo
 */
public class Model {
    
    private StringProperty pathGauche = new SimpleStringProperty("TestBC/RootBC_Left");
    private StringProperty pathDroite = new SimpleStringProperty("TestBC/RootBC_Right");
    private Path base1 = Paths.get(pathGauche.get()) ;
    private Path base2 = Paths.get(pathDroite.get()) ;
    private Fichier Gauche = new Dossier(pathGauche.get(), 'D',base1,true);
    private Fichier Droite = new Dossier(pathDroite.get(), 'D',base2,true);
    private Fichier gaucheModif;
    private Fichier droitModif;
    
    public Model() throws IOException{
        base1 = base1.toAbsolutePath();
        base2 = base2.toAbsolutePath();
        init();
    }
    
    public void init() throws IOException{
        buildTree(base1,Gauche, base2,base1);
        buildTree(base2,Droite, base1,base2);
    }
    
    public void modif(String newPathGauche, String newPathDroite) throws IOException{
        pathGauche.set(newPathGauche);
        pathDroite.set(newPathDroite);
        base1 = Paths.get(newPathGauche);
        base2 = Paths.get(newPathDroite);
        
        
        Droite = new Dossier(newPathDroite, 'D',base2,true);
        buildTree(base2,Droite, base1,base2);
        
        Gauche = new Dossier(newPathGauche, 'D',base1,true);
        buildTree(base1,Gauche, base2, base1);
        
        
    }
    
    public StringProperty pathLeftProperty(){
        return this.pathGauche;
    }
    
    public StringProperty pathRightProperty(){
        return this.pathDroite;
    }
  
    public TreeItem<Fichier> getLeft() throws IOException{
        return Gauche;
    }
    
    public TreeItem<Fichier> getRight() throws IOException{
        return Droite;
    }
    /*
    public void boutons(boolean newerLeft, boolean newerRight, boolean orphan, boolean same, boolean folders){
        gaucheModif = new Dossier(Gauche.getNom(),Gauche.type(),Gauche.path(),true);
        droitModif = new Dossier(Droite.getNom(),Droite.type(),Droite.path(),true);
        if(newerLeft == true || newerRight == true){
            if(newerLeft){
                    if(orphan== true && same == true){
                        newerOrphanSame(Gauche,gaucheModif,Etat.NEWER,folders);
                        newerOrphanSame(Droite,droitModif,Etat.OLDER,folders);
                    }else if(orphan== true && same == false){
                        newerOrphan(Gauche,gaucheModif,Etat.NEWER,folders);
                        newerOrphan(Droite,droitModif,Etat.OLDER,folders);
                    }else if(orphan== false && same == true){
                        newerSame(Gauche,gaucheModif,Etat.NEWER,folders);
                        newerSame(Droite,droitModif,Etat.OLDER,folders);
                    }else{
                        newer(Gauche,gaucheModif,Etat.NEWER,folders);
                        newer(Droite,droitModif,Etat.OLDER,folders);
                    }  
            }
            if(newerRight){
                if(orphan== true && same == true){
                    newerOrphanSame(Gauche,gaucheModif,Etat.OLDER,folders);
                    newerOrphanSame(Droite,droitModif,Etat.NEWER,folders);
                }else if(orphan== true && same == false){
                    newerOrphan(Gauche,gaucheModif,Etat.OLDER,folders);
                    newerOrphan(Droite,droitModif,Etat.NEWER,folders);
                }else if(orphan== false && same == true){
                    newerSame(Gauche,gaucheModif,Etat.OLDER,folders);
                    newerSame(Droite,droitModif,Etat.NEWER,folders);
                }else{
                    newer(Gauche,gaucheModif,Etat.OLDER,folders);
                    newer(Droite,droitModif,Etat.NEWER,folders);
                }
            }
        }else if(orphan || same){
            if(orphan == true && same == true){
                newerOrphan(Gauche,gaucheModif,Etat.SAME,folders);
                newerOrphan(Droite,droitModif,Etat.SAME,folders);
            }else if(orphan == true && same == false){
                newer(Gauche,gaucheModif,Etat.ORPHAN,folders);
                newer(Droite,droitModif,Etat.ORPHAN,folders);
            }else{
                newer(Gauche,gaucheModif,Etat.SAME,folders);
                newer(Droite,droitModif,Etat.SAME,folders);
            }
        }else if(folders){
            foldersOnly(Gauche,gaucheModif);
            foldersOnly(Droite,droitModif);
        }else{
            gaucheModif=Gauche;
            droitModif=Droite;
        }
    }
 
    public TreeItem<Fichier> getGaucheModif(){
        System.out.println(gaucheModif);
        return gaucheModif;
    }
    
    public TreeItem<Fichier> getDroiteModif(){
        System.out.println(droitModif);
        return droitModif;
    }
    

    public static void newer(Fichier base, Fichier nouveau, Etat e, Boolean folders){
        base.getContent().forEach(se -> {
            if(se.type()=='D'){    
                if(se.getSelected().contains(e)){
                    //(String nom, char type, Path path, Boolean tete)
                    Fichier x = new Dossier(se.getNom(),se.type(),se.path(),false);
                    x.setEtat(se.getValue().etat());
                    x.bindSizeTo(se.sizeProperty().asObject());
                    nouveau.ajoutFichier(x);
                    newer(se,x,e,folders);
                }
            }else{
                //String nom, char type, Path path,int taille, Etat etat
                if(folders == false){
                    if(se.etat()==e){
                        FichierSimple x = new FichierSimple(se.getNom(),se.type(),se.path(),se.getSize(),se.getDateTime(),se.etat(),se.getTextArea());
                        x.setEtat(se.etat());
                        nouveau.ajoutFichier(x);
                    }
                }
            }
        });   
    }
    
    public static void foldersOnly(Fichier base, Fichier nouveau){
        base.getContent().forEach(se -> {
            if(se.type()=='D'){    
                    //(String nom, char type, Path path, Boolean tete)
                    Fichier x = new Dossier(se.getNom(),se.type(),se.path(),false);
                    x.setEtat(se.etat());
                    x.bindSizeTo(se.sizeProperty().asObject());
                    nouveau.ajoutFichier(x);
                    foldersOnly(se,x);
            }
        });   
    }
    
    
    public static void newerOrphanSame(Fichier base, Fichier nouveau, Etat e, Boolean foldersOnly){
        base.getContent().forEach(se -> {
            if(se.type()=='D'){    
                if(se.getSelected().contains(e) || se.getSelected().contains(Etat.ORPHAN) || se.getSelected().contains(Etat.SAME)){
                    //(String nom, char type, Path path, Boolean tete)
                    Fichier x = new Dossier(se.getNom(),se.type(),se.path(),false);
                    x.setEtat(se.etat());
                    x.bindSizeTo(se.sizeProperty().asObject());
                    nouveau.ajoutFichier(x);
                    newerOrphanSame(se,x,e,foldersOnly);
                }
            }else{
                //String nom, char type, Path path,int taille, Etat etat
                if(foldersOnly == false){
                    if(se.etat()==e || se.etat()==Etat.ORPHAN || se.etat()==Etat.SAME){
                        FichierSimple x = new FichierSimple(se.getNom(),se.type(),se.path(),se.getSize(),se.getDateTime(),se.etat(),se.getTextArea());
                        x.setEtat(se.etat());
                        nouveau.ajoutFichier(x);
                    }
                }
            }
        });   
    }
    
    public static void newerOrphan(Fichier base, Fichier nouveau, Etat e, Boolean foldersOnly){
        base.getContent().forEach(se -> {
            if(se.type()=='D'){    
                if(se.getSelected().contains(e) || se.getSelected().contains(Etat.ORPHAN)){
                    //(String nom, char type, Path path, Boolean tete)
                    Fichier x = new Dossier(se.getNom(),se.type(),se.path(),false);
                    x.setEtat(se.etat());
                    x.bindSizeTo(se.sizeProperty().asObject());
                    nouveau.ajoutFichier(x);
                    newerOrphan(se,x,e,foldersOnly);
                }
            }else{
                //String nom, char type, Path path,int taille, Etat etat
                if(foldersOnly == false){
                    if(se.etat()==e || se.etat()==Etat.ORPHAN){
                        FichierSimple x = new FichierSimple(se.getNom(),se.type(),se.path(),se.getSize(),se.getDateTime(),se.etat(),se.getTextArea());
                        x.setEtat(se.etat());
                        nouveau.ajoutFichier(x);
                    }
                }
            }
        });   
    }
    
    
    public static void newerSame(Fichier base, Fichier nouveau, Etat e, Boolean foldersOnly){
        base.getContent().forEach(se -> {
            if(se.type()=='D'){    
                if(se.getSelected().contains(e) || se.getSelected().contains(Etat.SAME) ){
                    //(String nom, char type, Path path, Boolean tete)
                    Fichier x = new Dossier(se.getNom(),se.type(),se.path(),false);
                    x.setEtat(se.etat());
                    x.bindSizeTo(se.sizeProperty().asObject());
                    nouveau.ajoutFichier(x);
                    newerSame(se,x,e,foldersOnly);
                }
            }else{
                //String nom, char type, Path path,int taille, Etat etat
                if(foldersOnly == false){
                    if(se.etat()==e || se.etat()==Etat.SAME){
                        FichierSimple x = new FichierSimple(se.getNom(),se.type(),se.path(),se.getSize(),se.getDateTime(),se.etat(),se.getTextArea());
                        x.setEtat(se.etat());
                        nouveau.ajoutFichier(x);
                    }
                }
            }
        });   
    }
    */
    public static void buildTree(Path racine, Fichier source,Path compare, Path pathRoot) throws IOException{
        List<Fichier> fichiers;
        fichiers = new ArrayList<>();
        Path with = compare;
        Path a = pathRoot;
        File file = new File(racine.toString());
        File[] files = file.listFiles();
        for(int i = 0; i<files.length; i ++){
            if(files[i].isDirectory()){
                Fichier x = new Dossier(files[i].getName(), 'D',Paths.get(files[i].getAbsolutePath()),false);
                fichiers.add(x);
                buildTree(files[i].toPath(),x,with,a);               
                source.ajoutFichier(x);               
            }else{
                Path path = files[i].toPath();
                Path aCompare = compare.resolve(a.relativize(path));
                File fileCompare = new File(aCompare.toString());
                Etat etat;              
                if( existFile(aCompare) && fileCompare.isFile() ){
                    int comparaison = lastModificationTime(path).compareTo(lastModificationTime(aCompare));
                    if(comparaison >0){
                        etat =Etat.NEWER;
                    }else if(comparaison == 0){
                        etat =Etat.SAME;
                    }else{
                        etat =Etat.OLDER;
                    }
                }else{
                    etat =Etat.ORPHAN;
                } 
                Path fichierSimple = files[i].toPath();
                String textArea = loadFile(fichierSimple.toString());
                Fichier x = new FichierSimple(files[i].getName(), 'F', fichierSimple,files[i].length(),lastModificationTime(fichierSimple),etat,textArea);
                source.ajoutFichier(new FichierSimple(files[i].getName(), 'F', fichierSimple,files[i].length(),lastModificationTime(fichierSimple),etat,textArea));
                fichiers.add(x);
            }              
        }
        
        int orphan = 0;
        int same = 0;
        int newer = 0;
        int older = 0;
        
        for(int i = 0; i< fichiers.size();i++){
            if(fichiers.get(i).etat() == Etat.ORPHAN){
                orphan ++;
            }else if(fichiers.get(i).etat() == Etat.SAME){
                same ++;
            }else if(fichiers.get(i).etat() == Etat.NEWER){
                newer ++;
            }else if(fichiers.get(i).etat() == Etat.OLDER){
                older ++;
            }
        }
        
        
        Etat etat;
        //ici on compare pr les états 
        
        int nombrefichiers = fichiers.size();
        
        if(orphan == nombrefichiers){
            etat = Etat.ORPHAN;
        }else if(same == nombrefichiers){
            etat = Etat.SAME;
        }else if(nombrefichiers-newer == same){
            etat = Etat.NEWER;
        }else if(nombrefichiers-older == same){
            etat = Etat.OLDER;
        }else{
            etat = Etat.PARTIAL_SAME;
        }
        
        source.setEtat(etat);
        
        
        List<Etat> selection = new ArrayList<>();
        for(int i = 0; i< fichiers.size();i++){
            if(fichiers.get(i).type()=='F'){
                selection.add(fichiers.get(i).etat());
            }
            else{
                for(Etat e : fichiers.get(i).getSelected()){
                    if(!selection.contains(e)){
                        selection.add(e);
                    }
                }
            }
        }
        source.setSelected(selection);
    } 
    
    private static String loadFile(String fileName) {
        Path path = Paths.get(fileName);
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException ex) {
            return "";
        }
    }
    
    public static  boolean existFile(Path chemin){
        File comparateur = new File(chemin.toString());
        return comparateur.exists();
    }

    static LocalDateTime lastModificationTime(Path path) throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
        LocalDateTime result = attrs.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> dir = Files.newDirectoryStream(path)) {
                for (Path p : dir) {
                    LocalDateTime tmp = lastModificationTime(p);
                    if (tmp.isAfter(result)) {
                        result = tmp;
                    }
                }
            }
        }
        return result;
    }

    public void bindModif(Fichier f,String text, int size) {
        if(f.type()=='F'){
            f.setSize(size);//renvoyer la nouvelle taille
            f.setDateTime(LocalDateTime.now());
            f.setTextArea(text);
        }
    }
    
    public TreeItem<Fichier> getRootLeft(boolean newerLeft, boolean newerRight, boolean orphan, boolean same, boolean folders) {
        TreeItem<Fichier> getLeft = new TreeItem<>();
        if(newerLeft == true || newerRight == true){
            if(newerLeft){
                    if(orphan== true && same == true){
                        getLeft = getStateWithOrphanSame(Gauche,Etat.NEWER,folders);
                    }else if(orphan== true && same == false){
                        getLeft =  getStateWithOrphan(Gauche,Etat.NEWER,folders);
                    }else if(orphan== false && same == true){
                        getLeft =  getStateWithSame(Gauche,Etat.NEWER,folders);
                    }else{
                        getLeft =  getOnlyOneState(Gauche,Etat.NEWER,folders);
                    }  
            }
            if(newerRight){
                if(orphan== true && same == true){
                    getLeft =  getStateWithOrphanSame(Gauche,Etat.OLDER,folders);
                }else if(orphan== true && same == false){
                    getLeft =  getStateWithOrphan(Gauche,Etat.OLDER,folders);
                }else if(orphan== false && same == true){
                    getLeft =  getStateWithSame(Gauche,Etat.OLDER,folders);
                }else{
                    getLeft =  getOnlyOneState(Gauche,Etat.OLDER,folders);
                }
            }
        }else if(orphan || same){
            if(orphan == true && same == true){
                getLeft =  getStateWithOrphan(Gauche,Etat.SAME,folders);
            }else if(orphan == true && same == false){
                getLeft =  getOnlyOneState(Gauche,Etat.ORPHAN,folders);
            }else{
                getLeft =  getOnlyOneState(Gauche,Etat.SAME,folders);
            }
        }else if(folders){
            getLeft =  getOnlyFolders(Gauche);
        }else{
            getLeft =  Gauche;
        }
        return  getLeft ;
    }
    
    
    public TreeItem<Fichier> getRootRight(boolean newerLeft, boolean newerRight, boolean orphan, boolean same, boolean folders) {
        TreeItem<Fichier> getRight = new TreeItem<Fichier>();
        if(newerLeft == true || newerRight == true){
            if(newerLeft){
                    if(orphan== true && same == true){
                        getRight = getStateWithOrphanSame(Droite,Etat.OLDER,folders);
                    }else if(orphan== true && same == false){
                        getRight =  getStateWithOrphan(Droite,Etat.OLDER,folders);
                    }else if(orphan== false && same == true){
                        getRight =  getStateWithSame(Droite,Etat.OLDER,folders);
                    }else{
                        getRight =  getOnlyOneState(Droite,Etat.OLDER,folders);
                    }  
            }
            if(newerRight){
                if(orphan== true && same == true){
                    getRight =  getStateWithOrphanSame(Droite,Etat.NEWER,folders);
                }else if(orphan== true && same == false){
                    getRight =  getStateWithOrphan(Droite,Etat.NEWER,folders);
                }else if(orphan== false && same == true){
                    getRight =  getStateWithSame(Droite,Etat.NEWER,folders);
                }else{
                    getRight =  getOnlyOneState(Droite,Etat.NEWER,folders);
                }
            }
        }else if(orphan || same){
            if(orphan == true && same == true){
                getRight =  getStateWithOrphan(Droite,Etat.SAME,folders);
            }else if(orphan == true && same == false){
                getRight =  getOnlyOneState(Droite,Etat.ORPHAN,folders);
            }else{
                getRight =  getOnlyOneState(Droite,Etat.SAME,folders);
            }
        }else if(folders){
            getRight =  getOnlyFolders(Droite);
        }else{
            getRight =  Droite;
        }
        return  getRight ;
    }
    
    
    private TreeItem<Fichier> getOnlyFolders(Fichier dossier) {
        TreeItem<Fichier> result = new TreeItem<>(dossier);
        result.setExpanded(true);
        dossier.getContent().stream().filter(f -> f.type()=='D').forEachOrdered((f) -> {
            result.getChildren().add(getOnlyFolders(f));
        });
        return result;
    }
    
    private TreeItem<Fichier> getStateWithOrphanSame(Fichier dossier, Etat e, Boolean foldersOnly){
        TreeItem<Fichier> result = new TreeItem<>(dossier);
        result.setExpanded(true);
        dossier.getContent().forEach(se -> {
            if(se.type()=='D'){    
                if(se.getSelected().contains(e) || se.getSelected().contains(Etat.ORPHAN) || se.getSelected().contains(Etat.SAME)){
                    result.getChildren().add(getStateWithOrphanSame(se,e,foldersOnly));
                }
            }else{
                if(foldersOnly == false){
                    if(se.etat()==e || se.etat()==Etat.ORPHAN || se.etat()==Etat.SAME){
                        result.getChildren().add(se);
                    }
                }
            }
        }); 
        return result;
    }
    
    private TreeItem<Fichier> getStateWithOrphan(Fichier dossier,Etat e, boolean foldersOnly){
        TreeItem<Fichier> result = new TreeItem<>(dossier);
        result.setExpanded(true);
        dossier.getContent().forEach(se -> {
            if(se.type()=='D'){    
                if(se.getSelected().contains(e) || se.getSelected().contains(Etat.ORPHAN)){
                    result.getChildren().add(getStateWithOrphan(se,e,foldersOnly));
                }
            }else{
                if(foldersOnly == false){
                    if(se.etat()==e || se.etat()==Etat.ORPHAN){
                        result.getChildren().add(se);
                    }
                }
            }
        }); 
        return result;
    }
    
    private TreeItem<Fichier> getStateWithSame(Fichier dossier,Etat e, boolean foldersOnly){
        TreeItem<Fichier> result = new TreeItem<>(dossier);
        result.setExpanded(true);
        dossier.getContent().forEach(se -> {
            if(se.type()=='D'){    
                if(se.getSelected().contains(e) || se.getSelected().contains(Etat.SAME)){
                    result.getChildren().add(getStateWithSame(se,e,foldersOnly));
                }
            }else{
                if(foldersOnly == false){
                    if(se.etat()==e || se.etat()==Etat.SAME){
                        result.getChildren().add(se);
                    }
                }
            }
        }); 
        return result;
    }
    
    private TreeItem<Fichier> getOnlyOneState(Fichier dossier,Etat e, boolean foldersOnly){
        TreeItem<Fichier> result = new TreeItem<>(dossier);
        result.setExpanded(true);
        dossier.getContent().forEach(se -> {
            if(se.type()=='D'){    
                if(se.getSelected().contains(e)){
                    result.getChildren().add(getOnlyOneState(se,e,foldersOnly));
                }
            }else{
                if(foldersOnly == false){
                    if(se.etat()==e){
                        result.getChildren().add(se);
                    }
                }
            }
        }); 
        return result;
    }
}
