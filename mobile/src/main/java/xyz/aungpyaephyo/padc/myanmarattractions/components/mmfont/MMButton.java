package xyz.aungpyaephyo.padc.myanmarattractions.components.mmfont;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;


/**
 * Created by aung on 6/13/17.
 */
public class MMButton extends AppCompatButton {

    public MMButton(Context context) {
        super(context);
    }

    public MMButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MMButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode())
            MMFontUtils.setMMFont(this);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!MMFontUtils.isSupportUnicode()) {
            super.setText(MMFontUtils.mmText(text, MMFontUtils.TEXT_UNICODE, true, true), type);
        } else {
            super.setText(text, type);
        }
    }
}
