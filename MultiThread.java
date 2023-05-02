package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MultiThread extends Thread {

    /*
     * Loads the data from the central library system and the central user system
     * This runs in the background when you log in, so the program doesn't take
     * extra time to run it in a separate method
     * 
     */
    @Override
    public void run() {
        File f = new File("library-save.txt");
        try (Scanner input = new Scanner(f)) {
            while(input.hasNext()) {
                String j = input.nextLine();
                String i = input.nextLine();

                //puts each book in the binary tree
                Library.books.put(Integer.parseInt(i), j);
            }
        } catch (FileNotFoundException e) {
            e.getCause();
        }

        File g = new File("users-save.txt");
        try (Scanner in = new Scanner(g)) {
            while(in.hasNext()) {
                int id = in.nextInt();
                String name = in.next();
                int userType = in.nextInt();

                //puts each user in the user map
                Library.users.put(id, name);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
