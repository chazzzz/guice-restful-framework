package org.noobs2d.grf.web.controller.base;

import com.google.gson.JsonSyntaxException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.noobs2d.grf.system.Language;
import org.noobs2d.grf.system.Messages;
import org.noobs2d.grf.web.config.Validator;
import org.noobs2d.grf.web.config.json.GsonFactory;
import org.noobs2d.grf.web.controller.base.api.ApiError;
import org.noobs2d.grf.web.controller.base.api.ApiRequest;
import org.noobs2d.grf.web.controller.base.api.ApiResponse;
import org.noobs2d.grf.web.controller.base.api.Errors;
import org.noobs2d.grf.web.controller.base.factory.Responses;
import org.noobs2d.grf.web.security.session.SessionRequired;
import org.noobs2d.grf.web.util.IOUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class ApiController extends HttpServlet {

    private static final Logger _logger = LogManager.getLogger();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Language language = Language.forUri(req.getRequestURI());

        ApiResponse apiResponse;
        try {

            ApiRequest apiRequest = new ApiRequest(req, resp);

            // capitalizes first letter e.g. POST -> Post
            String method = req.getMethod().substring(0, 1) + req.getMethod().substring(1).toLowerCase();

            Method serviceMethod = this.getClass().getDeclaredMethod(
                    "on{}Request".replace("{}", method), ApiRequest.class);

            if (serviceMethod.isAnnotationPresent(SessionRequired.class) && apiRequest.getUserSession() == null) {
                IOUtils.write(Responses.unauthorized(Errors.UNAUTHORIZED,
                        Messages.get("error.unauthorized", language)), resp);
                return;
            }

            Validator validator = new Validator();

            // attempt to call validate method
            try {
                Method validationMethod = this.getClass().getDeclaredMethod(
                        "onValidate{}".replace("{}", method), Validator.class, ApiRequest.class
                );

                validationMethod.setAccessible(true);
                validationMethod.invoke(this, validator, apiRequest);

                if (validator.hasFails() || validator.hasRejects()) {
                    writeValidationErrors(validator, resp);
                    return;
                }
            } catch (NoSuchMethodException e) {
                // do nothing
            }

            serviceMethod.setAccessible(true);
            apiResponse = (ApiResponse) serviceMethod.invoke(this, apiRequest);

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);

        } catch (InvocationTargetException e) {
                _logger.error("An error occurred", e);
                apiResponse = Responses.error(Errors.DEFAULT_ERROR, Messages.get("error.default", language));
        } catch (JsonSyntaxException e) {
            apiResponse = new ApiResponse(ApiResponse.STATUS_ERROR, HttpStatus.SC_BAD_REQUEST);
            apiResponse.addError(new ApiError(Errors.INVALID_PAYLOAD, "payload is invalid"));
        }

        if ("GET".equals(req.getMethod()) && !apiResponse.isImage()) {
            String responseTxt = GsonFactory.makeDefault().toJson(apiResponse);
            String eTag = DigestUtils.md5Hex(responseTxt);

            // only generate eTag for 200 OK responses
            if (apiResponse.getStatusCode() == HttpStatus.SC_OK) {
                Long timestamp = System.currentTimeMillis();

                resp.addHeader(HttpHeaders.ETAG, eTag);
                _logger.debug("Generating eTag stamp took {}ms", System.currentTimeMillis() - timestamp);
            }

            // match cache header
            String ifNoneMatch = req.getHeader(HttpHeaders.IF_NONE_MATCH);
            if (!StringUtils.isEmpty(ifNoneMatch)) {
                if (eTag.equals(ifNoneMatch)) {
                    resp.reset();
                    resp.setStatus(HttpStatus.SC_NOT_MODIFIED);
                    return;
                }
            }

            IOUtils.write(responseTxt, apiResponse.getStatusCode(), resp);
            return;
        }

        if (apiResponse.isImage()) {
            IOUtils.writeImage(apiResponse.getBufferedImage(), resp);
            return;
        }

        IOUtils.write(apiResponse, resp);
    }

    private void writeValidationErrors(Validator validator, HttpServletResponse resp) throws IOException {
        // throw 400 for rejected request parameters
        if (validator.hasRejects()) {
            ApiResponse apiResponse = new ApiResponse(ApiResponse.STATUS_ERROR, HttpStatus.SC_BAD_REQUEST);
            apiResponse.setErrors(validator.getRejects());

            IOUtils.write(apiResponse, resp);
            return;
        }

        // throw 422 for failed request parameters
        if (validator.hasFails()) {
            ApiResponse apiResponse = new ApiResponse(ApiResponse.STATUS_FAILED, HttpStatus.SC_UNPROCESSABLE_ENTITY);
            apiResponse.setErrors(validator.getFails());

            IOUtils.write(apiResponse, resp);
            return;
        }
    }

    protected ApiResponse<?> onGetRequest(ApiRequest apiRequest) throws Exception {
        return Responses.notFound("This method is not supported");
    }

    protected ApiResponse<?> onPostRequest(ApiRequest apiRequest) throws Exception {
        return Responses.notFound("This method is not supported");
    }

    protected ApiResponse<?> onDeleteRequest(ApiRequest apiRequest) throws Exception {
        return Responses.notFound("This method is not supported");
    }

    protected ApiResponse<?> onPutRequest(ApiRequest apiRequest) throws Exception {
        return Responses.notFound("This method is not supported");
    }

    protected void onValidatePost(Validator validator, ApiRequest apiRequest) {
    }

    protected void onValidateGet(Validator validator, ApiRequest apiRequest) {
    }

    protected void onValidatePut(Validator validator, ApiRequest apiRequest) {
    }

    protected void onValidateDelete(Validator validator, ApiRequest apiRequest) {
    }
}
