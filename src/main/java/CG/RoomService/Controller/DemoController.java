package CG.RoomService.Controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@RestController

@RequestMapping("/demo-controller")
public class DemoController {


    @GetMapping("/admin")
    public ResponseEntity<String> sayHelloAdmin() {
        return ResponseEntity.ok("Hello i'm a secured Admin");
    }


    @GetMapping("/user")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello i'm a secured Werk");
    }
}
