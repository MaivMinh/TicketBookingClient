package service;

import components.AreaForm;
import components.TimePicker;
import model.Area;
import model.Movie;
import repository.TicketBookingRepo;
import view.TicketBookingViewClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketBookingService implements ActionListener, MouseListener {

  private final TicketBookingRepo repo = new TicketBookingRepo();

  @Override
  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();
    if (source instanceof JButton) {
      JButton button = (JButton) source;
      String name = button.getName();
      switch (name) {
        case "add-movie": {
          TicketBookingViewClient.showAddMovieDialog();
          break;
        }
        case "save-movie": {
          // Lấy thông tin từ AreaForm rồi lưu xuống Repo.
          // Thông tin ngày chiếu.
          Date releaseDate = TicketBookingViewClient.getDateChooser().getDate();
          // Thông tin tên phim.
          String title = TicketBookingViewClient.getMovieName();
          List<Area> areas = new ArrayList<>();

          // Giá vé vị trí thường.

          // Giá vé vị trí vip.

          // Thông tin danh sách rạp.
          List<Component> list = TicketBookingViewClient.getListArea();
          for (Component area : list) {
            if (area instanceof AreaForm areaForm) {
              if (areaForm.getArea().getShowTime() == null) {
                return;
              }
              areas.add(areaForm.getArea());
            }
          }

          Movie movie = new Movie(areas, releaseDate, title);
          repo.addMovie(movie);
          TicketBookingViewClient.getDialogInstance().dispose();

          // Sau khi thêm phim thành công, thoát dialog và hiển thị phim vào table chứa danh sách phim.
          TicketBookingViewClient.showMovieTable(repo.getLastMovie());
          break;
        }
        case "start-server": {
          JTextField portString = TicketBookingViewClient.getPortField();
          int portNumber = Integer.parseInt(portString.getText());
          TicketBookingViewClient.startServer(portNumber);
          break;
        }
        case "close-server": {
          TicketBookingViewClient.closeServer();
          break;
        }
        case "disconnect": {
          System.out.println("Disconnect button clicked!");
          break;
        }
        case "detail": {
          System.out.println("Detail button clicked!");
          break;
        }
      }
    } else if (source instanceof JComboBox<?>) {
      JComboBox<String> comboBox = (JComboBox<String>) source;
      String name = comboBox.getName();
      int data = Integer.parseInt(comboBox.getSelectedItem().toString());
      // Tạo ra Table mới rồi gửi về cho view để view render ra màn hình.
      switch (name) {
        case "show-time": {
          List<TimePicker> showTimes = new ArrayList<>();
          for (int i = 0; i < data; i++) {
            showTimes.add(new TimePicker());
          }

          break;
        }
        case "area": {
          List<AreaForm> areas = new ArrayList<>();
          for (int i = 0; i < data; i++) {
            areas.add(new AreaForm(String.valueOf(i + 1)));
          }
          TicketBookingViewClient.addListArea(areas);
          break;
        }
      }
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Object source = e.getSource();
    if (source instanceof JTable table) {
      int row = table.getSelectedRow();
      int stt = Integer.parseInt(table.getValueAt(row, 0).toString());
      int movieId = Integer.parseInt(table.getValueAt(row, 1).toString());
      String title = table.getValueAt(row, 2).toString();
      String releaseDate = table.getValueAt(row, 3).toString();

      // Từ thông tin trên, tạo lại các CinemaPanel rồi lưu lại vào centerAreasPanel.
      TicketBookingViewClient.generateCinemaPanel(repo.getAreaByMovieId(movieId));

    }
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}
