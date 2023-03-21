package CG.RoomService.Models.Responses;

import CG.RoomService.Models.DataModels.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomListResponse extends Response {
    List<Room> list;
}
