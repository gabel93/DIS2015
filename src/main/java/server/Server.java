package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import protocol.CreateGameRequest;
import protocol.DeleteGameRequest;
import protocol.GetGameRequest;
import protocol.GetHighScoresRequest;
import protocol.HelloRequest;
import protocol.LoginRequest;
import protocol.LogoutRequest;
import protocol.PlayGameRequest;
import protocol.SetCommandsRequest;
import server.backend.Services;
import database.Game;
import database.User;

public class Server
{
    public static void main(String[] strings)
    {
        new Server(2345).start();
    }
    
    private final int port;
    
    public Server(int port)
    {
        this.port = port;
    }
    
    public void start()
    {
        try(ServerSocket serverSocket = new ServerSocket(port))
        {
            System.out.println("Server is now started at " + port + ".");
            while(true)
            {
                Socket socket = serverSocket.accept();
                System.out.println("Client is connected from " + socket.getRemoteSocketAddress() + ".");
                
                new SocketThread(socket).start();
            }
        }catch(IOException e)
        {
            throw new RuntimeException(e);
        }
        
    }
    
    private static class SocketThread extends Thread
    {
        private final Socket socket;
        
        public SocketThread(Socket socket)
        {
            this.socket = socket;
        }
        
        @Override
        public void run()
        {
            try(ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());)
            {
                while(true)
                {
                    try
                    {
                        if(!this.handleRequest(inputStream, outputStream))
                        {
                            break;
                        }
                    }catch(RuntimeException e)
                    {
                        outputStream.writeObject(false);
                    }
                }
            }catch(Exception e)
            {
                ;
            }
        }
        
   //De forskellige menupunkter bliver tjekket en for en i login. 
   //Handlerrequest er oprettet som boolean, hvilket er ensbetydende med; 
   Der enten godtages en request, hvis den er sand,
        
   //eller springer videre til n�ste.
   //De forskellige request er inde under protocol "package" og er simple klasser der kan hente og sende data.
   //tjekker hvilken request der skal håndteres
        private boolean handleRequest(ObjectInputStream inputStream, ObjectOutputStream outputStream) throws Exception
        {
            Object request = inputStream.readObject();
            if(request instanceof HelloRequest)
            {
                String name = ((HelloRequest) request).getName();
                outputStream.writeObject("Hello " + name);
            }else if(request instanceof LoginRequest)
            {
                LoginRequest loginRequest = (LoginRequest) request;
                String loginKey = Services.INSTANCE.login(loginRequest.getUsername(), loginRequest.getPassword());
                outputStream.writeObject(loginKey);
            }else if(request instanceof LogoutRequest)
            {
                User user = Services.INSTANCE.getUserByUsername(((LogoutRequest) request).getUsername());
                boolean successful = Services.INSTANCE.logout(user.getId());
                outputStream.writeObject(successful);
                return false;
            }else if(request instanceof CreateGameRequest)
            {
                Game game = Services.INSTANCE.add(((CreateGameRequest) request).getGame());
                outputStream.writeObject(game);
            }else if(request instanceof DeleteGameRequest)
            {
                Game game = Services.INSTANCE.getGameByName(((DeleteGameRequest) request).getName());
                Services.INSTANCE.deleteGame(game.getId());
                outputStream.writeObject(true);
            }else if(request instanceof SetCommandsRequest)
            {
                SetCommandsRequest setCommandRequest = (SetCommandsRequest) request;
                Game game = Services.INSTANCE.getGameByName(setCommandRequest.getGameName());
                boolean successful = Services.INSTANCE.playGame(game.getId(), setCommandRequest.getUsername(), setCommandRequest.getCommands());
                outputStream.writeObject(successful);
            }else if(request instanceof PlayGameRequest)
            {
                Game game = Services.INSTANCE.getGameByName(((PlayGameRequest) request).getName());
                if(game == null)
                {
                    outputStream.writeObject(null);
                }else
                {
                    Game resultGame = Services.INSTANCE.playGame(game.getId());
                    outputStream.writeObject(resultGame);
                }
            }else if(request instanceof GetHighScoresRequest)
            {
                List<Game> games = Services.INSTANCE.getHighScores(((GetHighScoresRequest) request).getNumber());
                outputStream.writeObject(games);
            }else if(request instanceof GetGameRequest)
            {
                Game game = Services.INSTANCE.getGameByName(((GetGameRequest) request).getName());
                outputStream.writeObject(game);
            }
            return true;
        }
    };
}
