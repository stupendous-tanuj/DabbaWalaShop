package com.app.dabbawalashop.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.listner.IDialogListener;
import com.app.dabbawalashop.network.AppRestClient;
import com.app.dabbawalashop.notification.GcmIdGenerator;
import com.app.dabbawalashop.utils.AppUtil;
import com.app.dabbawalashop.utils.DialogUtils;
import com.app.dabbawalashop.utils.PreferenceKeeper;

import java.util.Locale;


public class SplashActivity extends BaseActivity {

    private static final long SPLASH_TIME_OUT = 1500;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        setDeviceInfo();
       // getGCMRegId();
        setHandler();
    }

    private void setDeviceInfo() {
        PreferenceKeeper.getInstance().setDeviceInfo(AppUtil.getDeviceId(this));
    }


    private void setHandler() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (AppUtil.isNetworkAvailable(SplashActivity.this)) {
                    boolean isUserLogin = PreferenceKeeper.getInstance().getIsLogin();
                    if (isUserLogin) {
                        launchActivityMain(HomeActivity.class);
                       // verifyAppApi();
                    } else {
                        launchActivityMain(LoginActivity.class);
                    }
                    finish();
                } else {
                    DialogUtils.showDialogNetwork(SplashActivity.this, getString(R.string.not_network), new IDialogListener() {
                        @Override
                        public void onClickOk() {
                            finish();
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                }
            }
        };
        handler.postDelayed(runnable, SPLASH_TIME_OUT);
    }

    private void getGCMRegId() {
        GcmIdGenerator gcm = new GcmIdGenerator(SplashActivity.this);
        if (AppUtil.checkPlayServices(this)) {
            gcm.getGCMRegId();
        }
    }

    @Override
    public void onBackPressed() {
        AppRestClient.getClient().cancelAll(TAG);
        handler.removeCallbacks(runnable);
        super.onBackPressed();
    }
/*
    private void verifyAppApi() {
        AppHttpRequest request = AppRequestBuilder.verifyApp(new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                launchActivityMain(HomeActivity.class);
            }

            @Override
            public void onError(ErrorObject error) {
                DialogUtils.showDialog(SplashActivity.this, error.getErrorMessage());
            }
        });

        AppRestClient.getClient().sendRequest(this, request, TAG);
    }*/
}
