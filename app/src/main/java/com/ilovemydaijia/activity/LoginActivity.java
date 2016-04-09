package com.ilovemydaijia.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.daija.BaseActivity;
import com.daija.models.BaseInfo;
import com.daija.models.BaseInfos;
import com.daijia.net.JSONCallBack;
import com.daijia.net.JSONService;
import com.daijia.net.JSONServiceImpl;
import com.daijia.utils.StringUtils;
import com.daijia.utils.SystemUtils;
import com.daijia.utils.UIHelper;
import com.example.ilovermydaijia.R;
import com.umeng.analytics.MobclickAgent;
/*import com.daija.ILoveMyCarApp;
import com.daija.db.UserDao;
import com.daija.db.UserEntity;
import com.daija.models.BaseInfo;
import com.daijia.net.JSONCallBack;
import com.daijia.net.JSONService;
import com.daijia.net.JSONServiceImpl;
import com.daijia.utils.StringUtils;
import com.daijia.utils.SystemUtils;
import com.daijia.utils.UIHelper;
import com.ilovemydaijia.daijia.R;
import com.umeng.analytics.MobclickAgent;*/

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class LoginActivity
  extends BaseActivity
  implements View.OnClickListener
{
  private View backBtn;
  private TextView forgetPwdTv;
  private EditText mobileEt;
  private Button okBtn;
  private EditText passwordEt;
  private Button registerBtn;
  private   String paramViewt;
 private JSONService jsonService = new JSONServiceImpl();
/* private ILoveMyCarApp iLoveMyCarApp;
  private String mobile;
  private SharedPreferences sharedPreferences;
  private UserDao userDao = new UserDao();
  */
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    case R.id.forget_pwd_tv:
      startActivity(new Intent(this, FindPwdInputMobileActivity.class));
      return;
    case R.id.back_btn: 
      finish();
    case R.id.register_btn:
      startActivity(new Intent(this, RegisterInputMobileActivity.class));
      return;
   default:
    SystemUtils.hideSoft(this);
    paramViewt = this.mobileEt.getText().toString();
    final String str = this.passwordEt.getText().toString();
    if (!StringUtils.isMobile(paramViewt))
    {
      UIHelper.showTip(this, "请输入正确的手机号码!");
      return;
    }
    if (StringUtils.isNullOrEmpty(str))
    {
      UIHelper.showTip(this, "请输入正确的密码!");
      return;
    }
    UIHelper.showProgressDialog(this, "正在登陆...");
    List<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
    localArrayList.add(new BasicNameValuePair("name", paramViewt));
    localArrayList.add(new BasicNameValuePair("pass", str));
    this.jsonService.login(localArrayList, new JSONCallBack()
    {
      public void onFail()
      {
        
        if (!SystemUtils.isNetworkAvailable(LoginActivity.this))
        {
          UIHelper.showTip(LoginActivity.this, LoginActivity.this.getResources().getString(R.string.net_error));
          return;
        }
        UIHelper.closeProgressDialog();
        UIHelper.showTip(LoginActivity.this, LoginActivity.this.getResources().getString(R.string.server_error));
      }
      
      public void onSuccess(Object paramAnonymousObject)
      {
    	BaseInfos baseInfo = (BaseInfos)paramAnonymousObject;
        if(baseInfo.isCode()){
          UIHelper.closeProgressDialog();
          UIHelper.showTip(LoginActivity.this, "登陆成功!");
          Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("USER_PHONE",paramViewt);
            intent.putExtra("DEMO",baseInfo.getDemo());
          LoginActivity.this.startActivity(intent);
        }else{
          UIHelper.closeProgressDialog();
          UIHelper.showTip(LoginActivity.this, "用户名或密码错误!");
          return;
        }
/*        UIHelper.closeProgressDialog();
        UIHelper.showTip(LoginActivity.this, "参数错误!");*/
      }});
    }
  }
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_login);
    this.forgetPwdTv = ((TextView)findViewById(R.id.forget_pwd_tv));
    this.forgetPwdTv.setOnClickListener(this);
    this.mobileEt = ((EditText)findViewById(R.id.mobile_et));
    this.passwordEt = ((EditText)findViewById(R.id.password_et));
    this.okBtn = ((Button)findViewById(R.id.login_btn));
    this.okBtn.setOnClickListener(this);
    this.registerBtn = ((Button)findViewById(R.id.register_btn));
    this.registerBtn.setOnClickListener(this);
    this.backBtn = findViewById(R.id.back_btn);
    this.backBtn.setOnClickListener(this);
 /*   this.sharedPreferences = getSharedPreferences("ILOVEMYCAR", 0);
    this.iLoveMyCarApp = ((ILoveMyCarApp)getApplication());
    this.mobile = this.sharedPreferences.getString("FULL_NAME", "");
    this.password = this.sharedPreferences.getString("PASSWORD", "");
    if (!StringUtils.isNullOrEmpty(this.mobile))
    {
      this.mobileEt.setText(this.mobile);
      this.passwordEt.setText(this.password);
    }*/
  }

  
 protected void onPause()
  {
    super.onPause();
    MobclickAgent.onPause(this);
  }
  
  protected void onResume()
  {
    super.onResume();
    MobclickAgent.onResume(this);
  }
}
