/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kuo
 */
public class NomFichierCell extends FichierCell{
    
    @Override
    String texte(Fichier elem) {
        return elem.nom();
    }
    
}
