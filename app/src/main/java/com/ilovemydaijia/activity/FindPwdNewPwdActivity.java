package com.ilovemydaijia.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.daija.BaseActivity;
import com.daija.models.BaseInfo;
import com.daijia.net.JSONCallBack;
import com.daijia.net.JSONService;
import com.daijia.net.JSONServiceImpl;
import com.daijia.utils.StringUtils;
import com.daijia.utils.SystemUtils;
import com.daijia.utils.UIHelper;
import com.example.ilovermydaijia.R;

public class FindPwdNewPwdActivity
  extends BaseActivity
  implements View.OnClickListener
{
  public static final String MOBILE = "MOBILE";
  private View backBtn;
  private EditText newPwdEt;
  private Button okBtn;
  private EditText reNewPwdEt;
  private String mobile;
  private JSONService jsonService = new JSONServiceImpl();
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    case R.id.back_btn: 
      finish();
      return;
    default:
    String str = this.newPwdEt.getText().toString();
    String localObject = this.reNewPwdEt.getText().toString();
    if (str.length() < 6)
    {
      UIHelper.showTip(this, "密码不能小于6个字符！");
      return;
    }
    if (!str.equals(localObject))
    {
      UIHelper.showTip(this, "两次密码不一致！");
      return;
    }
    SystemUtils.hideSoft(this);
    List<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
    localArrayList.add(new BasicNameValuePair("phone", this.mobile));
    localArrayList.add(new BasicNameValuePair("password", str));
    this.jsonService.findPwdUpdate(localArrayList, new JSONCallBack()
    {
      public void onFail()
      {
        if (!SystemUtils.isNetworkAvailable(FindPwdNewPwdActivity.this.getApplicationContext()))
        {
          UIHelper.showTip(FindPwdNewPwdActivity.this.getBaseContext(), FindPwdNewPwdActivity.this.getResources().getString(R.string.net_error));
          return;
        }
        UIHelper.showTip(FindPwdNewPwdActivity.this.getBaseContext(), FindPwdNewPwdActivity.this.getResources().getString(R.string.server_error));
      }
      
      public void onSuccess(Object paramAnonymousObject)
      {
        if (((BaseInfo)paramAnonymousObject).isCode())
        {
          UIHelper.showTip(FindPwdNewPwdActivity.this.getBaseContext(), "密码修改成功！");
          FindPwdNewPwdActivity.this.finish();
          return;
        }
        UIHelper.showTip(FindPwdNewPwdActivity.this.getBaseContext(), "密码修改失败！");
      }
    });
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
	setContentView(R.layout.find_pwd_new);
    this.mobile = getIntent().getStringExtra(MOBILE);
    this.newPwdEt = ((EditText)findViewById(R.id.new_pwd_et));
    this.reNewPwdEt = ((EditText)findViewById(R.id.re_pwd_et));
    this.okBtn = ((Button)findViewById(R.id.next_btn));
    this.okBtn.setOnClickListener(this);
    this.backBtn = findViewById(R.id.back_btn);
    this.backBtn.setOnClickListener(this);
  }
}
