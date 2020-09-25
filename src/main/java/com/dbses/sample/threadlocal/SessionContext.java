package com.dbses.sample.threadlocal;

import com.alibaba.fastjson.JSONObject;

public class SessionContext {

    private static final ThreadLocal<JSONObject> context = ThreadLocal.withInitial(JSONObject::new);

    private SessionContext() {
    }

    public static String getDbName() {
        return context.get().getString("dbName");
    }

    public static void setDbName(String dbName) {
        context.get().put("dbName", dbName);
    }

}
