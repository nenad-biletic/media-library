package com.medialibrary.controller;

import static com.medialibrary.constants.AttributeNames.MOVIE;
import static com.medialibrary.constants.AttributeNames.MOVIES;
import static com.medialibrary.constants.Messages.ERROR;
import static com.medialibrary.constants.Messages.SUCCESS;
import static com.medialibrary.constants.Templates.ADD_MOVIE;
import static com.medialibrary.constants.Templates.DELETE_MOVIE;
import static com.medialibrary.constants.Templates.MOVIES_TABLE;
import static com.medialibrary.constants.Templates.UPDATE_MOVIE;

import com.medialibrary.exception.MediaAlreadyExistsException;
import com.medialibrary.exception.NoSuchMediaExistsException;
import com.medialibrary.model.Movie;
import com.medialibrary.service.MovieService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/library/movies")
public class MovieController {

  private final MovieService movieService;
  private List<Movie> movies = new ArrayList<>();

  public MovieController(@Autowired MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping("/save")
  public String addMovie(Model model) {
    model.addAttribute(MOVIE, new Movie());
    return ADD_MOVIE;
  }

  @PostMapping("/save")
  public String saveMovie(Model model, @ModelAttribute Movie movie) {
    try {
      String result = movieService.saveMovie(movie);
      model.addAttribute(SUCCESS, result);
    } catch (MediaAlreadyExistsException e) {
      model.addAttribute(ERROR, e.getMessage());
    }
    return ADD_MOVIE;
  }

  @GetMapping
  public String getMovies(Model model) {
    movies = movieService.getMovies();
    model.addAttribute(MOVIES, movies);
    return MOVIES_TABLE;
  }

  @GetMapping("/genre/{genre}")
  public String getMoviesByGenre(Model model, @PathVariable String genre) {
    movies = movieService.getMoviesByGenre(genre);
    model.addAttribute(MOVIES, movies);
    return MOVIES_TABLE;
  }

  @GetMapping("/year/{yearOfRelease}")
  public String getMoviesByYearOfRelease(Model model, @PathVariable Integer yearOfRelease) {
    movies = movieService.getMoviesByYearOfRelease(yearOfRelease);
    model.addAttribute(MOVIES, movies);
    return MOVIES_TABLE;
  }

  @GetMapping("/update")
  public String getUpdateInfo(Model model, Movie movie) {
    model.addAttribute(MOVIE, movie);
    return UPDATE_MOVIE;
  }

  @PutMapping("/update")
  public String updateMovie(Model model, @ModelAttribute Movie movie) {
    try {
      String result = movieService.updateMovie(movie);
      model.addAttribute(SUCCESS, result);
    } catch (NoSuchMediaExistsException e) {
      model.addAttribute(ERROR, e.getMessage());
    }
    return UPDATE_MOVIE;
  }

  @GetMapping("/delete")
  public String getDeleteInfo() {
    return DELETE_MOVIE;
  }

  @DeleteMapping("/delete")
  public String deleteMovie(Model model, @RequestParam Long movieId) {
    try {
      String result = movieService.deleteMovie(movieId);
      model.addAttribute(SUCCESS, result);
    } catch (NoSuchMediaExistsException e) {
      model.addAttribute(ERROR, e.getMessage());
    }
    return DELETE_MOVIE;
  }
}
