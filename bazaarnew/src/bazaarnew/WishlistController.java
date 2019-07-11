/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazaarnew;

import com.jfoenix.controls.JFXButton;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
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
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static alert.Alert.showDialog;
import com.jfoenix.controls.JFXButton;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ResourceBundle;
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
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author KRITANK
 */
public class WishlistController implements Initializable {

    String localProductName;
    int localCustId,localAmount;
    Socket socket;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    String s;
    int CustId,result;
    ObservableList<saletable> list;
    
    double xOffset,yOffset;
    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane topAnchorPane;
    @FXML
    private ImageView close;
    @FXML
    private TableView<saletable> wishList;
    @FXML
    private Label amount;
    @FXML
    private Button resetButton;
    @FXML
    private Button addToCartButton;
    @FXML
    private ImageView back;
    @FXML
    private Label wishlist;
    @FXML
    private Label orders;
    @FXML
    private MenuItem fashionMenuItem;
    @FXML
    private MenuItem furnitureMenuItem;
    @FXML
    private MenuItem groceryMenuItem;
    @FXML
    private Label home;
    @FXML
    private TableColumn<saletable, String> productNameCol;
    @FXML
    private TableColumn<saletable, Integer> costCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        System.out.println("global: "+main.globalCustId);
         CustId=main.globalCustId;
    
         list= FXCollections.observableArrayList();
      
