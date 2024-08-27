package com.medialibrary.repository;

import com.medialibrary.model.Movie;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

  @Query("SELECT m FROM Movie m WHERE LOWER(m.title) = LOWER(:title)")
  Movie findByTitle(@Param("title") String title);

  @Query("SELECT m FROM Movie m WHERE LOWER(m.genre) = LOWER(:genre)")
  List<Movie> findByGenre(@Param("genre") String genre, Sort sort);

  List<Movie> findByYearOfRelease(Integer yearOfRelease, Sort sort);
}
