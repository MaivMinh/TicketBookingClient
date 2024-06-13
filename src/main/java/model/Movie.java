package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Movie implements Serializable {
  private static final long serialVersionUID = 130720L;
  private int movieId;
  private Date releaseDate;
  private String title;
  private List<Area> areas;

  public Movie(List<Area> areas, Date releaseDate, String title) {
    this.areas = areas;
    this.releaseDate = releaseDate;
    this.title = title;
    this.movieId = 0;
  }

  public int getMovieId() {
    return movieId;
  }

  public void setMovieId(int movieId) {
    this.movieId = movieId;
  }

  public List<Area> getAreas() {
    return areas;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  @Override
  public String toString() {
    return "Movie{" +
            "movieId=" + movieId +
            ", releaseDate=" + releaseDate +
            ", title='" + title + '\'' +
            ", area-list=" + areas.toString() +
            '}';
  }
}
