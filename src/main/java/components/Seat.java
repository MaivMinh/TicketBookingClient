package components;

import config.COLOR;
import config.FONT;
import repository.SeatBookedRepo;
import view.TicketBookingViewClient;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.transform.Source;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Seat extends JPanel {
  enum TYPE {REGULAR, VIP}

  enum STATUS {AVAILABLE, SELECTED, BOOKED}

  private int idMovie;
  private String areaName; // TÊN RẠP.
  private TYPE type;
  private STATUS status;
  private boolean selected = false;
  private int row;
  private int col;
  private double price;

  public Seat(int idMovie, String areaName, TYPE type, int _row, int _col, double _price) {
    this.idMovie = idMovie;
    this.areaName = areaName;
    this.type = type;
    this.row = _row;
    this.col = _col;
    boolean isBooked = SeatBookedRepo.isBooked(idMovie, areaName, detectCharacter(row) + "" + col);
    if (isBooked) {
      this.status = STATUS.BOOKED;
    } else {
      this.status = (TicketBookingViewClient.checkIsSelectedSeat(idMovie, areaName, detectCharacter(row) + "" + col) ? STATUS.SELECTED : STATUS.AVAILABLE);
      if (status == STATUS.SELECTED) {
        setBorder(new RoundedBorder(new Color(255, 255, 128), 2, 20, 20));
      }
    }
    this.price = _price;
    setPreferredSize(new Dimension(20, 20));
    this.setCursor(new Cursor(Cursor.HAND_CURSOR));

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (status == STATUS.BOOKED)
          return;
        selected = !selected;
        setStatus(selected ? STATUS.SELECTED : STATUS.AVAILABLE);
        Border border = null;
        if (selected) {
          // Người dùng chọn ghế.
          border = new RoundedBorder(new Color(255, 255, 128), 2, 20, 20);
          // Gọi hàm hiển thị ghế vào bảng.
          TicketBookingViewClient.showSelectedSeat(Seat.this);
        } else {
          // Người dùng huỷ chọn ghế.
          TicketBookingViewClient.removeSelectedSeat(Seat.this);
        }
        setBorder(border);
        repaint();
      }
    });
  }

  public Seat(int idMovie, String areaName, STATUS status, int _row, int _col, double _price) {
    this.idMovie = idMovie;
    this.areaName = areaName;
    this.status = status;
    this.type = TYPE.REGULAR;
    this.row = _row;
    this.col = _col;
    this.price = _price;
    setPreferredSize(new Dimension(20, 20));
    this.setCursor(new Cursor(Cursor.HAND_CURSOR));
  }

  public String getAreaName() {
    return areaName;
  }

  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }

  public void setType(TYPE type) {
    this.type = type;
  }

  public void setStatus(STATUS status) {
    this.status = status;
    revalidate();
    repaint();
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public STATUS getStatus() {
    return status;
  }

  public TYPE getType() {
    return type;
  }

  public int getCol() {
    return col;
  }

  public void setCol(int col) {
    this.col = col;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getIdMovie() {
    return idMovie;
  }

  public void setIdMovie(int idMovie) {
    this.idMovie = idMovie;
  }

  public char detectCharacter(int value) {
    return (char) (value - 1 + 'A');
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    switch (status) {
      case AVAILABLE: {
        Color color = (type == TYPE.REGULAR) ? COLOR.REGULAR_SEAT : COLOR.VIP_SEAT;
        g2.setColor(color);
        break;
      }
      case SELECTED:
        g2.setColor(COLOR.SELECTED_SEAT);
        break;
      case BOOKED:
        g2.setColor(COLOR.BOOKED_SEAT);
        break;
    }
    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

    if (row != 0 && col != 0) {
      // Thêm đoạn mã để vẽ văn bản row, col vào giữa JPanel
      g2.setColor(Color.WHITE); // Màu của văn bản
      g2.setFont(FONT.FONT_ROBOTO_ITALIC(13));
      String text = String.valueOf(detectCharacter(row)) + col;
      FontMetrics fm = g2.getFontMetrics();
      int textWidth = fm.stringWidth(text);
      int textHeight = fm.getAscent();
      int x = (getWidth() - textWidth) / 2;
      int y = (getHeight() + textHeight) / 2 - fm.getDescent();
      g2.drawString(text, x, y);
    }
  }
}
