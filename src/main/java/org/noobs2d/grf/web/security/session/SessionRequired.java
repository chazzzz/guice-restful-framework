package org.noobs2d.grf.web.security.session;

import java.lang.annotation.*;

/**
 * This annotation is appended to #onGetRequest #onPostRequest #onPutRequest and #onDeleteRequest
 * methods of the implementing API controller.
 *
 * When you put this, the controller will automatically require user parameters to be present and will
 * respond with an authentication error if no user is present
 *
 * Created by chazz on 12/28/2016.
 */
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionRequired {
}
