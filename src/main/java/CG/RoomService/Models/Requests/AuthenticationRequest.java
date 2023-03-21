package CG.RoomService.Models.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AuthenticationRequest class representing the request for user authentication
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    /**
     * User's email address
     */
    private String email;
    /**
     * User's password
     */
    private String password;
}