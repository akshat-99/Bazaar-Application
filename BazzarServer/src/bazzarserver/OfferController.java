package bazzarserver;
 
import static alert.Alert.showDialog;
import com.jfoenix.controls.JFXButton;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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
public class OfferController implements Initializable {
 
    double xOffset,yOffset;
    //LocalDate offerStartLocalDate;
    //LocalDate offerEndLocalDate;
    PreparedStatement ps;
    int Discount;
    int NoOfCustomer;
    ResultSet rs;
 
    String OfferStartLocalDate;
    String OfferEndLocalDate;
    String StartLocalDate;
    String EndLocalDate;
 
    DateTimeFormatter formatter;
 
    @FXML
    private StackPane pane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView close;
    @FXML
    private TextField discount;
    @FXML
    private TextField noOfCustomer;
    private DatePicker offerStartDate;
    private DatePicker offerEndDate;
    @FXML
    private ImageView back;
    @FXML
    private DatePicker searchStartDate;
    @FXML
    private DatePicker seachEndDate;
    @FXML
    private DatePicker addOfferStartDate;
    @FXML
    private DatePicker addOfferEndDate;
    @FXML
    private Button addButton;
    @FXML
    private Button reset;
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
 
    @FXML
    private void close(MouseEvent event) {
         System.exit(0);
    }
 
    @FXML
    private void addButtonFunc(ActionEvent event) {
        Discount = parseInt(discount.getText());
        NoOfCustomer = parseInt(noOfCustomer.getText());
 
        try {
//            String query = "INSERT INTO `products`(`Name`,`ManfDate`,`ExpDate`,`OfferStartDate`,`OfferEndDate`, `Seller`,`Category`,"
//                            + " `Quantity`,`CP`,`SP`,`Disc`,`Desc`,`Spec`,`Image1`,`Image2`,`Image3`,`Image4`)"
//                            + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
 
            String query="SELECT CustId, sum(Amt) FROM sales WHERE Date BETWEEN ? AND ?"+
                         "GROUP BY CustId ORDER By SUM(Amt) DESC Limit ?";
 
            ps = HandleClient.getConnection().prepareStatement(query);
            ps.setString(1, StartLocalDate);
            ps.setString(2, EndLocalDate);
            ps.setInt(3,NoOfCustomer);
 
            rs = ps.executeQuery();
            System.out.println("query executed");
         
            while(rs.next())
            {
                int CustId = rs.getInt("CustId");
                System.out.println(""+CustId);
 
                String query2 = "UPDATE register  SET SpecialDiscount = ?, SpecialDiscountStartDate = ?,"
                                +"SpecialDiscountEndDate = ? WHERE CustId = ?";
                ps = HandleClient.getConnection().prepareStatement(query2);
                ps.setInt(1, Discount);
                ps.setString(2, OfferStartLocalDate);
                ps.setString(3, OfferEndLocalDate);
                ps.setInt(4,CustId);
 
                if(ps.executeUpdate()>0)
                {
//                    JFXButton button=new JFXButton("Okay!");
//                    showDialog(pane,anchorPane,Arrays.asList(button),"special discount add successfull",null);
                }   
            }
                JFXButton button=new JFXButton("Okay!");
                showDialog(pane,anchorPane,Arrays.asList(button),"update successfull",null);
 
             }catch (Exception ex) {
                  JOptionPane.showMessageDialog(null,ex);
        }
    }
 
    @FXML
    private void searchStartDateFunc(ActionEvent event) {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        StartLocalDate = searchStartDate.getValue().format(formatter);
        System.out.println(StartLocalDate);
    }
 
    @FXML
    private void seachEndDateFunc(ActionEvent event) {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        EndLocalDate = seachEndDate.getValue().format(formatter);
        System.out.println(EndLocalDate);
    }
 
     @FXML
    private void addOfferStartDateFunc(ActionEvent event) {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        OfferStartLocalDate = addOfferStartDate.getValue().format(formatter);
        System.out.println(OfferStartLocalDate);
 
    }
 
    @FXML
    private void addOfferEndDateFunc(ActionEvent event) {
       formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        OfferEndLocalDate = addOfferEndDate.getValue().format(formatter);
        System.out.println(OfferEndLocalDate);
    }
 
    @FXML
    private void resetButtonFunc(ActionEvent event) {
        discount.setText("");
        noOfCustomer.setText("");
        
        searchStartDate.setValue(null);
        searchStartDate.getEditor().clear();
        
        seachEndDate.setValue(null);
        seachEndDate.getEditor().clear();
        
        addOfferStartDate.setValue(null);
        addOfferStartDate.getEditor().clear();
        
        addOfferEndDate.setValue(null);
        addOfferEndDate.getEditor().clear();
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
     public static Connection getConnection(){
      Connection c=null;
      try{
          c=DriverManager.getConnection("jdbc:mysql://localhost/kuku_bazaar","root","");
          }
 
     catch(SQLException e){
       JOptionPane.showMessageDialog(null,"no connection"+ e);
          }
    return c;
    } 

}