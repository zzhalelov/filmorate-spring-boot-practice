package kz.zzhalelov.filmoratespringbootpractice.service;

import kz.zzhalelov.filmoratespringbootpractice.exception.NotFoundException;
import kz.zzhalelov.filmoratespringbootpractice.model.Rating;
import kz.zzhalelov.filmoratespringbootpractice.storage.RatingStorage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    private final RatingStorage ratingStorage;

    public RatingService(RatingStorage ratingStorage) {
        this.ratingStorage = ratingStorage;
    }

    public List<Rating> findAll() {
        return ratingStorage.findAll();
    }

    public Rating findById(int ratingId) {
        return ratingStorage.findById(ratingId).orElseThrow(() -> new NotFoundException("Рейтинг не найден"));
    }
}
