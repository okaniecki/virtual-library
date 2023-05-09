package com.example;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Main.launch();
    }

    //starts the program by changing the scene to the beginning login page
    @Override
    public void start(Stage stage) throws Exception {
        Login.login(stage);
    }
    
}
