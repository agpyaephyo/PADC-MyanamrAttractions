package xyz.aungpyaephyo.padc.myanmarattractions.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;

public class ScreenUtils {

    private static ScreenUtils objInstance;

    private Context context;

    private ScreenUtils(){
        context = MyanmarAttractionsApp.getContext();
    }

    public static ScreenUtils getObjInstance(){
        if(objInstance == null){
            objInstance = new ScreenUtils();
        }
        return objInstance;
    }

    /**
     * Get pixel from dpi.
     * @param dpi
     * @return
     */
    public int getPixelFromDPI(float dpi){
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpi, context.getResources().getDisplayMetrics()));
    }

    /**
     * Get absolute width of the screen.
     * @return
     */
    public int getWidthOfScreen() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);

        return metrics.widthPixels;
    }

    /**
     * Get absoute height of the screen.
     * @return
     */
    public int getHeightOfScreen() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);

        return metrics.heightPixels;
    }

    /**
     * Show soft keyboard in some situation.
     */
    public void showSoftKeyboard(EditText et) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Service.INPUT_METHOD_SERVICE);
        if(imm != null){
            imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * Hide soft keyboard based on the EditText which the focus is in.
     * @param et
     */
    public void hideSoftKeyboard(EditText et){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Service.INPUT_METHOD_SERVICE);
        if(imm != null){
            imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusbarColor(int colorReference, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // finally change the color
            window.setStatusBarColor(activity.getResources().getColor(colorReference));
        }
    }
}
