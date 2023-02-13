package CG.RoomService.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "name")
    private final String name;
    @Column(name = "floor")
    private final int floor;
    @Column(name = "maxOccupancy")
    private int maxOccupancy;
    @Column(name = "isAccessible")
    private boolean isAccessible;
    private boolean pandemicMode = false;
    @OneToMany(mappedBy = "room")
    private List<Booking> bookings = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

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

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public long getId() {
        return id;
    }


    public Booking makeBooking(LocalDateTime start) {
        Booking booking = new Booking(this, start);
        this.bookings.add(booking);
        return(booking);
    }

    public List<Booking> getBookings() {
        return bookings;
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

    public boolean isBooked(LocalDateTime time) {
        boolean booked = false;
        for (Booking booking : bookings) {
            if (time.isAfter(booking.getTimeStart()) && time.isBefore(booking.getTimeEnd())) {
                booked = true;
            } else if (time.plusHours(1).isAfter(booking.getTimeStart()) && time.plusHours(1).isBefore(booking.getTimeEnd())) {
                booked = true;
            } else if (booking.getTimeStart().equals(time)) {
                booked = true;
            }
        }
        return booked;
    }

    public boolean isAccessible() {
        return isAccessible;
    }

    public void setAccessible(boolean accessible) {
        isAccessible = accessible;
    }
}