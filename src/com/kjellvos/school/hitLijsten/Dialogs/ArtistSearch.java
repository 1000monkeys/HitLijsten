package com.kjellvos.school.hitLijsten.Dialogs;

import com.kjellvos.school.hitLijsten.MainMenu;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 * Created by kjevo on 6/24/17.
 */
public class ArtistSearch {
    private MainMenu mainMenu;

    private Dialog<Pair<String, String>> dialog;
    private final boolean[] yes = {false};
    private TextField zoekTextField;

    public ArtistSearch(MainMenu mainMenu){
        this.mainMenu = mainMenu;
        dialog = new Dialog<>();

        zoekTextField = new TextField();

        ButtonType OkButtonType = new ButtonType("Ingevoerd!", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(OkButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(25);
        gridPane.setVgap(25);
        gridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        gridPane.add(new Label("Artiest naam:"), 0, 0, 1, 1);
        gridPane.add(zoekTextField, 1, 0, 1, 1);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == OkButtonType) {
                yes[0] = true;
            }
            return null;
        });

        dialog.getDialogPane().setContent(gridPane);
    }

    public void showDialog(){
        dialog.showAndWait();
        if (yes[0]) {
            //check input
            mainMenu.artistSearch(zoekTextField.getText());
        }else{
            //abort
        }
    }
}
