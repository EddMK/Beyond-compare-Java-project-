/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.geometry.Insets;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 *
 * @author kuo
 */
public class Programme extends Application{
    
    private final VBox root = new VBox();
    private final VBox tableau1 = new VBox();
    private final VBox tableau2 = new VBox();
 
    private final HBox boxEtats = new HBox();   
    private final HBox boxTable = new HBox();
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        Path base1 = Paths.get("TestBC/RootBC_Left");
        Path base2 = Paths.get("TestBC/RootBC_Right");
              
        Fichier Left = new Dossier("TestBC/RootBC_Left", 'D',base1,true);
        Execution.recursif(base1,Left, base2);
        
        Fichier Right = new Dossier("TestBC/RootBC_Right", 'D',base2,true);
        Execution.recursif(base2,Right, base1);
        
        String tablePath1 = base1.toAbsolutePath().toString();
        String tablePath2 = base2.toAbsolutePath().toString();
        
        TextFlow flow = new TextFlow();

        Text text1=new Text(tablePath1);
        text1.setStyle("-fx-font-weight: bold");

        flow.getChildren().addAll(text1);
        
        TextFlow flow2 = new TextFlow();

        Text text2 =new Text(tablePath2);
        text2.setStyle("-fx-font-weight: bold");
        
        flow2.getChildren().addAll(text2);
        
        HBox path1 = new HBox();
        path1.getChildren().addAll(flow);
        path1.setAlignment(Pos.CENTER);
        
        HBox path2 = new HBox();
        path2.getChildren().addAll(flow2);
        path2.setAlignment(Pos.CENTER);
        
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
        treeTableView.setShowRoot(false);
        treeTableView.setPrefWidth(550);
        treeTableView2.getColumns().setAll(nameCol2, sizeCol2,typeCol2,dateCol2);
        treeTableView2.setShowRoot(false);
        treeTableView2.setPrefWidth(550);
        
        Label orphan = new Label(Etat.ORPHAN.name());
        orphan.setStyle("-fx-text-fill: blueviolet;");
        Label same = new Label(Etat.SAME.name());
        same.setStyle("-fx-text-fill: orange;");
        Label partial = new Label(Etat.PARTIAL_SAME.name());
        partial.setStyle("-fx-text-fill: green;");
        Label newer = new Label(Etat.NEWER.name());
        newer.setStyle("-fx-text-fill: red;");
        Label older= new Label(Etat.OLDER.name());
        older.setStyle("-fx-text-fill: black;");
        boxEtats.getChildren().addAll(orphan,same,partial,newer,older);  
        
        boxEtats.setSpacing(10);
        boxEtats.setPadding(new Insets(5, 5, 5, 5));
        boxEtats.setAlignment(Pos.CENTER);
        
        
        tableau1.getChildren().addAll(path1, treeTableView);
        tableau2.getChildren().addAll(path2, treeTableView2);
        
        boxTable.getChildren().addAll(tableau1,tableau2);
        boxTable.setSpacing(10);
        boxTable.setPadding(new Insets(5, 5, 0, 5));
        boxTable.setAlignment(Pos.CENTER);
        
        
        root.getChildren().addAll(boxTable,boxEtats);
        Scene scene = new Scene(root, 1100, 450);

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
