package server.tui;

import java.util.List;

import server.backend.Services;
import database.Game;

//Denne klasse har til form�l at lave spil. med nyt navn, man kan ikke k�rer med samme navn
//Og det spil bliver s� listet sammen med den andre spil som er blevet tilf�jet.
public class MakeGames {

	public static void main(String[] strings)
    {
        String[] names = {"inds�t", "nogle", "navne"};
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

 