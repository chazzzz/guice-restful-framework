package org.noobs2d.grf.web.security;

import org.apache.commons.lang3.StringUtils;
import org.noobs2d.grf.web.security.error.InvalidTokenException;
import org.noobs2d.grf.web.util.Crypto;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Don't be confused. AuthTokens are used for login/authentication,
 * AccessTokens are used for access/authorization
 */
public class AuthTokens {

    private static final String SEPARATOR = "||||";

    public static TokenParts verify(String authToken) throws InvalidTokenException {
        String decrypted = Crypto.decrypt(authToken);

        if (StringUtils.isEmpty(decrypted) || !decrypted.contains(SEPARATOR)) {
            throw new InvalidTokenException();
        }

        String[] slices = decrypted.split(Pattern.quote(SEPARATOR));

        if (slices.length < 5) {
            throw new InvalidTokenException();
        }

        TokenParts tokenParts = new TokenParts();

        try {
            tokenParts.setTimestamp(Long.parseLong(slices[0]));
            tokenParts.setLoginName(slices[1]);
            tokenParts.setUserId(slices[2]);
            tokenParts.setSessionId(slices[3]);

            //check if the salt for the token is integer.
            Integer.parseInt(slices[4]);
            tokenParts.setSalt(slices[4]);


            return tokenParts;

        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    public static String generate(TokenParts tokenParts) {
        String signature = StringUtils.join(Arrays.asList(
                tokenParts.getTimestamp(),
                tokenParts.getLoginName(),
                tokenParts.getUserId(),
                tokenParts.getSessionId(),
                tokenParts.getSalt()
        ), SEPARATOR);

        return Crypto.encrypt(signature);
    }

    public static class TokenParts {

        private String loginName;

        private Long timestamp;

        private String salt;

        private String userId;

        private String sessionId;

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

    }
}
