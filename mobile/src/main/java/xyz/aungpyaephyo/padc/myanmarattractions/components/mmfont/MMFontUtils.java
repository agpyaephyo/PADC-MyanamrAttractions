package xyz.aungpyaephyo.padc.myanmarattractions.components.mmfont;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aung on 6/13/17.
 */
public class MMFontUtils {

    private static Typeface mmTypeFace;

    public static final int ENCODING_ORIGIN_UN = 1;
    public static final int ENCODING_ORIGIN_ZG = 2;
    public static final int TEXT_ZAWGYI = 0;
    public static final int TEXT_UNICODE = 1;
    public static final int TEXT_XPartial = 2;

    private static String[] xp = new String[]{
            "န့်", "န့်", "်", "ႀ", "ႅ", "ႍ", "႐", "ါၗ", "ၨ", "ၩ", "ၩ", "ၪ", "ၫ", "ၬ", "ၭ", "ၮ", "ၰ",
            "ဍၲ", "ၳၲၳ", "ၴ", "ၵ", "ၵ", "ၷ", "ၷ", "ၸ", "ၹ", "ၺ", "ၻ", "ၼ", "ၽ", "ၾ", "ၿ", "ႀ", "ႄ", "ႅ",
            "ႄ", "ႅ", "ႄ", "ႅ", "ႄ", "ႌ", "ႏ", "႑", "႒", "႐႐ဴ", "ႎ", "ၥ", "ၦ", "ၧ", "ဵ", "ၙ", "ဏၱ", "ၘ",
            "႔", "႕", "ၶ", "ဋၮ", "ႁ", "ႂ", "ရ", "ည", "ဏၱ"
    };

    private static String[] zawgi = new String[]{
            "န္႔", "န္႔", "္", "်", "ျ", "ြ", "ွ", "ၚ", "ၥ", "ၦ", "ၧ", "ၨ", "ၩ", "ၪ", "ၫ", "ၬ", "ၭ", "ၮ",
            "ၯ", "ၰ", "ၱ", "ၲ", "ၳ", "ၴ", "ၵ", "ၶ", "ၷ", "ၸ", "ၹ", "ၺ", "ၻ", "ၼ", "ၽ", "ၾ", "ၿ", "ႀ", "ႁ",
            "ႂ", "ႃ", "ႄ", "ႅ", "ႆ", "ႇ", "ႈ", "ႉ", "ႊ", "ႋ", "ႌ", "ႍ", "ႎ", "ႏ", "႑", "႒", "႔", "႕", "႖",
            "႗", "ြၽ", "ွၽ", "႐", "ၫ", "႑"
    };

    public static void setMMFont(TextView view) {
        if (mmTypeFace == null) {
            mmTypeFace = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/mymm.ttf");
        }

        if (!isSupportUnicode()) {
            //view.setText(MMFontUtils.mmText(view.getText().toString(), MMFontUtils.TEXT_UNICODE, true, true));
            view.setTypeface(mmTypeFace);
        }
    }

