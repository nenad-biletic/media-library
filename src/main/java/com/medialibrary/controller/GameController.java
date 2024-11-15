package com.medialibrary.controller;

import static com.medialibrary.constants.AttributeNames.GAME;
import static com.medialibrary.constants.AttributeNames.GAMES;
import static com.medialibrary.constants.Messages.ERROR;
import static com.medialibrary.constants.Messages.SUCCESS;
import static com.medialibrary.constants.Templates.ADD_GAME;
import static com.medialibrary.constants.Templates.DELETE_GAME;
import static com.medialibrary.constants.Templates.GAMES_TABLE;
import static com.medialibrary.constants.Templates.UPDATE_GAME;

import com.medialibrary.exception.MediaAlreadyExistsException;
import com.medialibrary.exception.NoSuchMediaExistsException;
import com.medialibrary.model.Game;
import com.medialibrary.service.GameService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/library/games")
public class GameController {

  private final GameService gameService;
  private List<Game> games = new ArrayList<>();

  public GameController(@Autowired GameService gameService) {
    this.gameService = gameService;
  }

  @GetMapping("/save")
  public String addGame(Model model) {
    model.addAttribute(GAME, new Game());
    return ADD_GAME;
  }

  @PostMapping("/save")
  public String saveGame(Model model, @ModelAttribute Game game) {
    try {
      String result = gameService.saveGame(game);
      model.addAttribute(SUCCESS, result);
    } catch (MediaAlreadyExistsException e) {
      model.addAttribute(ERROR, e.getMessage());
    }
    return ADD_GAME;
  }

  @GetMapping
  public String getGames(Model model) {
    games = gameService.getGames();
    model.addAttribute(GAMES, games);
    return GAMES_TABLE;
  }

  @GetMapping("/sort")
  public String getGamesSortedByName(Model model) {
    games = gameService.getGamesSortedByName();
    model.addAttribute(GAMES, games);
    return GAMES_TABLE;
  }

  @GetMapping("/genre/{genre}")
  public String getGamesByGenre(Model model, @PathVariable String genre) {
    games = gameService.getGamesByGenre(genre);
    model.addAttribute(GAMES, games);
    return GAMES_TABLE;
  }

  @GetMapping("/year/{yearOfRelease}")
  public String getGamesByYearOfRelease(Model model, @PathVariable Integer yearOfRelease) {
    games = gameService.getGamesByYearOfRelease(yearOfRelease);
    model.addAttribute(GAMES, games);
    return GAMES_TABLE;
  }

  @GetMapping("/update")
  public String getUpdateInfo(Model model, Game game) {
    model.addAttribute(GAME, game);
    return UPDATE_GAME;
  }

  @PutMapping("/update")
  public String updateGame(Model model, @ModelAttribute Game game) {
    try {
      String result = gameService.updateGame(game);
      model.addAttribute(SUCCESS, result);
    } catch (NoSuchMediaExistsException e) {
      model.addAttribute(ERROR, e.getMessage());
    }
    return UPDATE_GAME;
  }

  @GetMapping("/delete")
  public String getDeleteInfo() {
    return DELETE_GAME;
  }

  @DeleteMapping("/delete")
  public String deleteGame(Model model, @RequestParam Long gameId) {
    try {
      String result = gameService.deleteGame(gameId);
      model.addAttribute(SUCCESS, result);
    } catch (NoSuchMediaExistsException e) {
      model.addAttribute(ERROR, e.getMessage());
    }
    return DELETE_GAME;
  }
}
