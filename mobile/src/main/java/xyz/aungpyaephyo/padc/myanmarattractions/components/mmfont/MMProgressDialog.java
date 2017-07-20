package xyz.aungpyaephyo.padc.myanmarattractions.components.mmfont;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.Html;

/**
 * Created by aung on 6/13/17.
 */
public class MMProgressDialog extends ProgressDialog {

    public MMProgressDialog(Context context) {
        super(context);
    }

    public MMProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void setMessage(CharSequence message) {
        if (!MMFontUtils.isSupportUnicode()) {
            super.setMessage(Html.fromHtml(MMFontUtils.uni2zg(message.toString())));
        } else {
            super.setMessage(Html.fromHtml(message.toString()));
        }
    }
}
