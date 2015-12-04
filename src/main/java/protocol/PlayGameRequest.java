package protocol;

import java.io.Serializable;

public class PlayGameRequest implements Serializable 
{
	//Har til formål at starte et spil. spillet skal dog være oprettet først
	private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}

