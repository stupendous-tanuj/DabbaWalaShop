package com.app.dabbawalashop.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

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
import com.app.dabbawalashop.utils.PreferenceKeeper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ShopOperationTimeActivity extends BaseActivity {

    private EditText et_shop_time_closing_date;

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
        setContentView(R.layout.activity_shop_operational_time);
        setHeader("Shop Operation Time", "");
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        setUI();
    }

    private void setUI() {
        et_shop_time_closing_date = (EditText) findViewById(R.id.et_shop_time_closing_date);


        spinner_add_product_category = (Spinner) findViewById(R.id.spinner_add_product_category);
        spinner_add_product_shop_category = (Spinner) findViewById(R.id.spinner_add_product_shop_category);
        findViewById(R.id.tv_shop_time_add).setOnClickListener(this);
        findViewById(R.id.et_shop_time_closing_date).setOnClickListener(this);
        spinner_shopId = (Spinner) findViewById(R.id.spinner_shopId);
        ll_shopId = (LinearLayout) findViewById(R.id.ll_shopId);
        fetchAllShopCategoryApi();
        if((USER_TYPE.equals(AppConstant.UserType.DELIVERY_PERSON_TYPE)) || (USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE))) {
            ll_shopId.setVisibility(View.GONE);
        }
        else {
            ll_shopId.setVisibility(View.VISIBLE);
            fetchShopIdAPI();
        }
    }


    private void fetchAllShopCategoryApi() {

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchAllShopCategoryApi(new AppResponseListener<ShopCategoryResponse>(ShopCategoryResponse.class, this) {
            @Override
            public void onSuccess(ShopCategoryResponse result) {
                hideProgressBar();
                setSpinnerShopCtegory(result.getShopCategories());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setSpinnerShopCtegory(List<ShopCategory> shopC) {
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
                setSpinnerProductCtegory(shopCN, result.getProductCategories());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }


    private void setSpinnerProductCtegory(final String shopCN, List<ProductCategory> productC) {

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
            case R.id.et_shop_time_closing_date:
                setDte(et_shop_time_closing_date);
                break;
            case R.id.tv_shop_time_add:
                addShopTimeAPI();
                break;


        }
    }


    private void setDte(final EditText tv) {
        showDatePickerDialog();
        mDateListner = new DatePickerDialog.OnDateSetListener() {
            //         "fromDate": "2016-02-14",
            @Override
            public void onDateSet(final DatePicker datePicker, int year, int month, int day) {
                if (datePicker.isShown()) {
                    tv.setText(year + "-" + getData(++month) + "-" + getData(day));
                }
            }
        };
    }

    private static DatePickerDialog.OnDateSetListener mDateListner;

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

    private void addShopTimeAPI() {

        String closingDate = et_shop_time_closing_date.getText().toString().trim();
        String shopCategory = shopCN;
        String pCategory = productCN;

        if(USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE)) {
            shopIdValue = PreferenceKeeper.getInstance().getUserId();
        }

        showProgressBar(findViewById(R.id.tv_shop_time_add));
        AppHttpRequest request = AppRequestBuilder.addShopTimeAPI(shopIdValue,closingDate, shopCategory, pCategory, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar(findViewById(R.id.tv_shop_time_add));
                showToast(result.getSuccessMessage());
                finish();
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar(findViewById(R.id.tv_shop_time_add));
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }
}
