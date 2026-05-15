package gym;

import java.time.LocalDate;

public class Equipment {
    private static int idCounter = 4000;

    private String equipmentId;
    private String name;
    private EquipmentStatus status;
    private LocalDate lastMaintenance;
    private int maintenanceCount;

    public Equipment(String name) {
        this.equipmentId      = "EQP" + (++idCounter);
        this.name             = name;
        this.status           = EquipmentStatus.AVAILABLE;
        this.lastMaintenance  = LocalDate.now();
        this.maintenanceCount = 0;
    }

    public void sendForMaintenance() {
        this.status = EquipmentStatus.UNDER_MAINTENANCE;
        System.out.println(name + " sent for maintenance.");
    }

    public void completeMaintenance() {
        this.status           = EquipmentStatus.AVAILABLE;
        this.lastMaintenance  = LocalDate.now();
        this.maintenanceCount++;
        System.out.println(name + " maintenance complete. Times maintained: " + maintenanceCount);
    }

    public void markOutOfOrder() {
        this.status = EquipmentStatus.OUT_OF_ORDER;
        System.out.println(name + " marked as OUT OF ORDER.");
    }

    public void displayInfo() {
        System.out.println("=== Equipment Info ===");
        System.out.println("ID                 : " + equipmentId);
        System.out.println("Name               : " + name);
        System.out.println("Status             : " + status);
        System.out.println("Last Maintenance   : " + lastMaintenance);
        System.out.println("Maintenance Count  : " + maintenanceCount);
    }

    // Getters & Setters
    public String getEquipmentId()         { return equipmentId; }
    public String getName()                { return name; }
    public EquipmentStatus getStatus()     { return status; }
    public LocalDate getLastMaintenance()  { return lastMaintenance; }
    public int getMaintenanceCount()       { return maintenanceCount; }
    public void setName(String name)       { this.name = name; }

    @Override
    public String toString() {
        return String.format("Equipment{id=%s, name=%s, status=%s}", equipmentId, name, status);
    }
}