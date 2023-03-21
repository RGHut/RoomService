package CG.RoomService.Controllers;

import CG.RoomService.Models.Booking;

import CG.RoomService.Models.Response;
import CG.RoomService.Service.BookingService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.OffsetDateTime;
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
    public ResponseEntity<Response> getBookings() {
        return bookingService.getBookings();
//        return bookings;
    }
    /**
     * Creates a new booking with the given building name, room name and time
     * @param booking - booking object to be saved in the database
     * @return - HTTP response with a success or error message
     */
    @PostMapping("/makeBooking")
    public ResponseEntity<Response> makeBooking(@RequestBody Booking booking) {
        return bookingService.makeBooking(booking);
    }

    /**
     * Cancels a booking with the given token
     *
     * @param token - The unique token for the booking
     * @param email - the email of the user attempting to delete the booking
     * @return - HTTP response with a success or error message
     */
    @PostMapping("/cancelBooking")
    public ResponseEntity<Response> cancelBooking(@RequestParam String token, @RequestParam String email) {
        return bookingService.cancelBooking(email, token);
    }

    /**
     * Changes the time of a booking.
     *
     * @param token The token of the booking to change
     * @param time  The new time for the booking
     * @return A response indicating whether the booking was changed successfully or not
     */
    @PostMapping("/changeBooking")
    public ResponseEntity<Response> changeBooking(@RequestParam String token, @RequestParam OffsetDateTime time) {
        return bookingService.changeBooking(token, time);
    }

    /**
     * Retrieves a booking by its token.
     *
     * @param token The token of the booking to retrieve
     * @return The booking if it exists, or a response indicating that the booking does not exist
     */
    @PostMapping("/getBooking")
    public ResponseEntity<?> getBooking(@RequestParam String token) {
        return bookingService.getBooking(token);
    }

    /**
     * retrieves a list of bookings belonging to a specific user
     * @param userEmail email of the User to retrieve the bookings for
     * @return ResponseBody containing either the retrieved bookings or an error message if the user does not exist
     */
    @PostMapping("/getBookingByUser")
    public ResponseEntity<?> getBookingByUser(@RequestParam String userEmail) {
        if (bookingService.isUserExistByEmail(userEmail)) {
            return ResponseEntity.status(200).body(bookingService.getBookingsByUser(userEmail));
        }
        return ResponseEntity.status(400).body("{\"error\":\"User with Email '" + userEmail + "' does not exist\"}");
    }

    /**
     * retrieves a list of bookings belonging to a specific room
     * @param roomName name of the room to retrieve the bookings for
     * @return ResponseBody containing a list of bookings or an error message if the room does not exist
     */
    @PostMapping("/getBookingByRoom")
    public ResponseEntity<?> getBookingsByRoom(@RequestParam String roomName) {
        if (bookingService.isRoomExistByName(roomName)) {
            return ResponseEntity.status(200).body(bookingService.getBookingsByRoom(roomName));
        }
        return ResponseEntity.status(400).body("{\"error\":\"Room does not exist\"}");
    }

    /**
     * deletes all bookings from the database that have an end time that has already passed
     * @return a ResponseBody containing a message if the process was successful or not.
     */
    @DeleteMapping("/cleanBookings")
    public ResponseEntity<?> cleanBookings() {
        if (bookingService.bookingCleanup()) {
            return ResponseEntity.status(200).body("{\"Booking cleanup successful\"}");
        }

        return ResponseEntity.status(400).body("{\"error\":\"something went wrong during cleanup\"}");
    }

}
