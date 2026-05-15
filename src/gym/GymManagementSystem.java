package gym;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GymManagementSystem {
    private String gymName;
    private List<Member>    members;
    private List<Trainer>   trainers;
    private List<GymClass>  classes;
    private List<Equipment> equipment;

    public GymManagementSystem(String gymName) {
        this.gymName   = gymName;
        this.members   = new ArrayList<>();
        this.trainers  = new ArrayList<>();
        this.classes   = new ArrayList<>();
        this.equipment = new ArrayList<>();
        System.out.println("=== " + gymName + " Management System Initialized ===\n");
    }

    // ─── Member Operations ───────────────────────────────────────────────────

    public Member registerMember(String name, String email,
                                  String phone, int age, MembershipType type) {
        Member m = new Member(name, email, phone, age, type);
        members.add(m);
        System.out.println("Member registered: " + m.getName() + " [" + m.getMemberId() + "]");
        return m;
    }

    public Optional<Member> findMemberById(String id) {
        return members.stream().filter(m -> m.getMemberId().equals(id)).findFirst();
    }

    public Optional<Member> findMemberByName(String name) {
        return members.stream()
                .filter(m -> m.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public boolean removeMember(String memberId) {
        Optional<Member> m = findMemberById(memberId);
        if (m.isPresent()) {
            members.remove(m.get());
            System.out.println("Member removed: " + m.get().getName());
            return true;
        }
        System.out.println("Member not found: " + memberId);
        return false;
    }

    public void listAllMembers() {
        System.out.println("\n=== All Members (" + members.size() + ") ===");
        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }
        members.forEach(m ->
            System.out.printf("%-10s %-20s %-10s Active: %b%n",
                m.getMemberId(), m.getName(), m.getMembership().getType(), m.isActive()));
    }

    public long countActiveMembers() {
        return members.stream().filter(Member::isActive).count();
    }

    // ─── Trainer Operations ──────────────────────────────────────────────────

    public Trainer addTrainer(String name, String specialization,
                               String email, double salary) {
        Trainer t = new Trainer(name, specialization, email, salary);
        trainers.add(t);
        System.out.println("Trainer added: " + t.getName() + " [" + t.getTrainerId() + "]");
        return t;
    }

    public Optional<Trainer> findTrainerById(String id) {
        return trainers.stream().filter(t -> t.getTrainerId().equals(id)).findFirst();
    }

    public boolean removeTrainer(String trainerId) {
        Optional<Trainer> t = findTrainerById(trainerId);
        if (t.isPresent()) {
            trainers.remove(t.get());
            System.out.println("Trainer removed: " + t.get().getName());
            return true;
        }
        System.out.println("Trainer not found: " + trainerId);
        return false;
    }

    public void listAllTrainers() {
        System.out.println("\n=== All Trainers (" + trainers.size() + ") ===");
        if (trainers.isEmpty()) {
            System.out.println("No trainers available.");
            return;
        }
        trainers.forEach(t ->
            System.out.printf("%-10s %-20s %-20s Rs.%.2f%n",
                t.getTrainerId(), t.getName(), t.getSpecialization(), t.getSalary()));
    }

    // ─── GymClass Operations ─────────────────────────────────────────────────

    public GymClass scheduleClass(String className, Trainer trainer,
                                   java.time.LocalDateTime schedule,
                                   int capacity, int duration) {
        GymClass gc = new GymClass(className, trainer, schedule, capacity, duration);
        classes.add(gc);
        System.out.println("Class scheduled: " + className + " [" + gc.getClassId() + "]");
        return gc;
    }

    public Optional<GymClass> findClassById(String id) {
        return classes.stream().filter(c -> c.getClassId().equals(id)).findFirst();
    }

    public boolean cancelClass(String classId) {
        Optional<GymClass> gc = findClassById(classId);
        if (gc.isPresent()) {
            classes.remove(gc.get());
            System.out.println("Class cancelled: " + gc.get().getClassName());
            return true;
        }
        System.out.println("Class not found: " + classId);
        return false;
    }

    public void listAllClasses() {
        System.out.println("\n=== Scheduled Classes (" + classes.size() + ") ===");
        if (classes.isEmpty()) {
            System.out.println("No classes scheduled.");
            return;
        }
        classes.forEach(c ->
            System.out.printf("%-10s %-20s %-20s Slots: %d/%d%n",
                c.getClassId(), c.getClassName(),
                c.getTrainer().getName(),
                c.getEnrolledMembers().size(), c.getCapacity()));
    }

    // ─── Equipment Operations ────────────────────────────────────────────────

    public Equipment addEquipment(String name) {
        Equipment eq = new Equipment(name);
        equipment.add(eq);
        System.out.println("Equipment added: " + eq.getName() + " [" + eq.getEquipmentId() + "]");
        return eq;
    }

    public Optional<Equipment> findEquipmentById(String id) {
        return equipment.stream().filter(e -> e.getEquipmentId().equals(id)).findFirst();
    }

    public void listAllEquipment() {
        System.out.println("\n=== Equipment List (" + equipment.size() + ") ===");
        if (equipment.isEmpty()) {
            System.out.println("No equipment registered.");
            return;
        }
        equipment.forEach(e ->
            System.out.printf("%-10s %-25s %-25s%n",
                e.getEquipmentId(), e.getName(), e.getStatus()));
    }

    public long countAvailableEquipment() {
        return equipment.stream()
                .filter(e -> e.getStatus() == EquipmentStatus.AVAILABLE)
                .count();
    }

    // ─── Reports ─────────────────────────────────────────────────────────────

    public void generateReport() {
        System.out.println("\n========== GYM MANAGEMENT REPORT ==========");
        System.out.println("Gym Name         : " + gymName);
        System.out.println("Total Members    : " + members.size());
        System.out.println("Active Members   : " + countActiveMembers());
        System.out.println("Total Trainers   : " + trainers.size());
        System.out.println("Classes Scheduled: " + classes.size());
        System.out.println("Equipment Total  : " + equipment.size());
        System.out.println("Equipment Avail. : " + countAvailableEquipment());

        double totalRevenue = members.stream()
                .filter(Member::isActive)
                .mapToDouble(m -> m.getMembership().getMonthlyFee())
                .sum();
        System.out.printf("Monthly Revenue  : Rs.%.2f%n", totalRevenue);
        System.out.println("============================================");
    }

    // Getters
    public List<Member>    getMembers()    { return members; }
    public List<Trainer>   getTrainers()   { return trainers; }
    public List<GymClass>  getClasses()    { return classes; }
    public List<Equipment> getEquipment()  { return equipment; }
}