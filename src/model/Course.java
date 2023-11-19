package model;

public class Course {

    // Declaring attributes of Course
    private String name;
    private String capacity;
    private String year;
    private String delivery;
    private String day;
    private String time;
    private String duration;
    private String available;

    // Course Constructor
    public Course(String name, String capacity, String year, String delivery,
            String day, String time, String duration) {
        this.name = name;
        this.capacity = capacity;
        this.year = year;
        this.delivery = delivery;
        this.day = day;
        this.time = time;
        this.duration = duration;
        this.available = "Yes";
    }

    // Getter and Setter Methods for Course

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getCapacity() {
        return capacity;
    }
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }


    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }


    public String getDelivery() {
        return delivery;
    }
    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }


    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }


    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }



    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }



    public void setAvailable(String available) {
        this.available = available;
    }
    public String getAvailable() {
        return available;
    }


    @Override
    public String toString() {
        return name;
    }
    public String getData() {
        return name + "," + capacity + "," + year + "," + delivery + "," + day + "," + time + "," + duration;
    }

}
