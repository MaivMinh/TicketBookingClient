package model;

import repository.SeatBookedRepo;
import repository.TicketBookingRepo;

import java.io.Serializable;
import java.util.List;

public class MoviesAndBookedSeat implements Serializable {
  private static final long serialVersionUID = 130720L;
  private final List<Movie> movies = TicketBookingRepo.getMovies();
  private final List<SeatBooked> seats = SeatBookedRepo.getAll();

  public List<Movie> getMovies() {
    return movies;
  }

  public List<SeatBooked> getSeats() {
    return seats;
  }
}
