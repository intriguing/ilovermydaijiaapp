package com.daijia.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HttpUtils{
	public static final String TAG = "HttpUtils";
	public static String getHttpData(String paramString, List<NameValuePair> paramList)
	  {
	    try
	    {
	      // 目标地址  
	      HttpPost localHttpPost = new HttpPost(paramString);
	      Log.i("TAG", "url is :" + paramString);
	      if (paramList != null)
	      {
	    	// post 参数 传递 
	        localHttpPost.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
	      }
	      DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
	      HttpParams httpParams = defaultHttpClient.getParams();
	      HttpConnectionParams.setConnectionTimeout(httpParams, 60000);
	      HttpConnectionParams.setSoTimeout(httpParams, 60000);
	      HttpConnectionParams.setSocketBufferSize(httpParams, 20480);
	      HttpResponse httpResponse = defaultHttpClient.execute(localHttpPost);
	      Log.i("TAG", "response code=" + httpResponse.getStatusLine().getStatusCode());
	      if (httpResponse.getStatusLine().getStatusCode() == 200)
	      {
	    	HttpEntity httpEntity = httpResponse.getEntity();
	        if (httpEntity != null)
	        {
	          paramString = EntityUtils.toString(httpEntity);
	          System.out.println("response string ok=" + paramString);
	          return paramString;
	        }
	      }
	      else
	      {
	    	HttpEntity httpEntitys = httpResponse.getEntity();
	        if (httpEntitys != null)
	        {
	          paramString = EntityUtils.toString(httpEntitys);
	          Log.i("TAG", "response string j=" + paramString);
	          return paramString;
	        }
	      }
	    }
	    catch (Exception paramStrings)
	    {
	    	paramStrings.printStackTrace();
	    }
	    return (String)paramString;
	  }
	 public static String getHttpData2(String paramString, MultipartEntity paramMultipartEntity)
	  {
	    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
	    HttpParams localHttpParams = localDefaultHttpClient.getParams();
	    HttpConnectionParams.setConnectionTimeout(localHttpParams, 60000);
	    HttpConnectionParams.setSoTimeout(localHttpParams, 60000);
	    HttpConnectionParams.setSocketBufferSize(localHttpParams, 20480);
	    HttpPost httpPost = new HttpPost(paramString);
	    httpPost.setEntity(paramMultipartEntity);
	    try
	    {
	      HttpResponse httpResponse = localDefaultHttpClient.execute(httpPost);
	      if (httpResponse.getStatusLine().getStatusCode() == 200)
	      {
	        HttpEntity httpEntity = httpResponse.getEntity();
	        paramString = "";
	        if (httpEntity != null) {
	          paramString = EntityUtils.toString(httpEntity);
	        }
	        return paramString;
	      }
	      return null;
	    }
	    catch (ClientProtocolException clientProtocolException)
	    {
	     clientProtocolException.printStackTrace();
	      return null;
	    }
	    catch (IOException iOException)
	    {
	    	iOException.printStackTrace();
	    	return null;
	    }
	  }
	  
	  public static InputStream getInputStream(String paramString) throws ProtocolException
	  {
	    try
	    {
	      URL url = new URL(paramString);
	      HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
	      httpURLConnection.setDoInput(true);
	      httpURLConnection.setConnectTimeout(30000);
	      httpURLConnection.setRequestMethod("GET");
	      httpURLConnection.connect();
	      if (httpURLConnection.getResponseCode() != 200) {
	    	  InputStream inputStream = httpURLConnection.getInputStream();
	    	  return inputStream;
	      }
	    }
	    catch (MalformedURLException malformedURLException)
	    {
	    	malformedURLException.printStackTrace();
	    }
	    catch (IOException iOException)
	    {
	    	iOException.printStackTrace();
	    }
		return null;
	  }
	  public static Bitmap getNetBitmap(String paramString)
	  {
	    try
	    {
	      Bitmap bitmap = BitmapFactory.decodeStream(getInputStream(paramString));
	      return bitmap;
	    }
	    catch (Exception exception)
	    {
	    	exception.printStackTrace();
	    }
	    return null;
	  }
}

