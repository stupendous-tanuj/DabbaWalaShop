package com.app.dabbawalashop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.activity.BaseActivity;
import com.app.dabbawalashop.activity.UpdateDeliveryDetailsActivity;
import com.app.dabbawalashop.api.output.CartDetail;

import java.util.List;


public class CartDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = UpdateDeliveryDetailsActivity.class.getSimpleName();

    private List<CartDetail> cartDetails;
    private BaseActivity activity;


    public CartDetailAdapter(BaseActivity activity, List<CartDetail> cartDetails) {
        this.cartDetails = cartDetails;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View homeAdapter = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cart_detail, parent, false);
        return new CartDetailHolder(homeAdapter);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final CartDetail data = getItem(position);
        CartDetailHolder holder = (CartDetailHolder) viewHolder;
        setData(holder, data, position);
    }

    private void setData(final CartDetailHolder holder, final CartDetail cartDetail, final int pos) {
        //iv_productImage;
        holder.tv_productQuantity.setText(cartDetail.getCartProductOrderedQuantity());
        holder.tv_product_name.setText(cartDetail.getProductNameEnglish());
        holder.tv_product_des.setText(cartDetail.getProductDescription());
        holder.tv_product_unit.setText(cartDetail.getProductPriceForUnits()+" "+cartDetail.getProductOrderUnit());
        holder.tv_product_price.setText("Price: "+cartDetail.getCartProductPrice());

    }

    private CartDetail getItem(int position) {
        return cartDetails.get(position);
    }

    @Override
    public int getItemCount() {
        return cartDetails != null ? cartDetails.size() : 0;
    }

    class CartDetailHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_productImage;
        private final TextView tv_productQuantity;
        private final TextView tv_product_name;
        private final TextView tv_product_des;
        private final TextView tv_product_unit;
        private final TextView tv_product_price;

        public CartDetailHolder(View v) {
            super(v);
            tv_productQuantity = (TextView) v.findViewById(R.id.tv_productQuantity);
            tv_product_name = (TextView) v.findViewById(R.id.tv_product_name);
            tv_product_des = (TextView) v.findViewById(R.id.tv_product_des);
            tv_product_price = (TextView) v.findViewById(R.id.tv_product_price);
            tv_product_unit = (TextView) v.findViewById(R.id.tv_product_unit);
            iv_productImage = (ImageView) v.findViewById(R.id.iv_productImage);
        }
    }


}
