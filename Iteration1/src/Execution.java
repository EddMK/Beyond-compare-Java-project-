
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        /*Files.list(Paths.get("TestBC/RootBC_Left/PartialSame/ContentSameFileButOtherFolder"))
                .forEach(System.out::println);*/
        
        /*try (Stream<Path> walk = Files.walk(Paths.get("TestBC"))) {

		List<String> result = walk.filter(Files::isDirectory)
				.map(x -> x.toString()).collect(Collectors.toList());

		result.forEach(System.out::println);

	} catch (IOException e) {
		e.printStackTrace();
	}*/
     /*   
        File f = null;
        String[] paths;
            
      try {    
      
         // create new file
         f = new File("TestBC/RootBC_Left");
                                 
         // array of files and directory
         paths = f.list();
            
         // for each name in the path array
         for(String path:paths) {
         
            // prints filename and directory name
            System.out.println(path);
         }
         
      } catch(Exception e) {
         // if any error occurs
         e.printStackTrace();
      }*/
      /*
      File repertoire = new File("TestBC/RootBC_Left");
      Fichier racine = new Dossier("Racine");
      listeRepertoire(repertoire);*/
      
      Path base = Paths.get("TestBC/RootBC_Left");
      Fichier racine = new Dossier("Racine", 'D',base);
      recursif(base,racine);
      System.out.println(racine);
    }
    
    public static void recursif(Path racine, Fichier source){
        File file = new File(racine.toString());
        File[] files = file.listFiles();
        for(int i = 0; i<files.length; i ++){
            if(files[i].isDirectory()){
                Fichier x = new Dossier(files[i].getName(), 'D',files[i].toPath());
                recursif(files[i].toPath(),x);               
                source.ajoutFichier(x);               
            }else{
                source.ajoutFichier(new FichierSimple(files[i].getName(), 'F', files[i].toPath(),(int) files[i].length()));
            }
        }
    }
    
    
    
    
    
    public static void listeRepertoire ( File repertoire ) {
        //String nom = repertoire.getName();
        System.out.println ( repertoire.getName());
        //Fichier racine = new Dossier(nom);
 
        if ( repertoire.isDirectory ( ) ) {
                File[] list = repertoire.listFiles();
                if (list != null){
	                for ( int i = 0; i < list.length; i++) {
	                        // Appel récursif sur les sous-répertoires
	                        
                                listeRepertoire(list[i]);
                                //Fichier f = new Dossier(list[i].getName());
                                //racine.ajoutFichier(f);
                                
	                } 
                } else {
                	System.err.println(repertoire + " : Erreur de lecture.");
                }
        } 
        //System.out.println(racine);
    }
    
    
    public static void listeRepertoire(File path, List<String> allFiles) {
 
	if (path.isDirectory()) {
	    File[] list = path.listFiles();
	    if (list != null) {
		for (int i = 0; i < list.length; i++) {
		    // Appel récursif sur les sous-répertoires
		    listeRepertoire(list[i], allFiles);
		}
	    } else {
		System.err.println(path + " : Erreur de lecture.");
	    }
	} else {
	    String currentFilePath = path.getAbsolutePath();
	    System.out.println(currentFilePath);
	    allFiles.add(currentFilePath);
	}
    }

}
