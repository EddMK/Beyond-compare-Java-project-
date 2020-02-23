
import java.io.IOException;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private final int taille;
    private  String etat;
    

    public FichierSimple(String nom, char type, Path path,int taille, String etat) {
        super(nom,type, path);
        this.taille = taille;
        this.etat=etat;
    }
    
    @Override
    public void setEtat(String newEtat){
        etat=newEtat;
    }

    @Override
    public int taille() {
        return taille;
    }
    
    @Override
    public String etat() {
        return etat;
    }
    
    @Override
    protected String formatAffichage(int decalage) {       
        try{       
            return super.formatAffichage(decalage) + nom() +" " +  type()  + "  "+ 
                    lastModificationTime(path()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))+
                    " "+taille()+" "+etat()+"\n";
        }catch (IOException ex) {
            Logger.getLogger(FichierSimple.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    @Override
    public void ajoutFichier(Fichier f) {
        throw new UnsupportedOperationException("Not supported."); 
    }
}
