package CG.RoomService.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
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
        @Column(name = "token", unique = true)
        private String token;
        @Column(name = "timeStart", nullable = false)
        private LocalDateTime timeStart;
        @Column(name = "timeEnd")
        private LocalDateTime timeEnd;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
        @JoinColumn(name = "user_email", referencedColumnName = "email", nullable = false)
        @JsonBackReference(value = "user")
        private User user;

        public Booking() {
        }

        public Booking(Room room, LocalDateTime timeStart, LocalDateTime timeEnd, User user) {
                this.token = UUID.randomUUID().toString();
                this.room = room;
                this.timeStart = timeStart;
                this.timeEnd = timeEnd;
                this.user = user;
        }

        public Booking(Room room, LocalDateTime timeStart, User user) {
                this(room, timeStart, timeStart.plusHours(1), user);
        }

        public long getId() {
                return id;
        }

        public Room getRoom() {
                return room;
        }

        public void setTimeStart(LocalDateTime newTime) {
                this.timeStart = newTime;
                this.timeEnd = newTime.plusHours(1);
        }

        public LocalDateTime getTimeStart() {
                return timeStart;
        }

        public void setTimeEnd(LocalDateTime timeEnd) {
                this.timeEnd = timeEnd;
        }

        public LocalDateTime getTimeEnd() {
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
}
