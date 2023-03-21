package CG.RoomService.Models.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AuthenticationResponse class representing the response of an authentication request
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse extends Response{
    /**
     * JWT-token
     */
    private String token;

}