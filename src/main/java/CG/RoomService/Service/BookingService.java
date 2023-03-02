package CG.RoomService.Service;

import CG.RoomService.Models.Booking;
import CG.RoomService.Models.Room;
import CG.RoomService.Models.User;
import CG.RoomService.Repositories.BookingRepository;
import CG.RoomService.Repositories.RoomRepository;
import CG.RoomService.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final RoomRepository roomRepository;

    private final UserRepository userRepository;


    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBooking(String token) {
        return bookingRepository.findByToken(token);
    }
    @Transactional
    public boolean makeBooking(Booking booking) {
        Room room = roomRepository.findByName(booking.getRoom().getName());
        Optional<User> optionalUser = userRepository.findByEmail(booking.getUser().getEmail());
        if (optionalUser.isEmpty()) {
            return false;
        }
        if (room.isBooked(booking.getTimeStart())) {
            return false;
        }
        User user = optionalUser.get();
        user.makeBooking(booking);
        room.makeBooking(booking);
        bookingRepository.save(booking);
        userRepository.save(user);
        roomRepository.save(room);
        return true;
    }

    public boolean cancelBooking(String token) {
        if (bookingRepository.existsBookingByToken(token)) {
            Booking booking = bookingRepository.findByToken(token);
            bookingRepository.deleteById(booking.getId());
            Room room = booking.getRoom();
            room.cancelBooking(booking);
            User user = booking.getUser();
            user.cancelBooking(booking);
            roomRepository.save(room);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean changeBooking(String token, OffsetDateTime time) {
        Booking booking = bookingRepository.findByToken(token);
        if (booking.getRoom().isBooked(time)){
            return false;
        }
        booking.setTimeStart(time);
        booking.setTimeEnd(time.plusHours(1));
        bookingRepository.save(booking);
        return true;
    }

    public boolean isBookingExist(String token) {
        return bookingRepository.existsBookingByToken(token);
    }

    public boolean bookingCleanup() {
        OffsetDateTime current = OffsetDateTime.now();
        List<Booking> bookingList = getBookings();
        for (Booking booking: bookingList) {
            if (booking.getTimeEnd().isBefore(current)) {
                if (!cancelBooking(booking.getToken())) {
                    return false;
                }
            }
        }
        return true;
    }


}
