/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazzarserver;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author KRITANK
 */
public class SplashController implements Initializable {

    @FXML
    private StackPane stackPane;
    double xOffset,yOffset;
    @FXML
    private ImageView imageView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        new SplashScreen().start();
    }    
    
    class SplashScreen extends Thread{
       
        
        
        @Override
        public void run()
        {
            try {
                Thread.sleep(1200);
            } catch (InterruptedException ex) {
                Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
            }
             
             Platform.runLater(new Runnable(){
                 
             @Override
          public void run()
          {
                  /*Parent root=null;
                  root=FXMLLoader.load(getClass().getResource("admin.fxml"));
                  ((Stage)stackPane.getScene().getWindow()).close();
                  Stage stage = new Stage();
                  stage.initStyle(StageStyle.UNDECORATED);
                  stage.setScene(new Scene(root));
                  stage.show();*/
                  closeStage();
                  loadMain("admin.fxml");
          }
             });
           
        }
    }
     private void closeStage(){
        ((Stage)stackPane.getScene().getWindow()).close();
    }
  
    
    //load function sets new page
     void loadMain(String s){
        try{
            Parent root = FXMLLoader.load(getClass().getResource(s));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
                //System.out.println("hii");
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
                //System.out.println("hii");
            }
        });
            stage.setScene(new Scene(root));  
            stage.show();
        }
        catch(Exception e)
        {
            System.out.println("Cant load");
        }
    }
    
}
