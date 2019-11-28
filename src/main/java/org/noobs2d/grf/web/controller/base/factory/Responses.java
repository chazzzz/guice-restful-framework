package org.noobs2d.grf.web.controller.base.factory;

import org.apache.http.HttpStatus;
import org.noobs2d.grf.web.controller.base.api.ApiError;
import org.noobs2d.grf.web.controller.base.api.ApiResponse;
import org.noobs2d.grf.web.controller.base.api.Errors;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Responses {

    public static <T> ApiResponse success(String wrappingAttribute, T data) {
        HashMap<String, T> dataMap = new HashMap<>();
        dataMap.put(wrappingAttribute, data);

        return Responses.success(dataMap);
    }

    /**
     * Returns code 200 together with the data provided
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ApiResponse<T> success(T data) {

        if (data == null) {
            throw new IllegalArgumentException("Data provided is null");
        }

        ApiResponse response = new ApiResponse(
                ApiResponse.STATUS_SUCCESS,
                HttpStatus.SC_OK,
                data
        );

        return response;
    }

    /**
     * Returns code 204 without any content
     *
     * @return
     */
    public static ApiResponse success() {
        ApiResponse response = new ApiResponse(
                null,
                HttpStatus.SC_NO_CONTENT
        );

        return response;
    }

    /**
     * Returns code 500 with error information response
     *
     * @param code
     * @param message
     * @return
     */
    public static ApiResponse error(String code, String message) {
        ApiResponse response = new ApiResponse(
                ApiResponse.STATUS_FAILED,
                HttpStatus.SC_INTERNAL_SERVER_ERROR
        );
        response.addError(new ApiError(code, message));

        return response;
    }

    /**
     * Returns code 500 with error information response
     *
     * @param code
     * @param message
     * @return
     */
    public static ApiResponse badRequest(String code, String message) {
        ApiResponse response = new ApiResponse(
                ApiResponse.STATUS_ERROR,
                HttpStatus.SC_BAD_REQUEST
        );
        response.addError(new ApiError(code, message));

        return response;
    }

    /**
     * Returns code 422 with error information response
     *
     * @param code
     * @param message
     * @return
     */
    public static ApiResponse fail(String code, String message) {
        ApiResponse response = new ApiResponse(
                ApiResponse.STATUS_FAILED,
                HttpStatus.SC_UNPROCESSABLE_ENTITY
        );
        response.addError(new ApiError(code, message));

        return response;
    }

    /**
     * Returns code 401 with error information response
     *
     * @param code
     * @param message
     * @return
     */
    public static ApiResponse unauthorized(String code, String message) {
        ApiResponse response = new ApiResponse(
                ApiResponse.STATUS_ERROR,
                HttpStatus.SC_UNAUTHORIZED
        );
        response.addError(new ApiError(code, message));

        return response;
    }

    /**
     * Returns code 404 with error information response
     * @param message
     * @return
     */
    public static ApiResponse notFound(String message) {
        ApiResponse response = new ApiResponse(
                ApiResponse.STATUS_ERROR,
                HttpStatus.SC_NOT_FOUND
        );
        response.addError(new ApiError(Errors.NOT_FOUND, message));

        return response;
    }

    public static ApiResponse<?> image(BufferedImage bufferedImage) {
        ApiResponse apiResponse = new ApiResponse(
                ApiResponse.STATUS_SUCCESS,
                HttpStatus.SC_OK
        );
        apiResponse.setBufferedImage(bufferedImage);

        return apiResponse;
    }
}
