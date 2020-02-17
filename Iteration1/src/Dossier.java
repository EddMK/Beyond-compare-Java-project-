
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
public class Dossier extends Fichier{
    private final List<Fichier> fichiers = new ArrayList<>();
    
    public Dossier(String nom, char type, Path path) {
        super(nom,type, path);
    }

    @Override
    public int taille() {
        int res = 0;
        for (Fichier f : fichiers)
            res += f.taille();
        return res;
    }
    
    @Override
    protected String formatAffichage(int decalage) {
        StringBuilder res = new StringBuilder();
        try {
            res.append(super.formatAffichage(decalage))
                    .append(nom()).append(" ")
                    .append(type()).append(" ")
                    .append(lastModificationTime(path()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                    .append(" ").append(taille())
                    /*.append(" - contient : ").*/
                    .append("\n");
        } catch (IOException ex) {
            Logger.getLogger(Dossier.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Fichier f : fichiers) 
            res.append(f.formatAffichage(decalage + 1));
        return res.toString();
    }

    @Override
    public void ajoutFichier(Fichier f) {
        fichiers.add(f);
    }
}
