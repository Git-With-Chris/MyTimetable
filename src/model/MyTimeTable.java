/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MyTimeTable {

    // List to store courses
    private ArrayList<Course> courses;

    // Currently logged-in user
    private User currentUser;

    // Database instance
    private Database db;

    // Font style
    private String style;

    // Font color
    private String color;

    // Font type
    private String type;

    // Font size
    private int size;
    
    public MyTimeTable() {
        // Initialize the course list
        courses = new ArrayList<>();
        loadCourses();

        // Get the singleton instance of the Database class
        db = Database.getInstance();

        // Initialise Font
        style = "";
        color = "Black";
        type = "Calibri";
        size = 12;
    }
    
    private void loadCourses() {
        try {
            // Read course data from a CSV file
            Scanner sc = new Scanner(new File("course.csv"));
            sc.nextLine();
            sc.useDelimiter("[,\n]");
            while (sc.hasNext()) {
                // Create a new Course object and add it to the course list
                courses.add(new Course(sc.next(), sc.next(), sc.next(),
                        sc.next(), sc.next(), sc.next(), sc.next()));
            }
            sc.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    
    public void setFont(int size, String style, String color, String type) {
        this.size = size;
        this.style = style;
        this.color = color;
        this.type = type;
    }
    
    public String getFont() {
        // Generate the font style string based on the selected font attributes
        String s = "-fx-font-size:" + size + ";-fx-font-family:"
                + type + ";-fx-text-fill:" + color + ";";
        if (style.equals("Italic")) {
            s += "-fx-font-style:" + style;
        } else if (style.equals("Bold")) {
            s += ";-fx-font-weight:" + style;
            
        }
        return s;
    }
    
    public boolean login(String un, String pass) {
        // Authenticate the user
        User user = db.login(un, pass);

        // Set the current user
        currentUser = user;
        return user != null;
    }
    
    public boolean signup(User user) {
        if (db.getUser(user.getUsername()) == null) {

            // Sign up a new user
            db.signup(user);
            return true;
        }
        return false;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public String getColor() {
        return color;
    }
    
    public int getSize() {
        return size;
    }
    
    public String getStyle() {
        return style;
    }
    
    public String getType() {
        return type;
    }
    
    public boolean updateUser(User user) {
        // Update user information in the database
        if (db.updateUser(user)) {
            currentUser = user;
            return true;
        }
        return false;
    }
    
    public ArrayList<Course> getEnrolledCourses() {
        // Retrieve the Course objects corresponding to the enrolled courses
        ArrayList<String> list1 = db.getCourses(currentUser.getUsername());
        ArrayList<Course> list2 = new ArrayList<>();
        for (String courseName : list1) {
            list2.add(getCourse(courseName));
        }
        return list2;
    }
    
    private Course getCourse(String courseName) {

        // Find and return the Course object with the given course name
        for (Course course : courses) {
            if (course.getName().equals(courseName)) {
                return course;
            }
            // If the course is not found, return null
        }
        return null;
    }
    
    public ArrayList<Course> getCourses(String filter) {
        // Update the availability of courses based on enrollment capacity
        for (Course course : courses) {
            if (!course.getDelivery().equals("Online")) {
                int capacity = Integer.parseInt(course.getCapacity());
                int enrollments = db.getEnrollments(course.getName());
                if (enrollments == capacity) {
                    course.setAvailable("No");
                }
            }
        }
        // Filter the course list based on the given filter string
        ArrayList<Course> list = new ArrayList<>();
        for (Course course : courses) {
            if (course.getName().toLowerCase().contains(filter.toLowerCase())) {
                list.add(course);
            }
        }
        return list;
    }
    
    public boolean isEnrolled(Course selectedItem) {
        return getEnrolledCourses().contains(selectedItem);
    }
    
    public void enroll(Course selectedItem) {
        db.enrollIntoCourse(currentUser.getUsername(), selectedItem.getName());
    }
    
    public void withdraw(Course selectedItem) {
        db.withdrawCourse(currentUser.getUsername(), selectedItem.getName());
    }
    
    public void export(String name) {
        // Create a new CSV file for exporting
        try {
            PrintWriter pw = new PrintWriter(new File(name + ".csv"));
            pw.println("Course name,Capacity,Year,Delivery mode,Day of lecture,Time of lecture,Duration of lecture (hour)");
            for (Course enrolledCourse : getEnrolledCourses()) {
                pw.println(enrolledCourse.getData());
            }
            pw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}
