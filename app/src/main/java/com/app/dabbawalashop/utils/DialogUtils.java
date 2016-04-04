package com.app.dabbawalashop.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.app.dabbawalashop.R;
import com.app.dabbawalashop.activity.BaseActivity;
import com.app.dabbawalashop.api.output.ErrorObject;
import com.app.dabbawalashop.listner.IDialogListener;


/**
 * Created by umesh on 11/9/15.
 */
public class DialogUtils {

    public static void showDialog(Context context, String message) {
        final MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(context);
        materialDialog.title(context.getString(R.string.text));
        materialDialog.content(message).positiveColorRes(R.color.blue_button_background).negativeColorRes(R.color.blue_button_background)
                .positiveText(context.getString(R.string.ok));
        if (!((BaseActivity) context).isFinishing())
            materialDialog.show();
    }


    public static boolean isMobileVarification(BaseActivity activity, String mobileNumber) {
        boolean checkmobileNumber = TextUtils.isEmpty(mobileNumber);
        if (checkmobileNumber) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_mobile_number));
            return false;
        }
        return true;
    }


    public static void showDialogYesNo(Context context, final String msg, String yes, final String no, final IDialogListener listener) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_yes_no);
        dialog.setCanceledOnTouchOutside(true);

        TextView tv_msg = (TextView) dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_cancel);
        tv_cancel.setText(no);
        TextView tv_ok = (TextView) dialog.findViewById(R.id.tv_ok);
        tv_ok.setText(yes);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                listener.onCancel();
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    listener.onCancel();
                }
                return false;
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onCancel();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onClickOk();
            }
        });
        dialog.show();
    }

    public static void showDialogNetwork(Context context, final String msg, final IDialogListener listener) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_network);
        TextView tv_ok = (TextView) dialog.findViewById(R.id.tv_ok);
        TextView msgg = (TextView) dialog.findViewById(R.id.view2);
        msgg.setText(msg);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.onClickOk();
            }
        });
        dialog.show();
    }

    public static void showDialogError(final Context context, final ErrorObject errors) {

        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(R.layout.dialog_errors);
            TextView tv_ok = (TextView) dialog.findViewById(R.id.tv_error_ok);
            TextView tv_error_code = (TextView) dialog.findViewById(R.id.tv_error_code);
            TextView tv_error_msg = (TextView) dialog.findViewById(R.id.tv_error_msg);
            TextView tv_support_number = (TextView) dialog.findViewById(R.id.tv_support_number);
            tv_error_code.setText(String.valueOf(errors.getErrorCode()));
            tv_error_msg.setText(errors.getErrorMessage());
            tv_support_number.setText(errors.getSupportContactNumber());

            dialog.findViewById(R.id.linear_support_number).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String number = errors.getSupportContactNumber();
                    if (number != null) {
                        try {
                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                            callIntent.setData(Uri.parse("tel:" + number));
                            context.startActivity(callIntent);
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(context, "Calling functinality is not founded", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Support number getting is null", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            tv_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (Exception e) {

        }
    }

    public static boolean isLoginVerification(BaseActivity activity, String shopid, String pssword) {

        boolean cname = TextUtils.isEmpty(shopid);
        boolean cEmail = TextUtils.isEmpty(pssword);

        if (cname) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_name));
            return false;
        }

        if (cEmail) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_email));
            return false;
        }
        return true;
    }

    public static boolean isSpinnerDefaultValue(BaseActivity activity, String spinnerValue, String labelName) {

        if(spinnerValue.equals(activity.getString(R.string.please_select))){
            DialogUtils.showDialog(activity, "Please select "+labelName);
            return false;
        }
        return true;
    }


    public static boolean isChangePasswordVerification(BaseActivity activity, String oldPassword, String newPassword, String confNewPassword) {

        boolean cOldPassword = TextUtils.isEmpty(oldPassword);
        boolean cNewPassword = TextUtils.isEmpty(newPassword);
        boolean cConfNewPassword = TextUtils.isEmpty(confNewPassword);

        if (cOldPassword) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_oldPassword));
            return false;
        }

        if (cNewPassword) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_newPassword));
            return false;
        }

        if (cConfNewPassword) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_confNewPassword));
            return false;
        }

        if(!newPassword.equals(confNewPassword)){
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_confNewPassword_not_match));
            return false;
        }
        return true;
    }

    public static boolean isResetPasswordVerification(BaseActivity activity, String name,String emailId,String mobileNumber,String idNumber,String userId) {

        boolean cname = TextUtils.isEmpty(name);
        boolean cemailId = TextUtils.isEmpty(emailId);
        boolean cmobileNumber = TextUtils.isEmpty(mobileNumber);
        boolean cidNumber = TextUtils.isEmpty(idNumber);
        boolean cuserId = TextUtils.isEmpty(userId);

        if (cname) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_name));
            return false;
        }

        if (cemailId) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_emailId));
            return false;
        }

        if (cmobileNumber) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_mobile_number));
            return false;
        }

        if (cidNumber) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_idNumber));
            return false;
        }

        if (cuserId) {
            DialogUtils.showDialog(activity, activity.getString(R.string.enter_userId));
            return false;
        }

        return true;
    }

    public static boolean isUpdateDeliveryDetailsVerification(BaseActivity activity, String toDeliveryStatusValue) {

        boolean ctoDeliveryStatusValue = TextUtils.isEmpty(toDeliveryStatusValue);

        if (ctoDeliveryStatusValue) {
            DialogUtils.showDialog(activity, activity.getString(R.string.blank_deliveryStatus));
            return false;
        }

        if (toDeliveryStatusValue.equals("NA")) {
            DialogUtils.showDialog(activity, activity.getString(R.string.NA_deliveryStatus));
            return false;
        }
        return true;
    }

    public static boolean isUpdateOrderDetailsVerification(BaseActivity activity, String toOrderStatusValue) {

        boolean ctoOrderStatusValue = TextUtils.isEmpty(toOrderStatusValue);

        if (ctoOrderStatusValue) {
            DialogUtils.showDialog(activity, activity.getString(R.string.blank_orderStatus));
            return false;
        }

        if (toOrderStatusValue.equals("NA")) {
            DialogUtils.showDialog(activity, activity.getString(R.string.NA_orderStatus));
            return false;
        }
        return true;
    }

    public static boolean isQuotedAmountVerification(BaseActivity activity, String quotedAmount, String invoiceAmount) {

        boolean ctoInvoiceAmount = TextUtils.isEmpty(invoiceAmount);

        if (ctoInvoiceAmount) {
            DialogUtils.showDialog(activity, activity.getString(R.string.blank_invoiceAmount));
            return false;
        }

        int quoted = Integer.parseInt(quotedAmount);
        int invoice = Integer.parseInt(invoiceAmount);

        if (quoted!=invoice) {
            DialogUtils.showDialog(activity, activity.getString(R.string.notEqual_quotedInvoice));
            return false;
        }
        return true;
    }


    public static boolean showDialogProduct(BaseActivity activity, String dailyPrice,String weeklyPrice, String monthlyPrice) {
        boolean cname = TextUtils.isEmpty(dailyPrice);
        boolean cEmail = TextUtils.isEmpty(weeklyPrice);
        boolean cMonthlyPrice = TextUtils.isEmpty(monthlyPrice);
        if (cname) {
            DialogUtils.showDialog(activity, "Daily Price should not be empty.");
            return false;
        }
        if (cEmail) {
            DialogUtils.showDialog(activity, "Weekly Price should not be empty.");
            return false;
        }
        if (cMonthlyPrice) {
            DialogUtils.showDialog(activity, "Monthly Price should not be empty.");
            return false;
        }
        return true;
    }

    public static boolean showDialogShopOpertion(BaseActivity activity, String closingDate, String openingTime, String closingTime) {
        boolean cname = TextUtils.isEmpty(closingDate);
        boolean copeningTime = TextUtils.isEmpty(openingTime);
        boolean cclosingTime = TextUtils.isEmpty(closingTime);

        if (cname) {
            DialogUtils.showDialog(activity, "Closing date is not empty");
            return false;
        }
        if (copeningTime) {
            DialogUtils.showDialog(activity, "Opening time is not empty");
            return false;
        }
        if (cclosingTime) {
            DialogUtils.showDialog(activity, "Closing time is not empty");
            return false;
        }
        int open = AppUtil.getTotlTime(openingTime);
        int close = AppUtil.getTotlTime(closingTime);
        if (open > close) {
            DialogUtils.showDialog(activity, "OpeningTime cannot be greater than Closing time");
            return false;
        }
        return true;
    }

    public static boolean showDialogddProduct(BaseActivity activity, String eName, String hName, String des, String noOfUnit) {
        boolean ceName = TextUtils.isEmpty(eName);
        boolean chName = TextUtils.isEmpty(hName);
        boolean cdes = TextUtils.isEmpty(des);
        boolean cnoOfUnit = TextUtils.isEmpty(noOfUnit);


        if (ceName && chName) {
            DialogUtils.showDialog(activity, "Product name is not empty");
            return false;
        }
        if (cdes) {
            DialogUtils.showDialog(activity, "Description is not empty");
            return false;
        }
        if (cnoOfUnit) {
            DialogUtils.showDialog(activity, "No. of unit is not empty");
            return false;
        }

        return true;

    }

    public static boolean showDialogDeliveryPerson(BaseActivity activity, String name, String mobileNumber, String address, String idType, String idNumber) {
        boolean ceName = TextUtils.isEmpty(name);
        boolean cmobileNumber = TextUtils.isEmpty(mobileNumber);
        boolean caddress = TextUtils.isEmpty(address);
        boolean cidType = TextUtils.isEmpty(idType);
        boolean cidNumber = TextUtils.isEmpty(idNumber);

        if (ceName) {
            DialogUtils.showDialog(activity, "Delivery Person name is not empty");
            return false;
        }
        if (cmobileNumber) {
            DialogUtils.showDialog(activity, "Mobile number is not empty");
            return false;
        }
        if (caddress) {
            DialogUtils.showDialog(activity, "Residential Address is not empty");
            return false;
        }
        if (cidType) {
            DialogUtils.showDialog(activity, "ID Type is not empty");
            return false;
        }
        if (cidNumber) {
            DialogUtils.showDialog(activity, "ID Number is not empty");
            return false;
        }

        return true;
    }
}
