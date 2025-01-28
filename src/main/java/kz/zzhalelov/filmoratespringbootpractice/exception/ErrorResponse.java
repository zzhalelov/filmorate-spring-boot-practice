package kz.zzhalelov.filmoratespringbootpractice.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private final String error;
}