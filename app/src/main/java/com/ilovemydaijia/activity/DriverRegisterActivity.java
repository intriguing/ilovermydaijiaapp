package com.ilovemydaijia.activity;
/**
 * Creation Date:${date}-${time}
 * <p/>
 * Copyright 2008-${year} ? Inc. All Rights Reserved
 */


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daija.BaseActivity;
import com.daija.models.BaseInfo;
import com.daijia.net.JSONCallBack;
import com.daijia.net.JSONService;
import com.daijia.net.JSONServiceImpl;
import com.daijia.utils.StringUtils;
import com.daijia.utils.SystemUtils;
import com.daijia.utils.UIHelper;
import com.example.ilovermydaijia.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Description Of The Class<br/>
 * QQ:1226109187
 *
 * @author (周天晓 25059)
 * @version 1.0.0, ${date}-${time}
 * @since ${date}-${time}
 */
public class DriverRegisterActivity extends BaseActivity implements View.OnClickListener {
    private String picPath;
    private Uri photoUri=null;
    private int pointX;
    private int pointY;
    private String phone;
    private Button button;
    private EditText driverYear_et;
    private EditText driverRange_et;
    private View backBtn;
    private Button uploadbutton;
    private JSONService jsonService = new JSONServiceImpl();
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                return;
            case R.id.upload_btn:
                pickPhoto();
                return;
            case R.id.next_btn:
                SystemUtils.hideSoft(this);
                String driverYear = this.driverYear_et.getText().toString();
                if(StringUtils.isNullOrEmpty(driverYear)||Integer.parseInt(driverYear)<0||Integer.parseInt(driverYear)>100){
                    UIHelper.showTip(this, "请输入正确的驾驶年龄!");
                    return;
                }
                String driverRange=this.driverRange_et.getText().toString();
                if(StringUtils.isNullOrEmpty(driverRange)){
                    UIHelper.showTip(this, "请输入正确的一公里单价!");
                    return;
                }
                UIHelper.showProgressDialog(this, "正在进行注册...");
                List<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
                localArrayList.add(new BasicNameValuePair("phone", this.phone));
                localArrayList.add(new BasicNameValuePair("driverYear", driverYear));
                localArrayList.add(new BasicNameValuePair("driverRange", driverRange));
                localArrayList.add(new BasicNameValuePair("pointX", this.pointX+""));
                localArrayList.add(new BasicNameValuePair("pointY", this.pointY+""));
                this.jsonService.register(this.phone,driverYear,driverRange,this.pointX+"",this.pointY+"" ,picPath,new JSONCallBack(){
                    @Override
                    public void onFail() {
                        if (!SystemUtils.isNetworkAvailable(DriverRegisterActivity.this)) {
                            UIHelper.closeProgressDialog();
                            UIHelper.showTip(DriverRegisterActivity.this, DriverRegisterActivity.this.getString(R.string.net_error));
                            return;
                        }
                        UIHelper.closeProgressDialog();
                        UIHelper.showTip(DriverRegisterActivity.this, DriverRegisterActivity.this.getString(R.string.server_error));
                    }

                    @Override
                    public void onSuccess(Object paramObject) {
                        BaseInfo baseInfo = (BaseInfo) paramObject;
                        if (baseInfo.isCode()) {
                            UIHelper.closeProgressDialog();
                            UIHelper.showTip(DriverRegisterActivity.this, "注册代驾成功！");
                            DriverRegisterActivity.this.finish();
                        } else {
                            UIHelper.closeProgressDialog();
                            UIHelper.showTip(DriverRegisterActivity.this, "注册失败用户名已经被注册");
                        }
                    }
                });
                return;
        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.register_driver_info);
        this.phone = getIntent().getStringExtra("USER_PHONE");
        this.pointX = getIntent().getIntExtra("POINTX", 0);
        this.pointY = getIntent().getIntExtra("POINTY", 0);
        this.button = (Button) findViewById(R.id.next_btn);
        this.button.setOnClickListener(this);
        this.uploadbutton=(Button)findViewById(R.id.upload_btn);
        this.uploadbutton.setOnClickListener(this);
        this.driverYear_et = (EditText) findViewById(R.id.driverYear_et);
        this.driverRange_et=(EditText) findViewById(R.id.driver_DriverRange_et);
        this.backBtn = findViewById(R.id.back_btn);
        this.backBtn.setOnClickListener(this);
    }
    private void pickPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK)
        {
            doPhoto(requestCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * 选择图片后，获取图片的路径
     * @param requestCode
     * @param data
     */
    private void doPhoto(int requestCode,Intent data)
    {
        if(requestCode == 2 )  //从相册取图片，有些手机有异常情况，请注意
        {
            if(data == null)
            {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
            photoUri = data.getData();
            if(photoUri == null )
            {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
        }
        String[] pojo = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(photoUri, pojo, null, null,null);
        if(cursor != null )
        {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);
            Toast.makeText(this, "选择图片文件成功", Toast.LENGTH_LONG).show();
            cursor.close();
        }
    }

}
