/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazaarnew;

import static alert.Alert.showDialog;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.list;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * FXML Controller class
 *
 * @author KRITANK
 */
public class HomeController implements Initializable {

    int CustId;
    double xOffset,yOffset;
    Socket socket;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    int switchCase;
    String s="";
    int result;
    int pId;
    String Productname;
    int sp;
    int disc;
    String spec;
    List <Integer>arr;
    
    @FXML
    private StackPane stackPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane topAnchorPane;
    @FXML
    private ImageView close;
    @FXML
    private ImageView min;
    @FXML
    private MenuItem fashionMenuItem;
    @FXML
    private MenuItem furnitureMenuItem;
    @FXML
    private MenuItem groceryMenuItem;
    private Menu wishlistMenuButton;
    @FXML
    private Label wishlist;
    @FXML
    private Label orders;
    @FXML
    private ScrollPane scrollPane;
    GridPane grid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CustId=main.globalCustId;
        
        arr=new ArrayList<>();
        
        //showing alert for special Offer
        DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String Date=dateFormatter.format(java.time.LocalDate.now());
        System.out.println(Date);
        
        
        try{
            s="No Connection!";
            System.out.println("reached 1\n");
            String query="SELECT CustId FROM register WHERE  ? BETWEEN coalesce(`SpecialDiscountStartDate`,?) AND coalesce(`SpecialDiscountEndDate`,?);";
            
            System.out.println("reached 2\n");
            s="Server Not Started Yet!";
            
            socket=new Socket("localhost",5436);
            System.out.println("socket connected 101");
            
            objectOutputStream =new ObjectOutputStream(socket.getOutputStream());
            objectInputStream =new ObjectInputStream(socket.getInputStream()); 
            
            System.out.println("StartServer worked");
            //catch
            
            int switchCase = 10;
            objectOutputStream.writeObject(switchCase);
            objectOutputStream.writeObject(query);
            objectOutputStream.writeObject(Date);
            System.out.println(main.globalCustId);
            objectOutputStream.writeObject(main.globalCustId);
            System.out.println("reached 3\n");
            result=(int)objectInputStream.readObject();
            if(main.globalIslogged==false)
            {
              main.setIsLogged();
            if(result==1)
            {
                JFXButton button=new JFXButton("Okay!");
                showDialog(stackPane,borderPane,Arrays.asList(button),"You are eligible for special discount",null);
                //JOptionPane.showMessageDialog(null, "Congratulations You are eligible for special discount");
            }
            }
       }         
       catch(Exception e)
       {
           JOptionPane.showMessageDialog(null, e);
       }
        
        
        File file=new File("C:\\Users\\Dell\\Documents\\NetBeansProjects\\bazaarnew\\Res\\no-image.png");
        Image img=new Image(file.toURI().toString());
        grid=new GridPane();
        grid.setVgap(20);
        grid.setHgap(10);
        grid.setPadding(new Insets(20,20,20,20));
        grid.setAlignment(Pos.CENTER);
        
