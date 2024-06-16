package repository;

import model.SeatBooked;
import model.SeatSelected;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SeatBookedRepo {
  private static final List<SeatBooked> list = new ArrayList<>();

  public SeatBookedRepo() {
  }

  public static void addBookedSeat(SeatBooked seat) {
    list.add(seat);
  }

  public static boolean isBooked(Integer idMovie, String areaName, String position) {
    for (SeatBooked seat : list) {
      if (Objects.equals(seat.getIdMovie(), idMovie) && seat.getAreaName().equals(areaName) && seat.getPosition().equals(position)) {
        return true;
      }
    }
    return false;
  }

  public static List<SeatBooked> getAll() {
    return list;
  }

}
