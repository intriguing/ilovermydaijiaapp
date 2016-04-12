package com.ilovemydaijia.activity;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionInfo;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.daija.AppManager;
import com.daija.ILoveMyCarApp;
import com.daija.ui.Rotate3d;
import com.daijia.utils.BitmapProvider;
import com.daijia.utils.StringUtils;
import com.daijia.utils.SystemUtils;
import com.daijia.utils.UIHelper;
import com.example.ilovermydaijia.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LBSActivity
        extends Activity
        implements MKGeneralListener, OnClickListener, OnItemClickListener {
    MyLocationOverlay myLocationOverlay = null;
    LocationClient mLocClient;
    LocationData locData = null;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MapController mapController;
    boolean isFirstLoc = true;// 是否首次定位
    private ArrayAdapter<String> sugAdapter = null;
    public static final int BANK = 5;
    public static final int CAR_WASH = 3;
    public static final int FOURS = 7;
    public static final int GAS_STATION = 1;
    public static final int HOTEL = 4;
    public static final int PARKING = 2;
    public static final String POI_TYPE = "POI_TYPE";
    public static final int RESTAURANT = 6;
    private ILoveMyCarApp iLoveMyCarApp;
    private TextView addressTv;
    private View backBtn;
    private MKPoiInfo currentPoi;
    private boolean displayList = true;
    private Rotate3d endLeftAnimation;
    private Rotate3d endRightAnimation;
    private View footerView;
    private Button goBtn;
    private String keyWord;
    private Rotate3d leftAnimation;
    private View listMode;
    private View listModeBtn;
    private View locationBtn;
    private View mapMode;
    private View mapModeBtn;
    private MapView mapView;
    private Bitmap markerBitmap;
    public List<MKPoiInfo> mkPoiInfos = new ArrayList<MKPoiInfo>();
    private MKSearch mkSearch;
    /*    private Button moreBtn;*/
    private GeoPoint myGeoPoint;
    private TextView nameTv;
    private int pageIndex = 0;
    private SimpleAdapter poiAdapters;
    private ListView poiListView;
    private View poiPopView;
    private int poiType = 0;
    private boolean refesh = false;
    private Rotate3d rightAnimation;
    private RouteOverlay routeOverlay;
    private boolean showRoute = false;
    private TextView telTv;
    private int totalPages = 0;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        AppManager.getAppManager().addActivity(this);
        this.iLoveMyCarApp = (ILoveMyCarApp) this.getApplication();
        if (this.iLoveMyCarApp.mBMapManager == null) {
            this.iLoveMyCarApp.mBMapManager = new BMapManager(
                    getApplicationContext());
            /**
             * 如果BMapManager没有初始化则初始化BMapManager
             */
            if (!this.iLoveMyCarApp.mBMapManager.init(ILoveMyCarApp.strKey,
                    new ILoveMyCarApp.MyGeneralListener())) {
                Toast.makeText(
                        ILoveMyCarApp.getInstance().getApplicationContext(),
                        "BMapManager  初始化失败!", Toast.LENGTH_LONG).show();
            }
        }
        setContentView(R.layout.lbs);
        this.locationBtn = findViewById(R.id.location_btn);
        this.locationBtn.setOnClickListener(this);//
        this.backBtn = findViewById(R.id.back_btn);
        this.backBtn.setOnClickListener(this);
//    this.baiduLocationManager = BaiduLocationManager.getInstance(this);
        this.listModeBtn = findViewById(R.id.list_mode_btn);
        this.listModeBtn.setOnClickListener(this);//
        this.mapModeBtn = findViewById(R.id.map_mode_btn);
        this.mapModeBtn.setOnClickListener(this);//

       /* int i = SystemUtils.getScreenWidth(this) / 2;
        int j = SystemUtils.getScreenHeight(this) / 2;
        this.leftAnimation = new Rotate3d(0.0F, -90.0F, 0.0F, 0.0F, i, j);
        this.leftAnimation.setAnimationListener(this);
        this.rightAnimation = new Rotate3d(90.0F, 0.0F, 0.0F, 0.0F, i, j);
        this.endLeftAnimation = new Rotate3d(-90.0F, 0.0F, 0.0F, 0.0F, i, j);
        this.endRightAnimation = new Rotate3d(0.0F, 90.0F, 0.0F, 0.0F, i, j);
        this.endLeftAnimation.setFillAfter(false);
        this.endLeftAnimation.setDuration(1000L);
        this.endRightAnimation.setFillAfter(false);
        this.endRightAnimation.setDuration(1000L);
        this.leftAnimation.setFillAfter(false);
        this.leftAnimation.setDuration(1000L);
        this.rightAnimation.setFillAfter(false);
        this.rightAnimation.setDuration(1000L);*/
        /*this.footerView = getLayoutInflater().inflate(R.layout.footer_view, null);
        this.moreBtn = ((Button) this.footerView.findViewById(R.id.more_btn));
        this.moreBtn.setOnClickListener(this);//*/

        this.mapMode = findViewById(R.id.map_mode);
        this.listMode = findViewById(R.id.list_mode);
        this.poiListView = (ListView) findViewById(R.id.poi_list_view);
        this.poiListView.setOnItemClickListener(this);//
        this.mapView = ((MapView) findViewById(R.id.map_view));
        this.mapController = this.mapView.getController();
        this.mapController.setZoom(16);
        this.mapController.enableClick(true);
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
        this.poiPopView = getLayoutInflater().inflate(R.layout.poi_pop_overlay, null);
        this.goBtn = ((Button) this.poiPopView.findViewById(R.id.go_btn));
        this.goBtn.setOnClickListener(this);
        this.nameTv = ((TextView) this.poiPopView.findViewById(R.id.name_tv));
        this.telTv = ((TextView) this.poiPopView.findViewById(R.id.tel_tv));
        this.addressTv = ((TextView) this.poiPopView.findViewById(R.id.address_tv));

        this.mkSearch = new MKSearch();
        this.mkSearch.init(this.iLoveMyCarApp.mBMapManager, new MKSearchListener() {
            //在此处理详情页结果
            @Override
            public void onGetPoiDetailSearchResult(int type, int error) {
                if (error != 0) {
                    Toast.makeText(LBSActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LBSActivity.this, "成功，查看详情页面", Toast.LENGTH_SHORT).show();
                }
            }

            /**
             * 在此处理poi搜索结果
             */
            @Override
            public void onGetPoiResult(MKPoiResult paramMKPoiResult, int paramInt1, int paramInt2) {
                // 错误号可参考MKEvent中的定义
                if ((paramInt2 != 0) || (paramMKPoiResult == null)) {
                    UIHelper.showTip(LBSActivity.this, "抱歉，未找到结果");
                    return;
                }
                if (paramMKPoiResult.getCurrentNumPois() > 0) {
                    ArrayList<MKPoiInfo> localArrayList = paramMKPoiResult.getAllPoi();
                    if (LBSActivity.this.refesh) {
                        LBSActivity.this.refesh = false;
                        // this.poiListView.onRefreshComplete();
                        LBSActivity.this.mkPoiInfos.clear();
                    }
                    LBSActivity.this.mkPoiInfos.addAll(localArrayList);
                    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                    for (MKPoiInfo localMKPoiInfo : LBSActivity.this.mkPoiInfos) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("name_tv", localMKPoiInfo.name);
                        if (StringUtils.isNullOrEmpty(localMKPoiInfo.phoneNum)) {
                            map.put("phoneNum", localMKPoiInfo.phoneNum);
                        } else {
                            map.put("phoneNum", "电话:" + localMKPoiInfo.phoneNum);
                        }
                        map.put("address", localMKPoiInfo.address);
                        list.add(map);
                    }
                    LBSActivity.this.poiAdapters = new SimpleAdapter(LBSActivity.this, list, R.layout.poi_item, new String[]{"name_tv", "phoneNum", "address"}, new int[]{R.id.name_tv, R.id.phone_number_tv, R.id.address_tv});
                    LBSActivity.this.poiListView.setAdapter(LBSActivity.this.poiAdapters);
                    LBSActivity.this.loadMapOverlaysAnimateToMyLocation();
                    // LBSActivity.this.poiAdapters.notifyDataSetChanged();
                    LBSActivity.this.pageIndex = paramMKPoiResult.getPageIndex();
                    LBSActivity.this.totalPages = paramMKPoiResult.getNumPages();
                    return;
                }
                UIHelper.showTip(LBSActivity.this, "抱歉，未找到结果");
            }

            @Override
            public void onGetDrivingRouteResult(MKDrivingRouteResult res, int error) {
                if ((error != 0) || (res == null)) {
                    UIHelper.showTip(LBSActivity.this, "抱歉，未找到结果");
                    return;
                }
                if (LBSActivity.this.routeOverlay != null) {
                    LBSActivity.this.mapView.getOverlays().remove(LBSActivity.this.routeOverlay);
                }
                LBSActivity.this.routeOverlay = new RouteOverlay(LBSActivity.this, LBSActivity.this.mapView);
                LBSActivity.this.routeOverlay.setData(res.getPlan(0).getRoute(0));
                LBSActivity.this.mapView.getOverlays().add(LBSActivity.this.routeOverlay);
                LBSActivity.this.mapView.invalidate();
                LBSActivity.this.mapView.getController().animateTo(res.getStart().pt);
            }

            public void onGetTransitRouteResult(MKTransitRouteResult res,
                                                int error) {
            }

            public void onGetWalkingRouteResult(MKWalkingRouteResult res,
                                                int error) {
            }

            public void onGetAddrResult(MKAddrInfo res, int error) {
            }

            public void onGetBusDetailResult(MKBusLineResult result, int iError) {
            }

            /**
             * 更新建议列表
             */
            @Override
            public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
                if (res == null || res.getAllSuggestions() == null) {
                    return;
                }
                sugAdapter.clear();
                for (MKSuggestionInfo info : res.getAllSuggestions()) {
                    if (info.key != null)
                        sugAdapter.add(info.key);
                }
                sugAdapter.notifyDataSetChanged();

            }

            @Override
            public void onGetShareUrlResult(MKShareUrlResult result, int type, int error) {
            }
        });
        //   this.mapView.getController().setDrawOverlayWhenZooming(true);

        this.poiType = getIntent().getIntExtra("POI_TYPE", 0);
        if (this.poiType == 0) {
            this.poiType = 1;
        }
        switch (this.poiType) {
            case 1:
                this.markerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.box_gas_station);
                this.keyWord = "加油站";
                break;
            //continue;
            case 2:
                this.markerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.box_parking);
                this.keyWord = "停车场";
                break;
            // continue;
            case 3:
                this.markerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.box_car_wash);
                this.keyWord = "洗车";
                break;
            //continue;
            case 4:
                this.markerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.box_hotel);
                this.keyWord = "酒店";
                break;
            case 5:
                this.markerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.box_bank);
                this.keyWord = "银行";
                break;
            // continue;
            case 6:
                this.markerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.box_restautantl);
                this.keyWord = "餐饮";
                break;
            // continue;
            case 7:
                this.markerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.box_4s);
                this.keyWord = "4s店";
                break;
        }
        int i = SystemUtils.getScreenWidth(this) / 15;
        this.markerBitmap = BitmapProvider.getBitmap(this.markerBitmap, i, i);
    }

    private void loadMapOverlaysAnimateTo(GeoPoint paramGeoPoint) {
        if (this.mkPoiInfos.size() > 0) {
            this.mapView.getController().animateTo(paramGeoPoint);
            this.mapView.getController().setZoom(18);
            this.mapView.removeView(this.poiPopView);
            this.mapView.addView(this.poiPopView, new MapView.LayoutParams(-2, -2, null, 51));

            MyPOIOverlay paramGeoPoints = new MyPOIOverlay(this.mapView, new BitmapDrawable(this.markerBitmap), this.mkPoiInfos);
            paramGeoPoints.addItem(paramGeoPoints.mGeoList);
            this.mapView.getOverlays().clear();
            this.mapView.getOverlays().add(paramGeoPoints);
            this.mapView.invalidate();
        }
    }

    private void loadMapOverlaysAnimateToMyLocation() {
        if (this.mkPoiInfos.size() > 0) {
            this.mapView.getController().animateTo(this.myGeoPoint);
            this.mapView.getController().setZoom(18);
            this.mapView.removeView(this.poiPopView);
            this.mapView.addView(this.poiPopView, new MapView.LayoutParams(-2, -2, null, 51));

            MyPOIOverlay localMyPOIOverlay = new MyPOIOverlay(this.mapView, new BitmapDrawable(this.markerBitmap), this.mkPoiInfos);
            localMyPOIOverlay.addItem(localMyPOIOverlay.mGeoList);
            this.mapView.getOverlays().clear();
            this.mapView.getOverlays().add(localMyPOIOverlay);
            this.mapView.invalidate();
        }
    }

    private void showRoute(GeoPoint paramGeoPoint1, GeoPoint paramGeoPoint2) {
        MKPlanNode localMKPlanNode1 = new MKPlanNode();
        MKPlanNode localMKPlanNode2 = new MKPlanNode();
        localMKPlanNode1.pt = paramGeoPoint1;
        localMKPlanNode2.pt = paramGeoPoint2;
        this.mkSearch.drivingSearch(null, localMKPlanNode1, null, localMKPlanNode2);
    }

    private void updateDisplayPopWindow(MKPoiInfo paramMKPoiInfo) {
        String str = "";
        if (!StringUtils.isNullOrEmpty(paramMKPoiInfo.phoneNum)) {
            str = "电话:" + paramMKPoiInfo.phoneNum;
        }
        this.nameTv.setText(paramMKPoiInfo.name);
        this.telTv.setText(str);
        this.addressTv.setText(paramMKPoiInfo.address);
        this.mapView.updateViewLayout(this.poiPopView, new MapView.LayoutParams(-2, -2, paramMKPoiInfo.pt, 81));
        this.poiPopView.setVisibility(View.VISIBLE);
        this.mapView.getController().animateTo(paramMKPoiInfo.pt);

    }

    @Override
    public void onClick(View paramView) {
        switch (paramView.getId()) {
            case R.id.location_btn:
                if (this.myGeoPoint != null) {
                    this.mapView.getController().setZoom(18);
                    this.mapView.getController().animateTo(this.myGeoPoint);
                }
                break;
            case R.id.go_btn:
                if ((this.currentPoi != null) && (this.myGeoPoint != null)) {
                    this.poiPopView.setVisibility(View.GONE);
                    showRoute(this.myGeoPoint, this.currentPoi.pt);
                    this.pageIndex += 1;
                    this.mkSearch.goToPoiPage(this.pageIndex);
                }
                break;
            case R.id.back_btn:
                finish();
                break;
            case R.id.list_mode_btn:
                this.mapMode.setVisibility(View.GONE);
                this.listMode.setVisibility(View.VISIBLE);
                this.displayList = true;
                break;
            case R.id.map_mode_btn:
                this.mapMode.setVisibility(View.VISIBLE);
                this.listMode.setVisibility(View.GONE);
                this.displayList = false;
                break;
        }
    }

    @Override
    public void onGetNetworkState(int paramInt) {
    }

    @Override
    public void onGetPermissionState(int paramInt) {
    }

    @Override
    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
        // paramInt -= 1;
        if ((paramInt >= 0) && (paramInt < this.mkPoiInfos.size())) {
            this.mapMode.setVisibility(View.VISIBLE);
            this.listMode.setVisibility(View.GONE);
            this.displayList = false;
            MKPoiInfo mkPoiInfo = this.mkPoiInfos.get(paramInt);
            loadMapOverlaysAnimateTo(mkPoiInfo.pt);
            updateDisplayPopWindow(mkPoiInfo);
        }
    }

    @Override
    protected void onDestroy() {
        mLocClient.stop();
        mapView.destroy();
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    protected void onPause() {
        this.mapView.setVisibility(View.INVISIBLE);
        mLocClient.stop();
        this.mapView.onPause();
        //MobclickAgent.onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapView.setVisibility(View.VISIBLE);
        mapView.onResume();
        // MobclickAgent.onResume(this);
        super.onResume();
    }

    class MyPOIOverlay
            extends ItemizedOverlay<OverlayItem> {
        public List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();
        public List<MKPoiInfo> mkpoiInfo = new ArrayList<MKPoiInfo>();
        private final Paint paint = new Paint();

        public MyPOIOverlay(MapView mapView, Drawable marker, List<MKPoiInfo> paramList) {
            super(marker, mapView);
            this.mkpoiInfo = paramList;
            this.paint.setAntiAlias(true);
            for (int i = 0; i < this.mkpoiInfo.size(); i++) {
                OverlayItem overlayItem = new OverlayItem(mkpoiInfo.get(i).pt, "p" + i, "point" + i);
                overlayItem.setMarker(marker);
                this.mGeoList.add(overlayItem);
            }
        }

        protected OverlayItem createItem(int paramInt) {
            return this.mGeoList.get(paramInt);
        }


        protected boolean onTap(int paramInt) {
            MKPoiInfo localMKPoiInfo = this.mkpoiInfo.get(paramInt);
            LBSActivity.this.currentPoi = localMKPoiInfo;
            LBSActivity.this.updateDisplayPopWindow(localMKPoiInfo);
            return true;
        }

        public boolean onTap(GeoPoint paramGeoPoint, MapView paramMapView) {
            LBSActivity.this.poiPopView.setVisibility(View.GONE);
            return super.onTap(paramGeoPoint, paramMapView);
        }

        public int size() {
            return this.mkpoiInfo.size();
        }
    }

    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return;

             locData.latitude = location.getLatitude();
             locData.longitude = location.getLongitude();
/*            locData.latitude = 38.843716;
            locData.longitude = 115.436641;*/
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
                LBSActivity.this.myGeoPoint = new GeoPoint(
                        (int) (locData.latitude * 1e6),
                        (int) (locData.longitude * 1e6));
                mapController.animateTo(LBSActivity.this.myGeoPoint);
                myLocationOverlay.setLocationMode(MyLocationOverlay.LocationMode.FOLLOWING);
                UIHelper.showProgressDialog(LBSActivity.this, "正在搜索...");
                if (LBSActivity.this.poiType == 6) {
                    LBSActivity.this.mkSearch.poiSearchNearBy(LBSActivity.this.keyWord, LBSActivity.this.myGeoPoint, 2000);
                    UIHelper.closeProgressDialog();
                    return;
                }
                LBSActivity.this.mkSearch.poiSearchNearBy(LBSActivity.this.keyWord, LBSActivity.this.myGeoPoint, 10000);
                UIHelper.closeProgressDialog();
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
}
