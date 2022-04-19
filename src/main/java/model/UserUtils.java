package model;

import java.time.LocalDate;

public class UserUtils {
    public static boolean chek (User user){
        if (user.getEmail().isBlank()) {
            return false;

        } else if (!user.getEmail().contains("@")){
            return false;
        } else if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            return false;
        } else if (user.getBirthday().isAfter(LocalDate.now())){
            return false;
        }
        return true;
    }
}