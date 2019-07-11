/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazzarserver;

import static alert.Alert.showDialog;
import com.jfoenix.controls.JFXButton;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Integer.parseInt;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author KRITANK
 */
public class AddProductController implements Initializable {

        DateTimeFormatter formatter;
        PreparedStatement ps;
        String ProductName;
        String SellerName;
        int CostPrice;
        int SellingPrice;
        int Discount;
        int Quantity;
        String Choicebox;
        String Specifications;
        String Description;

        String arr[];
    
    List<String> categoryList;
    String mfLocalDate;
    String expLocalDate;
    String offerStartLocalDate;
    String offerEndLocalDate;
    List<String> filesType;
    List<ImageView> imageViews;
    List<File> f;
    
    @FXML
    private JFXButton selectButton;
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView close;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView4;
    @FXML
    private JFXButton resetImageButton;
    @FXML
    private DatePicker mfDate;
    @FXML
    private DatePicker expDate;
    @FXML
    private DatePicker offerStartDate;
    @FXML
    private DatePicker offerEndDate;
    @FXML
    private TextArea specifications;
    @FXML
    private TextArea description;
    @FXML
    private JFXButton resetButton;
    @FXML
    private JFXButton saveButton;
    @FXML
    private TextField productName;
    @FXML
    private TextField sellerName;
    @FXML
    private TextField costPrice;
    @FXML
    private TextField sellingPrice;
    @FXML
    private TextField discount;
    @FXML
    private TextField quantity;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ChoiceBox<String> choicebox;
    @FXML
    private ImageView back;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane topAnchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //adding files type to the fileChooser
        
        arr = new String[4];      
        
        filesType=new ArrayList<>();
        filesType.add(".jpeg");
        filesType.add(".jpg");
        filesType.add(".png");
        
        
        //adding imageView to the list
        imageViews=new ArrayList<>();
        imageViews.add(imageView1);
        imageViews.add(imageView2);
        imageViews.add(imageView3);
        imageViews.add(imageView4);
       // System.out.println(imageViews.size());
        
