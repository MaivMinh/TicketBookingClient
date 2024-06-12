package components;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class FilterPanelClient extends JPanel {
  /* TẠO PHẦN PANEL Ở PHÍA DƯỚI LOGO CHỨA THÔNG TIN CỦA THỜI GIAN, PHIM VÀ XUẤT CHIẾU HIỆN CÓ*/
  public FilterPanelClient() {
    setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
    // Phần panel ngày đặt vé.
    JLabel dateLabel = new JLabel("Ngày đặt vé");
    JDateChooser dateChooser = new JDateChooser(new Date());
    dateChooser.setDateFormatString("dd-MM-yyyy");
    dateChooser.setSize(200, 400);
    JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    datePanel.add(dateLabel);
    datePanel.add(dateChooser);
    add(datePanel);

    // Phần panel tên phim.
    JLabel movieLabel = new JLabel("Phim");
    JComboBox<String> movieComboBox = new JComboBox<>();
    movieComboBox.addItem("Fast And Furious 10");
    movieComboBox.addItem("Mission Impossible");
    movieComboBox.addItem("Marvel: The Avengers");
    movieComboBox.addItem("Spider-Man: Homecoming");
    movieComboBox.addItem("Titanic");
    movieComboBox.addItem("The Dark Knight");
    JPanel moviePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    moviePanel.add(movieLabel);
    moviePanel.add(movieComboBox);
    add(moviePanel);

    // Phần panel thời gian(xuất chiếu mấy giờ).
    JLabel timeLabel = new JLabel("Xuất chiếu");
    JComboBox<String> timeComboBox = new JComboBox<>();
    timeComboBox.addItem("18:00");
    timeComboBox.addItem("19:00");
    timeComboBox.addItem("20:00");
    timeComboBox.addItem("21:00");
    timeComboBox.addItem("22:00");
    timeComboBox.addItem("23:00");
    JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    timePanel.add(timeLabel);
    timePanel.add(timeComboBox);
    add(timePanel);
  }
}
