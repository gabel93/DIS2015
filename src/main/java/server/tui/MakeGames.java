package server.tui;

import java.util.List;

import server.backend.Services;
import database.Game;

//Denne klasse har til formål at lave spil. med nyt navn, man kan ikke kører med samme navn
//Og det spil bliver så listet sammen med den andre spil som er blevet tilføjet.
public class MakeGames {

	public static void main(String[] strings)
    {
        String[] names = {"indsæt", "nogle", "navne"};
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

 