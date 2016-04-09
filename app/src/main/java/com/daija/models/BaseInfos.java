package com.daija.models;
/**
 * Creation Date:${date}-${time}
 * <p/>
 * Copyright 2008-${year} ? Inc. All Rights Reserved
 */

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Description Of The Class<br/>
 * QQ:1226109187
 *
 * @author (周天晓 25059)
 * @version 1.0.0, ${date}-${time}
 * @since ${date}-${time}
 */
public class BaseInfos implements Serializable {
    private static final long serialVersionUID = 8872794255309426419L;
    boolean code;
    private int demo;
    public BaseInfos(JSONObject paramJSONObject){
        try {
            this.code=paramJSONObject.getBoolean("code");
            this.demo=paramJSONObject.getInt("demo");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public boolean isCode() {
        return code;
    }
    public int getDemo() {
        return demo;
    }
}