package kz.zzhalelov.filmoratespringbootpractice.exception;

public class ValidateException extends RuntimeException {
    public ValidateException(String message) {
        super(message);
    }
}