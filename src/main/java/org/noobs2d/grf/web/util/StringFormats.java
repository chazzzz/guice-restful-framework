package org.noobs2d.grf.web.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class StringFormats {

    public static String money(String value) {
        DecimalFormat format = new DecimalFormat("#,###,###,##0.00");
        return format.format(new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    public static String money(double value) {
        DecimalFormat format = new DecimalFormat("#,###,###,##0.00");
        return format.format(new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }
}
