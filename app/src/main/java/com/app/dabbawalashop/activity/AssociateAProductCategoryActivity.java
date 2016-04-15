package com.app.dabbawalashop.activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.api.output.AssociatedShopId;
import com.app.dabbawalashop.api.output.AssociatedShopIdResponse;
import com.app.dabbawalashop.api.output.CommonResponse;
import com.app.dabbawalashop.api.output.ErrorObject;
import com.app.dabbawalashop.api.output.ProductCategory;
import com.app.dabbawalashop.api.output.ProductCategoryResponse;
import com.app.dabbawalashop.api.output.ShopCategory;
import com.app.dabbawalashop.api.output.ShopCategoryResponse;
import com.app.dabbawalashop.constant.AppConstant;
import com.app.dabbawalashop.network.AppHttpRequest;
import com.app.dabbawalashop.network.AppRequestBuilder;
import com.app.dabbawalashop.network.AppResponseListener;
import com.app.dabbawalashop.network.AppRestClient;
import com.app.dabbawalashop.utils.DialogUtils;
import com.app.dabbawalashop.utils.PreferenceKeeper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AssociateAProductCategoryActivity extends BaseActivity {

    private EditText et_fromDeliveryTime;
    private EditText et_toDeliveryTime;
    TimePicker tp_fromTime;
    TimePicker tp_toTime;
    private Spinner spinner_add_product_category;
    private Spinner spinner_add_product_shop_category;
    private String shopCN;
    private String productCN;
    private Spinner spinner_shopId;
    LinearLayout ll_shopId;
    String shopIdValue = "ALL";
    String USER_TYPE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associate_aproduct_category);
        setHeader(getString(R.string.header_associate_a_product_category), "");
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        setUI();
    }

    private void setUI() {
        et_fromDeliveryTime = (EditText) findViewById(R.id.et_fromDeliveryTime);
        et_toDeliveryTime = (EditText) findViewById(R.id.et_toDeliveryTime);
        tp_fromTime = (TimePicker) findViewById(R.id.tp_fromTime);
        tp_toTime = (TimePicker) findViewById(R.id.tp_toTime);
        spinner_add_product_category = (Spinner) findViewById(R.id.spinner_add_product_category);
        spinner_add_product_shop_category = (Spinner) findViewById(R.id.spinner_add_product_shop_category);
        findViewById(R.id.tv_updateDeliveryTime).setOnClickListener(this);
        et_fromDeliveryTime.setOnClickListener(this);
        et_toDeliveryTime.setOnClickListener(this);
        spinner_shopId = (Spinner) findViewById(R.id.spinner_shopId);
        ll_shopId = (LinearLayout) findViewById(R.id.ll_shopId);
        tp_fromTime.setVisibility(View.GONE);
        tp_toTime.setVisibility(View.GONE);
        if((USER_TYPE.equals(AppConstant.UserType.DELIVERY_PERSON_TYPE)) || (USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE))) {
            ll_shopId.setVisibility(View.GONE);
        }
        else {
            ll_shopId.setVisibility(View.VISIBLE);
            fetchShopIdAPI();
        }
        fetchAllShopCategoryApi();
    }


    private void fetchAllShopCategoryApi() {

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchAllShopCategoryApi(new AppResponseListener<ShopCategoryResponse>(ShopCategoryResponse.class, this) {
            @Override
            public void onSuccess(ShopCategoryResponse result) {
                hideProgressBar();
                setSpinnerShopCategory(result.getShopCategories());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setSpinnerShopCategory(List<ShopCategory> shopC) {
        final List<String> categories = new ArrayList<>();
        categories.add("ALL");
        for (ShopCategory method : shopC) {
            categories.add(method.getShopCategoryName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_add_product_shop_category.setAdapter(dataAdapter);
        spinner_add_product_shop_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                shopCN = categories.get(pos);
                fetchAllProductCategoryApi(shopCN);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void fetchAllProductCategoryApi(final String shopCN) {
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchAllProductCategoryApi(shopCN, new AppResponseListener<ProductCategoryResponse>(ProductCategoryResponse.class, this) {
            @Override
            public void onSuccess(ProductCategoryResponse result) {
                hideProgressBar();
                setSpinnerProductCategory(result.getProductCategories());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }


    private void setSpinnerProductCategory(List<ProductCategory> productC) {

        final List<String> categories = new ArrayList<>();
        categories.add("ALL");
        for (ProductCategory method : productC) {
            categories.add(method.getProductCategoryName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_add_product_category.setAdapter(dataAdapter);
        spinner_add_product_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                productCN = categories.get(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
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



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_fromDeliveryTime:
                setTime(et_fromDeliveryTime);
                break;
            case R.id.et_toDeliveryTime:
                setTime(et_toDeliveryTime);
                break;
            case R.id.tv_updateDeliveryTime:
                updateDeliveryTimeAPI();
                break;


        }
    }


    private void setTime(final EditText tv) {
        showFromTimePickerDialog();
        mTimeListner = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(final TimePicker datePicker, int year, int month) {
                if (datePicker.isShown()) {
                    tv.setText(year + ":" + getData(month));
                }
            }
        };
    }

    private static TimePickerDialog.OnTimeSetListener mTimeListner;

    public void showFromTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "tp_fromTime");
    }

    public static class TimePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            return new TimePickerDialog(getActivity(), mTimeListner, year, month, false);
        }
    }

    private void updateDeliveryTimeAPI() {

        String fromTime = et_fromDeliveryTime.getText().toString().trim();
        String toTime = et_toDeliveryTime.getText().toString().trim();
        String shopCategory = shopCN;
        String pCategory = productCN;

        if (!DialogUtils.isSpinnerDefaultValue(this, shopIdValue, "Shop ID")) {
            return;
        }

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_From_Delivery_Time), fromTime)) {
            return;
        }

        if (!DialogUtils.checkForBlank(this, getString(R.string.label_To_Delivery_Time), toTime)) {
            return;
        }

        if(USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE)) {
            shopIdValue = PreferenceKeeper.getInstance().getUserId();
        }

        showProgressBar(findViewById(R.id.tv_updateDeliveryTime));
        AppHttpRequest request = AppRequestBuilder.associateAProductCategoryAPI(shopIdValue, fromTime, toTime, shopCategory, pCategory, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar(findViewById(R.id.tv_updateDeliveryTime));
                showToast(result.getSuccessMessage());
                finish();
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar(findViewById(R.id.tv_updateDeliveryTime));
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }
}
