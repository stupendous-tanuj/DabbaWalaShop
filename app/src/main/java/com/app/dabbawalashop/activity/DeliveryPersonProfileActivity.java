package com.app.dabbawalashop.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.api.output.DeliveryPerson;
import com.app.dabbawalashop.api.output.DeliveryPersonResponse;
import com.app.dabbawalashop.api.output.ErrorObject;
import com.app.dabbawalashop.constant.AppConstant;
import com.app.dabbawalashop.network.AppHttpRequest;
import com.app.dabbawalashop.network.AppRequestBuilder;
import com.app.dabbawalashop.network.AppResponseListener;
import com.app.dabbawalashop.network.AppRestClient;
import com.app.dabbawalashop.utils.PreferenceKeeper;

import java.net.URI;
import java.net.URL;

public class DeliveryPersonProfileActivity extends BaseActivity {

    TextView tv_dp_mobileNumber;
    TextView tv_dp_name;
    TextView tv_dp_registrationStatus;
    TextView tv_dp_residentialAddress;
    TextView tv_dp_IDType;
    TextView tv_dp_IDNumber;
    TextView tv_emailId;
    ImageView iv_deliveryPersonImage;
    String deliveryPersonMobileNumber = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_person);
        setHeader("Delivery Person Profile", "");
        setUI();
        fetchDeliveryPersonProfileAPI();
        if(PreferenceKeeper.getInstance().getUserType().equals(AppConstant.UserType.DELIVERY_PERSON_TYPE))
            deliveryPersonMobileNumber = PreferenceKeeper.getInstance().getUserId();
        else
            deliveryPersonMobileNumber = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.DELIVERY_PERSON_MOBILE);
    }

    public void setUI()
    {
         tv_dp_mobileNumber = (TextView) findViewById(R.id.tv_dp_mobileNumber);
         tv_dp_name = (TextView) findViewById(R.id.tv_dp_name);
         tv_dp_registrationStatus = (TextView) findViewById(R.id.tv_dp_registrationStatus);
         tv_dp_residentialAddress = (TextView) findViewById(R.id.tv_dp_residentialAddress);
         tv_dp_IDType = (TextView) findViewById(R.id.tv_dp_IDType);
         tv_dp_IDNumber = (TextView) findViewById(R.id.tv_dp_IDNumber);
         tv_emailId = (TextView) findViewById(R.id.tv_emailId);
         iv_deliveryPersonImage = (ImageView) findViewById(R.id.iv_deliveryPersonImage);
    }


    private void setDeliveryPersonData(DeliveryPerson deliveryPerson)
    {
        tv_dp_mobileNumber.setText(deliveryPerson.getDeliveryPersonMobileNumber());
        tv_dp_name.setText(deliveryPerson.getDeliveryPersonName());
        tv_dp_registrationStatus.setText(deliveryPerson.getDeliveryPersonRegistrationStatus());
        tv_dp_residentialAddress.setText(deliveryPerson.getDeliveryPersonResidentialAddress());
        tv_dp_IDType.setText(deliveryPerson.getDeliveryPersonIDType());
        tv_dp_IDNumber.setText(deliveryPerson.getDeliveryPersonIDNumber());
        tv_emailId.setText(deliveryPerson.getDeliveryPersonEmailId());

        try {
            URL imageURL = new URL(deliveryPerson.getDeliveryPersonImageURL());
            iv_deliveryPersonImage.setImageBitmap(BitmapFactory.decodeStream(imageURL.openConnection().getInputStream()));
        }
        catch(Exception e)
        {

        }
    }


    private void fetchDeliveryPersonProfileAPI() {

        AppHttpRequest request = AppRequestBuilder.fetchDeliveryPersonProfileAPI(deliveryPersonMobileNumber, new AppResponseListener<DeliveryPersonResponse>(DeliveryPersonResponse.class, this) {
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
