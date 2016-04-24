package com.app.dabbawalashop.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.preference.PreferenceManager;

import com.app.dabbawalashop.api.output.AssociatedShopId;
import com.app.dabbawalashop.constant.AppConstant;
import com.google.gson.Gson;

import java.util.List;

public class PreferenceKeeper {

    private SharedPreferences prefs;
    private static PreferenceKeeper keeper;
    private static Context context;


    public static PreferenceKeeper getInstance() {
        if (keeper == null) {
            keeper = new PreferenceKeeper(context);
        }
        return keeper;
    }

    public String getApplicationVersion() {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;
        }
        catch(Exception e)
        {

        }
        return "";
    }

    private PreferenceKeeper(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setContext(Context context1) {
        context = context1;
    }

    public void setGCMReg(String gcmId) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.GCM_REG_ID, gcmId).commit();
    }

    public void setDeviceInfo(String info) {
        prefs.edit().putString("device_info", info).commit();
    }

    public String getDeviceInfo() {
        return prefs.getString("device_info", "");
    }

    public boolean getIsLogin() {
        return prefs.getBoolean(AppConstant.PreferenceKeeperNames.LOGIN, false);
    }

    public void setIsLogin(boolean isLogin) {
        prefs.edit().putBoolean(AppConstant.PreferenceKeeperNames.LOGIN, isLogin).commit();
    }

    public void setUserId(String userId) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.USER_ID, userId).commit();
    }

    public String getUserId() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.USER_ID, "");
    }

    public String getUserType() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.USER_TYPE, "");
    }

    public void setUserType(String userType) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.USER_TYPE, userType).commit();
    }

    public String getLocale() {
        String locale = prefs.getString(AppConstant.PreferenceKeeperNames.LOCALE, "");
        if(locale.equals("") || locale==null)
        return AppConstant.DEFAULT_LOCALE;
        else
           return locale;
    }

    public void setLocale(String locale) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.LOCALE, locale).commit();
    }

    public List<AssociatedShopId> getAssociatedShopId() {
        Gson gson = new Gson();
        String json = prefs.getString(AppConstant.PreferenceKeeperNames.ASSOCIATED_SHOP_ID, "");
        List<AssociatedShopId> obj = (List<AssociatedShopId>)gson.fromJson(json, List.class);
        return obj;
    }

    public void setAssociatedShopId(List<AssociatedShopId> listAssociatedShopId) {
        Gson gson = new Gson();
        String json = gson.toJson(listAssociatedShopId); // myObject - instance of MyObject
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.ASSOCIATED_SHOP_ID, json).commit();
    }


    public void clearData() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}