      JFXButton button=new JFXButton("Okay!");
      //populating the table initially
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
             s="No Connection!";
            System.out.println("reached 1\n");
            String sql = "select * from wish where CustID=?";
            System.out.println("reached 2\n");
            s="Server Not Started Yet!";
            socket=new Socket("localhost",5436);
            System.out.println("socket connected 101");
            objectOutputStream =new ObjectOutputStream(socket.getOutputStream());
            objectInputStream =new ObjectInputStream(socket.getInputStream()); 
            System.out.println("StartServer worked");
            //catch
            int switchCase = 5;
            objectOutputStream.writeObject(switchCase);
            objectOutputStream.writeObject(sql);
            objectOutputStream.writeObject(CustId);
            System.out.println("reached 3\n");
            do
           {
                result=(int)objectInputStream.readObject();
                if(result!=1) break;
                
                System.out.println("reached 4\n");
                localCustId=(int) objectInputStream.readObject();
                localAmount=(int) objectInputStream.readObject();
                localProductName=(String) objectInputStream.readObject();
                System.out.println("entry verified"+ CustId);
                list.add(new saletable(localCustId,localAmount,localProductName));
            }while(result==1);
          
        } catch (Exception ex) {
            //showDialog(stackPane,anchorPane,Arrays.asList(button),"Problem in connecting database",null);
            JOptionPane.showMessageDialog(null, ex);
        }
        
        
     costCol.setCellValueFactory(new PropertyValueFactory<saletable, Integer>("amount"));
     productNameCol.setCellValueFactory(new PropertyValueFactory<saletable, String>("productName"));
     wishList.setItems(list); 
     
     showSum();
    }    

    @FXML
    private void close(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void back(MouseEvent event) {
        //((Stage)stackPane.getScene().getWindow()).setIconified(true);
        closeStage();
        loadMain("/bazaarnew/home.fxml");
    }

    @FXML
    private void resetButtonFunc(ActionEvent event) {
       System.out.println("resetButton");
        try {
            s="No Connection!";
            System.out.println("reached 1\n");
            String sql = "DELETE FROM `wish` WHERE CustId=?";
            System.out.println("reached 2\n");
            s="Server Not Started Yet!";
            socket=new Socket("localhost",5436);
            System.out.println("socket connected 101");
            objectOutputStream =new ObjectOutputStream(socket.getOutputStream());
            objectInputStream =new ObjectInputStream(socket.getInputStream()); 
            System.out.println("StartServer worked");
            //catch
            int switchCase = 7;
            objectOutputStream.writeObject(switchCase);
            objectOutputStream.writeObject(sql);
            objectOutputStream.writeObject(CustId);
            System.out.println("reached 3\n");
            result=(int)objectInputStream.readObject();
            if(result==1)
            {
             System.out.println("reached 4\n");
             amount.setText("");
             list.clear();
            }
          
        } catch (Exception ex) {
            //showDialog(stackPane,anchorPane,Arrays.asList(button),"Problem in connecting database",null);
            JOptionPane.showMessageDialog(null, ex);
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

    @FXML
    private void addToCartButtonFunc(ActionEvent event) {
        //reset button called and entries entered to the cart
        System.out.println("AddToCartButton");
         try {
             s="No Connection!";
            System.out.println("reached 111\n");
            String sql = "select * from wish where CustID=?";
            System.out.println("reached 2222\n");
            s="Server Not Started Yet!";
            socket=new Socket("localhost",5436);
            System.out.println("socket connected 101");
            objectOutputStream =new ObjectOutputStream(socket.getOutputStream());
            objectInputStream =new ObjectInputStream(socket.getInputStream()); 
            System.out.println("StartServer worked");
            //catch
            int switchCase = 9;
            objectOutputStream.writeObject(switchCase);
            objectOutputStream.writeObject(sql);
            objectOutputStream.writeObject(CustId);
            System.out.println("reached 3333\n");
            result=(int)objectInputStream.readObject();
            System.out.println(result);
            resetButtonFunc(new ActionEvent());
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
    }


    @FXML
    private void cartMenuButtonFunc(MouseEvent event) {
         closeStage();
        loadMain("/bazaarnew/cart.fxml");
    }

    @FXML
    private void orderMenuButtonFunc(MouseEvent event) {
         closeStage();
        loadMain("/bazaarnew/order.fxml");
    }

    @FXML
    private void homeMenuButtonFunc(MouseEvent event) {
         closeStage();
        loadMain("/bazaarnew/home.fxml");
    }

    @FXML
    private void fashionMenuItemFunc(ActionEvent event) {
        closeStage();
        //add change load function pass string to determine which frame to open
        loadMain("/bazaarnew/home.fxml");
    }

    @FXML
    private void furnitureMenuItemFunc(ActionEvent event) {
        closeStage();
        //add change load function pass string to determine which frame to open
        loadMain("/bazaarnew/home.fxml");
    }

    @FXML
    private void groceryMenuItemFunc(ActionEvent event) {
        closeStage();
        //add change load function pass string to determine which frame to open
        loadMain("/bazaarnew/home.fxml");
    }

    public void showSum()
    {
        System.out.println("showSum");
        try {
            s="No Connection!";
            System.out.println("reached 1\n");
            String sql = "SELECT SUM(`Quantity`*`Cost`) AS sum FROM wish where `CustId`=?";
            System.out.println("reached 2\n");
            s="Server Not Started Yet!";
            socket=new Socket("localhost",5436);
            System.out.println("socket connected 101");
            objectOutputStream =new ObjectOutputStream(socket.getOutputStream());
            objectInputStream =new ObjectInputStream(socket.getInputStream()); 
            System.out.println("StartServer worked");
            //catch
            int switchCase = 6;
            objectOutputStream.writeObject(switchCase);
            objectOutputStream.writeObject(sql);
            objectOutputStream.writeObject(CustId);
            System.out.println("reached 3\n");
            result=(int)objectInputStream.readObject();
            if(result==1)
            {
             System.out.println("reached 4\n");
             int sum=(int)objectInputStream.readObject();
             System.out.println(sum);
             amount.setText(Integer.toString(sum));
            }
          
        } catch (Exception ex) {
            //showDialog(stackPane,anchorPane,Arrays.asList(button),"Problem in connecting database",null);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    public static class saletable {
    
    private final SimpleIntegerProperty custId;
    private final SimpleIntegerProperty amount;
    private final SimpleStringProperty productName;
    
    
    public saletable(Integer custId,Integer amount,String productName)
    {
        this.custId=new SimpleIntegerProperty(custId);
        this.amount=new SimpleIntegerProperty(amount);
        this.productName=new SimpleStringProperty(productName);
    }
    
    public Integer getCustId()
    {
        return custId.get();
    }
  
    
    public Integer getAmount()
    {
        return amount.get();
    }
   
    
   public String getProductName()
    {
        return productName.get();
    }
    
   public void setCustId(Integer t)
    {
       custId.set(t);
    }
    
   
    
   public void setAmount(Integer t)
    {
        amount.set(t);
    }
    
    
    void setProductName(String t)
    {
        productName.set(t);
    }
    
  }
    
}
