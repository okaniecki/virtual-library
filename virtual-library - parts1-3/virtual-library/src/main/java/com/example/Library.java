package com.example;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Library {
    //stores the user that's currently online to safely get the id, username, and usertype of the user
    static User userOnline;
    //binary search tree storing the data for book objects
    static BinaryTree<Integer, String> books = new BinaryTree<>();
    //map that stores all the users with their id, and their username
    static Map<Integer, String> users = new Hashtable<>();
    //queue structure with book objects to store checked out books for a member
    static Queue<Book> checkedOutBooks = new LinkedList<>();
    
    /*
     * Dashboard for Members
     * 
     * Gives them the ability to check out or return books, check their profile and
     * their currently checked out books, and view the library
     */
    public static Scene memberHome(Stage stage, int id, String name) throws FileNotFoundException {
        
        userOnline = new User(id, name);

        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #2A2E37;");
        pane.setPadding(new Insets(11, 12, 13, 14));
    
        Label title = new Label("Welcome Back "  + userOnline.username + "!");
        title.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 20px; -fx-font-weight: bold");
        BorderPane.setMargin(title, new Insets(10));
        BorderPane.setAlignment(title, Pos.CENTER);
        
        VBox vbox1 = new VBox();
        vbox1.setSpacing(10);
        BorderPane.setAlignment(vbox1, Pos.BOTTOM_LEFT);
        BorderPane.setMargin(vbox1, new Insets(20));
        VBox vbox2 = new VBox();
        vbox2.setSpacing(10);
        BorderPane.setAlignment(vbox2, Pos.BOTTOM_RIGHT);
        BorderPane.setMargin(vbox2, new Insets(20));

        Button checkOutBook = new Button("Check Out Book");
        Button returnBook = new Button("Return Book");
        Button viewProfile = new Button("View Profile");
        Button viewLibrary = new Button("Search Books");
        Button viewBooks = new Button("View Library");
        Button checkedOutBooks = new Button("See Checked Out Book IDs");
        Button logout = new Button("Log out");

        checkOutBook.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        returnBook.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        viewProfile.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        viewLibrary.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        viewBooks.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        checkedOutBooks.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        logout.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");

        //button hover effect
        checkOutBook.setOnMouseMoved(f -> {
            checkOutBook.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        checkOutBook.setOnMouseExited(f -> {
            checkOutBook.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        returnBook.setOnMouseMoved(f -> {
            returnBook.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        returnBook.setOnMouseExited(f -> {
            returnBook.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        viewProfile.setOnMouseMoved(f -> {
            viewProfile.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        viewProfile.setOnMouseExited(f -> {
            viewProfile.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        viewLibrary.setOnMouseMoved(f -> {
            viewLibrary.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px; ");
        });
        viewLibrary.setOnMouseExited(f -> {
            viewLibrary.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px; ");
        });
        viewBooks.setOnMouseMoved(f -> {
            viewBooks.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px; ");
        });
        viewBooks.setOnMouseExited(f -> {
            viewBooks.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        checkedOutBooks.setOnMouseMoved(f -> {
            checkedOutBooks.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        checkedOutBooks.setOnMouseExited(f -> {
            checkedOutBooks.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        logout.setOnMouseMoved(f -> {
            logout.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        logout.setOnMouseExited(f -> {
            logout.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        
        vbox1.getChildren().addAll(checkOutBook, returnBook, viewProfile);
        vbox2.getChildren().addAll(viewLibrary, checkedOutBooks, viewBooks, logout);

        pane.setTop(title);
        pane.setLeft(vbox2);
        pane.setCenter(vbox1);

        //action statements for each button leading to their respective stage
        checkOutBook.setOnAction(e -> {
            Member.checkOutBook(stage);
        });

        returnBook.setOnAction(e -> {
            Member.returnBook(stage);
        });

        viewProfile.setOnAction(e -> {
            try {
                profile(stage);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        });

        viewLibrary.setOnAction(e -> {
            searchBooks(stage, 1);
        });

        viewBooks.setOnAction(e -> {
            viewLibrary(stage, 1);
        });

        checkedOutBooks.setOnAction(e -> {
            Member.viewCheckedOutBooks(stage);
        });

        logout.setOnAction(e -> {
            try {
                Member.memberLogout();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        

        Scene scene = new Scene(pane, 415, 350);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
        return scene;
    }

    /*
     * Dashboard for Admins
     * 
     * Admins are able to add books into the library, add or remove users into the system,
     * and view and search for books in the library
     */

    public static Scene adminHome(Stage stage, int id, String name) {
        userOnline = new User(id, name);

        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #2A2E37;");
        pane.setPadding(new Insets(11, 12, 13, 14));
        
        Label title = new Label("Welcome Back "  + userOnline.username + "!");
        title.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 20px; -fx-font-weight: bold");
        BorderPane.setMargin(title, new Insets(10));
        BorderPane.setAlignment(title, Pos.CENTER);

        VBox vbox1 = new VBox();
        vbox1.setSpacing(10);
        BorderPane.setAlignment(vbox1, Pos.BOTTOM_LEFT);
        BorderPane.setMargin(vbox1, new Insets(30));
        VBox vbox2 = new VBox();
        vbox2.setSpacing(10);
        BorderPane.setAlignment(vbox2, Pos.BOTTOM_RIGHT);
        BorderPane.setMargin(vbox2, new Insets(30));

        Button addBook = new Button("Add Book");
        Button viewUsers = new Button("View Users");
        Button addUser = new Button("Add User");
        Button removeUser = new Button("Remove User");
        Button viewBooks = new Button("Search Books");
        Button viewLibrary = new Button("View Library");
        Button logout = new Button("Log out");
        Button viewProfile = new Button("View Profile");

        addBook.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        viewUsers.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        addUser.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        viewLibrary.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        viewBooks.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        removeUser.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        logout.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        viewProfile.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");

        //button hover effect
        addBook.setOnMouseMoved(f -> {
            addBook.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        addBook.setOnMouseExited(f -> {
            addBook.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        viewUsers.setOnMouseMoved(f -> {
            viewUsers.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        viewUsers.setOnMouseExited(f -> {
            viewUsers.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        addUser.setOnMouseMoved(f -> {
            addUser.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        addUser.setOnMouseExited(f -> {
            addUser.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        removeUser.setOnMouseMoved(f -> {
            removeUser.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        removeUser.setOnMouseExited(f -> {
            removeUser.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        viewBooks.setOnMouseMoved(f -> {
            viewBooks.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        viewBooks.setOnMouseExited(f -> {
            viewBooks.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        viewLibrary.setOnMouseMoved(f -> {
            viewLibrary.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        viewLibrary.setOnMouseExited(f -> {
            viewLibrary.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        logout.setOnMouseMoved(f -> {
            logout.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        logout.setOnMouseExited(f -> {
            logout.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        viewProfile.setOnMouseMoved(f -> {
            viewProfile.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        viewProfile.setOnMouseExited(f -> {
            viewProfile.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });


        vbox1.getChildren().addAll(addBook, viewUsers, addUser, viewProfile);
        vbox2.getChildren().addAll(removeUser, viewBooks, viewLibrary, logout);

        pane.setTop(title);
        pane.setLeft(vbox2);
        pane.setCenter(vbox1);

        //action statements for all the buttons to go to their respective stages
        addBook.setOnAction(e -> {
            Admin.addBook(stage);
        });


        viewUsers.setOnAction(e -> {
            try {
                Admin.viewUsers(stage);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        });

        addUser.setOnAction(e -> {
            Admin.addUser(stage);
        });

        removeUser.setOnAction(e -> {
            Admin.removeUser(stage);
        });

        viewBooks.setOnAction(e -> {
            searchBooks(stage, 0);
        });

        viewLibrary.setOnAction(e -> {
            viewLibrary(stage, 0);
        });


        logout.setOnAction(e -> {
            Admin.adminLogout();
        });

        viewProfile.setOnAction(e -> {
            try {
                profile(stage);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        });
        

        Scene scene = new Scene(pane, 390, 350);
        stage.setTitle("Admin Dashboard");
        stage.setScene(scene);
        stage.show();
        return scene;
    }

    /*
     * Shows the profile of the user
     * 
     * Contains the username, ID, user type, and checked out books
     * if user is a member
     */

    public static Scene profile(Stage stage) throws FileNotFoundException {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #2A2E37;");
        pane.setPadding(new Insets(11, 12, 13, 14));
        File f = new File(Integer.toString(Library.userOnline.id));
        Scanner input = new Scanner(f);
        Label id = new Label(Integer.toString(input.nextInt()));
        Label name = new Label(input.next());
        int userType = input.nextInt();

        Button back = new Button("Back to Menu");
        Button logout = new Button("Logout");

        back.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        logout.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");

        //button hover effect
        back.setOnMouseMoved(g -> {
            back.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        back.setOnMouseExited(g -> {
            back.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });

        logout.setOnMouseMoved(g -> {
            logout.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        logout.setOnMouseExited(g -> {
            logout.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        
        back.setOnAction(e -> {
            if(userType == 0) {
                stage.setScene(adminHome(stage, Library.userOnline.id, Library.userOnline.username));
            } else if(userType == 1) {
                try {
                    stage.setScene(memberHome(stage, Library.userOnline.id, Library.userOnline.username));
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        logout.setOnAction(e -> {
            if(userType == 1) {
                try {
                    Member.memberLogout();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else if(userType == 0) {
                Admin.adminLogout();
            }
        });

        Label member;
        Label admin;
        HBox title = new HBox();
        if(userType == 1) {
            member = new Label("MEMBER");
            member.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 20px; -fx-font-weight: bold");
            title.getChildren().addAll(name, id, member);
        } else if(userType == 0) {
            admin = new Label("ADMIN");
            admin.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 20px; -fx-font-weight: bold");
            title.getChildren().addAll(name, id, admin);
        }

        name.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 20px; -fx-font-weight: bold");
        id.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 20px; -fx-font-weight: bold");

        BorderPane.setMargin(title, new Insets(20));
        BorderPane.setAlignment(title, Pos.CENTER);
        title.setSpacing(100);
        
        //switching the queue into an arraylist to print them onto the stage
        ArrayList<Book> book = new ArrayList<>(checkedOutBooks);

        

        pane.setTop(title);
        VBox queue = new VBox();
        Label t = new Label("Checked Out Books");
        t.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 18px; -fx-font-weight: bold; -fx-border-color: #FFFF8D; -fx-border-radius: 50px; -fx-background-radius: 50px; -fx-font-size: 15px; -fx-padding: 10px");
        BorderPane.setMargin(t, new Insets(20));
        queue.getChildren().addAll(t);
        if(userType == 1) {
            for(int i = 0; i < book.size(); i++) {
                Label temp = new Label(book.get(i).toString());
                temp.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 15px;");
                queue.getChildren().addAll(temp);
            }
            queue.setSpacing(10);
            pane.setRight(queue);
        } else if(userType == 0) {

        }
        VBox nav = new VBox();
        nav.getChildren().addAll(back, logout);
        pane.setLeft(nav);

        nav.setSpacing(10);
        BorderPane.setAlignment(nav, Pos.TOP_LEFT);
        BorderPane.setMargin(nav, new Insets(10));

        

        Scene scene = new Scene(pane, 500, 400);
        stage.setScene(scene);
        stage.setTitle("User Profile");
        stage.show();
        return scene;
    }

    /*
     * Utilizes the search method in the binary tree class to help the user
     * search for books in the library
     */

    public static Scene searchBooks(Stage stage, int userType) {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #2A2E37;");
        pane.setPadding(new Insets(11, 12, 13, 14));

        Label a = new Label("Type Book ID: ");
        TextField id = new TextField();
        Button search = new Button("Search");
        Button library = new Button("View Library");
        Button back = new Button("Back");

        Label t = new Label("Search Library");
        BorderPane.setMargin(t, new Insets(10));
        BorderPane.setAlignment(t, Pos.CENTER);
        pane.setTop(t);
        VBox searchBox = new VBox();
        searchBox.setSpacing(10);
        BorderPane.setAlignment(searchBox, Pos.BOTTOM_LEFT);
        BorderPane.setMargin(searchBox, new Insets(30));
        searchBox.getChildren().addAll(a, id, search, library, back);
        pane.setLeft(searchBox);

        a.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 15px;");
        id.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
        search.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        library.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        back.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");

        t.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 20px; -fx-font-weight: bold");

        //button hover effect
        search.setOnMouseMoved(f -> {
            search.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        search.setOnMouseExited(f -> {
            search.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        library.setOnMouseMoved(f -> {
            library.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        library.setOnMouseExited(f -> {
            library.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        back.setOnMouseMoved(f -> {
            back.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        back.setOnMouseExited(f -> {
            back.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });

        search.setOnAction(e -> {
            try {
                if(books.get(Integer.parseInt(id.getText())) == null) {
                    //error if id does not match with a book name in the library
                    id.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: #FFFF8D;");
                    search.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
                    Label f = new Label("Library does not \ncarry this book :(");
                    f.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 15px;");

                    f.setLineSpacing(10);
                    BorderPane.setAlignment(f, Pos.TOP_RIGHT);
                    BorderPane.setMargin(f, new Insets(30));
                    pane.setRight(f);
                } else {
                    //prints out the result with the id and its attached name
                    id.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: #FFFF8D;");
                    search.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
                    Label g = new Label(books.get(Integer.parseInt(id.getText())));
                    Label u = new Label(id.getText());
                    Label r = new Label("Result: ");
                    r.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 18px; -fx-font-weight: bold");
                    g.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 15px;");
                    u.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 15px;");

                    HBox result = new HBox();
                    result.setSpacing(10);
                    BorderPane.setAlignment(result, Pos.TOP_RIGHT);
                    BorderPane.setMargin(result, new Insets(30));
                    result.getChildren().addAll(r, u, g);

                    pane.setRight(result);
                }
            } catch (Exception h){
                //will send an error message if there is no id put in
                search.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                id.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: red");
                Label error = new Label("No ID, try again");
                error.setStyle("-fx-text-fill: red;");
                searchBox.getChildren().addAll(error);

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
        library.setOnAction(e -> {
            //sends the user to the library stage, but first must grab the user type from their text file
            File f = new File(Integer.toString(Library.userOnline.id));
            try (Scanner input = new Scanner(f)) {
                int i = input.nextInt();
                String name = input.next();
                int ut = input.nextInt();
                stage.setScene(viewLibrary(stage, ut));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        });

        back.setOnAction(e -> {
            if (userType == 0) {
                stage.setScene(adminHome(stage, Library.userOnline.id, Library.userOnline.username));
            } else if (userType == 1) {
                try {
                    stage.setScene(memberHome(stage, Library.userOnline.id, Library.userOnline.username));
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        Scene scene = new Scene(pane, 450, 400);
        stage.setScene(scene);
        stage.setTitle("Search Books");
        stage.show();
        return scene;
    }

    /*
     * Shows the full library 
     * 
     * Uses the data from the binary search tree
     */

    public static Scene viewLibrary(Stage stage, int userType) {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #2A2E37;");
        pane.setPadding(new Insets(11, 12, 13, 14));


        VBox vbox = new VBox();
        HBox title = new HBox();
        Label label = new Label("Library");
        label.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 20px; -fx-font-weight: bold");
        VBox lib = new VBox();
        Label library = new Label(books.toString());
        library.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 15px;");
        Label lsearch = new Label("Search");
        lsearch.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 20px; -fx-font-weight: bold");
        Label searchBook = new Label("Search Book ID: ");
        searchBook.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 15px;");
        lib.getChildren().addAll(library);
        Button back = new Button("Back to Menu");
        back.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        TextField id = new TextField();
        id.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
        Button reset = new Button("Reset Search");
        reset.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        Button search = new Button("Search");
        search.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");

        BorderPane.setAlignment(vbox, Pos.TOP_RIGHT);
        BorderPane.setMargin(vbox, new Insets(10));
        vbox.getChildren().addAll(searchBook, id, search, reset, back);
        vbox.setSpacing(10);
        BorderPane.setMargin(title, new Insets(10));
        BorderPane.setAlignment(title, Pos.CENTER);
        title.getChildren().addAll(new Label("            "), label, new Label("                                             "), lsearch);
        title.setSpacing(10);

        BorderPane.setMargin(lib, new Insets(10));
        BorderPane.setAlignment(lib, Pos.TOP_LEFT);
        HBox right = new HBox();
        right.getChildren().addAll(new Label("         "));

        pane.setTop(title);
        pane.setCenter(lib);
        pane.setRight(vbox);

        //button hover effect
        search.setOnMouseMoved(f -> {
            search.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        search.setOnMouseExited(f -> {
            search.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        reset.setOnMouseMoved(f -> {
            reset.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        reset.setOnMouseExited(f -> {
            reset.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        back.setOnMouseMoved(f -> {
            back.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        back.setOnMouseExited(f -> {
            back.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });

        search.setOnAction(e -> {
            try {
                if(books.get(Integer.parseInt(id.getText())) == null) {
                    //will send an error message if the book id does not exist in the library
                    search.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                    id.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: red");
                    Label error = new Label("BookID doesn't exist, try again");
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
                } else {
                    //will print out the result, including the book name and its id
                    id.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: #FFFF8D;");
                    search.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
                    Label g = new Label(books.get(Integer.parseInt(id.getText())));
                    Label u = new Label(id.getText());
                    Label r = new Label("Result: ");

                    r.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 18px; -fx-font-weight: bold");
                    g.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 15px;");
                    u.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 15px;");

                    VBox result = new VBox();
                    result.setSpacing(10);
                    BorderPane.setAlignment(result, Pos.TOP_RIGHT);
                    BorderPane.setMargin(result, new Insets(30));
                    result.getChildren().addAll(r, u, g);

                    pane.setCenter(result);
                }
            } catch (Exception h){
                //will send an error message if the id is not put into the textfield
                search.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                id.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: red");
                Label error = new Label("No ID, try again");
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

        reset.setOnAction(e -> {
            pane.setCenter(lib);
        });

        back.setOnAction(e -> {
            if (userType == 0) {
                stage.setScene(adminHome(stage, Library.userOnline.id, Library.userOnline.username));
            } else if (userType == 1) {
                try {
                    stage.setScene(memberHome(stage, Library.userOnline.id, Library.userOnline.username));
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });


        Scene scene = new Scene(pane, 430, 500);
        stage.setTitle("Library");
        stage.setScene(scene);
        stage.show();
        
        return scene;
    }

    /*
     * Appends the library save text file by adding a newly created Book object
     * 
     * If this is the first book to be added in the library, it will create
     * the library-save.txt file
     */

    public static void saveBook(int id, String bookName) throws IOException {
        File f = new File("library-save.txt");
        if(f.exists()) {
            FileWriter writer = new FileWriter(f.getName(), true);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(bookName + System.lineSeparator());
            bw.write(id + System.lineSeparator());

            bw.close();
        } else {
            FileWriter writer = new FileWriter("library-save.txt");
            writer.write(bookName + System.lineSeparator());
            writer.write(id + System.lineSeparator());

            writer.close();
        }
    }
}


