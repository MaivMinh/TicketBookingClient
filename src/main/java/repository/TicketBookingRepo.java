package repository;

import model.Area;
import model.Movie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketBookingRepo {
  private List<Movie> movies;
  private int count = 0;

  public TicketBookingRepo() {
    movies = new ArrayList<>();
  }

  public void addMovie(Movie movie) {
    count++;
    movie.setMovieId(count);
    movies.add(movie);
  }


  public List<Movie> getMoviesByDate(Date date) {
    // Tìm movie dựa vào ngày chiếu.
    return movies.stream().filter(movie -> movie.getReleaseDate().equals(date)).toList();
  }

  public List<Movie> getMovies() {
    return movies.stream().toList();
  }

  public Movie getLastMovie() {
    return movies.getLast();
  }

  public List<Area> getAreaByMovieId(int id) {
    for (Movie movie : movies) {
      if (movie.getMovieId() == id) return movie.getAreas();
    }
    return null;
  }
}
