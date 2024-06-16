package model;

import javax.swing.table.DefaultTableModel;
import java.io.Serializable;
import java.util.List;

// Là đối tượng dùng để chuyển giữa Client - Server.
public class BookingInfo implements Serializable {
  private static final long serialVersionUID = 130720L;
  private String clientName;  // Tên đặt vé của người dùng.
  private String clientPhoneNumber;
  private String email;
  private List<SeatSelected> seatSelectedList;

  public BookingInfo(String clientName, String clientPhoneNumber, String email, List<SeatSelected> seatSelectedList) {
    this.clientName = clientName;
    this.clientPhoneNumber = clientPhoneNumber;
    this.email = email;
    this.seatSelectedList = seatSelectedList;
  }

  public String getClientName() {
    return clientName;
  }

  public String getClientPhoneNumber() {
    return clientPhoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public List<SeatSelected> getSeatSelectedList() {
    return seatSelectedList;
  }

  @Override
  public String toString() {
    return "BookingInfo{" +
            "clientName='" + clientName + '\'' +
            ", clientPhoneNumber='" + clientPhoneNumber + '\'' +
            ", email='" + email + '\'' +
            ", seatSelectedList=" + seatSelectedList.toString() +
            '}';
  }
}
