package CG.RoomService.Service;

import CG.RoomService.Models.RegisterRequest;
import CG.RoomService.Models.AuthenticationRequest;
import CG.RoomService.Models.AuthenticationResponse;
import CG.RoomService.Models.Role;
import CG.RoomService.Models.User;
import CG.RoomService.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Service class for user authentication
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Register a new user
     *
     * @param request RegisterRequest object containing user details
     * @return AuthenticationResponse object containing JWT token
     */
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .company(request.getCompany())
                .role(Role.ADMIN)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Authenticate an existing user
     *
     * @param request AuthenticationRequest object containing user email and password
     * @return AuthenticationResponse object containing JWT token
     */


    /**
     * List all users
     *
     * @return List of User objects
     */
    public List<User> listAll() {
        return repository.findAll();
    }

    @Autowired
    private RestTemplate restTemplate;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        AuthenticationResponse response = AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

        return response;
    }
}
