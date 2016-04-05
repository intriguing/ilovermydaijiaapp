package com.ilovemydaijia.activity;

import android.content.Intent;
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

public class RegisterInputMobileActivity
        extends BaseActivity
        implements View.OnClickListener {
    private View backBtn;
    private Button button;
    private EditText mobileEt;
    private EditText passEt;
    private EditText infoEt;
    private EditText userEt;
    private EditText sexEt;
    private JSONService jsonService = new JSONServiceImpl();

    public void onClick(final View paramView) {
        switch (paramView.getId()) {
            case R.id.back_btn:
                finish();
                return;
            case R.id.next_btn:
                SystemUtils.hideSoft(this);
                String string = this.mobileEt.getText().toString();
                String pass = this.passEt.getText().toString();
                String info = this.infoEt.getText().toString();
                String user = this.userEt.getText().toString();
                String sex  = this.sexEt.getText().toString();
                if (!StringUtils.isMobile(string)) {
                    UIHelper.showTip(this, "请输入正确的手机号码!");
                    return;
                }
                UIHelper.showProgressDialog(this, "正在进行注册...");
                List<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
                localArrayList.add(new BasicNameValuePair("phone", string));
                localArrayList.add(new BasicNameValuePair("name", user));
                localArrayList.add(new BasicNameValuePair("pass", pass));
                localArrayList.add(new BasicNameValuePair("sex", sex));
                localArrayList.add(new BasicNameValuePair("info", info));
                this.jsonService.saveuserinfo(localArrayList, new JSONCallBack() {
                    public void onFail() {
                        if (!SystemUtils.isNetworkAvailable(RegisterInputMobileActivity.this)) {
                            UIHelper.closeProgressDialog();
                            UIHelper.showTip(RegisterInputMobileActivity.this, RegisterInputMobileActivity.this.getString(R.string.net_error));
                            return;
                        }
                        UIHelper.closeProgressDialog();
                        UIHelper.showTip(RegisterInputMobileActivity.this, RegisterInputMobileActivity.this.getString(R.string.server_error));
                    }

                    public void onSuccess(Object paramAnonymousObject) {
                        BaseInfo baseInfo = (BaseInfo) paramAnonymousObject;
                        if (baseInfo.isCode()) {
                            UIHelper.closeProgressDialog();
                            UIHelper.showTip(RegisterInputMobileActivity.this, "注册成功！");
                            paramAnonymousObject = new Intent(RegisterInputMobileActivity.this, LoginActivity.class);
                            RegisterInputMobileActivity.this.startActivity((Intent) paramAnonymousObject);
                        } else {
                            UIHelper.closeProgressDialog();
                            UIHelper.showTip(RegisterInputMobileActivity.this, "注册失败用户名已经被注册");
                        }
                    }
                });
        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.register_input_mobile);
        this.mobileEt = ((EditText) findViewById(R.id.mobile_et));
        this.button = ((Button) findViewById(R.id.next_btn));
        this.button.setOnClickListener(this);
        this.backBtn = findViewById(R.id.back_btn);
        this.backBtn.setOnClickListener(this);
        this.passEt = (EditText) findViewById(R.id.pass_et);
        this.userEt = (EditText) findViewById(R.id.user_etd);
        this.infoEt = (EditText) findViewById(R.id.info_et);
        this.sexEt = (EditText) findViewById(R.id.sex_et);
    }
}
