package service;

import client.Client;
import components.AreaForm;
import components.TimePicker;
import model.Area;
import model.BookingInfo;
import model.Movie;
import model.SeatSelected;
import repository.TicketBookingRepo;
import view.TicketBookingViewClient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketBookingService implements ActionListener, MouseListener {

  private static final TicketBookingRepo repo = new TicketBookingRepo();

  @Override
  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();
    if (source instanceof JButton) {
      JButton button = (JButton) source;
      String name = button.getName();
      switch (name) {
        case "refresh-list": {
          // Làm mới danh sách phim đã hiển thị ở table.
          break;
        }
        case "book": {
          TicketBookingViewClient.showBookingForm();
          break;
        }
        case "connect": {
          //Lấy thông tin host - port.
          String host = TicketBookingViewClient.addressField.getText();
          String portString = TicketBookingViewClient.portField.getText();
          int port = Integer.parseInt(portString);
          TicketBookingViewClient.startClient(host, port);
          break;
        }
        case "disconnect": {
          TicketBookingViewClient.closeClient();
          break;
        }
        case "cancel-booking": {
          TicketBookingViewClient.closeBookingForm();
          break;
        }
        case "confirm-booking": {
          String clientName = TicketBookingViewClient.getCustomerNameField().getText();
          String clientPhoneNumber = TicketBookingViewClient.getPhoneNumberField().getText();
          String clientEmail = TicketBookingViewClient.getEmailField().getText();
          DefaultTableModel model = TicketBookingViewClient.getSelectedSeatModel();
          List<SeatSelected> list = new ArrayList<>();
          for (int i = 0; i < model.getRowCount(); i++) {
            Integer idMovie = (Integer) model.getValueAt(i, 0);
            String areaName = (String) model.getValueAt(i, 1);
            String position = (String) model.getValueAt(i, 2);
            SeatSelected seat = new SeatSelected(idMovie, areaName, position);
            list.add(seat);
          }
          BookingInfo infor = new BookingInfo(clientName, clientPhoneNumber, clientEmail, list);
          TicketBookingViewClient.sendDataToServer(infor);
          break;
        }
        case "submit": {
          System.out.println("Submit button clicked!");
          break;
        }
      }
    } else if (source instanceof JComboBox<?>) {
      JComboBox<String> comboBox = (JComboBox<String>) source;
      String name = comboBox.getName();
      // Tạo ra Table mới rồi gửi về cho view để view render ra màn hình.
      switch (name) {
        case "show-time": {
          int data = Integer.parseInt(comboBox.getSelectedItem().toString());
          List<TimePicker> showTimes = new ArrayList<>();
          for (int i = 0; i < data; i++) {
            showTimes.add(new TimePicker());
          }

          break;
        }
        case "area": {
          int data = Integer.parseInt(comboBox.getSelectedItem().toString());
          List<AreaForm> areas = new ArrayList<>();
          for (int i = 0; i < data; i++) {
            areas.add(new AreaForm(String.valueOf(i + 1)));
          }
          TicketBookingViewClient.addListArea(areas);
          break;
        }
        case "voucher": {
          String selected = comboBox.getSelectedItem().toString();
          TicketBookingViewClient.calculateTicketPrice(selected);
          break;
        }
      }
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Object source = e.getSource();
    if (source instanceof JTable table) {
      if (table.getName().equals("list-movie")) {
        int row = table.getSelectedRow();
        int stt = Integer.parseInt(table.getValueAt(row, 0).toString());
        int movieId = Integer.parseInt(table.getValueAt(row, 1).toString());
        String title = table.getValueAt(row, 2).toString();
        String releaseDate = table.getValueAt(row, 3).toString();

        // Từ thông tin trên, tạo lại các CinemaPanel rồi lưu lại vào centerAreasPanel.
        List<Area> areas = repo.getAreaByMovieId(movieId);
        if (areas != null)
          TicketBookingViewClient.generateCinemaPanel(areas);
        else {
          TicketBookingViewClient.showError(null, "No area for this movie");
        }
      } else {
        // Dùng cho tabel ở dưới.
      }
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
