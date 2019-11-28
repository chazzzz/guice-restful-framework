package org.noobs2d.grf.system;

public class System {

    private static Config config;

    public static void init(Config config) {
        System.config = config;
    }

    public static Config getConfig() {
        return config;
    }

}
