package com.daijia.net;

import java.io.File;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.util.Log;

import com.daija.models.BaseInfo;
import com.daija.models.BaseInfos;
import com.daija.models.CommentVosInfo;
import com.daija.models.DriversInfo;
import com.daijia.utils.StringUtils;
import com.daijia.utils.ThreadManager;
import com.daijia.utils.Upload;

public class JSONServiceImpl
        implements JSONService {
    private Handler handler = new Handler();
    private boolean flag = false;
    private BaseInfo baseInfo = null;
    private BaseInfos baseInfos = null;
    private DriversInfo driversInfo;
    private CommentVosInfo commentVosInfo;
    // private BaseCodeInfo baseCodeInfo;
    private JSONCallBack paramJSONCallBackbase;
    List<NameValuePair> paramListbase;

    @Override
    public void findPwdUpdate(List<NameValuePair> paramList,
                              JSONCallBack paramJSONCallBack) {
        // TODO Auto-generated method stub
        this.flag = false;
        paramJSONCallBackbase = paramJSONCallBack;
        this.paramListbase = paramList;
        ThreadManager.getPool().execute(new Runnable() {
            public void run() {
                flag = false;
                String str = HttpUtils.getHttpData("http://23.94.123.193:8080/daijia/user/updatePass", paramListbase);
                Log.i("TAG", "R is :" + str);
                if (str != null) {
                    if (!StringUtils.isNullOrEmpty(str)) {
                        try {
                            baseInfo = new BaseInfo(new JSONObject(str));
                            flag = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                JSONServiceImpl.this.handler.post(new Runnable() {
                    public void run() {
                        if (flag) {
                            paramJSONCallBackbase.onSuccess(baseInfo);
                        } else {
                            paramJSONCallBackbase.onFail();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void getDriverComments(List<NameValuePair> paramList,
                                  JSONCallBack paramJSONCallBack) {
        // TODO Auto-generated method stub
        this.flag = false;
        paramJSONCallBackbase = paramJSONCallBack;
        this.paramListbase = paramList;
        ThreadManager.getPool().execute(new Runnable() {
            public void run() {
                flag = false;
                String str = HttpUtils.getHttpData("http://23.94.123.193:8080/daijia/driver/driverComments", paramListbase);
                Log.i("TAG", "R is :" + str);
                if (str != null) {
                    if (!StringUtils.isNullOrEmpty(str)) {
                        try {
                            commentVosInfo = new CommentVosInfo(new JSONObject(str));
                            flag = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                JSONServiceImpl.this.handler.post(new Runnable() {
                    public void run() {
                        if (flag) {
                            paramJSONCallBackbase.onSuccess(commentVosInfo);
                        } else {
                            paramJSONCallBackbase.onFail();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void getDrivers(JSONCallBack paramJSONCallBack) {
        // TODO Auto-generated method stub

    }


    @Override
    public void login(List<NameValuePair> paramList, JSONCallBack paramJSONCallBack) {
        // TODO Auto-generated method stub
        this.flag = false;
        paramJSONCallBackbase = paramJSONCallBack;
        this.paramListbase = paramList;
        ThreadManager.getPool().execute(new Runnable() {
            public void run() {
                flag = false;
                String str = HttpUtils.getHttpData("http://23.94.123.193:8080/daijia/user/login", paramListbase);
                Log.i("TAG", "R is :" + str);
                if (str != null) {
                    if (!StringUtils.isNullOrEmpty(str)) {
                        try {
                            baseInfos = new BaseInfos(new JSONObject(str));
                            flag = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                JSONServiceImpl.this.handler.post(new Runnable() {
                    public void run() {
                        if (flag) {
                            paramJSONCallBackbase.onSuccess(baseInfos);
                        } else {
                            paramJSONCallBackbase.onFail();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void postDriverComment(List<NameValuePair> paramList,
                                  JSONCallBack paramJSONCallBack) {
        // TODO Auto-generated method stub
        this.flag = false;
        paramJSONCallBackbase = paramJSONCallBack;
        this.paramListbase = paramList;
        ThreadManager.getPool().execute(new Runnable() {
            public void run() {
                flag = false;
                String str = HttpUtils.getHttpData("http://23.94.123.193:8080/daijia/driver/saveDriverComments", paramListbase);
                Log.i("TAG", "R is :" + str);
                if (str != null) {
                    if (!StringUtils.isNullOrEmpty(str)) {
                        try {
                            baseInfo = new BaseInfo(new JSONObject(str));
                            flag = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                JSONServiceImpl.this.handler.post(new Runnable() {
                    public void run() {
                        if (flag) {
                            paramJSONCallBackbase.onSuccess(baseInfo);
                        } else {
                            paramJSONCallBackbase.onFail();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void register(final String phone, final String driverYear, final String driverRange, final String pointX, final String pointY, final String picPath, JSONCallBack paramJSONCallBack) {
        // TODO Auto-generated method stub
        flag = false;
        paramJSONCallBackbase = paramJSONCallBack;
        ThreadManager.getPool().execute(new Runnable() {
            public void run() {
                flag = false;
                if(Upload.uploadpic("http://23.94.123.193:8080/daijia/driver/register", picPath, driverYear, phone, driverRange, pointX, pointY)){

                /*String str = HttpUtils.getHttpData("http://23.94.123.193:8080/daijia/driver/register", paramListbase);
                Log.i("TAG", "R is :" + str);
                if (str != null) {
                    if (!StringUtils.isNullOrEmpty(str)) {*/
                            baseInfo = new BaseInfo();
                            baseInfo.setCode(true);
                            flag = true;

                }
                JSONServiceImpl.this.handler.post(new Runnable() {
                    public void run() {
                        if (flag) {
                            paramJSONCallBackbase.onSuccess(baseInfo);
                        } else {
                            paramJSONCallBackbase.onFail();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void searchNearDriver(List<NameValuePair> paramList,
                                 JSONCallBack paramJSONCallBack) {
        // TODO Auto-generated method stub
        flag = false;
        paramJSONCallBackbase = paramJSONCallBack;
        this.paramListbase = paramList;
        ThreadManager.getPool().execute(new Runnable() {
            public void run() {
                flag = false;
                String str = HttpUtils.getHttpData("http://23.94.123.193:8080/daijia/driver/searchNearDriver", paramListbase);
                Log.i("TAG", "R is :" + str);
                if (str != null) {
                    if (!StringUtils.isNullOrEmpty(str)) {
                        try {
                            driversInfo = new DriversInfo(new JSONObject(str));
                            flag = true;
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                JSONServiceImpl.this.handler.post(new Runnable() {
                    public void run() {
                        if (flag) {
                            paramJSONCallBackbase.onSuccess(driversInfo);
                        } else {
                            paramJSONCallBackbase.onFail();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void sendFindPwdCode(List<NameValuePair> paramList,
                                JSONCallBack paramJSONCallBack) {
        // TODO Auto-generated method stub
        this.flag = false;
        paramJSONCallBackbase = paramJSONCallBack;
        this.paramListbase = paramList;
        ThreadManager.getPool().execute(new Runnable() {
            public void run() {
                flag = false;
                String str = HttpUtils.getHttpData("http://23.94.123.193:8080/daijia/user/validate", paramListbase);
                Log.i("TAG", "R is :" + str);
                if (str != null) {
                    if (!StringUtils.isNullOrEmpty(str)) {
                        try {
                            baseInfo = new BaseInfo(new JSONObject(str));
                            flag = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                JSONServiceImpl.this.handler.post(new Runnable() {
                    public void run() {
                        if (flag) {
                            paramJSONCallBackbase.onSuccess(baseInfo);
                        } else {
                            paramJSONCallBackbase.onFail();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void sendRegCode(List<NameValuePair> paramList,
                            JSONCallBack paramJSONCallBack) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updatePwd(List<NameValuePair> paramList,
                          JSONCallBack paramJSONCallBack) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateUserInfo(List<NameValuePair> paramList, File paramFile,
                               JSONCallBack paramJSONCallBack) {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveuserinfo(List<NameValuePair> localArrayList,
                             JSONCallBack jsonCallBack) {
        // TODO Auto-generated method stub
        flag = false;
        paramJSONCallBackbase = jsonCallBack;
        this.paramListbase = localArrayList;
        ThreadManager.getPool().execute(new Runnable() {
            public void run() {
                String str = HttpUtils.getHttpData("http://23.94.123.193:8080/daijia/user/register", paramListbase);
                flag = false;
                if (str != null) {
                    if (!StringUtils.isNullOrEmpty(str)) {
                        try {
                            baseInfo = new BaseInfo(new JSONObject(str));
                            flag = true;
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                JSONServiceImpl.this.handler.post(new Runnable() {
                    public void run() {
                        if (flag) {
                            paramJSONCallBackbase.onSuccess(baseInfo);
                            return;
                        }
                        paramJSONCallBackbase.onFail();
                    }
                });
            }
        });
    }

    @Override
    public void changeDriver(List<NameValuePair> localArrayList, JSONCallBack jsonCallBack) {
        // TODO Auto-generated method stub
        flag = false;
        paramJSONCallBackbase = jsonCallBack;
        this.paramListbase = localArrayList;
        ThreadManager.getPool().execute(new Runnable() {
            public void run() {
                String str = HttpUtils.getHttpData("http://23.94.123.193:8080/daijia/driver/driverStatus", paramListbase);
                flag = false;
                if (str != null) {
                    if (!StringUtils.isNullOrEmpty(str)) {
                        try {
                            baseInfo = new BaseInfo(new JSONObject(str));
                            flag = true;
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                JSONServiceImpl.this.handler.post(new Runnable() {
                    public void run() {
                        if (flag) {
                            paramJSONCallBackbase.onSuccess(baseInfo);
                            return;
                        }
                        paramJSONCallBackbase.onFail();
                    }
                });
            }
        });
    }

    @Override
    public void findDriverByPhone(List<NameValuePair> localArrayList, JSONCallBack jsonCallBack) {
        flag = false;
        paramJSONCallBackbase = jsonCallBack;
        this.paramListbase = localArrayList;
        ThreadManager.getPool().execute(new Runnable() {
            public void run() {
                String str = HttpUtils.getHttpData("http://23.94.123.193:8080/daijia/driver/driverByPhone", paramListbase);
                flag = false;
                if (str != null) {
                    if (!StringUtils.isNullOrEmpty(str)) {
                        try {
                            baseInfo = new BaseInfo(new JSONObject(str));
                            flag = true;
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                JSONServiceImpl.this.handler.post(new Runnable() {
                    public void run() {
                        if (flag) {
                            paramJSONCallBackbase.onSuccess(baseInfo);
                            return;
                        }
                        paramJSONCallBackbase.onFail();
                    }
                });
            }
        });
    }

}
