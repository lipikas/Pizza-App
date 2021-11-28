package com.project5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Launches menu fxml
 * @author Lipika, Kenisha
 */
public class MainMenuApplication extends Application {
    @Override
    /**
     * Sets the scene and stage for the main menu
     */
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuApplication.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 610, 578);
        stage.setTitle("RU Pizzeria");
        stage.setScene(scene);
        stage.show();
    }
/*
lauches main menu
 */
    public static void main(String[] args) {
        launch();
    }
}
