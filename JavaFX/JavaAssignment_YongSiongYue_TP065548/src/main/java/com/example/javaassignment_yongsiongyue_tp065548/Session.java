package com.example.javaassignment_yongsiongyue_tp065548;

import java.util.HashMap;

public class Session {
    private static Session instance;
    private final HashMap<String, Object> data;

    private Session() {
        data = new HashMap<>();
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void set(String key, Object value) {
        data.put(key, value);
    }

    public Object get(String key) {
        return data.get(key);
    }

    public void remove(String key) {
        data.remove(key);
    }

    public void clear() {
        data.clear();
    }

}
