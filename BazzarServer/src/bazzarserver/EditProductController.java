/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazzarserver;

import static alert.Alert.showDialog;
import com.jfoenix.controls.JFXButton;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Integer.parseInt;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author KRITANK
 */
public class EditProductController implements Initializable {

    DateTimeFormatter formatter;
    PreparedStatement ps;
    ResultSet rs;
    ObservableList<String> categoryList;
    String localProductName;
    Date d;
    String mfLocalDate;
    String expLocalDate;
    String offerStartLocalDate;
    String offerEndLocalDate;
    String ProductName;
    String SellerName;
    
    int CostPrice;
    int SellingPrice;
    int Discount;
    int Quantity;
    String Choicebox;
    String Specifications;
    String Description;
    int ProductId;
    Blob b ;
    String arr[];
    byte data[] =null;
        
    List<String> filesType;
    List<ImageView> imageViews;
    List<File> f;
    double xOffset,yOffset;
    
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView close;
    @FXML
    private TextField productName;
    @FXML
    private DatePicker mfDate;
    @FXML
    private DatePicker expDate;
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
    private DatePicker offerStartDate;
    @FXML
    private DatePicker offerEndDate;
    @FXML
    private TextArea specifications;
    @FXML
    private TextArea description;
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView4;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView2;
    @FXML
    private StackPane stackPane;
    private TextField category;
    @FXML
    private ImageView back;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ChoiceBox<String> choicebox;
    @FXML
    private Label des;
    @FXML
    private JFXButton resetButton;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton searchButton;
    @FXML
    private JFXButton resetSearchButton;
    @FXML
    private JFXButton resetImageButton;
    @FXML
    private JFXButton selectButton;
    @FXML
    private JFXButton deletProduct;

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
                  File f1=new File("C:\\Users\\Dell\\Documents\\NetBeansProjects\\BazzarServer\\Res\\no-image.png");
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
    private void mfDateShow(ActionEvent event) {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        mfLocalDate=mfDate.getValue().format(formatter);
    }

    @FXML
    private void expDateShow(ActionEvent event) {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        expLocalDate=expDate.getValue().format(formatter);
    }

    @FXML
    private void offerStartDateShow(ActionEvent event) {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        offerStartLocalDate=offerStartDate.getValue().format(formatter);
    }

    @FXML
    private void offerEndDateShow(ActionEvent event) {
       // offerEndLocalDate=offerEndDate.getValue();
       formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        offerEndLocalDate=offerEndDate.getValue().format(formatter);
    }

    @FXML
    private void resetButtonFunc(ActionEvent event) {
        
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
        
        try{
            String query1 = "UPDATE `products` SET `Name`=?,"
                            + "`ManfDate`=?,`ExpDate`=?,`Seller`=?,"
                            + "`Category`=?,`Quantity`=?,`CP`=?,`SP`=?,`Disc`=?,"
                            + "`Desc`=?,`Spec`=?,`Image1`=?,`Image2`=?,"
                            + "`Image3`=?,`Image4`=?,`OfferStartDate`=?,"
                            + "`OfferEndDate`=? WHERE ProId=?";
            ProductName = productName.getText();
            SellerName = sellerName.getText();
            CostPrice = parseInt(costPrice.getText());
            SellingPrice = parseInt(sellingPrice.getText());
            Discount = parseInt(discount.getText());
            Quantity = parseInt(quantity.getText());
            Choicebox = choicebox.getValue();
            Specifications = specifications.getText();
            Description = description.getText();
        
            ps = HandleClient.getConnection().prepareStatement(query1);
            ps.setString(1, ProductName);
            ps.setString(2, mfLocalDate);
            ps.setString(3, expLocalDate);
            ps.setString(4, SellerName);
            ps.setString(16, offerStartLocalDate);
            ps.setString(17, offerEndLocalDate);
            ps.setString(5, Choicebox);
            ps.setInt(6, Quantity);
            ps.setInt(7, CostPrice);
            ps.setInt(8, SellingPrice);
            ps.setInt(9, Discount);
            ps.setString(10, Description);
            ps.setString(11, Specifications);
            ps.setInt(18, ProductId);
            
            InputStream is = new FileInputStream(new File(arr[0]));
            ps.setBlob(12, is);
            
            InputStream is1 = new FileInputStream(new File(arr[1]));
            ps.setBlob(13, is1);
            
            InputStream is2 = new FileInputStream(new File(arr[2]));
            ps.setBlob(14, is2);
            
            InputStream is3 = new FileInputStream(new File(arr[3]));
            ps.setBlob(15, is3);
            
            if(ps.executeUpdate()>0)
            {
                JFXButton button=new JFXButton("Okay!");
                showDialog(stackPane,anchorPane,Arrays.asList(button),"product updated successfully",null);
            }

        }catch(Exception ex)
        {
            JFXButton button=new JFXButton("Okay!");
                showDialog(stackPane,anchorPane,Arrays.asList(button),"unable to save",null);
            
        }
    }

