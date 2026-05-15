package gym;

public enum MembershipType {
    BASIC(1000.0),
    PREMIUM(2500.0),
    VIP(5000.0);

    private final double monthlyFee;

    MembershipType(double monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }
}