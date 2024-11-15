package com.medialibrary.controller;

import static com.medialibrary.constants.AttributeNames.SERIES;
import static com.medialibrary.constants.Messages.ERROR;
import static com.medialibrary.constants.Messages.SUCCESS;
import static com.medialibrary.constants.Templates.ADD_SERIES;
import static com.medialibrary.constants.Templates.DELETE_SERIES;
import static com.medialibrary.constants.Templates.SERIES_TABLE;
import static com.medialibrary.constants.Templates.UPDATE_SERIES;

import com.medialibrary.exception.MediaAlreadyExistsException;
import com.medialibrary.exception.NoSuchMediaExistsException;
import com.medialibrary.model.Series;
import com.medialibrary.service.SeriesService;
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
@RequestMapping("/library/series")
public class SeriesController {

  private final SeriesService seriesService;
  private List<Series> series = new ArrayList<>();

  public SeriesController(@Autowired SeriesService seriesService) {
    this.seriesService = seriesService;
  }

  @GetMapping("/save")
  public String addSeries(Model model) {
    model.addAttribute(SERIES, new Series());
    return ADD_SERIES;
  }

  @PostMapping("/save")
  public String saveSeries(Model model, @ModelAttribute Series series) {
    try {
      String result = seriesService.saveSeries(series);
      model.addAttribute(SUCCESS, result);
    } catch (MediaAlreadyExistsException e) {
      model.addAttribute(ERROR, e.getMessage());
    }
    return ADD_SERIES;
  }

  @GetMapping
  public String getSeries(Model model) {
    series = seriesService.getSeries();
    model.addAttribute(SERIES, series);
    return SERIES_TABLE;
  }

  @GetMapping("/sort")
  public String getSeriesSortedByName(Model model) {
    series = seriesService.getSeriesSortedByName();
    model.addAttribute(SERIES, series);
    return SERIES_TABLE;
  }

  @GetMapping("/genre/{genre}")
  public String getSeriesByGenre(Model model, @PathVariable String genre) {
    series = seriesService.getSeriesByGenre(genre);
    model.addAttribute(SERIES, series);
    return SERIES_TABLE;
  }

  @GetMapping("/update")
  public String getUpdateInfo(Model model, Series series) {
    model.addAttribute(SERIES, series);
    return UPDATE_SERIES;
  }

  @PutMapping("/update")
  public String updateSeries(Model model, @ModelAttribute Series series) {
    try {
      String result = seriesService.updateSeries(series);
      model.addAttribute(SUCCESS, result);
    } catch (NoSuchMediaExistsException e) {
      model.addAttribute(ERROR, e.getMessage());
    }
    return UPDATE_SERIES;
  }

  @GetMapping("/delete")
  public String getDeleteInfo() {
    return DELETE_SERIES;
  }

  @DeleteMapping("/delete")
  public String deleteSeries(Model model, @RequestParam Long seriesId) {
    try {
      String result = seriesService.deleteSeries(seriesId);
      model.addAttribute(SUCCESS, result);
    } catch (NoSuchMediaExistsException e) {
      model.addAttribute(ERROR, e.getMessage());
    }
    return DELETE_SERIES;
  }
}
