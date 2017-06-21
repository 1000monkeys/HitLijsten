package com.kjellvos.school.hitLijsten;

import com.kjellvos.os.gridHandler.GridHandler;
import com.kjellvos.school.hitLijsten.common.DBClasses.Notering;
import com.kjellvos.school.hitLijsten.common.interfaces.SceneImplementation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by kjell on 19-6-2017.
 */
public class Artiesten implements SceneImplementation {
    private MainMenu mainMenu;

    private GridHandler gridHandler;
    private Scene scene;

    private TableView tableView;
    private TableColumn artiestTableColumn, singleTableColumn, noteringTableColumn, yearTableColumn, weekTableColumn;

    private Button backToLastMenuButton, top40Button, artiestenButton, singleButton;
    private TextField zoekTextField;
    private Button zoekButton;

    public Artiesten(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    @Override
    public Scene createAndGetScene() {
        gridHandler = new GridHandler();

        backToLastMenuButton = new Button("Terug naar vorig menu.");
        backToLastMenuButton.setOnMouseClicked(event -> {
            mainMenu.returnToPreviousScene();
        });

        tableView = new TableView();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setEditable(false);

        noteringTableColumn = new TableColumn("Notering");
        noteringTableColumn.setCellValueFactory(new PropertyValueFactory<Notering, Integer>("positie"));

        yearTableColumn = new TableColumn("Year");
        yearTableColumn.setCellValueFactory(new PropertyValueFactory<Artiesten, Integer>("year"));

        weekTableColumn = new TableColumn("Week");
        weekTableColumn.setCellValueFactory(new PropertyValueFactory<Artiesten, Integer>("week"));

        artiestTableColumn = new TableColumn("Artiest");
        artiestTableColumn.setCellValueFactory(new PropertyValueFactory<Notering, String>("artiestNaam"));

        singleTableColumn = new TableColumn("Single");
        singleTableColumn.setCellValueFactory(new PropertyValueFactory<Notering, String>("singleNaam"));

        tableView.getColumns().addAll(noteringTableColumn, yearTableColumn, weekTableColumn, artiestTableColumn, singleTableColumn);

        zoekTextField = new TextField();

        zoekButton = new Button("Zoeken");
        zoekButton.setOnMouseClicked(event -> {
            tableView.setItems(mainMenu.getDatabase().getNotatieBijArtiest(zoekTextField.getText()));
        });

        top40Button = new Button("Top 40");
        top40Button.setOnMouseClicked(event -> {
            mainMenu.showYearWeekEditionSelection();
        });
        singleButton = new Button("Single's");
        singleButton.setOnMouseClicked(event -> {
            mainMenu.titelSearch();
        });

        gridHandler.add(0, 0, backToLastMenuButton, 9, 1, false);

        gridHandler.add(0, 1, new Label("Zoeken op artiest naam:"), 3, 1, false);
        gridHandler.add(4, 1, zoekTextField, 3, 1, false);
        gridHandler.add(7, 1, zoekButton, 1, 1, false);

        gridHandler.add(0, 2, tableView, 8, 7, false);

        gridHandler.add(8, 1, top40Button, 1, 4, false);
        gridHandler.add(8, 5, singleButton, 1, 4, false);
        scene = gridHandler.getGridAsScene();
        return scene;
    }

    @Override
    public void reload() {
        //don't do anything
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
