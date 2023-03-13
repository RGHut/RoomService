package CG.RoomService.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room", uniqueConstraints = {@UniqueConstraint(columnNames = {"building_name", "name"})})
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
    @JsonManagedReference(value = "room")
    private List<Booking> bookings = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", referencedColumnName="id", nullable = false)
    @JoinColumn(name = "building_name", referencedColumnName = "name", nullable = false)
    @JsonBackReference(value = "building")
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


    public void makeBooking(Booking booking) {
        this.bookings.add(booking);
        booking.setRoom(this);
    }

    public void cancelBooking(Booking booking) {
        bookings.remove(booking);
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

    public boolean isBooked(OffsetDateTime time) {
        boolean booked = false;
        // create a ZonedDateTime object for the date and time in the UTC time zone
        ZonedDateTime utcDateTime = ZonedDateTime.parse(time.toString(), DateTimeFormatter.ISO_ZONED_DATE_TIME);

// convert the date and time to the Central European Time zone
        ZoneId cetZoneId = ZoneId.of("Europe/Paris"); // you can replace "Europe/Paris" with the appropriate time zone ID
        ZonedDateTime cetDateTime = utcDateTime.withZoneSameInstant(cetZoneId);

// format the date and time in the CET time zone
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        String formattedDateTime = cetDateTime.format(formatter);
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(formattedDateTime);
        System.out.println(offsetDateTime);


        for (Booking booking : bookings) {
            System.out.println(booking.getTimeStart());
            if (offsetDateTime.isAfter(booking.getTimeStart()) && offsetDateTime.isBefore(booking.getTimeEnd())) {
                booked = true;
            } else if (offsetDateTime.plusHours(1).isAfter(booking.getTimeStart()) && offsetDateTime.plusHours(1).isBefore(booking.getTimeEnd())) {
                booked = true;
            } else if (booking.getTimeStart().equals(offsetDateTime)) {
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