
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pseudo
 */
public class TreeTableView extends Application{
    @Override
    public void start(Stage primaryStage) throws IOException {
        Fichier racine = buildTree();
        javafx.scene.control.TreeTableView<Fichier> treeTableView = new javafx.scene.control.TreeTableView<>(makeTreeRoot(racine));
        
        TreeTableColumn<Fichier, Fichier> 
                nameCol = new TreeTableColumn<>("Nom"),
                sizeCol = new TreeTableColumn<>("Taille"),
                typeCol=new TreeTableColumn<>("type"),
                dateCol=new TreeTableColumn<>("date modif");
        
        nameCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        sizeCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        typeCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        dateCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        
        nameCol.setPrefWidth(250);
        nameCol.setCellFactory((param) -> {
            return new NomFichierCell();
        });
        sizeCol.setCellFactory((param) -> {
            return new TailleFichierCell();
        });
        typeCol.setCellFactory((param) -> {
            return new TypeFichierCell();
        });
        dateCol.setCellFactory((param) -> {
            return new DateFichierCell();
        });
        
        treeTableView.getColumns().setAll(nameCol, sizeCol,typeCol,dateCol);
        StackPane root = new StackPane();
        root.getChildren().add(treeTableView);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("TreeTableView");
        primaryStage.setScene(scene);
        primaryStage.show();
                
        
    }
     public static void main(String[] args) {
        launch(args);
    }
    public TreeItem<Fichier> makeTreeRoot(Fichier root) {

        TreeItem<Fichier> res = new TreeItem<>(root);
        res.setExpanded(true);
        if (root.type()=='D') {
            root.fichiers().forEach(se -> {
                res.getChildren().add(makeTreeRoot(se));
            });
        }
        
        return res;
    }
    public static Fichier buildTree() throws IOException {
        Path base1 = Paths.get("TestBC/RootBC_Left");
        Path base2 = Paths.get("TestBC/RootBC_Right");
        Fichier Left = new Dossier("TestBC/RootBC_Left", 'D',base1,true);
        Execution.recursif(base1,Left, base2);
        return Left;
    }
    
}
