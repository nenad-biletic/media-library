package com.medialibrary.service;

import static com.medialibrary.constants.FieldNames.ID;
import static com.medialibrary.constants.FieldNames.TITLE;

import com.medialibrary.authentication.AuthenticationService;
import com.medialibrary.exception.MediaAlreadyExistsException;
import com.medialibrary.exception.NoSuchMediaExistsException;
import com.medialibrary.model.Game;
import com.medialibrary.model.User;
import com.medialibrary.repository.GameRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GameService {

  private final GameRepository gameRepository;
  private final AuthenticationService authenticationService;

  @Autowired
  public GameService(GameRepository gameRepository, AuthenticationService authenticationService) {
    this.gameRepository = gameRepository;
    this.authenticationService = authenticationService;
  }

  public String saveGame(Game game) {
    User user = authenticationService.getCurrentUser();
    Optional<Game> gameOpt = gameRepository.findByTitle(game.getTitle(), user.getId());

    if (gameOpt.isPresent()) {
      throw new MediaAlreadyExistsException(
          "Game with title '" + game.getTitle() + "' already exists!");
    } else {
      game.setUser(user);
      gameRepository.save(game);
      return "Game saved successfully.";
    }
  }

  public List<Game> getGames() {
    User user = authenticationService.getCurrentUser();
    return gameRepository.findByUserId(user.getId(), Sort.by(Direction.ASC, ID));
  }

  public List<Game> getGamesSortedByName() {
    User user = authenticationService.getCurrentUser();
    return gameRepository.findByUserId(user.getId(), Sort.by(Direction.ASC, TITLE));
  }

  public List<Game> getGamesByGenre(String genre) {
    User user = authenticationService.getCurrentUser();
    return gameRepository.findByGenre(genre, user.getId(), Sort.by(Direction.ASC, TITLE));
  }

  public List<Game> getGamesByYearOfRelease(Integer yearOfRelease) {
    User user = authenticationService.getCurrentUser();
    return gameRepository.findByYearOfRelease(yearOfRelease, user.getId(),
        Sort.by(Direction.ASC, TITLE));
  }

  public String updateGame(Game game) {
    User user = authenticationService.getCurrentUser();
    Optional<Game> gameOpt = gameRepository.findByUserIdAndGameId(user.getId(), game.getId());

    if (gameOpt.isEmpty()) {
      throw new NoSuchMediaExistsException("Game with ID " + game.getId() + " does not exist!");
    } else {
      Game existingGame = gameOpt.get();
      existingGame.setTitle(game.getTitle());
      existingGame.setGenre(game.getGenre());
      existingGame.setYearOfRelease(game.getYearOfRelease());
      gameRepository.save(existingGame);
      return "Game updated successfully.";
    }
  }

  public String deleteGame(Long gameId) {
    User user = authenticationService.getCurrentUser();
    Optional<Game> gameOpt = gameRepository.findByUserIdAndGameId(user.getId(), gameId);

    if (gameOpt.isEmpty()) {
      throw new NoSuchMediaExistsException("Game with ID " + gameId + " does not exist!");
    } else {
      gameRepository.deleteById(gameId);
      return "Game deleted successfully.";
    }
  }
}
