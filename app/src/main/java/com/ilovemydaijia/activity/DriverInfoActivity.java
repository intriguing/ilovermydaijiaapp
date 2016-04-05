package com.ilovemydaijia.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.daija.BaseActivity;
import com.daija.models.DriverInfo;
import com.daijia.net.JSONCallBack;
import com.daijia.net.JSONService;
import com.daijia.net.JSONServiceImpl;
import com.daijia.utils.StringUtils;
import com.daijia.utils.SystemUtils;
import com.daijia.utils.UIHelper;
import com.example.ilovermydaijia.R;

public class DriverInfoActivity
  extends BaseActivity
  implements View.OnClickListener
{
  public static final String DRIVER_INFO = "DRIVER_INFO";
  public static final String SHAKE_USER = "SHAKE_USER";
  private static final String TAG = "DriverInfoActivity";
  private String apiKey = null;
  private View backBtn;
  private Button callBtn;
/*  private CommentAdapter commentAdapter;*/
/*  private TextView companyTv;*/
  private Drawable defaultHead;
/*  private TextView designatedDrivingNumTv;*/
  private TextView distanceTv;
  private DriverInfo driver;
  private EditText driverCommentEt;
  private TextView drivingYearsTv;
  private View footerView;
  private ImageView headView;
  private JSONService jsonService = new JSONServiceImpl();
  private Button locationBtn;
  private String mobile;
  private Button moreBtn;
  private TextView nameTv;
/*  private TextView nativePlaceTv;*/
  private int pageIndex = 1;
  private Button postCommentBtn;
/*  private boolean refresh = false;*/
  private ImageView sexIv;
  private TextView sexTv;
/*  private SharedPreferences sharedPreferences;*/
  private boolean star1State = true;
  private boolean star2State = true;
  private boolean star3State = true;
  private boolean star4State = true;
  private boolean star5State = true;
  private Button starRankBtn1;
  private Button starRankBtn2;
  private Button starRankBtn3;
  private Button starRankBtn4;
  private Button starRankBtn5;
  private LinearLayout starWarpView;
  private TextView stateTv;
  private int totalPage = 1;
  int tryTimes = 0;
  private int userId = -1;
  
/*  private ImageView createStarView()
  {
    ImageView localImageView = new ImageView(this);
    localImageView.setImageResource(R.drawable.star);
    return localImageView;
  }*/
  
  private void init()
  {
    String str;
    ImageView localImageView;
    if (this.driver != null)
    {
      this.mobile = this.driver.getPhone();
      this.nameTv.setText(this.driver.getName());
/*      this.bitmapManager.loadBitmap(this.driver.head, this.headView, this.defaultHead);*/
      switch (this.driver.getStatus())
      {
    	case 1:      
    	this.stateTv.setBackgroundResource(R.drawable.state_online);
        this.stateTv.setText("空闲");
/*        this.starWarpView.removeAllViews();*/
        this.drivingYearsTv.setText("驾龄：" + this.driver.getDrivingYears());
/*          this.designatedDrivingNumTv.setText("代驾次数：" + this.driver.get);*/
        this.distanceTv.setText("距离我：" + StringUtils.getLocDesc(this.driver.getDriverrange()));
        if (this.driver.getSex() != 1) {
            str = "男";
            localImageView = this.sexIv;
            localImageView.setImageResource(R.drawable.male);
        }else{
            str = "女";
        }
        this.sexTv.setText("性别:" + str);
/*        this.starWarpView.addView(createStarView());*/
        break;
    	case 2:
    	      this.stateTv.setBackgroundResource(R.drawable.state_offine);
    	      this.stateTv.setText("离线");
/*    	        this.starWarpView.removeAllViews();*/
    	          this.drivingYearsTv.setText("驾龄：" + this.driver.getDrivingYears());
    	/*          this.designatedDrivingNumTv.setText("代驾次数：" + this.driver.get);*/
    	          this.distanceTv.setText("距离我：" + StringUtils.getLocDesc(this.driver.getDriverrange()));
    	          if (this.driver.getSex() != 1) {
    	              str = "男";
    	              localImageView = this.sexIv;
    	              localImageView.setImageResource(R.drawable.male);
    	          }else{
    	              str = "女";
    	          }
    	          this.sexTv.setText("性别:" + str);
/*    	          this.starWarpView.addView(createStarView());*/
    	      break;
    	case 3:
    	      this.stateTv.setBackgroundResource(R.drawable.state_busy);
    	      this.stateTv.setText("忙碌");
/*    	        this.starWarpView.removeAllViews();*/
    	          this.drivingYearsTv.setText("驾龄：" + this.driver.getDrivingYears());
    	/*          this.designatedDrivingNumTv.setText("代驾次数：" + this.driver.get);*/
    	          this.distanceTv.setText("距离我：" + StringUtils.getLocDesc(this.driver.getDriverrange()));
    	          if (this.driver.getSex() != 1) {
    	              str = "男";
    	              localImageView = this.sexIv;
    	              localImageView.setImageResource(R.drawable.male);
    	          }else{
    	              str = "女";
    	          }
    	          this.sexTv.setText("性别:" + str);
    	      /*    this.starWarpView.addView(createStarView());*/
    	      break;

        }
      }


    }
 /* 
  private void loadDatas()
  {
    if (this.driver != null)
    {
      int i = this.driver.getUserid();
      ArrayList<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
      localArrayList.add(new BasicNameValuePair("pageIndex", String.valueOf(this.pageIndex)));
      localArrayList.add(new BasicNameValuePair("driverId", String.valueOf(i)));
      this.jsonService.getDriverComments(localArrayList, new JSONCallBack()
      {
        public void onFail()
        {
          DriverInfoActivity.this.driverCommentList.onRefreshComplete();
        }
        
        public void onSuccess(Object paramAnonymousObject)
        {
          DriverInfoActivity.this.driverCommentList.onRefreshComplete();
          paramAnonymousObject = (GetDriverComments)paramAnonymousObject;
          if (DriverInfoActivity.this.refresh) {
            DriverInfoActivity.this.comments.clear();
          }
          DriverInfoActivity.this.refresh = false;
          DriverInfoActivity.this.comments.addAll(((GetDriverComments)paramAnonymousObject).getComments());
          DriverInfoActivity.this.commentAdapter.notifyDataSetChanged();
          DriverInfoActivity.this.pageIndex = ((GetDriverComments)paramAnonymousObject).getPageIndex();
          DriverInfoActivity.this.totalPage = ((GetDriverComments)paramAnonymousObject).getTotalPage();
          if (DriverInfoActivity.this.pageIndex < DriverInfoActivity.this.totalPage)
          {
            DriverInfoActivity.this.moreBtn.setEnabled(true);
            DriverInfoActivity.this.moreBtn.setText("��������");
            DriverInfoActivity.this.driverCommentList.showFooterView();
            return;
          }
          DriverInfoActivity.this.driverCommentList.hideFooterView();
          DriverInfoActivity.this.moreBtn.setEnabled(false);
        }
      });
    }
  }*/
  
/*  private void reportCallLog(final int paramInt1, final int paramInt2)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new BasicNameValuePair("fromUserId", String.valueOf(paramInt1)));
    localArrayList.add(new BasicNameValuePair("toUserId", String.valueOf(paramInt2)));
    localArrayList.add(new BasicNameValuePair("apiKey", String.valueOf(this.apiKey)));
    String str = SystemUtils.getNativePhoneNumber(this);
    if (!StringUtils.isNullOrEmpty(str)) {}
    for (;;)
    {
      Log.i("DriverInfoActivity", "mobile=" + str);
      localArrayList.add(new BasicNameValuePair("mobile", String.valueOf(str)));
      this.jsonService.reportCallLog(localArrayList, new JSONCallBack()
      {
        public void onFail()
        {
          if (DriverInfoActivity.this.tryTimes < 200)
          {
            DriverInfoActivity localDriverInfoActivity = DriverInfoActivity.this;
            localDriverInfoActivity.tryTimes += 1;
            DriverInfoActivity.this.reportCallLog(paramInt1, paramInt2);
          }
        }
        
        public void onSuccess(Object paramAnonymousObject)
        {
          if (((ReportCallLog)paramAnonymousObject).getStatus() == 1) {
            System.out.println("windows");
          }
          while (DriverInfoActivity.this.tryTimes >= 200) {
            return;
          }
          paramAnonymousObject = DriverInfoActivity.this;
          ((DriverInfoActivity)paramAnonymousObject).tryTimes += 1;
          DriverInfoActivity.this.reportCallLog(paramInt1, paramInt2);
        }
      });
      return;
      str = "";
    }
  }*/
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
/*    case R.id.star_rank_btn1: 
    default: 
    case R.id.post_comment_btn: 
    case R.id.star_rank_btn2: 
    case R.id.star_rank_btn3: 
    case R.id.star_rank_btn4: 
    case R.id.star_rank_btn5: */
    case R.id.back_btn: this.finish();break;
    case R.id.location_btn:     Intent tintentg = new Intent(this, DriverLocationViewActivity.class);
     tintentg.putExtra("DRIVER_INFO", this.driver);
    startActivity(tintentg);break;
    case R.id.call_btn: 
    	if(!StringUtils.isNullOrEmpty(this.mobile)){
    /*      reportCallLog(this.userId, this.driver.getUserid());*/
    startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + this.mobile)));}return;
