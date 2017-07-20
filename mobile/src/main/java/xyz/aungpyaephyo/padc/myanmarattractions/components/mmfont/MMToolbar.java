package xyz.aungpyaephyo.padc.myanmarattractions.components.mmfont;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.AttributeSet;

/**
 * Created by aung on 7/14/17.
 */

public class MMToolbar extends Toolbar {

    public MMToolbar(Context context) {
        super(context);
    }

    public MMToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MMToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTitle(@StringRes int resId) {
        setTitle(getContext().getString(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        if (!MMFontUtils.isSupportUnicode()) {
            super.setTitle(Html.fromHtml(MMFontUtils.uni2zg(title.toString())));
        } else {
            super.setTitle(Html.fromHtml(title.toString()));
        }
    }
}
