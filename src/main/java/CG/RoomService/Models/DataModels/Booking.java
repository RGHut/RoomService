package CG.RoomService.Models.DataModels;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
    @JoinColumn(name = "room_name", referencedColumnName = "name", nullable = false)
    @JsonBackReference(value = "room")
    private Room room;
    @Column(name = "booked_room")
    private String roomname;
    @Column(name = "token", unique = true)
    private String token;
    @Column(name = "timeStart", nullable = false)
    private OffsetDateTime timeStart;
    @Column(name = "timeEnd")
    private OffsetDateTime timeEnd;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JoinColumn(name = "user_email", referencedColumnName = "email", nullable = false)
    @JsonBackReference(value = "user")
    private User user;
    @Column(name = "reserved_by")
    private String userEmail;

    public Booking() {
    }

    public Booking(Room room, OffsetDateTime timeStart, OffsetDateTime timeEnd, User user) {
        this.token = UUID.randomUUID().toString();
        this.room = room;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.user = user;
        this.userEmail = user.getEmail();
        this.roomname = room.getName();
    }

    public Booking(Room room, OffsetDateTime timeStart, User user) {
        this(room, timeStart, timeStart.plusHours(1), user);
    }

    public long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public void setTimeStart(OffsetDateTime newTime) {
        this.timeStart = newTime;
        this.timeEnd = newTime.plusHours(1);
    }

    public OffsetDateTime getTimeStart() {
        return timeStart;
    }

    public void setTimeEnd(OffsetDateTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public OffsetDateTime getTimeEnd() {
        return timeEnd;
    }

    public String getToken() {
        return token;
    }

    public void generateToken() {
        this.token = UUID.randomUUID().toString();
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }
}
