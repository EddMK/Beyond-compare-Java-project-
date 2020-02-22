
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kuo
 */
public abstract class Fichier {
    private final String nom;
    private final char type;
    private final Path path;
    private String etat;
    
    public Fichier(String nom, char type, Path path) {
        this.nom = nom;
        this.type = type;
        this.path = path;
    }
    
    public void setEtat(String newEtat){
        etat = newEtat;
    }
    
    public String etat(){
        return etat;
    }
    
    public char type(){ return type; }
    
    public Path path(){ return path; }
    
    public String nom() { return nom; }
    
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
    
    
    protected String formatAffichage(int decalage) {
        String res = "";
        for (int i = 0; i < decalage; ++i)
            res += "\t";
        return res;
    }
    
    public int compareTo(Fichier f){
        int res;
        res = Character.compare(this.type, f.type);
        return res;
    }
    
    @Override
    public String toString() {
        return formatAffichage(0);
    }
    
    public abstract int taille();
    public abstract void ajoutFichier(Fichier f);
}
