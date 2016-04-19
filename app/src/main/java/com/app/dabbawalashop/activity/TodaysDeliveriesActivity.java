package com.app.dabbawalashop.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.adapter.TodaysDeliveryDetailAdapter;
import com.app.dabbawalashop.api.output.AssociatedShopId;
import com.app.dabbawalashop.api.output.AssociatedShopIdResponse;
import com.app.dabbawalashop.api.output.DeliveryDetail;
import com.app.dabbawalashop.api.output.DeliveryDetailResponse;
import com.app.dabbawalashop.api.output.ErrorObject;
import com.app.dabbawalashop.constant.AppConstant;
import com.app.dabbawalashop.network.AppHttpRequest;
import com.app.dabbawalashop.network.AppRequestBuilder;
import com.app.dabbawalashop.network.AppResponseListener;
import com.app.dabbawalashop.network.AppRestClient;
import com.app.dabbawalashop.utils.DialogUtils;
import com.app.dabbawalashop.utils.Logger;
import com.app.dabbawalashop.utils.PreferenceKeeper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TodaysDeliveriesActivity extends BaseActivity {

    TextView tv_deliveryDate;
    private RecyclerView recycleView;
    private Spinner spinner_orderStatus;
    private Spinner spinner_shopId;
    LinearLayout ll_shopId;
    LinearLayout ll_orderStatus;
    String shopIdValue = "";
    String deliveryStatusValue = "ALL";
    String deliveryDate = "";
    String USER_TYPE = "";
    private static DatePickerDialog.OnDateSetListener mDateListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_deliveries);
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        setHeader(getString(R.string.header_my_deliveries) + " - " + USER_TYPE, "");
        initUI();
        getCurrentTime();
        setRecycler();
        setDeliveryStatusSpinner();

    }

    private void initUI() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_deliveryDate = (TextView) findViewById(R.id.tv_deliveryDate);
        recycleView = (RecyclerView) findViewById(R.id.rv_home_activity_my_order);
        spinner_orderStatus = (Spinner) findViewById(R.id.spinner_orderStatus);
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        spinner_shopId = (Spinner) findViewById(R.id.spinner_shopId);
        ll_shopId = (LinearLayout) findViewById(R.id.ll_shopId);
        ll_orderStatus = (LinearLayout) findViewById(R.id.ll_orderStatus);
        if ((USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE))) {
            ll_shopId.setVisibility(View.GONE);
        } else {
            ll_shopId.setVisibility(View.VISIBLE);
            associateShopIdAPI();
        }

        tv_deliveryDate.setOnClickListener(this);
    }

    private void getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
        Calendar cal = Calendar.getInstance();
        String fromDate = dateFormat.format(cal.getTime());
        tv_deliveryDate.setText(Html.fromHtml("<u>" +fromDate+"</u>"));
        deliveryDate = fromDate;
        if(!shopIdValue.equals(getString(R.string.please_select)))
            fetchTodaysDeliveryAPI();
    }


    private void setRecycler() {
        recycleView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(mLayoutManager);
    }


    private void fetchTodaysDeliveryAPI() {

        if(USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE)) {
            shopIdValue = PreferenceKeeper.getInstance().getUserId();
        }

        if (!DialogUtils.isSpinnerDefaultValue(this, shopIdValue, "Shop ID") || (shopIdValue.equals(""))) {
            return;
        }

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchTodaysDeliveryAPI(deliveryDate, deliveryStatusValue, shopIdValue, new AppResponseListener<DeliveryDetailResponse>(DeliveryDetailResponse.class, this) {
            @Override
            public void onSuccess(DeliveryDetailResponse result) {
                hideProgressBar();
                setTodaysDeliveryAdapterData(result.getDeliveryDetails());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setTodaysDeliveryAdapterData(List<DeliveryDetail> deliveryDetail) {
        TodaysDeliveryDetailAdapter todaysDeliveryAdapter = new TodaysDeliveryDetailAdapter(this, deliveryDetail);
        recycleView.setAdapter(todaysDeliveryAdapter);
    }

    private void setDate(final TextView tv) {
        showDatePickerDialog();
        mDateListner = new DatePickerDialog.OnDateSetListener() {
            //         "fromDate": "2016-02-14",
            @Override
            public void onDateSet(final DatePicker datePicker, int year, int month, int day) {
                if (datePicker.isShown()) {
                    Logger.INFO(TAG, "listner ");
                    tv.setText(Html.fromHtml("<u>" + getData(day) + "-" + getMonth(++month) + "-" + year+"</u>"));
                    deliveryDate = tv_deliveryDate.getText().toString();
                    if(!shopIdValue.equals(getString(R.string.please_select)))
                        fetchTodaysDeliveryAPI();
                }
            }
        };
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), mDateListner, year, month, day);
        }
    }

    private void setDeliveryStatusSpinner()
    {
        final List<String> orderStatus = new ArrayList<>();
        orderStatus.add(AppConstant.STATUS.STATUS_ALL);
        orderStatus.add(AppConstant.STATUS.STATUS_ORDERED);
        orderStatus.add(AppConstant.STATUS.STATUS_CONFIRMED);
        orderStatus.add(AppConstant.STATUS.STATUS_SUBSCRIBED);
        orderStatus.add(AppConstant.STATUS.STATUS_DISPATCHED);
        orderStatus.add(AppConstant.STATUS.STATUS_CLOSED);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, orderStatus);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_orderStatus.setAdapter(dataAdapter);
        spinner_orderStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                deliveryStatusValue = orderStatus.get(pos);
                if(!shopIdValue.equals(getString(R.string.please_select)))
                    fetchTodaysDeliveryAPI();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                if(!shopIdValue.equals(getString(R.string.please_select)))
                    fetchTodaysDeliveryAPI();
            }
        });
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
                    fetchTodaysDeliveryAPI();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_deliveryDate:
                setDate(tv_deliveryDate);
                fetchTodaysDeliveryAPI();
                break;
        }
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



}
