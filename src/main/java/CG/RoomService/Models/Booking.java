package CG.RoomService.Models;

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
        @Transient
        @Column(name = "room")
        private  Room room;
        private  UUID token;
        @Column(name = "timeStart")
        private LocalDateTime timeStart;
        @Column(name = "timeEnd")
        private LocalDateTime timeEnd;

        public Booking() {
        }

        public Booking(Room room, LocalDateTime timeStart, LocalDateTime timeEnd) {
                this.token = UUID.randomUUID();
                this.room = room;
                this.timeStart = timeStart;
                this.timeEnd = timeEnd;
        }

        public Booking(Room room, LocalDateTime timeStart) {
                this(room, timeStart, timeStart.plusHours(1));
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

        public UUID getToken() {
                return token;
        }
}
