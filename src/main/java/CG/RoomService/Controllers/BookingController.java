package CG.RoomService.Controllers;

import CG.RoomService.Models.Booking;

import CG.RoomService.Service.BookingService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;

import java.util.List;


/**
 * This class is a RESTful controller for handling bookings of rooms in a building.
 * It defines several endpoints, such as "/bookings" for getting all bookings,
 * "/makeBooking" for creating a new booking, "/cancelBooking" for canceling a booking,
 * "/changeBooking" for modifying a booking, and "/getBooking" for getting details of a specific booking.
 * The controller uses the Spring framework, specifically the `@RestController` annotation,
 * `@GetMapping`, `@PostMapping`, and `@RequestParam` annotations to handle HTTP requests and responses.
 * The code also appears to use a local ArrayList to store bookings, and uses the `BuildingController` class to retrieve buildings.
 */
@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    /**
     * Endpoint for getting all bookings
     *
     * @return List<Booking> list of all bookings
     */
    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookings() {
        return ResponseEntity.status(200).body(bookingService.getBookings());
//        return bookings;
    }
    /**
     * Creates a new booking with the given building name, room name and time
     * @param booking - booking object to be saved in the database
     * @return - HTTP response with a success or error message
     */
    @PostMapping("/makeBooking")
    public ResponseEntity<?> makeBooking(@RequestBody Booking booking) {
        if (bookingService.makeBooking(booking)){
            return ResponseEntity.status(200).body("{\"Booking created\" " + booking.getToken() + "}");
        }
        return ResponseEntity.status(400).body("{\"error\":\"Booking failed\"}");
    }

    /**
     * Cancels a booking with the given token
     *
     * @param token - The unique token for the booking
     * @return - HTTP response with a success or error message
     */
    @PostMapping("/cancelBooking")
    public ResponseEntity<?> cancelBooking(@RequestParam String token) {
        if (bookingService.cancelBooking(token)) {
            return ResponseEntity.status(200).body("{\"booking removed\"}");
        }
        return ResponseEntity.status(400).body("{\"error\": \"booking does not exist!\"}");
    }

    /**
     * Changes the time of a booking.
     *
     * @param token The token of the booking to change
     * @param time  The new time for the booking
     * @return A response indicating whether the booking was changed successfully or not
     */
    @PostMapping("/changeBooking")
    public ResponseEntity<?> changeBooking(@RequestParam String token, @RequestParam LocalDateTime time) {
        if (bookingService.isBookingExist(token)) {
            if (bookingService.changeBooking(token, time)) {
                return ResponseEntity.status(200).body("{\"booking changed to " + time + "\"}");

            }
            return ResponseEntity.status(400).body("{\"error\":\"Room is already booked for that timeslot!\"}");
        }
        return ResponseEntity.status(400).body("{\"error\":\"booking does not exist!\"}");
    }

    /**
     * Retrieves a booking by its token.
     *
     * @param token The token of the booking to retrieve
     * @return The booking if it exists, or a response indicating that the booking does not exist
     */
    @PostMapping("/getBooking")
    public ResponseEntity<?> getBooking(@RequestParam String token) {
        if (bookingService.isBookingExist(token)) {
            return ResponseEntity.status(200).body(bookingService.getBooking(token));
        }
        return ResponseEntity.status(400).body("{\"error\":\"booking does not exist\"}");
    }

    @DeleteMapping("/cleanBookings")
    public ResponseEntity<?> cleanBookings() {
        if (bookingService.bookingCleanup()) {
            return ResponseEntity.status(200).body("{\"Booking cleanup successful\"}");
        }

        return ResponseEntity.status(400).body("{\"error\":\"something went wrong during cleanup\"}");
    }

}
