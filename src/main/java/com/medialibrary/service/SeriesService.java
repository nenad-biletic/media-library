package com.medialibrary.service;

import static com.medialibrary.constants.FieldNames.ID;
import static com.medialibrary.constants.FieldNames.TITLE;

import com.medialibrary.exception.MediaAlreadyExistsException;
import com.medialibrary.exception.NoSuchMediaExistsException;
import com.medialibrary.model.Series;
import com.medialibrary.repository.SeriesRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class SeriesService {

  private final SeriesRepository seriesRepository;

  public SeriesService(@Autowired SeriesRepository seriesRepository) {
    this.seriesRepository = seriesRepository;
  }

  public String saveSeries(Series series) {
    Series existingSeries = seriesRepository.findByTitle(series.getTitle());
    if (existingSeries == null) {
      seriesRepository.save(series);
      return "Series saved successfully.";
    } else {
      throw new MediaAlreadyExistsException("Series already exists!");
    }
  }

  public List<Series> getSeries() {
    return seriesRepository.findAll(Sort.by(Direction.ASC, ID));
  }

  public List<Series> getSeriesSortedByName() {
    return seriesRepository.findAll(Sort.by(Direction.ASC, TITLE));
  }

  public List<Series> getSeriesByGenre(String genre) {
    return seriesRepository.findByGenre(genre, Sort.by(Direction.ASC, TITLE));
  }

  public String updateSeries(Series series) {
    Series existingSeries = seriesRepository.findById(series.getId()).orElse(null);
    if (existingSeries == null) {
      throw new NoSuchMediaExistsException("Series with ID " + series.getId() + " does not exist!");
    } else {
      existingSeries.setTitle(series.getTitle());
      existingSeries.setGenre(series.getGenre());
      existingSeries.setNumberOfSeasons(series.getNumberOfSeasons());
      seriesRepository.save(existingSeries);
      return "Series updated successfully.";
    }
  }

  public String deleteSeries(Long id) {
    Series existingSeries = seriesRepository.findById(id).orElse(null);
    if (existingSeries == null) {
      throw new NoSuchMediaExistsException("Series with ID " + id + " does not exist!");
    } else {
      seriesRepository.deleteById(id);
      return "Series deleted successfully.";
    }
  }
}
