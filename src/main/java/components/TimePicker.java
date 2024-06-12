package components;

import view.TicketBookingViewClient;

import javax.swing.*;
import java.awt.*;

public class TimePicker extends JPanel {

  private final JSpinner startHourSpinner;
  private final JSpinner startMinuteSpinner;
  private final JSpinner endHourSpinner;
  private final JSpinner endMinuteSpinner;

  public TimePicker() {
    setSize(400, 100);

    // Tạo JPanel chứa các thành phần chọn thời gian
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout());

    JPanel right = new JPanel();
    right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
    JPanel start = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel end = new JPanel(new FlowLayout(FlowLayout.LEFT));


    // Tạo JSpinner cho giờ
    SpinnerModel startHourModel = new SpinnerNumberModel(12, 0, 23, 1);
    startHourSpinner = new JSpinner(startHourModel);
    JSpinner.NumberEditor startHourEditor = new JSpinner.NumberEditor(startHourSpinner, "00");
    startHourSpinner.setEditor(startHourEditor);

    SpinnerModel endHourModel = new SpinnerNumberModel(12, 0, 23, 1);
    endHourSpinner = new JSpinner(endHourModel);
    JSpinner.NumberEditor endHourEditor = new JSpinner.NumberEditor(endHourSpinner, "00");
    endHourSpinner.setEditor(endHourEditor);


    // Tạo JSpinner cho phút
    SpinnerModel startMinuteModel = new SpinnerNumberModel(0, 0, 59, 1);
    startMinuteSpinner = new JSpinner(startMinuteModel);
    JSpinner.NumberEditor minuteEditor = new JSpinner.NumberEditor(startMinuteSpinner, "00");
    startMinuteSpinner.setEditor(minuteEditor);

    SpinnerModel endMinuteModel = new SpinnerNumberModel(0, 0, 59, 1);
    endMinuteSpinner = new JSpinner(endMinuteModel);
    JSpinner.NumberEditor endMinuteEditor = new JSpinner.NumberEditor(endMinuteSpinner, "00");
    endMinuteSpinner.setEditor(endMinuteEditor);

    // Thêm các thành phần vào JPanel
    start.add(new JLabel("Giờ:"));
    start.add(startHourSpinner);
    start.add(new JLabel("Phút:"));
    start.add(startMinuteSpinner);

    end.add(new JLabel("Giờ:"));
    end.add(endHourSpinner);
    end.add(new JLabel("Phút:"));
    end.add(endMinuteSpinner);

    right.add(start);
    right.add(end);
    panel.add(right);

    // Thêm JPanel vào JFrame
    add(panel);
  }

  public String getTime() {
    int startHour = (int) startHourSpinner.getValue();
    int startMinute = (int) startMinuteSpinner.getValue();

    int endHour = (int) endHourSpinner.getValue();
    int endMinute = (int) endMinuteSpinner.getValue();

    if (startHour > endHour || startHour == endHour && startMinute >= endMinute) {
      TicketBookingViewClient.showError(TicketBookingViewClient.getDialogInstance(), String.format("Thời gian %02d:%02d - %02d:%02d không hợp lệ!", startHour, startMinute, endHour, endMinute));
      return null;
    }
    return String.format("%02d:%02d - %02d:%02d", startHour, startMinute, endHour, endMinute);
  }
}
