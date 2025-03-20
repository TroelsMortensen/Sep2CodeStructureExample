package networking;

import networking.sockethandlers.MainSocketHandler;
import startup.ServiceLocator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ServiceLocator serviceLocator;

    public Server(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(2910);
        System.out.println("Server started, listening for connections...");
        while(true){
            Socket socket = serverSocket.accept();
            MainSocketHandler socketHandler = new MainSocketHandler(socket, serviceLocator);
            Thread socketThread = new Thread(socketHandler);
            socketThread.start();
        }
    }
}
