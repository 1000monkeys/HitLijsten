package com.kjellvos.school.hitLijsten.Dialogs;

import com.kjellvos.school.hitLijsten.MainMenu;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 * Created by kjevo on 6/24/17.
 */
public class NothingSelected {
    private Alert dialog;

    public NothingSelected(){
        dialog = new Alert(Alert.AlertType.ERROR);

        dialog.setTitle("Error Dialog");
        dialog.setHeaderText("Je hebt niks geselecteerd!");
        dialog.setContentText("Je moet natuurlijk wel wat selecteren in het tabel!");
        dialog.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));
    }

    public void showDialog(){
        dialog.showAndWait();
    }
}
