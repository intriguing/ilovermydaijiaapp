package com.daija;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class ILoveMyCarApp
  extends Application
{
	
    private static ILoveMyCarApp mInstance = null;
    public boolean m_bKeyRight = true;
    public BMapManager mBMapManager = null;

    public static final String strKey = "2z4vC7opN2zVkgaOZDkEm9LYlCNPbjB5";
	
	@Override
    public void onCreate() {
	    super.onCreate();
		mInstance = this;
		initEngineManager();
	}
	
     public void initEngineManager() {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(this);
        }

        if (!mBMapManager.init(strKey,new MyGeneralListener())) {
            Toast.makeText(ILoveMyCarApp.getInstance().getApplicationContext(), 
                    "BMapManager  初始化失败!", Toast.LENGTH_LONG).show();
        }
	}
	
	public static ILoveMyCarApp getInstance() {
		return mInstance;
	}
	
	
	// 常用事件监听，用来处理通常的网络错误，授权验证错误！
    public static class MyGeneralListener implements MKGeneralListener {
        
        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
                Toast.makeText(ILoveMyCarApp.getInstance().getApplicationContext(), "您的网络出错了",
                    Toast.LENGTH_LONG).show();
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                Toast.makeText(ILoveMyCarApp.getInstance().getApplicationContext(), "输入正确的检索条件",
                        Toast.LENGTH_LONG).show();
            }
            // ...
        }

        @Override
        public void onGetPermissionState(int iError) {
        	//非零表示key验证未通过
            if (iError != 0) {
                //授权key错误
                Toast.makeText(ILoveMyCarApp.getInstance().getApplicationContext(), 
                        "请输入正确的key "+iError, Toast.LENGTH_LONG).show();
                ILoveMyCarApp.getInstance().m_bKeyRight = false;
            }
            else{
            	ILoveMyCarApp.getInstance().m_bKeyRight = true;
            	Toast.makeText(ILoveMyCarApp.getInstance().getApplicationContext(), 
                        "key认证成功", Toast.LENGTH_LONG).show();
            }
        }
    }
}