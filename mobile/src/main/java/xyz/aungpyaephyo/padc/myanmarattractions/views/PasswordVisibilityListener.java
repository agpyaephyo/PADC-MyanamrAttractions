package xyz.aungpyaephyo.padc.myanmarattractions.views;

import android.os.Build;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import xyz.aungpyaephyo.padc.myanmarattractions.R;

/**
 * Created by aung on 7/14/16.
 */
public class PasswordVisibilityListener implements View.OnTouchListener {

    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_TOP = 1;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_BOTTOM = 3;

    private boolean isPasswordShown = false;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        EditText etPassword = (EditText) view;
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getRawX() >= (etPassword.getRight() - etPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                if (isPasswordShown) {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, view.getContext().getResources().getDrawable(R.drawable.ic_visibility_24dp, view.getContext().getTheme()), null);
                    } else {
                        etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, view.getContext().getResources().getDrawable(R.drawable.ic_visibility_24dp), null);
                    }

                    etPassword.setTransformationMethod(new PasswordTransformationMethod());
                } else {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, view.getContext().getResources().getDrawable(R.drawable.ic_visibility_off_24dp, view.getContext().getTheme()), null);
                    } else {
                        etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, view.getContext().getResources().getDrawable(R.drawable.ic_visibility_off_24dp), null);
                    }

                    etPassword.setTransformationMethod(null);
                }
                isPasswordShown = !isPasswordShown;

                return true;
            }
        }
        return false;
    }
}