/*      do
      {
        return;
        SystemUtils.hideSoft(this);
        if ((this.userId != -1) && (this.apiKey != null))
        {
          String paramViewg = this.driverCommentEt.getText().toString();
          if (!StringUtils.isNullOrEmpty(paramViewg))
          {
            int j = 0;
            if (this.star1State) {
              j = 0 + 1;
            }
            int i = j;
            if (this.star2State) {
              i = j + 1;
            }
            j = i;
            if (this.star3State) {
              j = i + 1;
            }
            i = j;
            if (this.star4State) {
              i = j + 1;
            }
            j = i;
            if (this.star5State) {
              j = i + 1;
            }
            UIHelper.showProgressDialog(this, "正在发表评论...");
            ArrayList localArrayList = new ArrayList();
            localArrayList.add(new BasicNameValuePair("userId", String.valueOf(this.userId)));
            localArrayList.add(new BasicNameValuePair("driverId", String.valueOf(this.driver.getUserid())));
            localArrayList.add(new BasicNameValuePair("content", String.valueOf(paramView)));
            localArrayList.add(new BasicNameValuePair("starLevel", String.valueOf(j)));
            localArrayList.add(new BasicNameValuePair("apiKey", String.valueOf(this.apiKey)));
            this.jsonService.postDriverComment(localArrayList, new JSONCallBack()
            {
              public void onFail()
              {
                UIHelper.closeProgressDialog();
                UIHelper.showTip(DriverInfoActivity.this, "评论失败");
              }
              
              public void onSuccess(Object paramAnonymousObject)
              {
                
                if (((PostDriverComment)paramAnonymousObject).getStatus() == 1)
                {
                  UIHelper.showTip(DriverInfoActivity.this, "评论成功");
                  DriverInfoActivity.this.driverCommentEt.setText("");
                  DriverInfoActivity.this.driverCommentList.requestRefresh();
                  return;
                }
                UIHelper.showTip(DriverInfoActivity.this, "评论失败");
              }
            });
            return;
          }*/
