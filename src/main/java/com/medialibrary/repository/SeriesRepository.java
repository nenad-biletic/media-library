package com.medialibrary.repository;

import com.medialibrary.model.Series;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {

  @Query("SELECT s FROM Series s WHERE LOWER(s.title) = LOWER(:title)")
  Series findByTitle(@Param("title") String title);

  @Query("SELECT s FROM Series s WHERE LOWER(s.genre) = LOWER(:genre)")
  List<Series> findByGenre(@Param("genre") String genre, Sort sort);
}
