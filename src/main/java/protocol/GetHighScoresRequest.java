package protocol;

import java.io.Serializable;

public class GetHighScoresRequest implements Serializable
{
	//Har til formål at hente highscore i databasen og vise den i klient terminal
    private int number;

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }
    
}