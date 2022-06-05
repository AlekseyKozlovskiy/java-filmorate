package util;

import model.User;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    public static boolean validate(User user) {
        String validEmail = "(.+@.+\\..+)";
        Pattern p = Pattern.compile(validEmail);
        Matcher m = p.matcher(user.getEmail());

        return !(user.getEmail().isBlank())
                && !(user.getLogin() == null)
                && !(user.getLogin().isBlank())
                && !(user.getLogin().contains(" "))
                && !(user.getBirthday().isAfter(LocalDate.now()))
                && m.matches();
    }
}
