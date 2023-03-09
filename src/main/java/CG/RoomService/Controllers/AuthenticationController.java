package CG.RoomService.Controllers;

import CG.RoomService.Auth.AuthenticationRequest;
import CG.RoomService.Auth.AuthenticationResponse;
import CG.RoomService.Auth.AuthenticationService;
import CG.RoomService.Auth.RegisterRequest;
import CG.RoomService.Models.Booking;
import CG.RoomService.Models.Building;
import CG.RoomService.Models.Room;
import CG.RoomService.Models.User;
import CG.RoomService.Repositories.RoomRepository;
import CG.RoomService.Service.BookingService;
import CG.RoomService.Service.BuildingService;
import CG.RoomService.Service.UserService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Locale;

/**
 * RestController annotation is used to mark this class as a controller where every method returns a domain object instead of a view.
 * RequestMapping annotation is used to map web requests onto specific handler classes and/or handler methods.
 * RequiredArgsConstructor annotation is used to create constructor with required fields
 */


@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class AuthenticationController {
    HttpServletResponse response = new HttpServletResponse() {
        @Override
        public String getCharacterEncoding() {
            return null;
        }

        @Override
        public String getContentType() {
            return null;
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return null;
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            return null;
        }

        @Override
        public void setCharacterEncoding(String charset) {

        }

        @Override
        public void setContentLength(int len) {

        }

        @Override
        public void setContentLengthLong(long length) {

        }

        @Override
        public void setContentType(String type) {

        }

        @Override
        public void setBufferSize(int size) {

        }

        @Override
        public int getBufferSize() {
            return 0;
        }

        @Override
        public void flushBuffer() throws IOException {

        }

        @Override
        public void resetBuffer() {

        }

        @Override
        public boolean isCommitted() {
            return false;
        }

        @Override
        public void reset() {

        }

        @Override
        public void setLocale(Locale loc) {

        }

        @Override
        public Locale getLocale() {
            return null;
        }

        @Override
        public void addCookie(Cookie cookie) {

        }

        @Override
        public boolean containsHeader(String name) {
            return false;
        }

        @Override
        public String encodeURL(String url) {
            return null;
        }

        @Override
        public String encodeRedirectURL(String url) {
            return null;
        }

        @Override
        public void sendError(int sc, String msg) throws IOException {

        }

        @Override
        public void sendError(int sc) throws IOException {

        }

        @Override
        public void sendRedirect(String location) throws IOException {

        }

        @Override
        public void setDateHeader(String name, long date) {

        }

        @Override
        public void addDateHeader(String name, long date) {

        }

        @Override
        public void setHeader(String name, String value) {

        }

        @Override
        public void addHeader(String name, String value) {

        }

        @Override
        public void setIntHeader(String name, int value) {

        }

        @Override
        public void addIntHeader(String name, int value) {

        }

        @Override
        public void setStatus(int sc) {

        }

        @Override
        public int getStatus() {
            return 0;
        }

        @Override
        public String getHeader(String name) {
            return null;
        }

        @Override
        public Collection<String> getHeaders(String name) {
            return null;
        }

        @Override
        public Collection<String> getHeaderNames() {
            return null;
        }
    };


    private final BuildingService buildingService;
    private final BookingService bookingService;
    private final UserService userService;

    private final AuthenticationService service;

    @GetMapping("/testData")
    public ResponseEntity<?> generateData() {
        Building building = new Building("test1");
        Room room1 = new Room("test 1.1", 1, 4, true);
        room1.setBuilding(building);
        Room room2 = new Room("test 1.2", 1, 4, true);
        room2.setBuilding(building);

        if (!buildingService.addBuilding("test1")) {
            buildingService.addRoom(room1);
            buildingService.addRoom(room2);
            RegisterRequest request = new RegisterRequest("test1", "lastName", "test1@cg.nl", "12345", "cg");
            service.register(request, response);
            RegisterRequest request2 = new RegisterRequest("test2", "lastName", "test2@cg.nl", "12345", "cg");
            service.register(request2, response);
            RegisterRequest request3 = new RegisterRequest("test3", "lastName", "test3@cg.nl", "12345", "cg");
            service.register(request3, response);
            return ResponseEntity.status(200).body("{\"Generated test data\"}");
        }
        return ResponseEntity.status(400).body("{\"Test data already exists\"}");
    }

    @GetMapping("/testBooking")
    public ResponseEntity<?> GenerateBooking() {
        if (buildingService.isBuildingExist("test1")) {
            User user1 = userService.findByEmail("test1@cg.nl").get();
            User user2 = userService.findByEmail("test2@cg.nl").get();
            User user3 = userService.findByEmail("test3@cg.nl").get();
            Room room1 = buildingService.getRoom("test 1.1");
            Room room2 = buildingService.getRoom("test 1.2");
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
        return ResponseEntity.status(400).body("{\"error\":\"Test date doesn't exist run /testData first\"}");
    }


    /**
     * The service instance being used to perform authentication and registration operations
     */



    /**
     * The PostMapping annotation is used to handle HTTP POST requests.
     * This method is used for registration, the request body is mapped to a RegisterRequest object
     *
     * @param firstName the request containing the user's registration information
     * @return a ResponseEntity with an AuthenticationResponse object
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request, HttpServletResponse response) {
        AuthenticationResponse authResponse = service.register(request, response);
        return ResponseEntity.ok(authResponse);
    }

    /**
     * The PostMapping annotation is used to handle HTTP POST requests.
     * This method is used for authentication, the request body is mapped to an AuthenticationRequest object
     *
     * @param email the request containing the user's authentication information
     * @param password password of the user
     * @return a ResponseEntity with an AuthenticationResponse object
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        AuthenticationResponse authResponse = service.authenticate(request, response);
        return ResponseEntity.ok(authResponse);
    }
    @GetMapping("/user")
    public ResponseEntity<String> sayHello() {
        System.out.println("Received GET request at /user endpoint");
        return ResponseEntity.ok("Hello i'm a secured Werk");
    }

}
