package com.app.dabbawalashop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.api.output.CommonResponse;
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

public class AssociateAProductActivity extends BaseActivity {

    private boolean isAddFromHome;
    private TextView tv_ass_product_add_sku;
    private EditText et_ass_product_add_english_name;
    private EditText et_ass_product_add_marathi_name;
    private EditText et_ass_product_add_description;
    private TextView tv_ass_product_add_unit;
    private EditText et_ass_product_monthly_subscription_price;
    private EditText et_ass_product_daily_subscription_price;
    private EditText et_ass_product_weekly_subscription_price;
    private CheckBox cbAvailable;
    private TextView tv_add_associate_product;
    private Spinner spinner_registrationStatus;
    private LinearLayout linear_spinner_shop_ctegory;
    private LinearLayout linear_spinner_product_ctegory;
    String productStatusValue = "ALL";
    private LinearLayout linear_associated_product_root;
    private TextView tv_no_product_found;
    String USER_TYPE = "";
    private ArrayAdapter<String> adapter;

    private ScrollView scrollview_ssocite_product;
    private TextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_associate_product);
        setHeader(getString(R.string.header_associate_a_product), "");
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        setUI();
        setUIListner();
        getIntentData();
    }


    private void setUI() {

        scrollview_ssocite_product = (ScrollView) findViewById(R.id.scrollview_ssocite_product);
        spinner_registrationStatus = (Spinner) findViewById(R.id.spinner_registrationStatus);
        tv_no_product_found = (TextView) findViewById(R.id.tv_no_product_found);
        linear_associated_product_root = (LinearLayout) findViewById(R.id.linear_associated_product_root);
        linear_spinner_shop_ctegory = (LinearLayout) findViewById(R.id.linear_spinner_shop_ctegory);
        linear_spinner_product_ctegory = (LinearLayout) findViewById(R.id.linear_spinner_product_ctegory);
        tv_add_associate_product = (TextView) findViewById(R.id.tv_add_associate_product);
        tv_ass_product_add_sku = (TextView) findViewById(R.id.tv_ass_product_add_sku);
        et_ass_product_add_english_name = (EditText) findViewById(R.id.et_ass_product_add_english_name);
        et_ass_product_add_marathi_name = (EditText) findViewById(R.id.et_ass_product_add_marathi_name);
        et_ass_product_add_description = (EditText) findViewById(R.id.et_ass_product_add_description);
        tv_ass_product_add_unit = (TextView) findViewById(R.id.tv_ass_product_add_unit);
        et_ass_product_monthly_subscription_price = (EditText) findViewById(R.id.et_ass_product_monthly_subscription_price);
        et_ass_product_daily_subscription_price = (EditText) findViewById(R.id.et_ass_product_daily_subscription_price);
        et_ass_product_weekly_subscription_price = (EditText) findViewById(R.id.et_ass_product_weekly_subscription_price);
        cbAvailable = (CheckBox) findViewById(R.id.cb_ass_product_add_availability);
        if((USER_TYPE.equals(AppConstant.UserType.DELIVERY_PERSON_TYPE)) || (USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE))) {
            spinner_registrationStatus.setEnabled(false);
        }
    }

    private void setUIListner() {
        findViewById(R.id.tv_add_associate_product).setOnClickListener(this);
    }




    private List<Product> products;

    private void setSpinnerProduct(final List<Product> products) {
        this.products = products;
        if (products.size() == 0) {

            tv_add_associate_product.setVisibility(View.GONE);
            linear_associated_product_root.setVisibility(View.GONE);
            tv_no_product_found.setVisibility(View.VISIBLE);
            return;
        } else {

            tv_add_associate_product.setVisibility(View.VISIBLE);
            linear_associated_product_root.setVisibility(View.VISIBLE);
            tv_no_product_found.setVisibility(View.GONE);
        }
        final List<String> categories = new ArrayList<>();
        for (Product method : products) {
            categories.add(method.getProductNameEnglish() + ", " + method.getProductNameMarathi());
        }




    }

    private void setProduct(Product product) {
        tv_ass_product_add_sku.setText(product.getProductSKUID());
        et_ass_product_add_english_name.setText(product.getProductNameEnglish());
        et_ass_product_add_marathi_name.setText(product.getProductNameMarathi());
        et_ass_product_add_description.setText(product.getProductDescription());
        tv_ass_product_add_unit.setText(product.getProductPriceForUnits() + " " + product.getProductOrderUnit());
    }

    private void getIntentData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            isAddFromHome = getIntent().getExtras().getBoolean(AppConstant.BUNDLE_KEY.IS_ADD_ASSOCIATE_PRODUCT_HOME);
            if (isAddFromHome) {
                tv_add_associate_product.setVisibility(View.GONE);
                linear_associated_product_root.setVisibility(View.GONE);

                tv_add_associate_product.setText(getString(R.string.title_activity_associate_a_product));
                setHeader(getString(R.string.title_activity_associate_a_product), "");

            } else {
                linear_spinner_shop_ctegory.setVisibility(View.GONE);
                linear_spinner_product_ctegory.setVisibility(View.GONE);

                tv_add_associate_product.setText(getString(R.string.title_activity_update_associated__product));
                setHeader(getString(R.string.title_activity_update_associated__product), "");
                String SKU_ID = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.SKU_ID);
                String ENGLISH_NAME = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.ENGLISH_NAME);
                String MARATHI_NAME = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.MARATHI_NAME);
                String DES = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.DES);
                String UNIT = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.UNIT);
                String DAILYPRICE = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.DAILY_PRICE);
                String MONTHLYPRICE = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.MONTHLY_PRICE);
                String WEEKLYPRICE = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.WEEKLY_PRICE);
                String IMAGE = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.IMAGE);
                productStatusValue = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.PRODUCT_STATUS);
                tv_ass_product_add_sku.setText(SKU_ID);
                et_ass_product_add_english_name.setText(ENGLISH_NAME);
                et_ass_product_add_marathi_name.setText(MARATHI_NAME);
                et_ass_product_add_description.setText(DES);
                tv_ass_product_add_unit.setText(UNIT);
                et_ass_product_daily_subscription_price.setText(DAILYPRICE);
                et_ass_product_weekly_subscription_price.setText(WEEKLYPRICE);
                et_ass_product_monthly_subscription_price.setText(MONTHLYPRICE);
                cbAvailable.setChecked(true);

                final List<String> productStatus = new ArrayList<>();
                productStatus.add(AppConstant.STATUS.STATUS_APPROVED);
                productStatus.add(AppConstant.STATUS.STATUS_REGISTERED);
                productStatus.add(AppConstant.STATUS.STATUS_REJECTED);

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, productStatus);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_registrationStatus.setAdapter(dataAdapter);
                if(productStatusValue.equals(AppConstant.STATUS.STATUS_APPROVED))
                    spinner_registrationStatus.setSelection(0);
                else if(productStatusValue.equals(AppConstant.STATUS.STATUS_REGISTERED))
                    spinner_registrationStatus.setSelection(1);
                else if(productStatusValue.equals(AppConstant.STATUS.STATUS_REJECTED))
                    spinner_registrationStatus.setSelection(2);
                spinner_registrationStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        productStatusValue = productStatus.get(pos);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_associate_product:
                if (isAddFromHome) {
                    addAssociatedProductAPI();
                } else {
                    updateAddAssociatedProductAPI();
                }
                break;
        }
    }

    private void addAssociatedProductAPI() {

        String sku = tv_ass_product_add_sku.getText().toString();
        String englishName = et_ass_product_add_english_name.getText().toString();
        String marathiName = et_ass_product_add_marathi_name.getText().toString();
        String productDes = et_ass_product_add_description.getText().toString();
        String productAvailability = cbAvailable.isChecked() == true ? "1" : "0";
        String dailyPrice = et_ass_product_daily_subscription_price.getText().toString().trim();
        String weeklyPrice = et_ass_product_weekly_subscription_price.getText().toString().trim();
        String monthlyPrice = et_ass_product_monthly_subscription_price.getText().toString().trim();
        if (!DialogUtils.showDialogProduct(this, dailyPrice, weeklyPrice,monthlyPrice)) {
            return;
        }
        showProgressBar(tv_add_associate_product);
        AppHttpRequest request = AppRequestBuilder.addAssociatedProductAPI(sku,englishName, marathiName, productDes, productAvailability, dailyPrice, weeklyPrice,monthlyPrice, productStatusValue, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar(tv_add_associate_product);
                showToast(result.getSuccessMessage());
                finish();
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar(tv_add_associate_product);
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void updateAddAssociatedProductAPI() {

        String sku = tv_ass_product_add_sku.getText().toString();
        String englishName = et_ass_product_add_english_name.getText().toString();
        String marathiName = et_ass_product_add_marathi_name.getText().toString();
        String productDes = et_ass_product_add_description.getText().toString();
        String productAvailability = cbAvailable.isChecked() == true ? "1" : "0";
        String dailyPrice = et_ass_product_daily_subscription_price.getText().toString().trim();
        String weeklyPrice = et_ass_product_weekly_subscription_price.getText().toString().trim();
        String monthlyPrice = et_ass_product_monthly_subscription_price.getText().toString().trim();

        if (!DialogUtils.showDialogAddProduct(this, englishName, marathiName, productDes, "noOfUnit", dailyPrice, weeklyPrice, monthlyPrice)) {
            return;
        }

        showProgressBar(tv_add_associate_product);
        AppHttpRequest request = AppRequestBuilder.updateAssociatedProductAPI( sku,englishName, marathiName, productDes, productAvailability, dailyPrice, weeklyPrice,monthlyPrice,productStatusValue, new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar(tv_add_associate_product);
                showToast(result.getSuccessMessage());
                finish();
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar(tv_add_associate_product);
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

}
