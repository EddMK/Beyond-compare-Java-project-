
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
        //Scanner scan = new Scanner(System.in);
        //System.out.println("Entrez");
      
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
                //existFile(files[i].toPath(),compare);
                /*le chemin du files[i],le chemin du compare,
                Path path = files[i].toPath();
                Path parti = path.subpath(2,path.getNameCount() );
                //System.out.println((path.subpath(2,path.getNameCount() )).toString());
                Path aCompare = compare.resolve(parti);
                File comparateur = new File(aCompare.toString());*/
                Path path = files[i].toPath();
                Path aCompare = compare.resolve(path.subpath(2,path.getNameCount() ));
                File fileCompare = new File(aCompare.toString());
                Etat etat;
                if( existFile(path,compare) && fileCompare.isFile() ){
                    int comparaison = lastModificationTime(path).compareTo(lastModificationTime(aCompare));
                    if(comparaison == 1){
                        etat =Etat.NEWER;
                    }else if(comparaison == 0){
                        etat =Etat.SAME;
                    }else{
                        etat =Etat.OLDER;
                    }
                }else{
                    etat =Etat.ORPHAN;
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
