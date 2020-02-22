
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


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kuo
 */
public class Execution {
    public static void main(String[] args) throws IOException {
        
      
        Path base1 = Paths.get("TestBC/RootBC_Left");
        Path base2 = Paths.get("TestBC/RootBC_Right");
        
        
        Fichier Left = new Dossier("TestBC/RootBC_Left", 'D',base1,true);
        recursif(base1,Left, base2);
        
        Fichier Right = new Dossier("TestBC/RootBC_Right", 'D',base2,true);
        recursif(base2,Right, base1);
        
        System.out.println(Left);
        System.out.println(Right); 
      
    }
    
    public static void recursif(Path racine, Fichier source,Path compare) throws IOException{
        List<Fichier> fichiers;
        fichiers = new ArrayList<>();
        Path with = compare;
        File file = new File(racine.toString());
        File[] files = file.listFiles();
        for(int i = 0; i<files.length; i ++){
            if(files[i].isDirectory()){
                Fichier x = new Dossier(files[i].getName(), 'D',files[i].toPath(),false);
                fichiers.add(x);
                recursif(files[i].toPath(),x,with);               
                source.ajoutFichier(x);               
            }else{
                
                Path path = files[i].toPath();
                Path aCompare = compare.resolve(path.subpath(2,path.getNameCount() ));
                File fileCompare = new File(aCompare.toString());
                String etat ="";
                if( existFile(path,compare) && fileCompare.isFile() ){
                    int comparaison = lastModificationTime(path).compareTo(lastModificationTime(aCompare));
                    if(comparaison == 1){
                        etat ="NEWER";
                    }else if(comparaison == 0){
                        etat ="SAME";
                    }else{
                        etat ="OLDER";
                    }
                }else{
                    etat ="ORPHAN";
                }                             
                Fichier x = new FichierSimple(files[i].getName(), 'F', files[i].toPath(),(int) files[i].length(),etat);
                source.ajoutFichier(new FichierSimple(files[i].getName(), 'F', files[i].toPath(),(int) files[i].length(),etat));
                fichiers.add(x);
            }              
        }
        
        int orphan = 0;
        int same = 0;
        int newer = 0;
        int older = 0;
        
        for(int i = 0; i< fichiers.size();i++){
            if(fichiers.get(i).etat() == "ORPHAN"){
                orphan ++;
            }else if(fichiers.get(i).etat() == "SAME"){
                same ++;
            }else if(fichiers.get(i).etat() == "NEWER"){
                newer ++;
            }else if(fichiers.get(i).etat() == "OLDER"){
                older ++;
            }
        }
        
        
        String etat = "";
        //ici on compare pr les états 
        
        int nombrefichiers = fichiers.size();
        
        if(orphan == nombrefichiers){
            etat = "ORPHAN";
        }else if(same == nombrefichiers){
            etat = "SAME";
        }else if(nombrefichiers-newer == same){
            etat = "NEWER";
        }else if(nombrefichiers-older == same){
            etat = "OLDER";
        }else{
            etat = "PARTIAL_SAME";
        }
        
        source.setEtat(etat);
        
    } 
    
    public static  boolean existFile(Path chemin,Path compare){
        Path parti = chemin.subpath(2,chemin.getNameCount() );
        Path aCompare = compare.resolve(parti);
        File comparateur = new File(aCompare.toString());
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
    
}
