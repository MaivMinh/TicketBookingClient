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

  public Area() {
  }

  public Area(String name, int regularPrice, int vipPrice, int row, int column, String showTime) {
    this.column = column;
    this.name = name;
    this.regularPrice = regularPrice;
    this.row = row;
    this.vipPrice = vipPrice;
    this.showTime = showTime;
  }


  public int getRegularPrice() {
    return regularPrice;
  }


  public int getVipPrice() {
    return vipPrice;
  }


  public String getName() {
    return name;
  }


  public int getColumn() {
    return column;
  }


  public int getRow() {
    return row;
  }

  public void setIdMovie(int idMovie) {
    this.idMovie = idMovie;
  }

  public int getIdMovie() {
    return idMovie;
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
