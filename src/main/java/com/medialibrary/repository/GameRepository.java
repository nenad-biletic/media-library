package com.medialibrary.repository;

import com.medialibrary.model.Game;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

  @Query("SELECT g FROM Game g WHERE LOWER(g.title) = LOWER(:title)")
  Game findByTitle(@Param("title") String title);

  @Query("SELECT g FROM Game g WHERE LOWER(g.genre) = LOWER(:genre)")
  List<Game> findByGenre(@Param("genre") String genre, Sort sort);

  List<Game> findByYearOfRelease(Integer yearOfRelease, Sort sort);
}
