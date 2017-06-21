package com.kjellvos.school.hitLijsten;

import com.kjellvos.school.hitLijsten.common.Extensions.MainScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private MainScene mainScene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainScene = new MainMenu(primaryStage);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
