package com.empress.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一数据响应对象
 *
 * @author Hystar
 * @date 2018/7/3
 */
@Data
public class JsonData {

    private boolean ret;

    private String msg;

    private Object data;

    public JsonData(boolean ret) {
        this.ret = ret;
    }

    public static JsonData success(Object obj, String msg) {
        JsonData jsonData = new JsonData(true);
        jsonData.data = obj;
        jsonData.msg = msg;
        return jsonData;
    }

    public static JsonData success(Object obj) {
        JsonData jsonData = new JsonData(true);
        jsonData.data = obj;
        return jsonData;
    }

    public static JsonData success() {
        JsonData jsonData = new JsonData(true);
        return jsonData;
    }

    public static JsonData fail(String msg) {
        JsonData jsonData = new JsonData(false);
        jsonData.msg = msg;
        return jsonData;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("ret", ret);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }
}
