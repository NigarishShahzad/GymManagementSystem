package gym;

import java.time.LocalDate;

public class Membership {
    private MembershipType type;
    private LocalDate startDate;
    private LocalDate endDate;
    private double monthlyFee;

    public Membership(MembershipType type) {
        this.type = type;
        this.monthlyFee = type.getMonthlyFee();
        this.startDate = LocalDate.now();
        this.endDate = startDate.plusMonths(1);
    }

    public boolean isActive() {
        return LocalDate.now().isBefore(endDate) || LocalDate.now().isEqual(endDate);
    }

    public void renewPlan(int months) {
        if (isActive()) {
            this.endDate = endDate.plusMonths(months);
        } else {
            this.startDate = LocalDate.now();
            this.endDate = startDate.plusMonths(months);
        }
        System.out.println("Membership renewed for " + months + " month(s). New end date: " + endDate);
    }

    public void upgradePlan(MembershipType newType) {
        this.type = newType;
        this.monthlyFee = newType.getMonthlyFee();
        System.out.println("Membership upgraded to: " + newType);
    }

    public double getTotalCost(int months) {
        return monthlyFee * months;
    }

    // Getters
    public MembershipType getType()       { return type; }
    public LocalDate getStartDate()       { return startDate; }
    public LocalDate getEndDate()         { return endDate; }
    public double getMonthlyFee()         { return monthlyFee; }

    @Override
    public String toString() {
        return String.format("[%s | Fee: Rs.%.2f/month | %s to %s | Active: %b]",
                type, monthlyFee, startDate, endDate, isActive());
    }
}