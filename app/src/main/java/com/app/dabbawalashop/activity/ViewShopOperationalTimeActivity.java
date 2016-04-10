package com.app.dabbawalashop.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.adapter.AssociatedDeliveryLocationAdapter;
import com.app.dabbawalashop.adapter.ShopOperationalTimeAdapter;
import com.app.dabbawalashop.api.output.AssociatedShopId;
import com.app.dabbawalashop.api.output.AssociatedShopIdResponse;
import com.app.dabbawalashop.api.output.DeliveryLocation;
import com.app.dabbawalashop.api.output.DeliveryLocationResponse;
import com.app.dabbawalashop.api.output.ErrorObject;
import com.app.dabbawalashop.api.output.ShopOperationalTime;
import com.app.dabbawalashop.api.output.ShopOperationalTimeResponse;
import com.app.dabbawalashop.constant.AppConstant;
import com.app.dabbawalashop.network.AppHttpRequest;
import com.app.dabbawalashop.network.AppRequestBuilder;
import com.app.dabbawalashop.network.AppResponseListener;
import com.app.dabbawalashop.network.AppRestClient;
import com.app.dabbawalashop.utils.DialogUtils;
import com.app.dabbawalashop.utils.Logger;
import com.app.dabbawalashop.utils.PreferenceKeeper;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ViewShopOperationalTimeActivity extends BaseActivity {


    private RecyclerView recycleView;
    private Spinner spinner_shopId;
    private TextView tv_closingDate;
    private LinearLayout linear_closingDate;
    LinearLayout ll_shopId;
    String shopIdValue = "";
    String USER_TYPE = "";
    String closingDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_shop_operational_time);
        setHeader(getString(R.string.header_view_shop_operational_time), "");
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        setUI();

        setRecycler();
        getCurrentTime();

    }

    private void setUI() {
        recycleView = (RecyclerView) findViewById(R.id.recycle_view_shop_operational_time);
        tv_closingDate = (TextView) findViewById(R.id.tv_closingDate);
        findViewById(R.id.iv_add_shopOperationalTime).setOnClickListener(this);
        spinner_shopId = (Spinner) findViewById(R.id.spinner_shopId);
        ll_shopId = (LinearLayout) findViewById(R.id.ll_shopId);
        linear_closingDate = (LinearLayout) findViewById(R.id.linear_closingDate);
        linear_closingDate.setOnClickListener(this);
        if((USER_TYPE.equals(AppConstant.UserType.DELIVERY_PERSON_TYPE)) || (USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE))) {
            ll_shopId.setVisibility(View.GONE);
            fetchShopOperationalTimeAPI();
        }
        else {
            ll_shopId.setVisibility(View.VISIBLE);
            fetchShopIdAPI();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_shopOperationalTime:
                launchActivity(ShopOperationalTimeActivity.class);
                break;
            case R.id.linear_closingDate:
                setDte(tv_closingDate);
                break;
        }
    }

    private void getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        closingDate = dateFormat.format(cal.getTime());
        tv_closingDate.setText(closingDate);
        fetchShopOperationalTimeAPI();
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
                if (!shopIdValue.equals(getString(R.string.please_select)))
                    fetchShopOperationalTimeAPI();
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

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private static DatePickerDialog.OnDateSetListener mDateListner;

    private void setDte(final TextView tv) {
        showDatePickerDialog();
        mDateListner = new DatePickerDialog.OnDateSetListener() {
            //         "fromDate": "2016-02-14",
            @Override
            public void onDateSet(final DatePicker datePicker, int year, int month, int day) {
                if (datePicker.isShown()) {
                    Logger.INFO(TAG, "listner ");
                    tv.setText(year + "-" + getData(++month) + "-" + getData(day));
                    closingDate = year + "-" + getData(++month) + "-" + getData(day);
                    fetchShopOperationalTimeAPI();
                }
            }
        };
    }

    public void fetchShopOperationalTimeAPI()
                {
        closingDate = tv_closingDate.getText().toString();

        if(USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE)) {
            shopIdValue = PreferenceKeeper.getInstance().getUserId();
        }

        if (!DialogUtils.isSpinnerDefaultValue(this, shopIdValue, "Shop ID") || (shopIdValue.equals("")) ||(closingDate.equals(""))) {
            return;
        }

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchShopOperationalTimeAPI(shopIdValue,closingDate, new AppResponseListener<ShopOperationalTimeResponse>(ShopOperationalTimeResponse.class, this) {
            @Override
            public void onSuccess(ShopOperationalTimeResponse result) {
                hideProgressBar();
                setAdapterData(result.getShopOperationalTime());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setAdapterData(List<ShopOperationalTime> shopOperationalTime) {
        ShopOperationalTimeAdapter adapter = new ShopOperationalTimeAdapter(this, shopOperationalTime, shopIdValue);
        recycleView.setAdapter(adapter);

    }
}
