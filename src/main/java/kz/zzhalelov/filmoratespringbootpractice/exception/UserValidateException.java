package kz.zzhalelov.filmoratespringbootpractice.exception;

public class UserValidateException extends RuntimeException {
    public UserValidateException(String message) {
        super(message);
    }
}