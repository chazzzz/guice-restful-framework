package org.noobs2d.grf.system;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

/**
 * Created by chazz on 11/28/2016.
 */
public enum Language {
    ENGLISH("en"),
    CHINESE("cn"),
    VIETNAMESE("vn");

    private final String id;

    Language(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Locale getLocale() {
        if (this.equals(CHINESE)) {
            return Locale.forLanguageTag("zh-cn");
        }

        if (this.equals(VIETNAMESE)) {
            return Locale.forLanguageTag("vi-vn");
        }

        return Locale.ENGLISH;
    }

    /**
     * Returns CHINESE if "cn" is specified.
     * Defaults to ENGLISH.
     *
     * @param id
     * @return
     */
    public static Language forId(String id) {
        if ("cn".equals(id) || "zh".equals(id)) {
            return CHINESE;
        }

        if ("vn".equals(id) || "vi".equals(id)) {
            return VIETNAMESE;
        }

        return ENGLISH;
    }

    public boolean isChinese() {
        return this.equals(CHINESE);
    }

    public boolean isVietnamese() {
        return this.equals(VIETNAMESE);
    }

    /**
     * Congratulations, you just found out the horrible truth.
     * The hardcoded reality is buried deep in this fortress.
     * There's no such thing as perfect code. :P
     *
     * @return
     */
    public int getCurrencyId() {
        if (isChinese()) {
            return 156;
        }

        return 704;
    }


    private static final String VI_LOCALE_HEADER = "8490e7e6b314442b31651a104ed75c59b1e5603cd14397735c26ea28abc67aaa4cfd76490093a0407d54e95c7357d712734219457caed34132ddbe7f0476fd9e4c3359387523fd82314ebb6ea922f70c1acae3c0c844a3b6ec9ee95e5f5a8c22d93cee2bf7f5784bbb25bcf487fb6402ffae78ab518d0a5a51560aa443fe88fb686aa0990740cbc7111591aca53d7e7015c5a0144218bd93";
    private static final String ZH_LOCALE_HEADER = "eccd8d23c20022ccc7e8e643e427cf9175ec340dff85b2fe50c3f5e9ddf8ee844cfd76490093a040954873f39cb0d1ed7d01bb34e84e857f4c3359387523fd82247cbb0370fee8cfc474625e930433a5ec9ee95e5f5a8c228b47bc77f250327d67a40b31fc53dfd0d081f180317588a51ea0f44e331d46bf9820ed3aa4dcfb540486f9ddf3a037cd";

    public String getLocaleHeader() {
        if (this.isChinese()) {
            return ZH_LOCALE_HEADER;

        } else if (this.isVietnamese()) {
            return VI_LOCALE_HEADER;

        } else {
            return StringUtils.EMPTY;
        }
    }

    public static Language forUri(String uri) {
        Language language = Language.CHINESE;
        if (uri.contains("/vi/")) {
            language = Language.VIETNAMESE;

        } else if (uri.contains("/en/")) {
            language = Language.ENGLISH;
        }

        return language;
    }
}
