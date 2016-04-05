package com.daijia.net;


import java.io.File;
import java.util.List;
import org.apache.http.NameValuePair;


public interface JSONService
{ 
  public  void findPwdUpdate(List<NameValuePair> paramList, JSONCallBack paramJSONCallBack);
  
  public  void getDriverComments(List<NameValuePair> paramList, JSONCallBack paramJSONCallBack);
  
  public  void getDrivers(JSONCallBack paramJSONCallBack);
  
  public  void login(List<NameValuePair> paramList, JSONCallBack paramJSONCallBack);
  
  public  void postDriverComment(List<NameValuePair> paramList, JSONCallBack paramJSONCallBack);
  
  public  void register(List<NameValuePair> paramList, File paramFile, JSONCallBack paramJSONCallBack);
  
  public  void searchNearDriver(List<NameValuePair> paramList, JSONCallBack paramJSONCallBack);
  
  public  void sendFindPwdCode(List<NameValuePair> paramList, JSONCallBack paramJSONCallBack);
  
  public  void sendRegCode(List<NameValuePair> paramList, JSONCallBack paramJSONCallBack);
  
  public  void updatePwd(List<NameValuePair> paramList, JSONCallBack paramJSONCallBack);
  
  public  void updateUserInfo(List<NameValuePair> paramList, File paramFile, JSONCallBack paramJSONCallBack);
  
  public void saveuserinfo(List<NameValuePair> localArrayList,JSONCallBack jsonCallBack);

}
