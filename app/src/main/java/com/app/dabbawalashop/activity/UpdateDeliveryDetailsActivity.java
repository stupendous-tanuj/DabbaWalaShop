package com.app.dabbawalashop.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.adapter.CartDetailAdapter;
import com.app.dabbawalashop.api.output.CartDetail;
import com.app.dabbawalashop.api.output.CommonResponse;
import com.app.dabbawalashop.api.output.DeliveryDetail;
import com.app.dabbawalashop.api.output.DeliveryDetailResponse;
import com.app.dabbawalashop.api.output.DeliveryPersonResponse;
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

public class UpdateDeliveryDetailsActivity extends BaseActivity {

    TextView tv_update_delivery;
    TextView tv_orderId;
    TextView tv_orderStatus;
    TextView tv_deliveryDate;
    TextView tv_deliveryStatus;
    TextView tv_shippedBy;
    TextView tv_invoiceAmount;
    TextView tv_balanceAmount;
    Spinner spinner_deliveryDates;
    Spinner spinner_toDeliveryStatus;
    Spinner spinner_beingShippedBy;
    LinearLayout ll_deliveryDetailLayout;
    LinearLayout ll_beingShippedBy;
    LinearLayout ll_shippedBy;
    LinearLayout ll_balanceAmountAdjusted;
    CheckBox cb_balanceAmountAdjusted;
    String orderIdValue = "";
    String orderStatusValue = "";
    String deliveryPerson = "";
    String deliveryDate = "";
    String toDeliveryStatusValue = "";
    String deliveryStatus = "";
    private RecyclerView recycleView;
    String shopId = "";
    String deliveryDatesValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delivery_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setUI();
        setHeader("Update Delivery Detail", "");
        setUIListener();
        getIntentData();
        setData(deliveryDate);
        setRecycler();
        if(!PreferenceKeeper.getInstance().getUserType().equals(AppConstant.UserType.DELIVERY_PERSON_TYPE))
        fetchDeliveryPersonAPI();
    }

    public void setUI() {
        recycleView = (RecyclerView) findViewById(R.id.recycle_view_cart_details);
        tv_update_delivery = (TextView) findViewById(R.id.tv_update_delivery);
        tv_orderId = (TextView) findViewById(R.id.tv_orderId);
        tv_orderStatus = (TextView) findViewById(R.id.tv_orderStatus);
        tv_deliveryDate = (TextView) findViewById(R.id.tv_deliveryDate);
        tv_deliveryStatus = (TextView) findViewById(R.id.tv_deliveryStatus);
        tv_shippedBy = (TextView) findViewById(R.id.tv_shippedBy);
        tv_invoiceAmount = (TextView) findViewById(R.id.tv_invoiceAmount);
        tv_balanceAmount = (TextView) findViewById(R.id.tv_balanceAmount);
        spinner_deliveryDates = (Spinner) findViewById(R.id.spinner_deliveryDates);
        spinner_toDeliveryStatus = (Spinner) findViewById(R.id.spinner_toDeliveryStatus);
        spinner_beingShippedBy = (Spinner) findViewById(R.id.spinner_beingShippedBy);
        ll_deliveryDetailLayout = (LinearLayout) findViewById(R.id.ll_deliveryDetailLayout);
        ll_beingShippedBy = (LinearLayout) findViewById(R.id.ll_beingShippedBy);
        ll_shippedBy = (LinearLayout) findViewById(R.id.ll_shippedBy);
        ll_balanceAmountAdjusted = (LinearLayout) findViewById(R.id.ll_balanceAmountAdjusted);
        cb_balanceAmountAdjusted = (CheckBox) findViewById(R.id.cb_balanceAmountAdjusted);
        ll_beingShippedBy.setVisibility(View.GONE);
        ll_shippedBy.setVisibility(View.GONE);
        ll_balanceAmountAdjusted.setVisibility(View.GONE);
    }

    private void setUIListener() {
        tv_update_delivery.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_update_delivery:
                updateDeliveryStatusAPI();
                break;
        }
    }


    private void getIntentData() {
        if(getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.TODAYS_DELIVERY_DATE_FLAG).equals("1")) {
            deliveryDate = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.DELIVERY_DATE);
            shopId = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.OrderPlacedTo);
            orderIdValue = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.ORDER_ID);
            orderStatusValue = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.ORDER_STATUS);
            deliveryDatesValue = deliveryDate+",";
        }
        else
        {
            deliveryDate = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.DELIVERY_DATE);
            shopId = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.SHOP_ID).split(" ")[0];
            orderIdValue = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.ORDER_ID);
            orderStatusValue = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.ORDER_STATUS);
            deliveryDatesValue = getIntent ().getExtras().getString(AppConstant.BUNDLE_KEY.DeliveryDates);
        }

    }

    private void setRecycler() {
        recycleView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(mLayoutManager);
    }

    public void updateDeliveryStatusAPI()
    {
        String additionalFieldValue = "";
        if(toDeliveryStatusValue.equals(AppConstant.STATUS.STATUS_PREPARED))
            additionalFieldValue = spinner_beingShippedBy.getSelectedItem().toString().split(" ")[0];
        if(toDeliveryStatusValue.equals(AppConstant.STATUS.STATUS_CLOSED))
            additionalFieldValue = cb_balanceAmountAdjusted.isChecked() == true ? getString(R.string.yes) :  getString(R.string.no);

        if (!DialogUtils.isUpdateDeliveryDetailsVerification(this, toDeliveryStatusValue)) {
            return;
        }

        showProgressBar(findViewById(R.id.tv_update_delivery));
        AppHttpRequest request = AppRequestBuilder.updateDeliveryStatusAPI(orderIdValue, deliveryStatus, toDeliveryStatusValue, deliveryDate, additionalFieldValue, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar(findViewById(R.id.tv_update_delivery));
                showToast(result.getSuccessMessage());
                fetchDeliveryDetailApi(deliveryDate);
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar(findViewById(R.id.tv_update_delivery));
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setDeliveryDetailData(DeliveryDetailResponse result) {
        DeliveryDetail deliveryDetail = result.getDeliveryDetails().get(0);
        deliveryStatus = deliveryDetail.getDeliveryStatus();
        tv_deliveryStatus.setText(deliveryDetail.getDeliveryStatus());

        tv_invoiceAmount.setText(deliveryDetail.getInvoiceAmount());
        tv_balanceAmount.setText(deliveryDetail.getBalanceAmount());
        if(deliveryStatus.equals(AppConstant.STATUS.STATUS_DISPATCHED) || deliveryStatus.equals(AppConstant.STATUS.STATUS_CLOSED))
        {
            ll_shippedBy.setVisibility(View.VISIBLE);
            tv_shippedBy.setText(deliveryDetail.getDeliveryPerson());
        }

        String toDeliveryStatusValues[] = deliveryDetail.getToDeliveryStatus().split(",");
        final List<String> toDeliveryStatus = new ArrayList<>();
        for (int i = 0; i < toDeliveryStatusValues.length; i++)
            toDeliveryStatus.add(toDeliveryStatusValues[i]);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, toDeliveryStatus);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_toDeliveryStatus.setAdapter(dataAdapter);
        spinner_toDeliveryStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                toDeliveryStatusValue = toDeliveryStatus.get(pos);
                if (PreferenceKeeper.getInstance().getUserType().equals(AppConstant.UserType.SHOP_TYPE)) {
                    if (toDeliveryStatusValue.equals(AppConstant.STATUS.STATUS_PREPARED)) {
                        ll_beingShippedBy.setVisibility(View.VISIBLE);
                    } else {
                        ll_beingShippedBy.setVisibility(View.GONE);
                    }
                    if (toDeliveryStatusValue.equals(AppConstant.STATUS.STATUS_CLOSED)) {
                        ll_balanceAmountAdjusted.setVisibility(View.VISIBLE);
                    } else {
                        ll_balanceAmountAdjusted.setVisibility(View.GONE);
                    }
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }



    private void fetchDeliveryPersonAPI() {
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.associatedDeliveryPersonAPI(shopId, "0", new AppResponseListener<DeliveryPersonResponse>(DeliveryPersonResponse.class, this) {
            @Override
            public void onSuccess(DeliveryPersonResponse result) {
                hideProgressBar();
                setDeliveryPersonDropDown(result);
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    public void setDeliveryPersonDropDown(DeliveryPersonResponse response)
    {
        final List<String> deliveryPersonList = new ArrayList<>();
        for (int i = 0; i < response.getDeliveryPerson().size(); i++)
            deliveryPersonList.add(response.getDeliveryPerson().get(i).getDeliveryPersonMobileNumber()+" "+response.getDeliveryPerson().get(i).getDeliveryPersonName());

        ArrayAdapter<String> deliveryDateDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, deliveryPersonList);
        deliveryDateDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_beingShippedBy.setAdapter(deliveryDateDataAdapter);
        spinner_beingShippedBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                deliveryPerson = deliveryPersonList.get(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void fetchDeliveryDetailApi(String deliveryDateValue) {
        System.out.print(deliveryDateValue);
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchDeliveryDetailAPI(orderIdValue, deliveryDateValue, new AppResponseListener<DeliveryDetailResponse>(DeliveryDetailResponse.class, this) {
            @Override
            public void onSuccess(DeliveryDetailResponse result) {
                setDeliveryDetailData(result);
                hideProgressBar();
                setAdapterData(result.getCartDetails());
            }
            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }


    public void setData(String deliveryDateValue) {

        tv_orderId.setText(orderIdValue);
        tv_orderStatus.setText(orderStatusValue);
        String deliveryDates[] = deliveryDatesValue.split(",");
        final List<String> deliveryDateList = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < deliveryDates.length; i++) {
            deliveryDateList.add(deliveryDates[i]);
            if(deliveryDateValue.equals(deliveryDates[i]))
              j =  i;
        }

        ArrayAdapter<String> deliveryDateDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, deliveryDateList);
        deliveryDateDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_deliveryDates.setAdapter(deliveryDateDataAdapter);
        spinner_deliveryDates.setSelection(j);
        spinner_deliveryDates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                deliveryDate = deliveryDateList.get(pos);
                fetchDeliveryDetailApi(deliveryDate);
                tv_deliveryDate.setText(deliveryDate);

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setAdapterData(List<CartDetail> cartDetail) {
        CartDetailAdapter adapter = new CartDetailAdapter(this, cartDetail);
        recycleView.setAdapter(adapter);

    }
}
