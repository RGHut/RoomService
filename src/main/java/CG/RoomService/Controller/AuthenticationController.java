package CG.RoomService.Controller;

import CG.RoomService.Auth.AuthenticationRequest;
import CG.RoomService.Auth.AuthenticationResponse;
import CG.RoomService.Auth.AuthenticationService;
import CG.RoomService.Auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestController annotation is used to mark this class as a controller where every method returns a domain object instead of a view.
 * RequestMapping annotation is used to map web requests onto specific handler classes and/or handler methods.
 * RequiredArgsConstructor annotation is used to create constructor with required fields
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class AuthenticationController {

    /**
     * The service instance being used to perform authentication and registration operations
     */
    private final AuthenticationService service;

    /**
     * The PostMapping annotation is used to handle HTTP POST requests.
     * This method is used for registration, the request body is mapped to a RegisterRequest object
     * @param request the request containing the user's registration information
     * @return a ResponseEntity with an AuthenticationResponse object
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    /**
     * The PostMapping annotation is used to handle HTTP POST requests.
     * This method is used for authentication, the request body is mapped to an AuthenticationRequest object
     * @param request the request containing the user's authentication information
     * @return a ResponseEntity with an AuthenticationResponse object
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
