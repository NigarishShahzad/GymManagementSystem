package gym;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GymManagementSystemTest {

    static GymManagementSystem gym;
    static Member alice, bob, carol;
    static Trainer trainer1, trainer2;
    static GymClass yogaClass, liftClass;
    static Equipment treadmill;

    @BeforeAll
    static void setup() {
        gym = new GymManagementSystem("Test Gym");

        alice = gym.registerMember("Alice", "alice@test.com", "03001", 25, MembershipType.PREMIUM);
        bob   = gym.registerMember("Bob", "bob@test.com", "03002", 30, MembershipType.BASIC);
        carol = gym.registerMember("Carol", "carol@test.com", "03003", 22, MembershipType.VIP);

        trainer1 = gym.addTrainer("Zaid", "Yoga", "zaid@test.com", 50000);
        trainer2 = gym.addTrainer("Sara", "Weight Training", "sara@test.com", 60000);

        yogaClass = gym.scheduleClass(
                "Yoga",
                trainer1,
                LocalDateTime.of(2026, 6, 1, 9, 0),
                2,
                60
        );

        liftClass = gym.scheduleClass(
                "Lifting",
                trainer2,
                LocalDateTime.of(2026, 6, 1, 11, 0),
                5,
                90
        );

        treadmill = gym.addEquipment("Treadmill");
    }

    // TC-01
    @Test
    @Order(1)
    @DisplayName("TC-01: Member registered with correct ID format")
    void testMemberRegistration() {
        assertNotNull(alice);
        assertTrue(alice.getMemberId().startsWith("MEM"));
        assertEquals("Alice", alice.getName());
        assertEquals("alice@test.com", alice.getEmail());
    }

    // TC-02
    @Test
    @Order(2)
    void testMembershipActive() {
        assertTrue(alice.isActive());
        assertTrue(bob.isActive());
    }

    // TC-03
    @Test
    @Order(3)
    void testMembershipFees() {
        assertEquals(1000.0, bob.getMembership().getMonthlyFee(), 0.01);
        assertEquals(2500.0, alice.getMembership().getMonthlyFee(), 0.01);
        assertEquals(5000.0, carol.getMembership().getMonthlyFee(), 0.01);
    }

    // TC-04
    @Test
    @Order(4)
    void testMembershipUpgrade() {
        bob.upgradeMembership(MembershipType.VIP);

        assertEquals(MembershipType.VIP, bob.getMembership().getType());
        assertEquals(5000.0, bob.getMembership().getMonthlyFee(), 0.01);

        bob.upgradeMembership(MembershipType.BASIC);
    }

    // TC-05
    @Test
    @Order(5)
    void testMembershipRenewal() {
        var before = alice.getMembership().getEndDate();

        alice.renewMembership(3);

        assertEquals(before.plusMonths(3), alice.getMembership().getEndDate());
    }

    // TC-06
    @Test
    @Order(6)
    void testTotalCost() {
        assertEquals(7500.0, alice.getMembership().getTotalCost(3), 0.01);
    }

    // TC-07
    @Test
    @Order(7)
    void testTrainerRegistration() {
        assertNotNull(trainer1);
        assertTrue(trainer1.getTrainerId().startsWith("TRN"));
        assertEquals("Zaid", trainer1.getName());
    }

    // TC-08
    @Test
    @Order(8)
    void testTrainerClassAssignment() {
        assertTrue(trainer1.getAssignedClasses().contains("Yoga"));
    }

    // TC-09
    @Test
    @Order(9)
    void testEnrollMember() {
        assertTrue(yogaClass.enroll(alice));
        assertEquals(1, yogaClass.getEnrolledMembers().size());
    }

    // TC-10
    @Test
    @Order(10)
    void testDuplicateEnrollment() {
        assertFalse(yogaClass.enroll(alice));
    }

    // TC-11
    @Test
    @Order(11)
    void testClassCapacity() {
        yogaClass.enroll(bob);
        assertFalse(yogaClass.enroll(carol));
    }

    // TC-12
    @Test
    @Order(12)
    void testAvailableSlots() {
        assertEquals(0, yogaClass.getAvailableSlots());
    }

    // TC-13
    @Test
    @Order(13)
    void testUnenrollMember() {
        assertTrue(yogaClass.unenroll(alice));
    }

    // TC-14
    @Test
    @Order(14)
    void testEquipmentDefaultStatus() {
        assertEquals(EquipmentStatus.AVAILABLE, treadmill.getStatus());
    }

    // TC-15
    @Test
    @Order(15)
    void testMaintenanceCycle() {
        treadmill.sendForMaintenance();
        assertEquals(EquipmentStatus.UNDER_MAINTENANCE, treadmill.getStatus());

        treadmill.completeMaintenance();
        assertEquals(EquipmentStatus.AVAILABLE, treadmill.getStatus());
    }

    // TC-16
    @Test
    @Order(16)
    void testEquipmentOutOfOrder() {
        treadmill.markOutOfOrder();
        assertEquals(EquipmentStatus.OUT_OF_ORDER, treadmill.getStatus());
    }

    // TC-17
    @Test
    @Order(17)
    void testFindMemberById() {
        assertTrue(gym.findMemberById(alice.getMemberId()).isPresent());
    }

    // TC-18
    @Test
    @Order(18)
    void testFindMemberByName() {
        assertTrue(gym.findMemberByName("bob").isPresent());
    }

    // TC-19
    @Test
    @Order(19)
    void testRemoveMember() {
        Member temp = gym.registerMember("Temp", "temp@test.com", "03099", 20, MembershipType.BASIC);
        assertTrue(gym.removeMember(temp.getMemberId()));
    }

    // TC-20
    @Test
    @Order(20)
    void testActiveMemberCount() {
        long count = gym.countActiveMembers();
        assertTrue(count >= 0);
    }

    // TC-21
    @Test
    @Order(21)
    void testCancelClass() {
        GymClass temp = gym.scheduleClass("Temp", trainer2,
                LocalDateTime.of(2026, 7, 1, 8, 0),
                3, 45);

        assertTrue(gym.cancelClass(temp.getClassId()));
    }

    // TC-22
    @Test
    @Order(22)
    void testRemoveTrainer() {
        Trainer temp = gym.addTrainer("Temp", "Zumba", "tmp@test.com", 40000);
        assertTrue(gym.removeTrainer(temp.getTrainerId()));
    }

    // TC-23
    @Test
    @Order(23)
    void testAvailableEquipmentCount() {
        gym.addEquipment("Bike");
        assertTrue(gym.countAvailableEquipment() >= 1);
    }

    // TC-24
    @Test
    @Order(24)
    void testTrainerSalaryUpdate() {
        trainer1.setSalary(70000);
        assertEquals(70000.0, trainer1.getSalary(), 0.01);
    }

    // TC-25
    @Test
    @Order(25)
    void testToStringMethods() {
        assertFalse(alice.toString().isEmpty());
        assertFalse(trainer1.toString().isEmpty());
        assertFalse(yogaClass.toString().isEmpty());
        assertFalse(treadmill.toString().isEmpty());
    }
}