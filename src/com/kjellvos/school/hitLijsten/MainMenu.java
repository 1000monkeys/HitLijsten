package com.kjellvos.school.hitLijsten;

import com.kjellvos.os.gridHandler.GridHandler;
import com.kjellvos.school.hitLijsten.Dialogs.ArtistSearch;
import com.kjellvos.school.hitLijsten.Dialogs.NothingSelected;
import com.kjellvos.school.hitLijsten.Dialogs.TitelSearch;
import com.kjellvos.school.hitLijsten.Dialogs.YearWeekEditionSelection;
import com.kjellvos.school.hitLijsten.common.DBClasses.Notation;
import com.kjellvos.school.hitLijsten.common.Database;
import com.kjellvos.school.hitLijsten.common.Extensions.MainScene;
import com.kjellvos.school.hitLijsten.common.interfaces.SceneImplementation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.text.*;
import javax.swing.text.TableView;
import java.sql.SQLException;

/**
 * Created by kjell on 19-6-2017.
 */
public class MainMenu extends MainScene implements SceneImplementation {
    private Database database;

    private GridHandler gridHandler;

    private Scene scene;

    private Notations notations;

    private MenuBar menuBar;
    private Label backToLastMenuLabel, wholeListMenuLabel, label;
    private Menu top40Menu, singlesMenu, artiestenMenu, backtoLastMenuMenu, wholeListMenu;
    private MenuItem top40LijstByDateMenuItem, top40LijstHoogsteNoteringCurrentSingle;
    private MenuItem singlesByCurrentArtist, singlesSearchOnArtist, singlesSearch;
    private MenuItem artistSearch;

    public MainMenu(Stage stage) throws SQLException {
        super(stage);
        database = new Database(this);

        getPrimaryStage().setTitle("Hitdossier");
        getPrimaryStage().setWidth(800D);
        getPrimaryStage().setHeight(600D);
        //getPrimaryStage().setResizable(false);
        getPrimaryStage().show();
        changeScene(this);
        notations();
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
        //TODO!!
        gridHandler = new GridHandler();
        gridHandler.menuBar(true, 25D);

        label = new Label("Gebruik het menu hierboven!");
        label.setAlignment(Pos.CENTER);

        gridHandler.add(0, 0, label, 1, 1, false);

        scene = gridHandler.getGridAsScene();
        ((Pane) scene.getRoot()).getChildren().addAll(createAndGetMenuBar());
        return scene;
    }

    public MenuBar createAndGetMenuBar(){
        menuBar = new MenuBar();
        menuBar.setPrefHeight(25D);
        menuBar.prefWidthProperty().bind(getPrimaryStage().widthProperty());

        backtoLastMenuMenu = new Menu();
        backToLastMenuLabel = new Label("Terug naar vorig menu");
        backToLastMenuLabel.setOnMouseClicked(event -> {
            returnToPreviousScene();
        });
        backtoLastMenuMenu.setGraphic(backToLastMenuLabel);


        wholeListMenu = new Menu();
        wholeListMenuLabel = new Label("De gehele lijst");
        wholeListMenuLabel.setOnMouseClicked(event -> {
            notations();
        });
        wholeListMenu.setGraphic(wholeListMenuLabel);


        top40Menu = new Menu("Top 40");

        top40LijstByDateMenuItem = new MenuItem("Top 40 bij datum");
        top40LijstByDateMenuItem.setOnAction(event -> {
            YearWeekEditionSelection yearWeekEditionSelection = new YearWeekEditionSelection(this);
            yearWeekEditionSelection.showDialog();
        });

        top40LijstHoogsteNoteringCurrentSingle = new MenuItem("Hoogste notering bij deze single");
        top40LijstHoogsteNoteringCurrentSingle.setOnAction(event -> {
            Notation notation = getCurrentScene().getSelectedItem();
            if (notation != null) {
                highestNotationSingle(notation.singleIdProperty().get());
            }else{
                NothingSelected nothingSelected = new NothingSelected();
                nothingSelected.showDialog();
            }
        });

        top40Menu.getItems().addAll(top40LijstByDateMenuItem, top40LijstHoogsteNoteringCurrentSingle);


        singlesMenu = new Menu("Notaties");

        singlesByCurrentArtist = new MenuItem("Singles van geselecteerde artiest");
        singlesByCurrentArtist.setOnAction(event -> {
            Notation notation = getCurrentScene().getSelectedItem();
            if (notation != null) {
                artistSearch(notation.artiestNaamProperty().get());
            }else{
                NothingSelected nothingSelected = new NothingSelected();
                nothingSelected.showDialog();
            }
        });

        singlesSearchOnArtist = new MenuItem("Zoeken op artiest daarvan alle singles");
        singlesSearchOnArtist.setOnAction(event -> {
            ArtistSearch artistSearch = new ArtistSearch(this);
            artistSearch.showDialog();
        });

        singlesSearch = new MenuItem("Zoeken op single naam");
        singlesSearch.setOnAction(event -> {
            TitelSearch titelSearch = new TitelSearch(this);
            titelSearch.showDialog();
        });

        singlesMenu.getItems().addAll(singlesByCurrentArtist, singlesSearchOnArtist, singlesSearch);


        artiestenMenu = new Menu("Artiesten");

        artistSearch = new MenuItem("Zoeken op artiest naam");
        artistSearch.setOnAction(event -> {
            ArtistSearch artistSearch = new ArtistSearch(this);
            artistSearch.showDialog();
        });

        artiestenMenu.getItems().add(artistSearch);

        menuBar.getMenus().addAll(top40Menu, singlesMenu, artiestenMenu, wholeListMenu, backtoLastMenuMenu);
        return menuBar;
    }

    private void highestNotationSingle(int id) {
        notations = new Notations(this);
        notations.setData(database.getTopListings(id));
        changeScene(notations);
    }

    public void notations(){
        notations = new Notations(this);
        notations.setData(database.getListings());
        changeScene(notations);
    }

    public void artistSearch(String naam){
        notations = new Notations(this);
        notations.setData(database.getNotations(naam, Main.artists));
        changeScene(notations);
    }

    public void titelSearch(String naam){
        notations = new Notations(this);
        notations.setData(database.getNotations(naam, Main.singles));
        changeScene(notations);
    }

    public void setTop40(int week, int year, int edition){
        notations = new Notations(this);
        notations.setData(database.getNotationByEdition(week, year, edition));
        changeScene(notations);
    }

    @Override
    public void reload() {

    }

    @Override
    public Scene getScene() {
        return scene;
    }


    @Override
    public Notation getSelectedItem() {
        return null;
    }
}
