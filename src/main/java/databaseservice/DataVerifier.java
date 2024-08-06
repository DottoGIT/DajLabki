package databaseservice;

import databaseservice.models.Wymiana;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataVerifier {
    public static Boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static Boolean isWymianaValid(Wymiana wymiana){
        if(wymiana.getIndeks_1() == wymiana.getIndeks_2() ||
                wymiana.getDzien_1() == wymiana.getDzien_2() && wymiana.getGodz_1() == wymiana.getDzien_2())
            return false;
        return true;
    }
}
