/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author kuo
 */
public class Programme extends Application{
    
    private final VBox root = new VBox();
    private final HBox boxTable = new HBox();
    private final HBox boxColors = new HBox();
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        Path base1 = Paths.get("TestBC/RootBC_Left");
        Path base2 = Paths.get("TestBC/RootBC_Right");
        
        
        Fichier Left = new Dossier("TestBC/RootBC_Left", 'D',base1,true);
        Execution.recursif(base1,Left, base2);
        
        Fichier Right = new Dossier("TestBC/RootBC_Right", 'D',base2,true);
        Execution.recursif(base2,Right, base1);
        
        TreeTableView<Fichier> treeTableView = new TreeTableView<>(makeTreeRoot(Left));
        TreeTableView<Fichier> treeTableView2 = new TreeTableView<>(makeTreeRoot(Right));
        
        TreeTableColumn<Fichier, Fichier> 
                nameCol = new TreeTableColumn<>("Nom"),
                sizeCol = new TreeTableColumn<>("Taille"),
                typeCol = new TreeTableColumn<>("Type"),
                dateCol = new TreeTableColumn<>("Date Modif");
        
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
            return new DateModifFichierCell();
        });
        
        TreeTableColumn<Fichier, Fichier> 
                nameCol2 = new TreeTableColumn<>("Nom"),
                sizeCol2 = new TreeTableColumn<>("Taille"),
                typeCol2 = new TreeTableColumn<>("Type"),
                dateCol2 = new TreeTableColumn<>("Date Modif");
        
        nameCol2.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        sizeCol2.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        typeCol2.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        dateCol2.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));
        
        nameCol2.setPrefWidth(250);
        
        nameCol2.setCellFactory((param) -> {
            return new NomFichierCell();
        });
        sizeCol2.setCellFactory((param) -> {
            return new TailleFichierCell();
        });
        typeCol2.setCellFactory((param) -> {
            return new TypeFichierCell();
        });
        dateCol2.setCellFactory((param) -> {
            return new DateModifFichierCell();
        });
        
        treeTableView.getColumns().setAll(nameCol, sizeCol,typeCol,dateCol);
        treeTableView2.getColumns().setAll(nameCol2, sizeCol2,typeCol2,dateCol2);
        
        /*
            VBox bpMenu = new VBox();
            bpMenu.getChildren().add(tfMenu);
            bpMenu.getChildren().add(gpButtons);

            BorderPane root = new BorderPane();
            root.setLeft(btnFood);
            root.setCenter(bpMenu);

        */
        
        /*
        FlowPane root = new FlowPane();

        root.getChildren().add(lName);
        */
        
        
        BorderPane boxTable = new BorderPane();
        boxTable.setLeft(treeTableView);
        boxTable.setCenter(treeTableView2);
        //root.setLeft(treeTableView);
        //root.setRight(treeTableView2);
        //root.getChildren().add(treeTableView);

        /*
        boxNumbers.setSpacing(10);
        boxNumbers.setPadding(new Insets(5, 5, 0, 5));
        boxNumbers.setAlignment(Pos.CENTER);
        */
        
        
        
        root.getChildren().addAll(boxTable, boxColors);
        Scene scene = new Scene(root, 1000, 450);

        primaryStage.setTitle("Iteration2");
        primaryStage.setScene(scene);
        primaryStage.show();          
    }
    
    /**
     * @param args the command line arguments
     */
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
    
    public static Fichier buildTree2() throws IOException {
        Path base1 = Paths.get("TestBC/RootBC_Left");
        Path base2 = Paths.get("TestBC/RootBC_Right");
        
        Fichier Right = new Dossier("TestBC/RootBC_Right", 'D',base2,true);
        Execution.recursif(base2,Right, base1);
        
        return Right;
    }
}
