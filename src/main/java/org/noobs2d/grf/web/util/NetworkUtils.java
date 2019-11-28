package org.noobs2d.grf.web.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class NetworkUtils {

    public static long ipAddressToLong(String ipAddress) {
        if (StringUtils.isEmpty(ipAddress)) {
            return -1;
        }

        if (ipAddress.contains(".")) {
            String[] parts = ipAddress.split(Pattern.quote("."));
            if (parts.length != 4) {
                return -1;
            }

            long result = 0;
            for (int i = 0; i < parts.length; i++) {
                int power = 3 - i;
                int ip = Integer.parseInt(parts[i]);
                result += ip * Math.pow(256, power);
            }

            return result;

        } else if (ipAddress.contains(":")) {
            String[] parts = ipAddress.split(Pattern.quote(":"));
            if (parts.length != 8) {
                return -1;
            }

            try {
                StringBuilder networkId = new StringBuilder(StringUtils.EMPTY);
                long temp;
                String tempStr;

                for (int i = 0; i < 3; i++) {
                    temp = Long.parseLong(parts[i], 16);
                    if (temp <= 65535) {
                        tempStr = String.format("%04x", temp);
                        networkId.append(tempStr);

                    } else {
                        throw new Exception();
                    }
                }
                return Long.parseLong(networkId.toString(), 16);

            } catch (Exception e) {
                return -1;
            }

        } else {
            return -1;
        }
    }

    private static final String[] HEADERS_LIST = {
            "X-Forwarded-For",
            "True-Client-IP",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    public static String getClientIp(HttpServletRequest request) {
        for (String header : HEADERS_LIST) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    public static boolean isValidIp(String ipAddress) {
        try {
            if (ipAddress == null || ipAddress.isEmpty()) {
                return false;
            }

            String[] parts = ipAddress.split("\\.");
            if (parts.length != 4) {
                return false;
            }

            for (String s : parts) {
                int i = Integer.parseInt(s);
                if ((i < 0) || (i > 255)) {
                    return false;
                }
            }
            return !ipAddress.endsWith(".");
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static String getIpAddressFromHostName(String hostName) {
        try {
            InetAddress address = InetAddress.getByName(hostName);
            return address.getHostAddress();

        } catch (UnknownHostException e) {
            return StringUtils.EMPTY;
        }
    }

}
