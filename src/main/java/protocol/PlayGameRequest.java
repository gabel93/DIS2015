package protocol;

import java.io.Serializable;

public class PlayGameRequest implements Serializable 
{
	//Har til form�l at starte et spil. spillet skal dog v�re oprettet f�rst
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

