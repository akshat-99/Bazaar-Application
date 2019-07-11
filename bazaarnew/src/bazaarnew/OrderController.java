/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazaarnew;

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

/**
 * FXML Controller class
 *
 * @author KRITANK
 */
public class OrderController implements Initializable {
 
    String localProductName,localDate,localTime;
    int localCustId,localProductId,localAmount;
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
    private Button continueShoppingButton;
    @FXML
    private ImageView back;
    @FXML
    private Label wishlist;
    @FXML
    private Label home;
    @FXML
    private MenuItem fashionMenuItem;
    @FXML
    private MenuItem furnitureMenuItem;
    @FXML
    private MenuItem groceryMenuItem;
    @FXML
    private TableView<saletable> orderTable;
    @FXML
    private TableColumn<saletable, String> productName;
    @FXML
    private TableColumn<saletable, Integer> billId;
    @FXML
    private TableColumn<saletable, Integer> amount;
    @FXML
    private TableColumn<saletable, String> date;
    @FXML
    private TableColumn<saletable, String> time;
    @FXML
    private AnchorPane anchorPane;

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
            String sql = "select * from sales where CustID=?";
            System.out.println("reached 2\n");
            s="Server Not Started Yet!";
            socket=new Socket("localhost",5436);
            System.out.println("socket connected 101");
            objectOutputStream =new ObjectOutputStream(socket.getOutputStream());
            objectInputStream =new ObjectInputStream(socket.getInputStream()); 
            System.out.println("StartServer worked");
            //catch
            int switchCase = 4;
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
                localProductId=(int) objectInputStream.readObject();
                localAmount=(int) objectInputStream.readObject();
                localDate=(String) objectInputStream.readObject();
                localTime=(String) objectInputStream.readObject();
                localProductName=(String) objectInputStream.readObject();
                System.out.println("entry verified"+ CustId);
                 list.add(new saletable(localCustId,localProductId,localAmount,localDate,localTime,localProductName));
            }while(result==1);
          
        } catch (Exception ex) {
            //showDialog(stackPane,anchorPane,Arrays.asList(button),"Problem in connecting database",null);
            JOptionPane.showMessageDialog(null, ex);
        }
        
   
     billId.setCellValueFactory(new PropertyValueFactory<saletable, Integer>("billId"));
     amount.setCellValueFactory(new PropertyValueFactory<saletable, Integer>("amount"));
     date.setCellValueFactory(new PropertyValueFactory<saletable, String>("date"));
     time.setCellValueFactory(new PropertyValueFactory<saletable, String>("time"));
     productName.setCellValueFactory(new PropertyValueFactory<saletable, String>("productName"));
     orderTable.setItems(list); 
    }    
        

    @FXML
    private void close(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void back(MouseEvent event) {
        //  ((Stage)stackPane.getScene().getWindow()).setIconified(true);
         closeStage();
         loadMain("/bazaarnew/home.fxml");
    }


    @FXML
    private void continueShoppingButtonFunc(ActionEvent event) {
        closeStage();
        loadMain("/bazaarnew/home.fxml");
    }
    
     private void closeStage(){
        ((Stage)stackPane.getScene().getWindow()).close();
    }
    
    
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
    private void wishlistMenuButtonFunc(MouseEvent event) {
          closeStage();
        loadMain("/bazaarnew/wishlist.fxml");
    }

    @FXML
    private void cartMenuButtonFunc(MouseEvent event) {
          closeStage();
        loadMain("/bazaarnew/cart.fxml");
    }

    @FXML
    private void homeMenuButtonFunc(MouseEvent event) {
         closeStage();
        loadMain("/bazaarnew/homes.fxml");
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
    
    public static class saletable {
    
    private final SimpleIntegerProperty custId;
    private final SimpleIntegerProperty billId;
    private final SimpleIntegerProperty amount;
    private final SimpleStringProperty date;
    private final SimpleStringProperty time;
    private final SimpleStringProperty productName;
    
    
    public saletable(Integer custId,Integer billId,Integer amount,String date,String time,String productName)
    {
        this.custId=new SimpleIntegerProperty(custId);
        this.billId=new SimpleIntegerProperty(billId);
        this.amount=new SimpleIntegerProperty(amount);
        this.date=new SimpleStringProperty(date);
        this.time=new SimpleStringProperty(time);
        this.productName=new SimpleStringProperty(productName);
    }
    
    public Integer getCustId()
    {
        return custId.get();
    }
    
    public Integer getBillId()
    {
        return billId.get();
    }
    
    public Integer getAmount()
    {
        return amount.get();
    }
    
    public String getDate()
    {
        return date.get();
    }
    
    public String getTime()
    {
        return time.get();
    }
    
   public String getProductName()
    {
        return productName.get();
    }
    
   public void setCustId(Integer t)
    {
       custId.set(t);
    }
    
   public void setBillId(Integer t)
    {
       billId.set(t);
    }
    
   public void setAmount(Integer t)
    {
        amount.set(t);
    }
    
    public void setDate(String t)
    {
        date.set(t);
    }
    
   public  void setTime(String t)
    {
         time.set(t);
    }
    
    void setProductName(String t)
    {
        productName.set(t);
    }
    
  }
}
