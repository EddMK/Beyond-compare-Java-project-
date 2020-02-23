/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javafx.scene.control.cell.TextFieldTreeTableCell;
/**
 *
 * @author kuo
 */
public abstract class FichierCell extends TextFieldTreeTableCell<Fichier, Fichier>{
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
        //this.getStyleClass().set(0, elem.type()=='D'? "DOSSIER" : "FICHIER");
        //this.getStyleClass().set(0, elem.etat()==Etat.ORPHAN ? "ORPHAN":"");
        /*this.getStyleClass().set(1, elem.etat()==Etat.SAME ? "SAME":"");
        this.getStyleClass().set(2, elem.etat()==Etat.PARTIAL_SAME ? "PARTIAL_SAME":"");
        this.getStyleClass().set(3, elem.etat()==Etat.NEWER ? "NEWER":"");
        this.getStyleClass().set(4, elem.etat()==Etat.OLDER ? "OLDER":"");*/
        
        if(elem.etat()==Etat.ORPHAN ){
            this.getStyleClass().set(0,  "ORPHAN");
        }else if(elem.etat()==Etat.SAME){
            this.getStyleClass().set(0,  "SAME");
        }else if(elem.etat()==Etat.PARTIAL_SAME){
            this.getStyleClass().set(0,  "PARTIAL_SAME");
        }else if(elem.etat()==Etat.NEWER){
            this.getStyleClass().set(0,  "NEWER");
        }else if(elem.etat()==Etat.OLDER){
            this.getStyleClass().set(0,  "OLDER");
        }
        
    }

    abstract String texte(Fichier elem);
}