    @FXML
    private void back(MouseEvent event) {
        closeStage();
        loadMain("/bazzarserver/admin.fxml");
    }

    @FXML
    private void searchButtonFunc(ActionEvent event) {
        ProductName=productName.getText();
        try{
            String query="select * from products where Name=?";
            ps = HandleClient.getConnection().prepareStatement(query);
            ps.setString(1, ProductName);
            rs=ps.executeQuery();
            if(rs.next())
            {
                
                d= rs.getDate("ManfDate");
                mfLocalDate = d.toString();
             
                d= rs.getDate("ExpDate");
                expLocalDate = d.toString();
                
                d=rs.getDate("OfferStartDate");
                offerStartLocalDate=d.toString();
                
                d=rs.getDate("OfferEndDate");
                offerEndLocalDate=d.toString();
                
                ProductId=rs.getInt("ProId");
                
                SellerName = rs.getString("Seller");
                Choicebox = rs.getString("Category");
                Quantity = rs.getInt("Quantity");
                CostPrice = rs.getInt("CP");
                SellingPrice = rs.getInt("SP");
                Discount = rs.getInt("Disc");
                Description= rs.getString("Desc");
                Specifications = rs.getString("Spec");
                
                sellerName.setText(SellerName);
                choicebox.setValue(Choicebox);
                quantity.setText(Integer.toString(Quantity));
                costPrice.setText(Integer.toString(CostPrice));
                sellingPrice.setText(Integer.toString(SellingPrice));
                discount.setText(Integer.toString(Discount));
                description.setText(Description);
                specifications.setText(Specifications);
                
                mfDate.setValue(LOCAL_DATE(mfLocalDate));
                expDate.setValue(LOCAL_DATE(expLocalDate));
                offerStartDate.setValue(LOCAL_DATE(offerStartLocalDate));
                offerEndDate.setValue(LOCAL_DATE(offerEndLocalDate));

                    File file1=new File("C:\\Users\\Dell\\Documents\\NetBeansProjects\\BazzarServer\\Res\\1_no-image.png");
                    File file2=new File("C:\\Users\\Dell\\Documents\\NetBeansProjects\\BazzarServer\\Res\\2_no-image.png");
                    File file3=new File("C:\\Users\\Dell\\Documents\\NetBeansProjects\\BazzarServer\\Res\\3_no-image.png");
                    File file4=new File("C:\\Users\\Dell\\Documents\\NetBeansProjects\\BazzarServer\\Res\\4_no-image.png");
                    
                    arr[0]=file1.getAbsolutePath();
                    arr[1]=file2.getAbsolutePath();
                    arr[2]=file3.getAbsolutePath();
                    arr[3]=file4.getAbsolutePath();
                       
                    FileOutputStream os1=new FileOutputStream(file1);
                    FileOutputStream os2=new FileOutputStream(file2);
                    FileOutputStream os3=new FileOutputStream(file3);
                    FileOutputStream os4=new FileOutputStream(file4);
                            
                    
                    b= rs.getBlob("Image1");
                    data=b.getBytes(1,(int)b.length());
                    os1.write(data);
                    Image image1=new Image(file1.toURI().toString());
                    imageView1.setImage(image1);
                
                    b= rs.getBlob("Image2");
                    data=b.getBytes(1,(int)b.length());
                    os2.write(data);
                    Image image2=new Image(file2.toURI().toString());
                    imageView2.setImage(image2);
                
                    b= rs.getBlob("Image3");
                    data=b.getBytes(1,(int)b.length());
                    os3.write(data);
                    Image image3=new Image(file3.toURI().toString());
                    imageView3.setImage(image3);
                    
                    b= rs.getBlob("Image4");
                    data=b.getBytes(1,(int)b.length());
                    os4.write(data);
                    Image image4=new Image(file4.toURI().toString());
                    imageView4.setImage(image4);
                    
            }
        }catch(Exception e)
        {   JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    private void resetSearchButtonFunc(ActionEvent event) {
      
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
         File f=new File("C:\\Users\\Dell\\Documents\\NetBeansProjects\\BazzarServer\\Res\\no-image.png");
        Image image=new Image(f.toURI().toString());
        for(int i=0;i<imageViews.size();i++)
        {
            ImageView t= imageViews.get(i);
            t.setImage(image);
        }
    }

    
    //closing page
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

    @FXML
    private void deleteButtonFunc(ActionEvent event) {
    }
    
    public static final LocalDate LOCAL_DATE (String dateString){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(dateString, formatter);
    return localDate;
}
    
}