/*          UIHelper.showTip(this, "评论内容不能为空");
          return;
        }
        UIHelper.showTip(this, "登陆后才能进行评论，请先登陆");
        return;
        if (this.star2State)
        {
          this.star2State = false;
          this.starRankBtn2.setBackgroundResource(R.drawable.star_rank_disable);
          return;
        }
        this.star2State = true;
        this.starRankBtn2.setBackgroundResource(R.drawable.star_rank_normal);
        return;
        if (this.star3State)
        {
          this.star3State = false;
          this.starRankBtn3.setBackgroundResource(R.drawable.star_rank_disable);
          return;
        }
        this.star3State = true;
        this.starRankBtn3.setBackgroundResource(R.drawable.star_rank_normal);
        return;
        if (this.star4State)
        {
          this.star4State = false;
          this.starRankBtn4.setBackgroundResource(R.drawable.star_rank_disable);
          return;
        }
        this.star4State = true;
        this.starRankBtn4.setBackgroundResource(R.drawable.star_rank_normal);
        return;
        if (this.star5State)
        {
          this.star5State = false;
          this.starRankBtn5.setBackgroundResource(R.drawable.star_rank_disable);
          return;
        }
        this.star5State = true;
        this.starRankBtn5.setBackgroundResource(R.drawable.star_rank_normal);
        return;*/
/*        this.pageIndex += 1;
        this.moreBtn.setText("正在加载...");
        this.moreBtn.setEnabled(false);
        loadDatas();
        return;
        finish();
        return;
      } while (StringUtils.isNullOrEmpty(this.mobile));
      reportCallLog(this.userId, this.driver.getUserid());
      startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + this.mobile)));
      return;
    }*/

  }
  }
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.driver_info);
    this.sexIv = ((ImageView)findViewById(R.id.sex_iv));
    this.sexTv = ((TextView)findViewById(R.id.sex_tv));
