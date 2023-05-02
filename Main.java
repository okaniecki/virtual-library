package com.example;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Main.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Login.login(stage);
    }
    
}
