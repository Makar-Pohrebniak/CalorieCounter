package model;

public class User {
    private String name;
    private int age;
    private double height; // см
    private double weight; // кг
    private String gender; // "male" / "female"
    private double activity; // коефіцієнт активності

    public User(String name, int age, double height, double weight, String gender, double activity) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.activity = activity;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public double getHeight() { return height; }
    public double getWeight() { return weight; }
    public String getGender() { return gender; }
    public double getActivity() { return activity; }
}
