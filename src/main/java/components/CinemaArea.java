package components;

import config.FONT;
import model.Area;

import javax.swing.*;
import java.awt.*;

public class CinemaArea extends JPanel {
  private final JPanel seatsPanel;
  private Integer idMovie;
  private final String _name;
  private double regularPrice;
  private double vipPrice;
  private Area area;

  public CinemaArea(Integer idMovie, Area area, double _regularPrice, double _vipPrice) {
    this.area = area;
    this.idMovie = idMovie;
    setLayout(new BorderLayout(0, 0));
    setPreferredSize(new Dimension(600, 500));
    this._name = area.getName();
    // TẠO MÀN HÌNH SAU ĐÓ THÊM VÀO CINEMA PANEL.
    JPanel screenPanel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(50, 10, getWidth() - 100, 30);
        g.setColor(new Color(243, 248, 255));
        g.setFont(FONT.FONT_ROBOTO_BOLD(18));
        g.drawString(_name, getWidth() / 2 - 25, 30);
      }
    };
    screenPanel.setPreferredSize(new Dimension(0, 50));
    add(screenPanel, BorderLayout.NORTH);

    this.regularPrice = _regularPrice;
    this.vipPrice = _vipPrice;
    int row = area.getRow();
    int col = area.getColumn();

    // TẠO DANH SÁCH GHẾ NGỒI SAU ĐÓ THÊM VÀO CINEMA PANEL.
    seatsPanel = new JPanel(new GridLayout(row, col, 6, 6));
    for (int i = 1; i <= row; i++) {
      for (int j = 1; j <= col; j++) {
        if ((row / 4 < i && i <= row * 3 / 4 + 1) && (col / 4 < j && j <= col * 3 / 4 + 1)) {
          seatsPanel.add(SeatFactory.generateSeat(area.getIdMovie(), this._name, Seat.TYPE.VIP, i, j, vipPrice), ((i-1)*col + (j-1)));
        } else seatsPanel.add(SeatFactory.generateSeat(area.getIdMovie(), this._name, Seat.TYPE.REGULAR, i, j, regularPrice), ((i - 1)*col + (j - 1)));
      }
    }
    add(seatsPanel, BorderLayout.CENTER);

    // TẠO CHÚ THÍCH PHẦN GHẾ NGỒI SAU ĐÓ THÊM VÀO CINEMA PANEL.
    JPanel seatInfoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 30));
    Seat booked = SeatFactory.generateSeat(area.getIdMovie(), this._name, Seat.STATUS.BOOKED, 0, 0, 0);
    Seat vip = SeatFactory.generateSeat(area.getIdMovie(), this._name, Seat.TYPE.VIP, 0, 0, 0);
    Seat regular = SeatFactory.generateSeat(area.getIdMovie(), this._name, Seat.TYPE.REGULAR, 0, 0, 0);
    Seat selected = SeatFactory.generateSeat(area.getIdMovie(), this._name, Seat.STATUS.SELECTED, 0, 0, 0);

    SeatInfo _booked = new SeatInfo(booked, "Đã đặt");
    SeatInfo _vip = new SeatInfo(vip, "Ghế VIP");
    SeatInfo _regular = new SeatInfo(regular, "Ghế thường");
    SeatInfo _selected = new SeatInfo(selected, "Ghế bạn chọn");

    seatInfoPanel.add(_booked);
    seatInfoPanel.add(_vip);
    seatInfoPanel.add(_regular);
    seatInfoPanel.add(_selected);
    add(seatInfoPanel, BorderLayout.SOUTH);
  }

  public String get_name() {
    return _name;
  }

  public Integer getIdMovie() {
    return idMovie;
  }

  public Area getArea() {
    return area;
  }

  public void repaintSeatAt(int colArea, String position) {
    char  row = position.charAt(0);
    String col = position.substring(1);
    int _row = row - 64;
    int _col = Integer.parseInt(col);
    Seat seat = (Seat)seatsPanel.getComponent((_row -1) * colArea + (_col -1));
    seat.setStatus(Seat.STATUS.BOOKED);
  }

}
