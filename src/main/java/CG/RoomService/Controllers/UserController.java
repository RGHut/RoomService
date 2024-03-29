package CG.RoomService.Controllers;

import CG.RoomService.Models.Requests.ChangeRoleRequest;
import CG.RoomService.Service.UserService;
import CG.RoomService.Models.DataModels.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/role")
    public ResponseEntity<User> changeRole(@RequestBody ChangeRoleRequest request) {
        Optional<User> updatedUser = userService.changeRole(request.getEmail(), request.getRole());
        return updatedUser
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}