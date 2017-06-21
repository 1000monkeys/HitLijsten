package com.kjellvos.school.hitLijsten.common.DBClasses;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by kjell on 19-6-2017.
 */
public class Notering {
    private IntegerProperty year, week, singleId, artiestId, positie;
    private StringProperty artiestNaam, singleNaam;


    public Notering(int positie, int singleId, int artiestId, int year, int week, String titel, String artiest) {
        this.positie = new SimpleIntegerProperty(positie);
        this.singleId = new SimpleIntegerProperty(singleId);
        this.artiestId = new SimpleIntegerProperty(artiestId);
        this.year = new SimpleIntegerProperty(year);
        this.week = new SimpleIntegerProperty(week);
        this.artiestNaam = new SimpleStringProperty(artiest);
        this.singleNaam = new SimpleStringProperty(titel);
    }

    public IntegerProperty positieProperty() {
        return positie;
    }

    public IntegerProperty singleIdProperty() {
        return singleId;
    }

    public IntegerProperty artiestIdProperty() {
        return artiestId;
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public IntegerProperty weekProperty() {
        return week;
    }

    public StringProperty artiestNaamProperty() {
        return artiestNaam;
    }

    public StringProperty singleNaamProperty() {
        return singleNaam;
    }
}
