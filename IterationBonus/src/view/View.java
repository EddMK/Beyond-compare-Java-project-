package view;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import static javafx.geometry.Orientation.VERTICAL;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
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
    private final HBox boxButtonUp = new HBox();
    private final HBox boxButtonDown = new HBox();
    private final Button all= new Button("All");
    private final ToggleButton newerLeft= new ToggleButton("NewerLeft");
    private final ToggleButton newerRight= new ToggleButton("NewerRight");
    private final ToggleGroup groupNewer = new ToggleGroup();
    private final ToggleButton orphanb= new ToggleButton("Orphan");
    private final ToggleButton sameb= new ToggleButton("Same");
    private final ToggleButton folder= new ToggleButton("Folders Only");
    private final Button move= new Button("Move  Left  ->  Right");
    Image imageOk = new Image(getClass().getResourceAsStream("folder-icon.png"));
    private final  Separator separator = new Separator(VERTICAL);
    private final  Separator separator2 = new Separator(VERTICAL);
    private final  Separator separator3 = new Separator(VERTICAL);
    private final Button button = new Button("", new ImageView(imageOk));
    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private final DirectoryChooser directoryChooser2 = new DirectoryChooser();
    private final Button button2 = new Button("", new ImageView(imageOk));
    private final Text text1 = new Text();
    private final Text text2 = new Text();
    private final TreeTableView<Fichier> treeTableView = new TreeTableView<>();
    private final TreeTableView<Fichier> treeTableView2 = new TreeTableView<>() ;
    
    public View(Stage primaryStage, ViewModel viewModel) throws IOException {
        new EditView(primaryStage, viewModel.getEditVM());
        this.viewModel = viewModel;
        configDataBindings();
        configAllListener();
        
        viewModel.selectedFileLeftProperty().bind(treeTableView.getSelectionModel().selectedItemProperty());
        viewModel.selectedFileRightProperty().bind(treeTableView2.getSelectionModel().selectedItemProperty());
        
        treeTableView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                viewModel.openSelectedLeftFile();
            }
        });
        
        treeTableView2.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                viewModel.openSelectedRightFile();
            }
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
        /*
        // Remarquez le type spécifique à la colonne pour le 2e paramètre générique
        TreeTableColumn<AbstractFile, String> nameCol = new TreeTableColumn<>("Nom");
        TreeTableColumn<AbstractFile, Long> sizeCol = new TreeTableColumn<>("Taille");
        TreeTableColumn<AbstractFile, LocalDateTime> timeCol = new TreeTableColumn<>("Date Modif");

        // Les méthodes d'AbstractFile doivent respecter la convention de nommage:
        // getName() et nameProperty(), getSize() et sizeProperty()...
        nameCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        sizeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("size"));
        timeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("dateTime"));

        nameCol.setCellFactory(column -> { return new ElemCell<>(); });
        sizeCol.setCellFactory(column -> { return new ElemCell<>(); });
        timeCol.setCellFactory(column -> { return new ElemDateTimeCell(); });
        */
        TreeTableColumn<Fichier, String> nameCol = new TreeTableColumn<>("Nom");
        TreeTableColumn<Fichier, Fichier> typeCol = new TreeTableColumn<>("Type");
        TreeTableColumn<Fichier, LocalDateTime> dateCol = new TreeTableColumn<>("Date modif");
        TreeTableColumn<Fichier, Long> sizeCol = new TreeTableColumn<>("Taille");
        
        nameCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("nom"));
        sizeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("size"));
        typeCol.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));//vaut mieux refaire comme avant
        dateCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("dateTime"));
        
        nameCol.setPrefWidth(250);
        
        nameCol.setCellFactory(column -> { return new ElemCell<>(); });
        sizeCol.setCellFactory(column -> { return new ElemCell<>(); });
        typeCol.setCellFactory((param) -> { return new TypeFichierCell(); });//pareil
        dateCol.setCellFactory(column -> { return new ElemDateTimeCell(); });
        
        TreeTableColumn<Fichier, String> nameCol2 = new TreeTableColumn<>("Nom");
        TreeTableColumn<Fichier, Fichier> typeCol2 = new TreeTableColumn<>("Type");
        TreeTableColumn<Fichier, LocalDateTime> dateCol2 = new TreeTableColumn<>("Date modif");
        TreeTableColumn<Fichier, Long> sizeCol2 = new TreeTableColumn<>("Taille");
        
        nameCol2.setCellValueFactory(new TreeItemPropertyValueFactory<>("nom"));
        sizeCol2.setCellValueFactory(new TreeItemPropertyValueFactory<>("size"));
        typeCol2.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getValue()));//vaut mieux refaire comme avant
        dateCol2.setCellValueFactory(new TreeItemPropertyValueFactory<>("dateTime"));
        
        nameCol2.setPrefWidth(250);
        
        nameCol2.setCellFactory(column -> { return new ElemCell<>(); });
        sizeCol2.setCellFactory(column -> { return new ElemCell<>(); });
        typeCol2.setCellFactory((param) -> { return new TypeFichierCell(); });//pareil
        dateCol2.setCellFactory(column -> { return new ElemDateTimeCell(); });
       
        
        
        
        treeTableView.getColumns().setAll(nameCol, sizeCol,typeCol,dateCol);
        treeTableView.setShowRoot(false);
        treeTableView.setPrefWidth(550);
        treeTableView2.getColumns().setAll(nameCol2, sizeCol2,typeCol2,dateCol2);
        treeTableView2.setShowRoot(false);
        treeTableView2.setPrefWidth(550);
        
        button.setOnAction(e -> {
            File selectedDirectory = directoryChooser.showDialog(primaryStage);
            if(selectedDirectory != null){
                text1.textProperty().setValue(selectedDirectory.getAbsolutePath());
            }
        });
        button2.setOnAction(e -> {
            File selectedDirectory = directoryChooser2.showDialog(primaryStage);
            if(selectedDirectory != null){
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
        groupNewer.getToggles().add(newerLeft);
        groupNewer.getToggles().add(newerRight);
        boxButtonUp.getChildren().addAll(all,separator,newerLeft,newerRight,separator2,orphanb,sameb,separator3,folder);
        boxButtonUp.setSpacing(10);
        boxButtonUp.setPadding(new Insets(5, 5, 0, 5));
        boxButtonUp.setAlignment(Pos.CENTER);
        
        boxButtonDown.getChildren().addAll(move);
        boxButtonDown.setSpacing(10);
        boxButtonDown.setPadding(new Insets(5, 5, 0, 5));
        boxButtonDown.setAlignment(Pos.CENTER);
        
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
        
        
        root.getChildren().addAll(boxButtonUp,boxTable,boxEtats,boxButtonDown);
        
        
        
        
        //configBindings();
        getChildren().addAll(boxButtonUp,boxTable,boxEtats,boxButtonDown);
        Scene scene = new Scene(this, 1100, 450);
        primaryStage.setTitle("Beyond Compare");
        primaryStage.setScene(scene);
    }
    
    public void configDataBindings() throws IOException{
        text1.textProperty().bindBidirectional(viewModel.pathLeftProperty());
        text2.textProperty().bindBidirectional(viewModel.pathRightProperty());
        treeTableView.rootProperty().bindBidirectional(viewModel.fichierGaucheProperty());
        treeTableView2.rootProperty().bindBidirectional(viewModel.fichierDroiteProperty());
        newerLeft.selectedProperty().bindBidirectional(viewModel.newerLeftSelectedProperty());
        newerRight.selectedProperty().bindBidirectional(viewModel.newerRightSelectedProperty());
        orphanb.selectedProperty().bindBidirectional(viewModel.orphanSelectedProperty());
        sameb.selectedProperty().bindBidirectional(viewModel.sameSelectedProperty());
        folder.selectedProperty().bindBidirectional(viewModel.foldersOnlySelectedProperty());
        move.disableProperty().bindBidirectional(viewModel.moveDisableProperty());
    }
    
    private void configAllListener() {
        all.setOnAction((ActionEvent event) -> {
            try {
                viewModel.all();
            } catch (IOException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
}
