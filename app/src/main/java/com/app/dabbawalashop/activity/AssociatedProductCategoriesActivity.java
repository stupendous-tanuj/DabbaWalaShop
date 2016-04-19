package com.app.dabbawalashop.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.adapter.AssociatedProductCategoryAdapter;
import com.app.dabbawalashop.api.output.AssociatedProductCategory;
import com.app.dabbawalashop.api.output.AssociatedProductCategoryResponse;
import com.app.dabbawalashop.api.output.AssociatedShopId;
import com.app.dabbawalashop.api.output.AssociatedShopIdResponse;
import com.app.dabbawalashop.api.output.ErrorObject;
import com.app.dabbawalashop.api.output.ShopOperationalTime;
import com.app.dabbawalashop.constant.AppConstant;
import com.app.dabbawalashop.network.AppHttpRequest;
import com.app.dabbawalashop.network.AppRequestBuilder;
import com.app.dabbawalashop.network.AppResponseListener;
import com.app.dabbawalashop.network.AppRestClient;
import com.app.dabbawalashop.utils.DialogUtils;
import com.app.dabbawalashop.utils.PreferenceKeeper;

import java.util.ArrayList;
import java.util.List;

public class AssociatedProductCategoriesActivity extends BaseActivity {


    private RecyclerView recycleView;
    private Spinner spinner_shopId;
    private TextView no_data_available;
    LinearLayout ll_shopId;
    String shopIdValue = "";
    String USER_TYPE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associated_product_categories);
        setHeader(getString(R.string.header_associated_products_categories), "");
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        setUI();
        setRecycler();

    }

    private void setUI() {
        no_data_available = (TextView) findViewById(R.id.no_data_available);
        recycleView = (RecyclerView) findViewById(R.id.recycle_view_associated_product_catgories);
        findViewById(R.id.iv_associateAProductCategory).setOnClickListener(this);
        spinner_shopId = (Spinner) findViewById(R.id.spinner_shopId);
        ll_shopId = (LinearLayout) findViewById(R.id.ll_shopId);
        if((USER_TYPE.equals(AppConstant.UserType.DELIVERY_PERSON_TYPE)) || (USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE))) {
            ll_shopId.setVisibility(View.GONE);
            fetchAssociatedProductCategoriesAPI();
        }
        else {
            ll_shopId.setVisibility(View.VISIBLE);
            fetchShopIdAPI();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_associateAProductCategory:
                launchActivity(AssociateAProductCategoryActivity.class);
                break;
        }
    }

    private void setRecycler() {
        recycleView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(mLayoutManager);
    }

    private void setVisibleUI(List<AssociatedProductCategory> associatedProductCategory) {
        if (associatedProductCategory == null || associatedProductCategory.size() == 0) {
            recycleView.setVisibility(View.GONE);
            no_data_available.setVisibility(View.VISIBLE);
            no_data_available.setText(getString(R.string.no_record_found));
        } else {
            recycleView.setVisibility(View.VISIBLE);
            no_data_available.setVisibility(View.GONE);
            setAdapterData(associatedProductCategory);
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
                if (!shopIdValue.equals(getString(R.string.please_select)))
                    fetchAssociatedProductCategoriesAPI();
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

    public void fetchAssociatedProductCategoriesAPI()
    {

        if(USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE)) {
            shopIdValue = PreferenceKeeper.getInstance().getUserId();
        }

        if (!DialogUtils.isSpinnerDefaultValue(this, shopIdValue, "Shop ID")) {
            return;
        }

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchAssociatedProductCategoriesAPI(shopIdValue, new AppResponseListener<AssociatedProductCategoryResponse>(AssociatedProductCategoryResponse.class, this) {
            @Override
            public void onSuccess(AssociatedProductCategoryResponse result) {
                hideProgressBar();
                setVisibleUI(result.getAssociatedProductCategory());
            }

            @Override
            public void onError(ErrorObject error) {

                hideProgressBar();
                setVisibleUI(null);
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setAdapterData(List<AssociatedProductCategory> associatedProductCategory) {
        AssociatedProductCategoryAdapter adapter = new AssociatedProductCategoryAdapter(this, associatedProductCategory, shopIdValue);
        recycleView.setAdapter(adapter);

    }
}