        try
        {
            s="No Connection!";
             System.out.println("reached 1\n");
             String sql = "select * from products";
            
            s="Server Not Started Yet!";
            socket=new Socket("localhost",5436);
            System.out.println("socket connected 101");
            
            objectOutputStream =new ObjectOutputStream(socket.getOutputStream());
            objectInputStream =new ObjectInputStream(socket.getInputStream()); 
            System.out.println("StartServer worked");
            //catch
           
            switchCase=14;
            objectOutputStream.writeObject(switchCase);
            objectOutputStream.writeObject(sql);
            int k=0;
            do
           {
                result=(int)objectInputStream.readObject();
                if(result!=1) break;
                
                System.out.println("reached 4\n");
                Productname = (String)objectInputStream.readObject();
                sp = (int)objectInputStream.readObject();
                disc = (int)objectInputStream.readObject();
                spec = (String)objectInputStream.readObject();
                arr.add((Integer)objectInputStream.readObject());
               
                
                int j=0;
                ImageView view1 = new ImageView(img);
                view1.setFitWidth(200);
                view1.setFitHeight(200);
                grid.add(view1,j,k);
                
//                ImageView view2 = new ImageView(img);
//                view2.setFitWidth(200);
//                view2.setFitHeight(200);
//                grid.add(view2,j+1,k);
                
//                ImageView view3 = new ImageView(img);
//                view3.setFitWidth(200);
//                view3.setFitHeight(200);
//                grid.add(view3,j+2,k);
                              
                File file1=new File("C:\\Users\\Dell\\Documents\\NetBeansProjects\\BazzarServer\\Res\\1_no-image.png");
                File file2=new File("C:\\Users\\Dell\\Documents\\NetBeansProjects\\BazzarServer\\Res\\2_no-image.png");
                File file3=new File("C:\\Users\\Dell\\Documents\\NetBeansProjects\\BazzarServer\\Res\\3_no-image.png");
//                File file4=new File("C:\\Users\\acer\\Documents\\NetBeansProjects\\BazzarServer\\Res\\4_no-image.png");
                
                FileOutputStream os1=new FileOutputStream(file1);
                FileOutputStream os2=new FileOutputStream(file2);
                FileOutputStream os3=new FileOutputStream(file3);
//                FileOutputStream os4=new FileOutputStream(file4);
                
                byte data1[] =null, data2[] =null,data3[] =null, data4[] =null;
                data1=(byte[])objectInputStream.readObject();
               
                os1.write(data1);
                Image image1=new Image(file1.toURI().toString());
                view1.setImage(image1);
                
                data2=(byte[])objectInputStream.readObject();
                os2.write(data2);
//                Image image2=new Image(file2.toURI().toString());
//                view2.setImage(image2);
                
                data3=(byte[])objectInputStream.readObject();
                os3.write(data3);
//                Image image3=new Image(file3.toURI().toString());
//                view3.setImage(image3);
                
                Label l = new Label("product: "+Productname+"\nPrice: "+Integer.toString(sp)+"\nDisc: "+Integer.toString(disc));
                l.setPrefHeight(120);
                l.setPrefWidth(400);
                grid.add(l,j+1,k);
                
                TextArea t = new TextArea("specifications: "+spec);
                t.setPrefHeight(120);
                t.setPrefWidth(600);
                grid.add(t,j+2,k);
                  
                k++;
                
                view1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    int s = GridPane.getRowIndex(view1);
                    System.out.println("currently on row - "+s);
                    System.out.println(arr.get(s));
//                    event.consume();
                    main.setPId(arr.get(s));
                    System.out.println("classification");
                    
                    loadMain("/bazaarnew/product.fxml");
                    
                    }
                });
                    
            }while(result==1);       
       }
      //multicatch statement
      catch(Exception e){
                    //JFXButton button=new JFXButton("Okay!");
                    //showDialog(stackPane,rootAnchor,Arrays.asList(button),s,null);
                    System.out.println("reached display catch");
                    System.out.println("reached 8\n"); 
      }
        
        
        scrollPane.setContent(grid);
    
    }    
    
    public void setCustId(int id)
    {
        CustId=id;
    }

    @FXML
    private void close(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void mini(MouseEvent event) {
        ((Stage)stackPane.getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void fashionMenuItemFunc(ActionEvent event) {
        System.out.println("fashion");
          closeStage();
        //add change load function pass string to determine which frame to open
         main.globalCategory="Women's Fashion";
         System.out.println(main.globalCategory);
        loadMain("/bazaarnew/category.fxml");
    }

    @FXML
    private void furnitureMenuItemFunc(ActionEvent event) {
          closeStage();
        //add change load function pass string to determine which frame to open
         main.globalCategory="Home Appliances";
         System.out.println(main.globalCategory);
        loadMain("/bazaarnew/category.fxml");
    }

    @FXML
    private void groceryMenuItemFunc(ActionEvent event) {
          closeStage();
        //add change load function pass string to determine which frame to open
        main.globalCategory="Grocery";
        System.out.println(main.globalCategory);
        loadMain("/bazaarnew/category.fxml");
    }

    @FXML
    private void orderMenuButtonFunc(MouseEvent event) {
        System.out.println("order");
        closeStage();
        loadMain("/bazaarnew/order.fxml");
    }

    @FXML
    private void wishlistMenuButtonFunc(MouseEvent event) {
        System.out.println("wishlist");
        closeStage();
        loadMain("/bazaarnew/wishlist.fxml");
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
    private void cartMenuButtonFunc(MouseEvent event) {
         System.out.println("cart");
        closeStage();
        loadMain("/bazaarnew/cart.fxml");
    }
    
   
}

