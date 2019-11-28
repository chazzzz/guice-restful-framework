package org.noobs2d.grf.web.config.json.adapter;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeOnlyAdapter extends TypeAdapter<Date> {

    private DateFormat formatter;

    public TimeOnlyAdapter() {
        formatter = new SimpleDateFormat("HH:mm:ss");
    }

    public TimeOnlyAdapter(String pattern) {
        this.formatter = new SimpleDateFormat(pattern);
    }

    @Override
    public void write(JsonWriter jsonWriter, Date date) throws IOException {
        jsonWriter.value(date != null? formatter.format(date): null);
    }

    @Override
    public Date read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        } else {
            try {
                return formatter.parse(jsonReader.nextString());
            } catch (ParseException var3) {
                throw new JsonSyntaxException(var3);
            }
        }
    }
}
