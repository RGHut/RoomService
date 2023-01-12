package CG.RoomService.Models;

import java.time.LocalDateTime;

public class Room {

    private String name;
    private int floor;
    private int maxOccupancy;
    private boolean isAccessible;

    public Room(String name, int floor, int maxOccupancy, boolean isAccessible) {
        this.name = name;
        this.floor = floor;
        this.maxOccupancy = maxOccupancy;
        this.isAccessible = isAccessible;
    }

    public Room(int floor, int maxOccupancy, boolean isAccessible) {
        this("", floor, maxOccupancy, isAccessible);
    }

    public Room(int floor, int maxOccupancy) {
        this(floor, maxOccupancy, true);
    }

    public Room(int floor) {
        this(floor, 8);
    }

    public Room() {
        this(1);
    }

    public Booking makeBooking(LocalDateTime start) {
        return(new Booking(this, start));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public boolean isAccessible() {
        return isAccessible;
    }

    public void setAccessible(boolean accessible) {
        isAccessible = accessible;
    }

}
