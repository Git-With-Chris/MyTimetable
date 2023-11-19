package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {

    // Declare Variables
    private static Database db;
    private Connection con;
    private Statement st;
    private String query;
    private static final String DB_URL = "jdbc:sqlite:MyTimeTable.db";

    // DataBase Constructor (2 Tables)
    private Database() {
        try {
            // Establish a connection to the database
            con = DriverManager.getConnection(DB_URL);
            st = con.createStatement();

            // Create the 'users' table if it doesn't exist
            query = "CREATE TABLE IF NOT EXISTS users (\n"
                    + "    fname    VARCHAR NOT NULL,\n"
                    + "    lname    VARCHAR NOT NULL,\n"
                    + "    number   VARCHAR NOT NULL,\n"
                    + "    username VARCHAR NOT NULL\n"
                    + "                     PRIMARY KEY,\n"
                    + "    password         NOT NULL\n"
                    + ");";
            st.executeUpdate(query);

            // Create the 'enrollments' table if it doesn't exist
            query = "CREATE TABLE IF NOT EXISTS enrollments (\n"
                    + "    username   VARCHAR NOT NULL\n"
                    + "                       PRIMARY KEY,\n"
                    + "    coursename VARCHAR NOT NULL\n"
                    + ");";
            st.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get the singleton instance of the Database class
    public static Database getInstance() {
        if (db == null) {
            db = new Database();
        }
        return db;
    }

    // Authenticate the user by checking the username and password in the 'users' table
    public User login(String un, String pass) {
        query = "SELECT * FROM users WHERE username = '" + un + "' AND password = '" + pass + "';";
        try {
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                return new User(rs.getString("fname"), rs.getString("lname"),
                        rs.getString("number"), un, pass);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    // Retrieve user information from the 'users' table based on the username
    public User getUser(String un) {
        query = "SELECT * FROM users WHERE username = '" + un + "';";
        try {
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                return new User(rs.getString("fname"), rs.getString("lname"),
                        rs.getString("number"), un, rs.getString("password"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    // Add a new user to the 'users' table
    public boolean signup(User user) {
        query = "Insert into users values('" + user.getFname() + "','" + user.getLname()
                + "','" + user.getNumber() + "','" + user.getUsername() + "','" + user.getPassword() + "');";
        try {
            return st.executeUpdate(query) > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    // Update user information in the 'users' table
    public boolean updateUser(User user) {
        query = "Update users set fname = '" + user.getFname()
                + "', lname = '" + user.getLname() + "', password = '"
                + user.getPassword() + "' where username = '" + user.getUsername() + "'";
        try {
            return st.executeUpdate(query) > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    // Get the list of courses enrolled by a user
    public ArrayList<String> getCourses(String username) {
        ArrayList<String> list = new ArrayList<>();
        query = "Select * from enrollments where username = '" + username + "'";
        try {
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                list.add(rs.getString("coursename"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    // Get the count of enrollments for a specific course
    public int getEnrollments(String name) {
        query = "select COUNT(coursename) AS count from enrollments where coursename = '" + name + "'";
        try {
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    // Enroll a user into a course by adding a new entry to the 'enrollments' table
    public boolean enrollIntoCourse(String username, String name) {
        query = "INSERT INTO enrollments values ('" + username + "','" + name + "')";
        try {
            return st.executeUpdate(query) > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    // Withdraw a course by removing the corresponding entry from the 'enrollments' table
    public boolean withdrawCourse(String username, String name) {
        query = "Delete from enrollments where username ='" + username + "' AND coursename = '" + name + "'";
        try {
            return st.executeUpdate(query) > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
