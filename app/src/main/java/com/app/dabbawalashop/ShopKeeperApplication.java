package com.app.dabbawalashop;

import android.app.Application;

import com.app.dabbawalashop.network.AppRestClient;
import com.app.dabbawalashop.utils.PreferenceKeeper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ShopKeeperApplication extends Application {

    private static ShopKeeperApplication _instance;
    private ImageLoader imgLoader;

    public static ShopKeeperApplication get() {
        return _instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        imgLoader = ImageLoader.getInstance();
        imgLoader.init(ImageLoaderConfiguration.createDefault(this));
        initializeVolley();
        PreferenceKeeper.setContext(getApplicationContext());
        AppRestClient.init(getApplicationContext());
    }


    private void initializeVolley() {
        AppRestClient.init(getBaseContext());
    }
}
