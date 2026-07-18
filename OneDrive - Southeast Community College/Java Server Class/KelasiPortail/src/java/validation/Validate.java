/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.apache.catalina.realm.SecretKeyCredentialHandler;

/**
 *
 * @author kembe
 */
public class Validate {

    public static boolean EmailFormat(String email) {

        boolean isValid = true;
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if(email == null || email.trim().isEmpty() || email.length() > 100){
            
            isValid = false;
        }
        else if (email.matches(regex)) {

            isValid = true;
        } else {

            isValid = false;

        }
        return isValid;
    }

    public static boolean NameFormat(String name) {

        boolean isValid = true;
        String regex = "[A-Za-zÀ-Ÿ' -]+([ '-][A-Za-zÀ-Ÿ]+)*";

        if (name == null || name.isEmpty() || name.trim().length() <= 0 || name.trim().length() > 50) {

            isValid = false;
        } else if (!name.matches(regex)) {

            isValid = false;
        }

        return isValid;

    }

    public static boolean AddressFormat(String theHomeAddress) {

        boolean isValid = true;
        if (theHomeAddress == null || theHomeAddress.isEmpty() || theHomeAddress.length() > 100) {

            isValid = false;
        } else {

            isValid = true;
        }

        return isValid;

    }

    public static boolean DateFormat(String date) {

        if (date == null || date.trim().isEmpty()) {

            return false;

        }

        try {

            LocalDate theDate = LocalDate.parse(date);

            if (theDate.isAfter(LocalDate.now())) {

                return false;
            }

            return true;

        } catch (DateTimeParseException ex) {

            return false;
        }

    }

    public static boolean PhoneFormat(String phone) {

        if (phone == null || phone.trim().isEmpty() || phone.length() > 15) {
            return false;
        }

        String cleanedPhone = phone.replaceAll("[\\s()-]", "");

        return cleanedPhone.matches("^\\+[1-9][0-9]{7,14}$");
    }

    public static String CleanPhone(String phone) {

        if (phone == null) {
            return null;
        }

        return phone.replaceAll("[\\s()-]", "");
    }

    public static boolean PasswordFormat(String password) {

        if (password == null || password.isEmpty() || password.length() > 15) {
            return false;
        }

        String regex
                = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%!&*?]).{8,50}$";

        return password.matches(regex);
    }

    public static String hashPassword(String password) throws Exception {

        SecretKeyCredentialHandler ch
                = new SecretKeyCredentialHandler();

        ch.setAlgorithm("PBKDF2WithHmacSHA256");
        ch.setKeyLength(256);
        ch.setSaltLength(16);
        ch.setIterations(4096);

        return ch.mutate(password);
    }

}
