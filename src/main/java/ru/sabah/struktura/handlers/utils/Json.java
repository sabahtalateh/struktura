package ru.sabah.struktura.handlers.utils;

import com.google.gson.Gson;


import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.util.Collections;

@ApplicationScoped
public class Json {
    private static final Gson gson = new Gson();

    public JsonObject from(Object o) {
        JsonReader jsonReader = javax.json.Json.createReader(new StringReader(gson.toJson(o)));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();

        return object;
    }

    public <T> T to(Class<T> cls, JsonObject o) {
        var s = o.toString();
        s = "{\"la\": 123}";
        System.out.println(s);
        var obj = gson.fromJson(s, cls);
        return obj;

//        JsonReader jsonReader = javax.json.Json.createReader(new StringReader(gson.toJson(o)));
//        JsonObject object = jsonReader.readObject();
//        jsonReader.close();
//
//        return object;
    }
}
