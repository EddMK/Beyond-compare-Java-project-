/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import view.View;
import viewmodel.ViewModel;

/**
 *
 * @author kuo
 */
public class Main extends Application{
    
    @Override
    public void start(Stage primaryStage) throws IOException{
        Model model = new Model();
        ViewModel viewModel = new ViewModel(model);
        View view = new View(primaryStage, viewModel);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
