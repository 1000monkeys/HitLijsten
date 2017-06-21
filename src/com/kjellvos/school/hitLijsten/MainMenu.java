package com.kjellvos.school.hitLijsten;

import com.kjellvos.os.gridHandler.GridHandler;
import com.kjellvos.school.hitLijsten.common.Database;
import com.kjellvos.school.hitLijsten.common.Extensions.MainScene;
import com.kjellvos.school.hitLijsten.common.interfaces.SceneImplementation;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by kjell on 19-6-2017.
 */
public class MainMenu extends MainScene implements SceneImplementation {
    private Database database;

    private GridHandler gridHandler;

    private Scene scene;

    private Top40 top40;
    private Artiesten artiesten;
    private Singles singles;

    private Button top40Button, artiestenButton, singleButton;

    public MainMenu(Stage stage) throws SQLException {
        super(stage);
        database = new Database(this);
        top40 = new Top40(this);

        getPrimaryStage().setTitle("Hitdossier");
        getPrimaryStage().setWidth(800D);
        getPrimaryStage().setHeight(600D);
        getPrimaryStage().setResizable(false);
        getPrimaryStage().show();
        changeScene(this);
    }

    @Override
    public Stage getPrimaryStage() {
        return super.getPrimaryStage();
    }

    public Database getDatabase(){
        return database;
    }

    @Override
    public Scene createAndGetScene() {
        gridHandler = new GridHandler();

        top40Button = new Button("Top 40!");
        top40Button.setOnMouseClicked(event -> {
            showYearWeekEditionSelection();
        });
        artiestenButton = new Button("Artiesten");
        artiestenButton.setOnMouseClicked(event -> {
            artiestenSearch();
        });
        singleButton = new Button("Single's");
        singleButton.setOnMouseClicked(event -> {
            titelSearch();
        });

        gridHandler.add(0, 0, top40Button, 1, 1, false);
        gridHandler.add(0, 1, artiestenButton, 1, 1, false);
        gridHandler.add(0, 2, singleButton, 1, 1, false);

        scene = gridHandler.getGridAsScene();
        return scene;
    }

    public void showYearWeekEditionSelection() {
        TextField jaarTextField, weekTextField, editieTextField;

        final boolean[] yes = {false};

        jaarTextField = new TextField();
        weekTextField = new TextField();
        editieTextField = new TextField();

        Dialog<Pair<String, String>> dialog = new Dialog<>();

        ButtonType OkButtonType = new ButtonType("Ingevoerd!", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(OkButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(25);
        gridPane.setVgap(25);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.add(new Label("Jaar:"), 0, 0, 1, 1);
        gridPane.add(jaarTextField, 1, 0, 1, 1);
        gridPane.add(new Label("Week:"), 0, 1, 1, 1);
        gridPane.add(weekTextField, 1, 1, 1, 1);
        gridPane.add(new Label("Editie:"), 0, 2, 1, 1);
        gridPane.add(editieTextField, 1, 2, 1, 1);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == OkButtonType) {
                yes[0] = true;
            }
            return null;
        });

        dialog.getDialogPane().setContent(gridPane);

        Optional<Pair<String, String>> result = dialog.showAndWait();
        if (yes[0]) {
            //check input
            top40 = new Top40(this);
            top40.setEditieJaarWeek(Integer.parseInt(jaarTextField.getText()), Integer.parseInt(weekTextField.getText()), Integer.parseInt(editieTextField.getText()));
            changeScene(top40);
        }else{
            //abort
        }
    }

    public void artiestenSearch(){
        artiesten = new Artiesten(this);
        changeScene(artiesten);
    }

    public void titelSearch(){
        singles = new Singles(this);
        changeScene(singles);
    }

    @Override
    public void reload() {

    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
