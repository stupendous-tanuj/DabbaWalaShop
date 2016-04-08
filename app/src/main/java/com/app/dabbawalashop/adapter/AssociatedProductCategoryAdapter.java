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
import com.app.dabbawalashop.activity.ViewShopProfileActivity;
import com.app.dabbawalashop.api.output.CommonResponse;
import com.app.dabbawalashop.api.output.ErrorObject;
import com.app.dabbawalashop.api.output.AssociatedProductCategory;
import com.app.dabbawalashop.constant.AppConstant;
import com.app.dabbawalashop.listner.IDialogListener;
import com.app.dabbawalashop.network.AppHttpRequest;
import com.app.dabbawalashop.network.AppRequestBuilder;
import com.app.dabbawalashop.network.AppResponseListener;
import com.app.dabbawalashop.network.AppRestClient;
import com.app.dabbawalashop.utils.DialogUtils;

import java.util.List;

public class AssociatedProductCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = HomeActivity.class.getSimpleName();
    private List<AssociatedProductCategory> associatedProductCategory;
    private BaseActivity activity;
    private String shopId;

    public AssociatedProductCategoryAdapter(BaseActivity activity, List<AssociatedProductCategory> associatedProductCategory, String shopId) {

        this.associatedProductCategory = associatedProductCategory;
        this.shopId = shopId;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View homeAdapter = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_associated_product_category, parent, false);
        return new AssociatedProductCategoryHolder(homeAdapter);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final AssociatedProductCategory data = getItem(position);
        AssociatedProductCategoryHolder holder = (AssociatedProductCategoryHolder) viewHolder;
        setData(holder, data, position);

    }

    private void setData(AssociatedProductCategoryHolder holder, final AssociatedProductCategory data, final int pos) {
        holder.tv_shopCategory.setText(data.getShopCategoryName());
        holder.tv_productCategory.setText(data.getProductCategoryName());
        holder.tv_fromDeliveryTime.setText(data.getFromDeliveryTime());
        holder.tv_toDeliveryTime.setText(data.getToDeliveryTime());

        holder.tv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogUtils.showDialogYesNo(activity, activity.getString(R.string.remove_shop_closing_record), activity.getString(R.string.yes), activity.getString(R.string.no), new IDialogListener() {
                    @Override
                    public void onClickOk() {
                        removeAssociatedProductCategoryAPI(shopId, data.getShopCategoryName(), data.getProductCategoryName(), pos);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });


    }


    private void removeAssociatedProductCategoryAPI(String shopId, String shopCategory, String productCategory,final int pos) {
        activity.showProgressBar();

        AppHttpRequest request = AppRequestBuilder.removeAssociatedProductCategoryAPI(shopId, shopCategory, productCategory, new AppResponseListener<CommonResponse>(CommonResponse.class, activity) {
            @Override
            public void onSuccess(CommonResponse result) {
                activity.hideProgressBar();
                activity.showToast(result.getSuccessMessage());
                associatedProductCategory.remove(pos);
                notifyDataSetChanged();
            }

            @Override
            public void onError(ErrorObject error) {
                activity.hideProgressBar();

            }
        });
        AppRestClient.getClient().sendRequest(activity, request, activity.TAG);
    }

    private AssociatedProductCategory getItem(int position) {
        return associatedProductCategory.get(position);
    }

    @Override
    public int getItemCount() {
        return associatedProductCategory != null ? associatedProductCategory.size() : 0;
    }

    public class AssociatedProductCategoryHolder extends RecyclerView.ViewHolder {

        private final TextView tv_shopCategory;
        private final TextView tv_productCategory;
        private final TextView tv_fromDeliveryTime;
        private final TextView tv_toDeliveryTime;
        private final TextView tv_remove;

        public AssociatedProductCategoryHolder(View view) {
            super(view);
            tv_remove = (TextView) view.findViewById(R.id.tv_remove);
            tv_fromDeliveryTime = (TextView) view.findViewById(R.id.tv_fromDeliveryTime);
            tv_toDeliveryTime = (TextView) view.findViewById(R.id.tv_toDeliveryTime);
            tv_productCategory = (TextView) view.findViewById(R.id.tv_productCategory);
            tv_shopCategory = (TextView) view.findViewById(R.id.tv_shopCategory);
        }
    }
}
