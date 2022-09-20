package com.skullprogrammer.project.utility;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.skullprogrammer.project.error.UtilityException;

@SuppressWarnings("unchecked")
public class JsonMapper {
    
    private String datePatternFormat = "dd-MM-yyyy HH:mm:ss";

    /* ******************************************
     *               Conversion
     * ****************************************** */
    public String toJson(Object object){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new AdapterDate());
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(object);
    }

    /* ******************************************
     *               Load
     * ****************************************** */
    public Object load(InputStream inputStream, Class t) throws UtilityException {
        Object object = null;
        Reader stream = null;
        try {
            stream = new InputStreamReader(inputStream);
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Date.class, new AdapterDate());
            Gson gson = builder.create();
            object = gson.fromJson(stream, t);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UtilityException(e);
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (java.io.IOException ioe) {
            }
        }
        return object;
    }
    public Object load(Reader reader, Class t) throws UtilityException {
        Object object = null;
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Date.class, new AdapterDate());
            Gson gson = builder.create();
            object = gson.fromJson(reader, t);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UtilityException(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (java.io.IOException ioe) {
            }
        }
        return object;
    }

    /* ******************************************
     *               Save
     * ****************************************** */
    public void save(Object object, OutputStream out) throws UtilityException {
        PrintWriter stream = null;
        try {
            stream = new PrintWriter(out);
            String jsonString = toJson(object);
            stream.print(jsonString);
        } catch (Exception ioe) {
            throw new UtilityException(ioe);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

    }

    private class AdapterDate implements JsonSerializer<Date>, JsonDeserializer<Date> {

        public JsonElement serialize(Date date, Type tipo, JsonSerializationContext context) {
            DateFormat dateFormat = new SimpleDateFormat(datePatternFormat);
            return new JsonPrimitive(dateFormat.format(date.getTime()));
        }

        public Date deserialize(JsonElement json, Type tipo, JsonDeserializationContext context) throws JsonParseException {
            try {
                String dateString = json.getAsString();
                DateFormat dateFormat = new SimpleDateFormat(datePatternFormat);
                Date date = dateFormat.parse(dateString);
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                return calendar.getTime();
            } catch (ParseException ex) {
                throw new JsonParseException(ex);
            }
        }
    }

    public String getDatePatternFormat() {
        return datePatternFormat;
    }

    public void setDatePatternFormat(String datePatternFormat) {
        this.datePatternFormat = datePatternFormat;
    }
}
