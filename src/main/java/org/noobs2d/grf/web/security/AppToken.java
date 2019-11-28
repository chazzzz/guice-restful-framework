package org.noobs2d.grf.web.security;

import org.apache.commons.lang3.StringUtils;
import org.noobs2d.grf.web.util.Crypto;

import java.util.Arrays;

public class AppToken {

    private String loginName;
    private String userId;
    private String agentCode;

    private String deviceId;
    private String application;
    private String platform;
    private String currencyId;
    private String timestamp;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        if (!StringUtils.isEmpty(this.loginName)) {
            String signature = StringUtils.join(Arrays.asList(
                    this.loginName,
                    this.userId,
                    this.agentCode,
                    this.deviceId,
                    this.application,
                    this.platform,
                    this.currencyId,
                    System.currentTimeMillis() + ""
            ), "|");

            return Crypto.encrypt(signature);
        }

        String signature = StringUtils.join(Arrays.asList(
                this.deviceId,
                this.application,
                this.platform,
                this.currencyId,
                System.currentTimeMillis() + ""
        ), "|");

        return Crypto.encrypt(signature);
    }
}
