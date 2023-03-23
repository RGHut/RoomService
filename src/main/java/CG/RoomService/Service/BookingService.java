package CG.RoomService.Service;

import CG.RoomService.Models.DataModels.Booking;
import CG.RoomService.Models.DataModels.Room;
import CG.RoomService.Models.DataModels.User;
import CG.RoomService.Models.Responses.BookingListResponse;
import CG.RoomService.Models.Responses.ExceptionResponse;
import CG.RoomService.Models.Responses.MessageResponse;
import CG.RoomService.Models.Responses.Response;
import CG.RoomService.Repositories.BookingRepository;
import CG.RoomService.Repositories.RoomRepository;
import CG.RoomService.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.OffsetDateTime;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final RoomRepository roomRepository;

    private final UserRepository userRepository;


    public ResponseEntity<Response> getBookings() {
        return ResponseEntity.status(200).body(new BookingListResponse(bookingRepository.findAll()));
    }

    public ResponseEntity<?> getBooking(String token) {
        if (isBookingExist(token)){
            return ResponseEntity.status(200).body(bookingRepository.findByToken(token));
        }
        return ResponseEntity.status(400).body("Booking does not exist!");
    }

    @Transactional
    public ResponseEntity<Response> makeBooking(Booking booking) {
        Room room = roomRepository.findByName(booking.getRoom().getName());
        Optional<User> optionalUser = userRepository.findByEmail(booking.getUser().getEmail());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(400).body(new ExceptionResponse("Booking failed, User does not exist"));
        }
        if (room.isBooked(booking.getTimeStart(), booking.getTimeEnd())) {
            return ResponseEntity.status(400).body(new ExceptionResponse("Booking failed, Room is already booked for that timeslot"));
        }
        User user = optionalUser.get();
        user.makeBooking(booking);
        room.makeBooking(booking);
        bookingRepository.save(booking);
        userRepository.save(user);
        roomRepository.save(room);
        return ResponseEntity.status(200).body(new MessageResponse(booking.getToken()));
    }

    public ResponseEntity<Response> cancelBooking(String email, String token) {
        if (bookingRepository.existsBookingByToken(token)) {
            Booking booking = bookingRepository.findByToken(token);
            if (booking.getUserEmail().equals(email)) {
                bookingRepository.deleteById(booking.getId());
                Room room = booking.getRoom();
                room.cancelBooking(booking);
                User user = booking.getUser();
                user.cancelBooking(booking);
                roomRepository.save(room);
                userRepository.save(user);
                return ResponseEntity.status(200).body(new MessageResponse("booking removed"));
            }
            return ResponseEntity.status(400).body(new ExceptionResponse("Booking can only be deleted by the user who created it"));
        }
        return ResponseEntity.status(400).body(new ExceptionResponse("booking does not exist!"));
    }

    public ResponseEntity<Response> changeBooking(String token, OffsetDateTime timeStart, OffsetDateTime timeEnd) {
        if (isBookingExist(token)) {
            Booking booking = bookingRepository.findByToken(token);
            if (booking.getRoom().isBooked(timeStart, timeEnd)){
                return ResponseEntity.status(400).body(new ExceptionResponse("Room is already booked for that timeslot!"));
            }
            booking.setTimeStart(timeStart);
            booking.setTimeEnd(timeEnd);
            bookingRepository.save(booking);
            return ResponseEntity.status(200).body(new MessageResponse("booking changed to " + timeStart + "-" + timeEnd));
        }
        return ResponseEntity.status(400).body(new ExceptionResponse("booking does not exist!"));
    }

    public boolean isBookingExist(String token) {
        return bookingRepository.existsBookingByToken(token);
    }

    public boolean isUserExistByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    public boolean isRoomExistByName(String name) {
        return roomRepository.existsRoomByName(name);
    }

    public ResponseEntity<Response> getBookingsByUser(String email) {
        if (isUserExistByEmail(email)) {
            Optional<User> optionalUser = userRepository.findByEmail(email);
            User user = optionalUser.get();
            return ResponseEntity.status(200).body(new BookingListResponse(bookingRepository.findByUser(user)));
        }
        return ResponseEntity.status(400).body(new ExceptionResponse("User with Email '" + email + "' does not exist"));
    }

    public ResponseEntity<Response> getBookingsByRoom(String roomName) {
        if (isRoomExistByName(roomName)) {
            Room room = roomRepository.findByName(roomName);
            return ResponseEntity.status(200).body(new BookingListResponse(bookingRepository.findByRoom(room)));
        }
        return ResponseEntity.status(400).body(new ExceptionResponse("Room does not exist"));

    }

    public ResponseEntity<Response> bookingCleanup() {
        OffsetDateTime current = OffsetDateTime.now();
        List<Booking> bookingList = bookingRepository.findAll();
        int i = 0;
        for (Booking booking: bookingList) {
            if (booking.getTimeEnd().isBefore(current)) {
                cancelBooking(booking.getUser().getEmail(), booking.getToken());
                i++;
            }
        }
        return ResponseEntity.status(200).body(new MessageResponse("Booking cleanup successful, deleted: " + i + " Bookings"));
    }


}
