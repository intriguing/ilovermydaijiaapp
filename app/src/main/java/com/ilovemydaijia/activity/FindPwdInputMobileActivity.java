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


public class FindPwdInputMobileActivity
        extends BaseActivity
        implements View.OnClickListener {
    private View backBtn;
    private EditText editText;
    private EditText editTexts;
    private Button nextBtn;
    private String phone;
    private JSONService jsonService = new JSONServiceImpl();

    public void onClick(final View paramView) {
        switch (paramView.getId()) {
            case R.id.back_btn:
                finish();
                return;
            default:
                phone=this.editTexts.getText().toString();
                String string = this.editText.getText().toString();
                if (StringUtils.isNullOrEmpty(string)) {
                    UIHelper.showTip(this, "请输入注册时的用户信息！");
                    return;
                }
                SystemUtils.hideSoft(this);
                UIHelper.showProgressDialog(this, "正在验证信息...");
                List<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
                localArrayList.add(new BasicNameValuePair("phone", phone));
                localArrayList.add(new BasicNameValuePair("info", string));
                this.jsonService.sendFindPwdCode(localArrayList, new JSONCallBack() {
                    public void onFail() {

                        if (!SystemUtils.isNetworkAvailable(FindPwdInputMobileActivity.this.getBaseContext())) {
                            UIHelper.showTip(FindPwdInputMobileActivity.this.getBaseContext(), FindPwdInputMobileActivity.this.getResources().getString(R.string.net_error));
                            return;
                        }
                        UIHelper.showTip(FindPwdInputMobileActivity.this.getBaseContext(), FindPwdInputMobileActivity.this.getResources().getString(R.string.server_error));
                    }

                    public void onSuccess(Object paramAnonymousObject) {
                        UIHelper.closeProgressDialog();
                        BaseInfo baseInfo = (BaseInfo) paramAnonymousObject;
                        if (baseInfo.isCode()) {
                            UIHelper.showTip(FindPwdInputMobileActivity.this.getBaseContext(), "验证成功！");
                            Intent intent = new Intent(FindPwdInputMobileActivity.this, FindPwdNewPwdActivity.class);
                            intent.putExtra("MOBILE",phone);
                            FindPwdInputMobileActivity.this.startActivity(intent);
                            FindPwdInputMobileActivity.this.finish();
                        } else {
                            UIHelper.showTip(FindPwdInputMobileActivity.this.getBaseContext(), "验证失败！");
                        }
                    }
                });
        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.find_pwd_input_mobile);
        this.backBtn = findViewById(R.id.back_btn);
        this.backBtn.setOnClickListener(this);
        this.editText = ((EditText) findViewById(R.id.info_ets));
        this.editTexts = ((EditText) findViewById(R.id.mobile_et));
        this.nextBtn = ((Button) findViewById(R.id.next_btn));
        this.nextBtn.setOnClickListener(this);
    }
}
