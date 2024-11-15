package com.medialibrary.repository;

import com.medialibrary.model.Movie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

  @Query("SELECT u.movies FROM User u WHERE u.id = :id")
  List<Movie> findByUserId(Long id, Sort sort);

  @Query("SELECT m FROM Movie m WHERE m.user.id = :userId AND m.id = :movieId")
  Optional<Movie> findByUserIdAndMovieId(Long userId, Long movieId);

  @Query("SELECT m FROM Movie m WHERE LOWER(m.title) = LOWER(:title) AND m.user.id = :userId")
  Optional<Movie> findByTitle(@Param("title") String title, Long userId);

  @Query("SELECT m FROM Movie m WHERE LOWER(m.genre) = LOWER(:genre) AND m.user.id = :userId")
  List<Movie> findByGenre(@Param("genre") String genre, Long userId, Sort sort);

  @Query("SELECT m FROM Movie m WHERE m.yearOfRelease = :yearOfRelease AND m.user.id = :userId")
  List<Movie> findByYearOfRelease(@Param("yearOfRelease") Integer yearOfRelease, Long userId,
      Sort sort);
}
