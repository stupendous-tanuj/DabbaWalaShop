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
import com.app.dabbawalashop.activity.UpdateOrderDetailActivity;
import com.app.dabbawalashop.api.output.OrderDetail;
import com.app.dabbawalashop.constant.AppConstant;

import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = HomeActivity.class.getSimpleName();
    private List<OrderDetail> carts;
    private BaseActivity activity;

    public HomeAdapter(BaseActivity activity, List<OrderDetail> carts) {
        this.carts = carts;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View homeAdapter = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_home, parent, false);
        return new OrderDetailHolder(homeAdapter);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        final OrderDetail data = getItem(position);
        OrderDetailHolder holder = (OrderDetailHolder) viewHolder;
        setData(holder, data, position);

    }

    private void setData(OrderDetailHolder holder, final OrderDetail orderDetail, final int pos) {
        String orderStatus = orderDetail.getOrderStatus();
        String amountAdjusted = orderDetail.getAmountAdjustmentDone();
        holder.orderId.setText(orderDetail.getOrderId());
        holder.orderPlacedBy.setText(orderDetail.getOrderPlacedBy());
        holder.orderPlacedTo.setText(orderDetail.getOrderPlacedTo());
        holder.orderQuotedAmount.setText(orderDetail.getOrderQuotedAmount());
        holder.orderDeliveryType.setText(orderDetail.getOrderDeliveryType());
        holder.orderPaymentMethod.setText(orderDetail.getOrderPaymentMethod());
        holder.orderStatus.setText(orderStatus);
        holder.orderPaymentStatus.setText(orderDetail.getPaymentStatus());
        holder.orderDeliveryAddressIdentifier.setText(orderDetail.getOrderDeliveryAddressIdentifier());
        holder.tv_deliveryDates.setText(orderDetail.getDeliveryDates());
        holder.tv_orderSubscriptionType.setText(orderDetail.getOrderSubscriptionType());
        holder.tv_amountAdjustmentDone.setText(amountAdjusted.equals("1") ? "Yes" : "No" );
        holder.tv_orderBalanceAmount.setText(orderDetail.getOrderBalanceAmount());
        holder.tv_orderInvoiceAmount.setText(orderDetail.getOrderInvoiceAmount());
        holder.tv_orderCancellationReason.setText(orderDetail.getOrderCancellationReason());
        holder.tv_my_order_order_time.setText(orderDetail.getOrderCreationTimestamp());
        if(orderStatus.equals("Cancelled")){
            holder.ll_orderInvoiceAmount.setVisibility(View.VISIBLE);
            holder.ll_balanceAmount.setVisibility(View.VISIBLE);
            holder.ll_amountAdjusted.setVisibility(View.VISIBLE);
            holder.ll_orderCancellationReason.setVisibility(View.VISIBLE);
        }
        if(orderStatus.equals("Subscribed") || orderStatus.equals("Closed") || orderStatus.equals("Delivered")){
            holder.ll_orderInvoiceAmount.setVisibility(View.VISIBLE);
            holder.ll_balanceAmount.setVisibility(View.VISIBLE);
            holder.ll_amountAdjusted.setVisibility(View.VISIBLE);
        }


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1 = new Bundle();
                bundle1.putString(AppConstant.BUNDLE_KEY.ORDER_ID, orderDetail.getOrderId());
                bundle1.putString(AppConstant.BUNDLE_KEY.ORDER_STATUS, orderDetail.getOrderStatus());
                bundle1.putString(AppConstant.BUNDLE_KEY.OrderPlacedTo, orderDetail.getOrderPlacedTo());
                bundle1.putString(AppConstant.BUNDLE_KEY.OrderPlacedBy, orderDetail.getOrderPlacedBy());
                bundle1.putString(AppConstant.BUNDLE_KEY.OrderQuotedAmount, orderDetail.getOrderQuotedAmount());
                bundle1.putString(AppConstant.BUNDLE_KEY.OrderDeliveryType, orderDetail.getOrderDeliveryType());
                bundle1.putString(AppConstant.BUNDLE_KEY.OrderPaymentMethod, orderDetail.getOrderPaymentMethod());
                bundle1.putString(AppConstant.BUNDLE_KEY.PaymentStatus, orderDetail.getPaymentStatus());
                bundle1.putString(AppConstant.BUNDLE_KEY.OrderCreationTimestamp, orderDetail.getOrderCreationTimestamp());
                bundle1.putString(AppConstant.BUNDLE_KEY.OrderDeliveryAddressIdentifier, orderDetail.getOrderDeliveryAddressIdentifier());
                bundle1.putString(AppConstant.BUNDLE_KEY.DeliveryDates, orderDetail.getDeliveryDates());
                bundle1.putString(AppConstant.BUNDLE_KEY.OrderSubscriptionType, orderDetail.getOrderSubscriptionType());
                bundle1.putString(AppConstant.BUNDLE_KEY.AmountAdjustmentDone, orderDetail.getAmountAdjustmentDone());
                bundle1.putString(AppConstant.BUNDLE_KEY.OrderBalanceAmount, orderDetail.getOrderBalanceAmount());
                bundle1.putString(AppConstant.BUNDLE_KEY.OrderInvoiceAmount, orderDetail.getOrderInvoiceAmount());
                bundle1.putString(AppConstant.BUNDLE_KEY.OrderCancellationReason, orderDetail.getOrderCancellationReason());
                bundle1.putString(AppConstant.BUNDLE_KEY.ToOrderStatus, orderDetail.getToOrderValues());
                activity.launchActivity(UpdateOrderDetailActivity.class, bundle1);
            }
        });
    }

    private OrderDetail getItem(int position) {
        return carts.get(position);
    }

    @Override
    public int getItemCount() {
        return carts != null ? carts.size() : 0;
    }

    class OrderDetailHolder extends RecyclerView.ViewHolder {

        private final LinearLayout linearLayout;
        public TextView orderId;
        public TextView orderPlacedBy;
        public TextView orderPlacedTo;
        public TextView orderQuotedAmount;
        public TextView orderDeliveryType;
        public TextView orderPaymentMethod;
        public TextView orderPaymentStatus;
        public TextView orderStatus;
        public TextView orderDeliveryAddressIdentifier;
        public TextView tv_my_order_order_time;
        public TextView tv_deliveryDates;
        public TextView tv_orderSubscriptionType;
        public TextView tv_amountAdjustmentDone;
        public TextView tv_orderBalanceAmount;
        public TextView tv_orderInvoiceAmount;
        public TextView tv_orderCancellationReason;
        public LinearLayout ll_orderCancellationReason;
        public LinearLayout ll_orderInvoiceAmount;
        public LinearLayout ll_amountAdjusted;
        public LinearLayout ll_balanceAmount;
        public OrderDetailHolder(View convertView) {
            super(convertView);
            orderId = (TextView) convertView.findViewById(R.id.tv_my_order_id);
            orderPlacedBy = (TextView) convertView.findViewById(R.id.tv_my_order_place_by);
            orderPlacedTo = (TextView) convertView.findViewById(R.id.tv_my_order_place_to);
            orderQuotedAmount = (TextView) convertView.findViewById(R.id.tv_my_order_quoted_ammont);
            orderDeliveryType = (TextView) convertView.findViewById(R.id.tv_my_order_delievry_type);
            orderPaymentMethod = (TextView) convertView.findViewById(R.id.tv_my_order_payment_method);
            orderPaymentStatus = (TextView) convertView.findViewById(R.id.tv_my_payment_status);
            orderStatus = (TextView) convertView.findViewById(R.id.tv_my_order_status);
            orderDeliveryAddressIdentifier = (TextView) convertView.findViewById(R.id.tv_my_order_address_id);
            tv_deliveryDates = (TextView) convertView.findViewById(R.id.tv_deliveryDates);
            tv_orderSubscriptionType = (TextView) convertView.findViewById(R.id.tv_orderSubscriptionType);
            tv_amountAdjustmentDone = (TextView) convertView.findViewById(R.id.tv_amountAdjustmentDone);
            tv_orderBalanceAmount = (TextView) convertView.findViewById(R.id.tv_orderBalanceAmount);
            tv_orderInvoiceAmount = (TextView) convertView.findViewById(R.id.tv_orderInvoiceAmount);
            tv_orderCancellationReason = (TextView) convertView.findViewById(R.id.tv_orderCancellationReason);
            tv_my_order_order_time = (TextView) convertView.findViewById(R.id.tv_my_order_order_time);
            linearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayout);
            ll_orderInvoiceAmount = (LinearLayout) convertView.findViewById(R.id.ll_orderInvoiceAmount);
            ll_orderCancellationReason = (LinearLayout) convertView.findViewById(R.id.ll_orderCancellationReason);
            ll_amountAdjusted = (LinearLayout) convertView.findViewById(R.id.ll_amountAdjusted);
            ll_balanceAmount = (LinearLayout) convertView.findViewById(R.id.ll_balanceAmount);
            ll_orderCancellationReason.setVisibility(View.GONE);
            ll_orderInvoiceAmount.setVisibility(View.GONE);
            ll_balanceAmount.setVisibility(View.GONE);
            ll_amountAdjusted.setVisibility(View.GONE);
        }
    }
}