//package CG.RoomService.Controllers;
//
//import CG.RoomService.Models.Booking;
//import CG.RoomService.Models.Building;
//import CG.RoomService.Models.Room;
//
//import CG.RoomService.Models.User;
//import CG.RoomService.Repositories.BookingRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.UUID;
//
///**
// * This class is a RESTful controller for handling bookings of rooms in a building.
// * It defines several endpoints, such as "/bookings" for getting all bookings,
// * "/makeBooking" for creating a new booking, "/cancelBooking" for canceling a booking,
// * "/changeBooking" for modifying a booking, and "/getBooking" for getting details of a specific booking.
// * The controller uses the Spring framework, specifically the `@RestController` annotation,
// * `@GetMapping`, `@PostMapping`, and `@RequestParam` annotations to handle HTTP requests and responses.
// * The code also appears to use a local ArrayList to store bookings, and uses the `BuildingController` class to retrieve buildings.
// */
//@RestController
//public class BookingController {
//    @Autowired
//    private BookingRepository bookingRepository;
//
//    private final ThreadLocal<ArrayList<Booking>> bookings = ThreadLocal.withInitial(() -> (ArrayList<Booking>) bookingRepository.findAll());
//
//    /**
//     * Endpoint for getting all bookings
//     * @return ArrayList<Booking> list of all bookings
//     */
//    @GetMapping("/bookings")
//    public ArrayList<Booking> getBookings() {
//        return (ArrayList<Booking>) bookingRepository.findAll();
////        return bookings;
//    }
//
//    /**
//     * Creates a new booking with the given building name, room name and time
//     * @param buildingName - The name of the building for the booking
//     * @param roomName - The name of the room for the booking
//     * @param time - The start time of the booking
//     * @return - HTTP response with a success or error message
//     */
////    @PostMapping("/makeBooking")
////    public ResponseEntity<?> makeBooking(@RequestParam String buildingName, @RequestParam String roomName, @RequestParam LocalDateTime time, @RequestParam User user) {
////
////        for (Building building: BuildingController.getBuildings()) {
////            if (building.getName().equals(buildingName)) {
////                for (Room room : building.getRooms()) {
////                    if (room.getName().equals(roomName)) {
////                        if (room.isBooked(time)) {
////                            return ResponseEntity.status(400).body("{\"error\":\"Room is already booked for that timeslot!\"}");
////                        } else {
////                            Booking booking = user.makeBooking(room, time);
////                            bookings.get().add(booking);
////                            bookingRepository.save(booking);
////                            return ResponseEntity.status(200).body("{\"Booking created\" " + booking.getToken() + "}");
////                        }
////                    }
////                }
////                return ResponseEntity.status(400).body("{\"error\":\"Room does not exists!\"}");
////            }
////        }
////        return ResponseEntity.status(400).body("{\"error\":\"Building does not exists!\"}");
//    }
//
//    /**
//     * Cancels a booking with the given token
//     * @param token - The unique token for the booking
//     * @return - HTTP response with a success or error message
//     */
//    @PostMapping("/cancelBooking")
//    public ResponseEntity<?> cancelBooking(@RequestParam String token) {
//        if (bookingRepository.existsBookingByToken(UUID.fromString(token))) {
//            Booking booking = bookingRepository.findByToken(UUID.fromString(token));
//            bookingRepository.deleteById(booking.getId());
//            booking.getUser().cancelBooking(booking);
//            return ResponseEntity.status(200).body("{\"booking removed\"}");
//        }
//        for (Booking booking: bookings.get()) {
//            if (booking.getToken().toString().equals(token)) {
//                bookings.get().remove(booking);
//                booking.getUser().cancelBooking(booking);
//                return ResponseEntity.status(200).body("{\"booking removed\"}");
//            }
//        }
//        return ResponseEntity.status(400).body("{\"error\": \"booking does not exist!\"}");
//    }
//
//    /**
//     * Changes the time of a booking.
//     * @param token The token of the booking to change
//     * @param time The new time for the booking
//     * @return A response indicating whether the booking was changed successfully or not
//     */
//    @PostMapping("/changeBooking")
//    public ResponseEntity<?> changeBooking(@RequestParam String token, @RequestParam LocalDateTime time) {
//        if (bookingRepository.existsBookingByToken(UUID.fromString(token))) {
//            Booking booking = bookingRepository.findByToken(UUID.fromString(token));
//            if (booking.getRoom().isBooked(time)) {
//                return ResponseEntity.status(400).body("{\"error\":\"Room is already booked for that timeslot!\"}");
//            } else {
//                booking.setTimeStart(time);
//                bookingRepository.save(booking);
//                return ResponseEntity.status(200).body("{\"booking changed to " + time + "\"}");
//            }
//        }
//        for (Booking booking: bookings.get()) {
//            if (booking.getToken().toString().equals(token)) {
//                if (booking.getRoom().isBooked(time)) {
//                    return ResponseEntity.status(400).body("{\"error\":\"Room is already booked for that timeslot!\"}");
//                } else {
//                    booking.setTimeStart(time);
//                    bookingRepository.save(booking);
//                    return ResponseEntity.status(200).body("{\"booking changed to " + time + "\"}");
//                }
//            }
//        }
//        return ResponseEntity.status(400).body("{\"error\":\"booking does not exist!\"}");
//    }
//
//    /**
//     * Retrieves a booking by its token.
//     * @param token The token of the booking to retrieve
//     * @return The booking if it exists, or a response indicating that the booking does not exist
//     */
//    @PostMapping("/getBooking")
//    public ResponseEntity<?> getBooking(@RequestParam String token){
//        if (bookingRepository.existsBookingByToken(UUID.fromString(token))) {
//            Booking booking = bookingRepository.findByToken(UUID.fromString(token));
//            return ResponseEntity.status(200).body(booking);
//        }
//        for (Booking booking: bookings.get()) {
//            if (booking.getToken().toString().equals(token)) {
//                return ResponseEntity.status(200).body(booking);
//            }
//        }
//        return ResponseEntity.status(400).body("{\"error\":\"booking does not exist\"}");
//    }
//
//}
