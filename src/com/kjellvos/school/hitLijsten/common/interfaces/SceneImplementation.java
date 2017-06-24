package com.kjellvos.school.hitLijsten.common.interfaces;

import com.kjellvos.school.hitLijsten.common.DBClasses.Notation;
import javafx.scene.Scene;

import javax.swing.text.TableView;

/**
 * Created by kjevo on 3/26/17.
 */
public interface SceneImplementation {
    Scene createAndGetScene();

    void reload();

    Scene getScene();

    Notation getSelectedItem();
}