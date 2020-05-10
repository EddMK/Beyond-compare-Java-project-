/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author kuo
 */

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableRow;
import model.Etat;
import model.Fichier;

public class ElemCell<T> extends TreeTableCell<Fichier, T> {

    public ElemCell() {
        getStylesheets().add("css/cssView.css");
    }

    @Override
    public void updateItem(T elem, boolean isEmpty) {
        super.updateItem(elem, isEmpty);

        // Lignes vides (sans contenu)
        if (isEmpty || elem == null) {
            setText("");
            return;
        }
        
        setText(texte(elem));
        
        // Coloriage des lignes
        TreeTableRow<Fichier> currentRow = getTreeTableRow();
        TreeItem<Fichier> treeItem = currentRow.treeItemProperty().getValue();
        if (treeItem == null) {
            return;
        }
        Fichier f = treeItem.getValue();
        getStyleClass().set(0, f.type()=='D' ? "DOSSIER" : "FICHIER");
        
        if(f.etat()==Etat.ORPHAN ){
            this.getStyleClass().set(0,  "ORPHAN");
        }else if(f.etat()==Etat.SAME){
            this.getStyleClass().set(0,  "SAME");
        }else if(f.etat()==Etat.PARTIAL_SAME){
            this.getStyleClass().set(0,  "PARTIAL_SAME");
        }else if(f.etat()==Etat.NEWER){
            this.getStyleClass().set(0,  "NEWER");
        }else if(f.etat()==Etat.OLDER){
            this.getStyleClass().set(0,  "OLDER");
        }
    }

    String texte(T elem) {
        return "" + elem;
    }

}
