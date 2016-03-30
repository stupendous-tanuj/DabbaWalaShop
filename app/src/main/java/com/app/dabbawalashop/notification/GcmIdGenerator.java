package com.app.dabbawalashop.notification;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.app.dabbawalashop.activity.BaseActivity;
import com.app.dabbawalashop.activity.HomeActivity;
import com.app.dabbawalashop.api.output.CommonResponse;
import com.app.dabbawalashop.api.output.ErrorObject;
import com.app.dabbawalashop.constant.AppConstant;
import com.app.dabbawalashop.network.AppHttpRequest;
import com.app.dabbawalashop.network.AppRequestBuilder;
import com.app.dabbawalashop.network.AppResponseListener;
import com.app.dabbawalashop.network.AppRestClient;
import com.app.dabbawalashop.utils.PreferenceKeeper;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;


public class GcmIdGenerator {

    private String TAG = HomeActivity.class.getSimpleName();
    private final BaseActivity context;
    private final PreferenceKeeper prefs;
    private GoogleCloudMessaging gcm;

    public GcmIdGenerator(BaseActivity context) {
        this.context = context;
        prefs = PreferenceKeeper.getInstance();
        //TODO Update GCM Id
    }

    /**
     * generates a gcm registration id
     */
    public void getGCMRegId() {
        new GCMIdGeneratorTask().execute();
    }

    private class GCMIdGeneratorTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String regid = "";
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }
                regid = gcm.register(AppConstant.GCM_ID);

            } catch (IOException ex) {

            }
            Log.i(TAG, "REG ID : " + regid);

            return regid;
        }

        @Override
        protected void onPostExecute(String regid) {
            super.onPostExecute(regid);
            prefs.setGCMReg(regid);
            if (PreferenceKeeper.getInstance().getIsLogin()){}
            updateGCMID(regid);
        }
    }

    private void updateGCMID(final String regId) {
        AppHttpRequest request = AppRequestBuilder.updateGCMIdAPI(regId, new AppResponseListener<CommonResponse>(CommonResponse.class, context) {
            @Override
            public void onSuccess(CommonResponse result) {
                prefs.setGCMReg(regId);
            }

            @Override
            public void onError(ErrorObject error) {

            }
        });

        AppRestClient.getClient().sendRequest(context, request, TAG);
    }
}
