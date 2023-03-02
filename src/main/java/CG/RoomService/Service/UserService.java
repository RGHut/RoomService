package CG.RoomService.Service;


import CG.RoomService.Models.Role;
import CG.RoomService.Models.User;
import CG.RoomService.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> changeRole(String email, String newRole) {
        if (newRole.equals("")) {
            newRole = "GUEST";
        }
        Optional<User> optionalUser = userRepository.findByEmail(email);
        String finalNewRole = newRole;
        optionalUser.ifPresent(user -> {
            user.setAuthorities(Role.valueOf(finalNewRole));
            userRepository.save(user);
        });
        return optionalUser;
    }
}



