package model;

import java.io.Serializable;

public class SeatBooked implements Serializable {
  private static final long serialVersionUID = 130720L;
  private String emailUser;
  private String clientName;
  private String clientPhoneNumber ;
  private Integer idMovie;
  private String areaName;
  private String position;

  public SeatBooked(Integer idMovie, String areaName, String position) {
    this.idMovie = idMovie;
    this.areaName = areaName;
    this.position = position;
  }

  public SeatBooked(String areaName, String clientName, String clientPhoneNumber, String emailUser, Integer idMovie, String position) {
    this.areaName = areaName;
    this.clientName = clientName;
    this.clientPhoneNumber = clientPhoneNumber;
    this.emailUser = emailUser;
    this.idMovie = idMovie;
    this.position = position;
  }

  public String getAreaName() {
    return areaName;
  }


  public Integer getIdMovie() {
    return idMovie;
  }


  public String getPosition() {
    return position;
  }

  @Override
  public String toString() {
    return "SeatBooked{" +
            "areaName='" + areaName + '\'' +
            ", idMovie='" + idMovie + '\'' +
            ", position='" + position + '\'' +
            '}';
  }
}
