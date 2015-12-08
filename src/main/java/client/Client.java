package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import protocol.CreateGameRequest;
import protocol.DeleteGameRequest;
import protocol.GetGameRequest;
import protocol.GetHighScoresRequest;
import protocol.HelloRequest;
import protocol.LoginRequest;
import protocol.LogoutRequest;
import protocol.PlayGameRequest;
import protocol.SetCommandsRequest;
import database.Game;

//Dette element i koden underminere advarslen, da det godt selv ved det kan kører
@SuppressWarnings("unchecked")

//Her connector klienten til serveren og giver adgang til brugermenuen. 
public class Client
{
  public static void main(String[] strings)
  {
      new Client().start();
  }
  //Programmet er bygget op efter request/response protocol, derfor er der importeret ObjectInputStreamm og ObjectOutputStream
  //til at simplificere processen hvor der hentes og sendes data.
  
  //Variabler instantieres
  private final Scanner scanner = new Scanner(System.in);
  private ObjectInputStream inputStream;
  private ObjectOutputStream outputStream;
  private String username;
  
  public void start()
  {
      System.out.print("Server host? [localhost]: ");
      String host = scanner.nextLine().trim();
      //bruger indtaster server host - er længden på det indtastede 0, vil det automatisk
      //være localhost
            if(host.length() == 0)
                {
          host = "localhost";
      }
      
      System.out.print("Server port? [2345]: ");
      String portString = scanner.nextLine().trim();
    //bruger indtaster server port - er længden på det indtastede 0, vil det automatisk
      //være port 2345
           if(portString.length() == 0)
      {
          portString = "2345";
      }
         //portnummeret vil være en string og oversÃ¦ttes herunder til en int
      int port = Integer.parseInt(portString);
      
      try(Socket socket = new Socket(host, port))
      {
      	// Etablerer forbindelse og opretter streams
          outputStream = new ObjectOutputStream(socket.getOutputStream());
          HelloRequest request = new HelloRequest();
          request.setName("world");
          outputStream.writeObject(request);
          inputStream = new ObjectInputStream(socket.getInputStream());
          inputStream.readObject();
          System.out.println("Server is now connected.");
          
          // Login.
          while(!this.login())
          {
              ;
          }
          
          // Execute operaions.
          options:
          while(true)
          {
              try
              {
                  int option = this.readOption("1. Create a game 2. Set commands 3. Get high scores 4. Delete a game 5. Start a game 6. Show a game 7. Logout : ", 1, 7);
                  switch(option)
                  {
                  case 1: this.createGame(); break;
                  case 2: this.setCommands(); break;
                  case 3: this.getHighScores(); break;
                  case 4: this.deleteGame(); break;
                  case 5: this.startGame(); break;
                  case 6: this.showGame(); break;
                  case 7: this.logout(); break options;
                  }
              }catch(Exception e)
              {
                  e.printStackTrace();
              }
          }
      }catch(Exception e)
      {
          throw new RuntimeException(e);
      }
  }
  
