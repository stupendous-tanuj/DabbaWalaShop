package com.app.dabbawalashop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.adapter.AssociatedProductAdapter;
import com.app.dabbawalashop.api.output.AssociatedProductResponse;
import com.app.dabbawalashop.api.output.AssociatedShopId;
import com.app.dabbawalashop.api.output.AssociatedShopIdResponse;
import com.app.dabbawalashop.api.output.ErrorObject;
import com.app.dabbawalashop.api.output.Product;
import com.app.dabbawalashop.constant.AppConstant;
import com.app.dabbawalashop.network.AppHttpRequest;
import com.app.dabbawalashop.network.AppRequestBuilder;
import com.app.dabbawalashop.network.AppResponseListener;
import com.app.dabbawalashop.network.AppRestClient;
import com.app.dabbawalashop.utils.DialogUtils;
import com.app.dabbawalashop.utils.PreferenceKeeper;

import java.util.ArrayList;
import java.util.List;

public class AssociatedProductActivity extends BaseActivity {

    private ListView lv_associated_product;
    private TextView tv_registrationStatus;
    private TextView tv_shopId;
    private Spinner spinner_registrationStatus;
    private Spinner spinner_shopId;
    String USER_TYPE = "";
    String shopIdValue = "";
    String productStatusValue = "ALL";
    LinearLayout ll_shopId;
    LinearLayout ll_registrationStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associated_product);
        setUI();
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        setHeader("Products - "+USER_TYPE, "");
        if(USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE)) {
            fetchAssociatedProductListApi();
        }
        else if(USER_TYPE.equals(AppConstant.UserType.SELLER_HUB_TYPE)) {
            associateShopIdAPI();
        }
    }

    // update product
    // AssociateProduct api for addition and update 7
    @Override
    protected void onResume() {
        super.onResume();
       // fetchAssociatedProductListApi();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_associated_product:
                Bundle bundle = new Bundle();
                bundle.putBoolean(AppConstant.BUNDLE_KEY.IS_ADD_ASSOCIATE_PRODUCT_HOME, true);
                launchActivity(AssociateAProductActivity.class, bundle);
                break;
        }
    }

    private void setUI() {
        lv_associated_product = (ListView) findViewById(R.id.lv_associated_product);
        findViewById(R.id.iv_add_associated_product).setOnClickListener(this);
        tv_registrationStatus = (TextView) findViewById(R.id.tv_registrationStatus);
        tv_shopId = (TextView) findViewById(R.id.tv_shopId);
        spinner_registrationStatus = (Spinner) findViewById(R.id.spinner_registrationStatus);
        spinner_shopId = (Spinner) findViewById(R.id.spinner_shopId);
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        ll_shopId = (LinearLayout) findViewById(R.id.ll_shopId);
        ll_registrationStatus = (LinearLayout) findViewById(R.id.ll_registrationStatus);
        if((USER_TYPE.equals(AppConstant.UserType.DELIVERY_PERSON_TYPE)) || (USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE)))
        {
            ll_shopId.setVisibility(View.GONE);
            ll_registrationStatus.setVisibility(View.GONE);
            tv_registrationStatus.setVisibility(View.GONE);
            tv_shopId.setVisibility(View.GONE);
            spinner_registrationStatus.setVisibility(View.GONE);
            spinner_shopId.setVisibility(View.GONE);
        }
        else
        {
            ll_shopId.setVisibility(View.VISIBLE);
            ll_registrationStatus.setVisibility(View.VISIBLE);
            tv_registrationStatus.setVisibility(View.VISIBLE);
            tv_shopId.setVisibility(View.VISIBLE);
            spinner_registrationStatus.setVisibility(View.VISIBLE);
            spinner_shopId.setVisibility(View.VISIBLE);
        }
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
                fetchAssociatedProductListApi();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final List<String> productStatus = new ArrayList<>();
        productStatus.add(AppConstant.STATUS.STATUS_ALL);
        productStatus.add(AppConstant.STATUS.STATUS_APPROVED);
        productStatus.add(AppConstant.STATUS.STATUS_REGISTERED);
        productStatus.add(AppConstant.STATUS.STATUS_REJECTED);

        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, productStatus);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_registrationStatus.setAdapter(dataAdapter);
        spinner_registrationStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                productStatusValue = productStatus.get(pos);
                fetchAssociatedProductListApi();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }



    private void associateShopIdAPI() {

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


    private void fetchAssociatedProductListApi() {

        if(USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE)) {
            shopIdValue = PreferenceKeeper.getInstance().getUserId();
        }

        if (!DialogUtils.isSpinnerDefaultValue(this, shopIdValue, "Shop ID") || (shopIdValue.equals(""))) {
            return;
        }

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchAssociatedProductListAPI(shopIdValue,productStatusValue, new AppResponseListener<AssociatedProductResponse>(AssociatedProductResponse.class, this) {
            @Override
            public void onSuccess(AssociatedProductResponse result) {
                hideProgressBar();
                setAssociatedProductAdapter(result.getProducts());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setAssociatedProductAdapter(List<Product> associatedProducts) {
        AssociatedProductAdapter associatedProductAdapter = new AssociatedProductAdapter(this, associatedProducts, shopIdValue);
        lv_associated_product.setAdapter(associatedProductAdapter);
    }

}
