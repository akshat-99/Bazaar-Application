/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazaarnew;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import static javafx.scene.layout.GridPane.getRowIndex;

/**
 *
 * @author Dell
 */
public class grid {

    @FXML
    private GridPane grids;
    @FXML
    private Button button2;
    @FXML
    private Button button1;

    @FXML
    private void gridClicked(MouseEvent event) {
        int i = getRowIndex(button1);
    }
    
}
