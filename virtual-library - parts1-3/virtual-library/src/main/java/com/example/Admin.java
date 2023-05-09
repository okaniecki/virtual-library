package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Admin extends User{
    private int userType;

    public Admin(int id, String username) {
        super(id, username);
        //automatically gives the userType a value of 0 if an admin object is created
        userType = 0;
    }

    /*
     * Creates a file for the newly created Admin
     * 
     * Text file will contain the Admins id, name, and user type using
     * it's overwritten toString() method in this class
     */

    public static void createAdminFile(Admin admin) {
        String id = Integer.toString(admin.getId());
        File file = new File(id);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(admin.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
    /*
     * Adds a book into the binary search tree (or library) and adds a book
     * into the library-save.txt file by using the saveBook method in the Library class
     */
   
    public static Scene addBook(Stage stage) {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #2A2E37;");
        pane.setPadding(new Insets(11, 12, 13, 14));

        Label title = new Label("Add Book:");
        title.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 20px; -fx-font-weight: bold");
        Button back = new Button("Back");
        back.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");        
        VBox vbox = new VBox();
        TextField bookid = new TextField();
        bookid.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
        TextField bookname = new TextField();
        bookname.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");

        Button add = new Button("Add Book");
        add.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        Label a = new Label("Book ID: ");
        Label b = new Label("Book Name: ");
        a.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 15px;");
        b.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 15px;");
        
        vbox.getChildren().addAll(a, bookid, b, bookname, add);
        vbox.setSpacing(10);
        add.setLineSpacing(10);
        BorderPane.setAlignment(vbox, Pos.CENTER_RIGHT);
        BorderPane.setMargin(vbox, new Insets(30));

        Scene scene = new Scene(pane, 400, 500);
        pane.setTop(title);
        pane.setBottom(back);
        pane.setCenter(vbox);

        //button hover effect
        add.setOnMouseMoved(f -> {
            add.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        add.setOnMouseExited(f -> {
            add.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        back.setOnMouseMoved(f -> {
            back.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        back.setOnMouseExited(f -> {
            back.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });


        add.setOnAction(e -> {
            try {
                int id = Integer.parseInt(bookid.getText());
                String bookName = bookname.getText();
                Library.books.put(id, bookName);
                    try {
                        Library.saveBook(id, bookName);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
    
                    Label success = new Label("Success! " + bookName + " has been added\nto your library!");
                    success.setStyle("-fx-text-fill: #FFFF8D; -fx-font-size: 13px");
                    BorderPane.setAlignment(success, Pos.CENTER);
                    BorderPane.setMargin(success, new Insets(20));
    
                    vbox.getChildren().addAll(success);
            
            } catch (Exception f) {
                f.getStackTrace();
                //gives an error message if id was invalid, or fields were not entered correctly
                Label error = new Label("Something went wrong, try again.");
                bookname.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                bookid.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 30px;");

                error.setStyle("-fx-text-fill: red;");
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

            }
        });

        back.setOnAction(e -> {
            stage.setScene(Library.adminHome(stage, Library.userOnline.id, Library.userOnline.username));
        });

        stage.setScene(scene);
        stage.setTitle("Add Book");
        stage.show();
        return scene;
    }

    /*
     * Gives a detailed list of all the users currently in the system
     * 
     * List is in a table format where the id, username, and usertype are listed according
     * from oldest to newest
     */

    public static Scene viewUsers(Stage stage) throws FileNotFoundException {
        int i = 0, j = 0;
        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: #2A2E37; -fx-border-color: #FFFF8D;");
        pane.setPadding(new Insets(11, 12, 13, 14));

        VBox vbox = new VBox();
        GridPane.setMargin(vbox, new Insets(10));
        vbox.setSpacing(10);

        Label label = new Label("         User Overview");
        label.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: #2A2E37");
        label.setPadding(new Insets(20));
        Button back = new Button("Back to Menu");
        back.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        //button hover effect
        back.setOnMouseMoved(f -> {
            back.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        back.setOnMouseExited(f -> {
            back.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });

        Scanner input = new Scanner(new File("users-save.txt"));

        pane.getColumnConstraints().add(new ColumnConstraints(80));
        pane.getColumnConstraints().add(new ColumnConstraints(100));
        pane.getColumnConstraints().add(new ColumnConstraints(30));
        while(input.hasNextLine()) {
            //lists the users from the save file
            Label a = new Label(input.nextLine());
            a.setLineSpacing(30);
            Label b = new Label(input.nextLine());
            b.setLineSpacing(30);
            Label c = new Label(input.nextLine());
            c.setLineSpacing(30);

            a.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 15px;");
            b.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 15px;");
            c.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 15px;");

            pane.add(a, i, j);
            pane.add(b, i+1, j);
            pane.add(c, i+2, j);

            i = 0;
            j++;
        }

        vbox.getChildren().addAll(label, pane, back);
        vbox.setStyle("-fx-background-color: #2A2E37");

        back.setOnAction(e -> {
            stage.setScene(Library.adminHome(stage, Library.userOnline.id, Library.userOnline.username));
        });

        Scene scene = new Scene(vbox, 300, 400);
        stage.setTitle("Users");
        stage.setScene(scene);
        stage.show();
        
        return scene;
    }

    /*
     * Adds a user by directing the user to the register screen, but adds an admin user type
     * to create another administrator that was not shown in the beginning registration page
     */

    public static Scene addUser(Stage stage) {
        BorderPane pane = new BorderPane();
        Label title = new Label("User Registration");
        title.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 20px; -fx-font-weight: bold");
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(10));
        pane.setStyle("-fx-background-color: #2A2E37;");
        pane.setPadding(new Insets(11, 12, 13, 14));
        Button back = new Button("Back to Menu");

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        TextField userid = new TextField();
        TextField name = new TextField();
        RadioButton member = new RadioButton("Member");
        RadioButton admin = new RadioButton("Admin");
        ToggleGroup group = new ToggleGroup();
        member.setToggleGroup(group);
        admin.setToggleGroup(group);

        Label id2 = new Label("User ID (Numbers Only): ");
        Label username2 = new Label("User Name: ");
        Label userType = new Label("User Type: ");
        Button add = new Button("Add and Sign In");
        vbox.getChildren().addAll(id2, userid, username2, name, userType, member, admin, add);

        member.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 13px; -fx-background-color: #2A2E37;");
        admin.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 13px; -fx-background-color: #2A2E37;");

        
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
        pane.setTop(title);
        pane.setBottom(back);
        pane.setCenter(vbox);

        back.setOnAction(e -> {
            stage.setScene(Library.adminHome(stage, Library.userOnline.id, Library.userOnline.username));
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
                        stage.setScene(Library.memberHome(stage, id, username));
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                } else if (admin.isSelected()) {
                    Admin a = new Admin(id, username);
                    createAdminFile(a);
                    try {
                        User.saveUsers(id, username, 0);
                        stage.setScene(Library.adminHome(stage, id, username));
                    } catch (IOException e1) {
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
        stage.setTitle("Add User");
        stage.show();
        return scene;
    }

    /*
     * Removes a user from the user system as well as the map of all the users
     * 
     * For this, the admin must have the user ID, and if the id matches a current user,
     * it will remove that user, and confirms its success by listing the user attached to the id
     */

    public static Scene removeUser(Stage stage) {
        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: #2A2E37;");
        pane.setPadding(new Insets(11, 12, 13, 14));

        Label a = new Label("Type User ID: ");
        TextField id = new TextField();
        Button remove = new Button("Remove");
        Button back = new Button("Back");

        a.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 15px;");
        id.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
        remove.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        back.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");

        pane.add(a, 0, 0);
        pane.add(id, 1, 0);
        pane.add(remove, 1, 1);
        pane.add(back, 0, 4);
        GridPane.setMargin(a, new Insets(10));
        GridPane.setMargin(id, new Insets(10));
        GridPane.setMargin(remove, new Insets(10));
        GridPane.setMargin(back, new Insets(40));

        //button hover effect
        remove.setOnMouseMoved(f -> {
            remove.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        remove.setOnMouseExited(f -> {
            remove.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        back.setOnMouseMoved(f -> {
            back.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        back.setOnMouseExited(f -> {
            back.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });

        remove.setOnAction(e -> {
                try {
                    if(Library.users.get(Integer.parseInt(id.getText())) == null) {
                        //gives an error message if the id is not attached to any user
                        id.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                        remove.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 30px;");

                        Label error = new Label("ID doesn't exist, try again");
                        error.setStyle("-fx-text-fill: red; -fx-font-size: 13px;");
                        pane.add(error, 1,3);

                
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
                        //gives a success message when removed from the text file and the map
                        Label l = new Label("Sucessfully removed " + Library.users.get(Integer.parseInt(id.getText())) + "!");
                        l.setStyle("-fx-text-fill: #FFFF8D; -fx-font-size: 13px");
                        pane.add(l, 1, 3);
                        Library.users.remove(Integer.parseInt(id.getText()),Library.users.get(Integer.parseInt(id.getText())));
                        File g = new File(id.getText());
                        if(g.delete()) {
                            System.out.println("success");
                        }
                        try {
                            User.rewriteUsers();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (Exception g) {
                    id.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                            remove.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 30px;");

                            Label error = new Label("No ID/Invalid ID, try again");
                            error.setStyle("-fx-text-fill: red; -fx-font-size: 13px;");
                            pane.add(error, 1,3);

                    
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

        back.setOnAction(e -> {
            stage.setScene(Library.adminHome(stage, Library.userOnline.id, Library.userOnline.username));
        });

        Scene scene = new Scene(pane, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Remove User");
        stage.show();
        return scene;
    }

    public static void adminLogout() {
        System.exit(0);
    }

    @Override
    public String toString() {
        return id + "\n" + username + "\n" + userType + "\n";
    }


    public int getUserType() {
        return this.userType;
    }


}
