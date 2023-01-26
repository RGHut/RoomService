package CG.RoomService.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name")
    private final String name;
    @Column(name = "floor")
    private final int floor;
    @Column(name = "maxOccupancy")
    private int maxOccupancy;
    @Column(name = "isAccessible")
    private boolean isAccessible;
    private boolean pandemicMode = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public int getFloor() {
        return floor;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public void switchPandemicMode(){
        if (pandemicMode) {
            setMaxOccupancy(maxOccupancy * 2);
            pandemicMode = false;
        } else {
            setMaxOccupancy(maxOccupancy / 2);
            pandemicMode = true;
        }
    }

    public boolean isAccessible() {
        return isAccessible;
    }

    public void setAccessible(boolean accessible) {
        isAccessible = accessible;
    }
}