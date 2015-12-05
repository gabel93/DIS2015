package server.tui;

import java.util.List;

import server.backend.Services;
import database.Game;

public class MakeGames {

	public static void main(String[] strings)
    {
        String[] names = {"", "", ""};
        for(String name : names)
        {
            Game game = new Game();
            game.setName(name);
            Services.INSTANCE.add(game);
        }
        List<Game> games = Services.INSTANCE.getGames();
        System.out.println("Current games: ");
        for(Game game : games)
        {
            System.out.println(game.getName());
        }
    }
}

}
 