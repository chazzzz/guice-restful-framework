package org.noobs2d.grf.web.controller.base.api;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.IOUtils;
import org.noobs2d.grf.system.Language;
import org.noobs2d.grf.web.config.json.GsonFactory;
import org.noobs2d.grf.web.security.session.UserSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiRequest {

    private static final Logger _logger = LogManager.getLogger();


    private final HttpServletResponse httpServletResponse;

    private HttpServletRequest httpServletRequest;

    public ApiRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
        this.method = httpServletRequest.getMethod();

        if (isPost() || isPut()) {
            try {
                String body = IOUtils.toString(new InputStreamReader(httpServletRequest.getInputStream(), "UTF-8"));
                if (StringUtils.isEmpty(body)) {
                    body = "{}"; //empty json
                }

                this.requestBody = GsonFactory.makeParser().parse(body).getAsJsonObject();

            } catch (IOException e) {
                _logger.error("There was an error during consumption of request payload", e);
            }
        }

        this.userSession = (UserSession) this.httpServletRequest.getAttribute(UserSession.class.getName());
        this.userAgent = (UserAgent) this.httpServletRequest.getAttribute(UserAgent.class.getName());
    }

    private String method;

    private JsonObject requestBody;

    private UserSession userSession;

    private UserAgent userAgent;

    public String getUri() {
        return this.httpServletRequest.getRequestURI().toString();
    }

    public String getParameter(String param) {
        if (isPost() || isPut()) {
            if (requestBody.has(param)) {
                return requestBody.get(param).getAsString();

            } else {
                return StringUtils.EMPTY;
            }
        }

        return this.httpServletRequest.getParameter(param);
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isPost() {
        return "POST".equals(this.method);
    }

    public boolean isPut() {
        return "PUT".equals(this.method);
    }

    public UserSession getUserSession() {
        return this.userSession;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(UserAgent userAgent) {
        this.userAgent = userAgent;
    }

    public <T> T getBodyAs(Class<T> clazz) {
        if (isPost() || isPut()) {
            return GsonFactory.makeDefault().fromJson(requestBody, clazz);

        } else {
            // the following block will make your eyes hurt but this is essential to achieve a brighter future
            // this code makes an instance of clazz with all GET and DELETE parameters with matching field names
            try {
                JsonObject params = new JsonObject();

                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    String name = field.getName();
                    String value = this.httpServletRequest.getParameter(name);

                    if (!StringUtils.isEmpty(value)) {
                        params.addProperty(name, value);
                    }
                }

                _logger.debug("Converting params {} to clazz", params.toString());

                return GsonFactory.makeDefault().fromJson(params, clazz);
            } catch (Exception e) {
                _logger.error("An error occurred.", e);
            }

            return null;
        }
    }

    public void attach(String id, Object value) {
        this.httpServletRequest.setAttribute(id, value);
    }

    public Object getAttachment(String id) {
        return this.httpServletRequest.getAttribute(id);
    }

    public String getPathVariable(String locationInfo) {
        Pattern pattern = Pattern.compile(locationInfo.replace("{}", "(.*)?"));
        String uri = getUri();

        Matcher matcher = pattern.matcher(uri);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return StringUtils.EMPTY;
    }

    public Language getLanguage() {
        return Language.forUri(httpServletRequest.getRequestURI());
    }

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }
}
