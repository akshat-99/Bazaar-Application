package bazaarnew;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class main extends Application {
    
    public static int globalCustId;
     public static int pId;
    public static String globalCategory;
    public static boolean globalIslogged;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    
    public static void main(String[] args) {
        
        globalIslogged=false;
        launch(args);
    }
    
    
    
    public static void setCustId(int c)
    {
        globalCustId=c;
    }
    
    public static void setCategory(String c)
    {
        globalCategory=c;
    }
    
    public static void setIsLogged()
    {
        globalIslogged=true;
    }
    
     public static void setPId(int c)
    {
        pId=c;
       }
}
