package client;

import model.BookingInfo;
import model.Movie;
import repository.TicketBookingRepo;
import view.TicketBookingViewClient;

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
      if (!clientSocket.isClosed() && oos != null) {
        oos.writeObject(infor);
        oos.flush();
      }
    } catch (IOException e)  {
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
          // Ở thời điểm ban đầu vừa kết nối, Server sẽ gửi danh sách các Movie đã thêm trước đó sang cho Client mới này.
          List<Object> list = (List<Object>) ois.readObject();
          for (Object object : list) {
            if (object instanceof Movie movie) {
              TicketBookingRepo.addMovie(movie);
              TicketBookingViewClient.showMovieTable(movie);
            }
          }

          while (!clientSocket.isClosed()) {
            Object data = ois.readObject();
            if (data instanceof Movie movie) {
              // Sau khi mà có được movie rồi thì cần phải thêm vào repo.
              TicketBookingRepo.addMovie(movie);
              TicketBookingViewClient.showMovieTable(movie);
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
