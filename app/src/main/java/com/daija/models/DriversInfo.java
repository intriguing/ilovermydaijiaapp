package com.daija.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DriversInfo {
private List<DriverInfo> driver;
private int PageIndex;
private int TotalPage;
@SuppressWarnings("unchecked")
public DriversInfo(JSONObject jsonObject) throws JSONException {
	// TODO Auto-generated constructor stub
	this.driver=new ArrayList<DriverInfo>();
    JSONArray array=jsonObject.getJSONArray("DriversInfo");
    for (int i = 0; i < array.length(); i++) {
		JSONObject ob = new JSONObject();
		ob = (JSONObject) array.get(i);
		DriverInfo driverinfo=new DriverInfo(ob);
		this.driver.add(driverinfo);

	}
	this.PageIndex=jsonObject.getInt("PageIndex");
	this.TotalPage=jsonObject.getInt("TotalPage");
}
public List<DriverInfo> getDriver() {
	return driver;
}
public void setDriver(List<DriverInfo> driver) {
	this.driver = driver;
}
public int getPageIndex() {
	return PageIndex;
}
public void setPageIndex(int pageIndex) {
	PageIndex = pageIndex;
}
public int getTotalPage() {
	return TotalPage;
}
public void setTotalPage(int totalPage) {
	TotalPage = totalPage;
}
}
