package kz.zzhalelov.filmoratespringbootpractice.controller;

import kz.zzhalelov.filmoratespringbootpractice.model.Rating;
import kz.zzhalelov.filmoratespringbootpractice.service.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @GetMapping
    public List<Rating> findAll() {
        return ratingService.findAll();
    }

    @GetMapping("/{ratingId}")
    public Rating findById(@PathVariable int ratingId) {
        return ratingService.findById(ratingId);
    }
}