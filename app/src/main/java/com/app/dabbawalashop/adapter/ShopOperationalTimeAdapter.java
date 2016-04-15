package com.app.dabbawalashop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.activity.BaseActivity;
import com.app.dabbawalashop.activity.HomeActivity;
import com.app.dabbawalashop.api.output.CommonResponse;
import com.app.dabbawalashop.api.output.ErrorObject;
import com.app.dabbawalashop.api.output.ShopOperationalTime;
import com.app.dabbawalashop.listner.IDialogListener;
import com.app.dabbawalashop.network.AppHttpRequest;
import com.app.dabbawalashop.network.AppRequestBuilder;
import com.app.dabbawalashop.network.AppResponseListener;
import com.app.dabbawalashop.network.AppRestClient;
import com.app.dabbawalashop.utils.DialogUtils;

import java.util.List;

public class ShopOperationalTimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = HomeActivity.class.getSimpleName();
    private List<ShopOperationalTime> shopOperationalTime;
    private BaseActivity activity;
    private String shopId;

    public ShopOperationalTimeAdapter(BaseActivity activity, List<ShopOperationalTime> shopOperationalTime, String shopId) {

        this.shopOperationalTime = shopOperationalTime;
        this.shopId = shopId;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View homeAdapter = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shop_operational_time, parent, false);
        return new ShopOperationalTimeHolder(homeAdapter);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ShopOperationalTime data = getItem(position);
        ShopOperationalTimeHolder holder = (ShopOperationalTimeHolder) viewHolder;
        setData(holder, data, position);

    }

    private void setData(ShopOperationalTimeHolder holder, final ShopOperationalTime data, final int pos) {
        holder.tv_shopCategory.setText(data.getShopCategoryName());
        holder.tv_closingDate.setText(data.getClosingDate());
        holder.tv_productCategory.setText(data.getProductCategoryName());

        holder.tv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogUtils.showDialogYesNo(activity, activity.getString(R.string.remove_shop_closing_record), activity.getString(R.string.yes), activity.getString(R.string.no), new IDialogListener() {
                    @Override
                    public void onClickOk() {
                        removeShopOperationalTimeAPI(shopId, data.getClosingDate(),data.getShopCategoryName(),data.getProductCategoryName(), pos);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });


    }


    private void removeShopOperationalTimeAPI(String shopId, String closingDate, String shopCategory, String productCategory,final int pos) {
        activity.showProgressBar();

        AppHttpRequest request = AppRequestBuilder.removeShopOperationalTimeAPI(shopId, closingDate, shopCategory, productCategory, new AppResponseListener<CommonResponse>(CommonResponse.class, activity) {
            @Override
            public void onSuccess(CommonResponse result) {
                activity.hideProgressBar();
                activity.showToast(result.getSuccessMessage());
                shopOperationalTime.remove(pos);
                notifyDataSetChanged();
            }

            @Override
            public void onError(ErrorObject error) {
                activity.hideProgressBar();

            }
        });
        AppRestClient.getClient().sendRequest(activity, request, activity.TAG);
    }

    private ShopOperationalTime getItem(int position) {
        return shopOperationalTime.get(position);
    }

    @Override
    public int getItemCount() {
        return shopOperationalTime != null ? shopOperationalTime.size() : 0;
    }

    public class ShopOperationalTimeHolder extends RecyclerView.ViewHolder {

        private final TextView tv_shopCategory;
        private final TextView tv_productCategory;
        private final TextView tv_closingDate;
        private final TextView tv_remove;

        public ShopOperationalTimeHolder(View view) {
            super(view);
            tv_remove = (TextView) view.findViewById(R.id.tv_remove);
            tv_closingDate = (TextView) view.findViewById(R.id.tv_closingDate);
            tv_productCategory = (TextView) view.findViewById(R.id.tv_productCategory);
            tv_shopCategory = (TextView) view.findViewById(R.id.tv_shopCategory);
        }
    }
}
