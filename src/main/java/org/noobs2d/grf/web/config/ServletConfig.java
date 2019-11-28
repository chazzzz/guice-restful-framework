package org.noobs2d.grf.web.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.noobs2d.grf.system.hook.ShutdownHook;
import org.noobs2d.grf.system.hook.StartupHook;

import javax.servlet.ServletContextEvent;

public class ServletConfig extends GuiceServletContextListener {

    private static Logger _logger = LogManager.getLogger();

    @Override
    protected Injector getInjector() {
        Injector injector = Guice.createInjector(
                new InstancesModule(),
                new ServletMappingModule()
        );

        return injector;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            StartupHook.start();

        } catch (Exception e) {
            throw new RuntimeException("Unable to start the application", e);
        }

        super.contextInitialized(servletContextEvent);

        _logger.info("All systems clear. We're good to go!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        super.contextDestroyed(servletContextEvent);

        try {
            ShutdownHook.start();

        } catch (Exception e) {
            LogManager.getLogger().error("Unable to shutdown components. ", e);
        }
    }
}
