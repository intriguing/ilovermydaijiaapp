package com.daija.ui;


import com.example.ilovermydaijia.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

public class BaseDialog
  extends Dialog
{
  public BaseDialog(Context paramContext, View paramView)
  {
    super(paramContext, R.style.Dialog);
    setContentView(paramView);
  }
}