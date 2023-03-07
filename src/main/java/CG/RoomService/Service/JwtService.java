package CG.RoomService.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;


/**
 * A service class to handle JSON Web Token (JWT) operations
 * such as creating, validating and extracting claims from the token
 */
@Service
public class JwtService {
    /**
     * Token validity time in seconds
     */
    @Value("${jwt.token.validity}")
    public long TOKEN_VALIDITY;

    /**
     * Key used in token to store authorities
     */
    @Value("${jwt.authorities.key}")
    public String AUTHORITIES_KEY;

    /**
     * Secret key used to sign the token
     */
    @Value("${jwt.secret.key}")
    public String SECRET_KEY;

    /**
     * Extract the username (subject) claim from the token
     *
     * @param token JWT-token
     * @return subject claim
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract a claim from the token
     *
     * @param token          JWT-token
     * @param claimsResolver function to extract the claim
     * @param <T>            type of claim
     * @return claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generate a token for the given user details
     *
     * @param userDetails user details
     * @return JWT-token
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generate a token for the given user details and extra claims
     *
     * @param extraClaims extra claims to include in the token
     * @param userDetails user details
     * @return JWT-token
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        ArrayList<String> authsList = new ArrayList<>(authorities.size());

        for (GrantedAuthority authority : authorities) {
            authsList.add(authority.getAuthority());
        }
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
//                .claim(AUTHORITIES_KEY, authsList)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * TOKEN_VALIDITY))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Check if the given token is valid for the provided user details
     *
     * @param token       - the token to check
     * @param userDetails - the user details to check against
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        // extract the username from the token
        final String username = extractUsername(token);
        // check if the token is not expired and the username matches the provided user details
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Check if the token has expired
     *
     * @param token - the token to check
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        // extract the expiration date of the token and check if it is before the current date
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extract the expiration date of the token
     *
     * @param token - the token to extract the expiration date from
     * @return the expiration date of the token
     */
    private Date extractExpiration(String token) {
        // extract the expiration date claim from the token
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract all claims from the token
     *
     * @param token - the token to extract the claims from
     * @return the claims in the token
     */
    private Claims extractAllClaims(String token) {
        // parse the claims from the token using the signing key
        return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     * Get the signing key to be used for token verification
     *
     * @return the signing key
     */
    private Key getSigninKey() {
        // decode the secret key and use it to create a hmac key
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}