  private boolean login()
  {
      System.out.print("Username? [Admin]: ");
      username = scanner.nextLine();
      if(username.length() == 0)
      {
          username = "Admin";
      }

      System.out.print("Password? [Password]: ");
      String password = scanner.nextLine();
      if(password.length() == 0)
      {
          password = "Password";
      }
     
      //login request oprettes
      
      
      LoginRequest request = new LoginRequest();
      
    //username og password bliver defineret som users input. 
      request.setUsername(username);
      request.setPassword(password);
      String loginKey = (String) this.sendRequest(request);
      //der er loginkey så programmet har noget referere til når man skal logge ind.
      //denne boolean tjekker loginKey
      boolean successful = loginKey != null;
    //Da der bliver genereret et loginkey ved login kan vi antage,
      //at hvis loginkey ikke er lig null, vil brugeren være logget ind.
      System.out.println(successful ? "You are now logged in." : " You have failed to login.");
      
      return successful;
  }
  //logout request sendes til serv
  private void logout()
  {
      LogoutRequest request = new LogoutRequest();
      request.setUsername(username);
      this.sendRequest(request);
  }
    //gethighscores request sendes til serv
  private void getHighScores()
  {
      GetHighScoresRequest request = new GetHighScoresRequest();
      request.setNumber(10);
      
      List<Game> games = (List<Game>) this.sendRequest(request);
      System.out.println("High scores:");
      int index = 1;
      for(Game game : games)
      {
          System.out.println(index + "\t\t" + game.getName() + "\t\t" + game.getHighScore());
          index++;
      }
  }
   
//setcommands request sendes til serv
  private void setCommands()
  {
      System.out.print("Game name? ");
      String name = scanner.nextLine();
      
      System.out.print("Commands? ");
      String commands = scanner.nextLine();
      
      SetCommandsRequest request = new SetCommandsRequest();
      request.setUsername(username);
      request.setGameName(name);
      request.setCommands(commands);
      
      boolean successful = (boolean) this.sendRequest(request);
      System.out.println(successful ? "Commands is now set successfully." : "Your comands is incorrect(failed).");
  }
//creategame request sendes til serv
  private void createGame()
  {
      System.out.print("Game name? ");
      String name = scanner.nextLine();
      
      CreateGameRequest request = new CreateGameRequest();
      Game game = new Game();
      request.setGame(game);
      game.setName(name);
      
      Game resultGame = (Game) this.sendRequest(request);
      System.out.println(resultGame == null ? "You have failed to create game the certain game \"" + name + "\"." :"Game \"" + name + "\" created successfully.");
  }
 //startGame request sendes til serv
  private void startGame()
  {
      System.out.print("Game name? ");
      String name = scanner.nextLine();
      
      PlayGameRequest request = new PlayGameRequest();
      request.setName(name);
      
      Game game = (Game) this.sendRequest(request);
      if(game == null)
      {
          System.out.println("No such game(game does not exist.");
          return;
      }
      
      System.out.println(game.getPlayer1() + " V.S. " + game.getPlayer2());
      System.out.println(game.getLastResult());
  }
  //deleteGame request sendes til serv
  private void deleteGame()
  {
      System.out.print("Game name? ");
      String name = scanner.nextLine();
      
      DeleteGameRequest request = new DeleteGameRequest();
      request.setName(name);
      
      boolean successful = (boolean) this.sendRequest(request);
      System.out.println(successful ? "Game \"" + name + "\" Your game has been deleted successfully." : "The game has failed to be deleted \"" + name + "\".");
  }
//showgame request sendes til serv
  private void showGame()
  {
      System.out.print("Game name? ");
      String name = scanner.nextLine();
      
      GetGameRequest request = new GetGameRequest();
      request.setName(name);
      
      Game game = (Game) this.sendRequest(request);
      if(game == null)
      {
          System.out.println("No such game is valid.");
          return;
      }
      
      System.out.println(game.getName());
      
      String player1 = game.getPlayer1();
      if(player1 != null)
      {
          System.out.println(player1 + ": " + game.getCommands1());
      }
      
      String player2 = game.getPlayer2();
      if(player2 != null)
      {
          System.out.println(player2 + ": " + game.getCommands2());
      }
      
      String result = game.getLastResult();
      if(result != null)
      {
          System.out.println(result);
      }
  }
  
  private Object sendRequest(Object request)
  {
      try
      {
          outputStream.writeObject(request);
          Object response = inputStream.readObject();
          return response;
      }catch(Exception e)
      {
          ;
      }
      return null;
  }
  
  private int readOption(String question, int min, int max)
  {
      while(true)
      {
          System.out.print(question);
          try
          {
              int option = Integer.parseInt(scanner.nextLine().trim());
              if(option >= min&& option <= max)
              {
                  return option;
              }
          }catch(Exception e)
          {
              ;
          }
          System.out.println(" The input is invalid!");
      }
  }
}