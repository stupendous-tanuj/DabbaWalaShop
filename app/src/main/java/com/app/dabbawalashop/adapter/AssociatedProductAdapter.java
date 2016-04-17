package com.app.dabbawalashop.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.activity.AssociateAProductActivity;
import com.app.dabbawalashop.activity.BaseActivity;
import com.app.dabbawalashop.api.output.CommonResponse;
import com.app.dabbawalashop.api.output.ErrorObject;
import com.app.dabbawalashop.api.output.Product;
import com.app.dabbawalashop.constant.AppConstant;
import com.app.dabbawalashop.listner.IDialogListener;
import com.app.dabbawalashop.network.AppHttpRequest;
import com.app.dabbawalashop.network.AppRequestBuilder;
import com.app.dabbawalashop.network.AppResponseListener;
import com.app.dabbawalashop.network.AppRestClient;
import com.app.dabbawalashop.utils.DialogUtils;

import java.util.List;

public class AssociatedProductAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private BaseActivity activity;
    private List<Product> product;
    private String shopId;


    public AssociatedProductAdapter(BaseActivity activity, List<Product> product, String shopId) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.product = product;
        this.shopId = shopId;
    }


    @Override
    public int getCount() {
        return product != null ? product.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return product != null ? product.get(position) : null;
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_associated_product, viewGroup, false);
            holder = new ViewHolder();
            holder.linear_associated_product = (LinearLayout) convertView.findViewById(R.id.linear_associated_product);
            holder.tv_ass_product_sku = (TextView) convertView.findViewById(R.id.tv_ass_product_sku);
            holder.tv_ass_product_name = (TextView) convertView.findViewById(R.id.tv_ass_product_name);
            holder.tv_ass_product_unit = (TextView) convertView.findViewById(R.id.tv_ass_product_unit);
            holder.tv_ass_product_daily_price = (TextView) convertView.findViewById(R.id.tv_ass_product_daily_price);
            holder.tv_ass_product_weekly_price = (TextView) convertView.findViewById(R.id.tv_ass_product_weekly_price);
            holder.tv_ass_product_monthly_price = (TextView) convertView.findViewById(R.id.tv_ass_product_monthly_price);
            holder.tv_ass_product_deassociate = (TextView) convertView.findViewById(R.id.tv_ass_product_deassociate);
            holder.tv_ass_product_update = (TextView) convertView.findViewById(R.id.tv_ass_product_update);
            holder.tv_productRegistrationStatus = (TextView) convertView.findViewById(R.id.tv_productRegistrationStatus);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setData(product.get(pos), holder, pos);

        return convertView;
    }

    private void setData(final Product product, final ViewHolder holder, final int pos) {
        holder.tv_ass_product_sku.setText(product.getProductSKUID());
        holder.tv_ass_product_name.setText(product.getProductNameEnglish());
        holder.tv_ass_product_unit.setText(product.getProductPriceForUnits() + " " + product.getProductOrderUnit());
        holder.tv_ass_product_daily_price.setText(product.getDailySubscriptionPrice());
        holder.tv_ass_product_weekly_price.setText(product.getWeeklySubscriptionPrice());
        holder.tv_ass_product_monthly_price.setText(product.getMonthlySubscriptionPrice());
        holder.tv_productRegistrationStatus.setText(product.getProductRegistrationStatus());
        holder.tv_ass_product_deassociate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogUtils.showDialogYesNo(activity, activity.getString(R.string.deassociated_product), activity.getString(R.string.yes), activity.getString(R.string.no), new IDialogListener() {
                    @Override
                    public void onClickOk() {
                        deassociateProductAPI(product.getProductSKUID(), shopId, pos);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });

        holder.linear_associated_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(AppConstant.BUNDLE_KEY.IS_ADD_ASSOCIATE_PRODUCT_HOME, false);
                bundle.putString(AppConstant.BUNDLE_KEY.SKU_ID, product.getProductSKUID());
                bundle.putString(AppConstant.BUNDLE_KEY.ENGLISH_NAME, product.getProductNameEnglish());
                bundle.putString(AppConstant.BUNDLE_KEY.MARATHI_NAME, product.getProductNameMarathi());
                bundle.putString(AppConstant.BUNDLE_KEY.DES, product.getProductDescription());
                bundle.putString(AppConstant.BUNDLE_KEY.UNIT, product.getProductPriceForUnits() + " " + product.getProductOrderUnit());
                bundle.putString(AppConstant.BUNDLE_KEY.DAILY_PRICE, product.getDailySubscriptionPrice());
                bundle.putString(AppConstant.BUNDLE_KEY.WEEKLY_PRICE, product.getWeeklySubscriptionPrice());
                bundle.putString(AppConstant.BUNDLE_KEY.MONTHLY_PRICE, product.getWeeklySubscriptionPrice());
                bundle.putString(AppConstant.BUNDLE_KEY.PRODUCT_STATUS, product.getProductRegistrationStatus());
                bundle.putString(AppConstant.BUNDLE_KEY.IMAGE, product.getProductImageName());
                activity.launchActivity(AssociateAProductActivity.class, bundle);
            }
        });
    }

    private void deassociateProductAPI(String productSKU, String shopId, final int pos) {
        activity.showProgressBar();

        AppHttpRequest request = AppRequestBuilder.deassociatedProductAPI(shopId, productSKU, new AppResponseListener<CommonResponse>(CommonResponse.class, activity) {
            @Override
            public void onSuccess(CommonResponse result) {
                activity.hideProgressBar();
                activity.showToast(result.getSuccessMessage());
                product.remove(pos);
                notifyDataSetChanged();
            }

            @Override
            public void onError(ErrorObject error) {
                activity.hideProgressBar();

            }
        });
        AppRestClient.getClient().sendRequest(activity, request, activity.TAG);
    }

    public class ViewHolder {
        private TextView tv_ass_product_sku;
        private TextView tv_ass_product_name;
        private TextView tv_ass_product_name_marathi;
        private TextView tv_ass_product_unit;
        private TextView tv_ass_product_weekly_price;
        private TextView tv_ass_product_monthly_price;
        private TextView tv_ass_product_daily_price;
        private TextView tv_ass_product_deassociate;
        private TextView tv_productRegistrationStatus;
        private TextView tv_ass_product_update;
        public LinearLayout linear_associated_product;
    }
}
