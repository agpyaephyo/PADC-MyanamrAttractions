package xyz.aungpyaephyo.padc.myanmarattractions.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.MyanmarAttractionsConstants;


/**
 * Created by aung on 6/1/16.
 */
public class SharedDialog {

    public static void confirmYesNo(Activity activity, String msg, final YesNoConfirmDelegate controller) {
        AlertDialog mDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(msg)
                .setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        controller.onConfirmYes();
                    }
                })
                .setNegativeButton(R.string.confirm_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        controller.onConfirmNo();
                    }
                });

        // Create the AlertDialog object
        mDialog = builder.create();
        if (mDialog != null)
            mDialog.show();
    }

    public static void confirmYesNoWithTheme(Activity activity, String msg, final YesNoConfirmDelegate controller) {
        confirmYesNoWithTheme(activity, msg, null, null, controller);
    }

    public static void confirmYesNoWithTheme(Activity activity, String msg, String yesBtn, String cancelBtn, final YesNoConfirmDelegate controller) {
        confirmYesNoWithTheme(activity, msg, yesBtn, cancelBtn, true, controller);
    }

    public static void confirmYesNoWithTheme(Activity activity, String msg, String yesBtn, String cancelBtn, boolean isCancellable, final YesNoConfirmDelegate controller) {
        if (!activity.isFinishing() && !TextUtils.isEmpty(msg)) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_comfirm);
            dialog.setCanceledOnTouchOutside(isCancellable);

            TextView tvConfirmMsg = (TextView) dialog.findViewById(R.id.tv_confirm_msg);
            tvConfirmMsg.setText(msg);

            Button btnOK = (Button) dialog.findViewById(R.id.btn_confirm_yes);
            if (!TextUtils.isEmpty(yesBtn)) {
                btnOK.setText(yesBtn);
            }

            Button btnCancel = (Button) dialog.findViewById(R.id.tv_confirm_no);
            if (!TextUtils.isEmpty(cancelBtn)) {
                btnCancel.setText(cancelBtn);
            }

            LinearLayout llDialogControls = (LinearLayout) dialog.findViewById(R.id.ll_controls);
            if (yesBtn.length() > MyanmarAttractionsConstants.DIALOG_BUTTON_LABEL_LIMIT || cancelBtn.length() > MyanmarAttractionsConstants.DIALOG_BUTTON_LABEL_LIMIT) {
                llDialogControls.setOrientation(LinearLayout.VERTICAL);
            }

            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (controller != null) {
                        controller.onConfirmYes();
                    }
                }
            });

            // if button is clicked, close the custom dialog
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (controller != null) {
                        controller.onConfirmNo();
                    }
                }
            });
            dialog.show();
        }
    }

    /*
    public static void confirmNewVersionUpgrade(Activity activity, String newVersion, YesNoConfirmController controller) {
        confirmYesNoWithTheme(activity, activity.getString(R.string.prompt_msg_force_upgrade, newVersion), activity.getString(R.string.confirm_upgrade), activity.getString(R.string.confirm_upgrade_not_now), false, controller);
    }
    */

    public static void promptMsgWithTheme(Activity activity, String msg) {
        promptMsgWithTheme(activity, msg, activity.getString(R.string.confirm_ok), null);
    }

    public static void promptMsgWithTheme(Activity activity, String msg, String yesBtn, final PromptConfirmDelegate delegate) {
        promptMsgWithTheme(activity, msg, yesBtn, true, delegate);
    }

    public static void promptMsgWithTheme(Activity activity, String msg, String yesBtn, boolean isCancellable, final PromptConfirmDelegate delegate) {
        if (!activity.isFinishing() && !TextUtils.isEmpty(msg)) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_comfirm);
            dialog.setCanceledOnTouchOutside(isCancellable);

            TextView tvConfirmMsg = (TextView) dialog.findViewById(R.id.tv_confirm_msg);
            tvConfirmMsg.setText(msg);

            Button btnOK = (Button) dialog.findViewById(R.id.btn_confirm_yes);
            if (TextUtils.isEmpty(yesBtn)) {
                btnOK.setText(R.string.confirm_ok);
            } else {
                btnOK.setText(yesBtn);
            }

            Button btnCancel = (Button) dialog.findViewById(R.id.tv_confirm_no);
            btnCancel.setVisibility(View.GONE);

            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (delegate != null) {
                        delegate.onConfirmOk();
                    }
                }
            });
            dialog.show();
        }
    }

    public interface YesNoConfirmDelegate {

        void onConfirmYes();

        void onConfirmNo();
    }

    public interface PromptConfirmDelegate {

        void onConfirmOk();
    }
}
