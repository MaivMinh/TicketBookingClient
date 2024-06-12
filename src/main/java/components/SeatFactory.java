package components;

public class SeatFactory {


  public static Seat generateSeat(Seat.TYPE type, int row, int col, double price) {
    return new Seat(type, row, col, price);
  }

  public static Seat generateSeat(Seat.STATUS status, int row, int col, double price) {
    return new Seat(status, row, col, price);
  }
}
