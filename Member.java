package com.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Member extends User {
    private int userType;


    public Member(int id, String username) {
        super(id, username);
        userType = 1;
    }

    /*
     * Appends a new file created for a new member with more information about the member
     * Contains the id, the username, and the usertype
     * 
     * This text file will be used to house all checked out books that the user will need to store over time
     */
    public static void createMemberFile(Member member) {
        String id = Integer.toString(member.getId());
        File file = new File(id);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(member.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }  

    /*
     * Reads the text file from the user online, and reads out all the checked out books in the inventory
     * 
     * The data will then be put into Book objects and sent into a Queue, meaning that the user will need
     * to return the oldest book before returning any more books
     */
    
    public static void loadCheckedOutBooks(int id) throws FileNotFoundException {
        File f = new File(Integer.toString(id));
        if(f.exists()) {
            Scanner input = new Scanner(f);
            
            int i = input.nextInt();
            String j = input.next();
            String k = input.next();

            while(input.hasNext()) {
                try{ 
                String name = input.nextLine();
                int id2 = input.nextInt();
                System.out.println(name);

                //new book object is being created after getting data for one book id and one book name
                //and then puts it into the queue
                Book book = new Book(id2, name);
                Library.checkedOutBooks.add(book);
            } catch (Exception e) {
                e.getCause();
                }
            }
            input.close();

        }
    }

    /*
     * Method for the user to check out a book from the library
     * 
     * If book is not in the library, or is already in their queue, it will send an error message and send them back 
     * to the check out screen
     */

    public static Scene checkOutBook(Stage stage) {
        //make it so it sees if the book is already in the queue
        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: #2A2E37;");
        pane.setPadding(new Insets(11, 12, 13, 14));
        
        Label title = new Label("Check Out Book:");
        title.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 20px; -fx-font-weight: bold");

        Label bookid = new Label("Book ID: ");
        bookid.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 13px");

        TextField id = new TextField();
        id.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");

        Button checkOut = new Button("Check out Book");
        checkOut.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");

        Button back = new Button("Back");
        back.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");


        pane.add(title, 0, 0);
        pane.add(bookid, 0, 1);
        pane.add(id, 0, 2);
        pane.add(back, 0, 7);
        pane.add(checkOut, 1, 2);

        GridPane.setMargin(title, new Insets(5));
        GridPane.setMargin(id, new Insets(5));
        GridPane.setMargin(bookid, new Insets(5));
        GridPane.setMargin(back, new Insets(20));
        GridPane.setMargin(checkOut, new Insets(5));


        checkOut.setOnMouseMoved(f -> {
            checkOut.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        checkOut.setOnMouseExited(f -> {
            checkOut.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        back.setOnMouseMoved(f -> {
            back.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        }); 
        back.setOnMouseExited(f -> {
            back.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });

        checkOut.setOnAction(e -> {
            
            try {
                if(Library.books.get(Integer.parseInt(id.getText())) != null){
                BorderPane pane2 = new BorderPane();
                pane2.setStyle("-fx-background-color: #2A2E37;");
                pane2.setPadding(new Insets(11, 12, 13, 14));

                String bookName = Library.books.get(Integer.parseInt(id.getText()));
                Button yes = new Button("Yes");
                Button no = new Button("No");
                yes.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                no.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                

                yes.setOnMouseMoved(f -> {
                    yes.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                });
                yes.setOnMouseExited(f -> {
                    yes.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                });
                no.setOnMouseMoved(f -> {
                    no.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                });
                no.setOnMouseExited(f -> {
                    no.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                });
                Label question = new Label("Do you want to check out " + bookName + "?");
                pane2.setTop(question);
                question.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 15px;");
                question.setPadding(new Insets(20));
                
                VBox yesno = new VBox();
                yesno.setSpacing(10);
                yesno.getChildren().addAll(yes, no);
                pane2.setLeft(yesno);

                yes.setOnAction(g -> {
                    Library.checkedOutBooks.add(new Book(Integer.parseInt(id.getText()), Library.books.get(Integer.parseInt(id.getText()))));
                    Label confirmation = new Label("Enjoy reading " + bookName + " and remember\n to return your books!" +
                                                "\n To return this book, you must return \nyour previous books first!\n Thank you!");
                    confirmation.setStyle("-fx-text-fill: #FFFF8D; -fx-font-size: 13px;");
                    Button back2 = new Button("Back");
                    back2.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                    back2.setOnMouseMoved(f -> {
                        back2.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                    });
                    back2.setOnMouseExited(f -> {
                        back2.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                    });
                    pane2.setCenter(confirmation);
                    pane2.setBottom(back2);
                    try {
                        saveCheckedOutBook(Integer.parseInt(id.getText()), bookName, new Member(Library.userOnline.id, Library.userOnline.username));
                    } catch (NumberFormatException | IOException e1) {
                        e1.printStackTrace();
                    }

                    back2.setOnAction(h -> {
                        try {
                            stage.setScene(Library.memberHome(stage, Library.userOnline.id, Library.userOnline.username));
                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        }
                    });
                
                });

                no.setOnAction(g -> {
                    try {
                        stage.setScene(Library.memberHome(stage, Library.userOnline.id, Library.userOnline.username));
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }

                     
                });

                Scene scene = new Scene(pane2, 400, 250);
                    stage.setTitle("Check Out Book");
                    stage.setScene(scene);
                    stage.show();
            } else {
                id.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                checkOut.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                Label error = new Label("ID doesn't exists, try again");
                error.setStyle("-fx-text-fill: red; -fx-font-size: 13px;");
                pane.add(error, 0, 3);

                
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

            } catch (Exception f) {
                id.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                checkOut.setStyle("-fx-text-fill: #B2B2B2; -fx-background-color: #2A2E37; -fx-highlight-fill: #FFFF8D; -fx-border-color: red; -fx-border-radius: 20px; -fx-background-radius: 30px;");
                Label error = new Label("No ID/Invalid ID. Try again!");
                error.setStyle("-fx-text-fill: red; -fx-font-size: 13px;");
                pane.add(error, 0, 3);

                
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
            try {
                stage.setScene(Library.memberHome(stage, Library.userOnline.id, Library.userOnline.username));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        });

        Scene scene = new Scene(pane, 400, 250);
        stage.setTitle("Check Out Book");
        stage.setScene(scene);
        stage.show();
        return scene;
    }

    /*
     * Appends the user text file to add another book
     * 
     * Updates right after the user checks out a book, in case the user wants to return the book or return any other 
     * book and needs to know how many books they have in their possession
     */
    public static void saveCheckedOutBook(int id, String name, Member member) throws IOException {
        File f = new File(Integer.toString(member.getId()));
        FileWriter writer = new FileWriter(f.getName(), true);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write("\n" + name + "\n");
            bw.write(Integer.toString(id));

            bw.close();
    }

    /*
     * Returns the oldest book in the member's queue
     * Has a confirmation page to make sure that the user wants to return the book
     * 
     * Updates immediately to the user's text file and the queue, to give the user the
     * ability to return another book if necessary
     */
    public static Scene returnBook(Stage stage) {
        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: #2A2E37;");
        pane.setPadding(new Insets(11, 12, 13, 14));

        Book book = Library.checkedOutBooks.peek();
        Label label = new Label("Do you want to return " + Library.checkedOutBooks.peek());
        label.setPadding(new Insets(10));
        label.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-color: #2A2E37");

        Button yes = new Button("Yes");
        Button no = new Button("No");

        yes.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        no.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        yes.setOnMouseMoved(f -> {
            yes.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        yes.setOnMouseExited(f -> {
            yes.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        no.setOnMouseMoved(f -> {
            no.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        no.setOnMouseExited(f -> {
            no.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        pane.add(label, 0, 0);
        pane.add(yes, 0, 1);
        pane.add(no, 1, 1);

        yes.setOnAction(e -> {
            Library.checkedOutBooks.remove();
            Label success = new Label("Successfully removed " + book.getBookName() + "!\nRemember to logout to save!");
            success.setPadding(new Insets(10));
            success.setStyle("-fx-text-fill: #FFFF8D; -fx-font-size: 13px;");

            Button back = new Button("Back");
            back.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
            back.setOnMouseMoved(f -> {
                back.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
            });
            back.setOnMouseExited(f -> {
                back.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px;");
            });
            pane.add(success, 0, 2);
            pane.add(back, 0, 4);

            back.setOnAction(f -> {
                try {
                    Library.memberHome(stage, Library.userOnline.id, Library.userOnline.username);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            });
        });

        no.setOnAction(e -> {
            try {
                stage.setScene(Library.memberHome(stage, Library.userOnline.id, Library.userOnline.username));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        });

        Scene scene = new Scene(pane, 400, 250);
        stage.setTitle("Return Book");
        stage.setScene(scene);
        stage.show();
        return scene;
    }

    /*
     * Shows a table of all the checked out books the user currently has in their possession
     * 
     * Shows the book's id and name
     */
    public static Scene viewCheckedOutBooks(Stage stage) {
        List list = (List) Library.checkedOutBooks;
        int i = 0, j = 0, l = 0;
        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: #2A2E37;");
        pane.setPadding(new Insets(11, 12, 13, 14));
        VBox vbox = new VBox();
        GridPane.setMargin(vbox, new Insets(10));
        vbox.setSpacing(10);
        Label label = new Label("                    Checked Out Books");
        label.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: #2A2E37");

        Button back = new Button("Back to Menu");
        back.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        back.setOnMouseMoved(f -> {
            back.setStyle("-fx-background-color: #FFFF8D; -fx-text-fill: #2A2E37; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });
        back.setOnMouseExited(f -> {
            back.setStyle("-fx-background-color: #2A2E37; -fx-text-fill: #B2B2B2; -fx-border-color: #FFFF8D; -fx-border-radius: 20px; -fx-background-radius: 30px; -fx-font-size: 15px;");
        });

        pane.getColumnConstraints().add(new ColumnConstraints(500));
        pane.getColumnConstraints().add(new ColumnConstraints(500));
        pane.getColumnConstraints().add(new ColumnConstraints(500));

        for(int k = 0; k < list.size(); k++) {
            Label a = new Label(list.get(l).toString());
            a.setStyle("-fx-text-fill: #B2B2B2; -fx-font-size: 15px;");
            a.setLineSpacing(30);

            pane.add(a, i, j);

            l++;
            i = 0;
            j++;
        }

        back.setOnAction(e -> {
            try {
                stage.setScene(Library.memberHome(stage, Library.userOnline.id, Library.userOnline.username));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        });

        vbox.getChildren().addAll(label, pane, back);
        vbox.setStyle("-fx-background-color: #2A2E37");


        Scene scene = new Scene(vbox, 400, 400);
        stage.setTitle("Checked Out Books");
        stage.setScene(scene);
        stage.show();
        
        return scene;
    }

    /*
     * Before the member logs out of the dashboard, this method will update their text file by
     * overwritting all the data to make sure that all their changes have been saved for next time
     */
    public static void memberLogout() throws IOException {
        File f = new File(Integer.toString(Library.userOnline.id));
        if(f.exists()) {
            FileWriter writer = new FileWriter(Integer.toString(Library.userOnline.id), false);

            writer.write(Library.userOnline.id + System.lineSeparator());
            writer.write(Library.userOnline.username + System.lineSeparator());
            writer.write("1");

            for(Book book: Library.checkedOutBooks) {
                writer.write("\n" + book.getBookName()+ "\n");
                writer.write(Integer.toString(book.getBookID()));
            }

            writer.close();
        }
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
