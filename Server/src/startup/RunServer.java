package startup;

import networking.Server;

import java.io.IOException;

public class RunServer {

    public static void main(String[] args) throws IOException {
        ServiceLocator serviceLocator = new ServiceLocator();
        Server server = new Server(serviceLocator);
        server.start();
    }
}
