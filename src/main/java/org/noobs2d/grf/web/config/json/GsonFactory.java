package org.noobs2d.grf.web.config.json;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * We decided to make a wrapper of GsonFactory
 *
 * @author chazz
 */
public class GsonFactory {

    public static Gson makeDefault() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, new TypeAdapter<Date>() {
                    DateFormat formatter = new SimpleDateFormat(
                            "HH/mm/yyyy"
                    );

                    @Override
                    public void write(JsonWriter jsonWriter, Date date) throws IOException {
                        String value = null;
                        if (date != null) {
                            value = formatter.format(date);
                        }

                        jsonWriter.value(value);
                    }

                    @Override
                    public Date read(JsonReader jsonReader) throws IOException {

                        if (jsonReader.hasNext()) {
                            String value = jsonReader.nextString();
                            if (!StringUtils.isEmpty(value)) {
                                try {
                                    return formatter.parse(value);
                                } catch (ParseException e) {
                                    throw new IOException(e);
                                }
                            }
                        }

                        return null;
                    }
                })
                .excludeFieldsWithModifiers(Modifier.STATIC)
                // exclude the fields with @JsonExclude
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                        return fieldAttributes.getAnnotation(JsonExclude.class) != null;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> aClass) {
                        return false;
                    }
                })
                .create();
    }

    public static JsonParser makeParser() {
        return new JsonParser();
    }
}
