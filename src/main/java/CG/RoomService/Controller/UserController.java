package CG.RoomService.Controller;

import CG.RoomService.User.ChangeRoleRequest;
import CG.RoomService.User.Service.UserService;
import CG.RoomService.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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