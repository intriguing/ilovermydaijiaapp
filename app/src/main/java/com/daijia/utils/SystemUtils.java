package com.daijia.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class SystemUtils
{
  public static String getImageUriPath(Activity paramActivity, Uri paramUri)
  {
    Cursor paramActivityt = paramActivity.managedQuery(paramUri, new String[] { "_data" }, null, null, null);
    int i = paramActivityt.getColumnIndexOrThrow("_data");
    paramActivityt.moveToFirst();
    return paramActivity.getString(i);
  }
  
  public static String getNativePhoneNumber(Context paramContext)
  {
    return ((TelephonyManager)paramContext.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
  }
  
  public static final int getScreenHeight(Activity paramActivity)
  {
    return paramActivity.getWindowManager().getDefaultDisplay().getHeight();
  }
  
  public static final int getScreenWidth(Activity paramActivity)
  {
    return paramActivity.getWindowManager().getDefaultDisplay().getWidth();
  }
  
  public static void hideSoft(Activity paramActivity)
  {
    InputMethodManager localInputMethodManager = (InputMethodManager)paramActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
    if (localInputMethodManager != null)
    {
      View paramActivityT = paramActivity.getCurrentFocus();
      if (paramActivityT != null) {
        localInputMethodManager.hideSoftInputFromWindow(paramActivityT.getWindowToken(), 0);
      }
    }
  }
  
  public static boolean isNetworkAvailable(Context paramContext)
  {
	ConnectivityManager paramContextt = (ConnectivityManager)paramContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    int i=0;
    NetworkInfo[] paramContextttg=null;
    if (paramContext != null)
    {
      paramContextttg = paramContextt.getAllNetworkInfo();
      if (paramContextttg != null) {
        i = 0;
      }
    }
    for (;;) 
    {
      if (i >= paramContextttg.length) {
        return false;
      }
      if (paramContextttg[i].getState() == NetworkInfo.State.CONNECTED) {
        return true;
      }
      i += 1;
    }
  }
}
