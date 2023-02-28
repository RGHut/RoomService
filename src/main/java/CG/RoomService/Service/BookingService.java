package CG.RoomService.Service;

import CG.RoomService.Models.Booking;
import CG.RoomService.Models.Room;
import CG.RoomService.Repositories.BookingRepository;
import CG.RoomService.Repositories.RoomRepository;
import CG.RoomService.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final RoomRepository roomRepository;

//    private final UserRepository userRepository;


    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBooking(String token) {
        return bookingRepository.findByToken(UUID.fromString(token));
    }

    public boolean makeBooking(Booking booking) {
        Room room = booking.getRoom();
        if (room.isBooked(booking.getTimeStart())) {
            return false;
        }
        room.makeBooking(booking);
        roomRepository.save(room);
        bookingRepository.save(booking);
        return true;
    }

    public boolean cancelBooking(String token) {
        if (bookingRepository.existsBookingByToken(UUID.fromString(token))) {
            Booking booking = bookingRepository.findByToken(UUID.fromString(token));
            bookingRepository.deleteById(booking.getId());
            Room room = booking.getRoom();
            room.cancelBooking(booking);
            roomRepository.save(room);
            return true;
        }
        return false;
    }

    public boolean changeBooking(String token, LocalDateTime time) {
        Booking booking = bookingRepository.findByToken(UUID.fromString(token));
        if (booking.getRoom().isBooked(time)){
            return false;
        }
        booking.setTimeStart(time);
        booking.setTimeEnd(time.plusHours(1));
        bookingRepository.save(booking);
        return true;
    }

    public boolean isBookingExist(String token) {
        return bookingRepository.existsBookingByToken(UUID.fromString(token));
    }


}
