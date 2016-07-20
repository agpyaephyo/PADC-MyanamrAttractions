package xyz.aungpyaephyo.padc.myanmarattractions.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;
import xyz.aungpyaephyo.padc.myanmarattractions.components.CustomTypefaceSpan;

/**
 * Created by aung on 6/25/16.
 */
public class MMFontUtils {

    private static Typeface mmTypeFace;

    static {
        Context context = MyanmarAttractionsApp.getContext();
        mmTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/Zawgyi.ttf");
    }

    public static void setMMFont(TextView view) {
        view.setTypeface(mmTypeFace);
    }

    private static void applyMMFontToMenuItem(MenuItem menuItem) {
        SpannableString mNewTitle = new SpannableString(menuItem.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", mmTypeFace), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        menuItem.setTitle(mNewTitle);
    }

    public static void applyMMFontToMenu(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);

            //for applying a font to subMenu ...
            SubMenu subMenu = menuItem.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    MMFontUtils.applyMMFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            MMFontUtils.applyMMFontToMenuItem(menuItem);
        }
    }

    public static void applyMMFontToTabLayout(TabLayout tl) {
        ViewGroup vg = (ViewGroup) tl.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    TextView tv = (TextView) tabViewChild;
                    //tv.setLineSpacing(1.5f, 1.5f);
                    setMMFont(tv);
                }
            }
        }
    }
}
