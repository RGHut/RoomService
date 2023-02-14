package CG.RoomService.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/demo-controller")
public class DemoController {


    @GetMapping("/admin")
    public ResponseEntity<String> sayHelloAdmin() {
        return ResponseEntity.ok("Hello i'm a secured Admin");
    }


    @GetMapping("/user")
    public ResponseEntity<String> sayHello() {
        System.out.println("Received GET request at /user endpoint");
        return ResponseEntity.ok("Hello, I'm a secured Work");
    }

    @PostMapping("/change-role")
    public ResponseEntity<String> sayChange() {
        return ResponseEntity.ok("Hello i'm a secured Werk");
    }
}
