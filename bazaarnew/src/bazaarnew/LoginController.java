/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazaarnew;

import static alert.Alert.showDialog;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * FXML Controller class
 *
 * @author KRITANK
 */
public class LoginController implements Initializable {

   Socket socket;
   ObjectOutputStream objectOutputStream;
   ObjectInputStream objectInputStream;
   int switchCase;
   String s="";
   int CustId;
   
    double xOffset,yOffset;
    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane rootAnchor;
    @FXML
    private Button loginButton;
    @FXML
    private Font x1;
    @FXML
    private Button resetButton;
    @FXML
    private Button registerButton;
    @FXML
    private ImageView close;
    @FXML
    private JFXTextField Username;
    @FXML
    private JFXPasswordField Password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loginButtonAction(ActionEvent event) {
        Username.setStyle("fx-focus-color:#DC143C; -fx-unfocus-color:#57d057;");
        Password.setStyle("fx-focus-color:#DC143C; -fx-unfocus-color:#57d057;");
        try
        {
             s="No Connection!";
            System.out.println("reached 1\n");
            String sql = "select * from register where Username=? and Password=?";
            String UsernameText=Username.getText();
            //System.out.println(DigestUtils.sha1Hex(Password.getText()).toString());
            String PasswordText=DigestUtils.sha1Hex(Password.getText()).toString();
            //try
            System.out.println("reached 2\n");
            s="Server Not Started Yet!";
            socket=new Socket("localhost",5436);
            System.out.println("socket connected 101");
            objectOutputStream =new ObjectOutputStream(socket.getOutputStream());
            objectInputStream =new ObjectInputStream(socket.getInputStream()); 
            System.out.println("StartServer worked");
            //catch
           
            switchCase=1;
            objectOutputStream.writeObject(switchCase);
            objectOutputStream.writeObject(sql);
            objectOutputStream.writeObject(UsernameText);
            objectOutputStream.writeObject(PasswordText);
            System.out.println("reached 3\n");
            if(Username.getText().equals(""))
            {
              Username.setStyle("fx-focus-color:#DC143C; -fx-unfocus-color:#DC143C;");
            }
            else if(Password.getText().equals(""))
            {
              Password.setStyle("fx-focus-color:#DC143C; -fx-unfocus-color:#DC143C;");
            }
            else{
               int result=(int)objectInputStream.readObject();
               if(result==1)
               {  
                System.out.println("reached 4\n");
                CustId=(int) objectInputStream.readObject();
               
                //setting global CustId which can be further used 
                main.setCustId(CustId);
                System.out.println(main.globalCustId);
                
                System.out.println("user verified"+ CustId);
                 closeStage();
                 loadMain("/bazaarnew/home.fxml");
               }
               else if(result==0)
               {
               //JOptionPane.showMessageDialog(null,"Wrong user name or password");
               Username.setStyle("fx-focus-color:#DC143C; -fx-unfocus-color:#DC143C;");
               Password.setStyle("fx-focus-color:#DC143C; -fx-unfocus-color:#DC143C;");
               System.out.println("reached 7\n");
               }
              }
       }
      //multicatch statement
      catch(Exception e){
                    JFXButton button=new JFXButton("Okay!");
                    showDialog(stackPane,rootAnchor,Arrays.asList(button),s,null);
                    /*
                    BoxBlur blur=new BoxBlur(3,3,3);
                    JFXDialogLayout dialogLayout=new JFXDialogLayout();
                    
                    JFXDialog dialog=new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.TOP, false);
                    button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) ->{
                    dialog.close();
                    });
                    button.getStyleClass().add("fx-dialog-button");
                    button.getStyleClass().add("fx-dialog-text");
                    dialogLayout.setHeading(new Label(s));
                    dialogLayout.setActions(button);
                    dialog.show();
                    rootAnchor.setEffect(blur);
                    dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                        rootAnchor.setEffect(null);
                    });*/
                    System.out.println("reached 8\n"); 
      }
         System.out.println("reached 10 _ last \n");

    }

    @FXML
    private void resetButtonAction(ActionEvent event) {
         Username.setText("");
         Password.setText("");
    }

    @FXML
    private void registerButtonAction(ActionEvent event) {
         System.out.println("register");
         closeStage();
         loadMain("/bazaarnew/register.fxml");
    }

    @FXML
    private void close(MouseEvent event) {
        System.exit(0);
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
