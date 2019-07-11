/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazzarserver;

import static alert.Alert.showDialog;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author KRITANK
 */
public class SalesController implements Initializable {
    ResultSet r;
    PreparedStatement ps;
    Date d;
    String localSelectDate;
    ObservableList<saletable> list;
    String localProductName,localDate,localTime;
    int localCustId,localProductId,localAmount;
    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView close;
    @FXML
    private ImageView back;
    @FXML
    private TableView<saletable> saleTable;
    @FXML
    private TableColumn<saletable, String> productName;
    @FXML
    private TableColumn<saletable, Integer> customerId;
    @FXML
    private TableColumn<saletable, Integer> billId;
    @FXML
    private TableColumn<saletable, Integer> amount;
    @FXML
    private TableColumn<saletable, String> date;
    @FXML
    private TableColumn<saletable, String> time;
    @FXML
    private Button searchButton;
    @FXML
    private DatePicker datePicker;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    list= FXCollections.observableArrayList();
      
      JFXButton button=new JFXButton("Okay!");
      
      datePicker.setConverter(new StringConverter<LocalDate>()
      {
       private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");  //yaha error aa sakti hai
                                                                                               //date ke format ki
       @Override
       public String toString(LocalDate localDate)
       {
        if(localDate==null)
            return "";
        return dateTimeFormatter.format(localDate);
       }

      @Override
     public LocalDate fromString(String dateString)
     {
        if(dateString==null || dateString.trim().isEmpty())
        {
            return null;
        }
        return LocalDate.parse(dateString,dateTimeFormatter);
     }
     });
      
      
      //populating the table initially
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
            String query = "select * from sales";
            ps = HandleClient.getConnection().prepareStatement(query);
            r = ps.executeQuery();
            while(r.next())
            {
                localProductName=r.getString("ProName");
                localCustId=r.getInt("CustId");
                localAmount=r.getInt("Amt");
                localProductId=r.getInt("BillId");
                d=r.getDate("Date");
                localDate=d.toString();
                localTime=r.getString("Time");
                list.add(new saletable(localCustId,localProductId,localAmount,localDate,localTime,localProductName));
            }
        } catch (SQLException ex) {
            showDialog(stackPane,anchorPane,Arrays.asList(button),"Problem in connecting database",null);
        }
        
   
     
  
     customerId.setCellValueFactory(new PropertyValueFactory<saletable, Integer>("custId"));
     billId.setCellValueFactory(new PropertyValueFactory<saletable, Integer>("billId"));
     amount.setCellValueFactory(new PropertyValueFactory<saletable, Integer>("amount"));
     date.setCellValueFactory(new PropertyValueFactory<saletable, String>("date"));
     time.setCellValueFactory(new PropertyValueFactory<saletable, String>("time"));
     productName.setCellValueFactory(new PropertyValueFactory<saletable, String>("productName"));
     saleTable.setItems(list); 
    }    

    @FXML
    private void close(MouseEvent event) {
        System.exit(0);
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
            stage.setScene(new Scene(root));  
            stage.show();
        }
        catch(Exception e)
        {
            System.out.println("Cant load");
        }
    }

    @FXML
    private void searchButtonFunc(ActionEvent event) {
        System.out.println("ander");
        localSelectDate=datePicker.getValue().toString();
        list.clear();
        JFXButton button=new JFXButton("Okay!");
        try {
            String query = "select * from sales where Date=?";
            ps = HandleClient.getConnection().prepareStatement(query);
            ps.setString(1,localSelectDate);
            r = ps.executeQuery();
            while(r.next())
            {
                localProductName=r.getString("ProName");
                localCustId=r.getInt("CustId");
                localAmount=r.getInt("Amt");
                localProductId=r.getInt("BillId");
                localDate=r.getString("Date");
                localTime=r.getString("Time");
                list.add(new saletable(localCustId,localProductId,localAmount,localDate,localTime,localProductName));
            }
        } catch (SQLException ex) {
            showDialog(stackPane,anchorPane,Arrays.asList(button),"Problem in connecting database",null);
        }
        saleTable.setItems(list); 
        
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
   
    
 

