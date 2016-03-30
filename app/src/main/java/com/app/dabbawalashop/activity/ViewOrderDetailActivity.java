package com.app.dabbawalashop.activity;

import android.os.Bundle;

import com.app.dabbawalashop.R;

public class ViewOrderDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_detail);
        setHeader("View Order Detail", "");
    }
}
