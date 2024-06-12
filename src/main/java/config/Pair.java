package config;

import java.util.Objects;

public class Pair {
  public int row, column;
  public Pair(int row, int column) {
    this.row = row;
    this.column = column;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Pair pair)) return false;
    return getRow() == pair.getRow() && getColumn() == pair.getColumn();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getRow(), getColumn());
  }
}
