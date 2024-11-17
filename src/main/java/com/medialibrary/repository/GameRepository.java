package com.medialibrary.repository;

import com.medialibrary.model.Game;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

  @Query("SELECT g FROM Game g WHERE g.user.id = :userId ORDER BY g.title ASC")
  List<Game> findByUserId(Long userId);

  @Query("SELECT g FROM Game g WHERE g.user.id = :userId AND g.id = :gameId")
  Optional<Game> findByUserIdAndGameId(Long userId, Long gameId);

  @Query("SELECT g FROM Game g WHERE LOWER(g.title) = LOWER(:title) AND g.user.id = :userId")
  Optional<Game> findByTitle(@Param("title") String title, Long userId);

  @Query("SELECT g FROM Game g WHERE LOWER(g.genre) = LOWER(:genre) AND g.user.id = :userId"
      + " ORDER BY g.title ASC")
  List<Game> findByGenre(@Param("genre") String genre, Long userId);

  @Query("SELECT g FROM Game g WHERE g.yearOfRelease = :yearOfRelease AND g.user.id = :userId"
      + " ORDER BY g.title ASC")
  List<Game> findByYearOfRelease(@Param("yearOfRelease") Integer yearOfRelease, Long userId);
}
