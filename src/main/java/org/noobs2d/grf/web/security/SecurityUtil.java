package org.noobs2d.grf.web.security;

import org.apache.commons.lang3.StringUtils;

public class SecurityUtil {

    /**
     * Convert first 4 characters of the text to asterisk *
     *
     * @param text
     * @return
     */
    public static String mystify(String text) {
        if (StringUtils.isEmpty(text)) {
            return StringUtils.EMPTY;
        }

        return text.replaceFirst("\\S{3}", "***");
    }

    /**
     * Convert last characters of the text to asterisk *
     *
     * @param text
     * @return
     */
    public static String mystifyEnd(String text) {
        if (StringUtils.isEmpty(text)) {
            return StringUtils.EMPTY;
        }

        return text.replace(text.substring(3), "***");
    }

}
