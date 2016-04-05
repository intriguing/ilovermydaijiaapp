package com.daijia.net;

public abstract interface JSONCallBack
{
  public abstract void onFail();
  
  public abstract void onSuccess(Object paramObject);
}
