package by.overone.online_store1.validator;

import by.overone.online_store1.dto.UserAllInfoDTO;
import by.overone.online_store1.dto.UserDateilsDTO;
import by.overone.online_store1.dto.UserRegistrationDTO;
import by.overone.online_store1.validator.exception.ValidatorException;

public class UserValidator {

    private final static String LOGIN_REGEX = "^[\\w]{4,12}$";
    private final static String EMAIL_REGEX = "^[\\S]+@[\\w]+\\.[\\a-z]+$";
    private final static String PASSWORD_REGEX = "^[\\w]{8,16}$";
    private final static String PHONE_REGEX = "^(\\+375|80)(17|29|33|44)(\\d){7}$";
    private final static String NAME_REGEX = "^[a-zA-Z]{2,30}$";
    private final static String ADDRESS_REGEX = "^[\\w]{5,50}$";


    public static boolean validateUserRegistrationDTO(UserRegistrationDTO user) throws ValidatorException {
        if (validateLogin(user.getLogin()) && validateEmail(user.getEmail()) && validatePassword(user.getPassword())) {
            return validateLogin(user.getLogin()) && validateEmail(user.getEmail()) && validatePassword(user.getPassword());
        }else {
            throw new ValidatorException("incorrect data");
        }
    }

    public static boolean validateUserDateils(UserDateilsDTO user) throws ValidatorException {
        if (validateName(user.getName()) && validateName(user.getSurname())
                && validateAddress(user.getAddress()) && validetePhone(user.getPhone())) {
            return validateName(user.getName()) && validateName(user.getSurname()) && validateAddress(user.getAddress())
                    && validetePhone(user.getPhone());
        }else {
            throw new ValidatorException("incorrect data");
        }
    }


    public static boolean validateUserAllDateils(UserAllInfoDTO user) throws ValidatorException {
        if (validateLogin(user.getLogin()) && validateEmail(user.getEmail()) && validatePassword(user.getPassword())
                && validateName(user.getName()) && validateName(user.getSurname()) && validateAddress(user.getAddress())
                && validetePhone(user.getPhone())){
            return validateLogin(user.getLogin()) && validateEmail(user.getEmail()) && validatePassword(user.getPassword())
                    && validateName(user.getName()) && validateName(user.getSurname()) && validateAddress(user.getAddress())
                    && validetePhone(user.getPhone());
        }else {
            throw new ValidatorException("incorrect data");
        }
    }

    private static boolean validateLogin(String login) {
        return login != null && !login.isBlank() && login.matches(LOGIN_REGEX);
    }

    private static boolean validateEmail(String email) {
        return email != null && !email.isBlank() && email.matches(EMAIL_REGEX);
    }

    private static boolean validatePassword(String password) {
        return password != null && !password.isBlank() && password.matches(PASSWORD_REGEX);
    }

    private static boolean validetePhone(String phone) {
        return phone != null && !phone.isBlank() && phone.matches(PHONE_REGEX);
    }

    private static boolean validateName(String name) {
        return name != null && !name.isBlank() && name.matches(NAME_REGEX);
    }

    private static boolean validateAddress(String address) {
        return address != null && !address.isBlank() && address.matches(ADDRESS_REGEX);
    }
}
