package xyz.aungpyaephyo.padc.myanmarattractions.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by aung on 7/16/16.
 */
public class CommonUtils {

    public static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(MyanmarAttractionsConstants.EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
