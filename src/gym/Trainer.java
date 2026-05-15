package gym;

import java.util.ArrayList;
import java.util.List;

public class Trainer {
    private static int idCounter = 2000;

    private String trainerId;
    private String name;
    private String specialization;
    private String email;
    private double salary;
    private List<String> assignedClasses;

    public Trainer(String name, String specialization, String email, double salary) {
        this.trainerId       = "TRN" + (++idCounter);
        this.name            = name;
        this.specialization  = specialization;
        this.email           = email;
        this.salary          = salary;
        this.assignedClasses = new ArrayList<>();
    }

    public void assignClass(String className) {
        assignedClasses.add(className);
        System.out.println(name + " assigned to class: " + className);
    }

    public void removeClass(String className) {
        if (assignedClasses.remove(className)) {
            System.out.println(name + " removed from class: " + className);
        } else {
            System.out.println("Class not found in trainer's list.");
        }
    }

    public void displayInfo() {
        System.out.println("=== Trainer Info ===");
        System.out.println("ID             : " + trainerId);
        System.out.println("Name           : " + name);
        System.out.println("Specialization : " + specialization);
        System.out.println("Email          : " + email);
        System.out.println("Salary         : Rs." + salary);
        System.out.println("Classes        : " + assignedClasses);
    }

    // Getters & Setters
    public String getTrainerId()              { return trainerId; }
    public String getName()                   { return name; }
    public String getSpecialization()         { return specialization; }
    public String getEmail()                  { return email; }
    public double getSalary()                 { return salary; }
    public List<String> getAssignedClasses()  { return assignedClasses; }
    public void setSalary(double salary)      { this.salary = salary; }
    public void setSpecialization(String s)   { this.specialization = s; }

    @Override
    public String toString() {
        return String.format("Trainer{id=%s, name=%s, spec=%s}", trainerId, name, specialization);
    }
}