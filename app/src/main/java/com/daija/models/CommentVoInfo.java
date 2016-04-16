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
public class CommentVoInfo  implements Serializable {
    private static final long serialVersionUID = 48L;
    private String userName;
    private String comment;
    private String starLevel;
    public CommentVoInfo(JSONObject paramJSONObject){
        try {
            this.userName=paramJSONObject.getString("userName");
            this.comment=paramJSONObject.getString("comment");
            this.starLevel=paramJSONObject.getString("starLevel");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(String starLevel) {
        this.starLevel = starLevel;
    }
}
