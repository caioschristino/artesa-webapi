package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;

public class GsonUtils {
    public static final Gson getInstance(){
        final GsonBuilder gsonb = new GsonBuilder();
        gsonb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        final DateSerializer ds = new DateSerializer();
        gsonb.registerTypeAdapter(Date.class, ds);
        return gsonb.create();
    }

    public static final <T> T getClassFromJson(final String json, final Class<T> c){
        final Gson gson = getInstance();
        final Type tipo = TypeToken.get(c).getType();
        return gson.fromJson(json, tipo);
    }

    public static final String getJsonFromClass(final Object object){
        final Gson gson = getInstance();
        return gson.toJson(object);
    }

    public static final <T> T getClassFromJson(final String json, final Type type){
        final Gson gson = getInstance();
        return gson.fromJson(json, type);
    }
}