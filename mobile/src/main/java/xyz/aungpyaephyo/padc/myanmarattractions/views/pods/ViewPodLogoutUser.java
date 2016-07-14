package xyz.aungpyaephyo.padc.myanmarattractions.views.pods;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.controllers.BaseController;
import xyz.aungpyaephyo.padc.myanmarattractions.controllers.ViewController;

/**
 * Created by aung on 7/6/16.
 */
public class ViewPodLogoutUser extends RelativeLayout implements ViewController {

    private LogoutUserController mController;

    public ViewPodLogoutUser(Context context) {
        super(context);
    }

    public ViewPodLogoutUser(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPodLogoutUser(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);
    }

    @OnClick(R.id.btn_login)
    public void onTapLogin(View view) {
        mController.onTapLogin();
    }

    @OnClick(R.id.btn_register)
    public void onTapRegister(View view) {
        mController.onTapRegister();
    }

    @Override
    public void setController(BaseController controller) {
        mController = (LogoutUserController) controller;
    }

    public interface LogoutUserController extends BaseController {
        void onTapLogin();

        void onTapRegister();
    }
}
