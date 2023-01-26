package CG.RoomService.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RegisterRequest class representing the request for user registration
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    /**
     * User's first name
     */
    private String firstname;
    /**
     * User's last name
     */
    private String lastname;
    /**
     * User's email address
     */
    private String email;
    /**
     * User's password
     */
    private String password;
    /**
     * User's company 
     */
    private String company;
}