package com.daija.location;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;

import com.baidu.mapapi.MKGeneralListener;

public class BaiduLocationManager
  implements MKGeneralListener
{
  public static BaiduLocationManager instance = null;
  private SharedPreferences sharedPreferences;
  
  public BaiduLocationManager(Context paramContext)
  {
    this.sharedPreferences = paramContext.getSharedPreferences("ILOVEMYCAR", 0);
  }
  
  public static BaiduLocationManager getInstance(Context paramContext)
  {
    if (instance == null) {
      instance = new BaiduLocationManager(paramContext);
    }
    return instance;
  }
  
  public void onGetNetworkState(int paramInt) {}
  
  public void onGetPermissionState(int paramInt) {}
  
  public void onLocationChanged(Location paramLocation)
  {
    Log.i("TAG", "location change=" + paramLocation);
    if (paramLocation == null) {
      return;
    }
    SimpleLocation simpleLocation = new SimpleLocation(paramLocation.getLongitude(), paramLocation.getLatitude());
    SharedPreferences.Editor localEditor = this.sharedPreferences.edit();
    localEditor.putFloat("LAST_POINT_X", (float)simpleLocation.getPointX());
    localEditor.putFloat("LAST_POINT_Y", (float)simpleLocation.getPointY());
    localEditor.commit();
  }
  
 public void saveLocation(Location paramLocation)
  {
    Log.i("TAG", "pointX" + paramLocation.getLongitude());
    Log.i("TAG", "pointY" + paramLocation.getLatitude());
/*    SharedPreferences.Editor localEditor = this.sharedPreferences.edit();
    localEditor.putFloat("LAST_POINT_X", (float)paramLocation.getLongitude());
    localEditor.putFloat("LAST_POINT_Y", (float)paramLocation.getLatitude());
    localEditor.commit();*/
  }
}
