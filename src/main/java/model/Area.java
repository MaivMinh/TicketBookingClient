package model;

import config.Pair;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArraySet;

//Rạp chiếu
public class Area implements Serializable {
  private static final long serialVersionUID = 130720L;
  private String showTime;
  private String name;
  private int row;
  private int column;
  private int regularPrice;
  private int vipPrice;
  private int idMovie;
  private CopyOnWriteArraySet<Pair> booked;  // Lưu các vị trí ngồi đã được đặt của rạp tương ứng.

  public Area() {
  }

  public Area(String name, int regularPrice, int vipPrice, int row, int column, String showTime) {
    this.column = column;
    this.name = name;
    this.regularPrice = regularPrice;
    this.row = row;
    this.vipPrice = vipPrice;
    this.showTime = showTime;
    this.booked = new CopyOnWriteArraySet<Pair>();
  }

  public CopyOnWriteArraySet<Pair> getBooked() {
    return booked;
  }

  public String getShowTime() {
    return showTime;
  }

  public void setShowTime(String showTime) {
    this.showTime = showTime;
  }

  public void setBooked(CopyOnWriteArraySet<Pair> booked) {
    this.booked = booked;
  }

  public int getRegularPrice() {
    return regularPrice;
  }

  public void setRegularPrice(int regularPrice) {
    this.regularPrice = regularPrice;
  }

  public int getVipPrice() {
    return vipPrice;
  }

  public void setVipPrice(int vipPrice) {
    this.vipPrice = vipPrice;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getColumn() {
    return column;
  }

  public void setColumn(int column) {
    this.column = column;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public void bookingSeat(int row, int col) {
    booked.add(new Pair(row, col));
  }

  public void removingSeat(int row, int col) {
    booked.remove(new Pair(row, col));
  }

  public int getIdMovie() {
    return idMovie;
  }

  public void setIdMovie(int idMovie) {
    this.idMovie = idMovie;
  }

  @Override
  public String toString() {
    return "Area{" +
            "show-time=" + showTime +
            ", name='" + name + '\'' +
            ", row=" + row +
            ", column=" + column +
            ", regularPrice=" + regularPrice +
            ", vipPrice=" + vipPrice +
            '}';
  }
}
