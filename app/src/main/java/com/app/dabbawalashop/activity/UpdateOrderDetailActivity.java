package com.app.dabbawalashop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.api.output.CommonResponse;
import com.app.dabbawalashop.api.output.ErrorObject;
import com.app.dabbawalashop.api.output.MyOrderDetailResponse;
import com.app.dabbawalashop.api.output.OrderDetail;
import com.app.dabbawalashop.constant.AppConstant;
import com.app.dabbawalashop.network.AppHttpRequest;
import com.app.dabbawalashop.network.AppRequestBuilder;
import com.app.dabbawalashop.network.AppResponseListener;
import com.app.dabbawalashop.network.AppRestClient;
import com.app.dabbawalashop.utils.DialogUtils;
import com.app.dabbawalashop.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class UpdateOrderDetailActivity extends BaseActivity {

    public TextView orderId;
    public TextView orderPlacedBy;
    public TextView orderPlacedTo;
    public TextView orderQuotedAmount;
    public TextView orderDeliveryType;
    public TextView orderPaymentMethod;
    public TextView orderPaymentStatus;
    public TextView orderStatus;
    public TextView orderDeliveryAddressIdentifier;
    public TextView tv_orderSubscriptionType;
    public TextView tv_amountAdjustmentDone;
    public TextView tv_orderBalanceAmount;
    public TextView tv_orderInvoiceAmount;
    public TextView tv_orderCancellationReason;
    public TextView tv_my_order_order_time;
    public TextView tv_update_order;
    public TextView tv_deliveryDates;
    public EditText et_orderCancellationReason;
    public EditText et_orderInvoiceAmount;
    public Spinner spinner_toOrderStatus;
    public Spinner spinner_deliveryDates;
    public LinearLayout ll_orderCancellationReason;
    public LinearLayout ll_orderInvoiceAmount;
    public LinearLayout ll_amountAdjusted;
    public LinearLayout ll_balanceAmount;
    String orderIdValue = "";
    String orderStatusValue = "";
    String toOrderStatusValue = "";
    String deliveryDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order_detail);
        setUI();
        setHeader(getString(R.string.header_update_order_detail), "");
        setUIListener();
        orderIdValue = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.ORDER_ID);
        orderStatusValue = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.ORDER_STATUS);
        fetchOrderDetailsAPI();
    }

    public void setUI() {
        orderId = (TextView) findViewById(R.id.tv_my_order_id);
        orderPlacedBy = (TextView) findViewById(R.id.tv_my_order_place_by);
        orderPlacedTo = (TextView) findViewById(R.id.tv_my_order_place_to);
        orderQuotedAmount = (TextView) findViewById(R.id.tv_my_order_quoted_ammont);
        orderDeliveryType = (TextView) findViewById(R.id.tv_my_order_delievry_type);
        orderPaymentMethod = (TextView) findViewById(R.id.tv_my_order_payment_method);
        orderPaymentStatus = (TextView) findViewById(R.id.tv_my_payment_status);
        orderStatus = (TextView) findViewById(R.id.tv_my_order_status);
        orderDeliveryAddressIdentifier = (TextView) findViewById(R.id.tv_my_order_address_id);
        tv_orderSubscriptionType = (TextView) findViewById(R.id.tv_orderSubscriptionType);
        tv_amountAdjustmentDone = (TextView) findViewById(R.id.tv_amountAdjustmentDone);
        tv_orderBalanceAmount = (TextView) findViewById(R.id.tv_orderBalanceAmount);
        tv_orderInvoiceAmount = (TextView) findViewById(R.id.tv_orderInvoiceAmount);
        tv_orderCancellationReason = (TextView) findViewById(R.id.tv_orderCancellationReason);
        tv_my_order_order_time = (TextView) findViewById(R.id.tv_my_order_order_time);
        spinner_toOrderStatus = (Spinner) findViewById(R.id.spinner_to_order_status);
        tv_update_order = (TextView) findViewById(R.id.tv_update_order);
        et_orderCancellationReason = (EditText)findViewById(R.id.et_orderCancellationReason);
        et_orderInvoiceAmount = (EditText)findViewById(R.id.et_orderInvoiceAmount);
        spinner_deliveryDates = (Spinner) findViewById(R.id.spinner_deliveryDates);
        tv_deliveryDates = (TextView) findViewById(R.id.tv_deliveryDates);
        et_orderCancellationReason.setVisibility(View.GONE);
        et_orderInvoiceAmount.setVisibility(View.GONE);
        ll_orderInvoiceAmount = (LinearLayout) findViewById(R.id.ll_orderInvoiceAmount);
        ll_orderCancellationReason = (LinearLayout)findViewById(R.id.ll_orderCancellationReason);
        ll_amountAdjusted = (LinearLayout) findViewById(R.id.ll_amountAdjustmentDone);
        ll_balanceAmount = (LinearLayout) findViewById(R.id.ll_orderBalanceAmount);
        ll_orderCancellationReason.setVisibility(View.GONE);
        ll_orderInvoiceAmount.setVisibility(View.GONE);
        ll_balanceAmount.setVisibility(View.GONE);
        ll_amountAdjusted.setVisibility(View.GONE);
    }

    String quotedAmount = "";
    String shopId = "";
    public void setData(OrderDetail orderDetail) {
        orderStatusValue = orderDetail.getOrderStatus();
        orderId.setText(orderIdValue);
        orderStatus.setText(orderStatusValue);
        orderPlacedBy.setText(orderDetail.getOrderPlacedBy());
        shopId = orderDetail.getOrderPlacedTo();
        orderPlacedTo.setText(shopId);
        quotedAmount = orderDetail.getOrderQuotedAmount();
        et_orderInvoiceAmount.setText(quotedAmount);
        orderQuotedAmount.setText(quotedAmount);
        orderDeliveryType.setText(orderDetail.getOrderDeliveryType());
        orderPaymentMethod.setText(orderDetail.getOrderPaymentMethod());
        orderPaymentStatus.setText(orderDetail.getPaymentStatus());
        orderDeliveryAddressIdentifier.setText(orderDetail.getOrderDeliveryAddressIdentifier());
        tv_orderSubscriptionType.setText(orderDetail.getOrderSubscriptionType());
        tv_amountAdjustmentDone.setText(orderDetail.getAmountAdjustmentDone());
        tv_orderBalanceAmount.setText(orderDetail.getOrderBalanceAmount());
        tv_orderInvoiceAmount.setText(orderDetail.getOrderInvoiceAmount());
        tv_orderCancellationReason.setText(orderDetail.getOrderCancellationReason());
        tv_my_order_order_time.setText(orderDetail.getOrderCreationTimestamp());
        String toOrderValues[] = orderDetail.getToOrderValues().split(",");
        final List<String> toOrderStatus = new ArrayList<>();
        for (int i = 0; i < toOrderValues.length; i++)
            toOrderStatus.add(toOrderValues[i]);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, toOrderStatus);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_toOrderStatus.setAdapter(dataAdapter);
        spinner_toOrderStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                toOrderStatusValue = toOrderStatus.get(pos);

                if (toOrderStatusValue.equals(AppConstant.STATUS.STATUS_SUBSCRIBED))
                    et_orderInvoiceAmount.setVisibility(View.VISIBLE);
                else if (toOrderStatusValue.equals(AppConstant.STATUS.STATUS_CANCELLED))
                    et_orderCancellationReason.setVisibility(View.VISIBLE);
                else {
                    et_orderInvoiceAmount.setVisibility(View.GONE);
                    et_orderCancellationReason.setVisibility(View.GONE);
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        tv_deliveryDates.setText(orderDetail.getDeliveryDates());
        final String deliveryDatesValue = orderDetail.getDeliveryDates();
        String deliveryDates[] = deliveryDatesValue.split(",");
        final List<String> deliveryDateList = new ArrayList<>();
        deliveryDateList.add(getString(R.string.default_Select_Date));
        for (int i = 0; i < deliveryDates.length; i++)
            deliveryDateList.add(deliveryDates[i]);

        ArrayAdapter<String> deliveryDateDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, deliveryDateList);
        deliveryDateDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_deliveryDates.setAdapter(deliveryDateDataAdapter);
        spinner_deliveryDates.setSelection(0);
        spinner_deliveryDates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                deliveryDate = deliveryDateList.get(pos);
                if (!deliveryDate.equals(getString(R.string.default_Select_Date))) {
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstant.BUNDLE_KEY.ORDER_ID, orderIdValue);
                    bundle.putString(AppConstant.BUNDLE_KEY.DELIVERY_DATE, deliveryDate);
                    bundle.putString(AppConstant.BUNDLE_KEY.ORDER_STATUS, orderStatusValue);
                    bundle.putString(AppConstant.BUNDLE_KEY.DeliveryDates, deliveryDatesValue);
                    bundle.putString(AppConstant.BUNDLE_KEY.SHOP_ID, shopId);
                    bundle.putString(AppConstant.BUNDLE_KEY.TODAYS_DELIVERY_DATE_FLAG, "0");

                    launchActivity(UpdateDeliveryDetailsActivity.class, bundle);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        if(orderStatusValue.equals(AppConstant.STATUS.STATUS_CANCELLED)){
            ll_orderInvoiceAmount.setVisibility(View.VISIBLE);
            ll_balanceAmount.setVisibility(View.VISIBLE);
            ll_amountAdjusted.setVisibility(View.VISIBLE);
            ll_orderCancellationReason.setVisibility(View.VISIBLE);
        }
        if(orderStatusValue.equals(AppConstant.STATUS.STATUS_SUBSCRIBED) || orderStatus.equals(AppConstant.STATUS.STATUS_CLOSED) || orderStatus.equals(AppConstant.STATUS.STATUS_DELIVERED)){
            ll_orderInvoiceAmount.setVisibility(View.VISIBLE);
            ll_balanceAmount.setVisibility(View.VISIBLE);
            ll_amountAdjusted.setVisibility(View.VISIBLE);
        }

    }
    private void setUIListener() {
        tv_update_order.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_update_order:
                updateOrderStatusApi();
                break;
        }
    }


    private void fetchOrderDetailsAPI() {

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchOrderDetailsAPI(orderIdValue, orderStatusValue, new AppResponseListener<MyOrderDetailResponse>(MyOrderDetailResponse.class, this) {
            @Override
            public void onSuccess(MyOrderDetailResponse result) {
                hideProgressBar();
                setData(result.getOrderDetails().get(0));
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }




    private void updateOrderStatusApi() {

        String additionalFieldValue = "";
        if(toOrderStatusValue.equals(AppConstant.STATUS.STATUS_SUBSCRIBED)) {
            additionalFieldValue = et_orderInvoiceAmount.getText().toString().trim();
            Logger.INFO("TAG", quotedAmount);
            Logger.INFO("TAG", additionalFieldValue);
            if (!DialogUtils.isQuotedAmountVerification(this, quotedAmount, additionalFieldValue)) {
                return;
            }
        }
        if(toOrderStatusValue.equals(AppConstant.STATUS.STATUS_CANCELLED))
            additionalFieldValue = et_orderCancellationReason.getText().toString();

        if (!DialogUtils.isUpdateOrderDetailsVerification(this, toOrderStatusValue)) {
            return;
        }

        showProgressBar(findViewById(R.id.tv_update_order));
        AppHttpRequest request = AppRequestBuilder.updateOrderStatusAPI(orderIdValue, orderStatusValue, toOrderStatusValue, additionalFieldValue, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar(findViewById(R.id.tv_update_order));
                showToast(result.getSuccessMessage());
                fetchOrderDetailsAPI();
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar(findViewById(R.id.tv_update_order));
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }


}
