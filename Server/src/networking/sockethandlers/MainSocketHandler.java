package networking.sockethandlers;

import dtos.Request;
import dtos.Response;
import dtos.error.ErrorResponse;
import model.exceptions.BusinessLogicException;
import model.exceptions.NotFoundException;
import model.exceptions.ValidationException;
import networking.exceptions.InvalidActionException;
import startup.ServiceLocator;

import java.io.*;
import java.net.Socket;

public class MainSocketHandler implements Runnable
{

    // I have specific SocketHandlers, generally per entity type, but not necessarily.
    // It is to split things out a little bit. Make parallel work easier.
    // The responsibility of this class is to
    // * receive the request from the client
    // * figure out which specific handler to pass the request to
    // * handle any exceptions thrown from anywhere in the server
    // * respond back to the client with either a success or failure message

    private final Socket clientSocket;
    private final ServiceLocator serviceLocator;

    public MainSocketHandler(Socket clientSocket, ServiceLocator serviceLocator)
    {
        this.clientSocket = clientSocket;
        this.serviceLocator = serviceLocator;
    }

    @Override
    public void run()
    {
        try
        {
            ObjectInputStream incomingData = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream outgoingData = new ObjectOutputStream(clientSocket.getOutputStream());
            handleRequestWithErrorHandling(incomingData, outgoingData);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                clientSocket.close();
            }
            catch (IOException _)
            {
            }
        }
    }

    private void handleRequestWithErrorHandling(ObjectInputStream incomingData, ObjectOutputStream outgoingData) throws IOException
    {
        try
        {
            handleRequest(incomingData, outgoingData);
        }
        catch (ValidationException | BusinessLogicException | NotFoundException | InvalidActionException e)
        {
            ErrorResponse payload = new ErrorResponse(e.getMessage());
            Response error = new Response("ERROR", payload);
            outgoingData.writeObject(error);
        }
        catch (ClassCastException e)
        {
            ErrorResponse payload = new ErrorResponse("Invalid request");
            Response error = new Response("ERROR", payload);
            outgoingData.writeObject(error);
        }
        catch (Exception e)
        {
            ErrorResponse payload = new ErrorResponse(e.getMessage());
            Response error = new Response("SERVER_FAILURE", payload);
            e.printStackTrace();
            outgoingData.writeObject(error);
        }
    }

    private void handleRequest(ObjectInputStream incomingData, ObjectOutputStream outgoingData) throws IOException, ClassNotFoundException
    {
        Request request = (Request) incomingData.readObject();
        SocketHandler handler = switch (request.handler())
        {
            case "auth" -> serviceLocator.getAuthenticationSocketHandler();
            case "users" -> serviceLocator.getUserSocketHandler();
            default -> throw new IllegalStateException("Unexpected value: " + request.handler());
        };

        Object result = handler.handle(request.action(), request.payload());
        Response response = new Response("SUCCESS", result);
        outgoingData.writeObject(response);
    }
}
