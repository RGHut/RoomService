package CG.RoomService.Controllers;

import CG.RoomService.Models.DataModels.Booking;
import CG.RoomService.Models.DataModels.Building;
import CG.RoomService.Models.DataModels.Room;
import CG.RoomService.Models.DataModels.User;
import CG.RoomService.Models.Requests.AuthenticationRequest;
import CG.RoomService.Models.Requests.RegisterRequest;
import CG.RoomService.Models.Responses.ExceptionResponse;
import CG.RoomService.Models.Responses.Response;
import CG.RoomService.Service.AuthenticationService;
import CG.RoomService.Service.BookingService;
import CG.RoomService.Service.BuildingService;
import CG.RoomService.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * RestController annotation is used to mark this class as a controller where every method returns a domain object instead of a view.
 * RequestMapping annotation is used to map web requests onto specific handler classes and/or handler methods.
 * RequiredArgsConstructor annotation is used to create constructor with required fields
 */


@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class AuthenticationController {

    /**
     * The service instance being used to perform authentication and registration operations
     */
    private final AuthenticationService service;
    private final BuildingService buildingService;
    private final BookingService bookingService;
    private final UserService userService;

    /**
     * API endpoint for generating data suitable for testing, if they do not exist already
     * creates 1 building, 2 rooms, 3 users.
     * @return ResponseBody containing a message
     */
    @GetMapping("/testData")
    public ResponseEntity<?> generateData() {
        Building building = new Building("Laan Corpus den Hoorn 106");
        Room room1 = new Room("A1.2 small meeting room", 1, 4, true);
        room1.setBuilding(building);
        Room room2 = new Room("A1.5 large meeting room", 1, 8, true);
        room2.setBuilding(building);

        if (!buildingService.isBuildingExist("Laan Corpus den Hoorn 106")) {
            buildingService.addBuilding("Laan Corpus den Hoorn 106");
            buildingService.addRoom(room1);
            buildingService.addRoom(room2);
            RegisterRequest request = new RegisterRequest("test1", "lastName", "test1@cg.nl", "12345", "cg");
            service.register(request);
            RegisterRequest request2 = new RegisterRequest("test2", "lastName", "test2@cg.nl", "12345", "cg");
            service.register(request2);
            RegisterRequest request3 = new RegisterRequest("test3", "lastName", "test3@cg.nl", "12345", "cg");
            service.register(request3);
            return ResponseEntity.status(200).body("{\"Generated test data\"}");
        }
        return ResponseEntity.status(400).body("{\"Test data already exists\"}");
    }

    /**
     * API endpoint for generating bookings using previously created data
     * only works if /testData has been called previously.
     * Makes 6 bookings for later today and tomorrow
     * @return ResponseBody containing a message
     */
    @GetMapping("/testBooking")
    public ResponseEntity<?> GenerateBooking() {
        if (buildingService.isBuildingExist("Laan Corpus den Hoorn 106")) {
            Optional<User> optionalUser1 =userService.findByEmail("test1@cg.nl");
            Optional<User> optionalUser2 =userService.findByEmail("test2@cg.nl");
            Optional<User> optionalUser3 =userService.findByEmail("test3@cg.nl");
            if (optionalUser1.isPresent() && optionalUser2.isPresent() && optionalUser3.isPresent()) {
                User user1 = optionalUser1.get();
                User user2 = optionalUser2.get();
                User user3 = optionalUser3.get();
                Room room1 = buildingService.getRoom("A1.2 small meeting room");
                Room room2 = buildingService.getRoom("A1.5 large meeting room");
                Booking booking1 = new Booking(room1, OffsetDateTime.now().plusHours(1).truncatedTo(ChronoUnit.HOURS), user1);
                bookingService.makeBooking(booking1);
                Booking booking2 = new Booking(room1, OffsetDateTime.now().plusHours(3).truncatedTo(ChronoUnit.HOURS), user2);
                bookingService.makeBooking(booking2);
                Booking booking3 = new Booking(room2, OffsetDateTime.now().plusHours(1).truncatedTo(ChronoUnit.HOURS), user3);
                bookingService.makeBooking(booking3);
                Booking booking4 = new Booking(room2, OffsetDateTime.now().plusHours(3).truncatedTo(ChronoUnit.HOURS), user1);
                bookingService.makeBooking(booking4);
                Booking booking5 = new Booking(room2, OffsetDateTime.now().plusDays(1).plusHours(2).truncatedTo(ChronoUnit.HOURS), user2);
                bookingService.makeBooking(booking5);
                Booking booking6 = new Booking(room1, OffsetDateTime.now().plusDays(1).plusHours(4).truncatedTo(ChronoUnit.HOURS), user3);
                bookingService.makeBooking(booking6);
                return ResponseEntity.status(200).body("{\"test bookings generated\"}");
            }
            else {
                bookingService.bookingCleanup();
                buildingService.deleteBuilding("test1");
                return ResponseEntity.status(400).body("{\"error\":\"something went wrong, test data reset, run /testData again\"}");
            }
        }
        return ResponseEntity.status(400).body("{\"error\":\"Test date doesn't exist run /testData first\"}");
    }


    /**
     * The PostMapping annotation is used to handle HTTP POST requests.
     * This method is used for registration, the request body is mapped to a RegisterRequest object
     * @return a ResponseEntity with an AuthenticationResponse object
     */
    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody RegisterRequest registerRequest) {
        try {
            return ResponseEntity.ok(service.register(registerRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse("Missing necessary information"));
        }
    }

    /**
     * The PostMapping annotation is used to handle HTTP POST requests.
     * This method is used for authentication, the request body is mapped to an AuthenticationRequest object
     * @return a ResponseEntity with an AuthenticationResponse object
     */
    @PostMapping("/authenticate")
    public ResponseEntity<Response> authenticate(@RequestBody AuthenticationRequest request ){
             try {
            return ResponseEntity.ok(service.authenticate(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse("Invalid email and password combination"));

    }
    }
    @GetMapping("/user")
    public ResponseEntity<String> sayHello() {
        System.out.println("Received GET request at /user endpoint");
        return ResponseEntity.ok("Hello i'm a secured Werk");
    }

}
