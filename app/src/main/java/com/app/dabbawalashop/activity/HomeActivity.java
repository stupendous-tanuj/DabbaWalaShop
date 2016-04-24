package com.app.dabbawalashop.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.adapter.HomeAdapter;
import com.app.dabbawalashop.api.output.AssociatedShopId;
import com.app.dabbawalashop.api.output.AssociatedShopIdResponse;
import com.app.dabbawalashop.api.output.CommonResponse;
import com.app.dabbawalashop.api.output.ErrorObject;
import com.app.dabbawalashop.api.output.MyOrderDetailResponse;
import com.app.dabbawalashop.api.output.OrderDetail;
import com.app.dabbawalashop.constant.AppConstant;
import com.app.dabbawalashop.network.AppHttpRequest;
import com.app.dabbawalashop.network.AppRequestBuilder;
import com.app.dabbawalashop.network.AppResponseListener;
import com.app.dabbawalashop.network.AppRestClient;
import com.app.dabbawalashop.notification.GcmIdGenerator;
import com.app.dabbawalashop.utils.AppUtil;
import com.app.dabbawalashop.utils.DialogUtils;
import com.app.dabbawalashop.utils.Logger;
import com.app.dabbawalashop.utils.PreferenceKeeper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends BaseActivity {

    private Toolbar toolbar;
    private DrawerLayout drawer_home_root;
    private ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout ll_home_layout;
    private LinearLayout nav_home_left_drawer;
    private RecyclerView recycleView;
    private TextView no_data_available;
    private TextView tv_userId;
    private TextView tv_userType;
    private TextView tv_from_dte_home;
    private TextView tv_to_dte_home;
    private static DatePickerDialog.OnDateSetListener mDateListner;
    String USER_TYPE = "";
    LinearLayout linear_home_shopkeeper_profile = null;
    LinearLayout linear_home_seller_hub_profile = null;
    LinearLayout linear_home_associated_product = null;
    LinearLayout linear_home_delivery_location = null;
    LinearLayout linear_home_add_a_product = null;
    LinearLayout linear_home_associated_delivery_person = null;
    LinearLayout linear_home_add_delivery_person = null;
    LinearLayout linear_home_shop_operation_time = null;
    LinearLayout linear_home_contactus = null;
    LinearLayout linear_home_logout = null;
    LinearLayout linear_home_from_dte = null;
    LinearLayout linear_home_to_dte = null;
    LinearLayout linear_change_password = null;
    LinearLayout linear_home_my_orders = null;
    LinearLayout linear_home_associated_shops = null;
    LinearLayout linear_bar_deliveryLocation = null;
    LinearLayout linear_bar_deliveryPerson = null;
    LinearLayout linear_bar_product = null;
    LinearLayout linear_home_addADeliveryLocation = null;
    LinearLayout linear_home_addAShop = null;
    LinearLayout linear_home_all_delivery_person = null;
    LinearLayout linear_home_associateAProductCategory = null;
    LinearLayout linear_home_todays_delivery = null;
    LinearLayout linear_home_associatedProductCategory = null;
    LinearLayout linear_home_settings = null;
    private TextView tv_orderStatus;
    private TextView tv_shopId;
    private Spinner spinner_orderStatus;
    private Spinner spinner_shopId;
    LinearLayout ll_shopId;
    LinearLayout ll_orderStatus;
    String shopIdValue = "";
    String orderStatusValue = "ALL";
    String from = "";
    String to = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        setHeader(getString(R.string.header_my_orders) + " - " + USER_TYPE, "");
        initUI();// initialized component
        setRecycler();
        initUIListner();
        initDrawerToggle(); // set listener of drawer with toggle
        getCurrentTime();
        verifyApplicationIDAPI();
        getGCMRegId();



    }

    private void getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String fromDate = dateFormat.format(cal.getTime());
        String toDate = dateFormat.format(cal.getTime());
        tv_from_dte_home.setText(Html.fromHtml("<u>" + fromDate + "</u>"));
        tv_to_dte_home.setText(Html.fromHtml("<u>" + toDate + "</u>"));
        from = fromDate;
        to = toDate;
        if(!shopIdValue.equals(getString(R.string.please_select)))
        fetchMyOrderDetailApi(from, to);
    }


    private void setRecycler() {
        recycleView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(mLayoutManager);
    }


    private void fetchMyOrderDetailApi(String fromDate, String toDate) {

        long from = AppUtil.getMillisFromDate(fromDate);
        long to = AppUtil.getMillisFromDate(toDate);

        if(USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE)) {
            shopIdValue = PreferenceKeeper.getInstance().getUserId();
        }

        if (from > to) {
            showToast(getString(R.string.from_date_greater));
            return;
        }

        if (!DialogUtils.isSpinnerDefaultValue(this, shopIdValue, "Shop ID") || (shopIdValue.equals(""))) {
            return;
        }

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.fetchMyOrderDetailAPI(fromDate, toDate, shopIdValue, orderStatusValue, new AppResponseListener<MyOrderDetailResponse>(MyOrderDetailResponse.class, this) {
            @Override
            public void onSuccess(MyOrderDetailResponse result) {
                hideProgressBar();
                setVisibleUI(result.getOrderDetails());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
                setVisibleUI(null);
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void setFromDate(final TextView tv) {
        showDatePickerDialog();
        mDateListner = new DatePickerDialog.OnDateSetListener() {
            //         "fromDate": "2016-02-14",
            @Override
            public void onDateSet(final DatePicker datePicker, int year, int month, int day) {
                if (datePicker.isShown()) {
                    Logger.INFO(TAG, "listner ");
                    from = year + "-" + getData(++month) + "-" + getData(day);
                    long fromLong = AppUtil.getMillisFromDate(from);
                    long toLong = AppUtil.getMillisFromDate(to);

                    if (fromLong > toLong) {
                        showToast(getString(R.string.from_date_greater));
                    }
                    else {
                        tv.setText(Html.fromHtml("<u>" + from + "</u>"));
                        if (!shopIdValue.equals(getString(R.string.please_select)))
                            fetchMyOrderDetailApi(from, to);
                    }
                }
            }
        };
    }

    private void setToDate(final TextView tv) {
        showDatePickerDialog();
        mDateListner = new DatePickerDialog.OnDateSetListener() {
            //         "fromDate": "2016-02-14",
            @Override
            public void onDateSet(final DatePicker datePicker, int year, int month, int day) {
                if (datePicker.isShown()) {
                    Logger.INFO(TAG, "listner ");
                    to = year + "-" + getData(++month) + "-" + getData(day);
                    long fromLong = AppUtil.getMillisFromDate(from);
                    long toLong = AppUtil.getMillisFromDate(to);

                    if (fromLong > toLong) {
                        showToast(getString(R.string.from_date_greater));
                    }
                    else {
                        tv.setText(Html.fromHtml("<u>" + to + "</u>"));
                        if (!shopIdValue.equals(getString(R.string.please_select)))
                            fetchMyOrderDetailApi(from, to);
                    }
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

    private void setVisibleUI(List<OrderDetail> carts) {
        if (carts == null || carts.size() == 0) {
            recycleView.setVisibility(View.GONE);
            no_data_available.setVisibility(View.VISIBLE);
            no_data_available.setText(getString(R.string.no_order_found));
        } else {
            recycleView.setVisibility(View.VISIBLE);
            no_data_available.setVisibility(View.GONE);
            setMyOrderAdapterData(carts);
        }
    }

    private void setMyOrderAdapterData(List<OrderDetail> carts) {
        HomeAdapter placeOrderlAdapter = new HomeAdapter(this, carts);
        recycleView.setAdapter(placeOrderlAdapter);
    }

    private void getGCMRegId() {
        GcmIdGenerator gcm = new GcmIdGenerator(HomeActivity.this);
        if (AppUtil.checkPlayServices(this)) {
            gcm.getGCMRegId();
        }
    }

    private void verifyApplicationIDAPI() {
        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.VerifyApplicationIDAPI(new AppResponseListener<CommonResponse>(CommonResponse.class, this) {
            @Override
            public void onSuccess(CommonResponse result) {
                hideProgressBar();
                setOrderStatusSpinner();
                if(USER_TYPE.equals(AppConstant.UserType.SELLER_HUB_TYPE) || USER_TYPE.equals(AppConstant.UserType.DELIVERY_PERSON_TYPE)) {
                    associateShopIdAPI();
                }
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
                showToast("Application is Blocked.");
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void initUI() {
        tv_userId = (TextView) findViewById(R.id.tv_userId);
        tv_userType = (TextView) findViewById(R.id.tv_userType);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_from_dte_home = (TextView) findViewById(R.id.tv_from_dte_home);
        tv_to_dte_home = (TextView) findViewById(R.id.tv_to_dte_home);
        no_data_available = (TextView) findViewById(R.id.no_data_available);
        recycleView = (RecyclerView) findViewById(R.id.rv_home_activity_my_order);
        tv_orderStatus = (TextView) findViewById(R.id.tv_orderStatus);
        tv_shopId = (TextView) findViewById(R.id.tv_shopId);
        spinner_orderStatus = (Spinner) findViewById(R.id.spinner_orderStatus);
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        spinner_shopId = (Spinner) findViewById(R.id.spinner_shopId);
        ll_shopId = (LinearLayout) findViewById(R.id.ll_shopId);
        ll_orderStatus = (LinearLayout) findViewById(R.id.ll_orderStatus);
        if((USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE))) {
            ll_shopId.setVisibility(View.GONE);
        }
        else {
            ll_shopId.setVisibility(View.VISIBLE);
        }
        // left
        //tv_home_address = (TextView) findViewById(R.id.tv_home_address);
        linear_home_logout = (LinearLayout) findViewById(R.id.linear_home_logout);
        drawer_home_root = (DrawerLayout) findViewById(R.id.drawer_home_root);        // mDrawerLayout object Assigned to the view
        drawer_home_root.setScrimColor(Color.TRANSPARENT);
        ll_home_layout = (RelativeLayout) findViewById(R.id.ll_home_layout);
        nav_home_left_drawer = (LinearLayout) findViewById(R.id.nav_home_left_drawer);

        linear_home_shopkeeper_profile = (LinearLayout) findViewById(R.id.linear_home_shopkeeper_profile);
        linear_home_seller_hub_profile = (LinearLayout) findViewById(R.id.linear_home_seller_hub_profile);
        linear_home_associated_product = (LinearLayout) findViewById(R.id.linear_home_associated_product);
        linear_home_delivery_location  = (LinearLayout) findViewById(R.id.linear_home_delivery_location);
        linear_home_add_a_product = (LinearLayout) findViewById(R.id.linear_home_add_a_product);
        linear_home_associated_delivery_person = (LinearLayout) findViewById(R.id.linear_home_associated_delivery_person);
        linear_home_add_delivery_person = (LinearLayout) findViewById(R.id.linear_home_add_delivery_person);
        linear_home_shop_operation_time = (LinearLayout) findViewById(R.id.linear_home_shop_operation_time);
        linear_home_settings = (LinearLayout) findViewById(R.id.linear_home_settings);
        linear_home_contactus = (LinearLayout) findViewById(R.id.linear_home_contactus);
        linear_home_logout = (LinearLayout) findViewById(R.id.linear_home_logout);
        linear_home_from_dte = (LinearLayout) findViewById(R.id.linear_home_from_dte);
        linear_home_to_dte = (LinearLayout) findViewById(R.id.linear_home_to_dte);
        linear_change_password = (LinearLayout) findViewById(R.id.linear_change_password);
        linear_home_my_orders = (LinearLayout) findViewById(R.id.linear_home_my_orders);
        linear_home_associated_shops  = (LinearLayout) findViewById(R.id.linear_home_associated_shops);
        linear_home_addAShop = (LinearLayout) findViewById(R.id.linear_home_addAShop);
        linear_home_addADeliveryLocation = (LinearLayout) findViewById(R.id.linear_home_addADeliveryLocation);
        linear_home_all_delivery_person = (LinearLayout) findViewById(R.id.linear_home_all_delivery_person);
        linear_home_todays_delivery = (LinearLayout) findViewById(R.id.linear_home_todays_delivery);
        //linear_home_associateAProductCategory = (LinearLayout) findViewById(R.id.linear_home_associateAProductCategory);
        linear_home_associatedProductCategory = (LinearLayout) findViewById(R.id.linear_home_associatedProductCategory);
        //Bars
        linear_bar_deliveryLocation = (LinearLayout) findViewById(R.id.linear_bar_deliveryLocation);
        linear_bar_deliveryPerson = (LinearLayout) findViewById(R.id.linear_bar_deliveryPerson);
        linear_bar_product = (LinearLayout) findViewById(R.id.linear_bar_product);

        if(USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE)) {
            linear_home_associated_shops.setVisibility(View.GONE);
            linear_home_addAShop.setVisibility(View.GONE);
            linear_home_addADeliveryLocation.setVisibility(View.GONE);
            linear_home_all_delivery_person.setVisibility(View.GONE);
        }
        else if(USER_TYPE.equals(AppConstant.UserType.SELLER_HUB_TYPE)) {
            linear_home_seller_hub_profile.setVisibility(View.GONE);
        }
        else if(USER_TYPE.equals(AppConstant.UserType.DELIVERY_PERSON_TYPE)) {
            //Menu
            linear_home_shop_operation_time.setVisibility(View.GONE);
            linear_home_associated_product.setVisibility(View.GONE);
            linear_home_add_a_product.setVisibility(View.GONE);
            linear_home_associated_delivery_person.setVisibility(View.GONE);
            linear_home_add_delivery_person.setVisibility(View.GONE);
            linear_home_delivery_location.setVisibility(View.GONE);
            linear_home_addAShop.setVisibility(View.GONE);
            linear_home_addADeliveryLocation.setVisibility(View.GONE);
            linear_home_all_delivery_person.setVisibility(View.GONE);
            //Bars
            linear_bar_deliveryLocation.setVisibility(View.GONE);
            linear_bar_deliveryPerson.setVisibility(View.GONE);
            linear_bar_product.setVisibility(View.GONE);
            linear_home_associatedProductCategory.setVisibility(View.GONE);
        }

        tv_userId.setText(PreferenceKeeper.getInstance().getUserId());
        tv_userType.setText(PreferenceKeeper.getInstance().getUserType());

    }

    private void setOrderStatusSpinner()
    {
        final List<String> orderStatus = new ArrayList<>();
        orderStatus.add(AppConstant.STATUS.STATUS_ALL);
        orderStatus.add(AppConstant.STATUS.STATUS_ORDERED);
        orderStatus.add(AppConstant.STATUS.STATUS_CONFIRMED);
        orderStatus.add(AppConstant.STATUS.STATUS_SUBSCRIBED);
        orderStatus.add(AppConstant.STATUS.STATUS_DELIVERED);
        orderStatus.add(AppConstant.STATUS.STATUS_CLOSED);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, orderStatus);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_orderStatus.setAdapter(dataAdapter);
        spinner_orderStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                orderStatusValue = orderStatus.get(pos);
                if(!shopIdValue.equals(getString(R.string.please_select)))
                    fetchMyOrderDetailApi(from, to);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                if(!shopIdValue.equals(getString(R.string.please_select)))
                    fetchMyOrderDetailApi(from, to);
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
                if(!shopIdValue.equals(getString(R.string.please_select)))
                fetchMyOrderDetailApi(from, to);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void associateShopIdAPI() {

        showProgressBar();
        AppHttpRequest request = AppRequestBuilder.associatedShopId(new AppResponseListener<AssociatedShopIdResponse>(AssociatedShopIdResponse.class, this) {
            @Override
            public void onSuccess(AssociatedShopIdResponse result) {
                hideProgressBar();
                setShopIdSpinner(result.getAssociatedShops());
                //PreferenceKeeper.getInstance().setAssociatedShopId(result.getAssociatedShops());
            }

            @Override
            public void onError(ErrorObject error) {
                hideProgressBar();
            }
        });
        AppRestClient.getClient().sendRequest(this, request, TAG);
    }

    private void initDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer_home_root, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float moveFactor = (linear_home_logout.getWidth() * slideOffset);
                ll_home_layout.setTranslationX(moveFactor);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }
        }; // mDrawerLayout Toggle Object Made
        drawer_home_root.setDrawerListener(mDrawerToggle); // mDrawerLayout Listener set to the mDrawerLayout toggle
    }

    private void initUIListner() {
        linear_home_shopkeeper_profile.setOnClickListener(this);
        linear_home_seller_hub_profile.setOnClickListener(this);
        linear_home_associated_product.setOnClickListener(this);
        linear_home_delivery_location.setOnClickListener(this);
        linear_home_add_a_product.setOnClickListener(this);
        linear_home_associated_delivery_person.setOnClickListener(this);
        linear_home_add_delivery_person.setOnClickListener(this);
        linear_home_shop_operation_time.setOnClickListener(this);
        linear_home_contactus.setOnClickListener(this);
        linear_home_logout.setOnClickListener(this);
        linear_home_from_dte.setOnClickListener(this);
        linear_home_to_dte.setOnClickListener(this);
        linear_change_password.setOnClickListener(this);
        linear_home_my_orders.setOnClickListener(this);
        linear_home_associated_shops.setOnClickListener(this);
        linear_home_addAShop.setOnClickListener(this);
        linear_home_addADeliveryLocation.setOnClickListener(this);
        linear_home_all_delivery_person.setOnClickListener(this);
        linear_home_todays_delivery.setOnClickListener(this);
        linear_home_settings.setOnClickListener(this);
        //linear_home_associateAProductCategory.setOnClickListener(this);
        linear_home_associatedProductCategory.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        drawer_home_root.closeDrawer(nav_home_left_drawer);
        switch (view.getId()) {
            case R.id.linear_home_addAShop:
                launchActivity(AddAShopActivity.class);
                break;
            case R.id.linear_home_addADeliveryLocation:
                launchActivity(AddADeliveryLocationActivity.class);
                break;
            case R.id.linear_home_my_orders:
                launchActivity(HomeActivity.class);
                break;
            case R.id.linear_home_shopkeeper_profile:
                if(USER_TYPE.equals(AppConstant.UserType.SHOP_TYPE))
                launchActivity(ShopKeeperProfileActivity.class);
                else if(USER_TYPE.equals(AppConstant.UserType.DELIVERY_PERSON_TYPE))
                    launchActivity(DeliveryPersonProfileActivity.class);
                else if(USER_TYPE.equals(AppConstant.UserType.SELLER_HUB_TYPE))
                    launchActivity(SellerHUBProfileActivity.class);
                break;
            case R.id.linear_home_seller_hub_profile:
                launchActivity(SellerHUBProfileActivity.class);
                break;
            case R.id.linear_home_associated_product:
                launchActivity(AssociatedProductActivity.class);
                break;
            case R.id.linear_home_add_a_product:
                launchActivity(AddProductActivity.class);
                break;
            case R.id.linear_home_delivery_location:
                launchActivity(AssociatedDeliveryLocationsActivity.class);
                break;
            case R.id.linear_home_associated_delivery_person:
                launchActivity(AssociatedDeliveryPersonActivity.class);
                break;
            case R.id.linear_home_add_delivery_person:
                launchActivity(AddDeliveryPersonActivity.class);
                break;
            case R.id.linear_home_shop_operation_time:
                launchActivity(ViewShopOperationalTimeActivity.class);
                break;
            case R.id.linear_home_contactus:
                launchActivity(ContactUsActivity.class);
                break;
            case R.id.linear_home_logout:
                PreferenceKeeper.getInstance().clearData();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
                break;
            case R.id.linear_home_from_dte:
                setFromDate(tv_from_dte_home);
                break;
            case R.id.linear_home_to_dte:
                setToDate(tv_to_dte_home);
                break;
            case R.id.linear_change_password:
                launchActivity(ChangePasswordActivity.class);
                break;
            case R.id.linear_home_associated_shops:
                launchActivity(AssociatedShopsActivity.class);
                break;
            case R.id.linear_home_all_delivery_person:
                launchActivity(AllDeliveryPersonsActivity.class);
                break;
            /*case R.id.linear_home_associateAProductCategory:
                launchActivity(AssociateAProductCategoryActivity.class);
                break;
                */
            case R.id.linear_home_associatedProductCategory:
                launchActivity(AssociatedProductCategoriesActivity.class);
                break;

            case R.id.linear_home_todays_delivery:
                launchActivity(TodaysDeliveriesActivity.class);
                break;
            case R.id.linear_home_settings:
                launchActivity(SettingsActivity.class);
                break;


        }
    }




    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


}
