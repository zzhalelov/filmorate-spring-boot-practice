package kz.zzhalelov.filmoratespringbootpractice.storage;

import kz.zzhalelov.filmoratespringbootpractice.model.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingStorage {
    Optional<Rating> findById(int id);

    List<Rating> findAll();
}