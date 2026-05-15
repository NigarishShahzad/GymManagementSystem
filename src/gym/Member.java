package gym;

public class Member {
    private static int idCounter = 1000;

    private String memberId;
    private String name;
    private String email;
    private String phone;
    private int age;
    private Membership membership;

    public Member(String name, String email, String phone, int age, MembershipType type) {
        this.memberId   = "MEM" + (++idCounter);
        this.name       = name;
        this.email      = email;
        this.phone      = phone;
        this.age        = age;
        this.membership = new Membership(type);
    }

    public boolean isActive() {
        return membership.isActive();
    }

    public void renewMembership(int months) {
        membership.renewPlan(months);
    }

    public void upgradeMembership(MembershipType newType) {
        membership.upgradePlan(newType);
    }

    public void displayInfo() {
        System.out.println("=== Member Info ===");
        System.out.println("ID     : " + memberId);
        System.out.println("Name   : " + name);
        System.out.println("Email  : " + email);
        System.out.println("Phone  : " + phone);
        System.out.println("Age    : " + age);
        System.out.println("Plan   : " + membership);
    }

    // Getters & Setters
    public String getMemberId()        { return memberId; }
    public String getName()            { return name; }
    public String getEmail()           { return email; }
    public String getPhone()           { return phone; }
    public int getAge()                { return age; }
    public Membership getMembership()  { return membership; }
    public void setName(String name)   { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return String.format("Member{id=%s, name=%s, active=%b}", memberId, name, isActive());
    }
}