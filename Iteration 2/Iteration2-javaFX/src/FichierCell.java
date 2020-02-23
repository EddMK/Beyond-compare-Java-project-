
import javafx.scene.control.cell.TextFieldTreeTableCell;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pseudo
 */
public abstract class FichierCell extends TextFieldTreeTableCell<Fichier, Fichier> {
    private static final String CSSPATH = "cssView.css";

    public FichierCell() {
        getStylesheets().add(CSSPATH);
    }

    @Override
    public void updateItem(Fichier elem, boolean isEmpty) {
        super.updateItem(elem, isEmpty);
        if (elem == null) {
            return;
        }
        this.setText(texte(elem));
        if(elem.etat()=="ORPHAN")
            this.getStyleClass().set(0,  "ORPHAN");
        else if (elem.etat()=="NEWER")
            this.getStyleClass().set(0,  "NEWER");
        else if (elem.etat()=="SAME")
            this.getStyleClass().set(0,  "SAME");   
        else if (elem.etat()=="OLDER")
            this.getStyleClass().set(0,  "OLDER");
        else if (elem.etat()=="PARTIAL_SAME")
            this.getStyleClass().set(0,  "PARTIALSAME");
    }

    abstract String texte(Fichier elem);
    
}
