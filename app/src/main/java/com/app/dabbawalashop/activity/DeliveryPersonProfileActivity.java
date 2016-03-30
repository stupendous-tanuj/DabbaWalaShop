package com.app.dabbawalashop.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.api.output.DeliveryPerson;
import com.app.dabbawalashop.api.output.DeliveryPersonResponse;
import com.app.dabbawalashop.api.output.ErrorObject;
import com.app.dabbawalashop.network.AppHttpRequest;
import com.app.dabbawalashop.network.AppRequestBuilder;
import com.app.dabbawalashop.network.AppResponseListener;
import com.app.dabbawalashop.network.AppRestClient;

public class DeliveryPersonProfileActivity extends BaseActivity {

    TextView tv_dp_mobileNumber;
    TextView tv_dp_name;
    TextView tv_dp_registrationStatus;
    TextView tv_dp_residentialAddress;
    TextView tv_dp_IDType;
    TextView tv_dp_IDNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_person);
        setHeader("Delivery Person Profile", "");
        setUI();
        fetchDeliveryPersonProfileAPI();

    }

    public void setUI()
    {
         tv_dp_mobileNumber = (TextView) findViewById(R.id.tv_dp_mobileNumber);
         tv_dp_name = (TextView) findViewById(R.id.tv_dp_name);
         tv_dp_registrationStatus = (TextView) findViewById(R.id.tv_dp_registrationStatus);
         tv_dp_residentialAddress = (TextView) findViewById(R.id.tv_dp_residentialAddress);
         tv_dp_IDType = (TextView) findViewById(R.id.tv_dp_IDType);
         tv_dp_IDNumber = (TextView) findViewById(R.id.tv_dp_IDNumber);
    }


    private void setDeliveryPersonData(DeliveryPerson deliveryPerson)
    {
        tv_dp_mobileNumber.setText(deliveryPerson.getDeliveryPersonMobileNumber());
        tv_dp_name.setText(deliveryPerson.getDeliveryPersonName());
        tv_dp_registrationStatus.setText(deliveryPerson.getDeliveryPersonRegistrationStatus());
        tv_dp_residentialAddress.setText(deliveryPerson.getDeliveryPersonResidentialAddress());
        tv_dp_IDType.setText(deliveryPerson.getDeliveryPersonIDType());
        tv_dp_IDNumber.setText(deliveryPerson.getDeliveryPersonIDNumber());
    }


    private void fetchDeliveryPersonProfileAPI() {

        AppHttpRequest request = AppRequestBuilder.fetchDeliveryPersonProfileAPI(new AppResponseListener<DeliveryPersonResponse>(DeliveryPersonResponse.class, this) {
            @Override
            public void onSuccess(DeliveryPersonResponse result) {
                hideProgressBar(findViewById(R.id.tv_contact_us_send));
                setDeliveryPersonData(result.getDeliveryPerson().get(0));
            }

            @Override
            public void onError(ErrorObject error) {

            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

}
