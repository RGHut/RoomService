package CG.RoomService.Controllers;

import CG.RoomService.Models.Booking;
import CG.RoomService.Models.Building;
import CG.RoomService.Models.Room;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
public class BookingController {
    private final ArrayList<Booking> bookings = new ArrayList<Booking>();

    @GetMapping("/bookings")
    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    @PostMapping("/makeBooking")
    public ResponseEntity<?> makeBooking(@RequestParam String buildingName, @RequestParam String roomName, @RequestParam LocalDateTime time) {
        for (Building building: BuildingController.getBuildings()) {
            if (building.getName().equals(buildingName)) {
                for (Room room : building.getRooms()) {
                    if (room.getName().equals(roomName)) {
                        if (isBooked(room, time)) {
                            return ResponseEntity.status(400).body("{\"error\":\"Room is already booked!\"" + "}");
                        } else {
                            bookings.add(new Booking(room, time));
                            return ResponseEntity.status(200).body("{\"Booking created\"}");
                        }
                    }
                }
                return ResponseEntity.status(400).body("{\"error\":" + "\"Room does not exists!\"" + "}");
            }
        }
        return ResponseEntity.status(400).body("{\"error\":" + "\"Building does not exists!\"" + "}");
    }

    @PostMapping("/cancelBooking")
    public ResponseEntity<?> cancelBooking(@RequestParam String token) {
        for (Booking booking: bookings) {
            if (booking.getToken().toString().equals(token)) {
                bookings.remove(booking);
                return ResponseEntity.status(200).body("{\"booking removed\"}");
            }
        }
        return ResponseEntity.status(400).body("{\"error\":" + "\"booking does not exist!\"}");
    }

    @PostMapping("/changeBooking")
    public ResponseEntity<?> changeBooking(@RequestParam String token, @RequestParam LocalDateTime time) {
        for (Booking booking: bookings) {
            if (booking.getToken().toString().equals(token)) {
                booking.setTimeStart(time);
                return ResponseEntity.status(200).body("{\"booking changed to " + time + "\"}");
            }
        }
        return ResponseEntity.status(400).body("{\"error\":\"booking does not exist!\"}");
    }

    private boolean isBooked(Room room, LocalDateTime time) {
        boolean booked = false;
        for (Booking booking : bookings) {
            if (booking.getRoom().equals(room)) {
                if (booking.getTimeStart().equals(time)) {
                    booked = true;
                }
            }
        }
        return booked;
    }
}
