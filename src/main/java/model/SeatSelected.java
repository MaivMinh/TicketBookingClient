package model;

import java.io.Serializable;

public class SeatSelected implements Serializable {
  private static final long serialVersionUID = 130720L;
  private Integer idMovie;
  private String areaName;
  private String position;

  public SeatSelected(Integer idMovie, String areaName, String position) {
    this.idMovie = idMovie;
    this.areaName = areaName;
    this.position = position;
  }

  public String getAreaName() {
    return areaName;
  }

  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }

  public Integer getIdMovie() {
    return idMovie;
  }

  public void setIdMovie(Integer idMovie) {
    this.idMovie = idMovie;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  @Override
  public String toString() {
    return "SeatSelected{" +
            "areaName='" + areaName + '\'' +
            ", idMovie='" + idMovie + '\'' +
            ", position='" + position + '\'' +
            '}';
  }
}
