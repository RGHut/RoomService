package CG.RoomService.Models;

import java.time.LocalDateTime;

public class Bookings {
        private final Room room;
        private final LocalDateTime timeStart;
        private final LocalDateTime timeEnd;

        public Bookings(Room room, LocalDateTime timeStart, LocalDateTime timeEnd) {
                this.room = room;
                this.timeStart = timeStart;
                this.timeEnd = timeEnd;
        }

        public Bookings(Room room, LocalDateTime timeStart) {
                this(room, timeStart, timeStart.plusHours(1));
        }

        public Room getRoom() {
                return room;
        }

        public LocalDateTime getTimeStart() {
                return timeStart;
        }

        public LocalDateTime getTimeEnd() {
                return timeEnd;
        }
}
