package components;

import javax.swing.*;
import java.awt.*;

public class SeatInfo extends JPanel {
  public SeatInfo(Seat seat, String info) {
    setLayout(new FlowLayout(FlowLayout.LEADING, 10, 0));
    JLabel label = new JLabel(info);
    add(seat);
    add(label);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
  }
}
