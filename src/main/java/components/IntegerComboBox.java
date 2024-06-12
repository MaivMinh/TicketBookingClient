package components;

import javax.swing.*;

public class IntegerComboBox extends JComboBox<Integer> {
  public IntegerComboBox(int size) {
    super();
    Integer[] numbers = new Integer[size];
    for (int i = 0; i < size; i++) {
      super.addItem(i + 1);
    }
  }
}
