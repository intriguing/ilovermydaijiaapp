package com.daijia.utils; /**
 * Creation Date:2016/5/26-16:09
 * <p>
 * Copyright 2008-2016  Inc. All Rights Reserved
 */

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import java.io.File;

/**
 * Description Of The Class<br/>
 * QQ:1226109187
 *
 * @author (25059)
 * @version 1.0.0, 2016/5/26-16:09
 * @since 2016/5/26-16:09
 */
public class Upload {
    public static boolean uploadpic(String targetUrl,String fileUrl,String driverYear,String phone,String driverRange,String pointX,String pointY){
  /*      String targetUrl = "http://localhost:8080/Test";*/
        PostMethod filePost = new PostMethod(targetUrl) {//这个用来中文乱码
            public String getRequestCharSet() {
                return "UTF-8";//
            }
        };
        try {
            HttpClient client = new HttpClient();
            File file = new File(fileUrl);
            Part[] parts = new Part[] {new CustomFilePart("file", file),new StringPart("driverYear",driverYear,"utf-8"),new StringPart("phone",phone,"utf-8"),new StringPart("driverRange",driverRange,"utf-8"),new StringPart("pointX",pointX,"utf-8"),new StringPart("pointY",pointY,"utf-8")};
            filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
            int statuscode=client.executeMethod(filePost);
            if(statuscode == HttpStatus.SC_OK) {
               return true;
            } else {
               return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
