package com.ilovemydaijia.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.daija.ILoveMyCarApp;
import com.daija.models.DriverInfo;
import com.daijia.utils.BitmapProvider;
import com.daijia.utils.DisplayUtil;
import com.example.ilovermydaijia.R;
import com.umeng.analytics.MobclickAgent;


public class DriverLocationViewActivity extends Activity implements
		View.OnClickListener {
	MyLocationOverlay myLocationOverlay = null;
	LocationClient mLocClient;
	LocationData locData = null;
	public MyLocationListenner myListener;
	private MapController mapController;
	boolean isFirstLoc = true;// 是否首次定位
	public static final String DRIVER_INFO = "DRIVER_INFO";
	private com.daija.ILoveMyCarApp iLoveMyCarApp;
	private View backBtn;
	private Bitmap bitmap;
	private DriverInfo driverInfo = null;
	private DriverLocationOverlay driverLocationOverlay;
	private Drawable driverMarker;
	private View driverPointPopView;
	private Bitmap icDriverBusy;
	private Bitmap icDriverOffline;
	private Bitmap icDriverOnLine;
	private View locationBtn;
	private MapView mapView;
	private GeoPoint myGeoPoint;
/*	private float pointX;
	private float pointY;*/
	private TextView popDriverNameTv;
	/* private LinearLayout popDriverStarWarp; */
	private TextView popDriverYearsTv;

/*	private ImageView createStarView() {
		ImageView localImageView = new ImageView(this);
		localImageView.setImageResource(R.drawable.star);
		return localImageView;
	}*/

/*	protected boolean isRouteDisplayed() {
		return false;
	}*/

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.location_btn:
			this.mapView.getController().animateTo(this.myGeoPoint);
			break;
		case R.id.back_btn:
			finish();
			break;
		}
	}

	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
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
		setContentView(R.layout.driver_location);
		this.locationBtn = findViewById(R.id.location_btn);
		this.locationBtn.setOnClickListener(this);
		this.driverInfo = ((DriverInfo) getIntent().getSerializableExtra(
				"DRIVER_INFO"));
		this.backBtn = findViewById(R.id.back_btn);
		this.backBtn.setOnClickListener(this);
		/* this.baiduLocationManager = BaiduLocationManager.getInstance(this); */
		this.mapView = ((MapView) findViewById(R.id.map_view));
		this.mapController = this.mapView.getController();
		this.mapController.setZoom(12);
		mapController.setOverlooking(-30);
		mapView.setTraffic(true);
		mapView.getController().enableClick(true);
		mapView.setBuiltInZoomControls(true);
		mLocClient = new LocationClient(this);
		locData = new LocationData();
		myListener = new MyLocationListenner();
		mLocClient.registerLocationListener(myListener);// 注册定位监听
		this.driverPointPopView = getLayoutInflater().inflate(
				R.layout.driver_point_pop_view, null);
		this.popDriverNameTv = ((TextView) this.driverPointPopView
				.findViewById(R.id.pop_driver_name_tv));
		this.popDriverYearsTv = ((TextView) this.driverPointPopView
				.findViewById(R.id.pop_driver_years_tv));
		/*
		 * this.popDriverStarWarp =
		 * ((LinearLayout)this.driverPointPopView.findViewById
		 * (R.id.pop_driver_star_warp));
		 */
		this.driverPointPopView.setVisibility(View.GONE);
		this.driverPointPopView.setClickable(true);
		// this.mapView.removeView(this.driverPointPopView);
		this.mapView.addView(this.driverPointPopView, new MapView.LayoutParams(
				-2, -2, null, 51));
		this.icDriverOnLine = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_driver_online);
		this.icDriverOffline = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_driver_offine);
		this.icDriverBusy = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_driver_busy);
		int i = DisplayUtil.dip2px(this, 30.0F);
		this.bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);
		this.bitmap = BitmapProvider.getBitmap(this.bitmap, i, i);
		this.driverMarker = new BitmapDrawable(this.bitmap);
		this.driverLocationOverlay = new DriverLocationOverlay(
				mapView, this.driverMarker,this.driverInfo);
		this.driverLocationOverlay.addItem(this.driverLocationOverlay.overlayItem);
	    this.mapView.getOverlays().add(this.driverLocationOverlay);
		this.mapView.invalidate();
		mapView.refresh();
		mapView.getController().setCenter(driverLocationOverlay.geoPoint);
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

	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;

			// locData.latitude = location.getLatitude();
			// locData.longitude = location.getLongitude();
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
	  protected void onDestroy()
	  {
		  mLocClient.stop();
	      // 关闭定位图层
		mapView.destroy();
	    super.onDestroy();
	  }
	  @Override 
	protected void onPause() {
		 mapView.setVisibility(View.INVISIBLE);
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

	public class DriverLocationOverlay extends ItemizedOverlay<OverlayItem> {
		private DriverInfo driverInfo = null;
		public GeoPoint geoPoint = null;
		public OverlayItem overlayItem =null;

		@SuppressWarnings("deprecation")
		public DriverLocationOverlay(MapView mapView, Drawable paramDrawable,
				DriverInfo paramDriverInfo) {
			super(paramDrawable, mapView);
			Bitmap bitmapg;
			this.driverInfo = paramDriverInfo;
			if (this.driverInfo.getStatus() == 1) {
				bitmapg = DriverLocationViewActivity.this.icDriverOnLine;
			}
			if (this.driverInfo.getStatus() == 2) {
				bitmapg = DriverLocationViewActivity.this.icDriverBusy;
			} else {
				bitmapg = DriverLocationViewActivity.this.icDriverOffline;
			}
			int i = bitmapg.getWidth() / 2;
			int j = bitmapg.getHeight() / 2;
			this.geoPoint = new GeoPoint(paramDriverInfo.getPointX() - i,
					paramDriverInfo.getPointY() - j);
			this.overlayItem = new OverlayItem(this.geoPoint, "xx", "xx");
			this.overlayItem.setMarker(new BitmapDrawable(bitmapg));
		}

		@Override
		protected OverlayItem createItem(int paramInt) {
			return this.overlayItem;
		}

		@Override
		protected boolean onTap(int paramInt) {
			switch (this.driverInfo.getStatus()) {
			case 1:
				DriverLocationViewActivity.this.driverPointPopView
						.setBackgroundResource(R.drawable.state_online_bg);
				DriverLocationViewActivity.this.popDriverNameTv
						.setText(this.driverInfo.getName());
				DriverLocationViewActivity.this.popDriverYearsTv.setText("驾龄："
						+ this.driverInfo.getDrivingYears());
				DriverLocationViewActivity.this.mapView.updateViewLayout(
						DriverLocationViewActivity.this.driverPointPopView,
						new MapView.LayoutParams(-2, -2, this.overlayItem
								.getPoint(), 81));
				DriverLocationViewActivity.this.driverPointPopView
						.setVisibility(View.VISIBLE);
				break;
			case 2:
				DriverLocationViewActivity.this.driverPointPopView
						.setBackgroundResource(R.drawable.state_busy_bg);
				DriverLocationViewActivity.this.popDriverNameTv
						.setText(this.driverInfo.getName());
				DriverLocationViewActivity.this.popDriverYearsTv.setText("驾龄："
						+ this.driverInfo.getDrivingYears());
				DriverLocationViewActivity.this.mapView.updateViewLayout(
						DriverLocationViewActivity.this.driverPointPopView,
						new MapView.LayoutParams(-2, -2, this.overlayItem
								.getPoint(), 81));
				DriverLocationViewActivity.this.driverPointPopView
						.setVisibility(View.VISIBLE);
				break;
			case 3:
				DriverLocationViewActivity.this.driverPointPopView
						.setBackgroundResource(R.drawable.state_offline_bg);
				DriverLocationViewActivity.this.popDriverNameTv
						.setText(this.driverInfo.getName());
				DriverLocationViewActivity.this.popDriverYearsTv.setText("驾龄："
						+ this.driverInfo.getDrivingYears());
				DriverLocationViewActivity.this.mapView.updateViewLayout(
						DriverLocationViewActivity.this.driverPointPopView,
						new MapView.LayoutParams(-2, -2, this.overlayItem
								.getPoint(), 81));
				DriverLocationViewActivity.this.driverPointPopView
						.setVisibility(View.VISIBLE);
				break;}
			return true;
		}

		@Override
		public boolean onTap(GeoPoint paramGeoPoint, MapView paramMapView) {
			DriverLocationViewActivity.this.driverPointPopView.setVisibility(View.GONE);
			return super.onTap(paramGeoPoint, paramMapView);
		}

		@Override
		public int size() {
			return 1;
		}
	}
}
