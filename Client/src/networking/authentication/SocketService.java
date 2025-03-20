package networking.authentication;

import dtos.Request;
import dtos.Response;
import dtos.error.ErrorResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketService
{
    public static Object sendRequest(Request request)
    {
        try (Socket socket = new Socket("localhost", 2910);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream()))
        {
            outputStream.writeObject(request);
            Response response = (Response) inputStream.readObject();

            switch (response.status())
            {
                case "ERROR", "SERVER_FAILURE" ->
                        throw new RuntimeException(((ErrorResponse) response.payload()).errorMessage());
                case "SUCCESS" ->
                {
                }
                default -> throw new RuntimeException("Unknown response");
            }

            return response.payload();
        }
        catch (IOException e)
        {
            throw new RuntimeException("Could not connect to server!");
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException("Invalid response from server.");
        }
    }
}
