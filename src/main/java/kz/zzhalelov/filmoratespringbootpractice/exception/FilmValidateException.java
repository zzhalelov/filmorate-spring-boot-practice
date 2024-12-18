package kz.zzhalelov.filmoratespringbootpractice.exception;

public class FilmValidateException extends RuntimeException {
    public FilmValidateException(String message) {
        super(message);
    }
}