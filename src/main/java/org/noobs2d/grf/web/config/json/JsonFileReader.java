package org.noobs2d.grf.web.config.json;

import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStreamReader;

public class JsonFileReader {

    private static final Logger _logger = LogManager.getLogger();

    public static JsonObject read(String fileUri) {
        _logger.debug("Loading json file " + fileUri);

        try {
            InputStreamReader streamReader = new InputStreamReader(
                    JsonFileReader.class.getClassLoader().getResourceAsStream(fileUri), Charsets.UTF_8
            );

            return GsonFactory.makeParser().parse(streamReader).getAsJsonObject();
        } catch (NullPointerException e) {
            return null;
        }
    }
}
