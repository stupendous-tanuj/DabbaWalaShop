package com.app.dabbawalashop.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.adapter.AssociatedShopAdapter;
import com.app.dabbawalashop.api.output.AssociatedShop;
import com.app.dabbawalashop.api.output.AssociatedShopsResponse;
import com.app.dabbawalashop.api.output.ErrorObject;
import com.app.dabbawalashop.network.AppHttpRequest;
import com.app.dabbawalashop.network.AppRequestBuilder;
import com.app.dabbawalashop.network.AppResponseListener;
import com.app.dabbawalashop.network.AppRestClient;

import java.util.List;

public class AssociatedShopsActivity extends BaseActivity {


    private RecyclerView recycleView;
    private String shopIdORSellerHubId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associated_shops);
        setHeader("Associated Shops", "");
        setUI();
        setRecycler();
        associateShopsAPI();
        //TODO Add a Shop button
    }

    private void setUI() {
        recycleView = (RecyclerView) findViewById(R.id.recycle_view_associated_shops);
    }

    private void setRecycler() {
        recycleView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(mLayoutManager);
    }

    private void associateShopsAPI() {

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.associatedShopsAPI("N",new AppResponseListener<AssociatedShopsResponse>(AssociatedShopsResponse.class, this) {
            @Override
            public void onSuccess(AssociatedShopsResponse result) {
                hideProgressBar();
                setAdapterData(result.getAssociatedShops());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setAdapterData(List<AssociatedShop> associatedShop) {
        AssociatedShopAdapter adapter = new AssociatedShopAdapter(this, associatedShop, shopIdORSellerHubId);
        recycleView.setAdapter(adapter);

    }
}
