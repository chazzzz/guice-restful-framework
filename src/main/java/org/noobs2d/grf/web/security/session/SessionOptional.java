package org.noobs2d.grf.web.security.session;

import java.lang.annotation.*;

/**
 * This annotation is appended to #onGetRequest #onPostRequest #onPutRequest and #onDeleteRequest
 * methods of the implementing API controller.
 * <p>
 * Unlike SessionRequired, this one doesn't do anything and just basically tells the co-developers
 * that a handler can support authenticated and un-authenticated calls
 * <p>
 * Created by chazz on 12/28/2016.
 */
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionOptional {
}