        //initialising the choiceBox
         categoryList=FXCollections.observableArrayList();
         categoryList.add("Electronics");
         categoryList.add("Fashion");
         categoryList.add("Home");
         categoryList.add("Furniture");
         
         
        //adding the items to choicebox
          choicebox.setValue("Fashion");
          choicebox.setItems((ObservableList<String>) categoryList);
    
    }    

    @FXML
    private void selectButtonFunc(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("IMAGE FILES", "*.png"));
         f=new ArrayList(fc.showOpenMultipleDialog(null));
        File file;
        int c;
        for(c=0;c<f.size();c++)
        {
          
           URL url;
           file=f.get(c);
           
           try {
                
                ImageView t=imageViews.get(c);
                System.out.println(file.getAbsolutePath());
                System.out.println(t);
                System.out.println(c);
                url = file.toURI().toURL();
                
                arr[c] = file.getAbsolutePath();
                System.out.println(arr[c]);
                t.setImage(new Image(url.toExternalForm()));
               
            } catch (MalformedURLException ex) {
                Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
           if(c<imageViews.size())
               {
                  File f1=new File("C:\\Users\\acer\\Documents\\NetBeansProjects\\BazzarServer\\Res\\no-image.png");
                  Image image=new Image(f1.toURI().toString());
                  while(c<imageViews.size())
                  {
                      ImageView t= imageViews.get(c);
                      System.out.println(t);
                      t.setImage(image);
                      c++;
                  }
             
               System.out.println("exit1");
           }
        
        System.out.println("exiting!!!!!!!");
    }

    @FXML
    private void close(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void hover(MouseEvent event) {
        URL url,url0;
        File file,file0;
        ImageView t,t0;
        try {
        if(event.getSource().equals(imageViews.get(1)))
          {
                System.out.println("hiiii");
                t=imageViews.get(1);
                t0=imageViews.get(0);
                file=f.get(1);
                file0=f.get(0);
                url = file.toURI().toURL();
                url0 = file0.toURI().toURL();
                t.setImage(new Image(url0.toExternalForm()));
                t0.setImage(new Image(url.toExternalForm()));
                Collections.swap(f, 0, 1);

           }
        else if(event.getSource().equals(imageViews.get(2)))
         {
                System.out.println("hiiii2");
                t=imageViews.get(2);
                t0=imageViews.get(0);
                file=f.get(2);
                file0=f.get(0);
                url = file.toURI().toURL();
                url0 = file0.toURI().toURL();
                t.setImage(new Image(url0.toExternalForm()));
                t0.setImage(new Image(url.toExternalForm()));
                Collections.swap(f, 0, 2);
            
         }
        else if(event.getSource().equals(imageViews.get(3)))
        {
                System.out.println("hiiii3");
                t=imageViews.get(3);
                t0=imageViews.get(0);
                file=f.get(3);
                file0=f.get(0);
                url = file.toURI().toURL();
                url0 = file0.toURI().toURL();
                t.setImage(new Image(url0.toExternalForm()));
                t0.setImage(new Image(url.toExternalForm()));  
                Collections.swap(f, 0, 3);
        }
         } catch (MalformedURLException ex) {
                System.out.println("Error in hover "+ex);
            }
    }


    @FXML
    private void resetimageButtonFunc(ActionEvent event) {
        File f=new File("C:\\Users\\acer\\Documents\\NetBeansProjects\\BazzarServer\\Res\\no-image.png");
        
        Image image=new Image(f.toURI().toString());
        for(int i=0;i<imageViews.size();i++)
        {
            ImageView t= imageViews.get(i);
            t.setImage(image);
        }
    }

    @FXML
    private void mfDateShow(ActionEvent event) {
        formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        mfLocalDate = mfDate.getValue().format(formatter);   
    }

    @FXML
    private void expDateShow(ActionEvent event) {
        formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        expLocalDate = expDate.getValue().format(formatter);
    }
    
     @FXML
    private void offerStartDateShow(ActionEvent event) {
        formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        offerStartLocalDate = offerStartDate.getValue().format(formatter);
       
    }

    @FXML
    private void offerEndDateShow(ActionEvent event) {
         formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
         offerEndLocalDate = offerEndDate.getValue().format(formatter);
       
    }

    @FXML
    private void resetButtonFunc(ActionEvent event) {
        
        
    //setting date to null
        mfDate.setValue(null);
        mfDate.getEditor().clear();
        expDate.setValue(null);
        expDate.getEditor().clear();
        offerStartDate.setValue(null);
        offerStartDate.getEditor().clear();
        offerEndDate.setValue(null);
        offerEndDate.getEditor().clear();
        
        //resetting textfeilds
        specifications.setText("");
        description.setText("");
        productName.setText("");
        sellerName.setText("");
        costPrice.setText("");
        sellingPrice.setText("");
        discount.setText("");
        quantity.setText("");
        
        //resettting the images
        resetimageButtonFunc(new ActionEvent());   
    }

    @FXML
    private void saveButtonFunc(ActionEvent event) {
        
        ProductName = productName.getText();
        SellerName = sellerName.getText();
        CostPrice = parseInt(costPrice.getText());
        SellingPrice = parseInt(sellingPrice.getText());
        Discount = parseInt(discount.getText());
        Quantity = parseInt(quantity.getText());
        Choicebox = choicebox.getValue();
        Specifications = specifications.getText();
        Description = description.getText();
        
        try {
            String query = "INSERT INTO `products`(`Name`,`ManfDate`,`ExpDate`,`OfferStartDate`,`OfferEndDate`, `Seller`,`Category`,"
                            + " `Quantity`,`CP`,`SP`,`Disc`,`Desc`,`Spec`,`Image1`,`Image2`,`Image3`,`Image4`)"
                            + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                
            ps = HandleClient.getConnection().prepareStatement(query);
            ps.setString(1, ProductName);
            ps.setString(2, mfLocalDate);
            ps.setString(3, expLocalDate);
            ps.setString(4, offerStartLocalDate);
            ps.setString(5, offerEndLocalDate);
            ps.setString(6, SellerName);
            ps.setString(7, Choicebox);
            ps.setInt(8, Quantity);
            ps.setInt(9, CostPrice);
            ps.setInt(10, SellingPrice);
            ps.setInt(11, Discount);
            ps.setString(12, Description);
            ps.setString(13, Specifications);
            
            InputStream is = new FileInputStream(new File(arr[0]));
            ps.setBlob(14, is);
            
            InputStream is1 = new FileInputStream(new File(arr[1]));
            ps.setBlob(15, is1);
            
            InputStream is2 = new FileInputStream(new File(arr[2]));
            ps.setBlob(16, is2);
            
            InputStream is3 = new FileInputStream(new File(arr[3]));
            ps.setBlob(17, is3);
            
            if(ps.executeUpdate()>0)
            {
                JFXButton button=new JFXButton("Okay!");
                showDialog(stackPane,anchorPane,Arrays.asList(button),"product added successfully",null);
            }
        
            
        }catch (Exception ex) {
//                JFXButton button=new JFXButton("Okay!");
//                showDialog(stackPane,anchorPane,Arrays.asList(button),""+ex,null);
                  JOptionPane.showMessageDialog(null,ex);
        }
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
