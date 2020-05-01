package view;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Fichier;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kuo
 */
public class DateModifFichierCell extends FichierCell {

    @Override
    String texte(Fichier elem) {
        try {
            return ""+Fichier.lastModificationTime(elem.path());
        } catch (IOException ex) {
            Logger.getLogger(DateModifFichierCell.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
