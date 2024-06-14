package view;

import client.Client;
import components.AreaForm;
import components.CinemaPanel;
import components.Seat;
import config.FONT;
import model.Area;
import model.Movie;
import service.TicketBookingService;
import utils.ConverDateToString;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class TicketBookingViewClient extends JFrame {

  private static final JTabbedPane tabbedPane = new JTabbedPane();
  private static final JPanel dashboardPane = new JPanel(); // Là khu vực dành để thêm thông tin phim sắp chiếu.
  private static final JPanel serverInformation = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));  // Là khu vực chứa thông tin cấu hình server - danh sách client đã kết nối tới server.
  private static final JPanel centerAreasPanel = new JPanel();
  private static final TicketBookingService service = new TicketBookingService();
  private static final JTextField nameField = new JTextField(30);
  ;
  private static JTable listMovieTable;
  private static final JTextField customerNameField = new JTextField(20);
  private static final JTextField phoneNumberField = new JTextField(20);
  private static final JTextField emailField = new JTextField(20);
  private static final JComboBox<Integer> areasComboBox = new JComboBox<>();
  private static JDialog addMovieDialog;
  private static DefaultTableModel model;
  private static JPanel cinemaPanel;
  public static final JTextField portField = new JTextField(20);
  public static final JTextField addressField = new JTextField(20);
  private static final JTextField content = new JTextField(20);
  private static Client client;
  private static DefaultTableModel selectedSeatModel;
  private static JTable selectedSeatTable;
  private static JLabel totalFee = new JLabel("0");
  private static JLabel remainFee = new JLabel("0");
  private static double total = 0;
  private static double remain = 0;
  private static final JComboBox voucherComboBox = new JComboBox<>(new String[]{"5%", "50K"});

  public TicketBookingViewClient() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    this.setTitle("Ticket Booking Client");

    // Cấu hình giao diện cho Tabbed Pane.
    setBounds(100, 100, 1500, 1125);
    tabbedPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    tabbedPane.setPreferredSize(new Dimension(1500, 1125));
    setContentPane(tabbedPane);

    // Cấu hình cho Dashboard.
    dashboardPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    dashboardPane.setBounds(100, 100, 1500, 1125);
    dashboardPane.setPreferredSize(new Dimension(1500, 1125));


    // Cấu hình cho serverInformation.
    serverInformation.setBorder(new EmptyBorder(5, 5, 5, 5));
    serverInformation.setBounds(100, 100, 1500, 1125);
    serverInformation.setPreferredSize(new Dimension(1500, 1125));

    // Thêm dashboardPane - serverInformation vào tabbedPane.
    tabbedPane.addTab("Cửa hàng đặt vé", dashboardPane);
    tabbedPane.addTab("Quản lý kết nối", serverInformation);


    // TOÀN BỘ GIAO DIỆN dashboardPane SẼ ĐƯỢC BỐ CỤC TỪ THẲNG ĐỨNG THÔNG QUA BOXLAYOUT.
    dashboardPane.setLayout(new BoxLayout(dashboardPane, BoxLayout.Y_AXIS));

    // Tạo Panel ở phía bên trên cho giao diện. Panel này sẽ chỉ chứa logo.
    JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    ImageIcon logo = new ImageIcon("src/main/resources/cgv-logo.png");
    logo.setImage(new ImageIcon(logo.getImage().getScaledInstance(500, 200, Image.SCALE_SMOOTH)).getImage());
    JLabel logoLabel = new JLabel(logo);
    topPanel.add(logoLabel);
    dashboardPane.add(topPanel);


    /* TẠO PHẦN PANEL PHÍA DƯỚI(PANEL LỚN NHẤT) BAO GỒM BÊN TRÁI LÀ DANH SÁCH CÁC PHIM CỦA HỆ THỐNG VÀ THÔNG TIN ĐẶT GHẾ - BÊN PHẢI CHỨA PHẦN RẠP PHIM CỦA PHIM ĐƯỢC CHỌN. */
    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
    bottomPanel.setSize(new Dimension(1500, 600));
    dashboardPane.add(bottomPanel);

    // Phần phía bên trái sẽ bao gồm button để REFRESH danh sách phim, bảng chứa danh sách phim đã thêm và thông tin ghế đã chọn bao gồm cả tổng chi phí lẫn voucher.
    JPanel listMovieAddedAndBookingSeat = new JPanel();
    listMovieAddedAndBookingSeat.setLayout(new BoxLayout(listMovieAddedAndBookingSeat, BoxLayout.Y_AXIS));
    listMovieAddedAndBookingSeat.setPreferredSize(new Dimension(600, 500));
    bottomPanel.add(listMovieAddedAndBookingSeat);

    // Phần bên trên của listMovieAddedAndBookingSeat sẽ chứa button và table.
    JPanel listMovieAdded = new JPanel();
    listMovieAdded.setLayout(new BoxLayout(listMovieAdded, BoxLayout.Y_AXIS));
    listMovieAdded.setPreferredSize(new Dimension(600, 300));


    // Tạo JPanel chứa button "Làm mới danh sách".
    JPanel _buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 0));
    _buttonPanel.setPreferredSize(new Dimension(600, 30));
    JButton addMovieButton = new JButton("Làm mới danh sách");
    addMovieButton.addActionListener(service);
    addMovieButton.setName("refresh-list");
    addMovieButton.addActionListener(service);
    addMovieButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    _buttonPanel.add(addMovieButton);
    listMovieAdded.add(_buttonPanel);

    // Tạo dữ liệu cho table lưu danh sách các phim được tạo bởi hệ thống.
    model = new DefaultTableModel();
    model.addColumn("Số thứ tự");
    model.addColumn("Mã phim");
    model.addColumn("Tên phim");
    model.addColumn("Ngày chiếu");
    listMovieTable = new JTable(model);
    listMovieTable.setName("list-movie");
    JScrollPane tableScrollPane = new JScrollPane(listMovieTable);
    tableScrollPane.setSize(new Dimension(600, 250));
    tableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    listMovieTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listMovieTable.setPreferredScrollableViewportSize(new Dimension(600, 250));
    listMovieTable.setName("list-movie");
    listMovieTable.addMouseListener(service);
    listMovieAdded.add(tableScrollPane);

    // Tiếp theo sẽ tạo JPanel phía dưới chứa thông tin ghế đã lựa chọn, chi phí.
    // Panel sẽ chia thành 2 bên, trái chứa danh sách và thông tin vé, phải chứa Tổng tiền, thành Voucher và tổng tiền.
    JPanel bookingSeat = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
    bookingSeat.setPreferredSize(new Dimension(600, 200));

    JPanel leftBookingSeat = new JPanel();
    leftBookingSeat.setLayout(new BoxLayout(leftBookingSeat, BoxLayout.Y_AXIS));
    leftBookingSeat.setPreferredSize(new Dimension(300, 200));
    leftBookingSeat.add(new JLabel("Danh sách đã chọn"));

    selectedSeatModel = new DefaultTableModel();
    selectedSeatModel.addColumn("Mã Phim");
    selectedSeatModel.addColumn("Tên Rạp");
    selectedSeatModel.addColumn("Vị Trí");
    selectedSeatModel.addColumn("Hạng Ghế");
    selectedSeatModel.addColumn("Giá Ghế");
    selectedSeatTable = new JTable(selectedSeatModel);
    selectedSeatTable.setName("selected-seat");
    selectedSeatTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    selectedSeatTable.setPreferredScrollableViewportSize(new Dimension(300, 150));
    selectedSeatTable.addMouseListener(service);
    selectedSeatTable.setName("selected-seat");
    JScrollPane selectedTableScrollPane = new JScrollPane(selectedSeatTable);
    selectedTableScrollPane.setPreferredSize(new Dimension(300, 150));
    selectedTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    leftBookingSeat.add(selectedTableScrollPane);
    bookingSeat.add(leftBookingSeat);


    JPanel rightBookingSeat = new JPanel(new GridBagLayout());
    rightBookingSeat.setPreferredSize(new Dimension(270, 200));
    GridBagConstraints _gbc = new GridBagConstraints();
    _gbc.fill = GridBagConstraints.HORIZONTAL;
    _gbc.insets = new Insets(5, 5, 5, 5);

    _gbc.gridx = 0;
    _gbc.gridy = 0;
    _gbc.gridwidth = 1;
    rightBookingSeat.add(new JLabel("Tổng tiền: "), _gbc);
    _gbc.gridx = 1;
    _gbc.gridy = 0;
    _gbc.gridwidth = 3;
    rightBookingSeat.add(totalFee, _gbc);

    _gbc.gridx = 0;
    _gbc.gridy = 1;
    _gbc.gridwidth = 1;
    rightBookingSeat.add(new JLabel("Voucher: "), _gbc);
    _gbc.gridx = 1;
    _gbc.gridy = 1;
    _gbc.gridwidth = 3;
    rightBookingSeat.add(voucherComboBox, _gbc);
    voucherComboBox.addActionListener(service);
    voucherComboBox.setName("voucher");

    _gbc.gridx = 0;
    _gbc.gridy = 2;
    _gbc.gridwidth = 1;
    rightBookingSeat.add(new JLabel("Thành tiền: "), _gbc);
    _gbc.gridx = 1;
    _gbc.gridy = 2;
    _gbc.gridwidth = 3;
    rightBookingSeat.add(remainFee, _gbc);

    _gbc.gridx = 0;
    _gbc.gridy = 3;
    JButton book = new JButton("Đặt Vé");
    book.setCursor(new Cursor(Cursor.HAND_CURSOR));
    book.addActionListener(service);
    book.setName("book");
    rightBookingSeat.add(book);

    bookingSeat.add(rightBookingSeat);

    listMovieAddedAndBookingSeat.add(listMovieAdded);
    listMovieAddedAndBookingSeat.add(bookingSeat);


    // Panel phía bên phải bao gồm một CinemaPanel và bên dưới bao gồm thông tin các loại ghế ngồi và trạng thái.
    cinemaPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 50, 10));
    cinemaPanel.setSize(new Dimension(700, 500));
    JScrollPane cinemaScrollPane = new JScrollPane(cinemaPanel);
    cinemaScrollPane.setPreferredSize(new Dimension(700, 500));
    cinemaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    cinemaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    bottomPanel.add(cinemaScrollPane);


    // CẤU HÌNH CHO PHẦN QUẢN LÝ KẾT NỐI. SẼ CHIA THÀNH 2 PHẦN, BÊN TRÁI LÀ THÔNG TIN SERVER VÀ BÊN PHẢI MỤC TRÒ CHUYỆN VỚI ADMIN.

    // Tạo 2 Panel, config chứa thông tin của server như port... Và panel còn lại chứa thông tin của các client.
    JPanel config = new JPanel();
    JPanel client = new JPanel();
    config.setBackground(Color.CYAN);
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, config, client);
    splitPane.setPreferredSize(new Dimension(1000, 500));
    splitPane.setDividerLocation(1000 / 2);
    splitPane.setOneTouchExpandable(true);
    serverInformation.add(splitPane);

    // Cấu hình cho phần panel config.
    config.setLayout(new BoxLayout(config, BoxLayout.Y_AXIS));
    config.setPreferredSize(new Dimension(500, 500));
    config.add(new JLabel("Thông tin cấu hình máy chủ"));
    JPanel configPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    configPanel.add(new JLabel("IP Address: "), gbc);
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    addressField.setText("localhost");
    configPanel.add(addressField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    configPanel.add(new JLabel("Port Number: "), gbc);
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    portField.setText("8080");
    configPanel.add(portField, gbc);


    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 1;
    JButton start = new JButton("Start Connect");
    start.setName("connect");
    start.addActionListener(service);
    configPanel.add(start, gbc);
    gbc.gridx = 1;
    gbc.gridy = 2;
    gbc.gridwidth = 1;
    JButton close = new JButton("Disconnect");
    close.setName("disconnect");
    close.addActionListener(service);
    configPanel.add(close, gbc);
    config.add(configPanel);


    // Cấu hình cho phần panel client
    client.setLayout(new BoxLayout(client, BoxLayout.Y_AXIS));
    client.setPreferredSize(new Dimension(500, 500));
    client.add(new JLabel("Trò chuyện"));

    JTextPane chatPane = new JTextPane();
    chatPane.setPreferredSize(new Dimension(400, 400));
    chatPane.setEditable(false);
    JScrollPane chatScrollPane = new JScrollPane(chatPane);
    chatScrollPane.setPreferredSize(new Dimension(400, 400));
    chatScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    client.add(chatScrollPane);

    // Để thêm tin nhắn mới với định dạng
    StyledDocument doc = chatPane.getStyledDocument();

    Style clientStyle = chatPane.addStyle("Client Style", null);
    StyleConstants.setForeground(clientStyle, Color.BLUE);

    Style serverStyle = chatPane.addStyle("Server Style", null);
    StyleConstants.setForeground(serverStyle, Color.RED);

    try {
      doc.insertString(doc.getLength(), "Client: Hello, Server!\n", clientStyle);
      doc.insertString(doc.getLength(), "Server: Hi, Client!\n", serverStyle);
      doc.insertString(doc.getLength(), "Client: How are you doing?\n", clientStyle);
      doc.insertString(doc.getLength(), "Server: I'm doing well, thank you. How about you?\n", serverStyle);
      doc.insertString(doc.getLength(), "Client: I'm great, thanks for asking.\n", clientStyle);
      doc.insertString(doc.getLength(), "Server: What can I help you with today?\n", serverStyle);
      doc.insertString(doc.getLength(), "Client: I have a question about my account.\n", clientStyle);
      doc.insertString(doc.getLength(), "Server: Sure, what is your question?\n", serverStyle);
      doc.insertString(doc.getLength(), "Client: Can you help me reset my password?\n", clientStyle);
      doc.insertString(doc.getLength(), "Server: Absolutely. Please provide your email address.\n", serverStyle);
      doc.insertString(doc.getLength(), "Client: It's example@example.com\n", clientStyle);
      doc.insertString(doc.getLength(), "Server: Thank you. I have sent a password reset link to your email.\n", serverStyle);
      doc.insertString(doc.getLength(), "Client: Great! I received it. What should I do next?\n", clientStyle);
      doc.insertString(doc.getLength(), "Server: Click the link in the email to reset your password.\n", serverStyle);
      doc.insertString(doc.getLength(), "Client: Okay, I will do that now. Thank you!\n", clientStyle);
      doc.insertString(doc.getLength(), "Server: You're welcome. Is there anything else I can assist you with?\n", serverStyle);
      doc.insertString(doc.getLength(), "Client: No, that's all for now. Have a great day!\n", clientStyle);
      doc.insertString(doc.getLength(), "Server: You too! Goodbye.\n", serverStyle);
      doc.insertString(doc.getLength(), "Client: Goodbye.\n", clientStyle);
    } catch (BadLocationException e) {
      e.printStackTrace();
    }

    client.add(content);
    JButton submit = new JButton("Gửi");
    submit.setName("submit");
    submit.addActionListener(service);
    client.add(submit);
  }

  public static void calculateTicketPrice(String voucher) {
    total = Double.parseDouble(totalFee.getText());
    remain = Double.parseDouble(remainFee.getText());

    switch (voucher) {
      case "5%": {
        remain = (int) (total * 0.95);
        break;
      }
      case "50K": {
        remain = total - 50;
        break;
      }
      default: {
        showError(null, "Unknown Voucher!");
        break;
      }
    }
    remainFee.setText(String.valueOf(remain));
  }

  public static void showSelectedSeat(Seat seat) {
    // Hàm giúp hiển thị ghế vừa chọn vào bảng.
    String seatPosition = seat.detectCharacter(seat.getRow()) + "" + seat.getCol();
    for (int i = 0; i < selectedSeatModel.getRowCount(); i++) {
      Integer idMovie = (Integer) selectedSeatModel.getValueAt(i, 0);
      String areaName = (String) selectedSeatModel.getValueAt(i, 1);
      String position = (String) selectedSeatModel.getValueAt(i, 2);
      if (seat.getIdMovie() == idMovie && seat.getAreaName().equals(areaName) && seatPosition.equals(position))
        return;
    }
    selectedSeatModel.addRow(new Object[]{seat.getIdMovie(), seat.getAreaName(), seatPosition, seat.getType(), seat.getPrice()});

    // Sau khi thêm vào bảng xong thì tính toán lại tổng tiền.
    total += seat.getPrice();
    String data = (String) voucherComboBox.getSelectedItem();
    switch (data) {
      case "5%": {
        remain = (int) (total * 0.95);
        break;
      }
      case "50K": {
        remain = total - 50;
        break;
      }
    }

    // Cập nhật lại cho JLabel.
    totalFee.setText(String.valueOf(total));
    remainFee.setText(String.valueOf(remain));
  }

  public static void removeSelectedSeat(Seat seat) {
    // Hàm giúp xoá đi ghế vừa huỷ chọn ra khỏi bảng.
    String seatPosition = seat.detectCharacter(seat.getRow()) + "" + seat.getCol();
    for (int i = 0; i < selectedSeatModel.getRowCount(); i++) {
      Integer idMovie = (Integer) selectedSeatModel.getValueAt(i, 0);
      String areaName = (String) selectedSeatModel.getValueAt(i, 1);
      String position = (String) selectedSeatModel.getValueAt(i, 2);
      if (seat.getIdMovie() == idMovie && seat.getAreaName().equals(areaName) && seatPosition.equals(position)) {
        selectedSeatModel.removeRow(i);

        // Sau khi xoá xong thì giảm số tiền.
        total -= seat.getPrice();
        String data = (String) voucherComboBox.getSelectedItem();
        switch (data) {
          case "5%": {
            remain = (int) (total * 0.95);
            break;
          }
          case "50K": {
            remain = total - 50;
            break;
          }
        }
        // Cập nhật lại cho JLabel.
        totalFee.setText(String.valueOf(total));
        remainFee.setText(String.valueOf(remain));
        return;
      }
    }
  }

  public static DefaultTableModel getSelectedSeatModel() {
    return selectedSeatModel;
  }

  public static void setSelectedSeatModel(DefaultTableModel selectedSeatModel) {
    TicketBookingViewClient.selectedSeatModel = selectedSeatModel;
  }

  public static boolean checkIsSelectedSeat(int _idMovie, String _areaName, String _position) {
    for (int i = 0; i < selectedSeatModel.getRowCount(); i++) {
      Integer idMovie = (Integer) selectedSeatModel.getValueAt(i, 0);
      String areaName = (String) selectedSeatModel.getValueAt(i, 1);
      String position = (String) selectedSeatModel.getValueAt(i, 2);
      if (idMovie == _idMovie && areaName.equals(_areaName) && position.equals(_position))
        return true;
    }
    return false;
  }

  public static void generateCinemaPanel(List<Area> areas) {
    cinemaPanel.removeAll();
    for (Area area : areas) {
      cinemaPanel.add(new CinemaPanel(area, area.getRegularPrice(), area.getVipPrice()));
    }
    cinemaPanel.revalidate();
    cinemaPanel.repaint();
  }

  public static void showBookingForm() {
    // Tạo JDialog
    addMovieDialog = new JDialog();
    addMovieDialog.setSize(500, 300);
    addMovieDialog.setLocationRelativeTo(null);
    addMovieDialog.setVisible(true);


    JPanel title = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel titleLabel = new JLabel("Thông tin đặt vé");
    titleLabel.setForeground(new Color(243, 248, 255));
    titleLabel.setFont(FONT.FONT_ROBOTO_BOLD(20));
    title.add(titleLabel);
    title.setBackground(new Color(2, 131, 145));

    JPanel topDialog = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);

    // Tạo Panel chứa tên khách hàng.
    JLabel nameLabel = new JLabel("Tên khách hàng:");
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    topDialog.add(nameLabel, gbc);
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    topDialog.add(customerNameField, gbc);

    // Tạo panel chứa số điện thoại.
    JLabel phoneLabel = new JLabel("Số điện thoại:");
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 1;
    topDialog.add(phoneLabel, gbc);
    gbc.gridx = 1;
    gbc.gridy = 2;
    gbc.gridwidth = 3;
    topDialog.add(phoneNumberField, gbc);

    // Tạo panel chứa số điện thoại.
    JLabel emailLabel = new JLabel("Email:");
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 1;
    topDialog.add(emailLabel, gbc);
    gbc.gridx = 1;
    gbc.gridy = 3;
    gbc.gridwidth = 3;
    topDialog.add(emailField, gbc);

    // Tạo 2 button là Xác nhận và Huỷ. Sẽ style lại sau.
    JButton cancel = new JButton("Huỷ");
    cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    JButton confirm = new JButton("Xác nhận");
    confirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 1;
    topDialog.add(cancel, gbc);
    gbc.gridx = 1;
    gbc.gridy = 4;
    gbc.gridwidth = 3;
    topDialog.add(confirm, gbc);

    JPanel dialogPanel = new JPanel();
    dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
    addMovieDialog.add(dialogPanel);

    dialogPanel.add(title);
    dialogPanel.add(topDialog);
    addMovieDialog.add(dialogPanel);
  }

  public static void addListArea(List<AreaForm> list) {
    centerAreasPanel.removeAll();
    for (AreaForm areaForm : list) {
      centerAreasPanel.add(areaForm);
    }
    centerAreasPanel.revalidate();
    centerAreasPanel.repaint();
  }

  public void showMe() {
    this.setVisible(true);
  }


  public static void showError(Component component, String message) {
    if (component == null) {
      JOptionPane.showMessageDialog(dashboardPane,
              message,
              "Error",
              JOptionPane.ERROR_MESSAGE);
    } else {
      JOptionPane.showMessageDialog(component,
              message,
              "Error",
              JOptionPane.ERROR_MESSAGE);
    }
  }

  public static void showSuccess(Component component, String message) {
    if (component == null) {
      JOptionPane.showMessageDialog(dashboardPane,
              message,
              "Success",
              JOptionPane.INFORMATION_MESSAGE);
    } else {
      JOptionPane.showMessageDialog(component,
              message,
              "Success",
              JOptionPane.INFORMATION_MESSAGE);
    }
  }

  public static JDialog getDialogInstance() {
    return addMovieDialog;
  }

  public static void showMovieTable(Movie movie) {
    // Phương thức hiển thị danh sách phim đã thêm gồm: STT, Ngày chiếu và tên phim.
    // Kiểm tra xem id phim đã tồn tại hay chưa.
    for (int i = 0; i < model.getRowCount(); i++) {
      Integer idMovie = (Integer) model.getValueAt(i, 1);
      if (idMovie == movie.getMovieId())
        return;
    }
    // Nếu id phim chưa tồn tại thì thêm phim này vào.
    model.addRow(new Object[]{listMovieTable.getRowCount() + 1, movie.getMovieId(), movie.getTitle(), ConverDateToString.convertDateToString(movie.getReleaseDate())});
    listMovieTable.revalidate();
    listMovieTable.repaint();
  }


  public static void startClient(String host, int port) {
    try {
      Socket socket = new Socket(host, port); // Tạo socket để kết nối tới Server.
      client = new Client(socket);
      Thread thread = new Thread(client); //Tạo một thread client để thực hiện nhận dữ liệu từ Server và gửi dữ liệu tới Server.
      thread.start();
      showSuccess(null, "Đã kết nối tới Server");
    } catch (RuntimeException | IOException e) {
      showError(null, e.getMessage());
      System.out.println(e.getMessage() + " - TicketBookingClient.startClient()");
    }
  }

  public static void closeClient() {
    // Phương thức này sẽ ngắt kết nối đối với Server.
    client.close();
    showSuccess(null, "Đóng kết nối thành công!");
  }

  public static void main(String[] args) {
    try {
      TicketBookingViewClient view = new TicketBookingViewClient();
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      view.showMe();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
