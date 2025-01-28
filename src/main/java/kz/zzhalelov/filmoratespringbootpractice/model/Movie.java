package kz.zzhalelov.filmoratespringbootpractice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Movie {
    private String title;
    private String mpa;
    private String genre;
    private double rating;
}