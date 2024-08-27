package com.medialibrary.service;

import static com.medialibrary.constants.FieldNames.ID;
import static com.medialibrary.constants.FieldNames.TITLE;

import com.medialibrary.exception.MediaAlreadyExistsException;
import com.medialibrary.exception.NoSuchMediaExistsException;
import com.medialibrary.model.Game;
import com.medialibrary.repository.GameRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class GameService {

  private final GameRepository gameRepository;

  public GameService(@Autowired GameRepository gameRepository) {
    this.gameRepository = gameRepository;
  }

  public String saveGame(Game game) {
    Game existingGame = gameRepository.findByTitle(game.getTitle());
    if (existingGame == null) {
      gameRepository.save(game);
      return "Game saved successfully.";
    } else {
      throw new MediaAlreadyExistsException("Game already exists!");
    }
  }

  public List<Game> getGames() {
    return gameRepository.findAll(Sort.by(Direction.ASC, ID));
  }

  public List<Game> getGamesSortedByName() {
    return gameRepository.findAll(Sort.by(Direction.ASC, TITLE));
  }

  public List<Game> getGamesByGenre(String genre) {
    return gameRepository.findByGenre(genre, Sort.by(Direction.ASC, TITLE));
  }

  public List<Game> getGamesByYearOfRelease(Integer yearOfRelease) {
    return gameRepository.findByYearOfRelease(yearOfRelease,
        Sort.by(Direction.ASC, TITLE));
  }

  public String updateGame(Game game) {
    Game existingGame = gameRepository.findById(game.getId()).orElse(null);
    if (existingGame == null) {
      throw new NoSuchMediaExistsException("Game with ID " + game.getId() + " does not exist!");
    } else {
      existingGame.setTitle(game.getTitle());
      existingGame.setGenre(game.getGenre());
      existingGame.setYearOfRelease(game.getYearOfRelease());
      gameRepository.save(existingGame);
      return "Game updated successfully.";
    }
  }

  public String deleteGame(Long id) {
    Game existingGame = gameRepository.findById(id).orElse(null);
    if (existingGame == null) {
      throw new NoSuchMediaExistsException("Game with ID " + id + " does not exist!");
    } else {
      gameRepository.deleteById(id);
      return "Game deleted successfully.";
    }
  }
}
