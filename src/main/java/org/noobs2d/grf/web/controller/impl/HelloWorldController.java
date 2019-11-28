package org.noobs2d.grf.web.controller.impl;

import com.google.inject.Singleton;
import org.noobs2d.grf.web.controller.base.ApiController;
import org.noobs2d.grf.web.controller.base.api.ApiRequest;
import org.noobs2d.grf.web.controller.base.api.ApiResponse;
import org.noobs2d.grf.web.controller.base.factory.Responses;

@Singleton
public class HelloWorldController extends ApiController {

    @Override
    protected ApiResponse<?> onGetRequest(ApiRequest apiRequest) throws Exception {
        return Responses.success("message", "Hello World!");
    }
}
