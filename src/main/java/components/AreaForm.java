package components;

import config.FONT;
import model.Area;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class AreaForm extends JPanel {

  private TimePicker timePicker;

  private final JTextField nameField = new JTextField(20);
  private final JComboBox<Integer> rowsComboBox = new IntegerComboBox(20);
  private final JComboBox<Integer> columnsComboBox = new IntegerComboBox(20);
  private final JTextField regularPriceField = new JTextField(20);
  private final JTextField vipPriceField = new JTextField(20);

  private Border createCustomBorder(String position) {
    Border lineBorder = new LineBorder(Color.DARK_GRAY, 2);
    Border emptyBorder = new EmptyBorder(10, 10, 10, 10);
    TitledBorder titledBorder = new TitledBorder(lineBorder, "Thông tin rạp chiếu " + position, TitledBorder.CENTER, TitledBorder.TOP);
    return new CompoundBorder(titledBorder, emptyBorder);
  }

  public AreaForm(String position) {
    super();
    setSize(400, 400);
    setBorder(createCustomBorder(position));
    setLayout(new FlowLayout());  // mặc định là center.
    timePicker = new TimePicker();
    JPanel left = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel pos = new JLabel(position);
    left.setBackground(new Color(66, 72, 116));
    pos.setFont(FONT.FONT_ROBOTO_ITALIC(20));
    pos.setForeground(Color.WHITE);
    left.add(pos);
// Right panel
    JPanel right = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);

    // Adding components
    gbc.gridx = 0;
    gbc.gridy = 0;
    right.add(new JLabel("Xuất chiếu:"), gbc);

    gbc.gridx = 1;
    gbc.gridy = 0;
    right.add(timePicker, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    right.add(new JLabel("Name:"), gbc);

    gbc.gridx = 1;
    gbc.gridy = 2;
    right.add(nameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    right.add(new JLabel("Số hàng ghế:"), gbc);

    gbc.gridx = 1;
    gbc.gridy = 3;
    right.add(rowsComboBox, gbc);

    gbc.gridx = 0;
    gbc.gridy = 4;
    right.add(new JLabel("Số cột ghế:"), gbc);

    gbc.gridx = 1;
    gbc.gridy = 4;
    right.add(columnsComboBox, gbc);

    gbc.gridx = 0;
    gbc.gridy = 5;
    right.add(new JLabel("Giá ghế thường:"), gbc);

    gbc.gridx = 1;
    gbc.gridy = 5;
    right.add(regularPriceField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 6;
    right.add(new JLabel("Giá ghế VIP:"), gbc);

    gbc.gridx = 1;
    gbc.gridy = 6;
    right.add(vipPriceField, gbc);

    add(left);
    add(right, BorderLayout.CENTER);
  }

  // Phương thức để lấy giá trị từ các trường đầu vào
  public String getName() {
    return nameField.getText();
  }

  public int getRows() {
    return (int) rowsComboBox.getSelectedItem();
  }

  public int getColumns() {
    return (int) columnsComboBox.getSelectedItem();
  }

  public int getRegularPrice() {
    return Integer.parseInt(regularPriceField.getText());
  }

  public int getVipPrice() {
    return Integer.parseInt(vipPriceField.getText());
  }

  public Area getArea() {
    return new Area(getName(), getRegularPrice(), getVipPrice(), getRows(), getColumns(), timePicker.getTime());
  }

}
