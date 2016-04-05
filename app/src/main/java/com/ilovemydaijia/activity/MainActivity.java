package com.ilovemydaijia.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.baidu.platform.comapi.map.Projection;
import com.daija.ILoveMyCarApp;
import com.daija.location.BaiduLocationManager;
import com.daija.models.DriverInfo;
import com.daija.models.DriversInfo;
import com.daijia.net.JSONCallBack;
import com.daijia.net.JSONService;
import com.daijia.net.JSONServiceImpl;
import com.daijia.utils.BitmapProvider;
import com.daijia.utils.DisplayUtil;
import com.daijia.utils.UIHelper;
import com.example.ilovermydaijia.R;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class MainActivity extends Activity
        implements View.OnClickListener {
    MyLocationOverlay myLocationOverlay = null;
    LocationClient mLocClient;
    LocationData locData = null;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MapController mapController;
    boolean isFirstLoc = true;// 是否首次定位
    private DriverLocationOverlay driverLocationOverlay = null;
    private int driverTotalPage = 0;
    private boolean driverRefesh = true;
    private List<DriverInfo> nearDrivers = new ArrayList<DriverInfo>();
    private int driverPageIndex = 1;
    private int status = -1;
    private View mapLoadView;
    private ListView listView;
    private boolean init = true;
    /*private GeoPoint geoPoint;*/
    private int currentIndex = 0;
    private View driverLayout;
    private View applicationLayout;
    /*	  private View userInfoLayout;*/
    private ImageView subItem1;
    private ImageView subItem2;
    private View bottomItem1;
    private View bottomItem2;
    private View parkingBtn;
    private View restaurantBtn;
    private View hotelBtn;
    private View gasStatinBtn;
    private View carWashBtn;
    private View bankStation;
    private View foursBtn;
    private static MainActivity me = null;
    private ILoveMyCarApp iLoveMyCarApp;
    private Bitmap icDriverBusy;
    private Bitmap icDriverOffline;
    private Bitmap icDriverOnLine;
    private View myLocationPopView;
    private MapView mapView;
    private View listModeBtn;
    private View mapModeBtn;
    private View driverMapView;
    private View driverPointPopView;
    private TextView popDriverNameTv;
    private TextView popDriverYearsTv;
    private Bitmap bitmap;
    private SimpleAdapter simpleAdapter;
    private Drawable driverMarker;
    private JSONService jsonService = new JSONServiceImpl();
    private View.OnClickListener navClickListener = new View.OnClickListener() {
        public void onClick(View paramAnonymousView) {
            switch (paramAnonymousView.getId()) {
                case R.id.item_1:
                    MainActivity.this.driverLayout.setVisibility(View.VISIBLE);
                    MainActivity.this.bottomItem1.setBackgroundResource(R.drawable.bottom_bg_pressed);
                    MainActivity.this.subItem1.setImageResource(R.drawable.ic_driver_pressed);
                    MainActivity.this.currentIndex = 0;
                    MainActivity.this.applicationLayout.setVisibility(View.GONE);
                    MainActivity.this.bottomItem2.setBackgroundResource(R.drawable.bottom_bg_normal);
                    MainActivity.this.subItem2.setImageResource(R.drawable.ic_app_normal);
                    break;
                case R.id.item_2:
                    MainActivity.this.applicationLayout.setVisibility(View.VISIBLE);
                    MainActivity.this.currentIndex = 1;
                    MainActivity.this.bottomItem2.setBackgroundResource(R.drawable.bottom_bg_pressed);
                    MainActivity.this.subItem2.setImageResource(R.drawable.ic_app_pressed);
                    MainActivity.this.driverLayout.setVisibility(View.GONE);
                    MainActivity.this.bottomItem1.setBackgroundResource(R.drawable.bottom_bg_normal);
                    MainActivity.this.subItem1.setImageResource(R.drawable.ic_driver_normal);
                    break;
            }
        }
    };

    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        /**
         * 使用地图sdk前需先初始化BMapManager. BMapManager是全局的，可为多个MapView共用，它需要地图模块创建前创建，
         * 并在地图地图模块销毁后销毁，只要还有地图模块在使用，BMapManager就不应该销毁
         */
        this.iLoveMyCarApp = (ILoveMyCarApp) this.getApplication();
        if (this.iLoveMyCarApp.mBMapManager == null) {
            this.iLoveMyCarApp.mBMapManager = new BMapManager(getApplicationContext());
            /**
             * 如果BMapManager没有初始化则初始化BMapManager
             */
            if (!this.iLoveMyCarApp.mBMapManager.init(ILoveMyCarApp.strKey, new ILoveMyCarApp.MyGeneralListener())) {
                Toast.makeText(ILoveMyCarApp.getInstance().getApplicationContext(),
                        "BMapManager  初始化失败!", Toast.LENGTH_LONG).show();
            }
        }
        setContentView(R.layout.main);
        me = this;
        this.gasStatinBtn = findViewById(R.id.gas_station_btn);
        this.bankStation = findViewById(R.id.bank_btn);
        this.hotelBtn = findViewById(R.id.hotel_btn);
        this.parkingBtn = findViewById(R.id.parking_btn);
        this.carWashBtn = findViewById(R.id.car_wash_btn);
        this.restaurantBtn = findViewById(R.id.restaurant_btn);
        this.foursBtn = findViewById(R.id.four_4s);
        this.gasStatinBtn.setOnClickListener(this);
        this.bankStation.setOnClickListener(this);
        this.hotelBtn.setOnClickListener(this);
        this.parkingBtn.setOnClickListener(this);
        this.carWashBtn.setOnClickListener(this);
        this.restaurantBtn.setOnClickListener(this);
        this.foursBtn.setOnClickListener(this);
        this.subItem1 = ((ImageView) findViewById(R.id.sub_item_1));
        this.subItem2 = ((ImageView) findViewById(R.id.sub_item_2));
        this.bottomItem1 = findViewById(R.id.item_1);
        this.bottomItem2 = findViewById(R.id.item_2);
        this.bottomItem1.setOnClickListener(this.navClickListener);
        this.bottomItem2.setOnClickListener(this.navClickListener);
        this.icDriverOnLine = BitmapFactory.decodeResource(getResources(), R.drawable.ic_driver_online);
        this.icDriverOffline = BitmapFactory.decodeResource(getResources(), R.drawable.ic_driver_offine);
        this.icDriverBusy = BitmapFactory.decodeResource(getResources(), R.drawable.ic_driver_busy);
        this.listModeBtn = findViewById(R.id.list_mode_btn);
        this.listModeBtn.setOnClickListener(this);
        this.mapModeBtn = findViewById(R.id.map_mode_btn);
        this.mapModeBtn.setOnClickListener(this);
/*    this.searchBtn = findViewById(R.id.search_btn);
    this.searchBtn.setOnClickListener(this);*/
        this.driverMapView = findViewById(R.id.driver_map_view);
        this.mapView = ((MapView) findViewById(R.id.map_view));
        this.mapView.setFocusable(true);
        this.mapView.setClickable(true);
/*    this.iLoveMyCarApp.adddUserPropsChangeListener(this);*/
        this.myLocationPopView = getLayoutInflater().inflate(R.layout.my_location_pop_view, null);
        this.mapView.removeView(this.myLocationPopView);
        this.mapView.addView(this.myLocationPopView, new MapView.LayoutParams(-2, -2, null, 51));
        this.driverPointPopView = getLayoutInflater().inflate(R.layout.driver_point_pop_view, null);
        this.popDriverNameTv = ((TextView) this.driverPointPopView.findViewById(R.id.pop_driver_name_tv));
        this.popDriverYearsTv = ((TextView) this.driverPointPopView.findViewById(R.id.pop_driver_years_tv));
 /*   this.popDriverStarWarp = ((LinearLayout)this.driverPointPopView.findViewById(R.id.pop_driver_star_warp));*/
        this.driverPointPopView.setVisibility(View.GONE);
        this.driverPointPopView.setClickable(true);
        this.mapView.removeView(this.driverPointPopView);
        this.mapView.addView(this.driverPointPopView, new MapView.LayoutParams(-2, -2, null, 51));
        int i = DisplayUtil.dip2px(this, 30.0F);
        this.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        this.bitmap = BitmapProvider.getBitmap(this.bitmap, i, i);
        this.driverMarker = new BitmapDrawable(this.bitmap);
        this.driverLayout = findViewById(R.id.driver_layout);
        this.applicationLayout = findViewById(R.id.application_layout);
        this.listView = (ListView) findViewById(R.id.driver_list_view);
        this.listView.setOnItemClickListener(new OnItemClickListenerImpl());
        this.mapLoadView = findViewById(R.id.map_load_view);
        this.mapController = this.mapView.getController();
        this.mapController.setZoom(12);
        mapController.setOverlooking(-30);
        mapView.setTraffic(true);
        mapView.getController().enableClick(true);
        mapView.setBuiltInZoomControls(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        locData = new LocationData();
        mLocClient.registerLocationListener(myListener);// 注册定位监听
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(10000);// 设置定时定位的时间间隔。单位ms
        mLocClient.setLocOption(option);
        mLocClient.start();

        // 定位图层初始化
        myLocationOverlay = new MyLocationOverlay(mapView);
        // 设置定位数据
        myLocationOverlay.setData(locData);
        // 添加定位图层
        mapView.getOverlays().add(myLocationOverlay);
        // 开启定位图层接受方向数据功能，当定位数据中有方向时，定位图标会旋转至该方向
        myLocationOverlay.enableCompass();
        // 修改定位数据后刷新图层生效
        mapView.refresh();

        mLocClient.requestLocation();
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return;

            //locData.latitude = location.getLatitude();
            //locData.longitude = location.getLongitude();
            locData.latitude = 38.843716;
            locData.longitude = 115.436641;
            // 如果不显示定位精度圈，将accuracy赋值为0即可
            locData.accuracy = location.getRadius();
            // 此处可以设置 locData的方向信息, 如果定位 SDK 未返回方向信息，用户可以自己实现罗盘功能添加方向信息。
            locData.direction = location.getDerect();
            // 更新定位数据
            myLocationOverlay.setData(locData);
            // 更新图层数据执行刷新后生效
            mapView.refresh();
            // 是手动触发请求或首次定位时，移动到定位点
            if (isFirstLoc) {
                // 移动地图到定位点
                Log.d("LocationOverlay", "receive location, animate to it");
                mapController.animateTo(new GeoPoint(
                        (int) (locData.latitude * 1e6),
                        (int) (locData.longitude * 1e6)));
                myLocationOverlay.setLocationMode(LocationMode.FOLLOWING);
                MainActivity.this.searchDriver(locData);
            }
            // 首次定位完成
            isFirstLoc = false;
        }

        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null) {
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        mLocClient.stop();
        // 关闭定位图层
        mapView.destroy();
        super.onDestroy();
    }

    protected void onPause() {
        mapView.setVisibility(View.INVISIBLE);
        mLocClient.stop();
        mapView.onPause();
        //MobclickAgent.onPause(this);
        super.onPause();
    }

    protected void onResume() {
        mapView.setVisibility(View.VISIBLE);
        mapView.onResume();
        //MobclickAgent.onResume(this);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.gas_station_btn:
                Intent paramView = new Intent(this, LBSActivity.class);
                paramView.putExtra("POI_TYPE", 1);
                startActivity(paramView);
                return;
            case R.id.bank_btn:
                Intent paramView1 = new Intent(this, LBSActivity.class);
                paramView1.putExtra("POI_TYPE", 5);
                startActivity(paramView1);
                return;
            case R.id.hotel_btn:
                Intent paramView2 = new Intent(this, LBSActivity.class);
                paramView2.putExtra("POI_TYPE", 4);
                startActivity(paramView2);
                return;
            case R.id.parking_btn:
                Intent paramView3 = new Intent(this, LBSActivity.class);
                paramView3.putExtra("POI_TYPE", 2);
                startActivity(paramView3);
                return;
            case R.id.car_wash_btn:
                Intent paramView4 = new Intent(this, LBSActivity.class);
                paramView4.putExtra("POI_TYPE", 3);
                startActivity(paramView4);
                return;
            case R.id.restaurant_btn:
                Intent paramView5 = new Intent(this, LBSActivity.class);
                paramView5.putExtra("POI_TYPE", 6);
                startActivity(paramView5);
                return;
            case R.id.four_4s:
                Intent paramView6 = new Intent(this, LBSActivity.class);
                paramView6.putExtra("POI_TYPE", 7);
                startActivity(paramView6);
                return;
            case R.id.list_mode_btn:
                this.driverMapView.setVisibility(View.GONE);
                this.listView.setVisibility(View.VISIBLE);
/*     this.listView.requestRefresh();*/
                return;
            case R.id.map_mode_btn:
                this.driverMapView.setVisibility(View.VISIBLE);
                this.listView.setVisibility(View.GONE);
                // searchDriver(locData);
                return;
        }
    }

    public void searchDriver(LocationData paramLocation) {
        this.mapLoadView.setVisibility(View.VISIBLE);
        List<NameValuePair> localArrayList = new ArrayList<NameValuePair>();
        localArrayList.add(new BasicNameValuePair("pointY", String.valueOf((int) (paramLocation.longitude * 1e6))));
        localArrayList.add(new BasicNameValuePair("pointX", String.valueOf((int) (paramLocation.latitude * 1e6))));
        this.jsonService.searchNearDriver(localArrayList, new JSONCallBack() {
            public void onFail() {
                MainActivity.this.mapLoadView.setVisibility(View.GONE);
                MainActivity.this.driverRefesh = false;
            }

            public void onSuccess(Object paramAnonymousObject) {
                MainActivity.this.nearDrivers.clear();
                DriversInfo driverInfo = (DriversInfo) paramAnonymousObject;
                MainActivity.this.driverPageIndex = driverInfo.getPageIndex();
                MainActivity.this.driverTotalPage = driverInfo.getTotalPage();
                if (MainActivity.this.driverRefesh) {
                    MainActivity.this.nearDrivers = driverInfo.getDriver();
                }
                MainActivity.this.mapLoadView.setVisibility(View.GONE);
                List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                for (DriverInfo driverInfotemp : MainActivity.this.nearDrivers) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("name", driverInfotemp.getName());
                    map.put("info", driverInfotemp.getInfor());
                    map.put("driverRange", "" + driverInfotemp.getDriverrange());
                    map.put("_id", "" + driverInfotemp.getUserid());
                    list.add(map);
                }
                MainActivity.this.simpleAdapter = new SimpleAdapter(MainActivity.this, list, R.layout.company_item, new String[]{"name", "info", "driverRange"}, new int[]{R.id.name_tv, R.id.company_profile_tv, R.id.price_tv});
                MainActivity.this.listView.setAdapter(simpleAdapter);
                if (MainActivity.this.driverLocationOverlay != null) {
                    MainActivity.this.mapView.getOverlays().remove(MainActivity.this.driverLocationOverlay);
                }
                MainActivity.this.driverLocationOverlay = new MainActivity.DriverLocationOverlay(MainActivity.this.mapView, MainActivity.this.driverMarker, MainActivity.this.nearDrivers);
                MainActivity.this.driverLocationOverlay.addItem(MainActivity.this.driverLocationOverlay.mGeoList);
                MainActivity.this.mapView.getOverlays().add(MainActivity.this.driverLocationOverlay);
                MainActivity.this.mapView.invalidate();
                if (MainActivity.this.locData != null) {
                    MainActivity.this.mapView.getController().animateTo(new GeoPoint((int) (MainActivity.this.locData.latitude * 1000000.0D), (int) (MainActivity.this.locData.longitude * 1000000.0D)));
                }
                mapView.refresh();
                UIHelper.showTip(MainActivity.this.getBaseContext(), "刷新成功");
                MainActivity.this.driverRefesh = false;
            }
        });
    }

    private class DriverLocationOverlay
            extends ItemizedOverlay<OverlayItem> {
        private List<DriverInfo> drivers = new ArrayList<DriverInfo>();
        public List<OverlayItem> mGeoList = null;

        public DriverLocationOverlay(MapView mapView, Drawable marker, List<DriverInfo> paramList) {
            super(marker, mapView);
            mGeoList = new ArrayList<OverlayItem>();
            this.drivers = paramList;
            Iterator<DriverInfo> iterator = paramList.iterator();
            while (iterator.hasNext()) {
                DriverInfo driverInfo =  iterator.next();
                Bitmap bitmapg = null;
                if (driverInfo.getStatus() == 1) {
                    bitmapg = MainActivity.this.icDriverOnLine;
                }
                if (driverInfo.getStatus() == 2) {
                    bitmapg = MainActivity.this.icDriverBusy;
                } else {
                    bitmapg = MainActivity.this.icDriverOffline;
                }
                int i = bitmapg.getWidth() / 2;
                int j = bitmapg.getHeight() / 2;
                GeoPoint geoPoint = new GeoPoint(driverInfo.getPointX() - i, driverInfo.getPointY() - j);
                OverlayItem overlayItem = new OverlayItem(geoPoint, "xx", "xx");
                overlayItem.setMarker(new BitmapDrawable(bitmapg));
                this.mGeoList.add(overlayItem);
            }
        }

        @Override
        protected OverlayItem createItem(int paramInt) {
            return this.mGeoList.get(paramInt);
        }

        @Override
        protected boolean onTap(int paramInt) {
            OverlayItem localOverlayItem =  this.mGeoList.get(paramInt);
            MainActivity.this.mapView.getController().animateTo(localOverlayItem.getPoint());
            final DriverInfo localDriverInfo =  this.drivers.get(paramInt);
            MainActivity.this.popDriverNameTv.setText(localDriverInfo.getName());
            MainActivity.this.popDriverYearsTv.setText("驾龄：" + localDriverInfo.getDrivingYears());
            MainActivity.this.mapView.updateViewLayout(MainActivity.this.driverPointPopView, new MapView.LayoutParams(-2, -2, localOverlayItem.getPoint(), 81));
            MainActivity.this.driverPointPopView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View paramAnonymousView) {
                    Intent intent = new Intent(MainActivity.this, DriverInfoActivity.class);
                    intent.putExtra("DRIVER_INFO", localDriverInfo);
                    MainActivity.this.startActivity(intent);
                }
            });
            MainActivity.this.driverPointPopView.setVisibility(View.VISIBLE);
            if (localDriverInfo.getStatus() == 3) {
                MainActivity.this.driverPointPopView.setBackgroundResource(R.drawable.state_offline_bg);
                return true;
            }
            if (localDriverInfo.getStatus() == 1) {
                MainActivity.this.driverPointPopView.setBackgroundResource(R.drawable.state_online_bg);
                return true;
            }
            if (localDriverInfo.getStatus() == 2) {
                MainActivity.this.driverPointPopView.setBackgroundResource(R.drawable.state_busy_bg);
                return true;
            }
            ImageView localImageView = MainActivity.this.createStarView();
