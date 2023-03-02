package CG.RoomService.Models;

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
        private Room room;
        @Column(name = "token", unique = true)
        private String token;
        @Column(name = "timeStart", nullable = false)
        private OffsetDateTime timeStart;
        @Column(name = "timeEnd")
        private OffsetDateTime timeEnd;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
        @JoinColumn(name = "user_email", referencedColumnName = "email", nullable = false)
        private User user;

        public Booking() {
        }

        public Booking(Room room, OffsetDateTime timeStart, OffsetDateTime timeEnd, User user) {
                this.token = UUID.randomUUID().toString();
                this.room = room;
                this.timeStart = timeStart;
                this.timeEnd = timeEnd;
                this.user = user;
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
}
