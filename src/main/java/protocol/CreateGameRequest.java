package protocol;

import java.io.Serializable;

import database.Game;

public class CreateGameRequest 
implements Serializable
{
	//Har til formål at oprette spil i terminalen som f.eks. et snake spil
    private Game game;

    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }
}