/*    MainActivity.this.popDriverStarWarp.addView(localImageView);*/
            LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams) localImageView.getLayoutParams();
            localLayoutParams.gravity = Gravity.CENTER_VERTICAL;
            localImageView.setLayoutParams(localLayoutParams);
            return true;
        }

        @Override
        public boolean onTap(GeoPoint paramGeoPoint, MapView paramMapView) {
            MainActivity.this.driverPointPopView.setVisibility(View.GONE);
            return super.onTap(paramGeoPoint, paramMapView);
        }

        public int size() {
            return this.drivers.size();
        }
    }

    private ImageView createStarView() {
        ImageView localImageView = new ImageView(this);
        int i = DisplayUtil.dip2px(this, 20.0F);
        localImageView.setMaxWidth(i);
        localImageView.setMaxHeight(i);
        localImageView.setAdjustViewBounds(true);
        localImageView.setImageResource(R.drawable.star);
        return localImageView;
    }

    class OnItemClickListenerImpl implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // TODO Auto-generated method stub
            DriverInfo driverInfo = MainActivity.this.nearDrivers.get(position);
            Intent intent = new Intent(MainActivity.this, DriverInfoActivity.class);
            intent.putExtra("DRIVER_INFO", driverInfo);
            MainActivity.this.startActivity(intent);
        }

    }
}
