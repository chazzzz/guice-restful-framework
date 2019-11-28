package org.noobs2d.grf.web.controller.base.api;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.http.HttpStatus;
import org.noobs2d.grf.web.config.json.JsonExclude;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = -8181971574834590213L;

    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_FAILED = "failed";
    public static final String STATUS_ERROR = "error";

    public ApiResponse(String status, int statusCode) {
        this.status = status;
        this.statusCode = statusCode;
    }

    public ApiResponse(String status, int statusCode, T data) {
        this(status, statusCode);
        this.data = data;
    }

    /**
     * Represents the http status code
     */
    @JsonExclude
    private int statusCode;

    private String status;

    private T data;

    private List<ApiError> errors;

    private String message;

    private transient BufferedImage bufferedImage;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void addError(ApiError apiError) {
        if (errors == null) {
            errors = new ArrayList<>();
        }

        errors.add(apiError);
    }

    public List<ApiError> getErrors() {
        return errors;
    }

    public void setErrors(List<ApiError> errors) {
        this.errors = errors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ApiResponse<?> that = (ApiResponse<?>) o;

        return new EqualsBuilder()
                .append(statusCode, that.statusCode)
                .append(data, that.data)
                .append(errors, that.errors)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(statusCode)
                .append(data)
                .append(errors)
                .toHashCode();
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public boolean isImage() {
        return this.bufferedImage != null;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ApiResponse withMessage(String message) {
        this.message = message;
        this.status = ApiResponse.STATUS_SUCCESS;
        this.statusCode = HttpStatus.SC_OK;
        return this;
    }
}
