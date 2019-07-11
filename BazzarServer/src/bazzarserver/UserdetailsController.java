/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazzarserver;

import static alert.Alert.showDialog;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author KRITANK
 */
public class UserdetailsController implements Initializable {
    ResultSet r;
    PreparedStatement ps;
    String username,localUserName,localEmail,localAddress,localDob, localPhoneNo;
    int localCustId;
    
     ObservableList<saletable> list;
    double xOffset,yOffset;
    @FXML
    private StackPane pane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView close;
    @FXML
    private TextField customerName;
    @FXML
    private TableColumn<saletable, Integer> mobileNo;
    @FXML
    private Button searchButton;
    @FXML
    private TableColumn<saletable, Integer> customerId;
    @FXML
    private TableColumn<saletable, String> dateOfBirth;
    @FXML
    private TableColumn<saletable, String> emailId;
    @FXML
    private TableColumn<saletable, String> address;
    @FXML
    private ImageView back;
    @FXML
    private TableView<saletable> userDetails;
    @FXML
    private TableColumn<saletable, String> usernameCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //populating the table initially
         list= FXCollections.observableArrayList();
         JFXButton button=new JFXButton("Okay!");
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
            String query = "select * from register";
            ps = HandleClient.getConnection().prepareStatement(query);
            r = ps.executeQuery();
            while(r.next())
            {
                
                localCustId=r.getInt("CustId");
                localPhoneNo=r.getString("PhoneNo");
                localAddress=r.getString("Address");
                localUserName=r.getString("Username");
                localEmail=r.getString("EmailId");
                localDob=r.getString("DOB");
                list.add(new saletable(localCustId,localPhoneNo,localAddress,localUserName,localEmail,localDob));
            }
        } catch (SQLException ex) {
            //showDialog(pane,anchorPane,Arrays.asList(button),"Problem in connecting database",null);
            JOptionPane.showMessageDialog(null,ex);
        }
        
     customerId.setCellValueFactory(new PropertyValueFactory<saletable, Integer>("custId"));
     mobileNo.setCellValueFactory(new PropertyValueFactory<saletable, Integer>("phoneNo"));
     usernameCol.setCellValueFactory(new PropertyValueFactory<saletable, String>("userName"));
     dateOfBirth.setCellValueFactory(new PropertyValueFactory<saletable, String>("dob"));
     emailId.setCellValueFactory(new PropertyValueFactory<saletable, String>("email"));
     address.setCellValueFactory(new PropertyValueFactory<saletable, String>("address"));
     userDetails.setItems(list); 
    }    

    @FXML
    private void close(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void searchButtonFunc(ActionEvent event) {
       username=customerName.getText();
       list.clear();
       JFXButton button=new JFXButton("Okay!");
        try {
            String query = "select * from register where UserName=?";
            ps = HandleClient.getConnection().prepareStatement(query);
            ps.setString(1, username);
         
            
            r = ps.executeQuery();
            if(!r.isBeforeFirst()&& r.getRow() == 0)
            {
                showDialog(pane,anchorPane,Arrays.asList(button),"Not Found",null);
            }
            if(r.next())
            {
                localCustId=r.getInt("CustId");
                localPhoneNo=r.getString("PhoneNo");
                localAddress=r.getString("Address");
                localUserName=r.getString("Username");
                localEmail=r.getString("EmailId");
                localDob=r.getString("DOB");
                list.add(new saletable(localCustId,localPhoneNo,localAddress,localUserName,localEmail,localDob));
                
            }
        } catch (SQLException ex) {
            showDialog(pane,anchorPane,Arrays.asList(button),"Problem in connecting database",null);
        }
       userDetails.setItems(list);
        
       }
    
    
    @FXML
    private void back(MouseEvent event) {
        closeStage();
        loadMain("/bazzarserver/admin.fxml");
        
    }
    
      private void closeStage(){
        ((Stage)close.getScene().getWindow()).close();
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
    public static class saletable {
    
    private final SimpleIntegerProperty custId;
    private final SimpleStringProperty phoneNo;
    private final SimpleStringProperty address;
    private final SimpleStringProperty userName;
    private final SimpleStringProperty email;
    private final SimpleStringProperty dob;
    
    
    
    public saletable(Integer custId,String phoneNo,String address,String userName,String email,String dob)
    {
        this.custId=new SimpleIntegerProperty(custId);
        this.phoneNo=new SimpleStringProperty(phoneNo);
        this.address=new SimpleStringProperty(address);
        this.userName=new SimpleStringProperty(userName);
        this.email=new SimpleStringProperty(email);
        this.dob=new SimpleStringProperty(dob);
    }
    
    public Integer getCustId()
    {
        return custId.get();
    }
    
    public String getPhoneNo()
    {
        return phoneNo.get();
    }
    
    public String getAddress()
    {
        return address.get();
    }
    
    public String getUserName()
    {
        return userName.get();
    }
    
    public String getEmail()
    {
        return email.get();
    }
    
    public String getDob()
    {
        return dob.get();
    }
    
    
}
}
