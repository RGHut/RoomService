package CG.RoomService.Controllers;

import CG.RoomService.Models.Building;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class BookingController {

    private final ArrayList<Building> buildings = new ArrayList<Building>();

    public void addBuilding() {
        Building building = new Building("Corpus den Hoorn 106");
        building.addRoom("grote vergader ruimte", 1, 8, true);
        buildings.add(building);
    }
}
