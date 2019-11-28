package org.noobs2d.grf.web.config;

import com.google.inject.AbstractModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A Guice component to create the singleton instance.
 */
public class InstancesModule extends AbstractModule {

    private static Logger _logger = LogManager.getLogger();

    @Override
    protected void configure() {
        // this is where objects which needs configuration before instantiated is declared
        // i.e. objects which needs configuration values like
        // e.g. new Client(config.getHost(), config.getPort());
        _logger.info("Instantiating singletons...");
    }
}
