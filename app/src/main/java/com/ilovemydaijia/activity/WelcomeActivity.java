package com.ilovemydaijia.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;

import com.daija.BaseActivity;
import com.example.ilovermydaijia.R;

public class WelcomeActivity
  extends BaseActivity
{
  private Handler handler = new Handler();
  
  private void start()
  {
    new Timer().schedule(new TimerTask()
    {
      public void run()
      {
        WelcomeActivity.this.handler.post(new Runnable()
        {
          public void run()
          {
           Intent localIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
            WelcomeActivity.this.startActivity(localIntent);
            WelcomeActivity.this.finish();
          }
        });
      }
    }, 5000L);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_welcome);
    start();
  }
}

