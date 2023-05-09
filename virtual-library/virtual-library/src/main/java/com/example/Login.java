package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Login {

    /*
     * First screen when program first runs
     * 
     * Gives the user the ability to either login with an existing id
     * or register with the library 
     */
    
    public static Scene login(Stage stage) throws FileNotFoundException {
        BorderPane pane = new BorderPane();

        pane.setStyle("-fx-background-color: #2A2E37;");
        pane.setPadding(new Insets(11, 12, 13, 14));
        
        Label title = new Label("Virtual Library Login");
        
        Label idl = new Label("         User ID: ");
        TextField userID = new TextField();
        Button login = new Button("Log in");

        final ImageView imageView  = new ImageView();   
        Image image1 = new Image(new FileInputStream("src/main/java/com/example/pictures/login.png"));
        imageView .setImage(image1);

        //Setting the position of the image 
        imageView.setX(225); 
        imageView.setY(25); 

        //setting the fit height and width of the image view 
        imageView.setFitHeight(80); 
        imageView.setFitWidth(80); 

        //Setting the preserve ratio of the image view 
        imageView.setPreserveRatio(true); 

        BorderPane grid = new BorderPane();
        grid.setTop(imageView);
        BorderPane.setAlignment(imageView, Pos.CENTER);
        grid.setBottom(title);

        userID.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
        title.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 20px; -fx-font-weight: bold");
        idl.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 13px");

        pane.setTop(grid);
        BorderPane.setMargin(grid, new Insets(10));
        BorderPane.setAlignment(title, Pos.TOP_CENTER);
        BorderPane.setAlignment(grid, Pos.TOP_CENTER);

        VBox center = new VBox();
        center.setSpacing(10);
        BorderPane.setAlignment(center, Pos.CENTER_RIGHT);
        center.getChildren().addAll(idl, userID, login);
        pane.setCenter(center);


        login.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
   

        Label registerLabel = new Label("Don't have an account? Register now!");
        registerLabel.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 13px");

        Button addUser = new Button("Register");
        addUser.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");

        VBox right = new VBox();
        right.setSpacing(10);
        BorderPane.setAlignment(right, Pos.CENTER_RIGHT);
        right.getChildren().addAll(registerLabel, addUser);
        pane.setRight(right);

        BorderPane.setMargin(center, new Insets(10));
        BorderPane.setMargin(right, new Insets(10));


        //changes the button color to green when mouse is hovered over it
        addUser.setOnMouseMoved(f -> {
            addUser.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
        });
        addUser.setOnMouseExited(f -> {
            addUser.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
        });

        login.setOnMouseMoved(f -> {
            login.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
        });
        login.setOnMouseExited(f -> {
            login.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
        });

        addUser.setOnAction(e -> {
            try {
                stage.setScene(register(stage));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        });

        
            login.setOnAction(e -> {
                try {
                //checks if the user exists in the system
                boolean f = User.checkUser(Integer.parseInt(userID.getText()));
                if (f) {
                    try {
                        Scanner input = new Scanner(new File(userID.getText()));
                        int i = input.nextInt();
                        String j = input.next();
                        String userType = input.next();

                        if(userType.contains("1")) {
                            //creates a member object if user type is 1
                            MultiThread thread = new MultiThread();
                            thread.start();
                            Member.loadCheckedOutBooks(i);
                            Library.memberHome(stage, i, j);
                        } else if (userType.contains("0")) {
                            //creates a admin object if user type is 0
                            MultiThread thread = new MultiThread();
                            thread.start();
                            Library.adminHome(stage, i, j);
                        } else {
                            //will send an error message to the console if the file with the id
                            //exists, but the user type is not in the place it's supposed to be in,
                            //implying a corruption or a change in the file
                            System.out.print("FILE CORRUPTION");
                        }

                    } catch(Exception g) {
                        Label error = new Label("No ID entered, try again!");
                        center.getChildren().addAll(error);
                        error.setStyle("-fx-text-fill: #B2B2B2;");
                    }
                } else {
                    //will send an error message if the user or file does not exist
                    login.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                    userID.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: red");
                    Label error = new Label("ID doesn't exist, try again");
                    error.setStyle("-fx-text-fill: red;");
                    center.getChildren().addAll(error);

                    Timeline blinker = new Timeline(new KeyFrame(
                        Duration.seconds(0),
                        new KeyValue(
                                error.opacityProperty(), 
                                1, 
                                Interpolator.DISCRETE
                        )
                ),
                new KeyFrame(
                        Duration.seconds(1.5),
                        new KeyValue(
                                error.opacityProperty(), 
                                0, 
                                Interpolator.DISCRETE
                        )
                ),
                new KeyFrame(
                        Duration.seconds(3),
                        new KeyValue(
                                error.opacityProperty(), 
                                1, 
                                Interpolator.DISCRETE
                        )
                )
        );
                    SequentialTransition blinkThenFade = new SequentialTransition(error, blinker);
                    blinkThenFade.play();
                    blinker.setOnFinished(event -> error.setText(""));
                }
            } catch(Exception f) {
                login.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                userID.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: red");
                Label error = new Label("No ID, try again");
                error.setStyle("-fx-text-fill: red;");
                center.getChildren().addAll(error);

                Timeline blinker = new Timeline(new KeyFrame(
                    Duration.seconds(0),
                    new KeyValue(
                            error.opacityProperty(), 
                            1, 
                            Interpolator.DISCRETE
                    )
            ),
            new KeyFrame(
                    Duration.seconds(1.5),
                    new KeyValue(
                            error.opacityProperty(), 
                            0, 
                            Interpolator.DISCRETE
                    )
            ),
            new KeyFrame(
                    Duration.seconds(3),
                    new KeyValue(
                            error.opacityProperty(), 
                            1, 
                            Interpolator.DISCRETE
                    )
            )
    );
                SequentialTransition blinkThenFade = new SequentialTransition(error, blinker);
                blinkThenFade.play();
                blinker.setOnFinished(event -> error.setText(""));
            }
        });
        
        Scene scene = new Scene(pane, 450, 270);
        stage.setTitle("Virtual Library");
        stage.setScene(scene);
        stage.show();
        return scene;
    }

    public static Scene register(Stage stage) throws FileNotFoundException {
        BorderPane pane = new BorderPane();

        pane.setStyle("-fx-background-color: #2A2E37;");
        pane.setPadding(new Insets(11, 12, 13, 14));

        Label title = new Label("User Registration");

        title.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 20px; -fx-font-weight: bold");
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(10));

        Button back = new Button("Back to Menu");

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        TextField userid = new TextField();
        TextField name = new TextField();
        RadioButton member = new RadioButton("Member");
        ToggleGroup group = new ToggleGroup();
        member.setToggleGroup(group);

        Label id2 = new Label("User ID (Numbers Only): ");
        Label username2 = new Label("User Name: ");
        Label userType = new Label("User Type: ");
        Button add = new Button("Add and Sign In");
        vbox.setAlignment(Pos.TOP_CENTER);

        member.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 13px; -fx-background-color: #2A2E37;");

  
        id2.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 13px; -fx-font-weight: bold");
        username2.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 13px; -fx-font-weight: bold");
        userType.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 13px; -fx-font-weight: bold");

        userid.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
        name.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");

        
        add.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
        
        //button hover effect
        add.setOnMouseMoved(f -> {
            add.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
        });
        add.setOnMouseExited(f -> {
            add.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
        });
        

        back.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
        back.setOnMouseMoved(f -> {
            back.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
        });
        back.setOnMouseExited(f -> {
            back.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
        });

        vbox.getChildren().addAll(id2, userid, username2, name, userType, member, add);

        pane.setTop(title);
        pane.setBottom(back);
        pane.setCenter(vbox);

        

        back.setOnAction(e -> {
            try {
                stage.setScene(login(stage));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        });

        add.setOnAction(e -> {
            try {
            int id = Integer.parseInt(userid.getText());
            String username = name.getText();
            //creates a new empty file that will later be appended in the createMemberFile or createAdminFile methods
            File f = new File(Integer.toString(id));

            if(f.exists()) {
                //sends an error message if the id the user wants to use is already in use, sending them back to the register screen
                userid.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                name.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 30px;");

                Label error = new Label("ID already exists, try again");
                error.setStyle("-fx-text-fill: red; -fx-font-size: 13px;");
                vbox.getChildren().addAll(error);

                
                Timeline blinker = new Timeline(new KeyFrame(
                    Duration.seconds(0),
                    new KeyValue(
                            error.opacityProperty(), 
                            1, 
                            Interpolator.DISCRETE
                    )
            ),
            new KeyFrame(
                    Duration.seconds(1.5),
                    new KeyValue(
                            error.opacityProperty(), 
                            0, 
                            Interpolator.DISCRETE
                    )
            ),
            new KeyFrame(
                    Duration.seconds(3),
                    new KeyValue(
                            error.opacityProperty(), 
                            1, 
                            Interpolator.DISCRETE
                    )
            )
    );
                SequentialTransition blinkThenFade = new SequentialTransition(error, blinker);
                blinkThenFade.play();
                blinker.setOnFinished(event -> error.setText(""));
            } else {
                if(member.isSelected()) {
                    //appends the file with the new id with more information about the user
                    Member m = new Member(id, username);
                    Member.createMemberFile(m);
                    try {
                        User.saveUsers(id, username, 1);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        MultiThread thread = new MultiThread();
                        thread.start();
                        Library.memberHome(stage, id, username);
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        } catch(Exception f) {
            f.getCause();
        }
    });

        Scene scene = new Scene(pane, 400, 500);
        stage.setScene(scene);
        stage.setTitle("Registration");
        stage.show();
        return scene;
    }
}
