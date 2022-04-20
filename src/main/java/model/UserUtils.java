package model;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserUtils {
    public static boolean chek (User user){
        if (user.getEmail().isBlank()) {
            return false;

        } else if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            return false;
        } else if (user.getBirthday().isAfter(LocalDate.now())){
            return false;
        }

        String validEmail = "(.+@.+\\..+)";
        Pattern p = Pattern.compile(validEmail);
        Matcher m = p.matcher(user.getEmail());
        return m.matches();
    }
}