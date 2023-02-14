package CG.RoomService.User.Service;


import CG.RoomService.User.Role;
import CG.RoomService.User.User;
import CG.RoomService.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
        if (newRole.equals("")){
            newRole = "GUEST";}
        Optional<User> optionalUser = userRepository.findByEmail(email);
        String finalNewRole = newRole;
        optionalUser.ifPresent(user -> {
            user.setAuthorities(Role.valueOf(finalNewRole));
            userRepository.save(user);
        });
        return optionalUser;
    }
    }



