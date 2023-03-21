package CG.RoomService.Models.Responses;

import CG.RoomService.Models.DataModels.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingListResponse extends Response{
    List<Booking> list;
}
