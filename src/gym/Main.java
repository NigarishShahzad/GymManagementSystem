package gym;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        GymManagementSystem gym = new GymManagementSystem("FitPro Gym");

        // Register members
        Member alice = gym.registerMember("Alice Khan",   "alice@email.com", "03001111111", 25, MembershipType.PREMIUM);
        Member bob   = gym.registerMember("Bob Ahmed",    "bob@email.com",   "03002222222", 30, MembershipType.BASIC);
        Member carol = gym.registerMember("Carol Malik",  "carol@email.com", "03003333333", 22, MembershipType.VIP);

        // Add trainers
        Trainer t1 = gym.addTrainer("Zaid Hassan",  "Yoga & Flexibility", "zaid@gym.com",  55000);
        Trainer t2 = gym.addTrainer("Sara Iqbal",   "Weight Training",    "sara@gym.com",  65000);

        // Schedule classes
        LocalDateTime classTime1 = LocalDateTime.of(2026, 5, 20, 9, 0);
        LocalDateTime classTime2 = LocalDateTime.of(2026, 5, 20, 11, 0);

        GymClass yogaClass  = gym.scheduleClass("Morning Yoga",   t1, classTime1, 10, 60);
        GymClass liftClass  = gym.scheduleClass("Weight Lifting", t2, classTime2, 5,  90);

        // Enroll members
        System.out.println();
        yogaClass.enroll(alice);
        yogaClass.enroll(bob);
        liftClass.enroll(carol);
        liftClass.enroll(alice);

        // Add equipment
        System.out.println();
        Equipment treadmill = gym.addEquipment("Treadmill A1");
        Equipment barbell   = gym.addEquipment("Olympic Barbell");
        Equipment bench     = gym.addEquipment("Adjustable Bench");

        // Maintenance cycle
        System.out.println();
        treadmill.sendForMaintenance();
        treadmill.completeMaintenance();
        barbell.markOutOfOrder();

        // Renew & upgrade
        System.out.println();
        bob.renewMembership(3);
        alice.upgradeMembership(MembershipType.VIP);

        // Display info
        System.out.println();
        alice.displayInfo();
        System.out.println();
        t1.displayInfo();
        System.out.println();
        yogaClass.displayInfo();
        System.out.println();
        treadmill.displayInfo();

        // Lists and report
        gym.listAllMembers();
        gym.listAllTrainers();
        gym.listAllClasses();
        gym.listAllEquipment();
        gym.generateReport();
    }
}