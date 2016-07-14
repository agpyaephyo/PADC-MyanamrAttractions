package xyz.aungpyaephyo.padc.myanmarattractions.views.items;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.aungpyaephyo.padc.myanmarattractions.R;

/**
 * Created by aung on 7/15/16.
 */
public class ViewItemCountry extends FrameLayout {

    @BindView(R.id.tv_country)
    TextView tvCountry;

    public ViewItemCountry(Context context) {
        super(context);
    }

    public ViewItemCountry(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewItemCountry(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);
    }

    public void setData(String country) {
        tvCountry.setText(country);
    }
}
