package org.noobs2d.grf.system;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private String fileUri;

    private Properties properties;

    public Config(String fileUri) throws IOException {
        this.fileUri = fileUri;
        properties = new Properties();

        load();
    }

    public void load() throws IOException {
        try(InputStream resourceAsStream = this.getClass().getResourceAsStream(this.fileUri)) {
            this.properties.load(resourceAsStream);
        }
    }
}
