package com.app.dabbawalashop.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.adapter.AssociatedDeliveryLocationAdapter;
import com.app.dabbawalashop.api.output.AssociatedShopId;
import com.app.dabbawalashop.api.output.AssociatedShopIdResponse;
import com.app.dabbawalashop.api.output.DeliveryLocation;
import com.app.dabbawalashop.api.output.DeliveryLocationResponse;
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

public class AssociatedDeliveryLocationsActivity extends BaseActivity {


    private RecyclerView recycleView;
    private String shopIdORSellerHubId;
    private Spinner spinner_shopId;
    LinearLayout ll_shopId;
    String shopIdValue = "";
    String USER_TYPE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associated_delivery_locations);
        setHeader(getString(R.string.header_associated_delivery_locations), "");
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        setUI();
        setRecycler();

    }

    private void setUI() {
        recycleView = (RecyclerView) findViewById(R.id.recycle_view_associated_delivery_locations);

        findViewById(R.id.iv_associate_delivery_location).setOnClickListener(this);
        spinner_shopId = (Spinner) findViewById(R.id.spinner_shopId);
        ll_shopId = (LinearLayout) findViewById(R.id.ll_shopId);
        if((USER_TYPE.equals(AppConstant.UserType.DELIVERY_PERSON_TYPE)) || (USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE))) {
            ll_shopId.setVisibility(View.GONE);
            associateDeliveryLocationAPI();
        }
        else {
            ll_shopId.setVisibility(View.VISIBLE);
            fetchShopIdAPI();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_associate_delivery_location:
                launchActivity(AssociateADeliveryLocationActivity.class);
                break;
        }
    }

    private void setRecycler() {
        recycleView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(mLayoutManager);
    }

    private void setShopIdSpinner(List<AssociatedShopId> associatedShopId)
    {
        final List<String> shopId = new ArrayList<>();
        shopId.add(getString(R.string.please_select));
        for (int i = 0; i < associatedShopId.size(); i++)
            shopId.add(associatedShopId.get(i).getShopID());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, shopId);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_shopId.setAdapter(dataAdapter);
        spinner_shopId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                shopIdValue = shopId.get(pos);
                if(!shopIdValue.equals(getString(R.string.please_select)))
                associateDeliveryLocationAPI();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void fetchShopIdAPI() {

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.associatedShopId(new AppResponseListener<AssociatedShopIdResponse>(AssociatedShopIdResponse.class, this) {
            @Override
            public void onSuccess(AssociatedShopIdResponse result) {
                hideProgressBar();
                setShopIdSpinner(result.getAssociatedShops());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }


    private void associateDeliveryLocationAPI() {

        if(USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE)) {
            shopIdValue = PreferenceKeeper.getInstance().getUserId();
        }

        if (!DialogUtils.isSpinnerDefaultValue(this, shopIdValue, "Shop ID")) {
            return;
        }

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.associatedDeliveryLocationAPI( shopIdValue, new AppResponseListener<DeliveryLocationResponse>(DeliveryLocationResponse.class, this) {
            @Override
            public void onSuccess(DeliveryLocationResponse result) {
                hideProgressBar();
                setAdapterData(result.getDeliveryLocations());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setAdapterData(List<DeliveryLocation> deliveryLocation) {
        AssociatedDeliveryLocationAdapter adapter = new AssociatedDeliveryLocationAdapter(this, deliveryLocation, shopIdValue);
        recycleView.setAdapter(adapter);

    }
}
