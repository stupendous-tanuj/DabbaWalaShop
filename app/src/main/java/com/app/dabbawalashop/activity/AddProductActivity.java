package com.app.dabbawalashop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.api.output.AssociatedShopId;
import com.app.dabbawalashop.api.output.AssociatedShopIdResponse;
import com.app.dabbawalashop.api.output.CommonResponse;
import com.app.dabbawalashop.api.output.ErrorObject;
import com.app.dabbawalashop.api.output.OrderUnit;
import com.app.dabbawalashop.api.output.OrderUnitResponse;
import com.app.dabbawalashop.api.output.ProductCategory;
import com.app.dabbawalashop.api.output.ProductCategoryResponse;
import com.app.dabbawalashop.api.output.ShopCategory;
import com.app.dabbawalashop.api.output.ShopCategoryResponse;
import com.app.dabbawalashop.constant.AppConstant;
import com.app.dabbawalashop.listner.IDialogListener;
import com.app.dabbawalashop.network.AppHttpRequest;
import com.app.dabbawalashop.network.AppRequestBuilder;
import com.app.dabbawalashop.network.AppResponseListener;
import com.app.dabbawalashop.network.AppRestClient;
import com.app.dabbawalashop.utils.DialogUtils;
import com.app.dabbawalashop.utils.PreferenceKeeper;

import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends BaseActivity {


    private EditText et_add_product_eng_name;
    private EditText et_add_product_marathi_name;
    private EditText et_add_product_des;
    private Spinner spinner_add_product_category;
    private Spinner spinner_add_product_shop_category;
    private Spinner spinner_add_product_order_unit;
    private EditText et_add_product_no_unit;
    private String shopCN;
    private String productCN;
    private String orderUnitC;
    private TextView tv_add_product;
    private EditText et_ass_product_monthly_subscription_price;
    private EditText et_ass_product_daily_subscription_price;
    private EditText et_ass_product_weekly_subscription_price;
    private CheckBox cbAvailable;
    private Spinner spinner_shopId;
    LinearLayout ll_shopId;
    String shopIdValue = "ALL";
    String USER_TYPE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        setHeader(getString(R.string.header_add_product), "");
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        setUI();
        fetchAllShopCategoryApi();
        fetchOrderUnitApi();
    }

    private void fetchAllShopCategoryApi() {
        tv_add_product.setVisibility(View.GONE);
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
        tv_add_product.setVisibility(View.VISIBLE);
        final List<String> categories = new ArrayList<>();
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

    private void fetchOrderUnitApi() {
        AppHttpRequest request = AppRequestBuilder.fetchOrderUnitApi(new AppResponseListener<OrderUnitResponse>(OrderUnitResponse.class, this) {
            @Override
            public void onSuccess(OrderUnitResponse result) {

                setSpinnerOrderUnit(result.getOrderUnits());
            }

            @Override
            public void onError(ErrorObject error) {

            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }


    private void setSpinnerOrderUnit(List<OrderUnit> orderUnits) {
        final List<String> categories = new ArrayList<>();
        for (OrderUnit method : orderUnits) {
            categories.add(method.getOrderUnit());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_add_product_order_unit.setAdapter(dataAdapter);
        spinner_add_product_order_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                orderUnitC = categories.get(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void setUI() {
        tv_add_product = (TextView) findViewById(R.id.tv_add_product);
        et_add_product_eng_name = (EditText) findViewById(R.id.et_add_product_eng_name);
        et_add_product_marathi_name = (EditText) findViewById(R.id.et_add_product_marathi_name);
        et_add_product_des = (EditText) findViewById(R.id.et_add_product_des);
        spinner_add_product_category = (Spinner) findViewById(R.id.spinner_add_product_category);
        spinner_add_product_shop_category = (Spinner) findViewById(R.id.spinner_add_product_shop_category);
        spinner_add_product_order_unit = (Spinner) findViewById(R.id.spinner_add_product_order_unit);
        et_add_product_no_unit = (EditText) findViewById(R.id.et_add_product_no_unit);
        et_ass_product_monthly_subscription_price = (EditText) findViewById(R.id.et_ass_product_monthly_subscription_price);
        et_ass_product_daily_subscription_price = (EditText) findViewById(R.id.et_ass_product_daily_subscription_price);
        et_ass_product_weekly_subscription_price = (EditText) findViewById(R.id.et_ass_product_weekly_subscription_price);
        cbAvailable = (CheckBox) findViewById(R.id.cb_ass_product_add_availability);

        findViewById(R.id.tv_add_product).setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_product:
                addAProductAPI();
                break;
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


    private void addAProductAPI() {
        String eName = et_add_product_eng_name.getText().toString().trim();
        String hName = et_add_product_marathi_name.getText().toString().trim();
        String des = et_add_product_des.getText().toString().trim();
        String shopCategory = shopCN;
        String pCategory = productCN;
        String orderUnit = orderUnitC;
        String noOfUnit = et_add_product_no_unit.getText().toString().trim();
        String dailyPrice = et_ass_product_daily_subscription_price.getText().toString().trim();
        String weeklyPrice = et_ass_product_weekly_subscription_price.getText().toString().trim();
        String monthlyPrice = et_ass_product_monthly_subscription_price.getText().toString().trim();
        String productAvailability = cbAvailable.isChecked() == true ? "1" : "0";
        if (!DialogUtils.showDialogddProduct(this, eName, hName, des, noOfUnit)) {
            return;
        }

                if(USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE)) {
            shopIdValue = PreferenceKeeper.getInstance().getUserId();
        }

        showProgressBar(findViewById(R.id.tv_add_product));
        AppHttpRequest request = AppRequestBuilder.addAProductAPI(shopIdValue, eName, hName, des, pCategory, shopCategory, orderUnit, noOfUnit, dailyPrice, weeklyPrice,monthlyPrice,productAvailability, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar(findViewById(R.id.tv_add_product));
                showToast(result.getSuccessMessage());
                DialogUtils.showDialogNetwork(AddProductActivity.this, result.getSuccessMessage(), new IDialogListener() {
                    @Override
                    public void onClickOk() {
                        launchActivity(UploadProductImageActivity.class);
                    }
                    @Override
                    public void onCancel() {
                        finish();
                    }
                });
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar(findViewById(R.id.tv_add_product));
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

}
