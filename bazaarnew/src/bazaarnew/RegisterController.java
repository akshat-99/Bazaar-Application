/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazaarnew;

import static alert.Alert.showDialog;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.sql.PreparedStatement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.apache.commons.codec.digest.DigestUtils;


/**
 *
 * @author Dell
 */
public class RegisterController {

        Socket socket;
        ObjectOutputStream objectOutputStream;
        ObjectInputStream objectInputStream;
        String Username;
        String Address;
        String PhoneNo;
        String Email;
        String DOB;
        String Password;
        String RePassword;
        int switchCase;    
        String query;
        
    @FXML
    private Font x1;
    @FXML
    private ImageView close;
    @FXML
    private PasswordField password;
    @FXML
    private Button resetButton;
    @FXML
    private Button registerButton;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField repassword;
    @FXML
    private TextField address;
    @FXML
    private TextField email;
    @FXML
    private TextField mobile;
    @FXML
    private TextField dob;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private StackPane stackPane;

    @FXML
    private void resetButtonAction(ActionEvent event) {
        userName.setText("");
        address.setText("");
        mobile.setText("");
        email.setText("");
        password.setText("");
        repassword.setText("");
        dob.setText("");
    }

    @FXML
    private void registerButtonAction(ActionEvent event) {
        Username=userName.getText();
        Address=address.getText();
        PhoneNo=mobile.getText();
        Email=email.getText();
        DOB=dob.getText();
        Password=DigestUtils.sha1Hex(password.getText()).toString();
        RePassword=DigestUtils.sha1Hex(repassword.getText()).toString();
        
        userName.setStyle("fx-focus-color:#DC143C; -fx-unfocus-color:#57d057;");
        password.setStyle("fx-focus-color:#DC143C; -fx-unfocus-color:#57d057;");
        address.setStyle("fx-focus-color:#DC143C; -fx-unfocus-color:#57d057;");
        email.setStyle("fx-focus-color:#DC143C; -fx-unfocus-color:#57d057;");
        dob.setStyle("fx-focus-color:#DC143C; -fx-unfocus-color:#57d057;");
        mobile.setStyle("fx-focus-color:#DC143C; -fx-unfocus-color:#57d057;");
        repassword.setStyle("fx-focus-color:#DC143C; -fx-unfocus-color:#57d057;");
        
        int result;

        if(Username.equals(""))
        {
            userName.setStyle("fx-focus-color:#DC143C; -fx-unfocus-color:#DC143C;");
        }
        else if(Address.equals(""))
        {
            address.setStyle("fx-focus-color:#DC143C; -fx-unfocus-color:#DC143C;");
        }
        else if(PhoneNo.equals(""))
        {
            mobile.setStyle("fx-focus-color:#DC143C; -fx-unfocus-color:#DC143C;");
        }
        else if(Email.equals(""))
        {
            email.setStyle("fx-focus-color:#DC143C; -fx-unfocus-color:#DC143C;");  
        }
        else if(DOB.equals(""))
        {
            dob.setStyle("fx-focus-color:#DC143C; -fx-unfocus-color:#DC143C;");
        }
        else if(Password.equals(""))
        {
            password.setStyle("fx-focus-color:#DC143C; -fx-unfocus-color:#DC143C;");
        }
        else if(!Password.equals(RePassword))
        {
           repassword.setStyle("fx-focus-color:#DC143C; -fx-unfocus-color:#DC143C;");
           password.setText("");
           repassword.setText("");
        }
        
        else
        {
            try {
                query="INSERT INTO `register`(`Username`, `Password`, `Address`, `PhoneNo`, `EmailId`,`DOB`) VALUES (?,?,?,?,?,?)";
                
                socket=new Socket("localhost",5436);
                System.out.println("socket connected 101");
                objectOutputStream =new ObjectOutputStream(socket.getOutputStream());
                objectInputStream =new ObjectInputStream(socket.getInputStream());
                System.out.println("StartServer worked");
                switchCase=2;
                
                objectOutputStream.writeObject(switchCase);
                objectOutputStream.writeObject(query);
                objectOutputStream.writeObject(Username);
                objectOutputStream.writeObject(Password);
                objectOutputStream.writeObject(Address);
                objectOutputStream.writeObject(PhoneNo);
                objectOutputStream.writeObject(Email);
                objectOutputStream.writeObject(DOB);
                
                result=(int)objectInputStream.readObject();
                if(result==0)
                {
                    JFXButton button=new JFXButton("Okay!");
                    showDialog(stackPane,anchorPane,Arrays.asList(button),"Enter different Username",null);
                }
                else if(result==1){
                    JFXButton button=new JFXButton("Okay!");
                    showDialog(stackPane,anchorPane,Arrays.asList(button),"Account registered",null);
                }
                else
                {
                    JFXButton button=new JFXButton("Okay!");
                    showDialog(stackPane,anchorPane,Arrays.asList(button),"Try again",null);
                    userName.setText("");
                    address.setText("");
                    mobile.setText("");
                    email.setText("");
                    password.setText("");
                    repassword.setText("");
                    dob.setText("");
                }
                  
                
            } catch (Exception ex) {
                JFXButton button=new JFXButton("Okay!");
                    showDialog(stackPane,anchorPane,Arrays.asList(button),"error in register"+ex,null);
            }
       }
        
    }

    @FXML
    private void closeclicked(MouseEvent event) {
        System.exit(0);
    }
    
//    void loadMain(){
//        try{
//            Parent root = FXMLLoader.load(getClass().getResource("/bazaarnew/login.fxml"));
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));  
//            stage.show();
//        }
//        catch(Exception e)
//        {
//            System.out.println("Cant load");
//        }
//    }
    
}
