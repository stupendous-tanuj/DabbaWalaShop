package com.app.dabbawalashop.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.api.output.CommonResponse;
import com.app.dabbawalashop.api.output.ErrorObject;
import com.app.dabbawalashop.constant.AppConstant;
import com.app.dabbawalashop.network.AppHttpRequest;
import com.app.dabbawalashop.network.AppRequestBuilder;
import com.app.dabbawalashop.network.AppResponseListener;
import com.app.dabbawalashop.network.AppRestClient;
import com.app.dabbawalashop.utils.DialogUtils;
import com.app.dabbawalashop.utils.PreferenceKeeper;

import java.util.ArrayList;
import java.util.List;

public class ForgetPasswordActivity extends BaseActivity {

    EditText et_name = null;
    EditText et_emailId = null;
    EditText et_mobileNumber = null;
    EditText et_ownerIdType = null;
    EditText et_idNumber = null;
    TextView tv_resetPassword = null;
    private EditText et_userId;
    private Spinner spinner_userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        setHeader("Forget Password", "");
        setUI();
        setUIListener();
        //TODO Complete Forget Password mechanism

    }

    public void setUI()
    {
        et_name = (EditText) findViewById(R.id.et_oldPassword);
        et_emailId = (EditText) findViewById(R.id.et_emailId);
        et_mobileNumber = (EditText) findViewById(R.id.et_mobileNumber);
        et_ownerIdType = (EditText) findViewById(R.id.et_ownerIdType);
        et_idNumber = (EditText) findViewById(R.id.et_idNumber);
        tv_resetPassword = (TextView) findViewById(R.id.tv_resetPassword);
        et_userId = (EditText) findViewById(R.id.et_userId);
        spinner_userType = (Spinner) findViewById(R.id.spinner_userType);

        final List<String> userTypeList = new ArrayList<>();
        userTypeList.add(0, AppConstant.UserType.SELLER_HUB_TYPE);
        userTypeList.add(1, AppConstant.UserType.SHOP_TYPE);
        userTypeList.add(2, AppConstant.UserType.DELIVERY_PERSON_TYPE);

    }

    public void setUIListener()
    {
        findViewById(R.id.tv_resetPassword).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_resetPassword:
                resetPasswordAPI();
                break;
        }
    }

    private void resetPasswordAPI() {

        String name = et_name.getText().toString().trim();
        String emailId = et_emailId.getText().toString().trim();
        String mobileNumber = et_mobileNumber.getText().toString().trim();
        String idType = et_ownerIdType.getText().toString().trim();
        String idNumber = et_idNumber.getText().toString().trim();
        String userId =  et_userId .getText().toString().trim();
        String userType =  et_userId .getText().toString().trim();

        if (!DialogUtils.isResetPasswordVerification(this, name, emailId, mobileNumber, idNumber, userId)) {
            return;
        }

        showProgressBar(findViewById(R.id.tv_resetPassword));
        AppHttpRequest request = AppRequestBuilder.resetPasswordAPI(name, emailId, mobileNumber,idType,idNumber, userId,userType, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar();
                showToast(result.getSuccessMessage());
                finish();
                launchActivity(LoginActivity.class);
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });

        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

}
