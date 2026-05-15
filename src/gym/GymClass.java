package gym;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GymClass {
    private static int idCounter = 3000;

    private String classId;
    private String className;
    private Trainer trainer;
    private LocalDateTime schedule;
    private int capacity;
    private int duration; // minutes
    private List<Member> enrolledMembers;

    public GymClass(String className, Trainer trainer,
                    LocalDateTime schedule, int capacity, int duration) {
        this.classId         = "CLS" + (++idCounter);
        this.className       = className;
        this.trainer         = trainer;
        this.schedule        = schedule;
        this.capacity        = capacity;
        this.duration        = duration;
        this.enrolledMembers = new ArrayList<>();
        trainer.assignClass(className);
    }

    public boolean enroll(Member member) {
        if (!member.isActive()) {
            System.out.println("Cannot enroll: " + member.getName() + "'s membership is expired.");
            return false;
        }
        if (enrolledMembers.size() >= capacity) {
            System.out.println("Cannot enroll: Class '" + className + "' is full.");
            return false;
        }
        if (enrolledMembers.contains(member)) {
            System.out.println(member.getName() + " is already enrolled in " + className);
            return false;
        }
        enrolledMembers.add(member);
        System.out.println(member.getName() + " enrolled in " + className);
        return true;
    }

    public boolean unenroll(Member member) {
        if (enrolledMembers.remove(member)) {
            System.out.println(member.getName() + " unenrolled from " + className);
            return true;
        }
        System.out.println(member.getName() + " was not enrolled in " + className);
        return false;
    }

    public int getAvailableSlots() {
        return capacity - enrolledMembers.size();
    }

    public void displayInfo() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("=== Class Info ===");
        System.out.println("ID        : " + classId);
        System.out.println("Class     : " + className);
        System.out.println("Trainer   : " + trainer.getName());
        System.out.println("Schedule  : " + schedule.format(fmt));
        System.out.println("Duration  : " + duration + " mins");
        System.out.println("Capacity  : " + enrolledMembers.size() + "/" + capacity);
        System.out.println("Available : " + getAvailableSlots() + " slots");
    }

    // Getters
    public String getClassId()                    { return classId; }
    public String getClassName()                  { return className; }
    public Trainer getTrainer()                   { return trainer; }
    public LocalDateTime getSchedule()            { return schedule; }
    public int getCapacity()                      { return capacity; }
    public int getDuration()                      { return duration; }
    public List<Member> getEnrolledMembers()      { return enrolledMembers; }

    @Override
    public String toString() {
        return String.format("GymClass{id=%s, name=%s, slots=%d/%d}",
                classId, className, enrolledMembers.size(), capacity);
    }
}