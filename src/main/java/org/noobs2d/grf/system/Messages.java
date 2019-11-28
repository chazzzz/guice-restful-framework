package org.noobs2d.grf.system;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

/**
 * Created by chazz on 12/10/2016.
 */
public class Messages {
    public static String get(String key, Language language) {
        String message = ResourceBundle.getBundle("messages/messages",
                language.getLocale()).getString(key);

        try {
            return new String(message.getBytes("ISO-8859-1"), "UTF-8");

        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
