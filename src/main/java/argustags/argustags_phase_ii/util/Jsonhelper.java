package argustags.argustags_phase_ii.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class Jsonhelper {
    /**
     * 单个解析JSON对象
     *
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return gson对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {

        Gson gson = new Gson();
        return gson.fromJson(json, clazz);

    }

    /**
     * 单个生成
     *
     * @param target
     * @return  gson对应字符串
     */
    public static String toJson(Object target) {

        GsonBuilder gsonbuilder = new GsonBuilder();
        Gson gson = gsonbuilder.setPrettyPrinting().create();
        return gson.toJson(target);

    }
    public static <T> String listFromJson(List<T> json){

        Gson gson=new Gson();
        return gson.toJson(json);

    }
}
