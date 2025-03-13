package startup;

import networking.Server;

public class RunServer {

    public static void main(String[] args) {
        ServiceLocator serviceLocator = new ServiceLocator();
        Server server = new Server(serviceLocator);
        server.start();
    }
}
