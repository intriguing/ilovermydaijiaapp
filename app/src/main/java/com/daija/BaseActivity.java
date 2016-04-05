package com.daija;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity
  extends Activity
{
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    AppManager.getAppManager().addActivity(this);
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    AppManager.getAppManager().finishActivity(this);
  }
}
