package view;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.Etat;
import model.Fichier;
import viewmodel.ViewModel;

/**
 *
 * @author kuo
 */
public class View extends VBox{
    
    private final VBox root = new VBox();
    private final ViewModel viewModel;
    private final VBox tableau1 = new VBox();
    private final VBox tableau2 = new VBox();
    private final HBox boxEtats = new HBox();
    private final HBox boxFolderPath1 = new HBox();
    private final HBox boxFolderPath2 = new HBox();
    private final HBox boxTable = new HBox();
    private final HBox boxBouton = new HBox();
    private final Button all= new Button("All");
    private final Button newerLeft= new Button("NewerLeft");
    private final Button newerRight= new Button("NewerRight");
    private final Button orphanb= new Button("Orphan");
    private final Button sameb= new Button("Same");
    private final Button folder= new Button("Folder");
    Image imageOk = new Image(getClass().getResourceAsStream("folder-icon.png"));
    private final Button button = new Button("", new ImageView(imageOk));
    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private final DirectoryChooser directoryChooser2 = new DirectoryChooser();
    private final Button button2 = new Button("", new ImageView(imageOk));
    private final Text text1 = new Text();
    private final Text text2 = new Text();
    private final TreeTableView<Fichier> treeTableView = new TreeTableView<>();
    private final TreeTableView<Fichier> treeTableView2 = new TreeTableView<>() ;
    
    private final ObjectProperty<TreeItem<Fichier>> FichierGauche = new SimpleObjectProperty();
    private final ObjectProperty<TreeItem<Fichier>> FichierDroite = new SimpleObjectProperty();
    
    public View(Stage primaryStage, ViewModel viewModel) throws IOException {
        this.viewModel = viewModel;
        configDataBindings();
        configViewModelBindings();
        
        treeTableView.rootProperty().addListener((o,old,newValue) ->{
            System.out.println("Bien été changé !");
        });
        
        TextFlow flow = new TextFlow();
        text1.setStyle("-fx-font-weight: bold");
        flow.getChildren().addAll(text1);
        
        TextFlow flow2 = new TextFlow();
        text2.setStyle("-fx-font-weight: bold");    
        flow2.getChildren().addAll(text2);
        
        HBox path1 = new HBox();
        path1.getChildren().addAll(flow);
        path1.setAlignment(Pos.CENTER);
        
        HBox path2 = new HBox();
        path2.getChildren().addAll(flow2);
        path2.setAlignment(Pos.CENTER);
        
        
        //treeTableView = new TreeTableView<>();
        //treeTableView2 = new TreeTableView<>();
        
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
        
        button.setOnAction(e -> {
            File selectedDirectory = directoryChooser.showDialog(primaryStage);
            if(selectedDirectory != null){
                //text1.setText(selectedDirectory.getAbsolutePath());
                text1.textProperty().setValue(selectedDirectory.getAbsolutePath());
            }
        });
        button2.setOnAction(e -> {
            File selectedDirectory = directoryChooser2.showDialog(primaryStage);
            if(selectedDirectory != null){
                //text2.setText(selectedDirectory.getAbsolutePath());
                text2.textProperty().setValue(selectedDirectory.getAbsolutePath());
            }
        });
        
        
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
        boxBouton.getChildren().addAll(all,newerLeft,newerRight,orphanb,sameb,folder);
        boxBouton.setSpacing(10);
        boxBouton.setPadding(new Insets(5, 5, 0, 5));
        boxBouton.setAlignment(Pos.CENTER);
        
        boxFolderPath1.getChildren().addAll(path1,button);
        boxFolderPath1.setSpacing(10);
        
        boxFolderPath2.getChildren().addAll(path2,button2);
        boxFolderPath2.setSpacing(10);
        
        
        
        tableau1.getChildren().addAll(boxFolderPath1, treeTableView);
        tableau2.getChildren().addAll(boxFolderPath2, treeTableView2);
        
        boxTable.getChildren().addAll(tableau1,tableau2);
        boxTable.setSpacing(10);
        boxTable.setPadding(new Insets(5, 5, 0, 5));
        boxTable.setAlignment(Pos.CENTER);
        
        
        root.getChildren().addAll(boxBouton,boxTable,boxEtats);
        
        
        
        
        //configBindings();
        getChildren().addAll(boxBouton,boxTable,boxEtats);
        Scene scene = new Scene(this, 1100, 450);
        primaryStage.setTitle("Gestion de texte - MVVM");
        primaryStage.setScene(scene);
    }
    /*
    public static TreeItem<Fichier> makeTreeRoot(Fichier root) {
        TreeItem<Fichier> res = new TreeItem<>(root);
        res.setExpanded(true);
        if (root.type()=='D') {
            root.fichiers().forEach(se -> {
                res.getChildren().add(makeTreeRoot(se));
            });
        }
        return res;
    }*/
    
    public void configDataBindings() throws IOException{
        text1.textProperty().bindBidirectional(viewModel.pathLeftProperty());
        text2.textProperty().bindBidirectional(viewModel.pathRightProperty());
        treeTableView.rootProperty().bindBidirectional(viewModel.fichierGaucheProperty());
        treeTableView2.rootProperty().bindBidirectional(viewModel.fichierDroiteProperty()); 
        //System.out.println(viewModel.fichierGaucheProperty());
    }
    
    public void configViewModelBindings() throws IOException{
        //viewModel.pathLeftProperty().bindBidirectional(text1.textProperty());
        //viewModel.pathRightProperty().bindBidirectional(text2.textProperty());

    }
    
}
