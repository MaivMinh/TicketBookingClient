package components;

import config.FONT;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BookingList {

  /* JPanel bookingPanel = new JPanel(new BorderLayout(0, 0));
    bookingPanel.setPreferredSize(new Dimension(600, 500));

  // Phần phía trên sẽ chứa label "Danh sách vé đã chọn"
  JPanel titlePanel = new JPanel(new BorderLayout(0, 0));
    titlePanel.setPreferredSize(new Dimension(0, 50));
  JLabel titleLabel = new JLabel("Danh sách vé đã chọn");
    titleLabel.setFont((FONT.FONT_ROBOTO_BOLD(22)));
    titlePanel.add(titleLabel, BorderLayout.CENTER);

  JPanel bookingTablePanel = new JPanel();
    bookingTablePanel.setLayout(new BoxLayout(bookingTablePanel, BoxLayout.Y_AXIS));
    bookingTablePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
    bookingTablePanel.setBackground(Color.CYAN);

  JPanel tableTicket = new JPanel();

  DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Tên phim");
    model.addColumn("Loại ghế");
    model.addColumn("Xuất chiếu");
    model.addColumn("Vị trí");
    model.addColumn("Giá ghế");
    model.addRow(new String[]{"Mission Impossible", "VIP", "19:00", "H09", "80,000"});
    model.addRow(new String[]{"Spider-Man: Homecoming", "Thường", "19:00", "H07", "60,000"});
    model.addRow(new String[]{"Fast And Furious 10", "VIP", "21:00", "H06", "70,0000"});
  table = new JTable(model);
    tableTicket.setPreferredSize(new Dimension(600, 300));
    table.setPreferredScrollableViewportSize(new Dimension(600, 300));
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setFillsViewportHeight(true);
    table.setBounds(10, 10, 500, 300);
  JScrollPane scrollPaneTable = new JScrollPane(table);
    scrollPaneTable.setViewportView(table);

    tableTicket.add(scrollPaneTable);

  JPanel statisticPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
    statisticPanel.setPreferredSize(new Dimension(600, 150));
  JPanel bonus = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    bonus.setPreferredSize(new Dimension(250, 150));
    bonus.setBackground(Color.YELLOW);

  JPanel sum = new JPanel();
    sum.setLayout(new BoxLayout(sum, BoxLayout.Y_AXIS));
    sum.setPreferredSize(new Dimension(250, 150));
  JLabel ticketFee = new JLabel("Tổng giá vé: 350,000 đ");
  JLabel bonusFee = new JLabel("Tổng combo: 150,000 đ");
  JLabel total = new JLabel("Tổng tiền: 500,000 đ");
    sum.add(ticketFee);
    sum.add(bonusFee);
    sum.add(total);
    statisticPanel.add(bonus);
    statisticPanel.add(sum);

  JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
  JButton button = new JButton("Thanh toán");
    buttonPanel.add(button);


    bookingTablePanel.add(tableTicket);
    bookingTablePanel.add(statisticPanel);
    bookingTablePanel.add(buttonPanel);

    bookingPanel.add(titlePanel, BorderLayout.NORTH);
    bookingPanel.add(bookingTablePanel, BorderLayout.CENTER);
    bottomPanel.add(bookingPanel); */

}
