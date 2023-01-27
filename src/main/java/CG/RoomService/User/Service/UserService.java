package CG.RoomService.User.Service;


import CG.RoomService.User.User;
import CG.RoomService.User.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
    public class UserService {

        private final UserRepository userRepository;

        public UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

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

    }

