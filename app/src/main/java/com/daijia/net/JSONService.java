package com.daijia.net;


import java.io.File;
import java.util.List;
import org.apache.http.NameValuePair;


public interface JSONService
{ 
  void findPwdUpdate(List<NameValuePair> paramList, JSONCallBack paramJSONCallBack);
  
   void getDriverComments(List<NameValuePair> paramList, JSONCallBack paramJSONCallBack);
  
  void getDrivers(JSONCallBack paramJSONCallBack);
  
  void login(List<NameValuePair> paramList, JSONCallBack paramJSONCallBack);
  
  void postDriverComment(List<NameValuePair> paramList, JSONCallBack paramJSONCallBack);
  
  void register(List<NameValuePair> paramList, JSONCallBack paramJSONCallBack);
  
  void searchNearDriver(List<NameValuePair> paramList, JSONCallBack paramJSONCallBack);
  
  void sendFindPwdCode(List<NameValuePair> paramList, JSONCallBack paramJSONCallBack);
  
  void sendRegCode(List<NameValuePair> paramList, JSONCallBack paramJSONCallBack);
  
  void updatePwd(List<NameValuePair> paramList, JSONCallBack paramJSONCallBack);
  
  void updateUserInfo(List<NameValuePair> paramList, File paramFile, JSONCallBack paramJSONCallBack);
  
  void saveuserinfo(List<NameValuePair> localArrayList,JSONCallBack jsonCallBack);
  void changeDriver(List<NameValuePair> localArrayList,JSONCallBack jsonCallBack);
}
