package components;

public class SeatFactory {


  public static Seat generateSeat(int idMovie, String areaName, Seat.TYPE type, int row, int col, double price) {
    return new Seat(idMovie, areaName, type, row, col, price);
  }

  public static Seat generateSeat(int idMovie, String areaName, Seat.STATUS status, int row, int col, double price) {
    return new Seat(idMovie, areaName, status, row, col, price);
  }
}
