package server;

import view.TicketBookingViewClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
  private ServerSocket serverSocket;
  private int port;

  public Server(int _port) {
    try {
      serverSocket = new ServerSocket(port);
      port = _port;
    } catch (IOException e) {
      System.out.println(e.getMessage() + " - Server.Server()");
    }
  }


  // Giao diện của ứng dụng vẫn khởi động bình thường, chỉ khi nào mà admin nhấn nút Start Server ở Server Information Component thì Thread của Server mới tự động được phép chạy hàm start.
  @Override
  public void run() {
    try {
      TicketBookingViewClient.showSuccess(null, "Server is running on port\t" + port);
      while (!serverSocket.isClosed()) {
        // Chấp nhận kết nối từ Client.
        Socket socket = serverSocket.accept();  // Vì đây là blocking method nên sau khi thực hiện câu lệnh này, toàn bộ ứng dụng sẽ bị dừng bởi vì chúng đang được thực thi trên một Thread main duy nhất.

        // Tạo Thread cho mỗi socket ở  đây. Cứ mỗi kết nối được tạo ra thì chương trình Server sẽ tạo ra một Thread ClientHandler để giao tiếp với Client tương ứng.

        ClientHandler handler = new ClientHandler(socket);
        Thread thread = new Thread(handler);
        thread.start();
      }
    } catch (IOException e) {
      System.out.println(e.getMessage() + " - Server.run()");
      if (!serverSocket.isClosed())
        TicketBookingViewClient.showError(null, "Server is failed!");
    }
  }

  // Vì Server của chúng ta đang chạy trên một Thread duy nhất để lắng nghe kết nối từ Client. Do đó, nếu chúng ta muốn sử dụng close() để đóng kết nối thì bắt buộc phải sử dụng Thread khác để làm việc này.
  public void close() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          if (serverSocket == null) {
            TicketBookingViewClient.showError(null, "Unable to close Server Socket!");
          } else if (!serverSocket.isClosed()) {
            serverSocket.close();
            TicketBookingViewClient.showSuccess(null, "Server is closed!");
          }
        } catch (IOException e) {
          System.out.println(e.getMessage() + " - Server.close()");
          TicketBookingViewClient.showError(null, "Something went wrong when trying close Server Socket!");
        }
      }
    }).start();
  }
}
