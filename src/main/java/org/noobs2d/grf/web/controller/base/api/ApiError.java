package org.noobs2d.grf.web.controller.base.api;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.noobs2d.grf.web.config.json.JsonExclude;

import java.io.Serializable;

public class ApiError implements Serializable {

    private static final long serialVersionUID = -3194966858481632434L;

    public ApiError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;

    @JsonExclude
    private int statusCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ApiError apiError = (ApiError) o;

        return new EqualsBuilder()
                .append(code, apiError.code)
                .append(message, apiError.message)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(code)
                .append(message)
                .toHashCode();
    }
}
