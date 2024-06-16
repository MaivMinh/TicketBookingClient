package repository;

import model.Area;
import model.Movie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketBookingRepo {
  private static List<Movie> movies;

  public TicketBookingRepo() {
    movies = new ArrayList<>();
  }

  public static void addMovie(Movie movie) {
    movies.add(movie);
  }


  public static List<Movie> getMovies() {
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
