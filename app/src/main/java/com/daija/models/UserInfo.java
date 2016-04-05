package com.daija.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInfo implements Serializable{
	  private static final long serialVersionUID = 8872794255309426814L;
	  private String name;
	  private String pass;
	  private String phone;
	  private String infor;
	  public UserInfo(JSONObject paramJSONObject){
		  try {
			this.name=paramJSONObject.getString("3");
			this.pass=paramJSONObject.getString("4");
			this.phone=paramJSONObject.getString("5");
			this.infor=paramJSONObject.getString("6");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getInfor() {
		return infor;
	}
	public void setInfor(String infor) {
		this.infor = infor;
	}
}
