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
        import android.widget.ListView;
        import android.widget.SimpleAdapter;
        import android.widget.TextView;

        import java.io.PrintStream;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import org.apache.http.NameValuePair;
        import org.apache.http.message.BasicNameValuePair;

        import com.daija.BaseActivity;
        import com.daija.models.BaseInfo;
        import com.daija.models.CommentVoInfo;
        import com.daija.models.CommentVosInfo;
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
        implements View.OnClickListener {
    public static final String DRIVER_INFO = "DRIVER_INFO";
    public static final String USER_PHONE = "USER_PHONE";
    private static final String TAG = "DriverInfoActivity";
    private View backBtn;
    private Button callBtn;
    private SimpleAdapter commentsAdapter;
    /*  private Drawable defaultHead;*/
/*  private TextView designatedDrivingNumTv;*/
    private TextView distanceTv;
    private DriverInfo driver;
    private String phone;
    private EditText driverCommentEt;
    private TextView drivingYearsTv;
    /*  private View footerView;*/
    private ImageView headView;
    private JSONService jsonService = new JSONServiceImpl();
    private List<CommentVoInfo> comments;
    private Button locationBtn;
    private String mobile;
    /*  private Button moreBtn;*/
    private TextView nameTv;
    private ListView driverCommentList;
    /*  private TextView nativePlaceTv;*/
/*  private int pageIndex = 1;*/
    private Button postCommentBtn;
    private ImageView sexIv;
    private TextView sexTv;
    /*  private SharedPreferences sharedPreferences;*/
    private boolean star1State = true;
    private boolean star2State = true;
    private boolean star3State = true;
    private boolean star4State = true;
    private boolean star5State = true;
    private LinearLayout starWarpView;
    private Button starRankBtn1;
    private Button starRankBtn2;
    private Button starRankBtn3;
    private Button starRankBtn4;
    private Button starRankBtn5;
    private TextView stateTv;

    /*  private int totalPage = 1;
      int tryTimes = 0;
      private int userId = -1;
      */
    private ImageView createStarView() {
        ImageView localImageView = new ImageView(this);
        localImageView.setImageResource(R.drawable.star);
        return localImageView;
    }

    private void init() {
        String str;
        ImageView localImageView;
        if (this.driver != null) {
            this.mobile = this.driver.getPhone();
            this.nameTv.setText(this.driver.getName());
/*      this.bitmapManager.loadBitmap(this.driver.head, this.headView, this.defaultHead);*/
            switch (this.driver.getStatus()) {
                case 1:
                    this.stateTv.setBackgroundResource(R.drawable.state_online);
                    this.stateTv.setText("空闲");
                    this.starWarpView.removeAllViews();
                    this.drivingYearsTv.setText("驾龄：" + this.driver.getDrivingYears());
/*          this.designatedDrivingNumTv.setText("代驾次数：" + this.driver.get);*/
                    this.distanceTv.setText("距离我：" + StringUtils.getLocDesc(this.driver.getRange()));
                    if (this.driver.getSex() != 1) {
                        str = "男";
                        localImageView = this.sexIv;
                        localImageView.setImageResource(R.drawable.male);
                    } else {
                        str = "女";
                    }
                    this.sexTv.setText("性别:" + str);
                    for(int i=0;i<this.driver.getStarLeave();i++)
                        this.starWarpView.addView(createStarView());
                    break;
                case 3:
                    this.stateTv.setBackgroundResource(R.drawable.state_offine);
                    this.stateTv.setText("离线");
                    this.starWarpView.removeAllViews();
                    this.drivingYearsTv.setText("驾龄：" + this.driver.getDrivingYears());
        /*          this.designatedDrivingNumTv.setText("代驾次数：" + this.driver.get);*/
                    this.distanceTv.setText("距离我：" + StringUtils.getLocDesc(this.driver.getRange()));
                    if (this.driver.getSex() != 1) {
                        str = "男";
                        localImageView = this.sexIv;
                        localImageView.setImageResource(R.drawable.male);
                    } else {
                        str = "女";
                    }
                    this.sexTv.setText("性别:" + str);
                    for(int i=0;i<this.driver.getStarLeave();i++)
                        this.starWarpView.addView(createStarView());
                    break;
                case 2:
                    this.stateTv.setBackgroundResource(R.drawable.state_busy);
                    this.stateTv.setText("忙碌");
                    this.starWarpView.removeAllViews();
                    this.drivingYearsTv.setText("驾龄：" + this.driver.getDrivingYears());
        /*          this.designatedDrivingNumTv.setText("代驾次数：" + this.driver.get);*/
                    this.distanceTv.setText("距离我：" + StringUtils.getLocDesc(this.driver.getRange()));
                    if (this.driver.getSex() != 1) {
                        str = "男";
                        localImageView = this.sexIv;
                        localImageView.setImageResource(R.drawable.male);
                    } else {
                        str = "女";
                    }
                    this.sexTv.setText("性别:" + str);
                    for(int i=0;i<this.driver.getStarLeave();i++)
                        this.starWarpView.addView(createStarView());
                    break;

            }
        }


    }

    private void loadDatas() {
        if (this.driver != null) {
            int i = this.driver.getUserid();
            ArrayList<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
            localArrayList.add(new BasicNameValuePair("driverId", String.valueOf(i)));
            this.jsonService.getDriverComments(localArrayList, new JSONCallBack() {
                public void onFail() {
                    return;
                }

                public void onSuccess(Object paramAnonymousObject) {
                    DriverInfoActivity.this.comments = ((CommentVosInfo) paramAnonymousObject).getCommentVoInfoList();
                    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                    for (CommentVoInfo commentVoInfo : DriverInfoActivity.this.comments) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("userName", commentVoInfo.getUserName());
                        map.put("comment", commentVoInfo.getComment());
                        map.put("stars",commentVoInfo.getStarLevel());
                        list.add(map);
                    }
                    DriverInfoActivity.this.commentsAdapter = new SimpleAdapter(DriverInfoActivity.this, list, R.layout.comment_list_item, new String[]{"userName", "comment","stars"}, new int[]{R.id.name_tv, R.id.content_tv,R.id.content_tvs});
                    DriverInfoActivity.this.driverCommentList.setAdapter(DriverInfoActivity.this.commentsAdapter);
                }
            });
        }
    }

    public void onClick(View paramView) {
        switch (paramView.getId()) {
            case R.id.star_rank_btn1:
                if (this.star1State) {
                    this.star1State = false;
                    this.starRankBtn1.setBackgroundResource(R.drawable.star_rank_disable);
                    return;
                }
                this.star1State = true;
                this.starRankBtn1.setBackgroundResource(R.drawable.star_rank_normal);
                return;
            case R.id.star_rank_btn2:
                if (this.star2State) {
                    this.star2State = false;
                    this.starRankBtn2.setBackgroundResource(R.drawable.star_rank_disable);
                    return;
                }
                this.star2State = true;
                this.starRankBtn2.setBackgroundResource(R.drawable.star_rank_normal);
                return;
            case R.id.star_rank_btn3:
                if (this.star3State) {
                    this.star3State = false;
                    this.starRankBtn3.setBackgroundResource(R.drawable.star_rank_disable);
                    return;
                }
                this.star3State = true;
                this.starRankBtn3.setBackgroundResource(R.drawable.star_rank_normal);
                return;
            case R.id.star_rank_btn4:
                if (this.star4State) {
                    this.star4State = false;
                    this.starRankBtn4.setBackgroundResource(R.drawable.star_rank_disable);
                    return;
                }
                this.star4State = true;
                this.starRankBtn4.setBackgroundResource(R.drawable.star_rank_normal);
                return;
            case R.id.star_rank_btn5:
                if (this.star5State) {
                    this.star5State = false;
                    this.starRankBtn5.setBackgroundResource(R.drawable.star_rank_disable);
                    return;
                }
                this.star5State = true;
                this.starRankBtn5.setBackgroundResource(R.drawable.star_rank_normal);
                return;
            case R.id.back_btn:
                this.finish();
                break;
            case R.id.location_btn:
                Intent tintentg = new Intent(this, DriverLocationViewActivity.class);
                tintentg.putExtra(DRIVER_INFO, this.driver);
                startActivity(tintentg);
                break;
            case R.id.call_btn:
                if (!StringUtils.isNullOrEmpty(this.mobile)) {
    /*      reportCallLog(this.userId, this.driver.getUserid());*/
                    startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + this.mobile)));
                }
                return;
            case R.id.post_comment_btn:
                SystemUtils.hideSoft(this);
                if (StringUtils.isNullOrEmpty(DriverInfoActivity.this.driverCommentEt.getText().toString())) {
                    UIHelper.showTip(this, "评论内容不能为空");
                    return;
                }
                if (StringUtils.isNullOrEmpty(DriverInfoActivity.this.phone)) {
                    UIHelper.showTip(this, "登陆后才能进行评论，请先登陆");
                    return;
                }
                if (this.driver.getUserid() == -1) {
                    UIHelper.showTip(this, "司机不存在");
                    return;
                }
                int j = 0;
                if (this.star1State) j++;
                if (this.star2State) j++;
                if (this.star3State) j++;
                if (this.star4State) j++;
                if (this.star5State) j++;
                UIHelper.showProgressDialog(this, "正在发表评论...");
                ArrayList localArrayList = new ArrayList();
                localArrayList.add(new BasicNameValuePair("phone", DriverInfoActivity.this.phone));
                localArrayList.add(new BasicNameValuePair("driverId", String.valueOf(this.driver.getUserid())));
                localArrayList.add(new BasicNameValuePair("comment", DriverInfoActivity.this.driverCommentEt.getText().toString()));
                localArrayList.add(new BasicNameValuePair("starLevel", String.valueOf(j)));
                this.jsonService.postDriverComment(localArrayList, new JSONCallBack() {
                    public void onFail() {
                        UIHelper.closeProgressDialog();
                        UIHelper.showTip(DriverInfoActivity.this, "评论失败");
                    }

                    public void onSuccess(Object paramAnonymousObject) {

                        if (((BaseInfo) paramAnonymousObject).isCode()) {
                            UIHelper.closeProgressDialog();
                            UIHelper.showTip(DriverInfoActivity.this, "评论成功");
                            DriverInfoActivity.this.driverCommentEt.setText("");
                            DriverInfoActivity.this.loadDatas();
                            return;
                        }
                        UIHelper.closeProgressDialog();
                        UIHelper.showTip(DriverInfoActivity.this, "评论失败");
                    }
                });
        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.driver_info);
        this.sexIv = ((ImageView) findViewById(R.id.sex_iv));
        this.sexTv = ((TextView) findViewById(R.id.sex_tv));

        this.driverCommentEt = ((EditText) findViewById(R.id.driver_comment_et));
        this.postCommentBtn = ((Button) findViewById(R.id.post_comment_btn));
        this.postCommentBtn.setOnClickListener(this);

        this.starRankBtn1 = ((Button) findViewById(R.id.star_rank_btn1));
        this.starRankBtn2 = ((Button) findViewById(R.id.star_rank_btn2));
        this.starRankBtn3 = ((Button) findViewById(R.id.star_rank_btn3));
        this.starRankBtn4 = ((Button) findViewById(R.id.star_rank_btn4));
        this.starRankBtn5 = ((Button) findViewById(R.id.star_rank_btn5));
        this.starRankBtn1.setOnClickListener(this);
        this.starRankBtn2.setOnClickListener(this);
        this.starRankBtn3.setOnClickListener(this);
        this.starRankBtn4.setOnClickListener(this);
        this.starRankBtn5.setOnClickListener(this);

        this.headView = ((ImageView) findViewById(R.id.head_iv));
  /*  this.bitmapManager = new BitmapManager();*/
    /*this.defaultHead = getResources().getDrawable(R.drawable.ic_launcher);*/
        this.backBtn = findViewById(R.id.back_btn);
        this.backBtn.setOnClickListener(this);
        this.driver = ((DriverInfo) getIntent().getSerializableExtra(DRIVER_INFO));
        this.phone = getIntent().getStringExtra(USER_PHONE);
        this.nameTv = ((TextView) findViewById(R.id.name_tv));
        this.stateTv = ((TextView) findViewById(R.id.state_tv));
        this.starWarpView = ((LinearLayout) findViewById(R.id.star_warp_view));
        this.drivingYearsTv = ((TextView) findViewById(R.id.driving_years_tv));
/*    this.designatedDrivingNumTv = ((TextView)findViewById(R.id.designated_driving_num_tv));*/
        this.distanceTv = ((TextView) findViewById(R.id.distance_tv));
        this.callBtn = ((Button) findViewById(R.id.call_btn));
        this.locationBtn = ((Button) findViewById(R.id.location_btn));
        this.callBtn.setOnClickListener(this);
        this.locationBtn.setOnClickListener(this);
        this.driverCommentList = (ListView) findViewById(R.id.comment_list_view);
        onRefresh();
        init();
    }

    public void onRefresh() {
        if (this.driver != null) {
            loadDatas();
        }
    }
}
