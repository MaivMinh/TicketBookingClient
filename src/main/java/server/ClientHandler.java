package server;

import java.net.Socket;

public class ClientHandler implements Runnable{
  private Socket socket;
  private String username;
  private String email;

  public ClientHandler(Socket _socket) {
    this.socket = _socket;
  }

  @Override
  public void run() {

  }
}
