package CG.RoomService.Service;

import CG.RoomService.Models.Booking;
import CG.RoomService.Repositories.BookingRepository;
import CG.RoomService.Repositories.RoomRepository;
import CG.RoomService.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final RoomRepository roomRepository;

    private final UserRepository userRepository;


    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    public void makeBooking(Booking booking) {

    }


}
