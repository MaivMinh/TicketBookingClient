package client;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable{
  private static int port;
  private static String host;
  private Socket clientSocket;
  private ObjectOutputStream oos;
  private ObjectInputStream ois;

  public Client(String _host, int _port, Socket _clientSocket) {
    // Phương thức này dùng để tạo lập đối tượng Client ở trạng thái New State.
    host = _host;
    port = _port;
    this.clientSocket = _clientSocket;
    try {
      this.oos = new ObjectOutputStream(clientSocket.getOutputStream());
      this.ois = new ObjectInputStream(clientSocket.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void listenFromServer() {
    new Thread(new Runnable() {
      // Thực hiện nhận dữ liệu được gửi từ Server.
      @Override
      public void run() {
        try {
          while (clientSocket.isConnected())  {
            Object  data = ois.readObject();
            
          }
        } catch (IOException | ClassNotFoundException exception) {
          System.out.println(exception.getMessage() + " - Client.listenFromServer()");
        }
      }
    }).start();
  }


  @Override
  public void run() {
    // Phương thức này đưa Thread vào trạng thái Runnable State.

  }

}
