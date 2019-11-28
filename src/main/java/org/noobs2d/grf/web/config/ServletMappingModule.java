package org.noobs2d.grf.web.config;

import com.google.inject.servlet.ServletModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.noobs2d.grf.web.controller.impl.HelloWorldController;

/**
 * The representation of web.xml servlet mappings.
 * This class is a Guice component and all servlets and filters must be registered here.
 *
 * All Filter and Servlet classes should be annotated with @Singleton
 */
public class ServletMappingModule extends ServletModule {

    private static final Logger _logger = LogManager.getLogger();

    private void configureFilters() {

    }

    @Override
    protected void configureServlets() {
        _logger.info("Mapping filters and servlets...");

        // I like to separate filters from servlets
        configureFilters();

        serve("/").with(HelloWorldController.class);
    }
}