package com.app.dabbawalashop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.api.output.CommonResponse;
import com.app.dabbawalashop.api.output.ErrorObject;
import com.app.dabbawalashop.network.AppHttpRequest;
import com.app.dabbawalashop.network.AppRequestBuilder;
import com.app.dabbawalashop.network.AppResponseListener;
import com.app.dabbawalashop.network.AppRestClient;
import com.app.dabbawalashop.utils.DialogUtils;
import com.app.dabbawalashop.utils.PreferenceKeeper;

public class ChangePasswordActivity extends BaseActivity {

    EditText et_oldPassword;
    EditText et_newPassword;
    EditText et_confirmNewPassword;
    TextView tv_changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setHeader("Change Password", "");
        setUI();
        setUIListener();
    }

    public void setUI()
    {
        et_oldPassword = (EditText) findViewById(R.id.et_oldPassword);
        et_newPassword = (EditText) findViewById(R.id.et_newPassword);
        et_confirmNewPassword = (EditText) findViewById(R.id.et_confirmNewPassword);

    }

    public void setUIListener()
    {
        findViewById(R.id.tv_changePassword).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_changePassword:
                changePasswordAPI();
                break;
        }
    }

    private void changePasswordAPI() {
        final String oldPassword = et_oldPassword.getText().toString().trim();
        final String newPassword = et_newPassword.getText().toString().trim();
        final String confNewPassword = et_confirmNewPassword.getText().toString().trim();
        if (!DialogUtils.isChangePasswordVerification(this, oldPassword, newPassword, confNewPassword)) {
            return;
        }

        showProgressBar(findViewById(R.id.tv_login_login));
        AppHttpRequest request = AppRequestBuilder.changePasswordAPI(oldPassword, newPassword, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar();
                showToast(result.getSuccessMessage());
                finish();
                launchActivity(HomeActivity.class);
                PreferenceKeeper.getInstance().setIsLogin(true);
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });

        AppRestClient.getClient().sendRequest(this, request, TAG);
    }
}
