package ru.sabah.struktura.handlers.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.io.StringReader;

@ApplicationScoped
public class Json {
//    private static final Gson gson = new Gson();

//    public JsonObject from(Object o) {
//        JsonReader jsonReader = javax.json.Json.createReader(new StringReader(gson.toJson(o)));
//        JsonObject object = jsonReader.readObject();
//        jsonReader.close();

//        return object;
//    }

//    public <T> T to(Class<T> cls, JsonObject o) {
//        var s = o.toString();
//        s = "{\"la\": 123}";
//        System.out.println(s);
//        var obj = gson.fromJson(s, cls);
//        return obj;

//        JsonReader jsonReader = javax.json.Json.createReader(new StringReader(gson.toJson(o)));
//        JsonObject object = jsonReader.readObject();
//        jsonReader.close();
//
//        return object;
//    }
}
