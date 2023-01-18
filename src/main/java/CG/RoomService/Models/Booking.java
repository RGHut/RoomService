package CG.RoomService.Models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Booking {
        private final Room room;
        private final UUID token;
        private LocalDateTime timeStart;
        private LocalDateTime timeEnd;

        public Booking(Room room, LocalDateTime timeStart, LocalDateTime timeEnd) {
                this.token = UUID.randomUUID();
                this.room = room;
                this.timeStart = timeStart;
                this.timeEnd = timeEnd;
        }

        public Booking(Room room, LocalDateTime timeStart) {
                this(room, timeStart, timeStart.plusHours(1));
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
