/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alert;

import java.util.List;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Akshat
 */
public class Alert {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    
    public static void showDialog(StackPane pane,Node toBeBlurred,List<JFXButton> controls,String s,String h)
    {
                    BoxBlur blur=new BoxBlur(3,3,3);
                    JFXDialogLayout dialogLayout=new JFXDialogLayout();
                    JFXDialog dialog=new JFXDialog(pane, dialogLayout, JFXDialog.DialogTransition.TOP, false);
                    //dialog.getScene().getStylesheets().add("dialog.css");
                    controls.forEach(controlButton->{
                        controlButton.setStyle("-fx-background-color:#FF0000;-fx-background-radius: 5px;-fx-border-radius: 5px;-fx-font-color:white;");
                        controlButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) ->{
                            dialog.close();
                          });
                    });
                    //button.getStyleClass().add("fx-dialog-text")
                    dialogLayout.setBody(new Label(s));
                    dialogLayout.setHeading(new Label(h));
                    dialogLayout.setActions(controls);
                    dialog.show();
                    toBeBlurred.setEffect(blur);
                    dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
                        toBeBlurred.setEffect(null);
                    });
    }
    
}
