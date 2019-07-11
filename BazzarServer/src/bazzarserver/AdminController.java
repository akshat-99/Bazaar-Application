
package bazzarserver;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;



public class AdminController implements Initializable, Runnable  {
    Server server;
    Thread t2;      //admincontroller thread
    boolean isrunning;
    double xOffset,yOffset;
    @FXML
    private RadioButton StatusButton;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private ImageView close;
    @FXML
    private ImageView userDetails;
    @FXML
    private ImageView add;
    @FXML
    private ImageView edit;
    @FXML
    private ImageView discount;
    @FXML
    private ImageView salesRecord;
    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane rootAnchor;
    @FXML
    private ImageView min;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //StatusButton.setSelectedEditable(false);
       
    }    

    @FXML
    private void startButtonAction(ActionEvent event) {
        
          StatusButton.setSelected(true);
          StatusButton.getStyleClass().add("-fx-mark-highlight-color: #bbbfbb ;-fx-mark-color: #bbbfbb ;");
        try {
//            server =new Server();
//            server.Start();
                
                t2=new Thread(this);
                t2.start();
            
            System.out.println("server started in admin controller");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"unable to start server");
    }
    }
    @FXML  

    private void stopButtonAction(ActionEvent event) throws Exception {
//       // server.flag=0;
//         //System.exit(0);
////        thread.destroy();
//            
////             if(isrunning==true)
////             {
////              thread.sleep(100000000);
////             isrunning=false;
////             }
//         server.k=1;   
//         //server.Stop();
//         System.out.println("in admin server closed");
//         StatusButton.setSelected(false);
//         StatusButton.getStyleClass().add("-fx-mark-highlight-color:#FF0000;-fx-mark-color:#FF0000;");
          closeStage();
          loadMain("/bazzarserver/admin.fxml");

    }

    @FXML
    private void close(MouseEvent event) {
        System.exit(0);
    }

    @Override
    public void run() {
        try {
            System.out.println("run() 1");
            server =new Server();
            if(isrunning==false)
            server.Start();
            isrunning=true;
            
        } catch (IOException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            
            System.out.println(" server not started");
            
        }
        
    }

    @FXML
    private void userDetails(MouseEvent event) {
        closeStage();
        loadMain("/bazzarserver/userdetails.fxml");
        
    }

    @FXML
    private void add(MouseEvent event) {
        closeStage();
        loadMain("/bazzarserver/addProduct.fxml");
    }
    

    @FXML
    private void edit(MouseEvent event) {
         closeStage();
        loadMain("/bazzarserver/editProduct.fxml");
    }

    @FXML
    private void discount(MouseEvent event) {
        closeStage();
        loadMain("/bazzarserver/offer.fxml");
    }

    @FXML
    private void salesRecord(MouseEvent event) {
        closeStage();
        loadMain("/bazzarserver/sales.fxml");
    }
    
    
     private void closeStage(){
        ((Stage)startButton.getScene().getWindow()).close();
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

    @FXML
    private void min(MouseEvent event) {
        ((Stage)startButton.getScene().getWindow()).setIconified(true);
        // ((Stage)((startButton).getScene().getWindow()).setIconified(true);
    }
    
}
