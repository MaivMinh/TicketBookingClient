package repository;

import components.CinemaArea;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CinemaAreaRepo {
  private static final List<CinemaArea> list = new ArrayList<>();

  public CinemaAreaRepo() {}

  public static void add(CinemaArea area) {
    list.add(area);
  }

  public static CinemaArea getCinemaAreaByIdMovieAndAreaName(Integer idMovie, String areaName) {
    for (CinemaArea area: list) {
      if (Objects.equals(area.getIdMovie(), idMovie) && area.get_name().equals(areaName)) {
        return area;
      }
    }
    return null;
  }
}
