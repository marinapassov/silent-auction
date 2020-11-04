package auction.entity;

import java.time.LocalDateTime;

/**
 * Represents an authorisation token details
 */
public class AuthorisationToken {
    final private  int authId;
    final private String token;
    final private LocalDateTime tokenExpiry;

    public AuthorisationToken(int authId, String token, LocalDateTime tokenExpiry) {
        this.authId = authId;
        this.token = token;
        this.tokenExpiry = tokenExpiry;
    }

    public int getAuthId() {
        return authId;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getTokenExpiry() {
        return tokenExpiry;
    }
}
