package grimreaper.pollvault;

import android.support.design.widget.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    static boolean isValid(String pass){
        boolean valid = false;
        if(pass.length() < 8 || pass.length() > 20) return valid;
        String expression = "^(?=.*[A-Za-z])(?=.*\\\\d)(?=.*[$@$!%*#?&])[A-Za-z\\\\d$@$!%*#?&]{8,}$";
        CharSequence inputStr = pass;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            valid = true;
        }
        return valid;
    }

}
