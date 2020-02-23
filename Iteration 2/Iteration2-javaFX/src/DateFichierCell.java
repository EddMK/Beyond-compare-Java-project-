
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pseudo
 */
public class DateFichierCell extends FichierCell{
    
            String texte(Fichier elem) {
                try {
                    return ""+elem.lastModificationTime(elem.path());
                } catch (IOException ex) {
                    Logger.getLogger(DateFichierCell.class.getName()).log(Level.SEVERE, null, ex);
                }
                return "problème";
    }
    
}
