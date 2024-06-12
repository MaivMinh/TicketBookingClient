package components;

import com.github.lgooddatepicker.components.TimePicker;

import javax.swing.*;
import java.awt.*;

public class _ShowTime extends JPanel {
  public _ShowTime(int position) {
    setPreferredSize(new Dimension(400, 50));
    setLayout(new FlowLayout(FlowLayout.CENTER, 0,0));

    JPanel indexPanel = new JPanel();
    indexPanel.setPreferredSize(new Dimension(50, 50));
    JLabel indexLabel = new JLabel(String.valueOf(position));
    indexPanel.add(indexLabel);

    JPanel showTimePanel = new JPanel();
    showTimePanel.setLayout(new BoxLayout(showTimePanel, BoxLayout.Y_AXIS));
    showTimePanel.setPreferredSize(new Dimension(350, 100));

    JPanel startPanel = new JPanel(); startPanel.setPreferredSize(new Dimension(350, 25));
    JPanel endPanel = new JPanel(); endPanel.setPreferredSize(new Dimension(350, 25));
    JLabel startLabel = new JLabel("Start: ");
    JLabel endLabel = new JLabel("End: ");
    TimePicker startPicker = new TimePicker();
    startPicker.setPreferredSize(new Dimension(100, 25));
    TimePicker endPicker = new TimePicker();
    endPicker.setPreferredSize(new Dimension(100, 25));
    startPanel.add(startLabel);
    startPanel.add(startPicker);
    endPanel.add(endLabel);
    endPanel.add(endPicker);
    showTimePanel.add(startPanel);
    showTimePanel.add(endPanel);

    add(indexPanel);
    add(showTimePanel);
  }
}
