package org.noobs2d.grf.web.security;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class TokenParts extends ArrayList<String> {

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
