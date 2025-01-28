package kz.zzhalelov.filmoratespringbootpractice.controller;

import kz.zzhalelov.filmoratespringbootpractice.model.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/task2")
public class MovieController {
    List<Movie> movies = new ArrayList<>() {
        {
            add(new Movie("The Avengers", "PG-13", "Action", 8.0));
            add(new Movie("Mad Max: Fury Road", "R", "Action", 8.1));
            add(new Movie("John Wick", "R", "Action", 8.6));
            add(new Movie("The Hangover", "R", "Comedy", 7.7));
            add(new Movie("Bridesmaids", "R", "Comedy", 8.0));
            add(new Movie("21 Jump Street", "R", "Comedy", 8.0));
            add(new Movie("The Godfather", "R", "Drama", 9.2));
            add(new Movie("The Shawshank Redemption", "R", "Drama", 9.3));
            add(new Movie("The Dark Knight", "PG-13", "Drama", 9.0));
            add(new Movie("Interstellar", "PG-13", "Sci-Fi", 8.6));
            add(new Movie("Arrival", "PG-13", "Sci-Fi", 8.0));
            add(new Movie("Star Wars: The Force Awakens", "PG-13", "Sci-Fi", 7.9));
            add(new Movie("Toy Story", "G", "Animation", 8.3));
            add(new Movie("Spirited Away", "PG", "Animation", 8.6));
            add(new Movie("How to Train Your Dragon", "PG", "Animation", 8.1));
            add(new Movie("The Conjuring", "R", "Horror", 7.5));
            add(new Movie("Get Out", "R", "Horror", 7.8));
            add(new Movie("Hereditary", "R", "Horror", 7.9));
            add(new Movie("The Silence of the Lambs", "R", "Thriller", 8.6));
            add(new Movie("Se7en", "R", "Thriller", 8.6));
            add(new Movie("Parasite", "R", "Thriller", 8.6));
            add(new Movie("The Notebook", "PG-13", "Romance", 7.8));
            add(new Movie("Titanic", "PG-13", "Romance", 7.9));
            add(new Movie("Call Me By Your Name", "R", "Romance", 7.9));
            add(new Movie("The Lord of the Rings: The Fellowship of the Ring", "PG-13", "Fantasy", 8.8));
            add(new Movie("Harry Potter and the Sorcerer's Stone", "PG", "Fantasy", 7.6));
            add(new Movie("The Chronicles of Narnia: The Lion, the Witch and the Wardrobe", "PG", "Fantasy", 6.9));
            add(new Movie("Knives Out", "PG-13", "Mystery", 7.9));
            add(new Movie("Gone Girl", "R", "Mystery", 8.1));
            add(new Movie("The Girl on the Train", "R", "Mystery", 7.5));
            add(new Movie("Planet Earth", "G", "Documentary", 9.5));
            add(new Movie("Free Solo", "PG-13", "Documentary", 8.4));
            add(new Movie("Apollo 11", "G", "Documentary", 8.6));
            add(new Movie("La La Land", "PG-13", "Musical", 8.0));
            add(new Movie("The Greatest Showman", "PG", "Musical", 7.5));
            add(new Movie("Bohemian Rhapsody", "PG-13", "Musical", 8.0));
        }
    };

    // Фильтрация фильмов по жанрам
    //  /task2/movies                        - все фильмы
    //  /task2/movies?genres=Drama           - все фильмы с жанром драма
    //  /task2/movies?genres=Drama,Thriller  - все фильмы с жанром драма или триллер
    @GetMapping("/movies")
    public List<Movie> findAllGenres(@RequestParam(required = false) List<String> genres,
                                     @RequestParam(required = false) String title,
                                     @RequestParam(required = false) Double minRating,
                                     @RequestParam(required = false) Double maxRating,
                                     @RequestParam(required = false) List<String> mpa) {

        return movies.stream()
                .filter(movie -> (genres == null || genres.isEmpty()) || genres
                        .contains(movie.getGenre())
                        && (title == null || movie.getTitle().contains(title))
                        && (minRating == null || movie.getRating() >= minRating)
                        && (maxRating == null || movie.getRating() <= maxRating)
                        && (mpa == null || mpa.contains(movie.getMpa())))
                .toList();
    }
}