/*    this.driverCommentEt = ((EditText)findViewById(R.id.driver_comment_et));
    this.postCommentBtn = ((Button)findViewById(R.id.post_comment_btn));
    this.companyTv = ((TextView)findViewById(R.id.company_tv));
    this.postCommentBtn.setOnClickListener(this);
    this.starRankBtn1 = ((Button)findViewById(R.id.star_rank_btn1));
    this.starRankBtn2 = ((Button)findViewById(R.id.star_rank_btn2));
    this.starRankBtn3 = ((Button)findViewById(R.id.star_rank_btn3));
    this.starRankBtn4 = ((Button)findViewById(R.id.star_rank_btn4));
    this.starRankBtn5 = ((Button)findViewById(R.id.star_rank_btn5));*/
/*    this.starRankBtn1.setOnClickListener(this);
    this.starRankBtn2.setOnClickListener(this);
    this.starRankBtn3.setOnClickListener(this);
    this.starRankBtn4.setOnClickListener(this);
    this.starRankBtn5.setOnClickListener(this);*/
    this.headView = ((ImageView)findViewById(R.id.head_iv));
  /*  this.bitmapManager = new BitmapManager();*/
    this.defaultHead = getResources().getDrawable(R.drawable.ic_launcher);
    this.backBtn = findViewById(R.id.back_btn);
    this.backBtn.setOnClickListener(this);
    this.driver = ((DriverInfo)getIntent().getSerializableExtra("DRIVER_INFO"));
    this.nameTv = ((TextView)findViewById(R.id.name_tv));
    this.stateTv = ((TextView)findViewById(R.id.state_tv));
/*    this.starWarpView = ((LinearLayout)findViewById(R.id.star_warp_view));*/
    this.drivingYearsTv = ((TextView)findViewById(R.id.driving_years_tv));
/*    this.designatedDrivingNumTv = ((TextView)findViewById(R.id.designated_driving_num_tv));*/
    this.distanceTv = ((TextView)findViewById(R.id.distance_tv));
    this.callBtn = ((Button)findViewById(R.id.call_btn));
    this.locationBtn = ((Button)findViewById(R.id.location_btn));
    this.callBtn.setOnClickListener(this);
    this.locationBtn.setOnClickListener(this);
/*    this.driverCommentList = ((PullToRefreshListView)findViewById(R.id.comment_list_view));*/
    this.footerView = getLayoutInflater().inflate(R.layout.footer_view, null);
/*    this.moreBtn = ((Button)this.footerView.findViewById(R.id.more_btn));
    this.moreBtn.setOnClickListener(this);*/
   /* this.commentAdapter = new CommentAdapter(null);*/
 /*   this.driverCommentList.setAdapter(this.commentAdapter);
    this.driverCommentList.addFooterView(this.footerView);
    this.driverCommentList.hideFooterView();
    this.driverCommentList.setOnRefreshListener(this);
    this.driverCommentList.requestRefresh();
    this.sharedPreferences = getSharedPreferences("ILOVEMYCAR", 0);
    this.userId = this.sharedPreferences.getInt("USER_ID", -1);
    this.apiKey = this.sharedPreferences.getString("API_KEY", null);*/
    init();
  }
  
/*  public void onRefresh()
  {
    if (this.driver != null)
    {
      this.refresh = true;
      this.pageIndex = 1;
      loadDatas();
    }
  }
  */
  /*private class CommentAdapter
    extends BaseAdapter
  {
    private CommentAdapter() {}
    
    public int getCount()
    {
      return DriverInfoActivity.this.comments.size();
    }
    
    public Object getItem(int paramInt)
    {
      return null;
    }
    
    public long getItemId(int paramInt)
    {
      return 0L;
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null)
      {
    	View view = DriverInfoActivity.this.getLayoutInflater().inflate(R.layout.comment_list_item, null);
    	CommentListViewItem commentListViewItem = new DriverInfoActivity.CommentListViewItem();
    	commentListViewItem.headIv = ((ImageView)view.findViewById(R.id.head_iv));
    	commentListViewItem.nameTV = ((TextView)view.findViewById(R.id.name_tv));
    	commentListViewItem.contentTv = ((TextView)view.findViewById(R.id.content_tv));
        paramViewGroup.setTag(commentListViewItem);
      }
      for (;;)
      {
        GetDriverComments.Comment localComment = (GetDriverComments.Comment)DriverInfoActivity.this.comments.get(paramInt);
        paramView.nameTV.setText(localComment.name);
        paramView.contentTv.setText(localComment.content);
        return paramViewGroup;
        paramViewGroup = paramView;
        paramView = (DriverInfoActivity.CommentListViewItem)paramView.getTag();
      }
    }
  }
*/  
  public class CommentListViewItem
  {
    public TextView contentTv;
    public ImageView headIv;
    public TextView nameTV;
    
    public CommentListViewItem() {}
  }
}
