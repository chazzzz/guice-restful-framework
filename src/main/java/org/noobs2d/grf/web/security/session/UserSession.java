package org.noobs2d.grf.web.security.session;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.noobs2d.grf.web.util.Crypto;

import java.io.Serializable;

/**
 * Created by chazz on 12/28/2016.
 */
public class UserSession implements Serializable {

    private String userTokenBody;

    private String accessToken;

    private String loginName;

    private String sessionId;

    private UserInfo userInfo;

    public UserSession(String accessToken, UserInfo userInfo, String userTokenBody) {
        this.loginName = userInfo.getLoginName();
        this.accessToken = accessToken;
        this.userInfo = userInfo;
        this.userTokenBody = userTokenBody;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getSessionId() {
        return Crypto.encrypt(
                this.userTokenBody + "|||" + System.currentTimeMillis()
        );
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserSession that = (UserSession) o;

        return new EqualsBuilder()
                .append(accessToken, that.accessToken)
                .append(sessionId, that.sessionId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(accessToken)
                .append(sessionId)
                .toHashCode();
    }

    public String getUserTokenBody() {
        return userTokenBody;
    }
}
