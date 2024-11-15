package com.medialibrary.service;

import static com.medialibrary.constants.FieldNames.ID;
import static com.medialibrary.constants.FieldNames.TITLE;

import com.medialibrary.authentication.AuthenticationService;
import com.medialibrary.exception.MediaAlreadyExistsException;
import com.medialibrary.exception.NoSuchMediaExistsException;
import com.medialibrary.model.Series;
import com.medialibrary.model.User;
import com.medialibrary.repository.SeriesRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SeriesService {

  private final SeriesRepository seriesRepository;
  private final AuthenticationService authenticationService;

  @Autowired
  public SeriesService(SeriesRepository seriesRepository,
      AuthenticationService authenticationService) {
    this.seriesRepository = seriesRepository;
    this.authenticationService = authenticationService;
  }

  public String saveSeries(Series series) {
    User user = authenticationService.getCurrentUser();
    Optional<Series> seriesOpt = seriesRepository.findByTitle(series.getTitle(), user.getId());

    if (seriesOpt.isPresent()) {
      throw new MediaAlreadyExistsException(
          "Series with title '" + series.getTitle() + "' already exists!");
    } else {
      series.setUser(user);
      seriesRepository.save(series);
      return "Series saved successfully.";
    }
  }

  public List<Series> getSeries() {
    User user = authenticationService.getCurrentUser();
    return seriesRepository.findByUserId(user.getId(), Sort.by(Direction.ASC, ID));
  }

  public List<Series> getSeriesSortedByName() {
    User user = authenticationService.getCurrentUser();
    return seriesRepository.findByUserId(user.getId(), Sort.by(Direction.ASC, TITLE));
  }

  public List<Series> getSeriesByGenre(String genre) {
    User user = authenticationService.getCurrentUser();
    return seriesRepository.findByGenre(genre, user.getId(), Sort.by(Direction.ASC, TITLE));
  }

  public String updateSeries(Series series) {
    User user = authenticationService.getCurrentUser();
    Optional<Series> seriesOpt = seriesRepository.findByTitle(series.getTitle(), user.getId());

    if (seriesOpt.isEmpty()) {
      throw new NoSuchMediaExistsException("Series with ID " + series.getId() + " does not exist!");
    } else {
      Series existingSeries = seriesOpt.get();
      existingSeries.setTitle(series.getTitle());
      existingSeries.setGenre(series.getGenre());
      existingSeries.setNumberOfSeasons(series.getNumberOfSeasons());
      seriesRepository.save(existingSeries);
      return "Series updated successfully.";
    }
  }

  public String deleteSeries(Long seriesId) {
    User user = authenticationService.getCurrentUser();
    Optional<Series> seriesOpt = seriesRepository.findByUserIdAndSeriesId(user.getId(), seriesId);

    if (seriesOpt.isEmpty()) {
      throw new NoSuchMediaExistsException("Series with ID " + seriesId + " does not exist!");
    } else {
      seriesRepository.deleteById(seriesId);
      return "Series deleted successfully.";
    }
  }
}
