package org.noobs2d.grf.web.config;

import org.apache.commons.lang3.StringUtils;
import org.noobs2d.grf.web.controller.base.api.ApiError;

import java.util.ArrayList;
import java.util.List;

public class Validator {

    /**
     * Client will receive status code 400 if this is not empty
     * which just means the request parameters are invalid right from the start.
     * (e.g. params that should be numbers, params that should not be empty, etc...)
     */
    private List<ApiError> rejects = new ArrayList<>();

    /**
     * Client will receive status code 422 if this is not empty
     * which means the params are valid but is not appropriate to make the request successful.
     * (e.g. wrong password, wrong verification code, etc...)
     */
    private List<ApiError> fails = new ArrayList<>();

    public boolean rejectIfEmpty(Object value, String errorCode, String errorMessage) {
        if (value == null || (value instanceof String && StringUtils.isEmpty((String) value))) {
            rejects.add(new ApiError(errorCode, errorMessage));
            return true;
        }

        return false;
    }

    public boolean rejectIfNonNumber(String value, String errorCode, String errorMessage) {
        if (!value.matches("\\d*(\\.\\d*)?")) {
            rejects.add(new ApiError(errorCode, errorMessage));
            return true;
        }

        return false;
    }

    public void fail(String errorCode, String errorMessage) {
        fails.add(new ApiError(errorCode, errorMessage));
    }

    public boolean hasRejects() {
        return !rejects.isEmpty();
    }

    public boolean hasFails() {
        return !fails.isEmpty();
    }


    public List<ApiError> getRejects() {
        return rejects;
    }

    public void setRejects(List<ApiError> rejects) {
        this.rejects = rejects;
    }

    public List<ApiError> getFails() {
        return fails;
    }

    public void setFails(List<ApiError> fails) {
        this.fails = fails;
    }

    public boolean rejectIfEmptyList(List<?> list, String errorCode, String errorMessage) {
        if (list == null || list.isEmpty()) {
            rejects.add(new ApiError(errorCode, errorMessage));
            return true;
        }

        return false;
    }

    public void reject(String code, String message) {
        rejects.add(new ApiError(code, message));
    }
}
