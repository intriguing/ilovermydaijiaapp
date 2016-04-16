package com.daija.models;
/**
 * Creation Date:${date}-${time}
 * <p/>
 * Copyright 2008-${year} ? Inc. All Rights Reserved
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Description Of The Class<br/>
 * QQ:1226109187
 *
 * @author (周天晓 25059)
 * @version 1.0.0, ${date}-${time}
 * @since ${date}-${time}
 */
public class CommentVosInfo {
    private List<CommentVoInfo> commentVoInfoList;

    public List<CommentVoInfo> getCommentVoInfoList() {
        return commentVoInfoList;
    }

    public CommentVosInfo(JSONObject jsonObject)throws JSONException {
        // TODO Auto-generated constructor stub
        this.commentVoInfoList=new ArrayList<CommentVoInfo>();
        JSONArray array=jsonObject.getJSONArray("CommentVosInfo");
        for (int i = 0; i < array.length(); i++) {
            JSONObject ob = (JSONObject) array.get(i);
            CommentVoInfo commentVoInfo=new CommentVoInfo(ob);
            this.commentVoInfoList.add(commentVoInfo);

        }

    }
}
