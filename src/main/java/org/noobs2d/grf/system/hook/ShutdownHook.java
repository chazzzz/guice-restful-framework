package org.noobs2d.grf.system.hook;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ShutdownHook {

    private static final Logger _logger = LogManager.getLogger();

    public static void start() throws IOException {
        _logger.info("Application context is destroyed.");
    }
}
