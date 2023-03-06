package CG.RoomService.Controllers;

import CG.RoomService.Auth.RegisterRequest;
import CG.RoomService.Models.Booking;
import CG.RoomService.Models.Building;
import CG.RoomService.Models.Room;
import CG.RoomService.Models.User;
import CG.RoomService.Service.BookingService;
import CG.RoomService.Service.BuildingService;
import CG.RoomService.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;


@RestController
@RequiredArgsConstructor
public class DataFactory {


    private final BuildingService buildingService;
    private final BookingService bookingService;
    private final UserService userService;

    @GetMapping("/testData")
    public ResponseEntity<?> generateData() {
        Building building = new Building("test1");
        Room room1 = new Room("test 1.1", 1, 4, true);
        Room room2 = new Room("test 1.2", 1, 4, true);


        if (!buildingService.addBuilding("test1")) {
            buildingService.addRoom(room1);
            buildingService.addRoom(room2);
            RegisterRequest request = new RegisterRequest("test1", "lastName", "test1@cg.nl", "12345", "cg");
            User user1 = userService.findByEmail("test1@cg.nl").get();
            RegisterRequest request2 = new RegisterRequest("test2", "lastName", "test2@cg.nl", "12345", "cg");
            User user2 = userService.findByEmail("test2@cg.nl").get();
            RegisterRequest request3 = new RegisterRequest("test3", "lastName", "test3@cg.nl", "12345", "cg");
            User user3 = userService.findByEmail("test3@cg.nl").get();
            Booking booking1 = new Booking(room1, OffsetDateTime.now().plusHours(1), user1);
            bookingService.makeBooking(booking1);
            Booking booking2 = new Booking(room1, OffsetDateTime.now().plusHours(2), user2);
            bookingService.makeBooking(booking2);
            Booking booking3 = new Booking(room2, OffsetDateTime.now().plusHours(1), user3);
            bookingService.makeBooking(booking3);
            Booking booking4 = new Booking(room2, OffsetDateTime.now().plusHours(2), user1);
            bookingService.makeBooking(booking4);
            Booking booking5 = new Booking(room2, OffsetDateTime.now().plusDays(1).plusHours(2), user2);
            bookingService.makeBooking(booking5);
            Booking booking6 = new Booking(room1, OffsetDateTime.now().plusDays(1).plusHours(4), user3);
            return ResponseEntity.status(200).body("{\"Generated test data\"}");
        }
        return ResponseEntity.status(400).body("{\"Test data already exists\"}");





    }
}
