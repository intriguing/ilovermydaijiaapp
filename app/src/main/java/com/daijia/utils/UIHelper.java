package com.daijia.utils;


import com.example.ilovermydaijia.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UIHelper
{
  public static ProgressDialog dialog;
  
  public static void closeProgressDialog()
  {
    if ((dialog != null) && (dialog.isShowing())) {
      dialog.dismiss();
    }
    dialog = null;
  }
  
  public static void showLongTip(Context paramContext, String paramString)
  {
    View localView = ((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(R.layout.msg_tip, null);
    ((TextView)localView.findViewById(R.id.content_tv)).setText(paramString);
    Toast paramContextt = new Toast(paramContext);
    paramContextt.setView(localView);
    paramContextt.setDuration(1);
    paramContextt.show();
  }
  
  public static void showProgressDialog(Context paramContext, String paramString)
  {
    if ((dialog == null) || (!dialog.isShowing()))
    {
      dialog = new ProgressDialog(paramContext);
      dialog.setCanceledOnTouchOutside(false);
      dialog.setMessage(paramString);
      dialog.show();
    }
  }
  
  public static void showTip(Context paramContext, String paramString)
  {
    View localView = ((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(R.layout.msg_tip, null);
    ((TextView)localView.findViewById(R.id.content_tv)).setText(paramString);
    Toast paramContextt = new Toast(paramContext);
    paramContextt.setView(localView);
    paramContextt.show();
  }
}
