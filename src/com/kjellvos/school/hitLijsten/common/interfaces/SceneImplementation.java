package com.kjellvos.school.hitLijsten.common.interfaces;

import javafx.scene.Scene;

/**
 * Created by kjevo on 3/26/17.
 */
public interface SceneImplementation {
    Scene createAndGetScene();

    void reload();

    Scene getScene();
}