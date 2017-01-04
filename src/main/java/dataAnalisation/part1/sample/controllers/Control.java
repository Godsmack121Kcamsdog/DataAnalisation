package dataAnalisation.part1.sample.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;

import java.io.IOException;


public class Control extends TabPane{

    Control(String name) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/" + name)
        );

        try {

            loader.setController(this);
            loader.setRoot(this);
            loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
