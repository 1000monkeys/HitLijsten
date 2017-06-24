package com.kjellvos.school.hitLijsten.Dialogs;

import com.kjellvos.school.hitLijsten.MainMenu;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

/**
 * Created by kjevo on 6/24/17.
 */
public class WrongInput {
    private Alert dialog;

    public WrongInput(String text){
        dialog = new Alert(Alert.AlertType.ERROR);

        dialog.setTitle("Error Dialog");
        dialog.setHeaderText("Verkeerde input!");
        dialog.setContentText(text);
        dialog.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));
    }

    public void showDialog(){
        dialog.showAndWait();
    }
}
