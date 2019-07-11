/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazaarnew;

import com.jfoenix.controls.JFXTextArea;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class ProductController implements Initializable {

    double xOffset,yOffset;
    Socket socket;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    int switchCase;
    String s="";
    int result;
    String Productnam;
    int sp;
    int disc;
    String manf;
    String expiry;
    String sellernam;
    String cat;
    int off;
    String specs;
    String descs;
    List<ImageView> imageViews;
    List<File> f;
    File file1,file2,file3,file4;
    
    
    
    @FXML
    private ImageView imageView1;
    @FXML
    private Button addToCartButton;
    @FXML
    private Label productName;
    @FXML
    private Label manufacturingDate;
    @FXML
    private Label expiryDate;
    @FXML
    private Label seller;
    @FXML
    private Label category;
    @FXML
    private ImageView imageView4;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView2;
    @FXML
    private Label price;
    @FXML
    private Label discount;
    @FXML
    private Label offerPrice;
    @FXML
    private JFXTextArea specifications;
    @FXML
    private JFXTextArea descriptions;
    @FXML
    private Button buyButton;
    @FXML
    private Button addToWishlistButton;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //adding imageView to the list
        imageViews=new ArrayList<>();
        imageViews.add(imageView1);
        imageViews.add(imageView2);
        imageViews.add(imageView3);
        imageViews.add(imageView4);
        
        System.out.println(main.pId);
        
        try
        {
            s="No Connection!";
             System.out.println("reached 1\n");
             String sql = "select * from products  where ProId=?";
            
            s="Server Not Started Yet!";
            socket=new Socket("localhost",5436);
            System.out.println("socket connected 101");
            
            objectOutputStream =new ObjectOutputStream(socket.getOutputStream());
            objectInputStream =new ObjectInputStream(socket.getInputStream()); 
            System.out.println("StartServer worked");
            //catch
           
            switchCase=16;
            objectOutputStream.writeObject(switchCase);
            objectOutputStream.writeObject(sql);
            objectOutputStream.writeObject(main.pId);
                
                System.out.println("reached 4\n");
                
                Productnam = (String)objectInputStream.readObject();
                manf = (String)objectInputStream.readObject();
                expiry = (String)objectInputStream.readObject();
                sellernam = (String)objectInputStream.readObject();
                cat = (String)objectInputStream.readObject();
                sp = (int)objectInputStream.readObject();
                disc = (int)objectInputStream.readObject();
                off=sp-(sp*disc)/100;
                specs = (String)objectInputStream.readObject();
                descs = (String)objectInputStream.readObject();
                
                //setting
                productName.setText(Productnam);
                manufacturingDate.setText(manf);
                expiryDate.setText(expiry);
                seller.setText(sellernam);
                category.setText(cat);
                price.setText(Integer.toString(sp));
                discount.setText(Integer.toString(disc));
                offerPrice.setText(Integer.toString(off));
                specifications.setText(specs);
                descriptions.setText(descs);
                
                file1=new File("C:\\Users\\Dell\\Documents\\NetBeansProjects\\BazzarServer\\Res\\1_no-image.png");
                file2=new File("C:\\Users\\Dell\\Documents\\NetBeansProjects\\BazzarServer\\Res\\2_no-image.png");
                file3=new File("C:\\Users\\Dell\\Documents\\NetBeansProjects\\BazzarServer\\Res\\3_no-image.png");
                file4=new File("C:\\Users\\Dell\\Documents\\NetBeansProjects\\BazzarServer\\Res\\4_no-image.png");
                
                FileOutputStream os1=new FileOutputStream(file1);
                FileOutputStream os2=new FileOutputStream(file2);
                FileOutputStream os3=new FileOutputStream(file3);
                FileOutputStream os4=new FileOutputStream(file4);
                
                byte data1[] =null, data2[] =null,data3[] =null, data4[] =null;
                data1=(byte[])objectInputStream.readObject();
               
                os1.write(data1);
                Image image1=new Image(file1.toURI().toString());
                imageView1.setImage(image1);
                
                data2=(byte[])objectInputStream.readObject();
                os2.write(data2);
                Image image2=new Image(file2.toURI().toString());
                imageView2.setImage(image2);
                
                data3=(byte[])objectInputStream.readObject();
                os3.write(data3);
                Image image3=new Image(file3.toURI().toString());
                imageView3.setImage(image3);
                
                data4=(byte[])objectInputStream.readObject();
                os4.write(data4);
                Image image4=new Image(file4.toURI().toString());
                imageView4.setImage(image4);
                System.out.println("all set here");
                
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
               
       //adding files
       f=new ArrayList<>();
       f.add(file1);
       f.add(file2);
       f.add(file3);
       f.add(file4);
        
    }    

    @FXML
    private void addToCartButtonFunc(ActionEvent event) {
        //reset button called and entries entered to the cart
        System.out.println("AddToCartButton");
         try {
             s="No Connection!";
            System.out.println("reached 111\n");
            String sql = "INSERT INTO `cart`(`CustId`,`ProId` ,`ProdName`,`Quantity`, `Cost`, `Seller`) VALUES (?,?,?,?,?,?)";
            System.out.println("reached 2222\n");
            s="Server Not Started Yet!";
            socket=new Socket("localhost",5436);
            System.out.println("socket connected 101");
            objectOutputStream =new ObjectOutputStream(socket.getOutputStream());
            objectInputStream =new ObjectInputStream(socket.getInputStream()); 
            System.out.println("StartServer worked");
            //catch
            
            switchCase = 17;
            objectOutputStream.writeObject(switchCase);
            objectOutputStream.writeObject(sql);
             System.out.println(main.globalCustId);
            objectOutputStream.writeObject(main.globalCustId);
            objectOutputStream.writeObject(main.pId);
            objectOutputStream.writeObject(Productnam);
            objectOutputStream.writeObject(sp);
            objectOutputStream.writeObject(sellernam);
            
            System.out.println("reached 3333\n");
            result=(int)objectInputStream.readObject();
            System.out.println(result);
            }
            
            catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
            }
    }

   
    @FXML
    private void addToWishlistButtonFunc(ActionEvent event) {
        
    }

    @FXML
    private void close(MouseEvent event) {
       closeStage();
    }

    @FXML
    private void back(MouseEvent event) {
        closeStage();
    }
    
    private void closeStage(){
        ((Stage)imageView1.getScene().getWindow()).close();
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
}
