package repository;

import model.SeatSelected;

import java.util.ArrayList;
import java.util.List;

public class SeatSelectedRepo {
  private static final List<List<SeatSelected>> list = new ArrayList<>();
  public static void add(List<SeatSelected> seatSelected) {
    list.add(seatSelected);
  }


  public static void removeLast() {
    list.removeLast();
  }

  public static List<SeatSelected> getLast() {
    return list.getLast();
  }


}
