package com.daija.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseInfo implements Serializable{
      private static final long serialVersionUID = 8872794255309426819L;
      boolean code;
      public BaseInfo(JSONObject paramJSONObject){
		  try {
			this.code=paramJSONObject.getBoolean("code");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	public boolean isCode() {
		return code;
	}
	public void setCode(boolean code) {
		this.code = code;
	}
}
