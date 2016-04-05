package com.daija.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class DriverInfo implements Serializable {
	private static final long serialVersionUID = 8872794255309426819L;
	private int userid;
	private String name;
	private String pass;
	private String phone;
	private String infor;
	private int status;
	private int sex;
	private int driverrange;
	private int drivingYears;
	private int pointX;
	private int pointY;
	public DriverInfo(JSONObject paramJSONObject) {
		try {
			this.userid=paramJSONObject.getInt("id");
			this.name = paramJSONObject.getString("name");
			this.sex=paramJSONObject.getInt("sex");
			this.pass=paramJSONObject.getString("pass");
			this.phone=paramJSONObject.getString("phone");
			this.infor=paramJSONObject.getString("infor");
			this.status=paramJSONObject.getInt("status");
			this.driverrange=paramJSONObject.getInt("driverrange");
			this.drivingYears=paramJSONObject.getInt("drivingYears");
			this.pointX=paramJSONObject.getInt("pointX");
			this.pointY=paramJSONObject.getInt("pointY");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getPointX() {
		return pointX;
	}

	public void setPointX(int pointX) {
		this.pointX = pointX;
	}

	public int getPointY() {
		return pointY;
	}

	public void setPointY(int pointY) {
		this.pointY = pointY;
	}


	public int getDrivingYears() {
		return drivingYears;
	}

	public void setDrivingYears(int drivingYears) {
		this.drivingYears = drivingYears;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDriverrange() {
		return driverrange;
	}

	public void setDriverrange(int driverrange) {
		this.driverrange = driverrange;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}


	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}
}
