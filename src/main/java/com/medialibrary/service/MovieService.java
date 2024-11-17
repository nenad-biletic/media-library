package com.medialibrary.service;

import com.medialibrary.authentication.AuthenticationService;
import com.medialibrary.exception.MediaAlreadyExistsException;
import com.medialibrary.exception.NoSuchMediaExistsException;
import com.medialibrary.model.Movie;
import com.medialibrary.model.User;
import com.medialibrary.repository.MovieRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MovieService {

  private final MovieRepository movieRepository;
  private final AuthenticationService authenticationService;

  @Autowired
  public MovieService(MovieRepository movieRepository,
      AuthenticationService authenticationService) {
    this.movieRepository = movieRepository;
    this.authenticationService = authenticationService;
  }

  public String saveMovie(Movie movie) {
    User user = authenticationService.getCurrentUser();
    Optional<Movie> movieOpt = movieRepository.findByTitle(movie.getTitle(), user.getId());

    if (movieOpt.isPresent()) {
      throw new MediaAlreadyExistsException(
          "Movie with title '" + movie.getTitle() + "' already exists!");
    } else {
      movie.setUser(user);
      movieRepository.save(movie);
      return "Movie saved successfully.";
    }
  }

  public List<Movie> getMovies() {
    User user = authenticationService.getCurrentUser();
    return movieRepository.findByUserId(user.getId());
  }

  public List<Movie> getMoviesByGenre(String genre) {
    User user = authenticationService.getCurrentUser();
    return movieRepository.findByGenre(genre, user.getId());
  }

  public List<Movie> getMoviesByYearOfRelease(Integer yearOfRelease) {
    User user = authenticationService.getCurrentUser();
    return movieRepository.findByYearOfRelease(yearOfRelease, user.getId());
  }

  public String updateMovie(Movie movie) {
    User user = authenticationService.getCurrentUser();
    Optional<Movie> movieOpt = movieRepository.findByUserIdAndMovieId(user.getId(), movie.getId());

    if (movieOpt.isEmpty()) {
      throw new NoSuchMediaExistsException("Movie with ID " + movie.getId() + " does not exist!");
    } else {
      Movie existingMovie = movieOpt.get();
      existingMovie.setTitle(movie.getTitle());
      existingMovie.setGenre(movie.getGenre());
      existingMovie.setYearOfRelease(movie.getYearOfRelease());
      movieRepository.save(existingMovie);
      return "Movie updated successfully.";
    }
  }

  public String deleteMovie(Long movieId) {
    User user = authenticationService.getCurrentUser();
    Optional<Movie> movieOpt = movieRepository.findByUserIdAndMovieId(user.getId(), movieId);

    if (movieOpt.isEmpty()) {
      throw new NoSuchMediaExistsException("Movie with ID " + movieId + " does not exist!");
    } else {
      movieRepository.deleteById(movieId);
      return "Movie deleted successfully.";
    }
  }
}
