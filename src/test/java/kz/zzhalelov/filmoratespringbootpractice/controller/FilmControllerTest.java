package kz.zzhalelov.filmoratespringbootpractice.controller;

import kz.zzhalelov.filmoratespringbootpractice.model.Film;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmController filmController = new FilmController();

    @Test
    public void validFilm() {
        Film film = Film.builder()
                .name("Inception")
                .description("Movie with Di Caprio")
                .releaseDate(LocalDate.of(2009, 1, 1))
                .duration(160)
                .build();

        Film result = filmController.add(film);

        assertEquals("Inception", result.getName());
        assertNotEquals(0, result.getId());
    }

    @Test
    public void durationLessZero() {
        String expected = "продолжительность фильма должна быть положительной";
        Film film = Film.builder()
                .name("Breaking Bad")
                .description("School teacher becames an american version of Escobar")
                .releaseDate(LocalDate.of(2013, 1, 1))
                .duration(-58)
                .build();

        FilmValidateException ex = assertThrows(FilmValidateException.class, () -> filmController.add(film));
        assertEquals(expected, ex.getMessage());
    }
}