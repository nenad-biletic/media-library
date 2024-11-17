package com.medialibrary.repository;

import com.medialibrary.model.Series;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {

  @Query("SELECT s FROM Series s WHERE s.user.id = :userId ORDER BY s.title ASC")
  List<Series> findByUserId(Long userId);

  @Query("SELECT s FROM Series s WHERE s.user.id = :userId AND s.id = :seriesId")
  Optional<Series> findByUserIdAndSeriesId(Long userId, Long seriesId);

  @Query("SELECT s FROM Series s WHERE LOWER(s.title) = LOWER(:title) AND s.user.id = :userId")
  Optional<Series> findByTitle(@Param("title") String title, Long userId);

  @Query("SELECT s FROM Series s WHERE LOWER(s.genre) = LOWER(:genre) AND s.user.id = :userId"
      + " ORDER BY s.title ASC")
  List<Series> findByGenre(@Param("genre") String genre, Long userId);
}
