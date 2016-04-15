package com.app.dabbawalashop.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.activity.BaseActivity;
import com.app.dabbawalashop.activity.HomeActivity;
import com.app.dabbawalashop.activity.UpdateDeliveryDetailsActivity;
import com.app.dabbawalashop.api.output.DeliveryDetail;
import com.app.dabbawalashop.constant.AppConstant;

import java.util.List;


public class TodaysDeliveryDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = HomeActivity.class.getSimpleName();
    private List<DeliveryDetail> carts;
    private BaseActivity activity;

    public TodaysDeliveryDetailAdapter(BaseActivity activity, List<DeliveryDetail> carts) {
        this.carts = carts;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View homeAdapter = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_todays_delivery, parent, false);
        return new DeliveryDetailHolder(homeAdapter);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        final DeliveryDetail data = getItem(position);
        DeliveryDetailHolder holder = (DeliveryDetailHolder) viewHolder;
        setData(holder, data, position);

    }

    private void setData(DeliveryDetailHolder holder, final DeliveryDetail deliveryDetail, final int pos) {

        holder.orderId.setText(deliveryDetail.getOrderId());
        holder.orderPlacedBy.setText(deliveryDetail.getOrderPlacedBy());
        holder.orderPlacedTo.setText(deliveryDetail.getOrderPlacedTo());
        holder.deliveryDate.setText(deliveryDetail.getDeliveryDate());
        holder.deliveryStatus.setText(deliveryDetail.getDeliveryStatus());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1 = new Bundle();
                bundle1.putString(AppConstant.BUNDLE_KEY.ORDER_ID, deliveryDetail.getOrderId());
                bundle1.putString(AppConstant.BUNDLE_KEY.OrderPlacedTo, deliveryDetail.getOrderPlacedTo());
                bundle1.putString(AppConstant.BUNDLE_KEY.OrderPlacedBy, deliveryDetail.getOrderPlacedBy());
                bundle1.putString(AppConstant.BUNDLE_KEY.TODAYS_DELIVERY_DATE_FLAG, "1");
                bundle1.putString(AppConstant.BUNDLE_KEY.DELIVERY_STATUS, deliveryDetail.getDeliveryStatus());
                bundle1.putString(AppConstant.BUNDLE_KEY.DELIVERY_DATE, deliveryDetail.getDeliveryDate());
                        activity.launchActivity(UpdateDeliveryDetailsActivity.class, bundle1);
            }
        });
    }

    private DeliveryDetail getItem(int position) {
        return carts.get(position);
    }


    @Override
    public int getItemCount() {
        return carts != null ? carts.size() : 0;
    }

    class DeliveryDetailHolder extends RecyclerView.ViewHolder {

        private final LinearLayout linearLayout;
        public TextView orderId;
        public TextView orderPlacedBy;
        public TextView orderPlacedTo;
        public TextView deliveryDate;
        public TextView deliveryStatus;
        public DeliveryDetailHolder(View convertView) {
            super(convertView);
            orderId = (TextView) convertView.findViewById(R.id.tv_orderId);
            orderPlacedBy = (TextView) convertView.findViewById(R.id.tv_orderPlacedBy);
            orderPlacedTo = (TextView) convertView.findViewById(R.id.tv_orderPlacedTo);
            deliveryStatus = (TextView) convertView.findViewById(R.id.tv_deliveryStatus);
            deliveryDate = (TextView) convertView.findViewById(R.id.tv_deliveryDate);
            linearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayout);
        }
    }
}
