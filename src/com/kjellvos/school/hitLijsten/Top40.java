package com.kjellvos.school.hitLijsten;

import com.kjellvos.os.gridHandler.GridHandler;
import com.kjellvos.school.hitLijsten.common.DBClasses.Notering;
import com.kjellvos.school.hitLijsten.common.interfaces.SceneImplementation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by kjell on 19-6-2017.
 */
public class Top40 implements SceneImplementation {
    private MainMenu mainMenu;

    private Scene scene;
    private GridHandler gridHandler;

    private TableView tableView;
    private TableColumn artiestTableColumn, singleTableColumn, noteringTableColumn;

    private Button backToLastMenuButton, top40Button, artiestenButton, singleButton;

    private int jaar, week, editie;

    public Top40(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void setEditieJaarWeek(int jaar, int week, int editie){
        this.jaar = jaar;
        this.week = week;
        this.editie = editie;
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

        artiestTableColumn = new TableColumn("Artiest");
        artiestTableColumn.setCellValueFactory(new PropertyValueFactory<Notering, String>("artiestNaam"));

        singleTableColumn = new TableColumn("Single");
        singleTableColumn.setCellValueFactory(new PropertyValueFactory<Notering, String>("singleNaam"));

        tableView.getColumns().addAll(noteringTableColumn, artiestTableColumn, singleTableColumn);

        tableView.setItems(mainMenu.getDatabase().getNotatieBijEditie(week, jaar, editie));

        top40Button = new Button("Top 40");
        top40Button.setOnMouseClicked(event -> {
            mainMenu.showYearWeekEditionSelection();
        });
        artiestenButton = new Button("Artiesten");
        artiestenButton.setOnMouseClicked(event -> {
            mainMenu.artiestenSearch();
        });
        singleButton = new Button("Single's");
        singleButton.setOnMouseClicked(event -> {
            mainMenu.titelSearch();
        });

        gridHandler.add(0, 0, backToLastMenuButton, 9, 1, false);

        gridHandler.add(0, 1, tableView, 8, 9, false);

        gridHandler.add(8, 1, top40Button, 1, 3, false);
        gridHandler.add(8, 4, artiestenButton, 1, 3, false);
        gridHandler.add(8, 7, singleButton, 1, 3, false);

        scene = gridHandler.getGridAsScene();
        return scene;
    }

    @Override
    public void reload() {
        //TODO RELOAD DATA
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
