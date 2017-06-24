package com.kjellvos.school.hitLijsten.common;

import com.kjellvos.school.hitLijsten.Main;
import com.kjellvos.school.hitLijsten.MainMenu;
import com.kjellvos.school.hitLijsten.common.DBClasses.Notation;
import com.kjellvos.school.hitLijsten.common.Extensions.DatabaseExt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

/**
 * Created by kjell on 19-6-2017.
 */
public class Database extends DatabaseExt {
    MainMenu mainMenu;

    public Database(MainMenu mainMenu) throws SQLException {
        super();
        this.mainMenu = mainMenu;
    }

    @Override
    public void setConnection(Connection connection) {
        super.setConnection(connection);
    }

    @Override
    public void setPreparedStatement(PreparedStatement preparedStatement) {
        super.setPreparedStatement(preparedStatement);
    }

    @Override
    public PreparedStatement getPreparedStatement() {
        return super.getPreparedStatement();
    }

    @Override
    public BasicDataSource getBasicDataSource() {
        return super.getBasicDataSource();
    }

    public ObservableList getNotationByEdition(int week, int jaar, int editie){
        ObservableList<Notation> data = FXCollections.observableArrayList();
        try{
            setConnection(getBasicDataSource().getConnection());

            String sql =    "SELECT hitlijst_notering.positie, single.id AS singleID, single.titel, artiest.id AS artiestID, artiest.naam AS artiestNaam FROM hitlijst_notering \n" +
                                "LEFT JOIN hitlijst_editie ON hitlijst_editie.id = hitlijst_notering.hitlijst_editie \n" +
                                "LEFT JOIN single ON hitlijst_notering.single = single.id \n" +
                                "LEFT JOIN artiest ON single.artiest = artiest.id \n" +
                                "LEFT JOIN hitlijst ON hitlijst_editie.hitlijst = hitlijst.id \n" +
                                "WHERE hitlijst_editie.week = " + week + " AND hitlijst_editie.jaar = " + jaar + " AND hitlijst.id = " + editie + " ORDER BY hitlijst_notering.positie ASC ;";

            setPreparedStatement(getConnection().prepareStatement(sql));
            ResultSet resultSet = super.getPreparedStatement().executeQuery();
            while (resultSet.next()) {
                int positie = resultSet.getInt("positie");
                int singleId = resultSet.getInt("singleID");
                String titel = resultSet.getString("titel");
                int artiestId = resultSet.getInt("artiestID");
                String artiest = resultSet.getString("artiestNaam");

                data.add(new Notation(positie, singleId, artiestId, jaar, week, titel, artiest));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public ObservableList getNotations(String naam, int singleOrArtist){
        ObservableList<Notation> data = FXCollections.observableArrayList();
        try{
            setConnection(getBasicDataSource().getConnection());

            String sql =    "SELECT hitlijst_notering.positie, single.id AS singleID, single.titel, artiest.id AS artiestID, artiest.naam AS artiestNaam, hitlijst_editie.jaar, hitlijst_editie.week FROM hitlijst_notering \n" +
                            "LEFT JOIN hitlijst_editie ON hitlijst_editie.id = hitlijst_notering.hitlijst_editie \n" +
                            "LEFT JOIN single ON hitlijst_notering.single = single.id \n" +
                            "LEFT JOIN artiest ON single.artiest = artiest.id \n" +
                            "LEFT JOIN hitlijst ON hitlijst_editie.hitlijst = hitlijst.id \n";
            if (singleOrArtist == Main.artists) {
                sql +=      "WHERE artiest.naam LIKE '%" + naam +"%';";
            }else if (singleOrArtist == Main.singles) {
                sql +=      "WHERE single.titel LIKE '%" + naam +"%';";
            }

            setPreparedStatement(getConnection().prepareStatement(sql));
            ResultSet resultSet = super.getPreparedStatement().executeQuery();
            while (resultSet.next()) {
                int positie = resultSet.getInt("positie");
                int singleId = resultSet.getInt("singleID");
                int artiestId = resultSet.getInt("artiestID");
                int jaar = resultSet.getInt("jaar");
                int week = resultSet.getInt("week");
                String titel = resultSet.getString("titel");
                String artiest = resultSet.getString("artiestNaam");

                data.add(new Notation(positie, singleId, artiestId, jaar, week, titel, artiest));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public ObservableList getListings(){
        ObservableList<Notation> data = FXCollections.observableArrayList();
        try{
            setConnection(getBasicDataSource().getConnection());

            String sql =    "SELECT hitlijst_notering.positie, single.id AS singleID, single.titel, artiest.id AS artiestID, artiest.naam AS artiestNaam, hitlijst_editie.jaar, hitlijst_editie.week FROM hitlijst_notering \n" +
                    "LEFT JOIN hitlijst_editie ON hitlijst_editie.id = hitlijst_notering.hitlijst_editie \n" +
                    "LEFT JOIN single ON hitlijst_notering.single = single.id \n" +
                    "LEFT JOIN artiest ON single.artiest = artiest.id \n" +
                    "LEFT JOIN hitlijst ON hitlijst_editie.hitlijst = hitlijst.id;";

            setPreparedStatement(getConnection().prepareStatement(sql));
            ResultSet resultSet = getPreparedStatement().executeQuery();
            while (resultSet.next()) {
                int positie = resultSet.getInt("positie");
                int singleId = resultSet.getInt("singleID");
                int artiestId = resultSet.getInt("artiestID");
                int jaar = resultSet.getInt("jaar");
                int week = resultSet.getInt("week");
                String titel = resultSet.getString("titel");
                String artiest = resultSet.getString("artiestNaam");

                data.add(new Notation(positie, singleId, artiestId, jaar, week, titel, artiest));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public ObservableList getTopListings(int id) {
        ObservableList<Notation> data = FXCollections.observableArrayList();
        try {
            setConnection(getBasicDataSource().getConnection());

            String sql = "SELECT MIN(positie) FROM hitlijst_notering WHERE single = " + id + ";";

            setPreparedStatement(getConnection().prepareStatement(sql));
            ResultSet resultSet = getPreparedStatement().executeQuery();

            resultSet.next();
            int maxPositie = resultSet.getInt("MIN(positie)");


            sql =       "SELECT hitlijst_notering.positie, single.id AS singleID, single.titel, artiest.id AS artiestID, artiest.naam AS artiestNaam, hitlijst_editie.jaar, hitlijst_editie.week FROM hitlijst_notering \n" +
                        "LEFT JOIN single ON hitlijst_notering.single = single.id \n" +
                        "LEFT JOIN artiest ON single.artiest = artiest.id \n" +
                        "LEFT JOIN hitlijst_editie ON hitlijst_editie.id = hitlijst_notering.hitlijst_editie \n" +
                        "LEFT JOIN hitlijst ON hitlijst_editie.hitlijst = hitlijst.id \n" +
                        "WHERE single = '" + id + "' AND positie = '" + maxPositie + "' \n" +
                        "ORDER BY hitlijst_editie.week, hitlijst_editie.jaar ASC;";


            setPreparedStatement(getConnection().prepareStatement(sql));
            resultSet = getPreparedStatement().executeQuery();

            while (resultSet.next()) {
                int positie = resultSet.getInt("positie");
                int singleId = resultSet.getInt("singleID");
                int artiestId = resultSet.getInt("artiestID");
                int jaar = resultSet.getInt("jaar");
                int week = resultSet.getInt("week");
                String titel = resultSet.getString("titel");
                String artiest = resultSet.getString("artiestNaam");

                data.add(new Notation(positie, singleId, artiestId, jaar, week, titel, artiest));
            }
        } catch (SQLException e) {
            System.out.println("Query gefaald : " + e.getMessage());
        }
        return data;
    }
}
