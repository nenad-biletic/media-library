package com.medialibrary.service;

import static com.medialibrary.constants.FieldNames.ID;
import static com.medialibrary.constants.FieldNames.TITLE;

import com.medialibrary.exception.MediaAlreadyExistsException;
import com.medialibrary.exception.NoSuchMediaExistsException;
import com.medialibrary.model.Movie;
import com.medialibrary.repository.MovieRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

  private final MovieRepository movieRepository;

  public MovieService(@Autowired MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public String saveMovie(Movie movie) {
    Movie existingMovie = movieRepository.findByTitle(movie.getTitle());
    if (existingMovie == null) {
      movieRepository.save(movie);
      return "Movie saved successfully.";
    } else {
      throw new MediaAlreadyExistsException("Movie already exists!");
    }
  }

  public List<Movie> getMovies() {
    return movieRepository.findAll(Sort.by(Direction.ASC, ID));
  }

  public List<Movie> getMoviesSortedByName() {
    return movieRepository.findAll(Sort.by(Direction.ASC, TITLE));
  }

  public List<Movie> getMoviesByGenre(String genre) {
    return movieRepository.findByGenre(genre, Sort.by(Direction.ASC, TITLE));
  }

  public List<Movie> getMoviesByYearOfRelease(Integer yearOfRelease) {
    return movieRepository.findByYearOfRelease(yearOfRelease,
        Sort.by(Direction.ASC, TITLE));
  }

  public String updateMovie(Movie movie) {
    Movie existingMovie = movieRepository.findById(movie.getId()).orElse(null);
    if (existingMovie == null) {
      throw new NoSuchMediaExistsException("Movie with ID " + movie.getId() + " does not exist!");
    } else {
      existingMovie.setTitle(movie.getTitle());
      existingMovie.setGenre(movie.getGenre());
      existingMovie.setYearOfRelease(movie.getYearOfRelease());
      movieRepository.save(existingMovie);
      return "Movie updated successfully.";
    }
  }

  public String deleteMovie(Long id) {
    Movie existingMovie = movieRepository.findById(id).orElse(null);
    if (existingMovie == null) {
      throw new NoSuchMediaExistsException("Movie with ID " + id + " does not exist!");
    } else {
      movieRepository.deleteById(id);
      return "Movie deleted successfully.";
    }
  }
}
