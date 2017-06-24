package com.kjellvos.school.hitLijsten;

import com.kjellvos.os.gridHandler.GridHandler;
import com.kjellvos.school.hitLijsten.common.DBClasses.Notation;
import com.kjellvos.school.hitLijsten.common.interfaces.SceneImplementation;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

/**
 * Created by kjell on 19-6-2017.
 */
public class Notations implements SceneImplementation {
    private MainMenu mainMenu;

    private GridHandler gridHandler;
    private Scene scene;

    private TableView tableView;
    private TableColumn artiestTableColumn, singleTableColumn, noteringTableColumn, yearTableColumn, weekTableColumn;

    private ObservableList<Notation> data = null;

    public Notations(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    @Override
    public Scene createAndGetScene() {
        gridHandler = new GridHandler();
        gridHandler.menuBar(true, 25D);

        tableView = new TableView();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setEditable(false);

        noteringTableColumn = new TableColumn("Notation");
        noteringTableColumn.setCellValueFactory(new PropertyValueFactory<Notation, Integer>("positie"));

        yearTableColumn = new TableColumn("Year");
        yearTableColumn.setCellValueFactory(new PropertyValueFactory<Notation, Integer>("year"));

        weekTableColumn = new TableColumn("Week");
        weekTableColumn.setCellValueFactory(new PropertyValueFactory<Notation, Integer>("week"));

        artiestTableColumn = new TableColumn("Artiest");
        artiestTableColumn.setCellValueFactory(new PropertyValueFactory<Notation, String>("artiestNaam"));

        singleTableColumn = new TableColumn("Single");
        singleTableColumn.setCellValueFactory(new PropertyValueFactory<Notation, String>("singleNaam"));

        tableView.getColumns().addAll(noteringTableColumn, yearTableColumn, weekTableColumn, artiestTableColumn, singleTableColumn);

        tableView.setItems(data);

        gridHandler.add(0, 0, tableView, 9, 9, false);

        scene = gridHandler.getGridAsScene();
        ((Pane) scene.getRoot()).getChildren().addAll(mainMenu.createAndGetMenuBar());
        return scene;
    }

    public void setData(ObservableList data) {
        this.data = data;
    }

    @Override
    public void reload() {
        //Lekker niks doen!
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public Notation getSelectedItem() {
        return ( (Notation) tableView.getSelectionModel().getSelectedItem());
    }
}
