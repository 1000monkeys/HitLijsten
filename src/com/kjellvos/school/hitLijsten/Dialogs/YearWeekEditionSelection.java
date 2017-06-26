package com.kjellvos.school.hitLijsten.Dialogs;

import com.kjellvos.school.hitLijsten.MainMenu;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 * Created by kjevo on 6/24/17.
 */
public class YearWeekEditionSelection {
    private MainMenu mainMenu;

    private Dialog<Pair<String, String>> dialog;
    private final boolean[] yes = {false};
    private TextField jaarTextField, weekTextField;
    private ChoiceBox editieChoiceBox;

    public YearWeekEditionSelection(MainMenu mainMenu){
        this.mainMenu = mainMenu;
        dialog = new Dialog<>();

        jaarTextField = new TextField();
        weekTextField = new TextField();
        editieChoiceBox= new ChoiceBox(FXCollections.observableArrayList("Top 40", "Tipparade"));

        ButtonType OkButtonType = new ButtonType("Invoeren!", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(OkButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(25);
        gridPane.setVgap(25);
        gridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        gridPane.add(new javafx.scene.control.Label("Jaar:"), 0, 0, 1, 1);
        gridPane.add(jaarTextField, 1, 0, 1, 1);
        gridPane.add(new javafx.scene.control.Label("Week:"), 0, 1, 1, 1);
        gridPane.add(weekTextField, 1, 1, 1, 1);
        gridPane.add(new javafx.scene.control.Label("Editie:"), 0, 2, 1, 1);
        gridPane.add(editieChoiceBox, 1, 2, 1, 1);

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
            int year = Integer.parseInt(jaarTextField.getText());
            int week = Integer.parseInt(weekTextField.getText());
            int edition = editieChoiceBox.getSelectionModel().getSelectedIndex();
            if (year < 2016 && year > 1964) {
                if (week < 54 && week > 0) {
                    if (edition == 1 || edition == 2) {
                        if (mainMenu.getDatabase().validWeekYearEdition(week, year, edition)) {
                            mainMenu.setTop40(week, year, edition);
                        }else{
                            WrongInput wrongInput = new WrongInput("Ongeldige input! Er is voor deze datum en editie geen lijst te vinden!");
                            wrongInput.showDialog();
                        }
                    }else{
                        WrongInput wrongInput = new WrongInput("Verkeerde input voor editie! Selecteer 1 van de 2 opties!");
                        wrongInput.showDialog();
                    }
                }else{
                    WrongInput wrongInput = new WrongInput("Verkeerde input voor week! Gebruik waarde tussen de 0 en 54!");
                    wrongInput.showDialog();
                }
            }else{
                WrongInput wrongInput = new WrongInput("Verkeerde input voor jaar! Gebruik waarde tussen de 1964 en 2016!");
                wrongInput.showDialog();
            }
        }
    }
}
