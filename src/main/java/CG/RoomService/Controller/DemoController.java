package CG.RoomService.Controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/demo-controller")
public class DemoController {
    @RolesAllowed("ADMIN")
    @GetMapping("/admin")
    public ResponseEntity<String> sayHelloAdmin() {
        return ResponseEntity.ok("Hello i'm a secured Admin");
    }

    @RolesAllowed({"WERKNEMER","ADMIN"})
    @GetMapping("/user")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello i'm a secured Werk");
    }
}
