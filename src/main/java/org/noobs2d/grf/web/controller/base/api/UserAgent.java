package org.noobs2d.grf.web.controller.base.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAgent {

    private static final Pattern PATTERN =
            Pattern.compile("Wellbet\\s(.*?)/(\\d+)\\s\\((.*?)\\s(.*?)/(\\d+);(.*?)/(.*)\\)");

    private String platform;

    private String appVersionCode;

    private String appVersion;

    private String osVersion;

    private String osVersionCode;

    private String deviceId;

    private String deviceInfo;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(String appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getOsVersionCode() {
        return osVersionCode;
    }

    public void setOsVersionCode(String osVersionCode) {
        this.osVersionCode = osVersionCode;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    @Override
    public String toString() {
        return String.format("Wellbet %s/%s (%s %s/%s; %s/%s)",
                this.appVersion,
                this.appVersionCode,
                this.platform,
                this.osVersion,
                this.osVersionCode,
                this.deviceInfo,
                this.deviceId);
    }

    public static UserAgent parse(String text) {
        Matcher matcher = PATTERN.matcher(text);
        if (!matcher.find()) {
            return null;
        }

        int i = 1;

        UserAgent userAgent = new UserAgent();
        userAgent.appVersion = matcher.group(i++).trim();
        userAgent.appVersionCode = matcher.group(i++).trim();
        userAgent.platform = matcher.group(i++).trim();
        userAgent.osVersion = matcher.group(i++).trim();
        userAgent.osVersionCode = matcher.group(i++).trim();
        userAgent.deviceInfo = matcher.group(i++).trim();
        userAgent.deviceId = matcher.group(i++).trim();

        return userAgent;
    }

}
