package client;

import model.*;
import repository.SeatBookedRepo;
import repository.SeatSelectedRepo;
import repository.TicketBookingRepo;
import view.TicketBookingViewClient;

import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class Client implements Runnable {
  private Socket clientSocket;
  private ObjectOutputStream oos;
  private ObjectInputStream ois;

  public Client(Socket _clientSocket) {
    // Phương thức này dùng để tạo lập đối tượng Client ở trạng thái New State.
    this.clientSocket = _clientSocket;
    try {
      this.clientSocket.setKeepAlive(true);
      this.oos = new ObjectOutputStream(clientSocket.getOutputStream());
      this.ois = new ObjectInputStream(clientSocket.getInputStream());
    } catch (IOException e) {
      System.out.println(e.getMessage() + " - Client.Client(Socket)");
    }
  }

  public void sendDataToServer(BookingInfo infor) {
    // HÀM NÀY CHỊU TRÁCH NHIỆM CHO VIỆC GỬI DỮ LIỆU CHO SERVER.
    try {
      if (!clientSocket.isClosed()) {
        oos.writeObject(infor);
        oos.flush();
      }
    } catch (IOException e) {
      System.out.println(e.getMessage() + " - Client.sendDataToServer()");
      closeAll();
    }
  }

  public void closeAll() {
    // HÀM NÀY CHỊU TRÁCH NHIỆM CHO VIỆC ĐÓNG KẾT NỐI.
    try {
      if (!clientSocket.isClosed()) {
        clientSocket.close();
      }
      if (oos != null) {
        oos.close();
      }
      if (ois != null) {
        ois.close();
      }
    } catch (IOException e) {
      TicketBookingViewClient.showError(null, e.getMessage());
      System.out.println(e.getMessage() + " - Client.closeAll()");
    }
  }

  public void receiveDataFromServer() {
    new Thread(new Runnable() {
      // Thực hiện nhận dữ liệu được gửi từ Server.
      @Override
      public void run() {
        // HÀM NÀY CHỊU TRÁCH NHIỆM CHO VIỆC ĐỌC DỮ LIỆU TỪ SERVER GỬI VỀ.
        try {
          Object result = ois.readObject();
          if (result instanceof MoviesAndBookedSeat data) {
            List<Movie> movies = data.getMovies();
            List<SeatBooked> seats = data.getSeats();
            for (Movie movie : movies) {
              TicketBookingRepo.addMovie(movie);
              TicketBookingViewClient.showMovieTable(movie);
            }
            for (SeatBooked seat : seats) {
              SeatBookedRepo.addBookedSeat(seat);
            }
          }

          while (!clientSocket.isClosed()) {
            Object data = ois.readObject();
            if (data instanceof Movie movie) {
              // Sau khi mà có được movie rồi thì cần phải thêm vào repo.
              TicketBookingRepo.addMovie(movie);
              TicketBookingViewClient.showMovieTable(movie);
            } else if (data instanceof Boolean isBooked) {
              // Một hoặc toàn bộ ghế ngồi không thể đặt được.
              if (isBooked) {
                TicketBookingViewClient.showSuccess(null, "Đặt ghế thành công!");
                TicketBookingViewClient.repaintSeatBooked();
                TicketBookingViewClient.repaintSelectedSeatTable();
              } else {
                TicketBookingViewClient.showSuccess(null, "Đặt ghế thất bại! Có thể ghế đã được đặt!");
                SeatSelectedRepo.removeLast(); // Xóa ghế đã chọn khỏi danh sách.
              }
            } else if (data instanceof BookingInfo info) {
              String clientName = info.getClientName();
              String phoneNumber = info.getClientPhoneNumber();
              String email = info.getEmail();
              List<SeatSelected> list = info.getSeatSelectedList(); // Danh sách chứa các ghế đã đặt.
              SeatSelectedRepo.add(list); // Thêm vào repo.
              TicketBookingViewClient.repaintSeatBooked();
            }
          }
        } catch (IOException | ClassNotFoundException exception) {
          System.out.println(exception.getMessage() + " - Client.receiveDataFromServer()");
        }
      }
    }).start();
  }

  @Override
  public void run() {
    // Phương thức này đưa Thread vào trạng thái Runnable State.
    receiveDataFromServer();
  }

}