    private static void applyMMFontToMenuItem(MenuItem menuItem) {
        String mNewTitle = menuItem.getTitle().toString();
        if (!isSupportUnicode()) {
            mNewTitle = uni2zg(mNewTitle);
        }
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
            int tabChildCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    TextView tv = (TextView) tabViewChild;
                    tv.setLineSpacing(1.2f, 1.2f);
                    //tv.setTextSize(ScreenUtils.getPixelFromDPI(tl.getContext(), 20));
                    if (!isSupportUnicode()) {
                        setMMFont(tv);
                        tv.setText(MMFontUtils.mmText(tv.getText().toString(), MMFontUtils.TEXT_UNICODE, true, true));
                    }
                }
            }
        }
    }

    public static void applyMMFontToSnackBar(Snackbar snackbar) {
        TextView tvMsgSnack = (TextView) (snackbar.getView()).findViewById(android.support.design.R.id.snackbar_text);
        if (tvMsgSnack != null) {
            tvMsgSnack.setLineSpacing(1.2f, 1.2f);
            if (!isSupportUnicode()) {
                MMFontUtils.setMMFont(tvMsgSnack);

                String msg = tvMsgSnack.getText().toString();
                tvMsgSnack.setText(MMFontUtils.mmText(msg, MMFontUtils.TEXT_UNICODE, true, true));

                /*
                String msg = tvMsgSnack.getText().toString();
                tvMsgSnack.setText(MMFontUtils.uni2zg(msg));
                */
            }
        }
    }

    public static Typeface getMmTypeFace() {
        return mmTypeFace;
    }

    //From Myat Min Soe's Repo
    public static boolean isSupportUnicode() {
        Paint paint = new Paint();
        return paint.measureText("\u1000\u1039\u1000") < paint.measureText("\u1000") + paint.measureText("\u1000") / 3;
    }

    public static String mmTextUnicodeOrigin(String originalText) {
        if (isSupportUnicode()) {
            return originalText;
        } else {
            return uni2zg(originalText);
        }
    }

    public static String mmTextZawgyiOrigin(String originalText) {
        if (isSupportUnicode()) {
            return zg2uni(originalText);
        } else {
            return originalText;
        }
    }

    public static String mmText(String originalText, int encodingOrigin) {
        if (encodingOrigin == ENCODING_ORIGIN_UN) {
            return mmTextUnicodeOrigin(originalText);
        } else if (encodingOrigin == ENCODING_ORIGIN_ZG) {
            return mmTextZawgyiOrigin(originalText);
        }

        return originalText;
    }

    //From Technomation's MMText
    public static String mmText(CharSequence originalText, int encodedFormat, boolean isSamsungSafe,
                                boolean isSyllableBreak) {

        switch (encodedFormat) {
            case TEXT_UNICODE:
                originalText = Uni2XP(String.valueOf(originalText));
                break;
            case TEXT_XPartial:

                break;
            case TEXT_ZAWGYI:
                originalText = ZG2XP(String.valueOf(originalText));
                break;
        }

        if (isSyllableBreak) {
            originalText = MyBreak.parser(String.valueOf(originalText));
        }

        if (isSamsungSafe) {
            originalText = ShiftCodes(String.valueOf(originalText));
        }
        return String.valueOf(originalText);
    }

    public static String Uni2XP(String str) {

/* uni1004103A1039 */
        str = str.replace("\u1004\u103A\u1039", "ၤ");
/* uni10391000 */
        str = str.replace("\u1039\u1000", "ၠ");
/* uni10391001 */
        str = str.replace("\u1039\u1001", "ၡ");
/* uni10391002 */
        str = str.replace("\u1039\u1002", "ၢ");
/* uni10391003 */
        str = str.replace("\u1039\u1003", "ၣ");
/* uni10391005 */
        str = str.replace("\u1039\u1005", "ၨ");
/* uni10391006 */
        str = str.replace("\u1039\u1006", "ၩ");
/* uni10391007 */
        str = str.replace("\u1039\u1007", "ၪ");
/* uni10391008 */
        str = str.replace("\u1039\u1008", "ၫ");
/* uni1039100F */
        str = str.replace("\u1039\u100F", "ၴ");
/* uni10391010 */
        str = str.replace("\u1039\u1010", "ၵ");
/* uni10391011 */
        str = str.replace("\u1039\u1011", "ၷ");
/* uni10391012 */
        str = str.replace("\u1039\u1012", "ၸ");
/* uni10391013 */
        str = str.replace("\u1039\u1013", "ၹ");
/* uni10391014 */
        str = str.replace("\u1039\u1014", "ၺ");
/* uni10391015 */
        str = str.replace("\u1039\u1015", "ၻ");
/* uni10391016 */
        str = str.replace("\u1039\u1016", "ၼ");
/* uni10391017 */
        str = str.replace("\u1039\u1017", "ၽ");
/* uni10391018 */
        str = str.replace("\u1039\u1018", "ၾ");
/* uni10391019 */
        str = str.replace("\u1039\u1019", "ၿ");
/* uni103B103D */
        str = str.replace("\u103B\u103D", "ႁ");
/* uni103C103D */
        str = str.replace("\u103C\u103D", "ႅxႍ");
/* uni103B103E */
        str = str.replace("\u103B\u103E", "ႂ");
/* uni103C103E */
        str = str.replace("\u103C\u103E", "ႅx႑");
/* uni103B103D103E */
        str = str.replace("\u103B\u103D\u103E", "ႃ");
/* uni103C103D103E */
        str = str.replace("\u103C\u103D\u103E", "ႅxႎ");
        str = str.replace("\u103d\u103e", "ႎ");
/* uni103E1030 */
        //ERROR str = str.replace("\u103E\u1030","");
        //specials
/* uni100C1039100C */
        str = str.replace("\u100C\u1039\u100C", "ၘ");
/* uni100B1039100C */
        //ERROR str = str.replace("\u100B\u1039\u100C","");
/* uni100F1039100D */
        str = str.replace("\u100F\u1039\u100D", "ဏ​ၱ");
/* uni100F1039100B */
        str = str.replace("\u100F\u1039\u100B", "ဏ​ၮ");
/* uni100F1039100C */
        str = str.replace("\u100F\u1039\u100C", "ဏ​ၰ");
/* uni100F1039100F */
        str = str.replace("\u100F\u1039\u100F", "ဏၴ​");
/* uni100F1039100E */
        str = str.replace("\u100F\u1039\u100E", "ဏ​ၳ");
/* uni100D1039100E */
        str = str.replace("\u100D\u1039\u100E", "ဎ​ၲ");
/* uni100D1039100D */
        str = str.replace("\u100D\u1039\u100D", "ဍ​ၮ");
/* uni100B1039100B */
        str = str.replace("\u100B\u1039\u100B", "ဋ​ၮ");
/* uni100710391009 */
        str = str.replace("\u1007\u1039\u1009", "ဥ​ၼ");
/* uni10391009 */
        str = str.replace("\u1039\u1009", "");
/* uni1039100B */
        str = str.replace("\u1039\u100B", "ၮ");
/* uni1039100Bvar */
        //ERROR str = str.replace("\u1039\u100B","");
/* uni1039100C */
        str = str.replace("\u1039\u100C", "ၰ");
/* uni1039100D */
        str = str.replace("\u1039\u100D", "ၱ");
/* uni1039100E */
        str = str.replace("\u1039\u103E", "ၳ");
/* uni1039100F */
        str = str.replace("\u1039\u100F", "ၴ");

        str = str.replace("ါ်", "ါၗ");

        str = str.replace("ဉ\u103a", "ဥ​်");
/* uni1039101B */
        //ERROR str = str.replace("\u1039\u101B","");
/* uni10391020 */
        //ERROR str = str.replace("\u1039\u1020","");
/* uni10391021 */
        //ERROR str = str.replace("\u1039\u1021","");
/* uni10391022 */
        //ERROR str = str.replace("\u1039\u1022","");
/* uni10511039100B */
        //ERROR str = str.replace("\u1051\u1039\u100B","");
/* uni10511039100C */
        //ERROR str = str.replace("\u1051\u1039\u100C","");
/* uni1039105A */
        //ERROR str = str.replace("\u1039\u105A","");
/* uni1039105B */
        //ERROR str = str.replace("\u1039\u105B","");
/* uni1039105C */
        //ERROR str = str.replace("\u1039\u105C","");
/* uni1039105D */
        //ERROR str = str.replace("\u1039\u105D","");

        char[] sawBefore = new char[]{
                '\u1025', '\u100A', '\u103B', '\u103C', '\u103D', '\u103E', '\u1039',
        };

        StringBuilder sb = new StringBuilder();
        int state = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= 0x1000 && c <= 0x1021) {
                if (state == 2) {
                    state = 1;
                } else {
                    state = 0;
                }
            }
            if (c == 0x1039) state = 2;
            if (isContain(sawBefore, c)) state = 1;
            if (state == 1 && c == 0x102f) c = 'ဳ';
            if (state == 1 && c == 0x1030) c = 'ဴ';

            sb.append(c);
        }
        str = sb.toString();

        char[] ukm = new char[]{'ႀ', 'ႍ', '႐', 'ု', 'ူ', 'န', 'ႅ'};
        //UKM
        sb = new StringBuilder();
        state = 0;
        boolean move = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= 0x1000 && c <= 0x1021) {
                state = 0;
                if (c != 'န') {
                    move = false;
                } else {
                    move = true;
                }
            }
            if (isContain(ukm, c)) move = true;
            if (c == '့') {
                if (move) {
                    c = '႔';
                }
            }
            sb.append(c);
        }
        str = sb.toString();

        // eVowel Arrangement
        sb = new StringBuilder();
        state = 0;
        int idx = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= 0x1000 && c <= 0x1021) {
                if (state == 2) {
                    state = 0;
                } else {
                    state = 0;
                    idx = i;
                }
            }
            if (c == 0x1039) state = 2;
            if (c == 0x1031) {
                sb.insert(idx, c);
            } else {
                sb.append(c);
            }
        }
        str = sb.toString();

        // Big Ya Yit
        char[] longcons = new char[]{
                '\u1000', '\u1003', '\u1006', '\u100F', '\u1010', '\u1011', '\u1018', '\u101C', '\u101E',
                '\u101F'
        };

        sb = new StringBuilder();
        state = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (isContain(longcons, c)) {
                state = 1;
            } else if (c >= 0x1000 && c <= 0x1021) state = 0;

            if (state == 1 && c == 0x103C) c = 'ႄ';
            sb.append(c);
        }
        str = sb.toString();

        // Ya Yit Rearrangement
        sb = new StringBuilder();
        state = 0;
        idx = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= 0x1000 && c <= 0x1021) {
                if (state == 2) {
                    state = 0;
                } else {
                    state = 0;
                    idx = i;
                }
            }
            if (c == 0x1039) state = 2;
            if (c == 0x103c) {
                sb.insert(idx, c);
            } else if (c == 'ႄ') {
                sb.insert(idx, 'ႄ');
            } else {
                sb.append(c);
            }
        }
        str = sb.toString();

        //Kinzi
        sb = new StringBuilder();
        state = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == 'ၤ') {
                state = 1;
            } else {
                sb.append(c);
            }
            if (state == 1 && (c >= 0x1000 && c <= 0x1021)) {
                sb.append('ၤ');
                state = 0;
            }
        }
        str = sb.toString();

        // Myanmar Above Base Replacements
        str = str.replace("ၤ\u102d", "ၥ");
        str = str.replace("ၤ\u102e", "ၦ");
        str = str.replace("ၤ\u1036", "ၧ");
        str = str.replace("\u102d\u1036", "ဵ");

        char[] na_after = new char[]{
                '\u102F', '\u1030', '\u103B', 'ႁ', 'ႃ', 'ႂ', '\u103D', 'ႎ', '\u103E',
        };
        // NA Shape
        sb = new StringBuilder();
        state = 0;
        idx = -1;
        boolean shorten = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= 0x1000 && c <= 0x1021) {
                state = 0;
                idx = -1;
                shorten = false;
            }
            if (c == 'ႄ' || c == 0x103c) shorten = true;
            if (c == 'န') {
                if (shorten) {
                    c = 'ၙ';
                } else {
                    idx = i;
                }
            }
            if (isContain(na_after, c)) {
                if (idx != -1) {
                    sb.setCharAt(idx, 'ၙ');
                    idx = -1;
                }
            }

            sb.append(c);
        }
        str = sb.toString();

        return str;
    }

    public static String ZG2XP(String s) {

        for (int i = xp.length - 1; i >= 0; i--) {
            s = s.replace(zawgi[i], xp[i]);
        }
        return s;
    }

    public static String ShiftCodes(String s) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= 0x1000 && s.charAt(i) <= 0x109f) {
                sb.append((char) ((int) s.charAt(i) + 0xD000));
            } else {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }

    private static boolean isContain(char[] array, char c) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == c) return true;
        }
        return false;
    }

    public static CharSequence getMMText(View v) {
        StringBuilder sb = new StringBuilder();
        if (v instanceof TextView) {
            String st = ((TextView) v).getText().toString();
            for (int i = 0; i < st.length(); i++) {
                if (st.charAt(i) >= (0x1000 + 0xD000) && st.charAt(i) <= (0x109f + 0xD000)) {
                    sb.append((char) ((int) st.charAt(i) - 0xD000));
                } else {
                    sb.append(st.charAt(i));
                }
            }
            return sb.toString();
        }
        return null;
    }

    //From saturngod's Rabit
    public static String uni2zg(String input) {
        String rule = "[ { \"from\": \"\u1004\u103a\u1039\", \"to\": \"\u1064\" }, { \"from\": \"\u1039\u1010\u103d\", \"to\": \"\u1096\" }, { \"from\": \"\u1014(?=[\u1030\u103d\u103e\u102f\u1039])\", \"to\": \"\u108f\" }, { \"from\": \"\u102b\u103a\", \"to\": \"\u105a\" }, { \"from\": \"\u100b\u1039\u100c\", \"to\": \"\u1092\" }, { \"from\": \"\u102d\u1036\", \"to\": \"\u108e\" }, { \"from\": \"\u104e\u1004\u103a\u1038\", \"to\": \"\u104e\" }, { \"from\": \"[\u1025\u1009](?=[\u1039\u102f\u1030])\", \"to\": \"\u106a\" }, { \"from\": \"[\u1025\u1009](?=[\u103a])\", \"to\": \"\u1025\" }, { \"from\": \"\u100a(?=[\u1039\u102f\u1030\u103d])\", \"to\": \"\u106b\" }, { \"from\": \"(\u1039[\u1000-\u1021])\u102f\", \"to\": \"$1\u1033\" }, { \"from\": \"(\u1039[\u1000-\u1021])\u1030\", \"to\": \"$1\u1034\" }, { \"from\": \"\u1039\u1000\", \"to\": \"\u1060\" }, { \"from\": \"\u1039\u1001\", \"to\": \"\u1061\" }, { \"from\": \"\u1039\u1002\", \"to\": \"\u1062\" }, { \"from\": \"\u1039\u1003\", \"to\": \"\u1063\" }, { \"from\": \"\u1039\u1005\", \"to\": \"\u1065\" }, { \"from\": \"\u1039\u1006\", \"to\": \"\u1066\" }, { \"from\": \"\u1039\u1007\", \"to\": \"\u1068\" }, { \"from\": \"\u1039\u1008\", \"to\": \"\u1069\" }, { \"from\": \"\u100a(?=[\u1039\u102f\u1030])\", \"to\": \"\u106b\" }, { \"from\": \"\u1039\u100b\", \"to\": \"\u106c\" }, { \"from\": \"\u1039\u100c\", \"to\": \"\u106d\" }, { \"from\": \"\u100d\u1039\u100d\", \"to\": \"\u106e\" }, { \"from\": \"\u100e\u1039\u100d\", \"to\": \"\u106f\" }, { \"from\": \"\u1039\u100f\", \"to\": \"\u1070\" }, { \"from\": \"\u1039\u1010\", \"to\": \"\u1071\" }, { \"from\": \"\u1039\u1011\", \"to\": \"\u1073\" }, { \"from\": \"\u1039\u1012\", \"to\": \"\u1075\" }, { \"from\": \"\u1039\u1013\", \"to\": \"\u1076\" }, { \"from\": \"\u1039\u1013\", \"to\": \"\u1076\" }, { \"from\": \"\u1039\u1014\", \"to\": \"\u1077\" }, { \"from\": \"\u1039\u1015\", \"to\": \"\u1078\" }, { \"from\": \"\u1039\u1016\", \"to\": \"\u1079\" }, { \"from\": \"\u1039\u1017\", \"to\": \"\u107a\" }, { \"from\": \"\u1039\u1018\", \"to\": \"\u107b\" }, { \"from\": \"\u1039\u1019\", \"to\": \"\u107c\" }, { \"from\": \"\u1039\u101c\", \"to\": \"\u1085\" }, { \"from\": \"\u103f\", \"to\": \"\u1086\" }, { \"from\": \"(\u103c)\u103e\", \"to\": \"$1\u1087\" }, { \"from\": \"\u103d\u103e\", \"to\": \"\u108a\" }, { \"from\": \"(\u1064)([\u1031]?)([\u103c]?)([\u1000-\u1021])\u102d\", \"to\": \"$2$3$4\u108b\" }, { \"from\": \"(\u1064)([\u1031]?)([\u103c]?)([\u1000-\u1021])\u102e\", \"to\": \"$2$3$4\u108c\" }, { \"from\": \"(\u1064)([\u1031]?)([\u103c]?)([\u1000-\u1021])\u1036\", \"to\": \"$2$3$4\u108d\" }, { \"from\": \"(\u1064)([\u1031]?)([\u103c]?)([\u1000-\u1021])\", \"to\": \"$2$3$4$1\" }, { \"from\": \"\u101b(?=[\u102f\u1030\u103d\u108a])\", \"to\": \"\u1090\" }, { \"from\": \"\u100f\u1039\u100d\", \"to\": \"\u1091\" }, { \"from\": \"\u100b\u1039\u100b\", \"to\": \"\u1097\" }, { \"from\": \"([\u1000-\u1021\u108f\u1029\u1090])([\u1060-\u1069\u106c\u106d\u1070-\u107c\u1085\u108a])?([\u103b-\u103e]*)?\u1031\", \"to\": \"\u1031$1$2$3\" }, { \"from\": \"([\u1000-\u1021\u1029])([\u1060-\u1069\u106c\u106d\u1070-\u107c\u1085])?(\u103c)\", \"to\": \"$3$1$2\" }, { \"from\": \"\u103a\", \"to\": \"\u1039\" }, { \"from\": \"\u103b\", \"to\": \"\u103a\" }, { \"from\": \"\u103c\", \"to\": \"\u103b\" }, { \"from\": \"\u103d\", \"to\": \"\u103c\" }, { \"from\": \"\u103e\", \"to\": \"\u103d\" }, { \"from\": \"\u103d\u102f\", \"to\": \"\u1088\" }, { \"from\": \"([\u102f\u1030\u1014\u101b\u103c\u108a\u103d\u1088])([\u1032\u1036]{0,1})\u1037\", \"to\": \"$1$2\u1095\" }, { \"from\": \"\u102f\u1095\", \"to\": \"\u102f\u1094\" }, { \"from\": \"([\u1014\u101b])([\u1032\u1036\u102d\u102e\u108b\u108c\u108d\u108e])\u1037\", \"to\": \"$1$2\u1095\" }, { \"from\": \"\u1095\u1039\", \"to\": \"\u1094\u1039\" }, { \"from\": \"([\u103a\u103b])([\u1000-\u1021])([\u1036\u102d\u102e\u108b\u108c\u108d\u108e]?)\u102f\", \"to\": \"$1$2$3\u1033\" }, { \"from\": \"([\u103a\u103b])([\u1000-\u1021])([\u1036\u102d\u102e\u108b\u108c\u108d\u108e]?)\u1030\", \"to\": \"$1$2$3\u1034\" }, { \"from\": \"\u103a\u102f\", \"to\": \"\u103a\u1033\" }, { \"from\": \"\u103a([\u1036\u102d\u102e\u108b\u108c\u108d\u108e])\u102f\", \"to\": \"\u103a$1\u1033\" }, { \"from\": \"([\u103a\u103b])([\u1000-\u1021])\u1030\", \"to\": \"$1$2\u1034\" }, { \"from\": \"\u103a\u1030\", \"to\": \"\u103a\u1034\" }, { \"from\": \"\u103a([\u1036\u102d\u102e\u108b\u108c\u108d\u108e])\u1030\", \"to\": \"\u103a$1\u1034\" }, { \"from\": \"\u103d\u1030\", \"to\": \"\u1089\" }, { \"from\": \"\u103b([\u1000\u1003\u1006\u100f\u1010\u1011\u1018\u101a\u101c\u101a\u101e\u101f])\", \"to\": \"\u107e$1\" }, { \"from\": \"\u107e([\u1000\u1003\u1006\u100f\u1010\u1011\u1018\u101a\u101c\u101a\u101e\u101f])([\u103c\u108a])([\u1032\u1036\u102d\u102e\u108b\u108c\u108d\u108e])\", \"to\": \"\u1084$1$2$3\" }, { \"from\": \"\u107e([\u1000\u1003\u1006\u100f\u1010\u1011\u1018\u101a\u101c\u101a\u101e\u101f])([\u103c\u108a])\", \"to\": \"\u1082$1$2\" }, { \"from\": \"\u107e([\u1000\u1003\u1006\u100f\u1010\u1011\u1018\u101a\u101c\u101a\u101e\u101f])([\u1033\u1034]?)([\u1032\u1036\u102d\u102e\u108b\u108c\u108d\u108e])\", \"to\": \"\u1080$1$2$3\" }, { \"from\": \"\u103b([\u1000-\u1021])([\u103c\u108a])([\u1032\u1036\u102d\u102e\u108b\u108c\u108d\u108e])\", \"to\": \"\u1083$1$2$3\" }, { \"from\": \"\u103b([\u1000-\u1021])([\u103c\u108a])\", \"to\": \"\u1081$1$2\" }, { \"from\": \"\u103b([\u1000-\u1021])([\u1033\u1034]?)([\u1032\u1036\u102d\u102e\u108b\u108c\u108d\u108e])\", \"to\": \"\u107f$1$2$3\" }, { \"from\": \"\u103a\u103d\", \"to\": \"\u103d\u103a\" }, { \"from\": \"\u103a([\u103c\u108a])\", \"to\": \"$1\u107d\" }, { \"from\": \"([\u1033\u1034])\u1094\", \"to\": \"$1\u1095\" }, {  \"from\": \"\u108F\u1071\",  \"to\" : \"\u108F\u1072\" }, {  \"from\": \"([\u1000-\u1021])([\u107B\u1066])\u102C\",  \"to\": \"$1\u102C$2\" }, {  \"from\": \"\u102C([\u107B\u1066])\u1037\",  \"to\": \"\u102C$1\u1094\" }]";
        return replaceWithRule(rule, input);
    }

    //From saturngod's Rabit
    public static String zg2uni(String input) {
        String rule = "[ { \"from\":\"\u200B\", \"to\" : \"\" }, { \"from\": \"(\u103d|\u1087)\", \"to\": \"\u103e\" }, { \"from\": \"\u103c\", \"to\": \"\u103d\" }, { \"from\": \"(\u103b|\u107e|\u107f|\u1080|\u1081|\u1082|\u1083|\u1084)\", \"to\": \"\u103c\" }, { \"from\": \"(\u103a|\u107d)\", \"to\": \"\u103b\" }, { \"from\": \"\u1039\", \"to\": \"\u103a\" }, { \"from\": \"(\u1066|\u1067)\", \"to\": \"\u1039\u1006\" }, { \"from\": \"\u106a\", \"to\": \"\u1009\" }, { \"from\": \"\u106b\", \"to\": \"\u100a\" }, { \"from\": \"\u106c\", \"to\": \"\u1039\u100b\" }, { \"from\": \"\u106d\", \"to\": \"\u1039\u100c\" }, { \"from\": \"\u106e\", \"to\": \"\u100d\u1039\u100d\" }, { \"from\": \"\u106f\", \"to\": \"\u100d\u1039\u100e\" }, { \"from\": \"\u1070\", \"to\": \"\u1039\u100f\" }, { \"from\": \"(\u1071|\u1072)\", \"to\": \"\u1039\u1010\" }, { \"from\": \"\u1060\", \"to\": \"\u1039\u1000\" }, { \"from\": \"\u1061\", \"to\": \"\u1039\u1001\" }, { \"from\": \"\u1062\", \"to\": \"\u1039\u1002\" }, { \"from\": \"\u1063\", \"to\": \"\u1039\u1003\" }, { \"from\": \"\u1065\", \"to\": \"\u1039\u1005\" }, { \"from\": \"\u1068\", \"to\": \"\u1039\u1007\" }, { \"from\": \"\u1069\", \"to\": \"\u1039\u1008\" }, { \"from\": \"(\u1073|\u1074)\", \"to\": \"\u1039\u1011\" }, { \"from\": \"\u1075\", \"to\": \"\u1039\u1012\" }, { \"from\": \"\u1076\", \"to\": \"\u1039\u1013\" }, { \"from\": \"\u1077\", \"to\": \"\u1039\u1014\" }, { \"from\": \"\u1078\", \"to\": \"\u1039\u1015\" }, { \"from\": \"\u1079\", \"to\": \"\u1039\u1016\" }, { \"from\": \"\u107a\", \"to\": \"\u1039\u1017\" }, { \"from\": \"\u107c\", \"to\": \"\u1039\u1019\" }, { \"from\": \"\u1085\", \"to\": \"\u1039\u101c\" }, { \"from\": \"\u1033\", \"to\": \"\u102f\" }, { \"from\": \"\u1034\", \"to\": \"\u1030\" }, { \"from\": \"\u103f\", \"to\": \"\u1030\" }, { \"from\": \"\u1086\", \"to\": \"\u103f\" }, { \"from\": \"\u1036\u1088\", \"to\": \"\u1088\u1036\" }, { \"from\": \"\u1088\", \"to\": \"\u103e\u102f\" }, { \"from\": \"\u1089\", \"to\": \"\u103e\u1030\" }, { \"from\": \"\u108a\", \"to\": \"\u103d\u103e\" }, { \"from\": \"([\u1000-\u1021])\u1064\", \"to\": \"\u1004\u103a\u1039$1\" }, { \"from\": \"([\u1000-\u1021])\u108b\", \"to\": \"\u1004\u103a\u1039$1\u102d\" }, { \"from\": \"([\u1000-\u1021])\u108c\", \"to\": \"\u1004\u103a\u1039$1\u102e\" }, { \"from\": \"([\u1000-\u1021])\u108d\", \"to\": \"\u1004\u103a\u1039$1\u1036\" }, { \"from\": \"\u108e\", \"to\": \"\u102d\u1036\" }, { \"from\": \"\u108f\", \"to\": \"\u1014\" }, { \"from\": \"\u1090\", \"to\": \"\u101b\" }, { \"from\": \"\u1091\", \"to\": \"\u100f\u1039\u100d\" }, { \"from\": \"\u1019\u102c(\u107b|\u1093)\", \"to\": \"\u1019\u1039\u1018\u102c\" }, { \"from\": \"(\u107b|\u1093)\", \"to\": \"\u1039\u1018\" }, { \"from\": \"(\u1094|\u1095)\", \"to\": \"\u1037\" }, { \"from\": \"\u1096\", \"to\": \"\u1039\u1010\u103d\" }, { \"from\": \"\u1097\", \"to\": \"\u100b\u1039\u100b\" }, { \"from\": \"\u103c([\u1000-\u1021])([\u1000-\u1021])?\", \"to\": \"$1\u103c$2\" }, { \"from\": \"([\u1000-\u1021])\u103c\u103a\", \"to\": \"\u103c$1\u103a\" }, { \"from\": \"\u1031([\u1000-\u1021])(\u103e)?(\u103b)?\", \"to\": \"$1$2$3\u1031\" }, { \"from\": \"([\u1000-\u1021])\u1031([\u103b\u103c\u103d\u103e]+)\", \"to\": \"$1$2\u1031\" }, { \"from\": \"\u1032\u103d\", \"to\": \"\u103d\u1032\" }, { \"from\": \"\u103d\u103b\", \"to\": \"\u103b\u103d\" }, { \"from\": \"\u103a\u1037\", \"to\": \"\u1037\u103a\" }, { \"from\": \"\u102f(\u102d|\u102e|\u1036|\u1037)\u102f\", \"to\": \"\u102f$1\" }, { \"from\": \"\u102f\u102f\", \"to\": \"\u102f\" }, { \"from\": \"(\u102f|\u1030)(\u102d|\u102e)\", \"to\": \"$2$1\" }, { \"from\": \"(\u103e)(\u103b|\u1037)\", \"to\": \"$2$1\" }, { \"from\": \"\u1025(\u103a|\u102c)\", \"to\": \"\u1009$1\" }, { \"from\": \"\u1025\u102e\", \"to\": \"\u1026\" }, { \"from\": \"\u1005\u103b\", \"to\": \"\u1008\" }, { \"from\": \"\u1036(\u102f|\u1030)\", \"to\": \"$1\u1036\" }, { \"from\": \"\u1031\u1037\u103e\", \"to\": \"\u103e\u1031\u1037\" }, { \"from\": \"\u1031\u103e\u102c\", \"to\": \"\u103e\u1031\u102c\" }, { \"from\": \"\u105a\", \"to\": \"\u102b\u103a\" }, { \"from\": \"\u1031\u103b\u103e\", \"to\": \"\u103b\u103e\u1031\" }, { \"from\": \"(\u102d|\u102e)(\u103d|\u103e)\", \"to\": \"$2$1\" }, { \"from\": \"\u102c\u1039([\u1000-\u1021])\", \"to\": \"\u1039$1\u102c\" }, { \"from\": \"\u103c\u1004\u103a\u1039([\u1000-\u1021])\", \"to\": \"\u1004\u103a\u1039$1\u103c\" }, { \"from\": \"\u1039\u103c\u103a\u1039([\u1000-\u1021])\", \"to\": \"\u103a\u1039$1\u103c\" }, { \"from\": \"\u103c\u1039([\u1000-\u1021])\", \"to\": \"\u1039$1\u103c\" }, { \"from\": \"\u1036\u1039([\u1000-\u1021])\", \"to\": \"\u1039$1\u1036\" }, { \"from\": \"\u1092\", \"to\": \"\u100b\u1039\u100c\" }, { \"from\": \"\u104e\", \"to\": \"\u104e\u1004\u103a\u1038\" }, { \"from\": \"\u1040(\u102b|\u102c|\u1036)\", \"to\": \"\u101d$1\" }, { \"from\": \"\u1025\u1039\", \"to\": \"\u1009\u1039\" }, { \"from\": \"([\u1000-\u1021])\u103c\u1031\u103d\", \"to\": \"$1\u103c\u103d\u1031\" }, { \"from\": \"([\u1000-\u1021])\u103b\u1031\u103d(\u103e)?\", \"to\": \"$1\u103b\u103d$2\u1031\" }, { \"from\": \"([\u1000-\u1021])\u103d\u1031\u103b\", \"to\": \"$1\u103b\u103d\u1031\" }, { \"from\": \"([\u1000-\u1021])\u1031(\u1039[\u1000-\u1021])\", \"to\": \"$1$2\u1031\" }, {  \"from\" : \" \u1037\",  \"to\": \"\u1037\" }]";
        return replaceWithRule(rule, input);
    }

    //From saturngod's Rabit
    private static String replaceWithRule(String rule, String output) {
        try {
            JSONArray rule_array = new JSONArray(rule);
            int max_loop = rule_array.length();

            //because of JDK 7 bugs in Android
            output = output.replace("null", "\uFFFF\uFFFF");

            for (int i = 0; i < max_loop; i++) {

                JSONObject obj = rule_array.getJSONObject(i);
                String from = obj.getString("from");
                String to = obj.getString("to");

                output = output.replaceAll(from, to);
                output = output.replace("null", "");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        output = output.replace("\uFFFF\uFFFF", "null");
        return output;

    }
}
