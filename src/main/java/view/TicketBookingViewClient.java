package view;

import com.toedter.calendar.JDateChooser;
import components.AreaForm;
import components.CinemaPanel;
import model.Area;
import model.Movie;
import server.Server;
import service.TicketBookingService;
import utils.ConverDateToString;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TicketBookingViewClient extends JFrame {

  private static final JTabbedPane tabbedPane = new JTabbedPane();
  private static final JPanel dashboardPane = new JPanel(); // Là khu vực dành để thêm thông tin phim sắp chiếu.
  private static final JPanel serverInformation = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));  // Là khu vực chứa thông tin cấu hình server - danh sách client đã kết nối tới server.
  private static final JPanel centerAreasPanel = new JPanel();
  private static final TicketBookingService service = new TicketBookingService();
  private static final JTextField nameField = new JTextField(30);
  ;
  private static JTable table;
  private static JDateChooser dateChooser = new JDateChooser(new Date());
  private static final JComboBox<Integer> areasComboBox = new JComboBox<>();
  private static JDialog addMovieDialog;
  private static DefaultTableModel model;
  private static JPanel cinemaPanel;
  private static final JTextField portField = new JTextField(20);
  private static Thread serverThread;
  private static Server server;

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

    // Phần phía bên trái sẽ bao gồm button để REFRESH danh sách phim, bảng chứa danh sách phim đã thêm và thông tin ghế đã chọn bao gồm cả tổng chi phí lẫn voucher.
    JPanel addMoviePanel = new JPanel();
    addMoviePanel.setSize(new Dimension(600, 450));
    addMoviePanel.setLayout(new BoxLayout(addMoviePanel, BoxLayout.Y_AXIS));

    //

    // Button Thêm phim mới.
    JButton addMovieButton = new JButton("Thêm phim mới");
    addMovieButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    addMovieButton.setName("add-movie");
    addMovieButton.addActionListener(service);

    // Tạo dữ liệu cho table lưu danh sách các phim được tạo bởi hệ thống.
    model = new DefaultTableModel();
    model.addColumn("Số thứ tự");
    model.addColumn("Mã phim");
    model.addColumn("Tên phim");
    model.addColumn("Ngày chiếu");
    table = new JTable(model);
    JScrollPane tableScrollPane = new JScrollPane(table);
    tableScrollPane.setSize(new Dimension(600, 450));
    tableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setPreferredScrollableViewportSize(new Dimension(600, 450));
    table.addMouseListener(service);

    addMoviePanel.add(addMovieButton);
    addMoviePanel.add(tableScrollPane);

    // Panel phía bên phải bao gồm một CinemaPanel và bên dưới bao gồm thông tin các loại ghế ngồi và trạng thái.
    cinemaPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 50, 10));
    cinemaPanel.setSize(new Dimension(700, 500));
    JScrollPane cinemaScrollPane = new JScrollPane(cinemaPanel);
    cinemaScrollPane.setPreferredSize(new Dimension(700, 500));
    cinemaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    cinemaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    bottomPanel.add(addMoviePanel);
    bottomPanel.add(cinemaScrollPane);
    dashboardPane.add(bottomPanel);


    // CẤU HÌNH CHO PHẦN SERVER CONFIG. SẼ CHIA THÀNH 2 PHẦN, BÊN TRÁI LÀ THÔNG TIN SERVER VÀ BÊN PHẢI DANH SÁCH CLIENT.

    // Tạo 2 Panel, config chứa thông tin của server như port... Và panel còn lại chứa thông tin của các client.
    JPanel config = new JPanel();
    JPanel client = new JPanel();
    config.setBackground(Color.CYAN);
    client.setBackground(Color.YELLOW);
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, config, client);
    splitPane.setPreferredSize(new Dimension(1000, 500));
    splitPane.setDividerLocation(1000 / 2);
    splitPane.setOneTouchExpandable(true);
    serverInformation.add(splitPane);

    // Cấu hình cho phần panel config.
    config.setLayout(new BoxLayout(config, BoxLayout.Y_AXIS));
    config.setPreferredSize(new Dimension(500, 300));
    config.add(new JLabel("Thông tin cấu hình máy chủ"));
    JPanel configPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    configPanel.add(new JLabel("Port Server: "), gbc);
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    portField.setText("8080");
    configPanel.add(portField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    JButton start = new JButton("Start Server");
    start.setName("start-server");
    start.addActionListener(service);
    configPanel.add(start, gbc);
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    JButton close = new JButton("Close Server");
    close.setName("close-server");
    close.addActionListener(service);
    configPanel.add(close, gbc);
    config.add(configPanel);


    // Cấu hình cho phần panel client
    client.setLayout(new BoxLayout(client, BoxLayout.Y_AXIS));
    client.setPreferredSize(new Dimension(500, 300));
    client.add(new JLabel("Thông tin máy khách"));
    JPanel buttonPanel = new JPanel(new GridBagLayout());
    GridBagConstraints _gbc = new GridBagConstraints();
    _gbc.fill = GridBagConstraints.HORIZONTAL;
    _gbc.insets = new Insets(5, 5, 5, 5);
    _gbc.gridx = 0;
    _gbc.gridy = 0;
    _gbc.gridwidth = 1;
    JButton disconnect = new JButton("Disconnect");
    disconnect.setName("disconnect");
    disconnect.addActionListener(service);
    buttonPanel.add(disconnect, _gbc);
    _gbc.gridx = 1;
    _gbc.gridy = 0;
    _gbc.gridwidth = 1;
    JButton refresh = new JButton("Refresh");
    refresh.setName("refresh");
    refresh.addActionListener(service);
    buttonPanel.add(refresh, _gbc);
    client.add(buttonPanel);

  }


  public static void generateCinemaPanel(List<Area> areas) {
    cinemaPanel.removeAll();
    for (Area area : areas) {
      cinemaPanel.add(new CinemaPanel(area, area.getRegularPrice(), area.getVipPrice()));
    }
    cinemaPanel.revalidate();
    cinemaPanel.repaint();
  }

  public static void showAddMovieDialog() {
    // Tạo JDialog
    addMovieDialog = new JDialog();
    addMovieDialog.setSize(500, 700);
    addMovieDialog.setLocationRelativeTo(null);

    JPanel topDialog = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);

    // Tạo Panel chứa ngày chiếu.
    JLabel timeLabel = new JLabel("Ngày chiếu:");
    dateChooser.setDateFormatString("dd-MM-yyyy");
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 1;
    topDialog.add(timeLabel, gbc);
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    topDialog.add(dateChooser, gbc);

    // Tạo panel chứa tên phim.
    JLabel nameLabel = new JLabel("Tên phim:");
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 1;
    topDialog.add(nameLabel, gbc);
    gbc.gridx = 1;
    gbc.gridy = 2;
    gbc.gridwidth = 3;
    topDialog.add(nameField, gbc);


    // Tạo panel cấu hình số lượng rạp chiếu - mỗi rạp sẽ có các thông tin như tên rạp, số hàng, cột và giá cơ bản cho mỗi loại ghế.
    JLabel topAreasLabel = new JLabel("Số Rạp Chiếu");
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 1;
    topDialog.add(topAreasLabel, gbc);

    areasComboBox.setName("area");
    areasComboBox.addActionListener(new TicketBookingService());
    areasComboBox.addItem(0);
    areasComboBox.addItem(1);
    areasComboBox.addItem(2);
    areasComboBox.addItem(3);
    areasComboBox.addItem(4);
    areasComboBox.addItem(5);
    areasComboBox.addItem(6);
    areasComboBox.addItem(7);
    areasComboBox.addItem(8);
    areasComboBox.addItem(9);
    areasComboBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
    gbc.gridx = 1;
    gbc.gridy = 3;
    gbc.gridwidth = 3;
    topDialog.add(areasComboBox, gbc);

    JPanel areas = new JPanel();
    areas.setLayout(new BoxLayout(areas, BoxLayout.Y_AXIS));
    areas.setPreferredSize(new Dimension(450, 450));

    centerAreasPanel.setLayout(new BoxLayout(centerAreasPanel, BoxLayout.Y_AXIS));
    centerAreasPanel.setSize(new Dimension(400, 450));

    JScrollPane centerAreasScrollPane = new JScrollPane(centerAreasPanel);
    centerAreasScrollPane.setPreferredSize(new Dimension(400, 450));
    centerAreasScrollPane.setViewportView(centerAreasPanel);
    centerAreasScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    areas.add(centerAreasScrollPane);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
    JButton saveButton = new JButton("Lưu");
    saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    saveButton.setName("save-movie");
    saveButton.addActionListener(service);
    buttonPanel.add(saveButton);

    // Sắp xếp các thành phần giao diện trong dialog
    JPanel dialogPanel = new JPanel();
    dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
    dialogPanel.add(topDialog);
    dialogPanel.add(areas);
    dialogPanel.add(buttonPanel);
    addMovieDialog.add(dialogPanel);

    // Hiển thị dialog
    addMovieDialog.setVisible(true);
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

  public static JDateChooser getDateChooser() {
    return dateChooser;
  }

  public static String getMovieName() {
    return nameField.getText();
  }

  public static int getNumsArea() {
    return Integer.parseInt(areasComboBox.getSelectedItem().toString());
  }


  public static List<Component> getListArea() {
    return Arrays.stream(centerAreasPanel.getComponents()).toList();
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
    model.addRow(new Object[]{table.getRowCount() + 1, movie.getMovieId(), movie.getTitle(), ConverDateToString.convertDateToString(movie.getReleaseDate())});
    table.revalidate();
    table.repaint();
  }

  public static JTextField getPortField() {
    return portField;
  }

  public static void startServer(int port) {
    // Truyền vào port và bắt đầu cho Server lắng nghe Client.
    server = new Server(port);
    serverThread = new Thread(server);
    serverThread.start();
  }

  public static void closeServer() {
    // Đóng kết nối từ Server.
    server.close();
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
