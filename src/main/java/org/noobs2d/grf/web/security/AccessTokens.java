package org.noobs2d.grf.web.security;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.noobs2d.grf.web.security.error.InvalidTokenException;
import org.noobs2d.grf.web.util.Crypto;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Don't be confused. AuthTokens are used for login/authentication,
 * AccessTokens are used for access/authorization
 */
public class AccessTokens {

    public static final String SECRET_KEY = "MqxSx48x86KCcc19pIWR8kFsxhj9XJaM";

    public static String create(String subject) {
        long timestamp = System.currentTimeMillis();
        String salt = RandomStringUtils.randomNumeric(5);

        return Crypto.encrypt(
                new TokenParts(timestamp, subject, salt).join(SECRET_KEY)
        );
    }

    public static TokenParts verify(String token) throws InvalidTokenException {
        String decrypted = Crypto.decrypt(token);
        if (StringUtils.isEmpty(decrypted)) {
            throw new InvalidTokenException();
        }

        TokenParts parts = new TokenParts(decrypted);
        if (!AccessTokens.isValid(parts)) {
            throw new InvalidTokenException();
        }

        return parts;
    }

    private static boolean isValid(TokenParts parts) {
        return parts != null && parts.isValid(SECRET_KEY);
    }

    public static class TokenParts extends ArrayList<String> {

        private static final String SEPARATOR = "||";

        public TokenParts(Long timestamp, String subject, String salt) {
            this.add(String.valueOf(timestamp));
            this.add(subject);
            this.add(salt);
        }

        public TokenParts(String token) {
            super(Arrays.asList(StringUtils.split(token, SEPARATOR)));
        }

        public boolean isValid(String secret) {
            if (this.size() != 4) {
                return false;
            }

            if (StringUtils.isEmpty(this.getSalt()) || this.getSalt().length() != 5) {
                return false;
            }

            return !StringUtils.isEmpty(this.getSecret()) && this.getSecret().equals(secret);
        }

        public String getUserId() {
            return this.get(1);
        }

        public Long getTimestamp() {
            return Long.valueOf(this.get(0));
        }

        public String getSalt() {
            return this.get(2);
        }

        public String getSecret() {
            return this.get(3);
        }

        public String join(String secret) {
            this.add(secret);
            return StringUtils.join(this, SEPARATOR);
        }
    }
}
