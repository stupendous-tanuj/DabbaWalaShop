package com.app.dabbawalashop.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.api.output.AssociatedShopIdResponse;
import com.app.dabbawalashop.api.output.CommonResponse;
import com.app.dabbawalashop.api.output.ErrorObject;
import com.app.dabbawalashop.constant.AppConstant;
import com.app.dabbawalashop.network.AppHttpRequest;
import com.app.dabbawalashop.network.AppRequestBuilder;
import com.app.dabbawalashop.network.AppResponseListener;
import com.app.dabbawalashop.network.AppRestClient;
import com.app.dabbawalashop.utils.PreferenceKeeper;

import java.util.ArrayList;
import java.util.List;

public class AddADeliveryLocationActivity extends BaseActivity {

    String USER_TYPE = "";
    EditText et_deliveryLocation;
    TextView tv_addDeliveryLocation;
    Spinner spinner_city;
    String city = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adelivery_location);
        setHeader(getString(R.string.header_add_a_delivery_location), "");
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        setUI();
        setCitySpinner();
    }

    private void setUI() {
        et_deliveryLocation = (EditText) findViewById(R.id.et_deliveryLocation);
        tv_addDeliveryLocation = (TextView) findViewById(R.id.tv_addDeliveryLocation);
        spinner_city = (Spinner) findViewById(R.id.spinner_city);
        findViewById(R.id.tv_addDeliveryLocation).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_addDeliveryLocation:
                addADeliveryLocationAPI();
                break;
        }
    }

    private void setCitySpinner()
    {
        final List<String> cityList = new ArrayList<>();
        cityList.add(AppConstant.CITY.CITY_PUNE);
        cityList.add(AppConstant.CITY.CITY_MUMBAI);
        cityList.add(AppConstant.CITY.CITY_HYD);
        cityList.add(AppConstant.CITY.CITY_BLR);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cityList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_city.setAdapter(dataAdapter);
        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                city = cityList.get(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    private void addADeliveryLocationAPI() {

        String deliveryLocation = et_deliveryLocation.getText().toString();

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.addADeliveryLocationAPI(deliveryLocation,city,new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar();
                showToast(result.getSuccessMessage());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    }
