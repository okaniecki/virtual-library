package com.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class User {
    protected int id;
    protected String username;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    /*
     * Checks to see if the user and their file exists in the system
     */
    public static boolean checkUser(int id) {
        File f = new File(Integer.toString(id));
        if(f.exists()) 
            return true;    
        return false;
    }

    /*
     * Saves a newly created user into the user-save.txt or the user system
     * Appends the current file, or create a new save file if this is the first
     * user in the system
     */
    public static void saveUsers(int id, String name, int userType) throws IOException {
        File f = new File("users-save.txt");
        if(f.exists()) {
            FileWriter writer = new FileWriter(f.getName(), true);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(id + System.lineSeparator());
            bw.write(name + System.lineSeparator());
            bw.write(userType + System.lineSeparator());

            bw.close();
        } else {
            FileWriter writer = new FileWriter("users-save.txt");
            writer.write(id + System.lineSeparator());
            writer.write(name + System.lineSeparator());
            writer.write(userType + System.lineSeparator());

            writer.close();
        }
    }

    /*
     * After removing an user, this method will rewrite the user system to make sure tha the user removed
     * is gone from the text file
     */
    public static void rewriteUsers() throws IOException {
        File f = new File("users-save.txt");
        if(f.exists()) {
            FileWriter writer = new FileWriter(f.getName(), false);
            BufferedWriter bw = new BufferedWriter(writer);

            for(Map.Entry<Integer, String> entry : Library.users.entrySet()){
                
                bw.write(entry.getKey() + "\n" + entry.getValue() + "\n");
                File g = new File(Integer.toString(entry.getKey()));
                Scanner input = new Scanner(g);
                int i = input.nextInt();
                String j = input.next();
                int k = input.nextInt();

                bw.write(Integer.toString(k));
                
                bw.newLine();
            }

            bw.close();
    }
}

    public String toString() {
        return id + "\n" + username + "\n";
    }



    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

